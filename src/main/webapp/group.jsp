<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="fetchgroups.Group"%>
<%@ page import="fetchgroups.GroupDAO"%>
<%@ page import="fetchgroups.Transaction"%>
<%@ page import="fetchgroups.Expense"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%
if (session.getAttribute("userId") == null) {
	response.sendRedirect("login.jsp");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@24,400,1,0" />
<link rel="stylesheet" href="styles/bootstrap.min.css">
<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/groupStyle.css">
<title>Split the bill</title>
</head>

<body>
	<%
	// Fetch group details using a Java class (GroupDetailsFetcher)
	String groupId = request.getParameter("groupId");
	int userId = (int) session.getAttribute("userId");

	GroupDAO groupDetailsFetcher = new GroupDAO();
	Group groupDetails = groupDetailsFetcher.getGroupDetails(groupId);
	List<Entry<Integer, String>> membersList = groupDetailsFetcher.getGroupMembers(groupId);
	List<Transaction> allTransactions = groupDetailsFetcher.getAllTransactions(groupId);
	List<Expense> allArchivedExpenses = groupDetailsFetcher.getAllArchivedExpenses(groupId);
	if (session.getAttribute("groupId") == null) {
		session.setAttribute("groupId", groupId);
	}

	Map<Integer, String> membersMap = new HashMap<>();

	// Iterate through the list and add each entry to the map
	for (Entry<Integer, String> entry : membersList) {
		membersMap.put(entry.getKey(), entry.getValue());
	}

	int currentUser = (int) session.getAttribute("userId");
	String currentUserName = (String) session.getAttribute("name");
	%>

	<nav class="sticky-top">
		<figure class="logo">
			<img src="assets/images/bill-cash.png" alt="Logo image">
			<h1 class="caption">BillSplit</h1>
		</figure>

		<div class="dropdown">
			<span class="material-symbols-rounded dropdown-toggle"
				data-bs-toggle="dropdown" aria-expanded="false">
				account_circle </span>

			<ul class="dropdown-menu">
				<li><a class="dropdown-item" href="/BillSharingApp/FetchGroups">My
						Groups</a></li>
				<li><a class="dropdown-item" href="index.jsp">Log out</a></li>
			</ul>
		</div>
	</nav>

	<div class="title  w-90">
		<div class="container-fluid">
			<span class="navbar-brand mb-0 h1"><%=groupDetails.getGroupName()%></span>
		</div>

		<form class="d-flex">
			<button class="group-details-btn btn btn-outline-success me-2"
				data-bs-toggle="modal" data-bs-target="#detailsModal" type="button">Group
				Details</button>
		</form>
	</div>
	<div class="container main-content">

		<div class="nav tabs nav-pills " role="tablist">
			<button class="nav-link active " id="nav-activity"
				data-bs-toggle="tab" data-bs-target="#activity"
				aria-controls="nav-activity" role="pill">Activity</button>
			<button class="nav-link  " id="nav-summary" data-bs-toggle="tab"
				data-bs-target="#summary" aria-controls="nav-summary" role="pill">Summary</button>
		</div>
		<!-- TAB CONTENTS -->
		<div class="tab-content w-50-lg w-100-sm" id="nav-tabContent">
			<div class="tab-pane fade show active p-3" id="activity"
				role="tabpanel">
				<!-- OVERVIEW TABLE -->

				<div class="container body-light bg-dark overview p-3 rounded-3">
					<div class="container  text-center ">
						<div class="row g-2">
							<div class="col col-first">
								<div class="p-3">Total Expenses</div>
								<p class="total-amt fw-bold">
									₹
									<%=groupDetailsFetcher.getTotalExpenses(groupId)%></p>
							</div>

							<div class="col">
								<div class="p-3">Your Expense</div>
								<p class="total-amt fw-bold">
									₹
									<%=groupDetailsFetcher.getYourExpense(groupId, userId)%></p>
							</div>
						</div>
						<hr class="mx-3" />
						<div class="row g-2">
							<div class="col col-first">
								<div class="p-3">You owe others</div>
								<p class="total-amt fw-bold">
									₹
									<%=groupDetailsFetcher.getYouOweOthers(groupId, userId)%></p>
							</div>
							<div class="col">
								<div class="p-3">Others owe you</div>
								<p class="total-amt fw-bold">
									₹
									<%=groupDetailsFetcher.getOthersOweYou(groupId, userId)%></p>
							</div>
						</div>
					</div>
				</div>

				<!-- TRANSACTIONS -->
				<!-- TRANSACTION -->
				<%-- <%
				for (Transaction transaction : allTransactions) {
					String sender = membersMap.get(Integer.parseInt(transaction.getSender()));
					int senderId = Integer.parseInt(transaction.getSender());
					String receiver = membersMap.get(Integer.parseInt(transaction.getRecipient()));
					int receiverId = Integer.parseInt(transaction.getRecipient());
					double amount = transaction.getAmount();
				%>
				<div class="card m-auto mb-2 p-3 group-card">
					<div class="d-flex justify-content-between align-items-center">
						<div class="transaction-from col-4">
							<p><%=sender%></p>
							<p class="total-amt fw-bold">
								₹
								<%=amount%></p>
						</div>
						<div class="transaction-icon col-4">
							<span class="material-symbols-rounded d-block text-center p-2">arrow_forward</span>
							<form action="settleExpense.jsp" method="post">
								<input type="hidden" name="sender" value="<%=sender%>">
								<input type="hidden" name="senderId" value="<%=senderId%>">
								<input type="hidden" name="amount" value="<%=amount%>">
								<input type="hidden" name="recipient" value="<%=receiver%>">
								<input type="hidden" name="recipientId" value="<%=receiverId%>">
								Disable the button if the sender is the current user
								<button type="submit"
									class="btn btn-success<%=sender.equals(currentUserName) ? "" : " hidden"%>">SETTLE</button>
							</form>
						</div>
						<div class="transaction-to col-4">
							<p><%=receiver%></p>
						</div>
					</div>
				</div>
				<%
				}
				%> --%>
				<div class="container">
					<h5 class="my-4">Transactions</h5>
					<!-- Bootstrap Tabs -->
					<ul class="nav nav-tabs" id="transactionTabs" role="tablist">
						<li class="nav-item" role="presentation"><a
							class="nav-link active te" id="my-transactions-tab"
							data-bs-toggle="tab" href="#my-transactions" role="tab"
							aria-controls="my-transactions" aria-selected="true">My
								Transactions</a></li>
						<li class="nav-item" role="presentation"><a class="nav-link "
							id="my-transactions-tab" data-bs-toggle="tab"
							href="#all-transactions" role="tab"
							aria-controls="all-transactions" aria-selected="false">All
								Transactions</a></li>
					</ul>
					<div class="tab-content" id="transactionTabsContent">
						<!-- My Transactions Tab -->
						<div class="tab-pane fade show active" id="my-transactions"
							role="tabpanel" aria-labelledby="my-transactions-tab">
							<%
							for (Transaction transaction : allTransactions) {
								int transactionId = transaction.getTransactionId();
								int expenseId = transaction.getExpenseId();
								String sender = membersMap.get(Integer.parseInt(transaction.getSender()));
								int senderId = Integer.parseInt(transaction.getSender());
								String receiver = membersMap.get(Integer.parseInt(transaction.getRecipient()));
								int receiverId = Integer.parseInt(transaction.getRecipient());
								double amount = transaction.getAmount();

								// Only display transactions where the sender is the current user
								if (sender.equals(currentUserName)) {
							%>
							<div class="card m-auto mb-2 p-3 group-card">
								<div class="d-flex justify-content-between align-items-center">
									<div class="transaction-from col-4">
										<p><%=sender%></p>
										<p class="total-amt fw-bold">
											₹<%=amount%></p>
									</div>
									<div class="transaction-icon col-4">
										<span class="material-symbols-rounded d-block text-center p-2">arrow_forward</span>
										<form action="settleExpense.jsp" method="post" target="_blank">
											<input type="hidden" name="transactionId" value="<%=transactionId%>">
											<input type="hidden" name="expenseId" value="<%=expenseId%>">
											<input type="hidden" name="sender" value="<%=sender%>">
											<input type="hidden" name="senderId" value="<%=senderId%>">
											<input type="hidden" name="amount" value="<%=amount%>">
											<input type="hidden" name="recipient" value="<%=receiver%>">
											<input type="hidden" name="recipientId"
												value="<%=receiverId%>">
											<button type="submit" class="btn btn-success">SETTLE</button>
										</form>
									</div>
									<div class="transaction-to col-4">
										<p><%=receiver%></p>
									</div>
								</div>
							</div>
							<%
							}
							}
							%>
						</div>

						<!-- All Transactions Tab -->
						<div class="tab-pane fade" id="all-transactions" role="tabpanel"
							aria-labelledby="all-transactions-tab">
							<%
							for (Transaction transaction : allTransactions) {
								int transactionId = transaction.getTransactionId();
								int expenseId = transaction.getExpenseId();
								String sender = membersMap.get(Integer.parseInt(transaction.getSender()));
								int senderId = Integer.parseInt(transaction.getSender());
								String receiver = membersMap.get(Integer.parseInt(transaction.getRecipient()));
								int receiverId = Integer.parseInt(transaction.getRecipient());
								double amount = transaction.getAmount();
							%>
							<div class="card m-auto mb-2 p-3 group-card">
								<div class="d-flex justify-content-between align-items-center">
									<div class="transaction-from col-4">
										<p><%=sender%></p>
										<p class="total-amt fw-bold">
											₹<%=amount%></p>
									</div>
									<div class="transaction-icon col-4">
										<span class="material-symbols-rounded d-block text-center p-2">arrow_forward</span>
										<form action="settleExpense.jsp" method="post" target="_blank">
											<input type="hidden" name="transactionId" value="<%=transactionId%>">
											<input type="hidden" name="expenseId" value="<%=expenseId%>">
											<input type="hidden" name="sender" value="<%=sender%>">
											<input type="hidden" name="senderId" value="<%=senderId%>">
											<input type="hidden" name="amount" value="<%=amount%>">
											<input type="hidden" name="recipient" value="<%=receiver%>">
											<input type="hidden" name="recipientId"
												value="<%=receiverId%>">
											<button type="submit"
												class="btn btn-success<%=sender.equals(currentUserName) ? "" : " hidden"%>">SETTLE</button>
										</form>
									</div>
									<div class="transaction-to col-4">
										<p><%=receiver%></p>
									</div>
								</div>
							</div>
							<%
							}
							%>
						</div>

					</div>
				</div>

			</div>
			<div class="tab-pane fade p-3" id="summary" role="tabpanel">
        <%
            for (Expense expense : allArchivedExpenses) {
                int expenseId = expense.getExpenseId();
                String expenseName = expense.getExpenseName();
                double amount = expense.getAmount();
                String date = expense.getDate();
                String receiver = membersMap.get(expense.getPaidBy());
        %>
        <div class="card m-auto mb-2 p-3 group-card">
            <div class="d-flex justify-content-between align-items-center">
                <div class="transaction-from">
                    <p class="fw-bold"><%= expenseName %></p>
                    <p><%= date %></p>
                    <p><%= receiver %></p>
                    <p class="total-amt fw-bold">₹<%= String.format("%.2f", amount) %></p>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
		</div>
	</div>

	<!-- ADD BUTTON -->
	<div class="create-transaction">
		<span class="material-symbols-rounded" data-bs-toggle="modal"
			data-bs-target="#newTransaction"> add_circle </span>
	</div>

	<!-- GROUP DETAILS MODAL -->
	<div class="modal fade" id="detailsModal" tabindex="-1"
		aria-labelledby="detailsModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="detailsModalLabel">Group
						Details</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form>
						<div class="mb-3">
							<label for="recipient-name" class="col-form-label">Group
								Name:</label>

							<div class="input-group mb-3 edit-name">
								<input type="text" class="form-control"
									value="<%=groupDetails.getGroupName()%>"
									aria-label="Recipient's username"
									aria-describedby="button-addon2">
								<button class="btn btn-outline-secondary" type="button"
									id="button-addon2">
									<span class="material-symbols-rounded"> edit </span>
								</button>
							</div>
						</div>
					</form>
					<div>
						<input type="text" class="form-control"
							value=<%=groupDetails.getLink()%> readonly>
					</div>
					<hr />
					<div class="mb-3">
						<span>Total expenses: ₹2000</span>
					</div>
					<hr />

					<div class="group-members ">
						<ul class="members-list list-group">
							<%
							for (Entry<Integer, String> member : membersList) {
							%>
							<li class="list-group-item"><span
								class="material-symbols-rounded">account_circle </span><%=member.getValue()%></li>

							<%
							}
							%>
						</ul>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- ADD TRANSACTION MODAL -->
	<div class="modal fade" id="newTransaction" tabindex="-1"
		aria-labelledby="transactionModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="transactionModalLabel">Create
						a new Expense</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form method="post" action="AddExpenseServlet">
						<div class="mb-3">
							<label for="expense-name" class="col-form-label">Expense
								Name: </label> <input type="text" class="form-control"
								name="expense_name" id="recipient-name" required>
						</div>
						<div class="mb-3">
							<label for="amount" class="col-form-label">Amount: </label>
							<div class="input-group mb-3 amount">
								<button class="btn btn-outline-secondary" type="button"
									id="button-addon2">₹</button>
								<input type="number" class="form-control" aria-label="amount"
									name="amount" aria-describedby="button-addon2">
							</div>
						</div>

						<div class="members">
							<p class="d-flex ">
								<span class="material-symbols-rounded position-relative top-50">info
								</span> Share with all members <span
									class="form-check form-switch switch mx-3"> <input
									class="form-check-input" type="checkbox" role="switch"
									data-bs-toggle="collapse" data-bs-target="#collapseMemeberList"
									aria-expanded="false" aria-controls="collapseMemberList"
									id="flexSwitchCheckChecked" checked>
								</span>
							</p>
							<div class="collapse" id="collapseMemeberList">
								<div class="group-members ">
									<h4 class="fs-6">Select members to share with:</h4>
									<ul class="members-list list-group mb-3 mx-3">

										<%
										for (Entry<Integer, String> member : membersList) {
										%>
										<li class="list-group-item form-check"><label
											class="form-check-label member-name"><%=member.getValue()%>
												<input class="form-check-input" type="checkbox"
												name="selected_members" id="<%=member.getKey()%>"
												value="<%=member.getKey()%>" checked> </label></li>
										<%
										}
										%>
									</ul>
								</div>
							</div>
						</div>
						<input type="hidden" name="group_id" value="<%=groupId%>">
						<button type="submit" class="btn btn-primary add-transaction-btn">Create
							Expense</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="Bootstarp/js/bootstrap.min.js"></script>
	<script src="Bootstrap/js/bootstrap.bundle.js"></script>

</body>
</html>