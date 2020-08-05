package be.atc.servlet;

import entities.RolesEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ServletAtc")
public class ServletAtc extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaSGBD");
        EntityManager em = emf.createEntityManager();
        RolesEntity role = new RolesEntity();
        System.out.println("avant peristence nouveau role");
        //role.setId(5);
        role.setLabel("Boss");
        role.setDescr("Le patron de l'entreprise");
        em.persist(role);
        System.out.println("objet rôle persisté");

    }
}
