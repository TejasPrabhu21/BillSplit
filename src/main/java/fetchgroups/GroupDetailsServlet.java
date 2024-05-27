package fetchgroups;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GroupDetailsServlet")
public class GroupDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GroupDetailsServlet() {
		// GroupDetailsServlet Auto-generated constructor stub
	}

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Assuming you have a method in your DAO class to retrieve group details by group ID
        Group group = GroupDAO.getGroupDetails(groupId); // Replace 'YourDAO' and 'groupId' with actual DAO and group ID

        // Assuming you have a method in your DAO class to retrieve all group members by group ID
        List<String> groupMembers = GroupDAO.getGroupMembers(groupId); // Replace 'YourDAO' and 'groupId' with actual DAO and group ID

        // Set the retrieved data as request attributes
        request.setAttribute("dynamicGroupName", group.getGroupName());
        request.setAttribute("dynamicTotalExpenses", group.getTotalExpenses());
        request.setAttribute("dynamicGroupMembers", groupMembers);

        // Forward to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("yourGroupDetailsJSP.jsp");
        dispatcher.forward(request, response);
    }
}
