<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
	<html>
	  <head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>书籍介绍编辑</title>
		   
		<link rel="stylesheet" href="${pageContext.request.contextPath}/mdl/material.min.css">
		<script src="${pageContext.request.contextPath}/mdl/material.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
		<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>

		<style>
		.book-card-wide.mdl-card {
			margin: 30px;
			width: 80%;
		}
/* 		.topic-card-wide > .mdl-card__title {
			color: #fff;
			height: 176px;
			background: url('../images/gallery/1.jpg') center / cover;
		} */
		.book-card-wide > .mdl-card__menu {
			color: #fff;
		}
		</style>
		
		<script>
			function submit(){
				$("#editIntroduction_form").submit() ;
			}
		</script>
	</head>

	<body>
	
		<div class="mdl-layout mdl-js-layout">
			<header class="mdl-layout__header mdl-layout__header--scroll"  style="background: #37474F;">
				<div class="mdl-layout__header-row">
					<!-- Title -->
					<span class="mdl-layout-title">${book.name }</span>
					<!-- Add spacer, to align navigation to the right -->
					<div class="mdl-layout-spacer"></div>
				</div>
			</header>
			<main class="mdl-layout__content">
				<div class="page-content">
					<form id="editIntroduction_form" action="Book_saveIntroduction" method="post">
						<input type="hidden" name="id" value="${book.id }">
						<div class="book-card-wide mdl-cardmdl-shadow--2dp ">
							<div class="mdl-card__title">
		                        <h2 class="mdl-card__title-text">${book.name }</h2>
							</div>
							<div class="mdl-card__supporting-text">
								<textarea name="introduction" class="ckeditor" cols="50" style="height:200px;width: 800px;" ></textarea>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
									href="javascript:submit()">
									提交内容
								</a>
							</div>
						</div>
					</form>
				</div>
			</main>
		</div>
	</body>
</html>
