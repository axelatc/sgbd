package be.atc.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")

public class AuthenticationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        log.debug("request URI: " + request.getRequestURI());
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";

        boolean loggedIn = session != null && session.getAttribute("authUser") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        log.debug("user is logged in: " + loggedIn);
        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }

        // All clients can access static files
        String incomingPath = request.getRequestURI().substring(request.getContextPath().length());
        if (incomingPath.startsWith("/resources")) {
            chain.doFilter(request, response);
            return;
        }



    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
