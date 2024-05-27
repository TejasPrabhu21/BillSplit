package addGroups;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddGroupServlet")
public class AddGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String gname = request.getParameter("group_name");
		String uname = (String) session.getAttribute("name");

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit", "root", "student");
			PreparedStatement getUserId = con.prepareStatement("SELECT user_id FROM users WHERE USERNAME = ?");
			getUserId.setString(1, uname);
			ResultSet res = getUserId.executeQuery();
			res.next();
			int uid = res.getInt("user_id");

			PreparedStatement query = con
					.prepareStatement("INSERT INTO user_groups(group_name, created_by, total_amt) VALUES (?, ?, 0.00)",Statement.RETURN_GENERATED_KEYS);
			query.setString(1, gname);
			query.setInt(2, uid);

			int rowCount = query.executeUpdate();
//			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");

			if (rowCount > 0) {
				ResultSet generatedKeys = query.getGeneratedKeys();
				if (generatedKeys.next()) {
					int groupId = generatedKeys.getInt(1);

					// Generate a unique link for the group
					String shareLink = generateShareLink(groupId);

					// Associate the share link with the group in the database
					PreparedStatement updateLink = con.prepareStatement("UPDATE user_groups SET share_link = ? WHERE group_id = ?");
					updateLink.setString(1, shareLink);
					updateLink.setInt(2, groupId);
					updateLink.executeUpdate();
					
					PreparedStatement addMember = con.prepareStatement("INSERT INTO group_members(user_id, group_id) VALUES(?, ?)");
					addMember.setInt(1, uid);
					addMember.setInt(2, groupId);
					addMember.executeUpdate();

					request.setAttribute("status", "success");
				}
			} else {
				request.setAttribute("status", "failed");
			}
//			dispatcher.forward(request, response);
			response.sendRedirect(request.getContextPath() + "/FetchGroups");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String generateShareLink(int groupId) {
		// You can customize this method to generate a unique link
		// For simplicity, using UUID in this example
		return "http://localhost:8080/BillSharingApp/group.jsp?groupId=" + groupId;
	}
}
