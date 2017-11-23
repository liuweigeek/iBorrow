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

            <script>

                function searchCheck() {
                    var keyword = $('#admin-search-keyword').val();
                    if (keyword == null || keyword == '') {
                        return false;
                    }
                    $('#admin-search-form').attr('action', 'Wish_list?keyword=' + keyword);
                }

            </script>

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
					<th class="mdl-data-table__cell--non-numeric">心愿书单的书名和出版社</th>
					<th class="mdl-data-table__cell--non-numeric">联系方式</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wishList }" var="wish">
					<tr>
						<td>${wish.id }</td>
						<td class="mdl-data-table__cell--non-numeric">${wish.user.nickname }</td>
						<td class="mdl-data-table__cell--non-numeric">${wish.text }</td>
						<td>${wish.phone }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="msg-toast" class="mdl-js-snackbar mdl-snackbar">
			<div class="mdl-snackbar__text"></div>
			<button class="mdl-snackbar__action" type="button"></button>
		</div>
		
		<script>
			
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
