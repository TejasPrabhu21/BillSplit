package payment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import fetchgroups.GroupDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ArchiveExpenses
 */
@WebServlet("/ArchiveExpenses")
public class ArchiveExpenses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ArchiveExpenses() {
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
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			int transactionId = Integer.parseInt(request.getParameter("transactionId"));
			int expenseId = Integer.parseInt(request.getParameter("expenseId"));

			GroupDAO groupDAO = new GroupDAO();
			boolean success = groupDAO.archiveTransactionAndExpense(transactionId, expenseId);

			if (success) {
				out.print("{\"status\": \"success\"}");
			} else {
				out.print("{\"status\": \"failure\"}");
			}
		} catch (Exception e) {
			out.print("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
		}
//        response.setContentType("application/json");
//        response.getWriter().write("{\"status\":\"success\"}");
	}

}
