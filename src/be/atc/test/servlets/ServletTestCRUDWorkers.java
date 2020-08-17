package be.atc.test.servlets;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.RolesEntity;
import be.atc.dataAccess.entities.SexeType;
import be.atc.dataAccess.entities.WorkersEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        log.debug(w.toString());

        log.debug("Building named query Workers.findAllWorkers");
        List<WorkersEntity> workers = em.createNamedQuery("Workers.findAllWorkers", WorkersEntity.class)
                .getResultList();
        for (WorkersEntity worker : workers) {
            log.debug(worker);
        }

        log.debug("Building named query Workers.deleteWorkerById");
        em.getTransaction().begin();
        int numDeleted = em.createNamedQuery("Workers.deleteWorkerById", WorkersEntity.class)
                .setParameter("id", 2)
                .executeUpdate();
        em.getTransaction().commit();
        log.debug("nombre de workers deleted: " + numDeleted);

        log.debug("Building named query Workers.updateWorkerById");
        WorkersEntity sec = em.createNamedQuery("Workers.findWorkerById", WorkersEntity.class)
                .setParameter("id", 1)
                .getSingleResult();
        RolesEntity role = new RolesEntity();
        role.setId(1);

        em.getTransaction().begin();
        int numUpdated = em.createNamedQuery("Workers.updateWorkerById", WorkersEntity.class)
                .setParameter("id", 1)
                .setParameter("rolesId", role)
                .setParameter("teamsId", sec.getTeamsByTeamsId())
                .setParameter("birthdate", java.sql.Date.valueOf("2020-09-04"))
                .setParameter("firstName", "Alabella")
                .setParameter("isDeleted", true)
                .setParameter("lastName", "DiCaprio")
                .setParameter("login", "alabella.dc")
                .setParameter("passwordKey", "betterpass")
                .setParameter("sexe", SexeType.AUTRE)
                .executeUpdate();
        em.getTransaction().commit();
        log.debug("nombre de workers updated: " + numUpdated);


    }
}
