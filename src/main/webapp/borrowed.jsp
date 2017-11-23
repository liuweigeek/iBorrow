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
	<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="borrowed.css" />

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
	
		var currentOrderId;

    	function confirmReceived(orderId) {
    		currentOrderId = orderId;
    		$("#received-modal").modal('show');
    		
    	}
    	
    	function received() {
    		if (currentOrderId != null) {
    			var damage = $("#order-receive-damage").val();
    			var checkIntact = $("#order-receive-intact").val();
    			$.post("Order_userReceived.action",
					{orderId: currentOrderId,
					damage: damage,
					checkIntact: checkIntact}, 
					function(result){
						currentOrderId = null;
						var result = eval('('+result+')');
						if (result.success) {
							window.location.reload(true);
						} else {
							console.log(result);
						}
					}
				);
    		} else {
    			console.log('currentOrderId is ' + currentOrderId);
    		}
    	}
    	
    	function confrimRefund() {
    		$("#refund-modal").modal('show');
    	}
    	
    	function refund() {
    		var selectedSpan=$(":checked").next("input");
    		if(selectedSpan.length==0){
				alert("请选择要退还的书籍！");
				return;
			}
			var strIds=[];
			for(var i=0;i<selectedSpan.length;i++){
				strIds.push(selectedSpan[i].value);
			}
			var ids=strIds.join(",");

			$.post("Order_refund.action",
				{ids: ids},
				function(result){
					$("#refund-modal").modal('hide');
					if(result.success){
						window.location.reload(true);
					}else{
						console.log(result);
					}
				},
				"json"
			);
    	}
	
	</script>
	
</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<h1 class="navbar-brand mb-0 navbar-title">已借阅书籍</h1>
	</nav>
	
    <div class="container" style="margin-bottom: 120px;">

		<!-- 底部导航按钮 -->
		<nav class="navbar navbar-default navbar-fixed-bottom">
			<div class="container" style="padding: 0px;">
				<ul class="nav nav-pills nav-fill">
					<li class="nav-item"><a class="nav-link"
						href="User_showIndex"> <i class="nav-link-icon material-icons">home</i>
							<div class="nav-item-text">主页</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showFavorite">
							<i class="nav-link-icon material-icons">favorite</i> <div
							class="nav-item-text">我的收藏</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showCart">
							<i class="nav-link-icon material-icons">shopping_cart</i>
							<div class="nav-item-text">借阅车</div>
					</a></li>
					<li class="nav-item"><a class="nav-link active" href="User_showBorrowed">
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

		<div class="container" style="margin-bottom: 100px;">

			<!-- <div id="borrow-faild-alert" class="alert alert-warning alert-dismissible fade show" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>借阅失败</strong>
				<span id="borrow-faild-alert-msg"></span>
			</div> -->

			<c:forEach items="${borrowedList }" var="order">
				<c:choose>
					<c:when test="${order.status==401}">
						<span class="badge badge-primary order-badge">
							未支付
						</span>
					</c:when>
					<c:when test="${order.status==402}">
						<span class="badge badge-primary order-badge">
							未发货
						</span>
					</c:when>
					<c:when test="${order.status==403}">
						<span class="badge badge-primary order-badge">
							已收货
						</span>
					</c:when>
					<c:when test="${order.status==404}">
						<span class="badge badge-primary order-badge">
							已发货
						</span>
					</c:when>
					<c:when test="${order.status==405}">
						<span class="badge badge-primary order-badge">
							已完成订单
						</span>
					</c:when>
					<c:otherwise>
						<span class="badge badge-primary order-badge">
							未知状态订单
						</span>
					</c:otherwise>
				</c:choose>

				<ul class="list-unstyled list-group">
					<c:forEach items="${order.orderItems }" var="orderItem">
						<li id="product-list" class="media list-group-item">
							<label class="custom-control custom-checkbox">
								<c:choose>
									<c:when test="${order.status==403 && !orderItem.returned}">
										<input type="checkbox" class="custom-control-input">
									</c:when>
									<c:otherwise>
										<input type="checkbox" disabled class="custom-control-input">
									</c:otherwise>
								</c:choose>
								<input type="hidden" value="${orderItem.id }">
								<span class="custom-control-indicator"></span>
								<img class="d-flex mr-3 product-item-cover"
										src="${orderItem.product.covers[0] }" alt="">
								<div class="media-body">
									<h5 class="mt-0 mb-1">『${orderItem.product.name }』</h5>
									${orderItem.product.manufacturer } ${orderItem.product.author }
								</div>
							</label>
						</li>
					</c:forEach>
				</ul>
				<c:choose>
					<c:when test="${order.status==404}">
						<div class="btn-toolbar justify-content-between" aria-label="">
							<div></div>
							<button type="button" style="margin-top: 5px; margin-right: 20px;" class="btn btn-outline-primary btn-sm"
								onclick="javascript:confirmReceived(${order.id});">确认收货</button>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>
    </div>

	<div class="modal" id="received-modal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">确认收货</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						确认已收到货？
						<div class="form-group">
							<label class="form-control-label" for="order-receive-damage">书籍损坏信息</label>
							<input type="text" class="form-control form-control-primary"
									id="order-receive-damage" name="damage">
							<div class="form-control-primary">请输入损坏详情</div>
							<small class="form-text text-muted">如无损坏可不填写</small>
						</div>

						<label class="custom-control custom-checkbox">						
							<input type="checkbox" value="1" onclick="this.value=(this.value==0)?1:0"
								class="custom-control-input" id="order-receive-intact" name="checkIntact">
							<span class="custom-control-indicator"></span>
							<span class="custom-control-description">对订单满意</span>
						</label>
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="javascript:received();">确认</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="refund-modal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">确认还书</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						确认归还选中书籍？
						<small>
							如需归还图书，请到线下门店或者通过快递邮寄<br>
							收货地址：山西省太原市小店区长治路百万庄园综合楼2号<br>
							收件人：李国栋<br>
							联系电话：15303513351<br>
							邮政编码：030000
						</small>
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="javascript:refund();">确认</button>
				</div>
			</div>
		</div>
	</div>

    <button type="button" id="return-order-button" class="btn btn-primary"
		onclick="javascript:confrimRefund();">一键还书</button>

</body>
</html>
