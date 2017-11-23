<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<!DOCTYPE html>
	<html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <title></title>

            <link href="${pageContext.request.contextPath}/css/bootstrap3.no_pagination.min.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/admin/datepicker/font-awesome.min.css" rel="stylesheet">
            <link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/admin/datepicker/daterangepicker-bs3.css" />
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/admin/datepicker/moment.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/admin/datepicker/daterangepicker.js"></script>

            <%--<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>--%>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text-color.css">
            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '时间范围') {
                        return false;
                    }
                    var timeranger = $('#date-picker').val();
                    var action;
                    if (timeranger && timeranger != "") {
                        action = 'Order_refundList?timeranger=' + timeranger + '&keyword=';
                    } else {
                        action = 'Order_refundList?keyword=';
                    }
                    $('#admin-search-form').attr('action', action + keyword);
                }

            </script>

            <style>

                .user-headimg-mini {
                    border-radius: 50%;
                    margin: auto;
                    height: 24px;
                    width: 24px;
                }

            </style>

            <%
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                }
            %>
	</head>

	<body id="user-body">
		<!-- user列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">ID</th>
					<th class="mdl-data-table__cell--non-numeric">所属用户</th>
					<th class="mdl-data-table__cell--non-numeric">创建时间</th>
					<th class="mdl-data-table__cell--non-numeric">产品数量</th>
					<th class="mdl-data-table__cell--non-numeric">收货地址</th>
					<th class="mdl-data-table__cell--non-numeric">物流单号</th>
					<%--<th class="mdl-data-table__cell--non-numeric">订单类型</th>--%>
					<th class="mdl-data-table__cell--non-numeric">物流状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderList }" var="order">
					<tr>
						<td class="mdl-data-table__cell--non-numeric">${order.id }</td>
						<td class="mdl-data-table__cell--non-numeric">
                            <img class="user-headimg-mini" src="${order.user.headimgurl}" alt="${order.user.nickname}">
                            ${order.user.nickname }
                        </td>
						<td class="mdl-data-table__cell--non-numeric">${order.createTime }</td>
						<td class="mdl-data-table__cell--non-numeric">
							${fn:length(order.orderItems) }
							<button id="td-menu-count-${order.id }" class="mdl-button mdl-js-button mdl-button--icon">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
									for="td-menu-count-${order.id }">
								<c:forEach items="${order.orderItems }" var="orderItem">
									<li class="mdl-menu__item">${orderItem.product.name }</li>
								</c:forEach>
							</ul>
						</td>
						<td class="mdl-data-table__cell--non-numeric">${order.userAddress.regionName } ${order.userAddress.address }</td>
						<td class="mdl-data-table__cell--non-numeric">${order.expressNum }</td>
						<%--<td class="mdl-data-table__cell--non-numeric">
							<c:choose>
								<c:when test="${order.orderType==301 }">
									借阅
								</c:when>
								<c:when test="${order.orderType==302 }">
									退还
								</c:when>
								<c:otherwise>
									未知
								</c:otherwise>
							</c:choose>
						</td>--%>
						<td class="mdl-data-table__cell--non-numeric">
							<c:choose>
								<c:when test="${order.status==401}">
									<span class="brownText">未支付</span>
								</c:when>
								<c:when test="${order.status==402}">
                                    <span class="redText">未发货</span>
								</c:when>
								<c:when test="${order.status==403}">
									<span class="greenText">已收货</span>
								</c:when>
								<c:when test="${order.status==404}">
									<span class="orangeText">已发货</span>
								</c:when>
								<c:when test="${order.status==405}">
									<span class="blueText">已完成</span>
								</c:when>
								<c:otherwise>
                                    <span class="grayText">未知状态订单</span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<button id="td-menu-${order.id }" class="mdl-button mdl-js-button mdl-button--icon">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
									for="td-menu-${order.id }">
								<c:choose>
									<c:when test="${order.orderType==301 }">
										<li class="mdl-menu__item" onclick="showQueryShippingDialog(${order.id });">确认发货</li>
									</c:when>
									<c:when test="${order.orderType==302 && order.status==404 }">
										<li class="mdl-menu__item" onclick="showQueryReceivedDialog(${order.id });">确认收货</li>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${order.orderType==301 }">
										<li class="mdl-menu__item" onclick="javascript:window.location.href='Order_print.action?orderId=${order.id }';">打印订单</li>
									</c:when>
								</c:choose>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 确认发货对话框 -->
		<dialog class="query-shipping-dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确认发货</h4>
			<div class="mdl-dialog__content">
				<ul class="mdl-list">
					<li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input type="hidden" id="query-shipping-id">
								<input class="mdl-textfield__input" type="text" id="query-shipping-num">
								<label class="mdl-textfield__label" for="query-shipping-num">物流单号</label>
							</div>
						</span>
					</li>
				</ul>
			</div>

			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">确认发货</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 确认收货对话框 -->
		<dialog class="query-received-dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确认收货</h4>
			<div class="mdl-dialog__content">
				<p>
					确认收到退还图书？
				</p>
			</div>

			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">确认</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

            $(function () {
                $("#date-picker").removeAttr("hidden")
            });
		
			var queryShippingDialog = document.querySelector('.query-shipping-dialog');
			/* 显示确认删除product对话框 */
			function showQueryShippingDialog(id) {
				
				$("#query-shipping-id").val(id);
				
				queryShippingDialog.querySelector('.agree').addEventListener('click', function() {
					queryShipping();
				});
				
				/* 绑定删除对话框取消按钮事件 */
				queryShippingDialog.querySelector('.close').addEventListener('click', function() {
					$("#query-shipping-id").val("");
					$("#query-shipping-num").val("");
					queryShippingDialog.close();
				});
				
				queryShippingDialog.showModal();
			}
			
			function queryShipping() {
				var orderId = $("#query-shipping-id").val();
				var num = $("#query-shipping-num").val();
				$.post("Order_shipping.action",
					{orderId: orderId, num: num},
					function(result){
						$("#query-shipping-id").val("");
						$("#query-shipping-num").val("");
						if(result.success){
							showMsg("发货成功！");
							queryShippingDialog.close();
							setTimeout(refreshPage, 1000);
						}else{
							showMsg("发货失败！");
						}
					},
					"json"
				);
			}
			
			var queryReceivedDialog = document.querySelector('.query-received-dialog');
			/* 显示确认删除product对话框 */
			function showQueryReceivedDialog(id) {
								
				queryReceivedDialog.querySelector('.agree').addEventListener('click', function() {
					queryReceived(id);
				});
				
				/* 绑定删除对话框取消按钮事件 */
				queryReceivedDialog.querySelector('.close').addEventListener('click', function() {
					queryReceivedDialog.close();
				});
				
				queryReceivedDialog.showModal();
			}
			
			function queryReceived(id) {
				$.post("Order_adminReceived.action",
					{orderId: id},
					function(result){
						if(result.success){
							queryReceivedDialog.close();
							showMsg("确认成功！");
							setTimeout(refreshPage, 1000);
						}else{
							showMsg("确认失败！");
						}
					},
					"json"
				);
			}
			
			function refreshPage() {
				window.location.reload(true);
			}

			/* 页面下方snackbar */
			var snackbarContainer = document.querySelector('#msg-toast');

			function showMsg(msg) {
				var data = {message: msg};
				snackbarContainer.MaterialSnackbar.showSnackbar(data);
			}

		</script>
	</body>
</html>
