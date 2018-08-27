<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link href="${pageContext.request.contextPath}/css/bootstrap4.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/jquery.toast.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/product/style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/product/productDetail.css" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.toast.min.js"></script>

<script src="${pageContext.request.contextPath}/js/go-back.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
<!-- <link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

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

<body style="margin-bottom: 80px;">
	<div class="container">

		<!-- 顶部菜单栏 -->
		<nav class="navbar navbar-light bg-faded">
			<a class="navbar-brand" href="javascript:goBack()">
				<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
				${product.name }
			</a>
		</nav>

		<!-- 底部导航按钮 -->
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container" style="padding: 0px;">
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
						<li class="md-li-favorite nav-item" onclick="javascript:removeFromFavorite(${product.id });">
							<div class="nav-link">
								<i class="md-icon-favorite nav-link-icon material-icons">favorite</i>
								<div class="md-text-favorite nav-item-text">取消收藏</div>
							</div>
						</li>
					<%
						} else {
					%>
						<li class="md-li-favorite nav-item" onclick="javascript:addToFavorite(${product.id });">
							<div class="nav-link">
								<i class="md-icon-favorite nav-link-icon material-icons">favorite_border</i>
								<div class="md-text-favorite nav-item-text">加入收藏</div>
							</div>
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
						<li class="md-li-cart nav-item" onclick="javascript:removeFromCart(${product.id });">
							<div class="nav-link">
								<i class="md-icon-cart nav-link-icon material-icons">shopping_cart</i>
								<div class="md-text-cart nav-item-text">取消借阅</div>
							</div>
						</li>
					
					<%
						} else {
					%>
					
						<li class="md-li-cart nav-item" onclick="javascript:addToCart(${product.id });">
							<div class="nav-link">
								<i class="md-icon-cart nav-link-icon material-icons">add_shopping_cart</i>
								<div class="md-text-cart nav-item-text">加入借阅</div>
							</div>
						</li>
					<%
						}
					 %>
				</ul>
			</div>
		</nav>

		<div class="card">
			<!-- 轮播图 -->
			<div id="carouselIndicators" class="card-img-top carousel slide"
				data-ride="carousel">
				<ol class="carousel-indicators">
					<c:forEach items="${product.covers }" var="cover" varStatus="stauts">
						<c:choose>
							<c:when test="${stauts.index=='0' }">
								<li data-target="#carouselIndicators"
									data-slide-to="${stauts.index+1}" class="active"></li>
							</c:when>
							<c:otherwise>
								<li data-target="#carouselIndicators"
									data-slide-to="${stauts.index+1}"></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ol>
				<div class="carousel-inner" role="listbox">
					<c:forEach items="${product.covers }" var="cover" varStatus="stauts">
						<c:choose>
							<c:when test="${stauts.index=='0' }">
								<div class="carousel-item active">
									<img class="d-block img-fluid" alt=""
										src="${cover }">
								</div>
							</c:when>
							<c:otherwise>
								<div class="carousel-item">
									<img class="d-block img-fluid" alt=""
										src="${cover }">
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
				<a class="carousel-control-prev" href="#carouselIndicators"
					role="button" data-slide="prev"> <span
					class="carousel-control-prev-icon" aria-hidden="true"></span> <span
					class="sr-only">Previous</span>
				</a> <a class="carousel-control-next" href="#carouselIndicators"
					role="button" data-slide="next"> <span
					class="carousel-control-next-icon" aria-hidden="true"></span> <span
					class="sr-only">Next</span>
				</a>
			</div>
			<div class="card-block">
				<h4 class="card-title">${product.name }</h4>
				<p class="card-text">
					<c:choose>
						<c:when test="${product.type==202 }">
							<img src="${pageContext.request.contextPath}/images/icon/robot.svg" alt="">
						</c:when>
						<c:when test="${type==203 }">
							<img src="${pageContext.request.contextPath}/images/icon/pen.svg" alt="">
						</c:when>
						<c:when test="${type==204 }">
							<img src="${pageContext.request.contextPath}/images/icon/robot.svg" alt="">
							<img src="${pageContext.request.contextPath}/images/icon/pen.svg" alt="">
						</c:when>
					</c:choose><br/>
					<label>作者: ${product.author }</label><br/>
					<label>出版社: ${product.manufacturer }</label><br/>
					<label>
						装订: 
						<c:choose>
							<c:when test="${product.bingding==250 }">
								平装
							</c:when>
							<c:otherwise>
								精装
							</c:otherwise>
						</c:choose>
					</label><br/>
					<label>库存: ${product.remainder }</label>
				</p>
			</div>
		</div>
		
		<span class="badge badge-pill badge-success zone_badge">简介</span>
		<div class="card card-introduction">
			${product.introduction }
		</div>
		
		<span class="badge badge-pill badge-success zone_badge">发表评论</span>

		<div style="margin:5px 5px 15px 5px;">
			<form id="save-comment-form" action="Comment_save" method="post">
				<input type="hidden" value="${product.id }" name="productId">
				<!-- <div class="form-group">
					<input type="text" name="title" class="form-control" placeholder="评论标题">
				</div> -->
				<div class="form-group">
					<textarea name="content" placeholder="输入评价内容" class="form-control" rows="3"></textarea>
				</div>
				<input type="button" class="btn btn-success" value="提交评论" onclick="javascript:save_comment(${product.id });"></input>
			</form>
		</div>
		
		<span class="badge badge-pill badge-success zone_badge">用户评论</span>
		<ul class="list-group" style="margin: 5px;">
			<c:forEach items="${commentList }" var="commentItem">
				<li class="list-group-item">
					<div class="media">
						<img class="list-item-headimg d-flex mr-3" src="${commentItem.user.headimgurl }" alt="">
						<div class="media-body">
							<h6 class="mt-0">${commentItem.user.nickname }</h6>
							<i>${commentItem.content }</i>
							<!-- <p>
								<small class="text-muted"></small>
							</p> -->
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<script>
	
		function addToFavorite(productId) {
			$.post("Product_addToFavorite.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$(".md-icon-favorite").empty();
						$(".md-icon-favorite").append("favorite");
						$(".md-text-favorite").empty();
						$(".md-text-favorite").append("取消收藏");
						$(".md-li-favorite").attr('onclick','javascript:removeFromFavorite(' + productId + ')');
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
						$(".md-icon-cart").empty();
						$(".md-icon-cart").append("shopping_cart");
						$(".md-text-cart").empty();
						$(".md-text-cart").append("取消借阅");
						$(".md-li-cart").attr('onclick','javascript:removeFromCart(' + productId + ')');
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
						$(".md-icon-favorite").empty();
						$(".md-icon-favorite").append("favorite_border");
						$(".md-text-favorite").empty();
						$(".md-text-favorite").append("添加收藏");
						$(".md-li-favorite").attr('onclick','javascript:addToFavorite(' + productId + ')');
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
						$(".md-icon-cart").empty();
						$(".md-icon-cart").append("add_shopping_cart");
						$(".md-text-cart").empty();
						$(".md-text-cart").append("加入借阅");
						$(".md-li-cart").attr('onclick','javascript:addToCart(' + productId + ')');
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

		function save_comment(productId) {
			$.ajax({
				cache: false,
				type: "POST",
				url:"Comment_save",	//把表单数据发送到ajax.jsp
				data:$('#save-comment-form').serialize(),	//要发送的是ajaxFrm表单中的数据
				async: false,
				error: function(request) {
					alert("评论失败，请稍后重试。");
				},
				success: function(data) {
					window.location.href="Product_show.action?productId=" + productId;
				}
			});
		}
	</script>
</body>
</html>
