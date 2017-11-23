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
	href="${pageContext.request.contextPath}/order/query_order.css">

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
		<a class="navbar-brand" href="User_showCart">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			确认订单
		</a>
	</nav>
	<div class="container" style="margin-top: 15px;">
		<form action="UserAddress_save" method="post">
			<h5><span class="badge badge-primary">选择收货地址</span></h5>
			<c:choose>
				<c:when test="${fn:length(userAddressList) == 0}">
					<button type="button" class="add-new-address btn btn-outline-primary btn-block"
							onclick="javascript:window.location.href='${pageContext.request.contextPath}/user/add_address.jsp';">
						新建收货地址	
					</button>
				</c:when>
				<c:otherwise>
					<c:forEach items="${userAddressList }" var="userAddress">
						<c:choose>
							<c:when test="${userAddress.firstChoice}">
								<div data-toggle="modal" data-target="#selectAddressModal"
										class="list-group-item list-group-item-action flex-column align-items-start" style="border-color: #0275d8;">
									<input type="hidden" id="selected-useraddress-id" value="${userAddress.id }">
									<div class="d-flex w-100 justify-content-between">
										<h5 id="selected-useraddress-consignee" class="mb-1">${userAddress.consignee }</h5>
										<small id="selected-useraddress-postcode">${userAddress.postcode }</small>
									</div>
									<p class="mb-1" id="selected-useraddress-region-name">${userAddress.regionName } ${userAddress.address }</p>
									<div class="d-flex w-100 justify-content-between">
										<small id="selected-useraddress-phone">${userAddress.phone }</small>
									</div>
								</div>
							</c:when>
						</c:choose>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<div style="margin-top: 15px;">
				<h5><span class="badge badge-primary">订单项目</span></h5>
				<ul class="list-unstyled list-group">
					<c:forEach items="${productList }" var="product">
						<li id="product-list" class="media list-group-item">
							<input class="product-item-id" type="hidden" value="${product.id }">
							<img class="d-flex mr-3 product-item-cover" src="${product.covers[0] }" alt="Generic placeholder image">
							<div class="media-body">
								<h5 class="mt-0 mb-1">『${product.name }』</h5>
								${product.manufacturer } ${product.author }
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</form>
	</div>

	<div class="modal fade" id="selectAddressModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">选择收货地址</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<c:forEach items="${userAddressList }" var="userAddress" >
						<c:choose>
							<c:when test="${userAddress.firstChoice}">
								<div id="modal-item-id-${userAddress.id }" onclick="javascript:selectedAddress(${userAddress.id });"
									class="active modal-item list-group-item list-group-item-action flex-column align-items-start">
							</c:when>
							<c:otherwise>
								<div id="modal-item-id-${userAddress.id }" onclick="javascript:selectedAddress(${userAddress.id });"
									class="modal-item list-group-item list-group-item-action flex-column align-items-start">
							</c:otherwise>
						</c:choose>
						<%-- <div id="modal-item-id-${userAddress.id }" onclick="javascript:selectedAddress(${userAddress.id });"
							class="modal-item list-group-item-action flex-column align-items-start"> --%>
							<input type="hidden" value="${userAddress.id }">
							<div class="d-flex w-100 justify-content-between">
								<h5 id="modal-item-consignee-${userAddress.id }" class="mb-1">${userAddress.consignee }</h5>
								<small id="modal-item-postcode-${userAddress.id }">${userAddress.postcode }</small>
							</div>
							<p id="modal-item-region-name-${userAddress.id }" class="mb-1">${userAddress.regionName } ${userAddress.address }</p>
							<div class="d-flex w-100 justify-content-between">
								<small id="modal-item-phone-${userAddress.id }">${userAddress.phone }</small>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="javascript:querySelect()">确认选择</button>
				</div>
			</div>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${fn:length(userAddressList) != 0}">
			<button type="button" id="add-to-order-button" class="btn btn-primary btn-lg"
				onclick="javascript:addToOrder();">提交</button>
		</c:when>
	</c:choose>
	
	<script>
		
		function selectedAddress(id) {
			$(".modal-item").removeClass("active");
			$("#modal-item-id-" + id).addClass("active");
		}
		
		function querySelect() {
			var id = $(".active").children().first().val();
			var consignee = $("#modal-item-consignee-" + id).html();
			var postcode = $("#modal-item-postcode-" + id).html();
			var regionName = $("#modal-item-region-name-" + id).html();
			var phone = $("#modal-item-phone-" + id).html();
			var msg = id + " " + consignee + " " + regionName + " " + phone;

			$("#selected-useraddress-id").val(id)
			$("#selected-useraddress-consignee").html(consignee)
			$("#selected-useraddress-region-postcode").html(postcode)
			$("#selected-useraddress-region-name").html(regionName);
			$("#selected-useraddress-phone").html(phone);
			$('#selectAddressModal').modal('hide');
		}
		
		function addToOrder() {
			var userAddressId = $("#selected-useraddress-id").val();
			var selectedSpan=$(".product-item-id");
			
			if(selectedSpan.length==0){
				return;
			}

			var strIds=[];
			for(var i=0;i<selectedSpan.length;i++){
				strIds.push(selectedSpan[i].value);
			}
			var ids=strIds.join(",");
			$.post("Order_addToOrder.action",
				{ids: ids, userAddressId: userAddressId},
				function(result){
					if(result.success){
						window.location.href="User_showBorrowed";
					}else{
						alert("error");
					}
				},
				"json"
			);
		}

	</script>
	
</body>
</html>
