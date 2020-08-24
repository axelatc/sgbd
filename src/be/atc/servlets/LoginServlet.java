package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.WorkerService;
import be.atc.models.UserModel;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletLogin",
        urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "auth/";
    public static final String LOGIN_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "login.jsp";

    private List<String> formErrorMessages;
    private List<String> systemErrorMessages;
    private List<String> systemSuccessMessages;

    @Override
    public void init() throws ServletException {
        super.init();
        this.formErrorMessages = new ArrayList<>();
        this.systemErrorMessages = new ArrayList<>();
        this.systemSuccessMessages = new ArrayList<>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User attempts to login");
        String forwardURL = "";
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/login")) {
            String inputUsername = request.getParameter("username");
            String inputPassword = request.getParameter("password");

            log.debug("Credentials inputted " + "username: " + inputUsername + " password: " + inputPassword);
            UserModel user = new UserModel(inputUsername, inputPassword);
            UserModel.UserValidator validator = user.new UserValidator();


            // Les données du formulaires sont-elles correctes?
            if (validator.isValid()) {
                EntityManager em = EMF.getEM();
                WorkerEntity worker = new WorkerService(em).findWorkerByUsernameOrNull(inputUsername);
                // L'utilisateur existe dans la DB
                if (worker != null) {
                    // Le mot de passe entré correspond
                    if (user.getPassword().equals(worker.getPassword())) {
                        request.getSession().setAttribute("authUser", worker);
                        log.debug("Authenticated user: " + worker.toString());
                        systemSuccessMessages.add("Succesful login!");
                        forwardURL = "/workers/list";

                    }
                    // Le mdp ne correspond pas
                    else {
                        systemErrorMessages.add("Votre mot de passe est incorrect");
                        forwardURL = LOGIN_VIEW_PATH;
                    }
                }
                // L'utilisateur n'existe pas dans la DB
                else {
                    systemErrorMessages.add("Votre nom d'utilisateur est introuvable.");
                    forwardURL = LOGIN_VIEW_PATH;
                }
            }
            // Données du formulaire incorrectes
            else {
                formErrorMessages.addAll(validator.getAllErrorMessagesOrEmpty());
                forwardURL = LOGIN_VIEW_PATH;
            }

        }


        request.setAttribute("formErrorMessages", formErrorMessages);
        request.setAttribute("systemSuccessMessages", systemSuccessMessages);
        request.setAttribute("systemErrorMessages", systemErrorMessages);
        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/login")) {
            log.debug("User requests the login page");
            request.setAttribute("pageTitle", "Login");
            log.debug("forwarded to JSP at path: " + LOGIN_VIEW_PATH);
            request.getRequestDispatcher(LOGIN_VIEW_PATH).forward(request, response);
        }

        if (requestURI.endsWith("/logout")) {

            HttpSession session = request.getSession();
            systemSuccessMessages.add("Vous avez été déconnecté.");
            session.removeAttribute("authUser");
            request.setAttribute("systemSuccessMessages", systemSuccessMessages);
            request.getRequestDispatcher(LOGIN_VIEW_PATH).forward(request, response);
        }
    }
}
