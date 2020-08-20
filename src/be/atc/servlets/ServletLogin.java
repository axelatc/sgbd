package be.atc.servlets;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WorkersEntity;
import be.atc.dataAccess.services.WorkersService;
import be.atc.models.UserModel;
import be.atc.test.servlets.ServletTestLogin;
import be.atc.utils.AppConfig;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name="ServletLogin",
            urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
    private static final Logger log = Logger.getLogger(ServletLogin.class);
    private static final Map<String, String> VIEWS_PATHS;
    static {
        Map<String, String> temp = new HashMap<String, String>();
        String thisViewsRootPath = AppConfig.VIEWS_ROOT_PATH + "auth/";
        temp.put("login", thisViewsRootPath + "login.jsp");
        temp.put("logout", thisViewsRootPath + "logout.jsp");
        VIEWS_PATHS = Collections.unmodifiableMap(temp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User attempts to login");
        Collection<String> errorMessages = new ArrayList<String>();
        String forwardURL = "";

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/login")) {
            String inputUsername = request.getParameter("username");
            String inputPassword = request.getParameter("password");
            UserModel user = new UserModel(inputUsername, inputPassword);

            log.debug("Credentials inputted " + "username: " + inputUsername + " password: " + inputPassword);
            // Les données du formulaires sont-elles correctes?
            if (user.loginIsValid()
                    && user.passwordIsValid()) {
                EntityManager em = EMF.getEM();
                WorkersEntity worker = new WorkersService(em).findWorkerByUsernameOrNull(inputUsername);
                // L'utilisateur existe dans la DB
                if (worker != null) {
                    // Le mot de passe entré est correct
                    if (inputPassword.equals(worker.getPasswordKey())) {
                        request.getSession().setAttribute("authUser", worker);
                        log.debug("Authenticated user: " + worker.toString());
                        forwardURL = "/home";

                    }
                    // Le mdp est incorrect
                    else {
                        errorMessages.add("Veuillez réessayer.");
                        forwardURL = VIEWS_PATHS.get("login");
                    }
                }
                // L'utilisateur n'existe pas dans la DB
                else {
                    errorMessages.add("Votre nom d'utilisateur est introuvable.");
                    forwardURL = VIEWS_PATHS.get("login");
                }
            }
            // Données du formulaire incorrectes
            else {
                errorMessages.add("Veuillez entrer un nom d'utilisateur et un mot de passe valides.");
                forwardURL = VIEWS_PATHS.get("login");
            }
        }

        StringBuilder errorMessagesBuilder = new StringBuilder();
        for(String s : errorMessages) {
            errorMessagesBuilder.append(s);
        }
        request.setAttribute("errors", errorMessagesBuilder.toString());

        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User requests the login page");
        request.setAttribute("pageTitle", "Login");
        log.debug("forwarded to JSP at path: " + VIEWS_PATHS.get("login"));
        request.getRequestDispatcher(VIEWS_PATHS.get("login")).forward(request, response);
    }
}
