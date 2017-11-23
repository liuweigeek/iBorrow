<%@page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="description" content="太原才智教育科技有限公司">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
		<title>${webpagename }后台管理</title>
		
		<!-- Bootstrap -->
		<%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
	    <link href="${pageContext.request.contextPath}/css/bootstrap4.min.css" rel="stylesheet">
		<link rel="stylesheet" href="style.css" />
		<link rel="stylesheet" href="index.css" />
	
	    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
		<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
	    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>
	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

	</head>
	<body>
	    <div class="container">
	    	<aside>
	    		<ul class="nav flex-column">
					<li class="nav-item">
						<a class="nav-link active" href="#">Active</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">Link</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">Link</a>
					</li>
					<li class="nav-item">
						<a class="nav-link disabled" href="#">Disabled</a>
					</li>
				</ul>
			</aside>
			
			
	    
	    	<main class="mdl-layout__content mdl-color--grey-100">
				<div style="margin: 20 auto;">
					<jsp:include page="${mainPage }"></jsp:include>
				</div>
			</main>
	    </div>
	</body>
</html>
