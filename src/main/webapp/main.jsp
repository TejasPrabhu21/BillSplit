<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="fetchgroups.Group"%>
<%@ page import="java.io.PrintWriter"%>

<%
if (session.getAttribute("name") == null) {
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@24,400,1,0" />

<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/mainStyle.css">
<title>Split the bill</title>
</head>
<body>
	<%
	out.print(session);
	%>
	<nav class="sticky-top ">
		<figure class="logo">
			<img src="assets/images/bill-cash.png" alt="Logo image">
			<h1 class="caption">BillSplit</h1>
		</figure>

		<div class="dropdown d-flex align-items-center">
			<p class="caption fs-5"><%=session.getAttribute("name")%></p>
			<span class="material-symbols-rounded dropdown-toggle"
				data-bs-toggle="dropdown" aria-expanded="false">
				account_circle </span>
			<ul class="dropdown-menu">
				<li><a class="dropdown-item" href="FetchGroups">My Groups</a></li>
				<li><a class="dropdown-item" href="LogoutServlet">Log out</a></li>
			</ul>
		</div>
	</nav>

	<div class="container-sm min-w-50 p-5 groups-container">
		<h2 class="mx-auto">My Groups</h2>
		<!-- ArrayList of users sent from login servlet as parameter -->
		<%
		ArrayList<Group> userGroups = (ArrayList<Group>) request.getAttribute("userGroups");

		if (userGroups != null) {
			for (Group group : userGroups) {
		%>
		<a href="group.jsp?groupId=<%=group.getGroupId()%>"
			style="text-decoration: none;">
			<div class="card m-auto mb-2 p-3 group-card">
				<!-- Display group information dynamically -->

				<div class="d-flex justify-content-between align-items-center">
					<div class="group-item">
						<h4 class="card-title"><%=group.getGroupName()%></h4>
						<p class="card-text">
							created by
							<%=group.getCreatedBy()%>
						</p>
						<p class="card-text">
							on
							<%=group.getCreatedOn()%>
						</p>
						<span class="material-symbols-rounded"> groups </span> <span
							class="members"><%=group.getMemCount()%></span>
					</div>
					<div class="expense">
						<p>Total expenses</p>
						<p class="total-amt fw-bold">
							₹ <%=group.getTotalAmt()%>
						</p>
					</div>
				</div>
			</div>
		</a>
		<%
		}
		} else {
		%>
		<p>No groups found.</p>
		<%
		}
		%>
	</div>

	<!-- Create group button -->
	<div class="create-group">
		<span class="material-symbols-rounded" data-bs-toggle="modal"
			data-bs-target="#newGroup"> add_circle </span>
	</div>



	<div class="modal fade" id="newGroup" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Create a
						new Group</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form method="post" action="AddGroupServlet">
						<div class="mb-3">
							<label for="group-name" class="col-form-label">Add a
								group name:</label> <input type="text" class="form-control"
								id="recipient-name" name="group_name">
						</div>
						<button type="submit" class="btn btn-primary add-group-btn">Create
							Group</button>
					</form>
				</div>
				<div class="modal-footer justify-content-start">
					<p class="d-flex justify-content-start align-items-center">
						<span class="material-symbols-rounded position-relative top-50">
							info </span> Share the invite link to add members to group
					</p>
				</div>
			</div>
		</div>
	</div>

	<script src="Bootstrap/js/bootstrap.min.js"></script>
	<script src="Bootstrap/js/bootstrap.bundle.js"></script>

</body>
</html>