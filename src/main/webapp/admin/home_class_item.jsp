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

            <style type="text/css">
                #show-add-homeClassItem-dialog-button {
                    position: fixed;
                    display: block;
                    right: 0;
                    bottom: 0;
                    margin-right: 40px;
                    margin-bottom: 40px;
                    z-index: 900;
                }
                .save-homeClassItem-dialog-list mdl-list {
                    width: 600px;
                }
                .save-homeClassItem-dialog-list-item {
                    margin: 0px;
                    padding: 0px;
                }
            </style>

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    $('#admin-search-form').attr('action', 'HomeClassItem_list?keyword=' + keyword);
                }

            </script>
		
		<%
	    	if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
				response.sendRedirect("admin.action");
				return;
			}
		%>
	</head>

	<body id="homeClassItem-body">
		<!-- homeClassItem列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th>ID</th>
					<th class="mdl-data-table__cell--non-numeric">所属小分类</th>
					<th class="mdl-data-table__cell--non-numeric">所指向书籍</th>
					<th>
						<button id="th-menu" class="mdl-button mdl-js-button mdl-button--icon">
							<i class="material-icons">more_vert</i>
						</button>
						<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
								for="th-menu">
							<li class="mdl-menu__item" onclick="javascript:showDeleteListDialog();">删除选中项</li>
							<li class="mdl-menu__item">刷新</li>
						</ul>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${homeClassItemList }" var="homeClassItem">
					<tr>
						<td>${homeClassItem.id }</td>
						<td>${homeClassItem.homeClassify.name }</td>
						<td>${homeClassItem.product.name }</td>
						<td>
							<button id="td-menu${homeClassItem.id }" class="mdl-button mdl-js-button mdl-button--icon">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
									for="td-menu${homeClassItem.id }">
								<li class="mdl-menu__item" onclick="javascript:showDeleteDialog(${homeClassItem.id });">删除</li>
								<li class="mdl-menu__item" onclick="javascript:showModifyDialog(${homeClassItem.id }, '${homeClassItem.homeClassify.id}', '${homeClassItem.product.id }');">修改</li>	
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 删除homeClassItem对话框 -->		
		<dialog class="confrim_delete_dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确定删除？</h4>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">删除</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 批量删除homeClassItem对话框 -->		
		<dialog class="confrim_delete_list_dialog mdl-dialog">
			<h4 class="mdl-dialog__title confrim_delete_list_dialog_title">批量删除</h4>
			<div class="mdl-dialog__content">
				<p>
					<div class="confrim_delete_list_dialog_content"></div>
				<p>
			</div>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">确定</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 保存homeClassItem对话框 -->
		<dialog class="save_dialog mdl-dialog">
			<h4 class="mdl-dialog__title save_dialog_title">添加首页小分类推荐项</h4>
			<div class="mdl-dialog__content">
				<form id="save-homeClassItem-form" action="HomeClassItem_save" method="post">
					<input id="save-dialog-id" type="hidden" name="id">
					<ul class="save-homeClassItem-dialog-list mdl-list">
						<li class="save-homeClassItem-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<label>目标书籍</label>
							</span>
							<span class="mdl-list__item-secondary-action">
								<select style="width: 100px; padding: 5px;" id="save-dialog-product" name="product.id"><option value="">请选择...</option>
									<c:forEach var="product" items="${productList }">
										<option value="${product.id }">${product.name }</option>
									</c:forEach>
								</select>
							</span>						
						</li>
						<li class="save-homeClassItem-dialog-list-item mdl-list__item">
							<span class="mdl-list__item-primary-content">
								<label>所属小分类</label>
							</span>
							<span class="mdl-list__item-secondary-action">
								<select style="width: 100px; padding: 5px;" id="save-dialog-homeClassify" name="homeClassify.id"><option value="">请选择...</option>
									<c:forEach var="homeClassify" items="${homeClassifyList }">
										<option value="${homeClassify.id }">${homeClassify.name }</option>
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
		
		<!-- 页面右下角添加HomeClassItem按钮 -->
		<button id="show-add-homeClassItem-dialog-button" onclick="javascript:showAddDialog();"
				class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
			<i class="material-icons">add</i>
		</button>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

			/* 删除homeClassItem对话框 */
			var deleteDialog = document.querySelector('.confrim_delete_dialog');
			/* 显示确认删除homeClassItem对话框 */
			function showDeleteDialog(homeClassItemId) {
				
				deleteDialog.querySelector('.agree').addEventListener('click', function() {
					deleteHomeClassItem(homeClassItemId)
				});
				
				/* 绑定删除对话框取消按钮事件 */
				deleteDialog.querySelector('.close').addEventListener('click', function() {
					deleteDialog.close();
				});
				
				deleteDialog.showModal();
			}

			/* 删除homeClassItem */
			function deleteHomeClassItem(homeClassItemId){
				$.post("HomeClassItem_delete.action", {homeClassItemId: homeClassItemId}, 
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

			
			/* 批量删除homeClassItem对话框 */
			var deleteListDialog = document.querySelector('.confrim_delete_list_dialog');

			/* 显示确认删除homeClassItem对话框 */
			function showDeleteListDialog() {
				var selectedSpan=$(".is-checked").parent().next("td");
				if(selectedSpan.length==0){
					$(".confrim_delete_list_dialog_content").html("请选择要删除的数据！");
					deleteListDialog.querySelector('.agree').addEventListener('click', function() {
						deleteListDialog.close();
					});
				} else {
					var strIds=[];
					for(var i=0;i<selectedSpan.length;i++){
						strIds.push(selectedSpan[i].innerHTML);
					}
					var ids=strIds.join(",");
					$(".confrim_delete_list_dialog_content").html("确定要删除"+selectedSpan.length+"条数据吗？");
					deleteListDialog.querySelector('.agree').addEventListener('click', function() {
						deleteHomeClassItems(ids);
					});
				}
				/* 绑定删除对话框取消按钮事件 */
				deleteListDialog.querySelector('.close').addEventListener('click', function() {
					deleteListDialog.close();
				});
				deleteListDialog.showModal();
			}
			
			
			/* 批量删除homeClassItem */
			function deleteHomeClassItems(ids){
				$.post("HomeClassItem_deleteList.action",{ids: ids},function(result){
					if(result.success){
						showMsg("删除成功！");
						setTimeout(refreshPage, 1000);
					}else{
						showMsg("删除失败！");
					}
				},"json");
			}
			
			/* 保存homeClassItem对话框 */
			var saveDialog = document.querySelector('.save_dialog');
			
			/* 显示添加homeClassItem对话框 */
			function showAddDialog() {
				$(".save_dialog_title").html("添加首页小分类推荐项");
				saveDialog.showModal();
			}
			
			/* 显示修改homeClassItem对话框 */
			function showModifyDialog(id, homeClassify, product) {
				$(".save_dialog_title").html("修改首页小分类推荐项");
				$("#save-dialog-id").val(id);
				$("#save-dialog-product").val(product);
				$("#save-dialog-homeClassify").val(homeClassify);
				saveDialog.showModal();
			}
			
			/* 绑定保存对话框取消按钮事件 */
			saveDialog.querySelector('.close').addEventListener('click', function() {
				resetValue();
				saveDialog.close();
			});
			/* 绑定保存对话框确认按钮事件 */
 			saveDialog.querySelector('.agree').addEventListener('click', function() {
				if ($("#save-dialog-product").val() == null||$("#save-dialog-product").val()=='') {
					showMsg("请选择所指向的书籍");
					return false;
				}
				if ($("#save-dialog-homeClassify").val() == null||$("#save-dialog-homeClassify").val()=='') {
					showMsg("请选择所属小分类");
					return false;
				}
				$("#save-homeClassItem-form").submit();
				resetValue();
				saveDialog.close();
				showMsg("保存成功！");
				setTimeout(refreshPage, 1000);
			});
			
			
			function resetValue(){
				$(".save_dialog_title").html("修改首页小分类推荐项");
				$("#save-dialog-id").val(null);
				$("#save-dialog-product").val(null);
				$("#save-dialog-homeClassify").val(null);
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
