package be.atc.test.servlets;

import be.atc.dataAccess.EMF;
import be.atc.dataAccess.entities.RolesEntity;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/ServletTestJpa")
public class ServletTestJpa extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EMF.getEM();
        RolesEntity role = new RolesEntity();

        //role.setId(5);
        role.setLabel("Boss");
        role.setDescr("Le patron de l'entreprise");
        System.out.println("avant persistence nouveau role");
        em.clear();
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.close();
        System.out.println("objet rôle persisté");
    }
}
