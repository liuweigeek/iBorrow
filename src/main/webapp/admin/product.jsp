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

        <script type="text/javascript" src="${pageContext.request.contextPath}/lib/uploadPreview.min.js"></script>

        <script type="text/javascript">
            $(function () {
                $("#productcover").uploadPreview({ Img: "coverPre", Width: 220, Height: 220 });
            });

        </script>

        <style type="text/css">
            #show-add-product-dialog-button {
                position: fixed;
                display: block;
                right: 0;
                bottom: 0;
                margin-right: 40px;
                margin-bottom: 40px;
                z-index: 900;
            }
            .save-product-dialog-list mdl-list {
                width: 600px;
            }
            .save-product-dialog-list-item {
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
                  $('#admin-search-form').attr('action', 'Product_list?keyword=' + keyword);
              }

              $(function () {
                  $("#mdl-navigation__link--" + "Product").addClass('mdl-navigation__link--current');
                  $("#content-header-title").text("书籍");
              });

          </script>

        <%
            if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                response.sendRedirect("admin.action");
                return;
            }
        %>
	</head>

	<body id="product-body">
		<!-- product列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th>ID</th>
					<th class="mdl-data-table__cell--non-numeric">书名</th>
					<th class="mdl-data-table__cell--non-numeric">编号</th>
					<th class="mdl-data-table__cell--non-numeric">库存</th>
					<th class="mdl-data-table__cell--non-numeric">剩余</th>
					<th class="mdl-data-table__cell--non-numeric">进价</th>
					<th class="mdl-data-table__cell--non-numeric">售价</th>
					<th class="mdl-data-table__cell--non-numeric">作者</th>
					<th class="mdl-data-table__cell--non-numeric">出版社</th>
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
				<c:forEach items="${productList }" var="product">
					<tr>
						<td>${product.id }</td>
						<td class="mdl-data-table__cell--non-numeric">${product.name }</td>
						<td class="mdl-data-table__cell--non-numeric">${product.number }</td>
						<td>${product.inventory }</td>
						<td>${product.remainder }</td>
						<td>${product.purchasePrice }</td>
						<td>${product.salePrice }</td>
						<td class="mdl-data-table__cell--non-numeric">${product.author }</td>
						<td class="mdl-data-table__cell--non-numeric">${product.manufacturer }</td>
						<td>
							<button id="td-menu${product.id }" class="mdl-button mdl-js-button mdl-button--icon">
								<i class="material-icons">more_vert</i>
							</button>
							<ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
									for="td-menu${product.id }">
								<li class="mdl-menu__item" onclick="showDeleteDialog(${product.id });">删除</li>
								<li class="mdl-menu__item" onclick="showAddInventoryDialog(${product.id });">增加库存</li>
								<li class="mdl-menu__item" onclick="javascript:window.location.href='Product_modify.action?productId=${product.id }';">修改</li>
							</ul>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- 删除product对话框 -->		
		<dialog class="confrim_delete_dialog mdl-dialog">
			<h4 class="mdl-dialog__title">确定删除？</h4>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">删除</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>
		
		<!-- 批量删除product对话框 -->		
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
		
		<!-- 批量删除product对话框 -->		
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
		
		<!-- 添加product库存对话框 -->
		<dialog class="add_inventory_dialog mdl-dialog">
			<h4 class="mdl-dialog__title add_inventory_dialog_title">增加库存</h4>
			<div class="mdl-dialog__content">
				<ul class="add_inventory_dialog-dialog-list mdl-list">
					<li class="add_inventory_dialog-dialog-list-item mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="number" id="add_inventory_dialog-inventory"
										name="inventory">
								<label class="mdl-textfield__label" for="add_inventory_dialog-inventory">新增库存数</label>
							</div>
						</span>
					</li>
				</ul>
			</div>
			<div class="mdl-dialog__actions">
				<button type="button" class="mdl-button agree mdl-js-button">保存</button>
				<button type="button" class="mdl-button close">取消</button>
			</div>
		</dialog>

		<!-- 页面右下角添加Product按钮 -->
		<button id="show-add-product-dialog-button" onclick="javascript:window.location.href='Product_add.action';"
				class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
			<i class="material-icons">add</i>
		</button>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

			/* 删除product对话框 */
			var deleteDialog = document.querySelector('.confrim_delete_dialog');
			/* 显示确认删除product对话框 */
			function showDeleteDialog(productId) {
				
				deleteDialog.querySelector('.agree').addEventListener('click', function() {
					deleteProduct(productId)
				});
				
				/* 绑定删除对话框取消按钮事件 */
				deleteDialog.querySelector('.close').addEventListener('click', function() {
					deleteDialog.close();
				});
				
				deleteDialog.showModal();
			}

			/* 删除product */
			function deleteProduct(productId){
				$.post("Product_delete.action", {productId: productId}, 
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
			
			/* 批量删除product对话框 */
			var deleteListDialog = document.querySelector('.confrim_delete_list_dialog');

			/* 显示确认删除product对话框 */
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
						deleteProducts(ids);
					});
				}
				/* 绑定删除对话框取消按钮事件 */
				deleteListDialog.querySelector('.close').addEventListener('click', function() {
					deleteListDialog.close();
				});
				deleteListDialog.showModal();
			}
			
			
			/* 批量删除product */
			function deleteProducts(ids){
				$.post("Product_deleteList.action",{ids: ids},function(result){
					if(result.success){
						showMsg("删除成功！");
						setTimeout(refreshPage, 1000);
					}else{
						showMsg("删除失败！");
					}
				},"json");
			}
			
			/* 增加库存对话框 */
			var addInventoryDialog = document.querySelector('.add_inventory_dialog');
			
			/* 显示增加库存对话框 */
			function showAddInventoryDialog(productId) {
				/* 绑定增加库存对话框确定按钮事件 */
				addInventoryDialog.querySelector('.agree').addEventListener('click', function() {
					var newInventory = $("#add_inventory_dialog-inventory").val();
					if (newInventory==null||newInventory==0) {
						showMsg("请输入要增加的库存数");
						return false;
					}
					addInventory(productId, newInventory);
				});
				
				/* 绑定增加库存对话框取消按钮事件 */
				addInventoryDialog.querySelector('.close').addEventListener('click', function() {
					addInventoryDialog.close();
				});
				
				addInventoryDialog.showModal();
			}

			/* 增加库存 */
			function addInventory(productId, newInventory){
				$.post("Product_addInventory.action", {productId: productId, newInventory: newInventory}, 
					function(result){
						var result = eval('('+result+')');
						addInventoryDialog.close();
						if (result.error) {
							showMsg(result.error);
						} else {
 							showMsg("修改成功！");
							setTimeout(refreshPage, 1000);
						}
					}
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
