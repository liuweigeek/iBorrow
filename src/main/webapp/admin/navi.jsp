<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                    $("#naviBackground").uploadPreview({ Img: "backgroundPre", Width: 220, Height: 220 });
                    $("#mdl-navigation__link--" + "Navi").addClass('mdl-navigation__link--current');
                    $("#content-header-title").text("轮播图");
                });

            </script>

            <style>
                #show-add-navi-dialog-button {
                    position: fixed;
                    display: block;
                    right: 0;
                    bottom: 0;
                    margin-right: 40px;
                    margin-bottom: 40px;
                    z-index: 900;
                }
                .save-navi-dialog-list mdl-list {
                    width: 600px;
                }
                .save-navi-dialog-list-item {
                    margin: 0px;
                    padding: 0px;
                }
                .navi-card-wide.mdl-card {
                    width: 500px;
                }
                .navi-card-wide > .mdl-card__title {
                    color: #fff;
                    height: 176px;
                }
                .navi-card-wide > .mdl-card__menu {
                    color: #fff;
                }
            </style>

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    $('#admin-search-form').attr('action', 'Navi_list?keyword=' + keyword);
                }

            </script>
            <%
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                }
            %>

	</head>

	<body id="navi-body">
		<!-- navi列表table -->
		
		<div class="mdl-grid fixed-width">
			<c:forEach items="${naviList }" var="navi">
				<div class="navi-block navi-block--content-titles mdl-cell mdl-cell--6-col
							mdl-cell--8-col-tablet mdl-cell--4-col-phone">
					<div class="navi-card-wide mdl-card mdl-shadow--2dp">
						<div class="mdl-card__title"
							style="background: url('${navi.background }') center / cover;">
							<h2 class="mdl-card__title-text">${navi.topic.title }</h2>
						</div>
						<!-- <div class="mdl-card__supporting-text">
							Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Mauris sagittis pellentesque lacus eleifend lacinia...
						</div> -->
						<div class="mdl-card__actions mdl-card--border">
							<a href="Topic_show.action?topicId=${navi.topic.id }" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
								跳转到内容
							</a>
						</div>
						<div class="mdl-card__menu">
							<button id="navi-menu${navi.id }" class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
								for="navi-menu${navi.id }">
								<li class="mdl-menu__item" onclick="javascript:showDeleteDialog(${navi.id });">删除</li>
								<li class="mdl-menu__item" onclick="javascript:showModifyDialog(${navi.id }, '${navi.background }', '${navi.topic.id}');">编辑</li>
							</ul>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<!-- 删除Navi对话框 -->		
		<dialog class="confrim_delete_dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确定删除？</h4>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">删除</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 保存Navi对话框 -->
		<dialog class="save_dialog mdl-dialog">
			<h4 class="mdl-dialog__title save_dialog_title">添加Navi</h4>
			<div class="mdl-dialog__content">
				<form id="save-navi-form" action="Navi_save" method="post" enctype="multipart/form-data">
					<input id="save-dialog-id" type="hidden" name="id">
					<ul class="save-navi-dialog-list mdl-list">
						<li class="save-navi-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<label>背景路径</label>
							</span>
							<span class="mdl-list__item-secondary-action">
								<img id="backgroundPre" class="pull-left" style="width: 180px; height: 100px; padding: 5px;"
									src="${navi.background }" />
							</span>
						</li>
						<li class="save-navi-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<input type="file" id="naviBackground" name="naviBackground">
							</span>
						</li>
						<li class="save-navi-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<label>目标Topic</label>
							</span>
							<span class="mdl-list__item-secondary-action">
								<select style="width: 100px; padding: 5px;" id="save-dialog-topic" name="topic.id">
									<option value="">请选择...</option>
									<c:forEach var="topic" items="${topicList }">
										<option value="${topic.id }">${topic.title }</option>
									</c:forEach>
								</select>
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

		<!-- 页面右下角添加Navi按钮 -->
		<button id="show-add-navi-dialog-button"  onclick="javascript:showAddDialog();"
				class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
			<i class="material-icons">add</i>
		</button>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

			/* 删除navi对话框 */
			var deleteDialog = document.querySelector('.confrim_delete_dialog');
			/* 显示确认删除navi对话框 */
			function showDeleteDialog(naviId) {
				
				deleteDialog.querySelector('.agree').addEventListener('click', function() {
					deleteNavi(naviId)
				});
				
				/* 绑定删除对话框取消按钮事件 */
				deleteDialog.querySelector('.close').addEventListener('click', function() {
					deleteDialog.close();
				});
				
				deleteDialog.showModal();
			}

			/* 删除navi */
			function deleteNavi(naviId){
				$.post("Navi_delete.action", {naviId: naviId}, 
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
			
			
			/* 保存navi对话框 */
			var saveDialog = document.querySelector('.save_dialog');
			
			/* 显示添加navi对话框 */
			function showAddDialog() {
				$(".save_dialog_title").html("添加Navi");
				saveDialog.showModal();
			}
			
			/* 显示修改navi对话框 */
			function showModifyDialog(id, background, topic) {
				$(".save_dialog_title").html("修改Navi");
				$("#save-dialog-id").val(id);
				$("#backgroundPre").attr("src",background);
				$("#save-dialog-topic").val(topic);
				saveDialog.showModal();
			}
			
			/* 绑定保存对话框取消按钮事件 */
			saveDialog.querySelector('.close').addEventListener('click', function() {
				resetValue();
				saveDialog.close();
			});
			/* 绑定保存对话框确认按钮事件 */
 			saveDialog.querySelector('.agree').addEventListener('click', function() {
				if ($("#save-dialog-topic").val()==null||$("#save-dialog-topic").val()=='') {
					showMsg("请选择所指向的内容");
					return false;
				}
				$("#save-navi-form").submit();
				resetValue();
				saveDialog.close();
				showMsg("保存成功！");
				setTimeout(refreshPage, 1000);
			});
			
			
			function resetValue(){
				$(".mdl-dialog__title").html("修改Navi");
				$("#save-dialog-id").val(null);
				$("#naviBackground").val(null);
				$("#save-dialog-topic").val(null);
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
