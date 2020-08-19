package be.atc.servlets;

import be.atc.test.servlets.ServletTestLogin;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ServletLogin",
            urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
    private static Logger log = Logger.getLogger(ServletTestLogin.class);
    private String viewsBaseDir;

    @Override
    public void init() throws ServletException {
        this.viewsBaseDir = getServletContext().getInitParameter("viewsBaseDir") + "auth/";
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User requests the login page");
        request.setAttribute("pageTitle", "Login");
        String pathToLoginJsp = this.viewsBaseDir + "login.jsp";
        log.debug("forwarded to JSP at path: " + pathToLoginJsp);
        request.getRequestDispatcher(pathToLoginJsp).forward(request,response);
    }
}
