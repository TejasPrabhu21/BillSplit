package registration;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		
//		out.print("Registered");
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upassword = request.getParameter("pass");
		String uphone = request.getParameter("contact");
		
//		out.print(uname);
//		out.print(uemail);
//		out.print(upassword);
//		out.print(uphone);
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsplit","root","student");
			PreparedStatement regQuery = con.prepareStatement("INSERT INTO USERS(username, email, password, phone) VALUES(?, ?, ?, ?)");
			regQuery.setString(1, uname);
			regQuery.setString(2, uemail);
			regQuery.setString(3, upassword);
			regQuery.setString(4, uphone);
			
			int rowCount = regQuery.executeUpdate();
			dispatcher = request.getRequestDispatcher("login.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
