package be.atc.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletWorkers", urlPatterns = "/workers/*")
public class ServletWorkers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String viewsBaseDirectoryContextPath = getServletContext().getInitParameter("viewsBaseDir");
        String workersViewsBaseDir = "workers/";
        String forwardURL = viewsBaseDirectoryContextPath + workersViewsBaseDir;
        if (requestURI.endsWith("/create")){
            forwardURL += "create.jsp";
        }
        if (requestURI.endsWith("/list")){
            forwardURL += "list.jsp";
        }
        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }
}
