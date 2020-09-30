package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.ClockingEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.ClockingService;
import be.atc.models.ClockingModel;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;


@WebServlet(name = "ClockingServlet", urlPatterns = "/clockings/*")
public class ClockingServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "clockings/";
    public static final String CLOCKING_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "clocking.jsp";
    public static final String SUCCESS_CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "ok.jsp";
    public static final String FAILLED_CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "fail.jsp";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("User attemps to reach: " + requestURI);
        if (requestURI.endsWith("/clocking")) {

            log.debug("Search if uncompleted clocking exist for the user");

            // Get auth user session as worker Entity
            HttpSession session = request.getSession();
            WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");

            ClockingService service = new ClockingService(EMF.getEM());
            ClockingEntity clockingEntity = service.findUncompletedOrNull(authUser);

            if (clockingEntity == null) {
                log.debug("No uncompleted clocking found");
                request.getRequestDispatcher(CLOCKING_VIEW_PATH).forward(request, response);
            } else {
                log.debug("Uncompleted clocking found");
                request.setAttribute("uncompletedClocking", clockingEntity);
                request.getRequestDispatcher(CLOCKING_VIEW_PATH).forward(request, response);
            }

        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/clockin")) {
            addClocking(request, response);
        } else if (requestURI.endsWith("/clockout")) {
            updateClocking(request, response);
        }


    }

    private void addClocking(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get auth user session as worker Entity
        HttpSession session = request.getSession();
        WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");
        LocalDateTime actualtime = ClockingModel.getActualDatetimeAsLocalDateTime();


        log.debug("User add clock-in");
        ClockingModel model = new ClockingModel(authUser, actualtime);
        ClockingService clockingservice = new ClockingService(EMF.getEM());
        ClockingEntity clockingToCreate = model.getEntity_OrNullIfValidationFailed(clockingservice);
        if (clockingToCreate == null) {
            response.sendRedirect(FAILLED_CREATE_VIEW_PATH);
        } else {
            clockingservice.save(clockingToCreate);
            response.sendRedirect(SUCCESS_CREATE_VIEW_PATH);
        }


    }

    private void updateClocking(HttpServletRequest request, HttpServletResponse response) throws IOException {


        LocalDateTime actualtime = ClockingModel.getActualDatetimeAsLocalDateTime();
        // Get form parameter id
        int clockingID = Integer.parseInt(request.getParameter("clockingID"));

        log.debug("User add clock-out");

        ClockingService service = new ClockingService(EMF.getEM());
        if (clockingID == 0) {
            response.sendRedirect(FAILLED_CREATE_VIEW_PATH);
        } else {
            service.update(clockingID, actualtime);
            response.sendRedirect(SUCCESS_CREATE_VIEW_PATH);
        }


    }


}

