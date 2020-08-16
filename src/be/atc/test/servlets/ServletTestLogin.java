package be.atc.test.servlets;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.WorkersEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletTestLogin", urlPatterns = "/ServletTestLogin")
public class ServletTestLogin extends HttpServlet {

    private static Logger log = Logger.getLogger(ServletTestLogin.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Start testing of named query Workers.findWorkerByLogin");
        EntityManager em = EMF.getEM();
        log.debug("Build named query Workers.findWorkerByLogin");
        WorkersEntity w = em
                .createNamedQuery("Workers.findWorkerByLogin", WorkersEntity.class)
                .setParameter("login", "jessica.sd")
                .getSingleResult();
        log.debug("Result Worker:" + " login: " + w.getLogin() +
                    ", password: " + w.getPasswordKey());

    }

}
