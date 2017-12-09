<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
	<html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
            <title></title>

            <%--<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>--%>
            <script type="text/javascript" src="${pageContext.request.contextPath}/lib/uploadPreview.min.js"></script>

            <script type="text/javascript">
                $(function () {
                    $("#vipCardBackground").uploadPreview({ Img: "backgroundPre", Width: 220, Height: 220 });
					$("#mdl-navigation__link--" + "VipCard").addClass('mdl-navigation__link--current');
					$("#content-header-title").text("会员卡");
                });
            </script>

            <style>
                #show-add-vipCard-dialog-button {
                    position: fixed;
                    display: block;
                    right: 0;
                    bottom: 0;
                    margin-right: 40px;
                    margin-bottom: 40px;
                    z-index: 900;
                }
                .save-vipCard-dialog-list mdl-list {
                    width: 600px;
                }
                .save-vipCard-dialog-list-item {
                    margin: 0px;
                    padding: 0px;
                }
                .vipCard-card-wide.mdl-card {
                    width: 500px;
                }
                .vipCard-card-wide > .mdl-card__title {
                    color: #fff;
                    height: 176px;
                }
                .vipCard-card-wide > .mdl-card__menu {
                    color: #fff;
                }
            </style>

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    $('#admin-search-form').attr('action', 'VipCard_list?keyword=' + keyword);
                }

            </script>

            <%
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                }
            %>
	</head>

	<body id="vipCard-body">
		<!-- vipCard列表table -->
		<div class="mdl-grid fixed-width">
			<c:forEach items="${vipCardList }" var="vipCard">
				<div class="vipCard-block vipCard-block--content-titles mdl-cell mdl-cell--6-col
							mdl-cell--8-col-tablet mdl-cell--4-col-phone">
					<div class="vipCard-card-wide mdl-card mdl-shadow--2dp">
						<div class="mdl-card__title"
							style="background: url('${vipCard.background }') center / cover;">
							<h2 class="mdl-card__title-text">${vipCard.price }元/${vipCard.day }天</h2>
						</div>
						<div class="mdl-card__menu">
							<button id="vipCard-menu${vipCard.id }" class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
								for="vipCard-menu${vipCard.id }">
								<li class="mdl-menu__item" onclick="javascript:showDeleteDialog(${vipCard.id });">删除</li>
								<li class="mdl-menu__item" onclick="javascript:showModifyDialog(${vipCard.id }, '${vipCard.background }', '${vipCard.price}', '${vipCard.day}');">编辑</li>
							</ul>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<!-- 删除VipCard对话框 -->		
		<dialog class="confrim_delete_dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确定删除？</h4>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">删除</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 保存VipCard对话框 -->
		<dialog class="save_dialog mdl-dialog">
			<h4 class="mdl-dialog__title save_dialog_title">添加会员卡</h4>
			<div class="mdl-dialog__content">
				<form id="save-vipCard-form" action="VipCard_save" method="post" enctype="multipart/form-data">
					<input id="save-dialog-id" type="hidden" name="id">
					<ul class="save-vipCard-dialog-list mdl-list">
						<li class="save-vipCard-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<label>背景路径</label>
							</span>
							<span class="mdl-list__item-secondary-action">
								<img id="backgroundPre" class="pull-left" style="width: 180px; height: 100px; padding: 5px;"
									src="${vipCard.background }" />
							</span>
						</li>
						<li class="save-vipCard-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<input type="file" id="vipCardBackground" name="vipCardBackground">
							</span>
						</li>
						<li class="save-section-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
									<input class="mdl-textfield__input" type="number" id="save-dialog-day"
											name="day">
									<label class="mdl-textfield__label" for="save-dialog-day">天数</label>
								</div>
							</span>
						</li>
						<li class="save-section-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
									<input class="mdl-textfield__input" type="text" id="save-dialog-price"
											name="price">
									<label class="mdl-textfield__label" for="save-dialog-price">价格</label>
								</div>
							</span>
						</li>
					</ul>
				</form>
			</div>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">保存</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>

		<!-- 页面右下角添加VipCard按钮 -->
		<button id="show-add-vipCard-dialog-button"  onclick="javascript:showAddDialog();"
				class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
			<i class="material-icons">add</i>
		</button>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

			/* 删除vipCard对话框 */
			var deleteDialog = document.querySelector('.confrim_delete_dialog');
			/* 显示确认删除vipCard对话框 */
			function showDeleteDialog(vipCardId) {
				
				deleteDialog.querySelector('.agree').addEventListener('click', function() {
					deleteVipCard(vipCardId)
				});
				
				/* 绑定删除对话框取消按钮事件 */
				deleteDialog.querySelector('.close').addEventListener('click', function() {
					deleteDialog.close();
				});
				
				deleteDialog.showModal();
			}

			/* 删除vipCard */
			function deleteVipCard(vipCardId){
				$.post("VipCard_delete.action", {vipCardId: vipCardId}, 
					function(result){
						var result = eval('('+result+')');
						deleteDialog.close();
						if (result.error) {
							showMsg(result.error);
						} else {
 							showMsg("删除成功！");
							setTimeout(refreshPage, 1000);
						}
					}
				);
			}
			
			
			/* 保存vipCard对话框 */
			var saveDialog = document.querySelector('.save_dialog');
			
			/* 显示添加vipCard对话框 */
			function showAddDialog() {
				$(".save_dialog_title").html("添加会员卡");
				saveDialog.showModal();
			}
			
			/* 显示修改vipCard对话框 */
			function showModifyDialog(id, background, price, day) {
				$(".save_dialog_title").html("修改会员卡");
				$("#save-dialog-id").val(id);
				$("#backgroundPre").attr("src",background);
				$("#save-dialog-price").val(price);
				$("#save-dialog-day").val(day);
				saveDialog.showModal();
			}
			
			/* 绑定保存对话框取消按钮事件 */
			saveDialog.querySelector('.close').addEventListener('click', function() {
				resetValue();
				saveDialog.close();
			});
			/* 绑定保存对话框确认按钮事件 */
 			saveDialog.querySelector('.agree').addEventListener('click', function() {
				/* if ($("#save-dialog-topic").val()==null||$("#save-dialog-topic").val()=='') {
					showMsg("请选择所指向的内容");
					return false;
				} */
				$("#save-vipCard-form").submit();
				resetValue();
				saveDialog.close();
				showMsg("保存成功！");
				setTimeout(refreshPage, 1000);
			});
			
			
			function resetValue(){
				$(".mdl-dialog__title").html("修改会员卡");
				$("#save-dialog-id").val(null);
				$("#vipCardBackground").val(null);
				$("#save-dialog-price").val(null);
				$("#save-dialog-day").val(null);
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
