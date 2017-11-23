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
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0"> -->

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
	
<title>${topic.title }</title>

<!-- Tether -->
<link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/tether.min.js"></script>

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap4.min.css"
	rel="stylesheet">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
<!-- <link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

</head>

<body>
	<div class="container">
		${topic.content }
	</div>
</body>
</html>
