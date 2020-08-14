package be.atc.test.servlets;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.EntityFinderImpl;
import be.atc.dataAccess.entities.RolesEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/ServletTestJpa")
public class ServletTestJpa extends HttpServlet {

    private static Logger log = Logger.getLogger(ServletTestJpa.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EMF.getEM();
        RolesEntity role = new RolesEntity();

        //role.setId(5);
        role.setLabel("Boss");
        role.setDescr("Le patron de l'entreprise");
        log.debug("Before persisting new Role");

        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.clear();
        em.close();
        log.debug("New Role has been peristed successfully");
    }
}
