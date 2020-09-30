package be.atc.servlets;

import be.atc.AppConfig;
import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WeeklySchedulesEntity;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.WeeklySchedulesService;
import be.atc.dataAccess.services.WorkerService;
import be.atc.models.WeeklySchedulesModel;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@WebServlet(name = "WeeklySchedulesServlet", urlPatterns = "/schedules/*")
public class WeeklySchedulesServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(LoginServlet.class);
    private static final String THIS_VIEWS_ROOT_PATH = AppConfig.VIEWS_ROOT_PATH + "schedules/";
    public static final String CONSULT_SCHEDULES_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "consult.jsp";
    public static final String SELECT_SCHEDULES_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "select.jsp";
    public static final String LIST_SCHEDULES_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "list.jsp";
    public static final String EDIT_SCHEDULES_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "edit.jsp";
    public static final String SUCCESS_CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "ok.jsp";
    public static final String FAILLED_CREATE_VIEW_PATH = THIS_VIEWS_ROOT_PATH + "fail.jsp";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("User attemps to reach: " + requestURI);

        if (requestURI.endsWith("/consult")) {
            log.debug("User attemps to consult his schedule");
            // Get auth user session as worker Entity
            HttpSession session = request.getSession();
            WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");
            WeeklySchedulesService service = new WeeklySchedulesService(EMF.getEM());
            Collection<WeeklySchedulesEntity> actualSchedules=service.findActualWorkerSchedulesOrNull(authUser,LocalDate.now());

            request.setAttribute("actualSchedules", actualSchedules);
            request.getRequestDispatcher(CONSULT_SCHEDULES_VIEW_PATH).forward(request, response);
            return;
        }
        if (requestURI.endsWith("/select")) {
            log.debug("User attemps to select a worker");
            // Get workers to select
            WorkerService service = new WorkerService(EMF.getEM());
            Collection<WorkerEntity> allWorkers = service.findAllOrNull();

            request.setAttribute("allWorkers", allWorkers);
            request.getRequestDispatcher(SELECT_SCHEDULES_VIEW_PATH).forward(request, response);
            return;
        }
        if (requestURI.endsWith("/list")) {
            log.debug("User attemps to list worker schedule");
            int workerID = Integer.parseInt(request.getParameter("workerID"));

            WorkerService workerService = new WorkerService(EMF.getEM());
            WorkerEntity selectedWorker = workerService.findByIdOrNull(workerID);
            if (selectedWorker==null){
                request.setAttribute("systemErrorMessages", Arrays.asList("System ERROR."));
                request.getRequestDispatcher("/schedules/select").forward(request, response);
                return;
            }

            WeeklySchedulesService service = new WeeklySchedulesService(EMF.getEM());
            Collection<WeeklySchedulesEntity> actualSchedules=service.findActualWorkerSchedulesOrNull(selectedWorker,LocalDate.now());

            request.setAttribute("workerID",workerID);
            request.setAttribute("actualSchedules", actualSchedules);
            request.getRequestDispatcher(LIST_SCHEDULES_VIEW_PATH).forward(request, response);
            return;
        }
        if (requestURI.endsWith("/edit")) {
            log.debug("User attemps to edit a schedule");
            String workerID=request.getParameter("workerID");
            String scheduleID = request.getParameter("editID");
            String dayWeek = request.getParameter("dayWeek");

            request.setAttribute("scheduleID", scheduleID);
            request.setAttribute("dayWeek", dayWeek);
            request.setAttribute("workerID", workerID);
            request.getRequestDispatcher(EDIT_SCHEDULES_VIEW_PATH).forward(request, response);
            return;
        }



    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/edit")) {
            editSchedule(request, response);
        }

    }

    private void editSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("User attempts to edit a Schedule");

        // Get form parms
        String editID = request.getParameter("editID");
        int workerID = Integer.parseInt(request.getParameter("workerID"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        int dayWeek = Integer.parseInt(request.getParameter("dayWeek"));

        WeeklySchedulesModel model = new WeeklySchedulesModel(workerID,startDate,endDate,startTime,endTime,dayWeek);
        WeeklySchedulesService weeklySchedulesService = new WeeklySchedulesService(EMF.getEM());
        WeeklySchedulesEntity weeklySchedulesToCreate= model.getEntity_OrNullIfValidationFailed(weeklySchedulesService);
        if (weeklySchedulesToCreate == null) {
            request.setAttribute("formErrorMessages", model.getAllErrorMessagesOrEmpty());
            request.getRequestDispatcher(EDIT_SCHEDULES_VIEW_PATH).forward(request, response);
            return;
        }else{
            weeklySchedulesService.save(weeklySchedulesToCreate);
            response.sendRedirect(SUCCESS_CREATE_VIEW_PATH);
        }




    }

}
