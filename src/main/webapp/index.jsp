<%@ page import="com.zhinang.iborrow.constant.Constant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="太原才智教育科技有限公司">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>${webpagename }</title>

    <!-- Tether -->
    <link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/tether.min.js"></script>

    <!-- Bootstrap -->
    <%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
    <link href="${pageContext.request.contextPath}/css/bootstrap4.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/jquery.toast.min.css"
          rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.toast.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
    <!-- <link rel="stylesheet"
        href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

    <link rel="stylesheet" href="style.css"/>
    <link rel="stylesheet" href="index.css"/>

    <!-- 	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
        <script type="text/javascript" src="js/jquery.touchSlider.js"></script>
        <script type="text/javascript" src="js/carousel.js"></script> -->

    <%
        if (session.getAttribute(Constant.key_value.CURRENT_USER) == null) {
            response.sendRedirect("User_requestUserInfo.action");
            return;
        }
    %>

    <script>

        function searchByKeyword() {
            $("#search-form").submit();
            /* $.post("Product_search.action", {keyword: keyword},
             function(result){}
             ); */
        }

    </script>

</head>

<body>
<!-- 顶部搜索框 -->
<nav class="navbar navbar-expand-lg navbar-light bg-faded">
    <form action="Product_search.action" method="post" id="search-form" class="form-inline">
        <div class="input-group">
            <!-- <span class="input-group-addon" id="search-input-addon">
                <i style="border-style: none" class="nav-link-icon material-icons navbar-toggler" data-toggle="collapse" data-target="#navbarNav">menu</i>
            </span> -->
            <input type="text" name="keyword" id="search-keyword-input" class="form-control" placeholder="书名/作者/出版社"
                   aria-describedby="search-input-addon">
            <span
                    class="input-group-addon" onclick="javascript:searchByKeyword();" id="search-input-addon">
					<i class="nav-link-icon material-icons">search</i>
				</span>
        </div>
        <%-- <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <c:forEach items="${productTypeList }" var="productType">
                    <li class="nav-item">
                        <a class="nav-link" href="#">${productType.title }</a>
                    </li>
                </c:forEach>
            </ul>
        </div> --%>
    </form>
</nav>

<div class="container">

    <!-- 底部导航按钮 -->
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container" style="padding: 0px;">
            <ul class="nav nav-pills nav-fill">
                <li class="nav-item">
                    <a class="nav-link active" href="User_showIndex">
                        <i class="nav-link-icon material-icons">home</i>
                        <div class="nav-item-text">主页</div>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="User_showFavorite">
                        <i class="nav-link-icon material-icons">favorite</i>
                        <div class="nav-item-text">我的收藏</div>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="User_showCart">
                        <i class="nav-link-icon material-icons">shopping_cart</i>
                        <div class="nav-item-text">借阅车</div>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="User_showBorrowed">
                        <i class="nav-link-icon material-icons">shopping_basket</i>
                        <div class="nav-item-text">已借阅</div>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="User_showUserCenter">
                        <i class="nav-link-icon material-icons">person</i>
                        <div class="nav-item-text">个人中心</div>
                    </a></li>
            </ul>
        </div>
    </nav>

    <!-- 首页轮播图 -->
    <div style="margin: 5px 10px 0px 10px;" id="carouselIndicators" class="carousel slide"
         data-ride="carousel">
        <ol class="carousel-indicators">
            <c:forEach items="${naviList }" var="navi" varStatus="stauts">
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
            <c:forEach items="${naviList }" var="navi" varStatus="stauts">
                <c:choose>
                    <c:when test="${stauts.index=='0' }">
                        <div class="carousel-item active">
                            <a href="Topic_show.action?topicId=${navi.topic.id }">
                                <img style="border-radius: 5px 5px 0 0;" class="d-block img-fluid"
                                     src="${navi.background }" alt="First slide">
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="carousel-item">
                            <a href="Topic_show.action?topicId=${navi.topic.id }">
                                <img style="border-radius: 5px 5px 0 0" class="d-block img-fluid"
                                     src="${navi.background }" alt="First slide">
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <img width="100%" src="images/index_com_info.png">

        <a class="carousel-control-prev" href="#carouselIndicators"
           role="button" data-slide="prev"> <span
                class="carousel-control-prev-icon" aria-hidden="true"></span> <span
                class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <div class="container">
        <c:forEach items="${homeClassZoneList }" var="homeClassZone">
            <div class="home-class-zone-title">${homeClassZone.name }</div>
            <!-- <span class="badge badge-pill badge-success zone_badge">${homeClassZone.name }</span> -->
            <div class="row" style="margin: 0px">
                <c:forEach items="${homeClassZone.homeClassifies }"
                           var="homeClassify">
                    <div
                            class="card home-classify-card col-4"
                            style="margin: 10px 0 5px 0; float: center; text-align: center;"
                            onclick="javascript:window.location.href='HomeClassify_show.action?homeClassifyId=${homeClassify.id }';">
                        <img style="margin:0 auto;" class="card-img-top home-classify-img"
                             src="${homeClassify.cover }" alt="${homeClassify.name }">
                        <!-- <div class="card-block">
								<div class="card-text home-classify-text">${homeClassify.name }</div>
							</div> -->
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <div class="container" style="margin-bottom: 80px;">
        <c:forEach items="${homeZoneList }" var="homeZone">
            <div class="home-zone-title">${homeZone.name }</div>
            <%--<span class="badge badge-primary zone_badge">${homeZone.name }</span>--%>
            <div class="mdl-grid">
                <c:forEach items="${homeZone.products }" var="product">
                    <div class="card mdl-cell mdl-cell--3-col mdl-cell--4-col-tablet mdl-cell--2-col-phone">
                        <img class="card-img-top home-item-card-img"
                             src="${product.covers[0] }"
                             alt="${product.name }"
                             onclick="javascript:window.location.href='Product_show.action?productId=${product.id }';">
                        <div class="card-block home-item-card-block">
                            <p class="card-text home-item-card-text">
                            <div style="padding-left: 15px;">
                                <c:choose>
                                    <c:when test="${product.type==202 }">
                                        <img src="${pageContext.request.contextPath}/images/icon/robot.svg" alt="">
                                    </c:when>
                                    <c:when test="${product.type==203 }">
                                        <img src="${pageContext.request.contextPath}/images/icon/pen.svg" alt="">
                                    </c:when>
                                    <c:when test="${product.type==204 }">
                                        <img src="${pageContext.request.contextPath}/images/icon/robot.svg" alt="">
                                        <img src="${pageContext.request.contextPath}/images/icon/pen.svg" alt="">
                                    </c:when>
                                </c:choose>
                                <div class="product-name-text">
                                        ${product.name }
                                </div>
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
                                <li id="md-li-favorite-${product.id }" class="nav-item"
                                    onclick="javascript:removeFromFavorite(${product.id });">
                                    <i id="md-icon-favorite-${product.id }"
                                       class="nav-link-icon material-icons home-item-card-action">favorite</i>
                                </li>
                                <%
                                } else {
                                %>
                                <li id="md-li-favorite-${product.id }" class="nav-item"
                                    onclick="javascript:addToFavorite(${product.id });">
                                    <i id="md-icon-favorite-${product.id }"
                                       class="nav-link-icon material-icons home-item-card-action">favorite_border</i>
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
                                <li id="md-li-cart-${product.id }" class="nav-item"
                                    onclick="javascript:removeFromCart(${product.id });">
                                    <i id="md-icon-cart-${product.id }"
                                       class="nav-link-icon material-icons home-item-card-action">shopping_cart</i>
                                </li>
                                <%
                                } else {
                                %>
                                <li id="md-li-cart-${product.id }" class="nav-item"
                                    onclick="javascript:addToCart(${product.id });">
                                    <i id="md-icon-cart-${product.id }"
                                       class="nav-link-icon material-icons home-item-card-action">add_shopping_cart</i>
                                </li>
                                <%
                                    }
                                %>

                                    <%-- <li class="nav-item" onclick="javascript:addToCart(${homeItem.product.id });">
                                        <i id="md-icon-cart-${homeItem.product.id }" class="nav-link-icon material-icons home-item-card-action">add_shopping_cart</i>
                                    </li> --%>
                            </ul>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>

<script>

    function addToFavorite(productId) {
        $.post("Product_addToFavorite.action", {productId: productId},
            function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $("#md-icon-favorite-" + productId).empty();
                    $("#md-icon-favorite-" + productId).append("favorite");
                    $("#md-li-favorite-" + productId).attr('onclick', 'javascript:removeFromFavorite(' + productId + ')');
                    showMsg('已加入收藏');
                }
            }
        );
    }

    function addToCart(productId) {
        $.post("Product_addToCart.action", {productId: productId},
            function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $("#md-icon-cart-" + productId).empty();
                    $("#md-icon-cart-" + productId).append("shopping_cart");
                    $("#md-li-cart-" + productId).attr('onclick', 'javascript:removeFromCart(' + productId + ')');
                    showMsg('已加入借阅');
                }
            }
        );
    }

    function removeFromFavorite(productId) {
        $.post("Product_removeFromFavorite.action", {productId: productId},
            function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $("#md-icon-favorite-" + productId).empty();
                    $("#md-icon-favorite-" + productId).append("favorite_border");
                    $("#md-li-favorite-" + productId).attr('onclick', 'javascript:addToFavorite(' + productId + ')');
                    showMsg('已取消收藏');
                }
            }
        );
    }

    function removeFromCart(productId) {
        $.post("Product_removeFromCart.action", {productId: productId},
            function (result) {
                var result = eval('(' + result + ')');
                if (result.success) {
                    $("#md-icon-cart-" + productId).empty();
                    $("#md-icon-cart-" + productId).append("add_shopping_cart");
                    $("#md-li-cart-" + productId).attr('onclick', 'javascript:addToCart(' + productId + ')');
                    showMsg('已取消借阅');
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
