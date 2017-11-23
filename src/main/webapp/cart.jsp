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
	<link rel="stylesheet" href="cart.css" />

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
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<h1 class="navbar-brand mb-0 navbar-title">我的借阅车</h1>
	</nav>
	
    <div class="container">

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
					<li class="nav-item"><a class="nav-link active" href="User_showCart">
							<i class="nav-link-icon material-icons">shopping_cart</i>
							<div class="nav-item-text">借阅车</div>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="User_showBorrowed">
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

		<div class="container" style="margin-bottom: 120px;">

			<!-- <div id="borrow-faild-alert" class="alert alert-warning alert-dismissible fade show" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<strong>借阅失败</strong>
				<span id="borrow-faild-alert-msg"></span>
			</div> -->

			<div class="list-group">
				<c:forEach items="${cartList }" var="cart">
					<!-- 列表项 -->
					<div id="list-group-item-${cart.id }" class="list-group-item mdl-grid">
						<div class="form-check mdl-cell mdl-cell--9-col mdl-cell--6-col-tablet mdl-cell--3-col-phone">
							<label class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input">
								<input type="hidden" value="${cart.id }">
								<span class="custom-control-indicator"></span>
								<div class="media">
									<img class="d-flex align-self-center mr-3 cart-item-product-cover" src="${cart.covers[0] }" alt=""
										 onclick="javascript:window.location.href='Product_show.action?productId=${cart.id }';">
									<div class="media-body">
										<h6 class="mt-0">${cart.name }</h6>
											剩余库存:${cart.remainder }
									</div>
								</div>
							</label>
						</div>
						<!-- 列表项操作控件组 -->
						<div class="cart-item-product-tool mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--1-col-phone">
							<!-- 删除标签 -->
							<div style="color: #0275d8;" onclick="javascript:removeFromCart(${cart.id });">
								<i class="material-icons mt-0">remove_shopping_cart</i><br/>
							</div>
							<!-- 修改数量控件组 -->
	 						<div class="input-group cart-item-product-quantity mt-0">
								<span class="input-group-btn">
									<button class="btn btn-sm btn-secondary" type="button"
											onclick="javascript:reduceCount(${cart.id});">-</button>
								</span>
								<span class="input-group-btn input-group-count">
									<c:choose>
										<c:when test="${cart.remainder > 0}">
											<button id="product-count-${cart.id }" class="product-count btn btn-sm btn-secondary"
												disabled="disabled" value="1" type="button">1</button>
										</c:when>
										<c:otherwise>
											<button id="product-count-${cart.id }" class="product-count btn btn-sm btn-secondary"
												disabled="disabled" value="1" type="button">0</button>
										</c:otherwise>
									</c:choose>
								</span>
								<!-- <input type="number" class="form-control" maxlength="1" value="1"> -->
								<span class="input-group-btn">
									<button class="btn btn-sm btn-secondary" type="button"
											onclick="javascript:addCount(${cart.id}, ${cart.remainder });">+</button>
								</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
    </div>

	<div class="modal fade" id="addPhoneModal" tabindex="-1" role="dialog" aria-labelledby="addPhoneModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form action="User_addPhoneAndEmail.action" onsubmit="return checkPhoneAndEmailParams();" method="post">
					<div class="modal-header">
						<h5 class="modal-title" id="addPhoneModalLabel">补充信息</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="phone" class="form-control-label">联系电话:</label>
							<input type="tel" class="form-control" name="phone" id="phone">
						</div>
						<div class="form-group">
							<label for="email" class="form-control-label">电子邮箱:</label>
							<input type="email" class="form-control" name="email" id="email">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<button type="button" id="add-to-order-button" class="btn btn-primary"
			onclick="javascript:preAddToOrder();">一键借阅</button>

	<form id="queryOrder_form" action="Order_queryOrder.action" method="post">
		<input type="hidden" id="queryOrder_ids" name="ids">
		<input type="hidden" id="queryOrder_counts" name="counts">
	</form>

    <script>
    
    	/* function closeAlert() {
    		$("#borrow-faild-alert").alert('close');
    	} */
    	
    	function removeFromCart(productId) {
			/* closeAlert(); */
			$.post("Product_removeFromCart.action", {productId: productId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#list-group-item-" + productId).remove();
					}
				}
			);
		}

		function addCount(id, remainder) {
			/* closeAlert(); */
			var element = $("#product-count-" + id);
			var currentCount = parseInt(element.text());
			if (currentCount < remainder) {
				element.text(currentCount+1);
				element.val(currentCount+1);
			}
		}
	
		function reduceCount(id) {
			/* closeAlert(); */
			var element = $("#product-count-" + id);
			var currentCount = parseInt(element.text());
			if (currentCount > 1) {
				element.text(currentCount-1);
				element.val(currentCount-1);
			}
		}
		
		function preAddToOrder() {
			/*checkHasPhone();*/
            var selectedSpan=$(":checked").next("input");
            if(selectedSpan.length<=0){
                alert("请选择要借阅的书籍！");
                return;
            }

            $.post("User_checkAddToCart.action",
                {},
                function(result){
                    if(result.success){
                        checkRemainAndValid();
                    }else{
                        switch (result.errType) {
                            case 1:
                                $('#addPhoneModal').modal('show');
                                break;
                            case 2:
                                window.location.href='VipCard_showlist';
                                break;
                            case 3:
                                alert(result.errMsg);
                                break;
                        }
                    }
                },
                "json"
            );
		}
		
		function checkHasPhone() {
			$.post("User_hasPhoneNum.action",
				{},
				function(result){
					if(result.success){
						checkIsVip();
					}else{
						$('#addPhoneModal').modal('show');
					}
				},
				"json"
			);
		}
		
		function checkIsVip() {
			$.post("User_isVip.action",
				{},
				function(result){
					if(result.success){
						checkDeposit();
					}else{
						window.location.href='VipCard_showlist';
					}
				},
				"json"
			);
		}
		
		function checkDeposit() {
			$.post("User_checkDeposit.action",
				{},
				function(result){
					if(result.success){
						checkRemainAndValid();
					}else{
						alert('押金不足300，请前往个人中心页面充值押金');
					}
				},
				"json"
			);
		}

		function checkRemainAndValid() {
			var selectedSpan=$(":checked").next("input");
			if(selectedSpan.length<=0){
				alert("请选择要借阅的书籍！");
				return;
			}
			var strIds=[];
			for(var i=0;i<selectedSpan.length;i++){
				strIds.push(selectedSpan[i].value);
			}
			var ids=strIds.join(",");
			
			var selectedSpanCount = $(":checked").parent().parent().next()
									.children(".cart-item-product-quantity")
									.children(".input-group-count").children("button");
			var strCounts=[];
			for(var i=0;i<selectedSpanCount.length;i++){
				strCounts.push(selectedSpanCount[i].value);
			}
			var counts=strCounts.join(",");

			$.post("Order_checkRemainAndValid.action",
				{ids: ids, counts: counts},
				function(result){
					if(result.success){
						addToOrder(ids, counts)
					}else{
						alert(result.errorMsg);
					}
				},
				"json"
			);
		}

		function addToOrder(ids, counts) {
			$("#queryOrder_ids").val(ids);
			$("#queryOrder_counts").val(counts);
			$("#queryOrder_form").submit();
		}

        function checkPhoneAndEmailParams() {
            var phone = $("#phone").val();
            var email = $("#email").val();
            if (!isPhoneNo(phone)) {
                alert("请输入有效的联系电话");
                return false;
            }
            if (!isEmail(email)) {
                alert("请输入有效的电子邮箱");
                return false;
            }

            return true;
        }

        function isPhoneNo(phone) {
            var pattern = /^1[34578]\d{9}$/;
            return pattern.test(phone);
        }

        function isEmail(email) {
            var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            return pattern.test(email);
        }

    </script>
</body>
</html>
