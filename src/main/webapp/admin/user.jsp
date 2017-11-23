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

            <%
                Boolean isAdmin = false;
                if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
                    response.sendRedirect("admin.action");
                    return;
                } else {
                    int userType = Integer.parseInt(session.getAttribute(Constant.key_value.USER_TYPE).toString());
                    if (userType != 0 && userType == Constant.userType.ADMIN) {
                        isAdmin = true;
                    }
                }
            %>

            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/text-color.css">

            <style>
                .product-cover {
                    width: 40px;
                    height: 40px;
                }

                .mdl-menu__item {
                    max-width: 300px;
                }

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
                    $('#admin-search-form').attr('action', 'User_list?keyword=' + keyword);
                }

            </script>

        </head>

	<body id="user-body">
		<!-- user列表table -->
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">ID</th>
					<th class="mdl-data-table__cell--non-numeric">昵称</th>
					<th class="mdl-data-table__cell--non-numeric">押金</th>
					<th class="mdl-data-table__cell--non-numeric">积分</th>
					<th class="mdl-data-table__cell--non-numeric">已借阅数量</th>
					<th class="mdl-data-table__cell--non-numeric">注册时间</th>
					<th class="mdl-data-table__cell--non-numeric">会员到期时间</th>
                    <th class="mdl-data-table__cell--non-numeric">电子邮箱</th>
                    <th class="mdl-data-table__cell--non-numeric">手机号</th>
                    <th class="mdl-data-table__cell--non-numeric">用户类型</th>
                    <th>
                        <button id="th-menu" class="mdl-button mdl-js-button mdl-button--icon">
                            <i class="material-icons">more_vert</i>
                        </button>
                        <ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
                            for="th-menu">
                            <li class="mdl-menu__item">刷新</li>
                        </ul>
                    </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList }" var="user">
					<tr>
						<td>${user.id }</td>
						<td class="mdl-data-table__cell--non-numeric">
                                <img class="user-headimg-mini" src="${user.headimgurl}" alt="${user.nickname}">
                                ${user.nickname }
                        </td>
						<td>${user.deposit }</td>
						<td>${user.integral }</td>
						<td>
                            <c:choose>
                                <c:when test="${user.borrowSum == 0}">
                                    ${user.borrowSum}
                                </c:when>
                                <c:otherwise>
                                    <span class="blueText">
                                        ${user.borrowSum}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
						<td class="mdl-data-table__cell--non-numeric">${user.regTime }</td>
						<td class="mdl-data-table__cell--non-numeric">${user.expirationTime }</td>
                        <td class="mdl-data-table__cell--non-numeric">${user.email }</td>
                        <td>${user.phone }</td>
                        <c:choose>
                            <c:when test="${user.type == 101}">
                                <td class="mdl-data-table__cell--non-numeric">超级管理员</td>
                            </c:when>
                            <c:when test="${user.type == 102}">
                                <td class="mdl-data-table__cell--non-numeric">普通管理员</td>
                            </c:when>
                            <c:otherwise>
                                <td class="mdl-data-table__cell--non-numeric">普通用户</td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <button id="td-menu-${user.id }" class="mdl-button mdl-js-button mdl-button--icon">
                                <i class="material-icons">more_vert</i>
                            </button>
                            <ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
                                for="td-menu-${user.id }">
                                <li class="mdl-menu__item" onclick="javascript:showAddDepositDialog(${user.id});">添加押金</li>
                                <li class="mdl-menu__item" onclick="javascript:showReduceDepositDialog(${user.id}, ${user.deposit});">退还押金</li>
                                <li class="mdl-menu__item" onclick="javascript:showAddToCartDialog(${user.id}, ${5 - user.borrowSum});">添加借阅</li>
                                <%
                                    if (isAdmin) {
                                %>
                                <c:choose>
                                    <c:when test="${user.type!=101 && user.type!=102}">
                                        <li class="mdl-menu__item" onclick="javascript:setAdmin(${user.id}, '${user.nickname}', '${user.phone}', '${user.password}');">设为管理员</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="mdl-menu__item" onclick="javascript:cancelAdmin(${user.id});">取消管理员</li>
                                    </c:otherwise>
                                </c:choose>
                                <%
                                    }
                                %>
                            </ul>
                        </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

        <!-- 添加押金对话框 -->
        <dialog class="add_deposit_dialog mdl-dialog">
            <h4 class="mdl-dialog__title add_deposit_dialog_title">添加押金</h4>
            <div class="mdl-dialog__content">
                <ul class="add_deposit_dialog-dialog-list mdl-list">
                    <li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="number" id="add_deposit_dialog-deposit">
								<label class="mdl-textfield__label" for="add_deposit_dialog-deposit">新增押金数(元)</label>
							</div>
						</span>
                    </li>
                </ul>
            </div>
            <div class="mdl-dialog__actions">
                <button type="button" class="mdl-button agree mdl-js-button">提交</button>
                <button type="button" class="mdl-button close">取消</button>
            </div>
        </dialog>

        <!-- 退还押金对话框 -->
        <dialog class="reduce_deposit_dialog mdl-dialog">
            <h4 class="mdl-dialog__title reduce_deposit_dialog_title">退还押金</h4>
            <div class="mdl-dialog__content">
                <ul class="mdl-list">
                    <li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="number" id="reduce_deposit_dialog-deposit">
								<label class="mdl-textfield__label" for="reduce_deposit_dialog-deposit">退还押金数(元)</label>
							</div>
						</span>
                    </li>
                </ul>
            </div>
            <div class="mdl-dialog__actions">
                <button type="button" class="mdl-button agree mdl-js-button">提交</button>
                <button type="button" class="mdl-button close">取消</button>
            </div>
        </dialog>

        <!-- 添加借阅对话框 -->
        <dialog class="add_to_cart_dialog mdl-dialog" style="width: 500px;">
            <h4 class="mdl-dialog__title add_to_cart_dialog_title">添加借阅</h4>
            <div class="mdl-dialog__content">
                <input type="hidden" id="add_to_cart-userid">
                <input type="hidden" id="add_to_cart-count">
                <div class="mdl-grid--no-spacing">
                    <div class="mdl-textfield mdl-js-textfield mdl-cell mdl-cell--9-col">
                        <input class="mdl-textfield__input" type="text" id="add_to_cart_dialog-keyword">
                        <label class="mdl-textfield__label" for="add_to_cart_dialog-keyword">书名/作者/出版社</label>

                        <ul class="mdl-menu mdl-menu--bottom-left mdl-js-menu mdl-js-ripple-effect"
                            data-mdl-for="add_to_cart_dialog-keyword" id="add_to_cart_dialog-menu"></ul>
                    </div>
                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-cell mdl-cell--2-col" onclick="javascript:searchProduct();">
                        搜索
                    </button>
                </div>
                <ul class="mdl-list" id="add-to-cart-list"></ul>
            </div>
            <div class="mdl-dialog__actions">
                <button type="button" onclick="javascript:addToCart();" class="mdl-button agree mdl-js-button">提交</button>
                <button type="button" class="mdl-button close">取消</button>
            </div>
        </dialog>

        <!-- 退还押金对话框 -->
        <dialog class="add_phone_password_dialog mdl-dialog">
            <h4 class="mdl-dialog__title" id="add_phone_password_dialog-title"></h4>
            <div class="mdl-dialog__content">
                <ul class="mdl-list">
                    <li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
							<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="add_phone_password_dialog-phone">
								<label class="mdl-textfield__label" for="add_phone_password_dialog-phone">手机号</label>
							</div>
                    	</span>
                    </li>
                    <li class="mdl-list__item">
						<span class="mdl-list__item-primary-content">
						   <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
								<input class="mdl-textfield__input" type="text" id="add_phone_password_dialog-password">
								<label class="mdl-textfield__label" for="add_phone_password_dialog-password">密码</label>
							</div>
						</span>
                    </li>
                </ul>
            </div>
            <div class="mdl-dialog__actions">
                <button type="button" class="mdl-button agree mdl-js-button">提交</button>
                <button type="button" class="mdl-button close">取消</button>
            </div>
        </dialog>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>

            var addDepositDialog = document.querySelector('.add_deposit_dialog');
            var reduceDepositDialog = document.querySelector('.reduce_deposit_dialog');
            var addToCartDialog = document.querySelector(".add_to_cart_dialog");
            var addPhoneAndPasswordDialog = document.querySelector(".add_phone_password_dialog");

            function showAddDepositDialog(userId) {
                addDepositDialog.querySelector('.agree').addEventListener('click', function() {
                    var deposit = $("#add_deposit_dialog-deposit").val();
                    addDeposit(userId, deposit);
                });

                /* 绑定删除对话框取消按钮事件 */
                addDepositDialog.querySelector('.close').addEventListener('click', function() {
                    addDepositDialog.close();
                });

                addDepositDialog.showModal();
            }

            function addDeposit(userId, deposit) {
                $.post("User_addDeposit.action",
                    {userId: userId, newDeposit: deposit},
                    function(result){
                        var result = eval('('+result+')');
                        addDepositDialog.close();
                        if (result.success) {
                            showMsg("添加押金成功！");
                            setTimeout(refreshPage, 1000);
                        } else {
                            showMsg(result.errorMsg);
                        }
                    }
                );
            }

            function showReduceDepositDialog(userId, currentDeposit) {
                reduceDepositDialog.querySelector('.agree').addEventListener('click', function() {
                    var deposit = $("#reduce_deposit_dialog-deposit").val();
                    if (deposit <= currentDeposit) {
                        reduceDeposit(userId, deposit);
                    } else {
                        alert("退还押金不可大于" + currentDeposit + "元！");
                    }

                });

                /* 绑定删除对话框取消按钮事件 */
                reduceDepositDialog.querySelector('.close').addEventListener('click', function() {
                    reduceDepositDialog.close();
                });

                reduceDepositDialog.showModal();
            }

            function reduceDeposit (userId, deposit) {
                $.post("User_reduceDeposit.action",
                    {userId: userId, newDeposit: deposit},
                    function(result){
                        var result = eval('('+result+')');
                        reduceDepositDialog.close();
                        if (result.success) {
                            showMsg("退还押金成功！");
                            setTimeout(refreshPage, 1000);
                        } else {
                            showMsg(result.errorMsg);
                        }
                    }
                );
            }

            $('#add_to_cart_dialog-keyword').bind('keypress',function(event){
                if(event.keyCode == "13") {
                    event.stopPropagation();
                    searchProduct();
                    return false;
                }
            });


            function showAddToCartDialog(userId, count) {
                $("#add_to_cart-userid").val(userId);
                $("#add_to_cart-count").val(count);

                addToCartDialog.querySelector('.close').addEventListener('click', function() {
                    addToCartDialog.close();
                });

                addToCartDialog.showModal();
            }

            function addToCart() {
                var count = $("#add_to_cart-count").val();
                var selectedSpan=$(".cart-list-product-id");
                if(selectedSpan.length==0){
                    alert('请添加要借阅的书籍');
                } else if(selectedSpan.length > count) {
                    alert('当前最多可借阅' + count + '本书籍');
                }else {
                    var strIds=[];
                    for(var i=0;i<selectedSpan.length;i++){
                        strIds.push(selectedSpan.eq(i).val());
                    }
                    var ids=strIds.join(",");

                    var userId = $("#add_to_cart-userid").val();

                    $.post("Order_addToOrderFromAdmin.action",
                        {userId: userId, ids: ids},
                        function(result){
                            var result = eval('('+result+')');
                            if (result.success) {
                                addToCartDialog.close();
                                alert("添加借阅成功！")
                            } else {
                                showMsg(result.errorMsg);
                            }
                        }
                    );
                }
            }

            function searchProduct() {
                $("#add_to_cart_dialog-menu").text("");
                var keyword = $("#add_to_cart_dialog-keyword").val();
                if (keyword.length > 0) {
                    $.post("Product_searchFromUser.action",
                        {keyword: keyword},
                        function(result){
                            var result = eval('('+result+')');
                            if (result.success) {
                                var products = result.products;
                                for (var i = 0; i < products.length; i++) {
                                    var product = products[i];
                                    var cover = product.covers[0];
                                    if (cover == null || typeof(cover) == "undefined") {
                                        cover = "admin/images/favicon.ico".toString();
                                    }
                                    var item = createProductMenuItem(product.id,
                                        product.name,
                                        cover,
                                        product.manufacturer + " " + product.author);
                                    $("#add_to_cart_dialog-menu").append(item);
                                }
                                $("#add_to_cart_dialog-keyword").click();
                            } else {
                                showMsg(result.errorMsg);
                            }
                        }
                    );
                }
            }

            function addToCartList(id, name, cover, content) {
                $("#add_to_cart_dialog-keyword").click();
                var item = createCartListItem(id, name, cover, content);
                $("#add-to-cart-list").append(item);
            }

            function removeFromCartList(productId) {
                $("#cart-list-" + productId).remove();
            }

            function createProductMenuItem(id, name, cover, content) {
                var item = "<li class='mdl-menu__item' value='" + id + "'"
                    + " onclick='javascript:addToCartList(" + id + ",\"" + name + "\",\"" + cover + "\",\"" + content + "\");'>" + name + "</li>";
                return item;
            }

            function createCartListItem(id, name, cover, content) {
                var item = "<li class='mdl-list__item mdl-list__item--three-line' id='cart-list-" + id + "'>"
                            + "<input type='hidden' class='cart-list-product-id' value='" + id + "'>"
                            + "<span class='mdl-list__item-primary-content'>"
                            + "<img src=" + cover + " class='mdl-list__item-avatar product-cover'/>"
                            + "<span>" + name + "</span>"
                            + "<span class='mdl-list__item-text-body'>" + content + "</span>"
                            + "</span>"
                            + "<span class='mdl-list__item-secondary-content'>"
                            + "<a class='mdl-list__item-secondary-action' href='javascript:removeFromCartList(" + id + ");'>"
                            + "<i class='material-icons'>delete</i></a></span></li>";
                return item;
            }

            function setAdmin(userId, nickname, currentPhone, currentPassword) {
                if (!currentPhone || !currentPassword) {
                    alert('请先为用户绑定手机号与密码');
                    showAddPhoneAndPasswordDialog(userId, nickname, currentPhone, currentPassword);
                } else if (currentPhone == "" || currentPassword == "") {
                    alert('请先为用户绑定手机号与密码');
                    showAddPhoneAndPasswordDialog(userId, nickname, currentPhone, currentPassword);
                } else {
                    $.post("User_setAdmin.action",
                        {userId: userId},
                        function(result){
                            var result = eval('('+result+')');
                            if (result.success) {
                                showMsg("设置管理员成功");
                                setTimeout(refreshPage, 1000);
                            } else {
                                alert(result.errorMsg);
                            }
                        }
                    );
                }
            }

            function cancelAdmin(userId) {
                $.post("User_cancelAdmin.action",
                    {userId: userId},
                    function(result){
                        var result = eval('('+result+')');
                        if (result.success) {
                            showMsg("取消管理员成功");
                            setTimeout(refreshPage, 1000);
                        } else {
                            alert(result.errorMsg);
                        }
                    }
                );
            }

            function showAddPhoneAndPasswordDialog(userId, nickname, currentPhone, currentPassword) {
                $("#add_phone_password_dialog-title").text(nickname);
                if (currentPhone && currentPhone != "") {
                    $("#add_phone_password_dialog-phone").val(currentPhone);
                }
                if (currentPassword && currentPassword != "") {
                    $("#add_phone_password_dialog-password").val(currentPassword);
                }

                addPhoneAndPasswordDialog.querySelector('.agree').addEventListener('click', function() {
                    var phone = $("#add_phone_password_dialog-phone").val();
                    var password = $("#add_phone_password_dialog-password").val();
                    if(!phone || !isPhoneNo(phone)) {
                        alert("请输入正确的手机号");
                        return false;
                    }
                    if (password) {
                        if (password == "") {
                            alert("密码不可为空");
                            return false;
                        }
                        if (password.length > 16) {
                            alert("密码不可超过16位")
                            return false;
                        }
                    } else {
                        alert("请输入用户密码");
                        return false;
                    }
                    addPhoneAndPassword(userId, phone, password);
                });

                /* 绑定删除对话框取消按钮事件 */
                addPhoneAndPasswordDialog.querySelector('.close').addEventListener('click', function() {
                    addPhoneAndPasswordDialog.close();
                });

                addPhoneAndPasswordDialog.showModal();
            }

            function isPhoneNo(phone) {
                var pattern = /^1[34578]\d{9}$/;
                return pattern.test(phone);
            }

            function addPhoneAndPassword(userId, phone, password) {
                $.post("User_addPhoneAndPassword.action",
                    {userId: userId, adminPhone: phone, adminPassword: password},
                    function(result){
                        var result = eval('('+result+')');
                        if (result.success) {
                            showMsg("保存成功");
                            setTimeout(refreshPage, 1000);
                        } else {
                            showMsg(result.errorMsg);
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
