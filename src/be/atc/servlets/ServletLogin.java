package be.atc.servlets;

import be.atc.test.servlets.ServletTestLogin;
import be.atc.utils.AppConfig;
import org.apache.log4j.Logger;

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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User requests the login page");
        request.setAttribute("pageTitle", "Login");
        log.debug("forwarded to JSP at path: " + VIEWS_PATHS.get("login"));
        request.getRequestDispatcher(VIEWS_PATHS.get("login")).forward(request, response);
    }
}
