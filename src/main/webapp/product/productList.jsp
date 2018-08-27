<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="太原才智教育科技有限公司">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>图书列表</title>
    
	<!-- Tether -->
	<link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/tether.min.js"></script>
    
    <!-- Bootstrap -->
	<%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
    <link href="${pageContext.request.contextPath}/css/bootstrap4.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/jquery.toast.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/product/productList.css" rel="stylesheet"/>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.toast.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
	<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

</head>

<body>

	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="User_showIndex">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			图书列表
		</a>
	</nav>
	<div class="container" style="margin-bottom: 80px;">
		<div class="mdl-grid">
			<c:forEach items="${productList }" var="product">
				<div class="card mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--2-col-phone">
					<img class="card-img-top product-item-card-img" src="${product.covers[0] }" alt="${product.name }"
							onclick="javascript:window.location.href='Product_show.action?productId=${product.id }';">
					<div class="card-block product-item-card-block">
						<p class="card-text product-item-card-text">
							<div class="product-name-text">
								${product.name }
							</div>
						</p>
						<ul class="nav nav-pills nav-fill">
							<% boolean isFavorite = false; %>
									<c:forEach items="${favoriteList }" var="favoriteItem">
										<c:choose>
											<c:when test="${product.id == favoriteItem.id }">
												<% isFavorite = true; %>
											</c:when>
										</c:choose>
									</c:forEach>
									<%
										if (isFavorite) {
											isFavorite = false;
									%>
										<li id="md-li-favorite-${product.id }" class="nav-item" onclick="javascript:removeFromFavorite(${product.id });">
											<i id="md-icon-favorite-${product.id }" class="nav-link-icon material-icons product-item-card-action">favorite</i>
										</li>
									<%
										} else {
									%>
										<li id="md-li-favorite-${product.id }" class="nav-item" onclick="javascript:addToFavorite(${product.id });">
											<i id="md-icon-favorite-${product.id }" class="nav-link-icon material-icons product-item-card-action">favorite_border</i>
										</li>
									<%
										}
									 %>

									<% boolean isCart = false; %> 
									<c:forEach items="${cartList }" var="cartItem">
										<c:choose>
											<c:when test="${product.id == cartItem.id }">
												<% isCart = true; %>
											</c:when>
										</c:choose>
									</c:forEach>
									<%
										if (isCart) {
											isCart = false;
									%>
										<li id="md-li-cart-${product.id }" class="nav-item" onclick="javascript:removeFromCart(${product.id });">
											<i id="md-icon-cart-${product.id }" class="nav-link-icon material-icons product-item-card-action">shopping_cart</i>
										</li>
									<%
										} else {
									%>
										<li id="md-li-cart-${product.id }" class="nav-item" onclick="javascript:addToCart(${product.id });">
											<i id="md-icon-cart-${product.id }" class="nav-link-icon material-icons product-item-card-action">add_shopping_cart</i>
										</li>
									<%
										}
									 %>
							
							<!-- <li class="nav-item">
								<a class="nav-link" href="index.jsp">
									<i class="nav-link-icon material-icons product-item-card-action">favorite_border</i>
								</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="user.jsp">
									<i class="nav-link-icon material-icons product-item-card-action">add_shopping_cart</i>
								</a>
							</li> -->
						</ul>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<nav>
			<ul class="pagination justify-content-center">
				<li class="page-item" id="pager-previous" style="margin: 15px;">
					<a class="page-link" style="border-radius: 20px;" href="Product_search.action?keyword=${keyword }&currentPage=${currentPage-1 }">
						<span aria-hidden="true">上一页</span>
					</a>
				</li>
				<li class="page-item" id="pager-next" style="margin: 15px;">
					<a class="page-link" style="border-radius: 20px;" href="Product_search.action?keyword=${keyword }&currentPage=${currentPage+1 }">
						<span aria-hidden="true">下一页</span>
					</a>
				</li>
			</ul>
		</nav>
		
	</div>
	<script>
	
		$(function () {
			if(${currentPage} == 1) {
				$("#pager-previous").addClass("disabled");
			}
			if (${currentPage} == ${totalPage}) {
				$("#pager-next").addClass("disabled");
			}
			if (${totalPage}==0 || ${totalPage}==1) {
				$("#pager-next").addClass("disabled");
			}
		});

		function addToFavorite(productId) {
			$.post("Product_addToFavorite.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#md-icon-favorite-" + productId).empty();
						$("#md-icon-favorite-" + productId).append("favorite");
						$("#md-li-favorite-" + productId).attr('onclick','javascript:removeFromFavorite(' + productId + ')');
						showMsg('已加入收藏');
					}
				}
			);
		}

		function addToCart(productId) {
			$.post("Product_addToCart.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#md-icon-cart-" + productId).empty();
						$("#md-icon-cart-" + productId).append("shopping_cart");
						$("#md-li-cart-" + productId).attr('onclick','javascript:removeFromCart(' + productId + ')');
						showMsg('已加入借阅');
					}
				}
			);
		}

		function removeFromFavorite(productId) {
			$.post("Product_removeFromFavorite.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#md-icon-favorite-" + productId).empty();
						$("#md-icon-favorite-" + productId).append("favorite_border");
						$("#md-li-favorite-" + productId).attr('onclick','javascript:addToFavorite(' + productId + ')');
						showMsg('已取消收藏');
					}
				}
			);
		}

		function removeFromCart(productId) {
			$.post("Product_removeFromCart.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#md-icon-cart-" + productId).empty();
						$("#md-icon-cart-" + productId).append("add_shopping_cart");
						$("#md-li-cart-" + productId).attr('onclick','javascript:addToCart(' + productId + ');');
						showMsg('已取消借阅');
					}
				}
			);
		}
		
		function showMsg(msg) {
			$.toast({
				text: msg,
                hideAfter: 1000,
				position: 'bottom-center',
				textAlign: 'center',
				stack: false
			})
		}
	</script>
</body>
</html>
