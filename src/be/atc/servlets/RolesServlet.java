package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.AuthorityService;
import be.atc.dataAccess.services.RoleService;
import be.atc.models.RoleModel;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static be.atc.utils.ValidationUtils.isEntityId;

@WebServlet(name = "RolesServlet", urlPatterns = "/roles/*")
public class RolesServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RolesServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "roles/";
    public static final String CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "create.jsp";
    public static final String LIST_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "list.jsp";
    public static final String EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "edit.jsp";
    public static final String DELETE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "delete.jsp";
    public static final String SUCCESS_EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "success_edit.jsp";
    public static final String FAILURE_EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "failure_edit.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("User attemps to reach: " + requestURI);
        if (requestURI.endsWith("/create")) {
            request.getRequestDispatcher(CREATE_VIEW_PATH).forward(request, response);
            return;
        }

        if (requestURI.endsWith("/list")) {
            log.debug("User attemps to see list of roles");
            RoleService service = new RoleService(EMF.getEM());
            Collection<RoleEntity> allRoles = service.findAllOrNull();

            // On retire le rôle de l'utilisateur authentifié
            HttpSession session = request.getSession();
            WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");
            RoleEntity authUserRole = authUser.getRole();
            allRoles.remove(authUserRole);

            request.setAttribute("roles", allRoles);
            request.getRequestDispatcher(LIST_VIEW_PATH).forward(request, response);
            return;
        }

        if (requestURI.endsWith("/edit")) {
            //recup le query param ?editID=
            String editID = request.getParameter("editID");
            if(editID == null) {
                request.setAttribute("systemErrorMessages", Arrays.asList("Erreur système. Veuillez réessayer."));
                request.getRequestDispatcher("/roles/list").forward(request, response);
                return;
            }

            //obtenir le role
            RoleEntity role = isValidOrNull(editID);
            if(role == null) {
                request.setAttribute("systemErrorMessages", Arrays.asList("Erreur système. Veuillez réessayer."));
                request.getRequestDispatcher("/roles/list").forward(request, response);
                return;
            }
            //peupler la vue
            request.setAttribute("role", role);
            request.setAttribute("authorities", new AuthorityService(EMF.getEM()).findAllOrNull());
            request.getRequestDispatcher(EDIT_VIEW_PATH).forward(request, response);
            return;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/edit")) {
            editRole(request, response);
        }

    }

    private void editRole(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("User attempts to edit a role");

        // On récupère les paramètres du formulaire soumis
        String editID = request.getParameter("editID");
        String inputLabel = request.getParameter("label");
        String inputDescription = request.getParameter("description");
        String[] selectedAuthoritiesIds = request.getParameterValues("selected-authorities");

        log.debug("parameters on edit POST: "
                + editID
                + " " + inputLabel
                + " " + inputDescription
                + " " + Arrays.toString(selectedAuthoritiesIds));
        // On vérifie que les paramètres existent sur la requête, que l'editID est valide et pointe vers un role existant en DB
        if(editID == null
                || isValidOrNull(editID) == null
                || inputLabel == null
                || inputDescription == null
                || selectedAuthoritiesIds == null) {

            request.setAttribute("systemErrorMessages", Arrays.asList("Erreur système. Veuillez réessayer."));
            request.getRequestDispatcher(EDIT_VIEW_PATH).forward(request, response);
            return;
        }

        // On vérifie que les paramètres du formulaire forment un Role valide
        RoleModel model = new RoleModel(inputLabel, inputDescription, selectedAuthoritiesIds);
        RoleService roleService = new RoleService(EMF.getEM());
        RoleEntity roleToUpdate = model.getEntity_OrNullIfValidationFailed(roleService);
        if(roleToUpdate == null) {
            request.setAttribute("formErrorMessages", model.getAllErrorMessagesOrEmpty());
            request.getRequestDispatcher(EDIT_VIEW_PATH).forward(request, response);
            return;
        }

        // On met à jour le rôle dans la DB
        int roleId = Integer.parseInt(editID);
        roleToUpdate.setId(roleId);
        roleService.update(roleToUpdate);
        response.sendRedirect(SUCCESS_EDIT_VIEW_PATH);
    }

    private RoleEntity isValidOrNull(String editID) {
        // On vérifie que l'editID est valide et que le rôle existe déjà dans la DB
        if(isEntityId(editID)) {
            int roleId = Integer.parseInt(editID);
            return new RoleService(EMF.getEM()).findByIdOrNull(roleId);
        }
        return null;
    }




}
