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

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
	<!-- <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

	<link rel="stylesheet" href="style.css" />
	<link rel="stylesheet" href="user.css" />

	<!-- <script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->

    <%
    	if(session.getAttribute(Constant.key_value.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		}
	%>
	
</head>

<body>
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
				<li class="nav-item"><a class="nav-link" href="User_showBorrowed">
						<i class="nav-link-icon material-icons">shopping_basket</i>
						<div class="nav-item-text">已借阅</div>
				</a></li>
				<li class="nav-item"><a class="nav-link active" href="User_showUserCenter">
						<i class="nav-link-icon material-icons">person</i><div
						class="nav-item-text">个人中心</div>
				</a></li>
			</ul>
		</div>
	</nav>
	
	<!-- 头像面板区域 -->
	<div class="container">
		<!-- <div style="position: relative;">
			<figure class="figure">
				<img src="images/headimg.jpg" style="width: 400px; height: 240px;" class="figure-img img-fluid rounded headimg-bg" alt="headimg background">
				<figcaption class="figure-caption text-center">A Brilliant Diamond</figcaption>
			</figure>
			<img src="images/headimg.jpg" alt="" class="wx-headimg"></div>
		</div> -->
	
		<div style="position: relative;">
			<figure class="figure">
				<img src="${user.headimgurl }" style="width: 400px; height: 240px;" class="figure-img img-fluid rounded headimg-bg" alt="headimg background">
				<figcaption class="figure-caption rounded-circle text-center">${user.nickname }</figcaption>
			</figure>
			<img src="${user.headimgurl }" alt="" class="wx-headimg text-center"
					onclick="javascript:window.location.href='User_showUserInfo.action';"></div>
		</div>
	</div>
	
	<div class="container" style="margin-bottom: 80px;">
		<ul class="list-group">
			<li onclick="javascript:window.location.href='Order_showlist'" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #4BAF4F;">reorder</i>
				<span class="list-group-text">借阅记录</span>
			</li>
			<li onclick="javascript:window.location.href='UserAddress_showList'" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #2196F3;">location_on</i>
				<span class="list-group-text">收货地址</span>
			</li>
			<li class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #F44336;">star_half</i>
				<span class="list-group-text">我的积分</span>
				<span class="badge badge-default badge-pill" style="margin-left:15px;">${user.integral }分</span>
			</li>
			<li onclick="javascript:$('#rechargeDepositModal').modal('show');" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #FFC107;">attach_money</i>
				<span class="list-group-text">我的押金</span>
				<span class="badge badge-default badge-pill" style="margin-left:15px;">${user.deposit }元</span>
			</li>
			<li onclick="javascript:window.location.href='VipCard_showList'" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #795548;">assignment_ind</i>
				<span class="list-group-text">会员卡</span>
			</li>
			<li onclick="javascript:window.location.href='Payment_showList'" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #F2922B;">payment</i>
				<span class="list-group-text">交易记录</span>
			</li>
			<%--<li class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #BE46DC;">message</i>
				<span class="list-group-text">通知</span>
			</li>--%>
			<li onclick="javascript:$('#wishModal').modal('show');" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #B5E61D;">star</i>
				<span class="list-group-text">心愿书单</span>
			</li>
			<li onclick="javascript:$('#contactUsModal').modal('show');" class="list-group-item list-group-item-action">
				<i class="material-icons" style="color: #7092BE;">group</i>
				<span class="list-group-text">联系我们</span>
			</li>
		</ul>
	</div>
	
	<div class="modal fade" id="rechargeDepositModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="rechargeDepositModalLabel">充值押金</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="deposit-num" class="form-control-label">充值金额(元):</label>
						<input type="number" class="form-control" id="deposit-num">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="preToRechargeDeposit();">提交</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="wishModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">心愿书单</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="wish-text" class="form-control-label">心愿书单的书名和出版社：</label>
						<input type="text" class="form-control" id="wish-text">
					</div>
					<div class="form-group">
						<label for="wish-phone" class="form-control-label">联系电话：</label>
						<input type="tel" class="form-control" id="wish-phone">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" onclick="addToWish();">提交</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="contactUsModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">联系我们</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						联系地址：山西省太原市小店区长治路百万庄园综合楼2号<br>
						电子邮箱：510963711@qq.com<br>
						联系电话：15343510766<br>
						微信：caizhijiaoyuty
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		
		function addToWish() {
			var wishText = $("#wish-text").val();
			var wishPhone = $("#wish-phone").val();
			$("#wishModal").modal('hide');
			$("#wish-text").val('');
			$("#wish-phone").val('');
			$.post("Wish_save.action",
				{wishText: wishText, wishPhone: wishPhone},
				function(result){
					if(result.success){
						alert('提交成功');
					}else{
						alert('提交失败');
					}
				},
				"json"
			);
		}
	
		function preToRechargeDeposit() {
            var money = $("#deposit-num").val();

            var pattern = /^\+?[1-9][0-9]*$/;
            if (!pattern.test(money)) {
                alert("请输入整数");
                return false;
            }

            if (money > 1000) {
            	alert("单次充值不可大于1000")；
            	return false;
            }

			rechargeDeposit(money, "押金充值");
		}
	
		function rechargeDeposit(money, bodyStr) {
			$.post("Payment_buy.action",
				{money: money, bodyStr: bodyStr, payType: 502},
				function(result){
					$('#rechargeDepositModal').modal('hide');
					var result = eval('('+result+')');
					if (result.success) {
						WeixinJSBridge.invoke(
							'getBrandWCPayRequest', {
								"appId" : result.appId,
								"timeStamp": result.timeStamp,
								"nonceStr" : result.nonceStr,
								"package" : result.package,
								"signType" : result.signType,
								"paySign" : result.paySign
							},
							function(res){
								// 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
								if(res.err_msg == "get_brand_wcpay_request:ok" ) {
									var tradeNum = result.out_trade_no;
									$.post("User_confirmRechargeDeposit.action",
										{depositNum: money, tradeNum: tradeNum}, 
										function(result){
											var result = eval('('+result+')');
											if (result.success) {
												refreshPage()
											}
										}
									);
								} else {
									alert('支付失败，请稍后重试');
								}
							}
						);
					} else {
					    if (result.errMsg) {
					        alert(result.errMsg);
						} else {
                            alert('暂时无法购买，请稍后重试');
						}
					}
				}
			);
		}
		
		function refreshPage() {
			window.location.reload(true);
		}
	
	</script>
	
</body>
</html>
