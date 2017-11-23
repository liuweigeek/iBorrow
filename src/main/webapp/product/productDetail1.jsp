<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>商品详情</title>
<meta name="Keywords" content="">
<meta name="Description" content="">
<!-- 移动设备支持 -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />

    <link href="book/css/mall.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="book/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="book/js/jquery.Spinner.js"></script>
    
	<link rel="stylesheet" href="${pageContext.request.contextPath}/mdl/material.min.css">
	<script src="${pageContext.request.contextPath}/mdl/material.min.js"></script>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
	
	
</head>

<body class="body_color">
<div class="mall_main">
    <div id="child_header">
        <div class="goback"><a href="javascript:history.back(-1)"><i></i></a></div>
        <div class="current_location"><span>商品详情</span></div>
    </div>

	<div id="banner_box" class="box_swipe">
		<ul>
			<li><img src="${pageContext.request.contextPath}/${book.cover }"></li>
			<li><img src="${pageContext.request.contextPath}/${book.cover }"></li>
			<li><img src="${pageContext.request.contextPath}/${book.cover }"></li>
			<li><img src="${pageContext.request.contextPath}/${book.cover }"></li>
		</ul>
		<ol>
			<li class="on"></li>&nbsp;
			<li></li>&nbsp;
			<li></li>&nbsp;
			<li></li>&nbsp;
		</ol>
	</div>
    <a class="praise_icon" href="#">2658</a>
    <div class="des_goods">
        <p>《${book.name }》</p>
        <p><span class="sp_style1">${book.publisher }</span><em class="em_integral">剩余：${book.remainder }</em></p>
        <p><span class="pr">价格</span><span class="sp_style2">¥${book.purchasePrice }</span></p>
        <p><span class="pr">编号</span><span class="sp_style3">${book.number }</span></p>
    </div>

    <div class="box_list">
        <ul class="box_nav">
            <li class="current"><a>书籍介绍</a></li>
            <li><a>评论(230)</a></li>
        </ul>
        <div class="goods_box">
        	<div style="padding: 10px;">
				${book.introduction }
			</div>
        </div>
        <div class="goods_box" style="display: none">
            <h3>累计评价(25)：</h3>
            <div class="evaluate">
                <span>18762678928</span><span class="text_t">2015-10-19</span>
                <p>速度快，服务周到</p>
                <hr/>
            </div>
            <div class="evaluate">
                <span>18762678928</span><span class="text_t">2015-10-19</span>
                <p>速度快，服务周到速度快，服务周到速度快，服务周到速度快，服务周到速度快，服务周到</p>
                <hr/>
            </div>
            <div class="evaluate">
                <span>18762678928</span><span class="text_t">2015-10-19</span>
                <p>速度快，服务周到</p>
                <hr/>
            </div>
        </div>
    </div>
    <div class="bottomdiv clearfix">
        <div class="inner clearfix">
            <div class="fl btn_sure">
                <a href="">收藏</a>
            </div>
            <div class="fl btn_buy_detail">
                <a class="addshop_cat">加入订阅车</a>
            </div>
        </div>

    </div>
</div>
<script src="book/js/swipe2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        new Swipe(document.getElementById('banner_box'), {
            speed: 500,
            auto: 3000,
            callback: function(){
                var lis = $(this.element).next("ol").children();
                lis.removeClass("on").eq(this.index).addClass("on");
            }
        });
    });
</script>
<script type="text/javascript">
    $(function(){
        $(".spec_select ul li em").click(function(){
            $(this).addClass("click").siblings().removeClass("click");
        })
    })
</script>
<!--商品数量加减-->
<script type="text/javascript">
    $(function(){
        $("#a").Spinner({value:1, min:0, len:3, max:10000});
    });
</script>
<script type="text/javascript">
    jQuery(function($){
        $('.box_list ul li').click(function(){
            var index = $('.box_list ul li').index(this);
            $(this).addClass('current').siblings('li').removeClass('current');
            $('.box_list .goods_box:eq('+index+')').show().siblings('.goods_box').hide();
        })
    })
</script>
</body>
</html>