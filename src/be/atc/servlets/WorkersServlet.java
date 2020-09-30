package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.SexeType;
import be.atc.dataAccess.entities.TeamEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.RoleService;
import be.atc.dataAccess.services.TeamService;
import be.atc.dataAccess.services.WorkerService;
import be.atc.models.SexeTypeValidator;
import be.atc.models.UserModel;
import be.atc.models.Validator;
import be.atc.models.WorkerModel;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static be.atc.utils.ValidationUtils.hasContent;

@WebServlet(name = "WorkersServlet", urlPatterns = "/workers/*")
public class WorkersServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(WorkersServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "workers/";
    public static final String CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "create.jsp";
    public static final String LIST_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "list.jsp";
    public static final String EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "edit.jsp";
    public static final String DELETE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "delete.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> formErrorMessages = new ArrayList<>();
        List<String> systemErrorMessages = new ArrayList<>();
        List<String> systemSuccessMessages = new ArrayList<>();
        String forwardURL = "";

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/edit/checkID")) {
            String editID = request.getParameter("editID");
            int editIDAsInt;
            // On vérifie que l'id n'est pas null, ni blank et est un entier
            if (hasContent(editID)) {
                try {
                    editIDAsInt = Integer.parseInt(editID);
                    WorkerEntity workerToEdit = new WorkerService(EMF.getEM()).findByIdOrNull(editIDAsInt);
                    log.debug("worker to edit " + workerToEdit.toString());
                    //On verifie que le worker existe dans la DB
                    if (workerToEdit != null) {
                        request.setAttribute("worker", workerToEdit);
                        populateSelectOptionsForCreateOrEditView(request);
                        forwardURL = EDIT_VIEW_PATH;
                        //Le worker n'existe pas dans la DB
                    } else {
                        systemErrorMessages.add("Le worker que vous essayez d'éditer n'existe pas dans la DB");
                        forwardURL = LIST_VIEW_PATH;
                    }
                } catch (NumberFormatException e) {
                    systemErrorMessages.add("L'editID du worker n'est pas un chiffre entier. Réessayer d'éditer");
                    forwardURL = LIST_VIEW_PATH;
                }
            } else {
                systemErrorMessages.add("L'editID du worker n'a pas de contenu");
                forwardURL = LIST_VIEW_PATH;
            }
            request.setAttribute("systemErrorMessages", systemErrorMessages);
            request.getRequestDispatcher(forwardURL).forward(request, response);
        }

        if (requestURI.endsWith("/delete")) {
            String deleteID = request.getParameter("deleteID");
            int deleteIDAsInt;
            // On vérifie que l'id n'est pas null, ni blank et est un entier
            if (hasContent(deleteID)) {
                try {
                    deleteIDAsInt = Integer.parseInt(deleteID);
                    WorkerService service = new WorkerService(EMF.getEM());
                    WorkerEntity workerToDelete = service.findByIdOrNull(deleteIDAsInt);
                    //On verifie que le worker existe dans la DB
                    if (workerToDelete != null) {
                        request.setAttribute("worker", workerToDelete);
                        service.delete(workerToDelete);

                        systemSuccessMessages.add("Vous avez supprimé votre worker.");
                        request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                        request.getRequestDispatcher(LIST_VIEW_PATH).forward(request, response);
                        //Le worker n'existe pas dans la DB
                    } else {
                        systemErrorMessages.add("Le worker que vous essayez de supprimer n'existe pas dans la DB");
                        request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                        request.getRequestDispatcher(LIST_VIEW_PATH).forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    systemErrorMessages.add("Le deleteID du worker n'est pas un chiffre entier. Réessayer de supprimer.");
                    request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                    request.getRequestDispatcher(LIST_VIEW_PATH).forward(request, response);
                }
            } else {
                systemErrorMessages.add("L'deleteID du worker n'a pas de contenu");
                request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                request.getRequestDispatcher(LIST_VIEW_PATH).forward(request, response);
            }
            request.setAttribute("systemSuccessMessages", systemSuccessMessages);
            request.getRequestDispatcher(forwardURL).forward(request, response);
        }


        if (requestURI.endsWith("/create")
                || requestURI.endsWith("/edit")) {
            String appropriateViewPathToForwardTo = requestURI.endsWith("/create") ? CREATE_VIEW_PATH : EDIT_VIEW_PATH;
            log.debug("User attempts to create new worker");

            //On récupère les paramètres du formulaire
            UserModel userModel = new UserModel(request.getParameter("username"),
                    UserModel.generateRandomPassword());
            Validator userValidator = userModel.new UserValidator();
            WorkerModel workerModel = new WorkerModel(request.getParameter("firstName"),
                    request.getParameter("lastName"),
                    request.getParameter("birthdate"));
            Validator workerValidator = workerModel.new WorkerValidator();
            String inputSelectedSexe = request.getParameter("selected-sexe");
            log.debug("inputed sexe " + inputSelectedSexe);
            Validator sexeValidator = new SexeTypeValidator(inputSelectedSexe);
            String inputSelectedRoleId = request.getParameter("selected-role");
            String inputSelectedTeamId = request.getParameter("selected-team");

            // Les données du formulaires sont-elles valides?
            if (userValidator.isValid()
                    && workerValidator.isValid()
                    && sexeValidator.isValid()
                    && inputSelectedTeamId != null
                    && inputSelectedRoleId != null) {

                // On récupère le rôle choisi, s'il n'a pas été supprimé/modifié entre-temps
                log.debug("selected role id: " + inputSelectedRoleId);
                int selectedRoleId = Integer.parseInt(inputSelectedRoleId);
                RoleEntity selectedRole = new RoleService(EMF.getEM()).findByIdOrNull(selectedRoleId);

                // On récupère le team choisi, s'il n'a pas été supprimé/modifié entre-temps
                log.debug("selected team id: " + inputSelectedTeamId);
                int selectedTeamId = Integer.parseInt(inputSelectedTeamId);
                TeamEntity selectedTeam = new TeamService(EMF.getEM()).findByIdOrNull(selectedTeamId);

                //On récupère le sexe
                SexeType selectedSexeType = SexeType.valueOf(inputSelectedSexe);

                // L'équipe ou le role sélectionnés sont-ils en DB?
                if (selectedTeam != null
                        && selectedRole != null) {

                    // On crée un worker basé sur les entrées valides de l'utilisateur
                    EntityManager em = EMF.getEM();
                    WorkerService service = new WorkerService(em);
                    WorkerEntity inputWorker = service.create(workerModel.getFirstName(),
                            workerModel.getLastName(),
                            workerModel.getBirthdateAsLocalDate(),
                            userModel.getUsername(),
                            userModel.getPassword(),
                            selectedSexeType,
                            selectedRole,
                            selectedTeam);

                    if (requestURI.endsWith("/create")) {
                        // Le nouveau worker existe-t-il déjà dans la DB?
                        // Le worker est nouveau
                        if (!service.hasDuplicate(inputWorker)) {
                            service.save(inputWorker);

                            systemSuccessMessages.add("Votre nouveau worker a été créé");
                            request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                            request.getRequestDispatcher(CREATE_VIEW_PATH).forward(request, response);
                        }
                        //Le nouveau worker existe déjà
                        else {
                            systemErrorMessages.add("Le worker existe déjà");
                            request.setAttribute("systemErrorMessages", systemErrorMessages);
                            request.getRequestDispatcher(CREATE_VIEW_PATH).forward(request, response);
                        }
                    }

                    if (requestURI.endsWith("/edit")) {
                        String editID = request.getParameter("editID");
                        int editIDAsInt = Integer.parseInt(editID);

                        inputWorker.setId(editIDAsInt);
                        service.update(inputWorker);

                        systemSuccessMessages.add("Votre worker a été édité.");
                        request.setAttribute("systemSuccessMessages", systemSuccessMessages);
                        request.getRequestDispatcher("/workers/list").forward(request, response);
                    }
                }
                // Le rôle ou l'équipe n'ont pas été trouvées dans la DB
                else {
                    systemErrorMessages.add("Veuillez rafraichir la page et réessayer, le rôle et/ou l'équipe sélectionnés n'ont pas été trouvés");
                    request.setAttribute("systemErrorMessages", systemErrorMessages);
                    request.getRequestDispatcher(appropriateViewPathToForwardTo).forward(request, response);
                }
                // Les données du formulaires sont invalides
            } else {
                formErrorMessages.addAll(userValidator.getAllErrorMessagesOrEmpty());
                formErrorMessages.addAll(workerValidator.getAllErrorMessagesOrEmpty());
                formErrorMessages.addAll(sexeValidator.getAllErrorMessagesOrEmpty());
                if (inputSelectedRoleId == null) formErrorMessages.add("Veuillez sélectionner un rôle");
                if (inputSelectedTeamId == null) formErrorMessages.add("Veuillez sélectionner une équipe");
                request.setAttribute("formErrorMessages", formErrorMessages);
                request.getRequestDispatcher(appropriateViewPathToForwardTo).forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String forwardURL = "";
        if (requestURI.endsWith("/create")) {
            populateSelectOptionsForCreateOrEditView(request);
            forwardURL = CREATE_VIEW_PATH;
        }
        if (requestURI.endsWith("/list")) {
            WorkerService service = new WorkerService(EMF.getEM());
            Collection<WorkerEntity> allWorkers = service.findAllOrNull();

            // On retire l'utilisateur authentifié de la liste
            allWorkers.remove((WorkerEntity) request.getAttribute("authUser"));
            request.setAttribute("workers", allWorkers);
            forwardURL = LIST_VIEW_PATH;
        }
        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }

    private void populateSelectOptionsForCreateOrEditView(HttpServletRequest request) {
        // On peuple le select role
        RoleService roleService = new RoleService(EMF.getEM());
        Collection<RoleEntity> allRoles = roleService.findAllOrNull();
        request.setAttribute("roles", allRoles);

        // On peuple le select role
        TeamService teamService = new TeamService(EMF.getEM());
        Collection<TeamEntity> allTeams = teamService.findAllOrNull();
        request.setAttribute("teams", allTeams);

        // On peuple le select sexe
        request.setAttribute("sexes", SexeType.values());
    }
}
