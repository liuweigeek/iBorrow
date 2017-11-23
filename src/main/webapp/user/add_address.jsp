<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="太原才智教育科技有限公司">
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

<%
    	if(session.getAttribute(Constant.key_value.CURRENT_USER) == null){
			response.sendRedirect("User_requestUserInfo.action");
			return;
		}
	%>
	
	<script>
		$(function(){
			$.post("Region_listByParentId.action", {regionId: 1}, 
				function(result){
					var result = eval('('+result+')');
					if (!result.error) {
						for(var i = 0; i < result.regions.length; i++){
							var item = "<option value='" + result.regions[i].id + "''>" + result.regions[i].regionName + "</option>"
							$("#address-region-level1").append(item);
						}
					}
				}
			);
			$("#address-region-level1").change(function(){
				$("#address-region-level2 option").remove();
				$("#address-region-level3 option").remove();
				$("#address-region-level2").append("<option selected>---城市---</option>");
				$("#address-region-level3").append("<option selected>---区/县---</option>");
				$.post("Region_listByParentId.action", {regionId: $("#address-region-level1").val()}, 
					function(result){
						var result = eval('('+result+')');
						if (!result.error) {
							for(var i = 0; i < result.regions.length; i++){
								var item = "<option value='" + result.regions[i].id + "''>" + result.regions[i].regionName + "</option>"
								$("#address-region-level2").append(item);
							}
						}
					}
				);
			})
			$("#address-region-level2").change(function(){
				$("#address-region-level3 option").remove();
				$("#address-region-level3").append("<option selected>---区/县---</option>");
				$.post("Region_listByParentId.action", {regionId: $("#address-region-level2").val()}, 
					function(result){
						var result = eval('('+result+')');
						if (!result.error) {
							for(var i = 0; i < result.regions.length; i++){
								var item = "<option value='" + result.regions[i].id + "''>" + result.regions[i].regionName + "</option>"
								$("#address-region-level3").append(item);
							}
						}
					}
				);
			})
		})
	</script>

</head>

<body>
	<!-- 顶部菜单栏 -->
	<nav class="navbar navbar-light bg-faded">
		<a class="navbar-brand" href="javascript:history.go(-1)">
			<img src="${pageContext.request.contextPath}/images/icon/ic_arrow_back.svg" width="30" height="30" class="d-inline-block align-top" alt="">
			编辑收货地址
		</a>
	</nav>
	<div class="container">

		<form action="UserAddress_save" method="post" onsubmit="return checkParams();">
			<input class="form-control" type="hidden" value="${userAddress.id }" name="id">
			<%-- <input id="user" name="user.id" value="${currentUser.id }" type="hidden"/> --%>
			<div class="form-group row" style="margin-top: 15px;">
				<label for="address-consignee-input" class="col-4 col-form-label">收货人</label>
				<div class="col-8">
					<input class="form-control" type="text" value="${userAddress.consignee }" name="consignee" id="address-consignee-input">
				</div>
			</div>
			
			<!-- 所属地区下拉选择框组 -->
			<div class="form-group row">
				<label for="address-region-level1" class="col-4 col-form-label">省份</label>
				<div class="col-8">
					<select id="address-region-level1" name="region1.id" class="form-control custom-select">
						<c:choose>
							<c:when test="${userAddress.region1 != null }">
								<option selected  value="${userAddress.region1.id }">${userAddress.region1.regionName }</option>
							</c:when>
							<c:otherwise>
								<option selected>---省份---</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="address-region-level2" class="col-4 col-form-label">城市</label>
				<div class="col-8">
					<select id="address-region-level2" name="region2.id" class="form-control custom-select">
						<c:choose>
							<c:when test="${userAddress.region2 != null }">
								<option selected  value="${userAddress.region2.id }">${userAddress.region2.regionName }</option>
							</c:when>
							<c:otherwise>
								<option selected>---城市---</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="address-region-level3" class="col-4 col-form-label">区/县</label>
				<div class="col-8">
					<select id="address-region-level3" name="region3.id" class="form-control custom-select">
						<c:choose>
							<c:when test="${userAddress.region3 != null }">
								<option selected  value="${userAddress.region3.id }">${userAddress.region3.regionName }</option>
							</c:when>
							<c:otherwise>
								<option selected>---区/县---</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="address-address-input" class="col-4 col-form-label">详细地址</label>
				<div class="col-8">
					<input class="form-control" type="text" value="${userAddress.address }" name="address" id="address-address-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="address-postcode-input" class="col-4 col-form-label">邮政编码</label>
				<div class="col-8">
					<input class="form-control" type="number" value="${userAddress.postcode }" name="postcode" id="address-postcode-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="address-phone-input" class="col-4 col-form-label">联系电话</label>
				<div class="col-8">
					<input class="form-control" type="tel" value="${userAddress.phone }" name="phone" id="address-phone-input">
				</div>
			</div>
			<div class="form-group row">
				<label for="address-firstchoice-input" class="col-4 col-form-label">默认地址</label>
				<div class="col-8">
					<div class="form-group">
						<label class="custom-control custom-checkbox">
							<c:choose>
								<c:when test="${userAddress.firstChoice }">
									<input type="checkbox" checked value="1" onclick="this.value=(this.value==0)?1:0"
										class="custom-control-input" id="address-firstchoice-input" name="isFirstChoice">
								</c:when>
								<c:otherwise>
									<input type="checkbox" value="0" onclick="this.value=(this.value==0)?1:0"
										class="custom-control-input" id="address-firstchoice-input" name="isFirstChoice">
								</c:otherwise>
							</c:choose>
							<input type="checkbox" class="custom-control-input">
							<span class="custom-control-indicator"></span>
							<span class="custom-control-description">设为默认地址</span>
						</label>
					</div>
				</div>
			</div>
			<div class="form-group row">
				<button type="submit" style="margin: 15px 10% 10%;" class="btn btn-primary btn-block">保存收货地址</button>
			</div>
		</form>
	</div>

    <script>
        
        function checkParams() {
            var consignee = $("#address-consignee-input").val();
            var regionLevel1 = $("#address-region-level1").val();
            var regionLevel2 = $("#address-region-level2").val();
            var regionLevel3 = $("#address-region-level3").val();
            var address = $("#address-address-input").val();
            var phone = $("#address-phone-input").val();

            if (!consignee || !regionLevel1 || !regionLevel2 || !regionLevel3 || !address || !phone) {
                alert("请完整输入所需信息");
                return false;
            }
            if (consignee == "") {
                alert("请输入收件人姓名");
                return false;
            }
            if (!isPhoneNo(phone)) {
                alert("请输入有效的联系电话");
                return false;
            }
            if (address == "") {
                alert("请输入详细地址");
                return false;
            }
            return true;
        }

        function isPhoneNo(phone) {
            var pattern = /^1[34578]\d{9}$/;
            return pattern.test(phone);
        }
        
    </script>

</body>
</html>
