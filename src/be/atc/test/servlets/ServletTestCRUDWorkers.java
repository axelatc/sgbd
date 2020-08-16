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

@WebServlet(name = "ServletTestCRUDWorkers", urlPatterns = "/test/CRUDWorkers")
public class ServletTestCRUDWorkers extends HttpServlet {

    private static Logger log = Logger.getLogger(ServletTestJpa.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EMF.getEM();
        log.debug("Building named query Workers.findWorkerById");
        WorkersEntity w = em.createNamedQuery("Workers.findWorkerById", WorkersEntity.class)
                .setParameter("id", 1)
                .getSingleResult();
        log.debug(w);
    }
}
