package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.RoleService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "RolesServlet", urlPatterns = "/roles/*")
public class RolesServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RolesServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "roles/";
    public static final String CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "create.jsp";
    public static final String LIST_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "list.jsp";
    public static final String EDIT_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "edit.jsp";
    public static final String DELETE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "delete.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String forwardURL = "";
        if (requestURI.endsWith("/create")) {

            forwardURL = CREATE_VIEW_PATH;
        }
        if (requestURI.endsWith("/list")) {
            RoleService service = new RoleService(EMF.getEM());
            Collection<RoleEntity> allRoles = service.findAllOrNull();

            // On retire le rôle de l'utilisateur authentifié
            HttpSession session = request.getSession();
            WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");
            RoleEntity authUserRole = authUser.getRole();
            allRoles.remove(authUserRole);

            request.setAttribute("roles", allRoles);
            forwardURL = LIST_VIEW_PATH;
        }

        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }
}
