package es.uvigo.esei.dgss.exercises.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.EJB;
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

import es.uvigo.esei.dgss.exercise.service.PostEJB;
import es.uvigo.esei.dgss.exercise.service.UserEJB;
import es.uvigo.esei.dgss.exercises.domain.Link;
import es.uvigo.esei.dgss.exercises.domain.Photo;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;
import es.uvigo.esei.dgss.exercises.domain.Video;

@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {

	@Inject
	private Facade facade;

	@Resource
	private UserTransaction transaction;

	@EJB
	private UserEJB userEJB;

	@EJB
	private PostEJB postEJB;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();

		writer.println("<html>");
		writer.println("<body>");
		writer.println("<h1>Facade tests</h1>");

		// task2_1
		writer.println("<form method='POST'>" + "<button type='submit' name='task' value='2_1'>Task 2_1. Create user"
				+ "</button></form>");

		// task2_1_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_1_EJB'>Task 2_1_EJB. Create user with EJB"
				+ "</button></form>");

		// task2_2
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_2'>Task 2_2. Create friendship" + "</button></form>");

		// task2_2_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_2_EJB'>Task 2_2_EJB. Create friendship with EJB"
				+ "</button></form>");

		// task2_3
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_3'>Task 2_3. Get friendships" + "</button></form>");

		// task2_3_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_3_EJB'>Task 2_3_EJB. Get friendships with EJB"
				+ "</button></form>");

		// task2_4
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_4'>Task 2_4. Get posts of friends" + "</button></form>");

		// task2_4_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_4_EJB'>Task 2_4_EJB. Get posts of friends with EJB"
				+ "</button></form>");

		// task2_5
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_5'>Task 2_5. Get commented posts of friends after a given date"
				+ "</button></form>");

		// task2_5_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_5_EJB'>Task 2_5_EJB. Get commented posts of friends after a given date with EJB"
				+ "</button></form>");

		// task2_6
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_6'>Task 2_6. Get the users which are friends of a given user who like a given post"
				+ "</button></form>");

		// task2_6_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_6_EJB'>Task 2_6_EJB. Get the users which are friends of a given user who like a given post with EJB"
				+ "</button></form>");

		// task2_7
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_7'>Task 2_7. Get pictures that user likes"
				+ "</button></form>");

		// task2_7_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_7_EJB'>Task 2_7_EJB. Get pictures that user likes with EJB"
				+ "</button></form>");

		// task2_8
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_8'>Task 2_8. Get potential friends" + "</button></form>");

		// task2_8_EJB
		writer.println("<form method='POST'>"
				+ "<button type='submit' name='task' value='2_8_EJB'>Task 2_8_EJB. Get potential friends with EJB"
				+ "</button></form>");

		writer.println("</body>");
		writer.println("</html>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.println("<html><body>");
		if (req.getParameter("task").equals("2_1")) {
			task2_1(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_1_EJB")) {
			task2_1_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_2")) {
			task2_2(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_2_EJB")) {
			task2_2_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_3")) {
			task2_3(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_3_EJB")) {
			task2_3_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_4")) {
			task2_4(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_4_EJB")) {
			task2_4_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_5")) {
			task2_5(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_5_EJB")) {
			task2_5_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_6")) {
			task2_6(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_6_EJB")) {
			task2_6_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_7")) {
			task2_7(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_7_EJB")) {
			task2_7_EJB(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_8")) {
			task2_8(req, resp, writer);
		}
		if (req.getParameter("task").equals("2_8_EJB")) {
			task2_8_EJB(req, resp, writer);
		}
		writer.println("</body></html>");
	}

	private void task2_1(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.1
			User u = facade.addUser(UUID.randomUUID().toString(), "task2_1", "password", new byte[] {});

			transaction.commit();

			writer.println("User " + u.getLogin() + " created successfully<br><br>");

			writer.println("<a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_1_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade
		// Task 2.1.EJB
		User u = new User(UUID.randomUUID().toString());
		u.setName("task2_1_EJB");
		u.setPassword("password");
		u.setPicture(new byte[] {});
		userEJB.createUser(u);
		writer.println("User " + u.getLogin() + " created successfully with EJB<br><br>");

		writer.println("<a href='SimpleServlet'>Go to menu</a>");

	}

	private void task2_2(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.2
			UserFriendship uf = facade.addFriendship(new User(UUID.randomUUID().toString()),
					new User(UUID.randomUUID().toString()));

			transaction.commit();

			writer.println("User frienship 1" + uf.getUser1().getLogin() + " created successfully<br>");
			writer.println("User frienship 2" + uf.getUser2().getLogin() + " created successfully<br>");
			writer.println("Date" + uf.getDate() + " created successfully<br><br>");

			writer.println("<a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_2_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade
		try {
			transaction.begin();
			// Task 2.2.EJB
			User u1 = new User(UUID.randomUUID().toString());
			u1.setName("task2_2_EJB-1");
			u1.setPassword("password");
			u1.setPicture(new byte[] {});
			userEJB.createUser(u1);

			User u2 = new User(UUID.randomUUID().toString());
			u2.setName("task2_2_EJB-2");
			u2.setPassword("password");
			u2.setPicture(new byte[] {});
			userEJB.createUser(u2);

			UserFriendship uf = userEJB.createFriendship(u1, u2);
			writer.println("User frienship 1" + uf.getUser1().getLogin() + " created successfully<br>");
			writer.println("User frienship 2" + uf.getUser2().getLogin() + " created successfully<br>");
			writer.println("Date" + uf.getDate() + " created successfully<br><br>");

			writer.println("<a href='SimpleServlet'>Go to menu</a>");
			transaction.commit();
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void task2_3(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();
			// Task 2.3

			User u = new User(UUID.randomUUID().toString());

			List<User> friends = facade.getFriendships(u);

			transaction.commit();

			writer.println("Friends of user " + u.getLogin() + ":<br><br>");
			for (User friend : friends) {
				writer.println(friend.getLogin() + "<br>");
			}

			writer.println("<br><a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_3_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade
		// Task 2.3.EJB
		try {
			transaction.begin();
			User u = userEJB.createUser(new User(UUID.randomUUID().toString()));
			userEJB.createFriendship(u, new User(UUID.randomUUID().toString()));
			userEJB.createFriendship(u, new User(UUID.randomUUID().toString()));

			List<User> friends = userEJB.getFriends(u);

			writer.println("Friends of user " + u.getLogin() + ":<br><br>");
			for (User friend : friends) {
				writer.println(friend.getLogin() + "<br>");
			}

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

	private void task2_4(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();
			// Task 2.4
			User u = new User(UUID.randomUUID().toString());

			List<Post> posts = facade.getFriendsPosts(u);

			transaction.commit();

			writer.println("Friends posts of user " + u.getLogin() + ":<br><br>");
			for (Post post : posts) {
				writer.println(post.getId() + "<br>");
			}

			writer.println("<br><a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_4_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade
		try {
			transaction.begin();
			// Task 2.4.EJB
			
			//Creating example users
			User u = userEJB.createUser(new User(UUID.randomUUID().toString()));
			User prueba1 = userEJB.createUser(new User(UUID.randomUUID().toString()));
			User prueba2 = userEJB.createUser(new User(UUID.randomUUID().toString()));
			User prueba3 = userEJB.createUser(new User(UUID.randomUUID().toString()));
			userEJB.createFriendship(u, prueba1);
			userEJB.createFriendship(u, prueba2);
			
			//Creating example posts
			Link post1 = new Link();
			post1.setUser(prueba1);
			postEJB.createLink(post1);
			Video post2 = new Video();
			post2.setUser(prueba1);
			postEJB.createVideo(post2);
			Photo post3 = new Photo();
			post3.setUser(prueba2);
			postEJB.createPhoto(post3);
			Link post4 = new Link();
			post4.setUser(prueba3);
			postEJB.createLink(post4);
			Video post5 = new Video();
			post5.setUser(prueba3);
			postEJB.createVideo(post5);
			
			List<Post> posts = new ArrayList<>();
			List<User> friends = userEJB.getFriends(u);
			
			for(User friend : friends){
				posts.addAll(postEJB.getPostsOfUser(friend));
			}
			
			writer.println("Friends posts of user " + u.getLogin() + ":<br><br>");
			for (Post post : posts) {
				writer.println(post.getId() + "<br>");
			}

			writer.println("<br><a href='SimpleServlet'>Go to menu</a>");
			
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

	private void task2_5(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.5
			Date date = new Date();
			User u = new User(UUID.randomUUID().toString());

			List<Post> posts = facade.getCommentedPostsByFriends(u, date);

			transaction.commit();
			
			writer.println("Posts commented by friends of " + u.getLogin() + " after a given date:<br><br>");
			for (Post post : posts) {
				writer.println(post.getId() + "<br>");
			}

			writer.println("<br><a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_5_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		// Task 2.5.EJB
		// TODO

		writer.println("<a href='SimpleServlet'>Go to menu</a>");

	}

	private void task2_6(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.6
			User u = new User(UUID.randomUUID().toString());
			Post p = new Video();

			List<User> users = facade.unnamed(u, p);

			transaction.commit();
			
			writer.println("Get the users which are friends of " + u.getLogin() + " who like " + p.getId() + ":<br><br>");
			for (User user : users) {
				writer.println(user.getLogin() + "<br>");
			}

			writer.println("<br><a href='SimpleServlet'>Go to menu</a>");

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

	private void task2_6_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.6.EJB
			// TODO

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

	private void task2_7(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.7
			// TODO

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

	private void task2_7_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.7.EJB
			// TODO

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

	private void task2_8(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.8
			// TODO

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

	private void task2_8_EJB(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) throws IOException {
		// work with Facade

		try {
			transaction.begin();

			// Task 2.8.EJB
			// TODO

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
