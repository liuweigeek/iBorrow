<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
    <link href="${pageContext.request.contextPath}/css/bootstrap4.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/jquery.toast.min.css" rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.toast.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
	<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

	<link rel="stylesheet" href="style.css" />
	<link rel="stylesheet" href="favorite.css" />

	<!-- <script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->

    <%
    	if(session.getAttribute(Constant.key_value.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		}
	%>

	<script>

		function onImgClick(event, productId) {
            event.stopPropagation();
            window.location.href='Product_show.action?productId=' + productId;
		}

	</script>
	
</head>

<body>
    <div class="container">
    
    	<!-- 顶部菜单栏 -->
		<nav class="navbar navbar-light bg-faded">
			<h1 class="navbar-brand mb-0 navbar-title">我的收藏</h1>
		</nav>

		<!-- 底部导航按钮 -->
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container" style="padding: 0px;">
				<ul class="nav nav-pills nav-fill">
					<li class="nav-item"><a class="nav-link"
						href="User_showIndex"> <i class="nav-link-icon material-icons">home</i>
							<div class="nav-item-text">主页</div>
					</a></li>
					<li class="nav-item"><a class="nav-link active" href="User_showFavorite">
							<i class="nav-link-icon material-icons">favorite</i> <div
							class="nav-item-text">我的收藏</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showCart">
							<i class="nav-link-icon material-icons">shopping_cart</i>
							<div class="nav-item-text">借阅车</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showBorrowed">
							<i class="nav-link-icon material-icons">shopping_basket</i>
							<div class="nav-item-text">已借阅</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showUserCenter">
							<i class="nav-link-icon material-icons">person</i><div
							class="nav-item-text">个人中心</div>
					</a></li>
				</ul>
			</div>
		</nav>
		
		<div class="container" style="margin-bottom: 80px;">
		
			<div class="list-group">
			
				<c:forEach items="${favoriteList }" var="favorite">
				<!-- 列表项 -->
					<div id="list-group-item-${favorite.id }" class="list-group-item mdl-grid">
						<div class="mdl-cell mdl-cell--9-col mdl-cell--6-col-tablet mdl-cell--3-col-phone">
							<label>
								<div class="media">
									<img class="d-flex align-self-center mr-3 favorite-item-product-cover"
										src="${favorite.covers[0] }" alt=""
										onclick="javascript:onImgClick(event, ${favorite.id });">
									<div class="media-body">
										<h6 class="mt-0">${favorite.name }</h6>
											剩余库存:${favorite.remainder }
									</div>
								</div>
							</label>
						</div>
						<!-- 列表项操作控件组 -->
						<div class="favorite-item-product-tool mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--1-col-phone">

							<% boolean isCart = false; %> 
							<c:forEach items="${cartList }" var="cartItem">
								<c:choose>
									<c:when test="${favorite.id == cartItem.id }">
										<% isCart = true; %>
									</c:when>
								</c:choose>
							</c:forEach>
							<%
								if (isCart) {
									isCart = false;
							%>
								<div id="add-remove-cart-${favorite.id }" onclick="javascript:removeFromCart(${favorite.id });">
									<i id="add-remove-cart-icon-${favorite.id }" class="favorite-tools-item material-icons mt-0">shopping_cart</i><br/>
								</div>
							<%
								} else {
							%>
								<div id="add-remove-cart-${favorite.id }" onclick="javascript:addToCart(${favorite.id });">
									<i id="add-remove-cart-icon-${favorite.id }" class="favorite-tools-item material-icons mt-0">add_shopping_cart</i><br/>
								</div>
							<%
								}
							 %>

							<!-- 删除标签 -->
							<div onclick="javascript:removeFromFavorite(${favorite.id });">
								<i class="favorite-tools-item material-icons mt-0">delete</i><br/>
							</div>
						</div>
					</div>
				</c:forEach>
				<!-- 列表项
				<div class="list-group-item mdl-grid">
					<div class="form-check mdl-cell mdl-cell--9-col mdl-cell--6-col-tablet mdl-cell--3-col-phone">
						<label class="custom-control custom-checkbox">
							<input type="checkbox" class="custom-control-input">
							<span class="custom-control-indicator"></span>
							<div class="media">
								<img class="d-flex align-self-center mr-3 favorite-item-product-cover"
									src="images/product_cover/20170313024151.jpg" alt="">
								<div class="media-body">
									<h5 class="mt-0">史蒂夫 · 乔布斯传</h5>
										剩余库存:13
								</div>
							</div>
						</label>
					</div>
					列表项操作控件组
					<div class="favorite-item-product-tool mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--1-col-phone">
						删除标签
						<a href="">
							<i class="material-icons mt-0">delete</i><br/>
						</a>
					</div>
				</div> -->
			</div>
			
		</div>
    </div>
    <script>
    	
    	function addToCart(productId) {
			$.post("Product_addToCart.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#add-remove-cart-icon-" + productId).empty();
						$("#add-remove-cart-icon-" + productId).append("shopping_cart");
						$("#add-remove-cart-" + productId).attr('onclick','javascript:removeFromCart(' + productId + ')');
						showMsg('已加入借阅');
					}
				}
			);
		}
		
		function removeFromCart(productId) {
			$.post("Product_removeFromCart.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#add-remove-cart-icon-" + productId).empty();
						$("#add-remove-cart-icon-" + productId).append("add_shopping_cart");
						$("#add-remove-cart-" + productId).attr('onclick','javascript:addToCart(' + productId + ')');
						showMsg('已取消借阅');
					}
				}
			);
		}
		
		
    	function removeFromFavorite(productId) {
			$.post("Product_removeFromFavorite.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#list-group-item-" + productId).remove();
					}
				}
			);
		}
		
		function showMsg(msg) {
			$.toast({
				text: msg,
				position: 'bottom-center',
				textAlign: 'center',
				stack: false
			})
		}
    </script>
</body>
</html>
