<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="fetchgroups.GroupDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@24,400,1,0" />
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="Bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/groupStyle.css">
<title>Payment</title>
<style>
.payment-sheet {
	max-width: 500px;
	margin: 0 auto;
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	text-align: center;
}

.qr-code {
	margin-top: 20px;
}
</style>
</head>
<body>
	<%
	GroupDAO groupDetailsFetcher = new GroupDAO();

	int senderId = Integer.parseInt(request.getParameter("senderId"));
	int receiverId = Integer.parseInt(request.getParameter("recipientId"));
	int transactionId = Integer.parseInt(request.getParameter("transactionId"));
	int expenseId = Integer.parseInt(request.getParameter("expenseId"));

	String senderName = request.getParameter("sender");
	String receiverName = request.getParameter("recipient");
	double amount = Double.parseDouble(request.getParameter("amount"));

	String senderPhone = groupDetailsFetcher.getUserPhone(senderId);
	String receiverPhone = groupDetailsFetcher.getUserPhone(receiverId);
	%>
	<div class="container">
		<div class="payment-sheet">
			<h1>Payment Details</h1>
			<div>
				<p>
					Sender:
					<%=senderName%>
				</p>
				<p>
					Amount:
					<%=amount%>
				</p>
				<p>
					Recipient:
					<%=receiverName%>
				</p>
				<div class="qr-code">
					<!-- QR code image will be displayed here -->
					<img src="<%=generateQRCode(receiverPhone, amount)%>" alt="QR Code">
				</div>
			</div>
			<p class="text-muted">Scan the QR code to make the payment.</p>
			<form method="post" action="ArchiveExpenses">
				<input type="hidden" name="transactionId" value="<%=transactionId%>">
				<input type="hidden" name="expenseId" value="<%=expenseId%>">
				<button type="submit"
					class="group-details-btn btn btn-outline-success me-2"
					data-bs-toggle="modal" data-bs-target="#confirmModal" type="button">Confirm</button>
			</form>
		</div>
	</div>



	<!-- GROUP DETAILS MODAL -->
	<div class="modal fade" id="confirmModal" tabindex="-1"
		aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="paymentModalLabel">Payment
						Complete</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div id="paymentAnimation" style="text-align: center;">
						<!-- Add your payment complete animation here -->
						<div class="spinner-border text-success" role="status">
							<span class="sr-only"></span>
						</div>
						<p class="sr-only">Loading...</p>
						<p>Processing payment...</p>
					</div>
					<div id="paymentSuccess" style="display: none; text-align: center;">
						<i class="material-symbols-rounded"
							style="font-size: 48px; color: green;">check_circle</i>
						<p>Payment Successful!</p>
					</div>
				</div>
			</div>
		</div>
	</div>



	<%-- Include your QR code generation logic here --%>
	<%-- This is just a placeholder function --%>
	<%!// Method to generate QR code
	public String generateQRCode(String receiverPhone, double amount) {
		// Placeholder implementation, replace with actual QR code generation logic
		String upiString = "upi://pay?pa=" + receiverPhone + "/pn=Recipient/am=" + amount + "/cu=INR";
		String qrCodeUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + upiString;
		return qrCodeUrl;
	}%>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
	<script src="Bootstarp/js/bootstrap.min.js"></script>
	<script src="Bootstrap/js/bootstrap.bundle.js"></script>

	<script>
		function confirmPayment(transactionId, expenseId) {
			$.ajax({
				type : "POST",
				url : "ArchiveExpenses",
				data : {
					transactionId : transactionId,
					expenseId : expenseId
				},
				success : function(response) {
					if (response.status === "success") {
						showPaymentModal();
					}
				},
				error : function() {
					alert("An error occurred while processing the payment.");
				}
			});
		}
		function showPaymentModal() {
			var modal = new bootstrap.Modal(document
					.getElementById('confirmModal'));
			var bsModal = new bootstrap.Modal(modal);

			// Show the modal
			bsModal.show();

			// Show the spinner and hide the success message initially
			document.getElementById('paymentAnimation').style.display = 'block';
			document.getElementById('paymentSuccess').style.display = 'none';

			// After 2 seconds, hide the spinner and show the success message
			setTimeout(
					function() {
						document.getElementById('paymentAnimation').style.display = 'none';
						document.getElementById('paymentSuccess').style.display = 'block';
					}, 2000);
		}}
	</script>
</body>
</html>
