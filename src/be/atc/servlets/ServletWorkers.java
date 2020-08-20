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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletWorkers", urlPatterns = "/workers/*")
public class ServletWorkers extends HttpServlet {
    private static final Logger log = Logger.getLogger(ServletWorkers.class);
    private static final Map<String, String> VIEWS_PATHS;
    static {
        Map<String, String> temp = new HashMap<String, String>();
        String thisViewsRootPath = AppConfig.VIEWS_ROOT_PATH + "workers/";
        temp.put("list", thisViewsRootPath + "list.jsp");
        temp.put("create", thisViewsRootPath + "create.jsp");
        VIEWS_PATHS = Collections.unmodifiableMap(temp);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String forwardURL = "";
        if (requestURI.endsWith("/create")){
            forwardURL = VIEWS_PATHS.get("create");
        }
        if (requestURI.endsWith("/list")){
            forwardURL = VIEWS_PATHS.get("list");
        }
        getServletContext()
                .getRequestDispatcher(forwardURL)
                .forward(request, response);
    }
}
