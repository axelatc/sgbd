/*
package be.atc.filters;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WorkerEntity;
import be.atc.dataAccess.services.WorkerService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // authentified user is reloaded from database on every request
        // to keep its roles and permissions updated
        HttpSession session = request.getSession();
        WorkerEntity authUser = (WorkerEntity) session.getAttribute("authUser");

        if (authUser != null) {
            log.debug("auth user: " + authUser.toString());
            WorkerService service = new WorkerService(EMF.getEM());
            WorkerEntity freshUser = service.findByIdOrNull(authUser.getId());
            session.removeAttribute("authUser");
            session.setAttribute("authUser", freshUser);
            log.debug("fresh user reloaded for authorizations" + freshUser.toString());
        }
        chain.doFilter(req, resp);

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
*/
