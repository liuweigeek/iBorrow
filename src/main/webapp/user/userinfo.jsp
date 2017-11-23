<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="太原才智教育科技有限公司">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0"> -->

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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
<!-- <link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->
	
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/userinfo.css" />

	<%
    	if(session.getAttribute(Constant.key_value.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		} 
	%>

</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="javascript:history.go(-1)">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			个人资料
		</a>
	</nav>
	<div class="container">
		<ul class="list-group userinfo-list">
			<!-- 列表项 -->
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">昵称</span>
				<span class="list-group-text">${user.nickname }</span>
			</li>
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">手机号</span>
				<span class="list-group-text">${user.phone }</span>
			</li>
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">电子邮箱</span>
				<span class="list-group-text">${user.email }</span>
			</li>
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">会员开卡时间</span>
				<span class="list-group-text">${user.openTime }</span>
			</li>
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">会员到期时间</span>
				<span class="list-group-text">${user.expirationTime }</span>
			</li>
			<li class="list-group-item justify-content-between">
				<span class="list-group-text">会员号</span>
				<span class="list-group-text">${user.vipId }</span>
			</li>
			<li class="list-group-item">
				<div data-toggle="collapse" data-target="#collapseRecommendeds">
					<span>推荐人数</span>
					<span class="badge badge-default badge-pill" style="margin-left:5px;">
						${fn:length(user.recommendeds)}
					</span>
				</div>
				<div id="collapseRecommendeds" class="collapse" role="tabpanel" aria-labelledby="">
					<div id="card-recommendeds" class="card-block">
						<c:forEach items="${user.recommendeds }" var="recommended">
							<div class="recommendeds-item media">
								<img class="list-item-headimg d-flex mr-3" src="${recommended.headimgurl }" alt="">
								<div class="media-body">
									<h6 class="mt-0">${recommended.nickname }</h6>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</li>
		</ul>
	</div>
</body>
</html>
