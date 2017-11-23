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
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">

	<style>
		#print {
			position: fixed;
			display: block;
			right: 0;
			bottom: 0;
			margin-right: 100px;
			margin-bottom: 100px;
			z-index: 900;
		}
	</style>
	
	<script>
		function doPrint() {
			bdhtml=window.document.body.innerHTML;
			sprnstr="<!--startprint-->";
			eprnstr="<!--endprint-->";
			prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr) );
			prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
			window.document.body.innerHTML=prnhtml;
			window.print();
		} 
	</script>

</head>

<body>
	
    <div class="container" style="width:794px; padding-top:20px;">
    	<!--startprint-->
    	<h4 style="text-align: center;">
    		<b>才智教育图书借阅订单</b>
    	</h4>
    	<table class="table table-bordered">
			<tbody>
				<tr>
					<th scope="row">客户姓名</th>
					<td>${order.user.nickname }</td>
					<th scope="row">订单编号</th>
					<td id="order-id"></td>
				</tr>
				<tr>
					<th scope="row">联系方式</th>
					<td>${order.user.phone }</td>
					<th scope="row">创建时间</th>
					<td>${order.createTime }</td>
				</tr>
				<tr>
					<th scope="row">收货地址</th>
					<td colspan="3">${order.userAddress.regionName } ${order.userAddress.address }</td>
				</tr>
			</tbody>
		</table>
		
		<table class="table table-bordered">
			<thead>
				<tr>
					<td colspan="4">（请务必认真阅读借阅须知哦）</td>
				</tr>
				<tr>
					<th>序号</th>
					<th>书籍编号（ISBN）</th>
					<th>书籍名称</th>
					<th>单价（元）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${order.orderItems }" var="orderItem" varStatus="status">
					<tr>
						<th scope="row">${status.index+1}</th>
						<td>${orderItem.product.number }</td>
						<td>${orderItem.product.name }</td>
						<td>${orderItem.product.salePrice }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td colspan="3">客户备注：</td>
				</tr>
				<tr>
					<td>配货人：</td>
					<td>检验人：</td>
					<td>发货人：</td>
				</tr>
			</tbody>
		</table>

		<div>
			<h5>借阅须知：</h5>
			<ol>
				<li>
					本馆以专业臭氧图书消毒剂执行消毒程序，请您和您的孩子放心阅读。
				</li>
				<li>
					请第一时间核对所借图书订单，检查书籍是否有毁损（如涂画、撕页、写字、污迹等），如果您发现图书破损或与描述不符等任何问题，请及时联系我们的客服热线15343510766。
				</li>
				<li>
					请根据宝宝的阅读需求选择合适的阅读计划借阅。如需帮助，请联系才智教育绘本阅读指导老师，我们将免费为您提供个性化阅读指导。
				</li>
				<li>
					全年不限次借阅服务（单次借阅不应超过5本，还旧借新），借阅无时限。会员若在借阅期内，发生图书毁损（如涂画、撕页、写字、污迹等）、遗失等情况，须按书籍原价的七折赔付，如因自身原因贻误赔付时间，会籍期限不能顺延。
				</li>
				<li>
					阅读结束后，请将绘本包装好，通过本馆指定快递公司寄回或送回。绘本馆根据顾客返回书籍包装情况，为顾客赠送积分。每完成一次牢固包装回邮，计10分，积分累计可换购相应产品或会期。如普通包装则不累计积分。
				</li>
			</ol>
			<small>
				注：如果您满意，请告诉您的朋友，如果您有任何意见和建议，欢迎告诉我们。祝您生活愉快~
			</small>
		</div>
    	<!--endprint-->
    </div>
    
    <button id="print" onclick="javascript:doPrint();"
    	type="button" class="btn btn-primary">打印</button>
    	
    	<script>

    		$(function () {
    			var orderId = parseInt(${order.id }).toString();
    			if(orderId.length < 10) {
    				var preLength = 10 - orderId.length;
    				for (var i = 0; i < preLength; i++) {
    					orderId = "0" + orderId;
    				}
    			}
    			$("#order-id").empty();
    			$("#order-id").append(orderId);
    		});
    	
    	</script>

</body>
</html>
