<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="styles/indexStyle.css">
<title>Split the bill</title>
</head>
<body>
	<nav>
		<figure class="logo">
			<img src="assets/images/bill-cash.png" alt="Logo image">	
			<h1 class="caption">BillSplit</h1>
		</figure>
		<div class="buttons me-2">
		<a href="register.jsp" class="btn" role="button">SignUp</a>
		<div class="vr my-3 bg-dark"></div>
		<a href="login.jsp" class="btn" role="button" >LogIn</a>
		</div>
		<!--<a href="register.jsp"><button class="btn btn-outline-success m-2"
				  type="button">SignUp</button></a>
		<a href="login.jsp"><button class="btn btn-outline-success m-2"
				 type="button">SignIn</button></a>-->
	</nav>
	
	<div class="content">
		<div class="welcome-text">
			<p class="main-text">Streamlining Shared Expenses for <br> Stress-free Living.</p>
			<small class="fs-6">Manage your bills with friends effortlessly.</small>
			<small class="fs-6">SignUp to access the app.</small>
			<div class="d-flex align-items-center">
			<a href="register.jsp"><button class="start-btn mt-4" >Get Started </button></a>
			</div>
		</div>
		
		<div class="main-image">
			<img class="image" src="assets/images/home.png">
		</div>
	</div>
</body>
</html>