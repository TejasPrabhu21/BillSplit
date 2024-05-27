<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@24,400,1,0" />
<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/loginStyle.css">
</head>
<body>
	<input type="hidden" id="status"
		value="<%=request.getAttribute("status")%>">
	<div class="main">
		<section class="sign-in">
			<div class="container">
				<div class="content">
					<div class="signin-image">
						<figure>
							<img src="assets/images/signin-image.jpg" alt="sing up image">
						</figure>
						<a href="register.jsp" class="signup-link">Create an
							account</a>
					</div>

					<div class="form">
						<h2 class="form-title">Sign In</h2>
						<form method="post" action="LoginServlet" class="register-form" id="login-form">
							<div class="form-group">
								<label for="email"> <span
									class="material-symbols-rounded"> mail </span>
								</label> <input type="email" name="email" id="email"
									placeholder="E-Mail" required />
							</div>
							<div class="form-group">
								<label for="password"><span
									class="material-symbols-rounded"> lock </span></label> <input
									type="password" name="password" id="password"
									placeholder="Password" required/>
							</div>
			
							<div class="form-button">
								<input type="submit" name="signin" id="signin"
									class="form-submit" value="Log in" />
							</div>
						</form>
						
					</div>
				</div>
			</div>
		</section>

	</div>
	
	<div class="toast-container position-fixed bottom-0 end-0 p-4 ">
		<div id="liveToast" class="toast p-2" role="alert"
			aria-live="assertive" aria-atomic="true">
			<div class="toast-body">Login Failed.. Wrong user name or password.</div>
		</div>
	</div>


	<script src="Bootstrap/js/bootstrap.bundle.js"></script>
	<script>
		var status = document.getElementById('status').value;
		const toastLiveExample = document.getElementById('liveToast');
		if (status == "failed") {
			const toastLive = new bootstrap.Toast(toastLiveExample);
			toastLive.show();
		}
	</script>
</body>
</html>