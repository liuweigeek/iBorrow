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

<link rel="stylesheet" href="${pageContext.request.contextPath}/user/address_list.css" />

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

		function removeAddress(event, userAddressId) {
            event.stopPropagation();
		    $.post("UserAddress_delete.action", {userAddressId: userAddressId},
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						window.location.reload(true);
						/* $("#list-group-item-" + userAddressId).remove(); */
					} else {
					    alert(result.errMsg);
					}
				}
			);
		}

	</script>
	
</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="User_showUserCenter">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			我的收货地址
		</a>
	</nav>
	<div class="container">

		<button type="button" class="add-new-address btn btn-outline-primary btn-block"
				onclick="javascript:window.location.href='${pageContext.request.contextPath}/user/add_address.jsp';">
			新建收货地址
		</button>

		<div class="list-group user-address-list">
			<!-- 列表项 -->
			<c:forEach items="${userAddressList }" var="userAddress" >
				<c:choose>
					<c:when test="${userAddress.firstChoice}">
						<div id="list-group-item-${userAddress.id }" onclick="javascript:window.location.href='UserAddress_showDetail?userAddressId=${userAddress.id }'"
							class="active list-group-item list-group-item-action flex-column align-items-start">
					</c:when>
					<c:otherwise>
						<div id="list-group-item-${userAddress.id }" onclick="javascript:window.location.href='UserAddress_showDetail?userAddressId=${userAddress.id }'"
							class="list-group-item list-group-item-action flex-column align-items-start">
					</c:otherwise>
				</c:choose>

					<div class="d-flex w-100 justify-content-between">
						<h5 class="mb-1">${userAddress.consignee }</h5>
						<small>${userAddress.postcode }</small>
					</div>
					<p class="mb-1">${userAddress.regionName } ${userAddress.address }</p>
					<div class="d-flex w-100 justify-content-between">
						<small>${userAddress.phone }</small>
						<c:choose>
							<c:when test="${userAddress.firstChoice}">
								<div onclick="javascript:removeAddress(event, ${userAddress.id });">
									<i class="material-icons mt-0">delete</i><br/>
								</div>
							</c:when>
							<c:otherwise>
								<div style="color: #0275d8;" onclick="javascript:removeAddress(event, ${userAddress.id });">
									<i class="material-icons mt-0">delete</i><br/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
		</div>

<%-- 		<!-- 列表项 -->
		<c:forEach items="${userAddressList }" var="userAddress" >
			<div class="card">
				<div class="card-header">
					${userAddress.consignee }
				</div>
				<div class="card-block">
					<h4 class="card-title">${userAddress.regionName }</h4>
					<p class="card-text">${userAddress.address }</p>
				</div>
			</div>
		</c:forEach> --%>
	</div>
</body>
</html>
