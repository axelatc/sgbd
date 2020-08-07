package be.atc.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ServletAtc")
public class ServletAtc extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/jpaDS");
            try {
                Connection conn = ds.getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        //        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaSGBD");
//        EntityManager em = emf.createEntityManager();
//        RolesEntity role = new RolesEntity();
//        System.out.println("avant peristence nouveau role");
//        //role.setId(5);
//        role.setLabel("Boss");
//        role.setDescr("Le patron de l'entreprise");
//        em.persist(role);
//        System.out.println("objet rôle persisté");

    }
}
