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
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
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
	
<link rel="stylesheet" href="${pageContext.request.contextPath}/order/order_list.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/text-color.css">

<!-- 	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->

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
		<a class="navbar-brand" href="User_showUserCenter">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			借阅记录
		</a>
	</nav>
	<div class="container">
		
		<div class="list-group order-list">
			<!-- 列表项 -->
			<c:forEach items="${orderList }" var="order" >
				<div id="list-group-item-${order.id }" onclick="javascript:window.location.href='Order_showDetail?orderId=${order.id }'"
					class="list-group-item list-group-item-action flex-column align-items-start">
					<div class="d-flex w-100 justify-content-between">
						<h5 class="mb-1">${order.createTime }</h5>
						<small>
							<c:choose>
								<c:when test="${order.orderType==301 }">
									<span class="greenText">
										借阅订单
									</span>
								</c:when>
								<c:when test="${order.orderType==302 }">
									<span class="orangeText">
										退还订单
									</span>
								</c:when>
							</c:choose>
						</small>
					</div>
					<p class="mb-1">物流单号 ${order.expressNum }</p>
					<div class="d-flex w-100 justify-content-between">
						<small>
							<c:choose>
								<c:when test="${order.status==401}">
									<span class="brownText">未支付</span>
								</c:when>
								<c:when test="${order.status==402}">
									<span class="redText">未发货</span>
								</c:when>
								<c:when test="${order.status==403}">
									<span class="greenText">已收货</span>
								</c:when>
								<c:when test="${order.status==404}">
									<span class="orangeText">已发货</span>
								</c:when>
								<c:when test="${order.status==405}">
									<span class="blueText">已完成</span>
								</c:when>
								<c:otherwise>
									<span class="grayText">未知状态订单</span>
								</c:otherwise>
							</c:choose>
						</small>
						包含${fn:length(order.orderItems)}本
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>
