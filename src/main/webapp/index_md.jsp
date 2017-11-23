<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A portfolio template that uses Material Design Lite.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>${webpagename }</title>
    
	<link rel="stylesheet" href="${pageContext.request.contextPath}/mdl/material.min.css">
	<script src="${pageContext.request.contextPath}/mdl/material.min.js"></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
	<link rel="stylesheet" href="styles.css" />
	<link rel="stylesheet" href="css/carousel.css" />
	
	<script type="text/javascript" src="lib/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script>

    <%
/*    		if(session.getAttribute(Constant.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		}  */
	%>
	
	<style>
		.home-item-action > .mdl-card__actions {
			height: 52px;
			padding: 16px;
			background: rgba(0, 0, 0, 0.2);
		}
	</style>
</head>

<body>
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
		<header class="mdl-layout__header mdl-layout__header--waterfall home-header">
            <div class="mdl-layout__header-row wx-headimg-row">
                <span class="mdl-layout__title">
                	<a href="user.jsp">
	                    <img class="wx-headimg" alt="" src="${currentUser.headimgurl }"></img>
	                    <span class="mdl-layout__title">${currentUser.nickname }</span>
                	</a>
                </span>
            </div>
            <div class="mdl-layout__header-row index-navigation-row"><!-- mdl-layout--large-screen-only -->
                <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
                    <a class="mdl-navigation__link is-active" href="index.jsp">主页</a>
                    <a class="mdl-navigation__link" href="cart.jsp">借阅车</a>
                    <a class="mdl-navigation__link" href="favorite.jsp">收藏</a>
<!--                     <a class="mdl-navigation__link" href="contact.html">个人中心</a> -->
                </nav>
            </div>
        </header>
<!--         <div class="mdl-layout__drawer mdl-layout--small-screen-only">
            <nav class="mdl-navigation mdl-typography--body-1-force-preferred-font">
                <a class="mdl-navigation__link is-active" href="index.html">主页</a>
                <a class="mdl-navigation__link" href="blog.html">借阅车</a>
                <a class="mdl-navigation__link" href="about.html">收藏</a>
                <a class="mdl-navigation__link" href="contact.html">个人中心</a>
            </nav>
        </div> -->
        <main class="mdl-layout__content">
        	<div class="main_visual">
                <div class="flicking_con">
                	<div class="flicking_inner">
	                    <c:forEach items="${naviList }" var="navi">
							<li>
								<a href="Topic_show.action?topicId=${navi.topic.id }"></a>
							</li>
						</c:forEach>
	               	 </div>
	            </div>
				<div class="main_image">
					<ul>
	                    <c:forEach items="${naviList }" var="navi">
							<li>
								<a href="Topic_show.action?topicId=${navi.topic.id }">
									<img src="${pageContext.request.contextPath}/${navi.background }"
										class="img">
								</a>
							</li>
						</c:forEach>
					</ul>
					<a href="javascript:;" id="btn_prev"></a>
					<a href="javascript:;" id="btn_next"></a>
				</div>
			</div>
        	<c:forEach items="${homeClassZoneList }" var="homeClassZone">
				<div class="mdl-grid homezone-max-width">
		            <h6 style="margin-left: 16px;" class="mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
		            	${homeClassZone.name }
					</h6>
					<c:forEach items="${homeClassZone.homeClassifies }" var="homeClassify">
		                <div class="mdl-cell mdl-card mdl-shadow--4dp home-item-card
		                        mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--2-col-phone">
		                    <a href="HomeClassify_show.action?homeClassifyId=${homeClassify.id }">
			                    <div class="mdl-card__media" style="background: #FFFFFF;">
			                        <img class="article-image" src="${homeClassify.cover }" border="0" alt="">
			                    </div>
			                    <div class="mdl-card__title">
			                        <h6>${homeClassify.name }</h6>
			                    </div>
     		                </a>
		                </div>
					</c:forEach>
				</div>
			</c:forEach>
        	<c:forEach items="${homeZoneList }" var="homeZone">
	        	<div class="mdl-grid homezone-max-width">
		            <h6 style="margin-left: 16px;" class="mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
		            	${homeZone.name }
					</h6>
	            	<c:forEach items="${homeZone.homeItems }" var="homeItem">
		                <div class="mdl-cell mdl-card mdl-shadow--4dp home-item-card
		                        mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--2-col-phone"
		                        onclick="javascript:window.location.href='Book_show.action?bookId=${homeItem.book.id }';">
		                    <div class="mdl-card__media" style="padding: 10px; margin: 0; background: #FFFFFF;">
		                        <img class="article-image" src="${homeItem.book.cover }" border="0" alt="">
		                    </div>
		                    <div style="bottom: 0px; margin: 0px; padding: 0 10px 0 10px;">
			                    <div style="font-size: 14px;">${homeItem.book.name }</div>
			                    <div class="home-item-action mdl-card__actions mdl-card--border mdl-grid">
				                    <span style="text-align: left;" class="mdl-cell
										mdl-cell--6-col mdl-cell--4-col-tablet mdl-cell--2-col-phone">
										<a href="">	
											<i class="material-icons">favorite_border</i>
										</a>
									</span>
									<span style="text-align: right;" class="mdl-cell
										mdl-cell--6-col mdl-cell--4-col-tablet mdl-cell--2-col-phone">
										<a href="">
											<i class="material-icons">add_shopping_cart</i>
										</a>
									</span>
			                    </div>
		                    </div>
		                </div>
					</c:forEach>
				</div>
            </c:forEach>
            <footer style="margin: 0px;" class="mdl-mini-footer">
                <div class="mdl-mini-footer__left-section">
                    <div class="mdl-logo">Simple portfolio website</div>
                </div>
                <div class="mdl-mini-footer__right-section">
                    <ul class="mdl-mini-footer__link-list">
                        <li><a href="#">Help</a></li>
                        <li><a href="#">Privacy & Terms</a></li>
                    </ul>
                </div>
            </footer>
        </main>
    </div>
</body>
</html>
