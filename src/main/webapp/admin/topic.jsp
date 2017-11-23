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
                #show-add-topic-dialog-button {
                    position: fixed;
                    display: block;
                    right: 0;
                    bottom: 0;
                    margin-right: 40px;
                    margin-bottom: 40px;
                    z-index: 900;
                }
                .td_topic_description {
                    width:500px;
                    overflow:hidden;
                    text-overflow:ellipsis;
                }
            </style>

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    $('#admin-search-form').attr('action', 'Topic_list?keyword=' + keyword);
                }

            </script>

            <%
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                }
            %>
	</head>

	<body id="topic-body">
		<!-- topic列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th>ID</th>
					<th class="mdl-data-table__cell--non-numeric">Topic名称</th>
					<!-- <th class="mdl-data-table__cell--non-numeric">Topic内容</th> -->
					<th class="mdl-data-table__cell--non-numeric">发布时间</th>
					<th class="mdl-data-table__cell--non-numeric">修改时间</th>
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
				<c:forEach items="${topicList }" var="topic">
					<tr>
						<td>${topic.id }</td>
						<td class="mdl-data-table__cell--non-numeric">${topic.title }</td>
<%-- 						<td class="mdl-data-table__cell--non-numeric">
							<xmp>
								<div class="td_topic_description">${topic.content }</div>
							</xmp>
						</td> --%>
						<td class="mdl-data-table__cell--non-numeric">${topic.publishTime }</td>
						<td class="mdl-data-table__cell--non-numeric">${topic.modifyTime }</td>
						<td>
							<button id="td-menu${topic.id }" class="mdl-button mdl-js-button mdl-button--icon">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
									for="td-menu${topic.id }">
								<li class="mdl-menu__item" onclick="javascript:showDeleteDialog(${topic.id });">删除</li>
								<li class="mdl-menu__item" onclick="javascript:window.location.href='Topic_modify.action?topicId=${topic.id }';">修改</li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 删除topic对话框 -->		
		<dialog class="confrim_delete_dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确定删除？</h4>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">删除</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 批量删除topic对话框 -->		
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
		
		<!-- 页面右下角添加Topic按钮 -->
		<button id="show-add-topic-dialog-button" onclick="location='${pageContext.request.contextPath}/topic/topicAdd.jsp'"
				class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
			<i class="material-icons">add</i>
		</button>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>
			/* 删除topic对话框 */
			var deleteDialog = document.querySelector('.confrim_delete_dialog');
			/* 显示确认删除topic对话框 */
			function showDeleteDialog(topicId) {

				deleteDialog.querySelector('.agree').addEventListener('click', function() {
					deleteTopic(topicId)
				});
				
				/* 绑定删除对话框取消按钮事件 */
				deleteDialog.querySelector('.close').addEventListener('click', function() {
					deleteDialog.close();
				});
				
				deleteDialog.showModal();
			}

			/* 删除topic */
			function deleteTopic(topicId){
				$.post("Topic_delete.action", {topicId: topicId}, 
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
			
			/* 批量删除topic对话框 */
			var deleteListDialog = document.querySelector('.confrim_delete_list_dialog');

			/* 显示确认删除topic对话框 */
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
						deleteTopics(ids);
					});
				}
				/* 绑定删除对话框取消按钮事件 */
				deleteListDialog.querySelector('.close').addEventListener('click', function() {
					deleteListDialog.close();
				});
				deleteListDialog.showModal();
			}
			
			
			/* 批量删除topic */
			function deleteTopics(ids){
				$.post("Topic_deleteList.action",{ids: ids},function(result){
					if(result.success){
						showMsg("删除成功！");
						setTimeout(refreshPage, 1000);
					}else{
						showMsg("删除失败！");
					}
				},"json");
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
