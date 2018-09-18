package es.uvigo.esei.dgss.exercises.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;

@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {

    @Inject
    private Facade facade;

    @Resource
    private UserTransaction transaction;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<h1>Facade tests</h1>");
        
        //task1
        writer.println("<form method='POST'>"
                + "<button type='submit' name='task' value='1'>Task 1. Create User"
                + "</button></form>");
        
        //task2
        writer.println("<form method='POST'>"
                + "<button type='submit' name='task' value='2'>Task 2. Create Friendship"
                + "</button></form>");

        writer.println("</body>");
        writer.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body>");
        if (req.getParameter("task").equals("1")) {
            task1(req, resp, writer);
        }
        if (req.getParameter("task").equals("2")) {
            task2(req, resp, writer);
        }
        writer.println("</body></html>");
    }

    private void task1(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) 
            throws IOException {
        // work with Facade

        try {
            transaction.begin();

            // Task 2.1
            User u = facade.addUser(UUID.randomUUID().toString(), "name", "password", new byte[] {});
            writer.println("User " + u.getLogin() + " created successfully<br><br>");

            writer.println("<a href='SimpleServlet'>Go to menu</a>");

            transaction.commit();

        } catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
                | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                transaction.rollback();
            } catch (IllegalStateException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SystemException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }
    
    private void task2(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) 
            throws IOException {
        // work with Facade

        try {
            transaction.begin();

            // Task 2.1
            UserFriendship uf = facade.addFriendship(new User(UUID.randomUUID().toString()), new User(UUID.randomUUID().toString()), new Date());
            writer.println("User frienship 1" + uf.getUser1().getLogin() + " created successfully<br>");
            writer.println("User frienship 2" + uf.getUser2().getLogin() + " created successfully<br>");
            writer.println("Date" + uf.getDate() + " created successfully<br><br>");

            writer.println("<a href='SimpleServlet'>Go to menu</a>");

            transaction.commit();

        } catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
                | HeuristicMixedException | HeuristicRollbackException e) {
            try {
                transaction.rollback();
            } catch (IllegalStateException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SystemException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }
}
