package registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uemail = request.getParameter("email");
		String upassword = request.getParameter("password");

		Connection con = null;
		HttpSession session = request.getSession();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit", "root", "student");
			PreparedStatement query = con.prepareStatement("SELECT * FROM users WHERE email= ? AND password = ?");
			query.setString(1, uemail);
			query.setString(2, upassword);
			RequestDispatcher dispatcher = null;

			ResultSet res = query.executeQuery();
			if (res.next()) {
				session.setAttribute("name", res.getString("username"));
				session.setAttribute("userId", res.getInt("user_id"));
//				dispatcher = request.getRequestDispatcher("");
				response.sendRedirect(request.getContextPath() + "/FetchGroups");
			} else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
