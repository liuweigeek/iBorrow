<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/text-color.css">

            <%--<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>--%>

			<style>

				.user-headimg-mini {
					border-radius: 50%;
					margin: auto;
					height: 24px;
					width: 24px;
				}

			</style>

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    var timeranger = $('#date-picker').val();
                    var action;
                    if (timeranger && timeranger != "时间范围") {
                        action = 'Payment_list?timeranger=' + timeranger + '&keyword=';
                    } else {
                        action = 'Payment_list?keyword=';
                    }
                    $('#admin-search-form').attr('action', action + keyword);
                }

                $(function () {
                    $("#mdl-navigation__link--" + "Payment").addClass('mdl-navigation__link--current');
                    $("#content-header-title").text("交易记录");
                });

            </script>

            <%
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                }
            %>
        </head>

	<body id="user-body">
		<!-- payment列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">ID</th>
					<th class="mdl-data-table__cell--non-numeric">所属用户</th>
					<th class="mdl-data-table__cell--non-numeric">商品描述</th>
					<th class="mdl-data-table__cell--non-numeric">金额</th>
					<th class="mdl-data-table__cell--non-numeric">交易起始时间</th>
					<th class="mdl-data-table__cell--non-numeric">交易结束时间</th>
					<th class="mdl-data-table__cell--non-numeric">支付完成</th>
					<%--<th class="mdl-data-table__cell--non-numeric">微信订单号</th>--%>
					<th class="mdl-data-table__cell--non-numeric">商户订单号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paymentList }" var="payment">
					<tr>
						<td class="mdl-data-table__cell--non-numeric">${payment.id }</td>
						<td class="mdl-data-table__cell--non-numeric">
							<img class="user-headimg-mini" src="${payment.user.headimgurl}" alt="${payment.user.nickname}">
							${payment.user.nickname }
						</td>
						<td class="mdl-data-table__cell--non-numeric">${payment.body }</td>
						<td class="mdl-data-table__cell--non-numeric">
							<fmt:formatNumber type="number" value="${payment.total_fee*(1/100) }" pattern="0.00" maxFractionDigits="2"/>元
						</td>
						<td class="mdl-data-table__cell--non-numeric">
							<fmt:formatDate value="${payment.time_start }" pattern="yyyy年MM月dd日 HH时mm分ss秒"/>
						</td>
						<td class="mdl-data-table__cell--non-numeric">
							<fmt:formatDate value="${payment.time_expire }" pattern="yyyy年MM月dd日 HH时mm分ss秒"/>
						</td>
						<td class="mdl-data-table__cell--non-numeric">
							<c:choose>
								<c:when test="${payment.finish }">
									<span class="greenText">已支付</span>
								</c:when>
								<c:otherwise>
									<span class="redText">未支付</span>
								</c:otherwise>
							</c:choose>
						</td>
						<%--<td class="mdl-data-table__cell--non-numeric">${payment.transaction_id }</td>--%>
						<td class="mdl-data-table__cell--non-numeric">${payment.out_trade_no }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

            $(function () {
                $("#date-picker").removeAttr("hidden")
            });

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
