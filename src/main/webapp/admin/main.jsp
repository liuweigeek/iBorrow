<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>${webpagename }后台管理</title>

	<!-- Add to homescreen for Chrome on Android -->
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="icon" sizes="192x192" href="${pageContext.request.contextPath}/admin/images/android-desktop.png">

	<!-- Add to homescreen for Safari on iOS -->
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="Material Design Lite">
	<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/admin/images/ios-desktop.png">

	<!-- Tile icon for Win8 (144x144 + tile color) -->
	<meta name="msapplication-TileImage" content="admin/images/touch/ms-touch-icon-144x144-precomposed.png">
	<meta name="msapplication-TileColor" content="#3372DF">

	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
	<link rel="shortcut icon" href="../admin/images/favicon.ico">

	<%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
	<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/bootstrap4-btn.css" rel="stylesheet">
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/homeclassify/classifyList.css" />--%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/mdl/material.min.css">
	<script src="${pageContext.request.contextPath}/mdl/material.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
	<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->
	<%--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">--%>
	<!--     <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.cyan-light_blue.min.css"> -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/styles.css">

	<style>

		a{
			text-decoration:none!important;
		}

		#main-table {
			overflow-x: scroll;
		}

		.pagination {
			margin: 0 auto 15px;
			left: 0;
			right: 0;
			position: fixed;
			bottom: 0;
		}

	</style>

	<script>

        $(function(){
            $('#admin-search-keyword').bind('keypress',function(event){
                if(event.keyCode == "13") {
                    $('#admin-search-form').submit();
                }
            });
        });

	</script>

	<%
		if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
			response.sendRedirect("admin.action");
			return;
		}
	%>

</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
	<header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
		<div class="mdl-layout__header-row">
			<span id="content-header-title" class="mdl-layout-title"></span>
			<div class="mdl-layout-spacer"></div>
			<form id="admin-search-form" action="#" onsubmit="return searchCheck()" method="post">
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
					<input type="button" class="mbtn mbtn-outline-info mbtn-sm" value="时间范围" id="date-picker" hidden="hidden">
					<label class="mdl-button mdl-js-button mdl-button--icon" for="admin-search-keyword">
						<i class="material-icons">search</i>
					</label>
					<div class="mdl-textfield__expandable-holder">
						<input class="mdl-textfield__input" type="text" id="admin-search-keyword">
						<label class="mdl-textfield__label" for="admin-search-keyword">请输入搜索内容...</label>
					</div>
				</div>
			</form>
			<%--<ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="reservation">
				<li class="mdl-menu__item">关于</li>
				<li class="mdl-menu__item">联系方式</li>
				<li class="mdl-menu__item">服务支持</li>
			</ul>--%>
		</div>
	</header>
	<div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
		<header class="demo-drawer-header">
			<img src="${adminHeadImg}" class="demo-avatar">
			<div class="demo-avatar-dropdown">
				<span>${adminNickname}</span>
				<div class="mdl-layout-spacer"></div>
				<button id="accbtn" class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon">
					<i class="material-icons" role="presentation">arrow_drop_down</i>
					<span class="visuallyhidden">Accounts</span>
				</button>
				<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
					<li class="mdl-menu__item">${adminPhone}</li>
					<%--<li class="mdl-menu__item">liuweigeek@outlook.com</li>
                    <li class="mdl-menu__item"><i class="material-icons">add</i>添加管理员</li>--%>
				</ul>
			</div>
		</header>
		<nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
			<a id="mdl-navigation__link--Home" class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">home</i>管理主页</a>
			<a id="mdl-navigation__link--Navi" class="mdl-navigation__link" href="Navi_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">wallpaper</i>轮播图</a>
			<a id="mdl-navigation__link--Topic" class="mdl-navigation__link" href="Topic_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">chrome_reader_mode</i>内容稿</a>
			<a id="mdl-navigation__link--HomeClassZone" class="mdl-navigation__link" href="HomeClassZone_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">label</i>首页大分类</a>
			<a id="mdl-navigation__link--HomeClassify" class="mdl-navigation__link" href="HomeClassify_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">bookmark</i>首页小分类</a>
			<%--<a class="mdl-navigation__link" href="HomeClassItem_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">book</i>小分类书籍</a>--%>
			<a id="mdl-navigation__link--HomeZone" class="mdl-navigation__link" href="HomeZone_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">bookmark_border</i>首页推荐分类</a>
			<%--<a class="mdl-navigation__link" href="HomeItem_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">collections_bookmark</i>首页推荐书籍</a>--%>
			<%--<a class="mdl-navigation__link" href="ProductType_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">label_outline</i>书籍分类</a>--%>
			<a id="mdl-navigation__link--Product" class="mdl-navigation__link" href="Product_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">library_books</i>书籍</a>
			<a id="mdl-navigation__link--Payment" class="mdl-navigation__link" href="Payment_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">attach_money</i>交易记录</a>
			<a id="mdl-navigation__link--Borrow_Order" class="mdl-navigation__link" href="Order_borrowList"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">description</i>借阅订单</a>
			<a id="mdl-navigation__link--Refund_Order" class="mdl-navigation__link" href="Order_refundList"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">assignment_return</i>归还订单</a>
			<a id="mdl-navigation__link--VipCard" class="mdl-navigation__link" href="VipCard_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">credit_card</i>会员卡</a>
			<a id="mdl-navigation__link--Wish" class="mdl-navigation__link" href="Wish_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">star</i>心愿书单</a>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">input</i>书籍入库记录</a>--%>
			<a id="mdl-navigation__link--User" class="mdl-navigation__link" href="User_list"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">people</i>用户</a>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">shopping_cart</i>用户借阅车</a>--%>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">reorder</i>用户订单</a>--%>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">message</i>用户通知</a>--%>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">comment</i>评论</a>--%>
			<%--<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">add_location</i>收货地址</a>--%>
			<div class="mdl-layout-spacer"></div>
			<a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">help_outline</i><span class="visuallyhidden">Help</span></a>
		</nav>
	</div>

	<main class="mdl-layout__content mdl-color--grey-100">
		<div class="mdl-grid demo-content">
			<jsp:include page="${mainPage }"></jsp:include>
			<nav>
				<ul class="pagination justify-content-center">
					${pageCode }
				</ul>
			</nav>
		</div>
	</main>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        if (!$('#date-picker').is(":hidden")) {
            $('#date-picker').daterangepicker(null, function(start, end, label) {
                console.log(start.toISOString(), end.toISOString(), label);
            });
        }
    });
</script>
</body>
</html>
