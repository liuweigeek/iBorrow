<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="太原科技有限公司">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>${webpagename }</title>

<!-- Tether -->
<link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/tether.min.js"></script>

<!-- Bootstrap -->
<%-- <link href="${pageContext.request.contextPath}/css/bootstrap3.min.css" rel="stylesheet"> --%>
<link href="${pageContext.request.contextPath}/css/bootstrap4.min.css"
	rel="stylesheet">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>
<%-- <script src="${pageContext.request.contextPath}/js/bootstrap3.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>

	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">
	<!-- <link rel="stylesheet"
		href="https://fonts.googleapis.com/icon?family=Material+Icons"> -->

<!-- 	<script type="text/javascript" src="js/jquery.event.drag-1.5.min.js"></script>
	<script type="text/javascript" src="js/jquery.touchSlider.js"></script>
	<script type="text/javascript" src="js/carousel.js"></script> -->
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/vip/vipcard_list.css" />

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
		<a class="navbar-brand" href="javascript:history.go(-1)">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			会员卡
		</a>
	</nav>
	<div class="container">

		<button type="button" id="user-voucher-code" class="btn btn-outline-primary btn-block"
				onclick="javascript:$('#use-voucher-dialog').modal('show');">
			使用代金券
		</button>
		
		<p>
			用户在借阅书籍时，系统会要求用户的押金至少达到300元，如押金不足，请在个人中心页面充值押金。<br>
			如需退还押金，请到山西省太原市小店区长治路百万庄园综合楼2号确认后退还。
		</p>
		
		<!-- 列表项 -->
		<c:forEach items="${vipCardList }" var="vipCard">
			<figure class="figure card" style="border-radius: 0.25rem;">
				<img src="${vipCard.background }" height="240px" class="figure-img img-fluid rounded" alt="">
				<figcaption class="figure-caption text-right">
					${vipCard.price }元/${vipCard.day }天
					<button class="card-link btn btn-link" type="button"
						onclick="javascript:showAcceptDialog(${vipCard.id }, ${vipCard.price }, '会员卡', ${vipCard.day});">
						购买
					</button>
				</figcaption>
			</figure>
		</c:forEach>
		
		<div class="modal fade" id="use-voucher-dialog" tabindex="-1" role="dialog" aria-labelledby="addPhoneModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">使用代金券</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="voucher-code" class="form-control-label">代金券码:</label>
							<input type="text" class="form-control" name="mCode" id="voucher-code">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" onclick="javascript:useVoucher();">使用</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="buy_vipcard_success" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">购买成功</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="buy_vipcard_success_body" class="modal-body">
					...
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
					<button type="button" onclick="javascript:$('#setReferee-dialog').modal('show');" class="btn btn-primary">填写推荐人</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="setReferee-dialog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">推荐人</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="regeree-user-id" class="form-control-label">推荐人会员号:</label>
						<input type="tel" class="form-control" id="regeree-user-id">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">跳过</button>
					<button type="button" onclick="javascript:setReferee();" class="btn btn-primary">提交</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="accept-dialog" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<input id="accept-vipcard-id" type="hidden">
				<input id="accept-vipcard-money" type="hidden">
				<input id="accept-vipcard-body" type="hidden">
                <input id="accept-vipcard-day" type="hidden">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">会员注册条款须知</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
                    <ul>
                        <li>
                            <h5>一、会员使用须知</h5>
                            会员应保证其提供给绘本馆的资料均真实无误，如因用户提供虚假资料或被他人获悉自己的注册资料，从而导致的损失由会员本人承担，损失包括但不限于因资料虚假而无法取回丢失的借阅卡/会员名。
                        </li>
                        <li>
                            <h5>二、阅读计划使用须知</h5>
                            <ul>
                                <li>
                                    1.阅读计划的会员费仅指图书借阅使用费。年费：980元/年。
                                </li>
                                <li>
                                    2.请根据宝宝的阅读需求选择合适的阅读计划借阅。如需帮助，请联系绘本阅读指导老师，我们将免费为您提供个性化阅读指导。
                                </li>
                                <li>
                                    3. 全年不限次借阅服务（单次借阅不应超过5本，且上次借阅数量和本次借阅数量合在一起不应超过5本），借阅无时限。绘本馆采取寄书到家，回邮到付（仅限山西省境内），线上下单方式开展。
                                </li>
                                <li>
                                    4.每个账户除借阅费用外，收取借书押金300元。会员使用期限到期后，如不再续费，视为自动离会，且不违反本馆借阅注意事项，全额退还押金。押金退还说明：借书服务期满后，如用户不再借书，需在3日内归还全部书籍，若书籍有丢失或破损，需现金赔付或从押金直接抵扣。若书籍无任何丢失或破损，将于5个工作日内凭卡和押金收据退还。
                                </li>
                                <li>
                                    5.会员期内，所有会员可免费参加馆内举办的主题活动、阅读分享会、故事会、父母课堂、亲子沙龙等活动（少量活动如需收费另行通知）。
                                </li>
                                <li>
                                    6.会员期内，在所借图书归还后，会员可在线上申请2次停卡（仅限寒暑假，单次时间最长为1个月）。停卡期间，所有服务暂停；复效后，卡有效期时间延续。如未申请则视为正常借阅，时间不延续。
                                </li>
                            </ul>
                        </li>
                        <li>
                            <h5>三、注意事项</h5>
                            <ul>
                                <li>
                                    1.会员在收到图书时，请第一时间核对所借图书订单，检查书籍是否有毁损（如涂画、撕页、写字、污迹等），如有上述情况，请及时联系工作人员或通过微信平台反馈。
                                </li>
                                <li>
                                    2.会员若在借阅期内，发生图书毁损、遗失等情况，须按书籍原价的七折购买此书，或买一本相同且全新的书归还。
                                </li>
                                <li>
                                    3.如您的通讯联络信息有变，请及时更账户信息，以便我们给您提供方便快捷的借阅服务。
                                </li>
                                <li>
                                    4、在阅读结束后，请将绘本包装好，通过本馆指定快递公司寄回或送回。绘本馆根据顾客返回书籍包装情况，为顾客赠送积分。每完成一次牢固包装回邮，计10分；如普通包装不累计积分。换购相应产品或会期。
                                </li>
                                <li>
                                    5.所有绘本馆公告信息（如：新书推荐、送书时间调整、活动安排等）都会在微信公众号发布，请随时关注信息。
                                </li>
                            </ul>
                        </li>
                        <li>
                            <h5>四、如有其它未尽事宜，请联系工作人员。</h5><br>
                        </li>
                        <li>
                            <h4>愿您和孩子的阅读之门从此开启！衷心祝愿您和孩子阅读愉快！ </h4>
                        </li>
                    </ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">拒绝</button>
					<button type="button" class="btn btn-primary" onclick="javascript:accept();">接受</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
		function useVoucher() {
			var code = $("#voucher-code").val();
			if(!code || code=="") {
				alert("请输入代金券码");
			} else {
				$.post("Voucher_activate.action",
					{voucherCode: code}, 
					function(result){
						var result = eval('('+result+')');
						$("#use-voucher-dialog").modal('hide');
						$("#voucher-code").val('');
						if (result.success) {
							alert("使用成功")
						} else {
 							alert(result.error);
						}
					}
				);
			}
		}
		
		function showAcceptDialog(vipCardId, money, bodyStr, day) {
			$('#accept-vipcard-id').val(vipCardId);
			$('#accept-vipcard-money').val(money);
			$('#accept-vipcard-body').val(bodyStr);
            $('#accept-vipcard-day').val(day);
			$('#accept-dialog').modal('show');
		}
		
		function accept() {
			var vipCardId = $('#accept-vipcard-id').val();
			var money = $('#accept-vipcard-money').val();
			var bodyStr = $('#accept-vipcard-body').val();
            var day = $('#accept-vipcard-day').val();
			$('#accept-dialog').modal('hide');
			payVipCard(vipCardId, money, bodyStr, day);
		}

		function payVipCard(vipCardId, money, bodyStr, day) {
			$.post("Payment_buy.action",
				{money: money, bodyStr: bodyStr, payType: 501, vipCardDay: day},
				function(result){
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
								// 使用以上方式判断前端返回,微信团队郑重提示:res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
								if(res.err_msg == "get_brand_wcpay_request:ok" ) {
									var tradeNum = result.out_trade_no;
									$.post("VipCard_confirmActivate.action",
										{vipCardId: vipCardId, tradeNum: tradeNum}, 
										function(result){
											var result = eval('('+result+')');
											if (result.success) {
												var msg = "已开通会员至" + result.endTime;
												if(result.isFirst) {
													$("#buy_vipcard_success_body").text("");													
													$("#buy_vipcard_success_body").append(msg);
													$("#buy_vipcard_success").modal('show');
												} else {
													alert(msg);
												}
											}
										}
									);
								} else {
									alert('支付失败，请稍后重试');
								}
							}
						);
					} else {
						alert('暂时无法购买，请稍后重试');
					}
				}
			);
		}
		
		function setReferee() {
            var refereeVipId = $("#regeree-user-id").val();
            if (!refereeVipId || refereeVipId =="") {
                alert("请输入推荐人会员卡号");
                return false;
            }
			$("#buy_vipcard_success").modal('hide');

			$.post("User_setReferee.action",
				{refereeVipId: refereeVipId}, 
				function(result){
					var result = eval('('+result+')');
					if (result.success) {
						$("#setReferee-dialog").modal('hide');
						alert('已为您与对方各续约一个月');
					} else {
						alert(result.errorMsg);
					}
				}
			);
		}

	</script>
</body>
</html>
