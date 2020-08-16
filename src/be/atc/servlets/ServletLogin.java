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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("User requests the login page");
        request.setAttribute("pageTitle", "Login");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
    }
}
