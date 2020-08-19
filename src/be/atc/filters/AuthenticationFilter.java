package be.atc.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;

        String incomingPath = request.getRequestURI().substring( request.getContextPath().length() );

        // All clients can access static files
        if (incomingPath.startsWith( "/resources" )) {
            chain.doFilter( request, response );
            return;
        }

        // Only authentified clients can access the app
        HttpSession session= request.getSession();
        if(session.getAttribute("currentUser") != null || incomingPath.equalsIgnoreCase("/login")) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }

}
