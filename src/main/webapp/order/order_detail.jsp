<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/order/order_detail.css">

<!-- <link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

<!-- 	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->

<%
    	/* if(session.getAttribute(Constant.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		} */
	%>
	
</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="Order_showlist">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			借阅记录详情
		</a>
	</nav>
	<div class="container" style="margin-top: 15px;">
		<form action="UserAddress_save" method="post">
			<c:choose>
				<c:when test="${order.orderType == 301 }">
					<h5><span class="badge badge-primary">收货地址</span></h5>
					<div class="list-group-item list-group-item-action flex-column align-items-start"
						style="border-color: #0275d8;">
						
						<input type="hidden" id="selected-useraddress-id" value="${order.userAddress.id }">
						<div class="d-flex w-100 justify-content-between">
							<h5 id="selected-useraddress-consignee" class="mb-1">${order.userAddress.consignee }</h5>
							<small id="selected-useraddress-postcode">${order.userAddress.postcode }</small>
						</div>
						<p class="mb-1" id="selected-useraddress-region-name">${order.userAddress.regionName } ${order.userAddress.address }</p>
						<div class="d-flex w-100 justify-content-between">
							<small id="selected-useraddress-phone">${order.userAddress.phone }</small>
						</div>
					</div>
				</c:when>
			</c:choose>
			
			<div style="margin-top: 15px;">
				<h5><span class="badge badge-primary">订单项目</span></h5>
				<ul class="list-unstyled list-group">
					<c:forEach items="${order.orderItems }" var="orderItem">
						<li id="product-list" class="media list-group-item">
							<input class="product-item-id" type="hidden" value="${orderItem.product.id }">
							<img class="d-flex mr-3 product-item-cover" src="${orderItem.product.covers[0] }" alt="">
							<div class="media-body">
								<h5 class="mt-0 mb-1">『${orderItem.product.name }』</h5>
								${orderItem.product.manufacturer } ${orderItem.product.author }
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>