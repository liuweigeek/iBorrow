<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="太原才智教育科技有限公司">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
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
	
<link rel="stylesheet" href="${pageContext.request.contextPath}/user/payment_list.css" />

<!-- 	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->

	<%
    	if(session.getAttribute(Constant.key_value.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		} 
	%>

	<script>

		function formatDate(timelong) {
			var year=timelong.getYear();
			var month=timelong.getMonth()+1;
			var date=timelong.getDate();
			/* var hour=timelong.getHours();
			var minute=timelong.getMinutes();
			var second=timelong.getSeconds(); */
			return year+"-"+month+"-"+date;
		}

	</script>

</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="User_showUserCenter">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			我的交易记录
		</a>
	</nav>
	<div class="container">
		<div class="list-group user-address-list">
			<!-- 列表项 -->
			<c:forEach items="${paymentList }" var="payment" >
				<div id="list-group-item-${payment.id }" class="list-group-item list-group-item-action flex-column align-items-start">
				
					<div class="d-flex w-100 justify-content-between">
						<h6 class="mb-1">
							<fmt:formatDate value="${payment.time_start }" pattern="yyyy年MM月dd日 HH时mm分ss秒"/>
						</h6>
						<small>${payment.body }</small>
					</div>
					<p class="mb-1">${payment.transaction_id }</p>
					<div class="d-flex w-100 justify-content-between">
						<small>
							<span class="badge badge-warning badge-pill">
								金额：<fmt:formatNumber type="number" value="${payment.total_fee*(1/100) }" pattern="0.00" maxFractionDigits="2"/>元
							</span>
						</small>
						<c:choose>
							<c:when test="${payment.finish}">
								<span class="badge badge-success badge-pill">
									已支付
								</span>
							</c:when>
							<c:otherwise>
								<span class="badge badge-danger badge-pill">
									未支付
								</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>
