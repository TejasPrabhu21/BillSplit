package fetchgroups;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class FetchGroups
 */
@WebServlet("/FetchGroups")
public class FetchGroups extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FetchGroups() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		HttpSession session = request.getSession();
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit", "root", "student");

			// Get current users groups from group_member table
			PreparedStatement query = con.prepareStatement("SELECT * FROM group_members WHERE user_id = ?");
			if(session.getAttribute("userId") == null) {
				response.sendRedirect("login.jsp");
			}
			int uid = (int) session.getAttribute("userId");
			query.setInt(1, uid);
			ResultSet res = query.executeQuery();

			ArrayList<Group> groupsList = new ArrayList<>();

			while (res.next()) {
				int groupId = res.getInt("group_id");

				// Fetch group details from user_groups table
				PreparedStatement groupQuery = con.prepareStatement("SELECT * FROM user_groups WHERE group_id = ?");
				groupQuery.setInt(1, groupId);
				ResultSet groupRes = groupQuery.executeQuery();

				PreparedStatement countQuery = con.prepareStatement("SELECT COUNT(*) as count FROM group_members WHERE group_id = ?");
				countQuery.setInt(1, groupId);
				ResultSet countRes = countQuery.executeQuery();


				if (groupRes.next() && countRes.next()) { 
					// Create a Group object and initialize it with group details
					Group group = new Group();
					group.setGroupId(groupId);
					group.setGroupName(groupRes.getString("group_name"));
					group.setCreatedBy(groupRes.getInt("created_by"));
					group.setLink(groupRes.getString("share_link"));
					group.setCreatedOn(groupRes.getString("created_on"));
			        group.setMemCount(countRes.getInt("count"));
			        group.setTotalAmt(groupRes.getInt("total_amt"));

					// Add the Group object to the list
					groupsList.add(group);
				}
			}

//			PrintWriter out = response.getWriter();
//			if (groupsList != null) {
//				for (Group group : groupsList) {
//
//					out.print(group.getGroupName() + "\n");
//				}
//			}

			// Add the list of groups to the request attribute
			request.setAttribute("userGroups", groupsList);

			// Forward the request to the main.jsp page
			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
