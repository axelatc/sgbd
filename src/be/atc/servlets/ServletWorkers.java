package be.atc.servlets;

import be.atc.AppConfig;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletWorkers", urlPatterns = "/workers/*")
public class ServletWorkers extends HttpServlet {
    private static final Logger log = Logger.getLogger(ServletWorkers.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "workers/";
    public static final  String CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "create.jsp";
    public static final  String LIST_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "list.jsp";
    public static final  String EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "edit.jsp";
    public static final  String DELETE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "delete.jsp";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String forwardURL = "";
        if (requestURI.endsWith("/create")){
            forwardURL = CREATE_VIEW_PATH;
        }
        if (requestURI.endsWith("/list")){
            forwardURL = LIST_VIEW_PATH;
        }
        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }
}
