<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@24,400,1,0" />
<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/loginStyle.css">
<title>Insert title here</title>
</head>
<body>
	<input type="hidden" id="status"
		value="<%=request.getAttribute("status")%>">
	<div class="main">
		<section class="sign-up">
			<div class="container">
				<div class="content">
					<div class="form">
						<h2 class="form-title">Sign Up</h2>

						<form method="post" action="RegisterServlet" class="register-form"
							id="register-form">
							<div class="form-group">
								<label for="name"><span class="material-symbols-rounded">
										person </span></label> <input type="text" name="name" id="name"
									placeholder="Your Name" required />
							</div>
							<div class="form-group">
								<label for="email"><span
									class="material-symbols-rounded"> mail </span></label> <input
									type="email" name="email" id="email" placeholder="Your Email"
									required />
							</div>
							<div class="form-group">
								<label for="pass"><span class="material-symbols-rounded">
										lock </span></label> <input type="password" name="pass" id="pass"
									placeholder="Password" required />
							</div>
							<div class="form-group">
								<label for="re-pass"><span
									class="material-symbols-rounded"> lock </span></label> <input
									type="password" name="re_pass" id="re_pass"
									placeholder="Repeat your password" required />
							</div>
							<div class="form-group">
								<label for="contact"><span
									class="material-symbols-rounded"> phone </span></label> <input
									type="text" name="contact" id="contact"
									placeholder="Contact no" required />
							</div>

							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup"
									class="form-submit" value="Register" />
							</div>

						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="assets/images/signup-image.jpg" alt="sing up image">
						</figure>
						<a href="login.jsp" class="signin-link">Already a member?
							Login</a>
					</div>
				</div>
			</div>
		</section>


	</div>

	<div class="toast-container position-fixed bottom-0 end-0 p-4 ">
		<div id="liveToast" class="toast p-2" role="alert"
			aria-live="assertive" aria-atomic="true">
			<div class="toast-body">Registered Successfully.</div>
		</div>
	</div>


	<script src="Bootstrap/js/bootstrap.bundle.js"></script>
	<script>
		var status = document.getElementById('status').value;
		const toastLiveExample = document.getElementById('liveToast');
		if (status == "success") {
			const toastLive = new bootstrap.Toast(toastLiveExample);
			toastLive.show();
		}
	</script>
</body>
</html>