<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="太原才智教育科技有限公司">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<title>${webpagename }</title>

<!-- Tether -->
<link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/tether.min.js"></script>

<!-- Bootstrap -->
<%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
<link href="${pageContext.request.contextPath}/css/bootstrap4.min.css"
	rel="stylesheet">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>

	<style>
		body {
			background-image: url(/admin/images/login-bg.jpg);
			background-position: center;
			background-repeat: no-repeat;
			background-attachment: fixed;
		}
		
		#login-card {
			opacity: 0.9;		
		}
	</style>

</head>

<body>
	
	<div class="container" style="width: 20rem; margin-top: 15%;">
		<div id="login-card"  class="card">
			<div class="card-block">
				<h4 class="card-title">管理员登录</h4>
				<p class="card-text">

					<div class="form-group">
						<label class="form-control-label" for="login-card-phone">手机号</label>
						<input type="tel" class="form-control"
							id="login-card-phone" placeholder="请输入手机号">
					</div>
					<div class="form-group">
						<label class="form-control-label" for="login-card-password">密码</label>
						<input type="password" class="form-control"
							id="login-card-password" placeholder="请输入密码">
					</div>
				</p>

				<div style="text-align: center;">
					<button style="cursor: pointer; text-decoration-line: none;" type="button" onclick="javascript:login();" class="btn btn-link">登录</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	
		function login() {
			
			var adminPhone = $("#login-card-phone").val();
			var adminPassword = $("#login-card-password").val();
			
			$.post("User_adminLogin.action",
				{adminPhone: adminPhone, adminPassword: adminPassword}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						window.location.href='admin';
					} else {
						alert('手机号或密码错误');
					}
				}
			);
		
		}
	
	</script>
	
</body>
</html>
