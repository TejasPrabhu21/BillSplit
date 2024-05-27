package addGroups;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import fetchgroups.GroupDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddExpenseServlet
 */
@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public AddExpenseServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		PrintWriter out = response.getWriter();

		String expense_name = request.getParameter("expense_name");
		double amount = Double.parseDouble(request.getParameter("amount"));
		String group_id = request.getParameter("group_id");
		String uname = (String) session.getAttribute("name");
		String[] selectedMembers = request.getParameterValues("selected_members");
		int total_members = selectedMembers.length;

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit", "root", "student");
			PreparedStatement getUserId = con.prepareStatement("SELECT user_id FROM users WHERE USERNAME = ?");
			getUserId.setString(1, uname);
			ResultSet res = getUserId.executeQuery();
			res.next();
			int uid = res.getInt("user_id");

			PreparedStatement queryExpense = con.prepareStatement(
					"INSERT INTO expenses(expense_name, amount, group_id, paid_by) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			queryExpense.setString(1, expense_name);
			queryExpense.setDouble(2, amount);
			queryExpense.setString(3, group_id);
			queryExpense.setInt(4, uid);
			PreparedStatement queryUpdateAmt = con.prepareStatement(
					"UPDATE user_groups AS ug SET ug.total_amt = ( SELECT SUM(e.amount) FROM expenses AS e WHERE e.group_id = ug.group_id) WHERE ug.group_id = ?;");
			queryUpdateAmt.setString(1, group_id);

			int rowCount = queryExpense.executeUpdate();
//			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");

			if (rowCount > 0) {
				ResultSet generatedKeys = queryExpense.getGeneratedKeys();
				if (generatedKeys.next()) {
				    int expenseId = generatedKeys.getInt(1);
				    for (String memberId : selectedMembers) {
				        // Check if memberId is not equal to uid (user ID of the user who created the expense)
				        if (!memberId.equals(Integer.toString(uid))) {
				            PreparedStatement queryTransact = con.prepareStatement(
				                "INSERT INTO transactions(expense_id, user_id, share_amount) VALUES(?, ?, ?)");
				            queryTransact.setInt(1, expenseId);
				            queryTransact.setInt(2, Integer.parseInt(memberId));
				            Double shareAmt = amount / total_members;
				            queryTransact.setDouble(3, shareAmt);

				            int transactCount = queryTransact.executeUpdate();
				            if (transactCount > 0) {
				                request.setAttribute("status", "success");
				            } else {
				                request.setAttribute("status", "failed");
				            }
				        }
				    }
				}
				int updateRowCount = queryUpdateAmt.executeUpdate();
				if (updateRowCount > 0) {
					out.print("Table Updated");
				}

			} else {
				request.setAttribute("status", "failed");
			}
//			dispatcher.forward(request, response);
			response.sendRedirect(request.getContextPath() + "/group.jsp?groupId=" + group_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
