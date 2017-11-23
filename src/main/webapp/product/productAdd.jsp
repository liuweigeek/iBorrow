<%@ page import="com.zhinang.iborrow.constant.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="太原才智教育科技有限公司">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
	<title>${webpagename }</title>
	
	<link href="${pageContext.request.contextPath}/css/tether.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/tether.min.js"></script>

    <link href="${pageContext.request.contextPath}/css/bootstrap4.min.css" rel="stylesheet">


    <script src="${pageContext.request.contextPath}/lib/jquery-3.1.1.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/bootstrap4.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdl-grid-cell.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/material-icon.css">

	<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>

	<link href="${pageContext.request.contextPath}/product/productAdd.css" rel="stylesheet">

	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/uploadPreview.min.js"></script>
    <script src="${pageContext.request.contextPath}/product/productAdd.js"></script>

	<script type="text/javascript">
		$(function () {
			$("#cover1").uploadPreview({ Img: "cover1Pre", Width: 150, Height: 150 });
			$("#cover2").uploadPreview({ Img: "cover2Pre", Width: 150, Height: 150 });
			$("#cover3").uploadPreview({ Img: "cover3Pre", Width: 150, Height: 150 });
			$("#cover4").uploadPreview({ Img: "cover4Pre", Width: 150, Height: 150 });
			$("#cover5").uploadPreview({ Img: "cover5Pre", Width: 150, Height: 150 });
		});

	</script>

    <%
    	if(session.getAttribute(Constant.key_value.CURRENT_ADMIN) == null){
			response.sendRedirect("admin.action");
			return;
		}
	%>
	
</head>

<body>
    <div class="container">
    	<form id="save-product-form" action="Product_save" method="post" onsubmit="return checkForm();" enctype="multipart/form-data">
	    	<input type="hidden" name="id" value="${product.id }">
		    <%--轮播图--%>
            <div class="mdl-grid">
			    <div class="mdl-cell mdl-cell--1-col mdl-cell--0-col-tablet mdl-cell--0-col-phone"></div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="text-center">
						<span>轮播图1</span>
						<img id="cover1Pre" class="img-thumbnail rounded" style="width: 150px; height:150px;"
								src="${product.covers[0] }" alt="">
					</div>
					<input type="file" id="cover1" name="cover" style="font-size: 12px;">
				</div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="text-center">
						<span>轮播图2</span>
						<img id="cover2Pre" class="img-thumbnail rounded" style="width: 150px; height:150px;"
								src="${product.covers[1] }" alt="">
					</div>
					<input type="file" id="cover2" name="cover" style="font-size: 12px;">
				</div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="text-center">
						<span>轮播图3</span>
						<img id="cover3Pre" class="img-thumbnail rounded" style="width: 150px; height:150px;"
								src="${product.covers[2] }" alt="">
					</div>
					<input type="file" id="cover3" name="cover" style="font-size: 12px;">
				</div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="text-center">
						<span>轮播图4</span>
						<img id="cover4Pre" class="img-thumbnail rounded" style="width: 150px; height:150px;"
								src="${product.covers[3] }" alt="">
					</div>
					<input type="file" id="cover4" name="cover" style="font-size: 12px;">
				</div>
				<div class="mdl-cell mdl-cell--2-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="text-center">
						<span>轮播图5</span>
						<img id="cover5Pre" class="img-thumbnail rounded" style="width: 150px; height:150px;"
								src="${product.covers[4] }" alt="">
					</div>
					<input type="file" id="cover5" name="cover" style="font-size: 12px;">
				</div>
				<div class="mdl-cell mdl-cell--1-col mdl-cell--0-col-tablet mdl-cell--0-col-phone"></div>
		    </div>


			<div class="mdl-grid">
                <div class="mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                    <%--类型标签--%>
                    <%--<div class="form-group row" style="margin-bottom: 0;">
                        <label for="search-product-type-keyword" class="col-1 col-form-label">类型标签</label>
                        <div class="col-11 row">
                            <div class="dropdown col-3">
                                <div class="input-group">
                                    <input type="text" id="search-product-type-keyword" class="form-control dropdown-toggle" data-toggle="dropdown" placeholder="搜索分类标签">
                                    <div class="dropdown-menu search-product-type-menu">
                                    </div>
                                    <button type="button" class="input-group-addon" onclick="javascript:searchTypeByKeyword();">
                                        <i class="nav-link-icon material-icons">search</i>
                                    </button>
                                </div>
                            </div>
                            <div id="selected-product-types-zone" class="col-9 card zone-card">
                                <c:forEach items="${product.productTypes }" var="productType">
                                    <span class='badge badge-pill badge-primary badge-type'>${productType.title }</span>
                                    <input type='hidden' name='productTypeId' value="${productType.id }">
                                </c:forEach>
                            </div>
                        </div>
                    </div>--%>

                    <%--推荐分类--%>
					<div class="form-group row" style="margin-bottom: 0;">
						<label for="search-homeclassify-keyword" class="col-1 col-form-label">推荐分类</label>
						<div class="col-11 row">
							<div class="dropdown col-3">
								<div class="input-group">
									<input type="text" id="search-homeclassify-keyword" class="form-control dropdown-toggle" data-toggle="dropdown" placeholder="搜索推荐分类">
									<div class="dropdown-menu search-homeclassify-menu"></div>
									<button type="button" class="input-group-addon" onclick="javascript:searchHomeClassifyByKeyword();">
										<i class="nav-link-icon material-icons">search</i>
									</button>
								</div>
							</div>
							<div id="selected-homeclassify-zone" class="col-9 zone-card">
								<c:forEach items="${product.homeClassifies }" var="homeClassify">
									<span id="homeclassify-chip-${homeClassify.id}" class="homeclassify-chip"><input type="hidden" value="${homeClassify.id}">
                                        ${homeClassify.name}
                                            <span class="close-icon-span" onclick="javascript:removeChip('homeclassify-chip-${homeClassify.id}');">
                                                &times;
                                            </span>
                                    </span>
                                </c:forEach>
                            </div>
						</div>
					</div>

                    <%--推荐版块--%>
					<div class="form-group row" style="margin-bottom: 0;">
						<label for="search-homezone-keyword" class="col-1 col-form-label">推荐板块</label>
						<div class="col-11 row">
							<div class="dropdown col-3">
								<div class="input-group">
									<input type="text" id="search-homezone-keyword" class="form-control dropdown-toggle" data-toggle="dropdown" placeholder="搜索推荐版块">
									<div class="dropdown-menu search-homezone-menu"></div>
									<button type="button" class="input-group-addon" onclick="javascript:searchHomeZoneByKeyword();">
										<i class="nav-link-icon material-icons">search</i>
									</button>
								</div>
							</div>
							<div id="selected-homezone-zone" class="col-9 zone-card">
								<c:forEach items="${product.homeZones }" var="homeZone">
                                    <span id="homezone-chip-${homeZone.id}" class="homezone-chip"><input type="hidden" value="${homeZone.id}">
                                        ${homeZone.name}
                                            <span class="close-icon-span" onclick="javascript:removeChip('homezone-chip-${homeZone.id}');">
                                                &times;
                                            </span>
                                    </span>
								</c:forEach>
                            </div>
						</div>
					</div>
				</div>

                <%--左侧控件--%>
				<div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="form-group row">
						<div class="col-6">
							<div class="input-group mb-2 mr-sm-2 mb-sm-0">
								<div class="input-group-addon">名称</div>
								<input type="text" class="form-control" value="${product.name }" id="product-name-input" name="name">
							</div>
						</div>
						<div class="col-6">
							<div class="input-group mb-2 mr-sm-2 mb-sm-0">
								<div class="input-group-addon">作者</div>
								<input type="text" class="form-control" value="${product.author }" id="product-author-input" name="author">
							</div>
						</div>
					</div>
					<div class="form-group row">
						<label for="product-manufacturer-input" class="col-2 col-form-label">生产商/出版社</label>
						<div class="col-10">
							<input class="form-control" type="text" value="${product.manufacturer }" id="product-manufacturer-input" name="manufacturer">
						</div>
					</div>
					<div class="form-group row">
						<label for="product-source-input" class="col-2 col-form-label">来源</label>
						<div class="col-10">
							<input class="form-control" type="text" value="${product.source }" id="product-source-input" name="source">
						</div>
					</div>
					<div class="form-group row">
						<label for="product-number-input" class="col-2 col-form-label">编号</label>
						<div class="col-10">
							<input class="form-control" type="number" value="${product.number }" id="product-number-input" name="number">
						</div>
					</div>
					<div class="form-group row">
						<label for="product-inventory-input" class="col-2 col-form-label">库存</label>
						<div class="col-10">
							<input class="form-control" type="number" value="${product.inventory }" id="product-inventory-input" name="inventory">
						</div>
					</div>
					<div class="form-group row">
						<label for="product-placement-input" class="col-2 col-form-label">上架位置</label>
						<div class="col-10">
							<input class="form-control" type="text" value="${product.placement }" id="product-placement-input" name="placement">
						</div>
					</div>
					<div class="form-group row">
						<label for="product-language-input" class="col-2 col-form-label">语言</label>
						<div class="col-10">
							<select class="form-control custom-select" id="product-language-input" name="language">
								<option value="中文">中文</option>
								<option value="英文">英文</option>
								<option value="双语">双语</option>
							</select>
						</div>
					</div>
				</div>
                <%--右侧控件--%>
				<div class="mdl-cell mdl-cell--6-col mdl-cell--4-col-tablet mdl-cell--4-col-phone">
					<div class="form-group row">
						<div class="col-6">
							<div class="input-group mb-2 mr-sm-2 mb-sm-0">
								<div class="input-group-addon">进价</div>
								<input type="number" step="0.1" class="form-control" value="${product.purchasePrice }" id="product-purchasePrice-input" name="purchasePrice">
							</div>
						</div>
						<div class="col-6">
							<div class="input-group mb-2 mr-sm-2 mb-sm-0">
								<div class="input-group-addon">售价</div>
								<input type="number" step="0.1" class="form-control" value="${product.salePrice }" id="product-salePrice-input" name="salePrice">
							</div>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-4">
							<label class="custom-control custom-checkbox">
								<c:choose>
									<c:when test="${product.audio }">
										<input type="checkbox" checked value="1" onclick="this.value=(this.value==0)?1:0"
											class="custom-control-input" id="product-audio-input" name="hasAudio">
									</c:when>
									<c:otherwise>
										<input type="checkbox" value="0" onclick="this.value=(this.value==0)?1:0"
											class="custom-control-input" id="product-audio-input" name="hasAudio">
									</c:otherwise>
								</c:choose>
								<!-- <input type="checkbox" value="0" onclick="this.value=(this.value==0)?1:0" class="custom-control-input" id="product-audio-input" name="audio"> -->
								<span class="custom-control-indicator"></span>
								<span class="custom-control-description">包含音频</span>
							</label>
						</div>
						<div class="col-4">
							<select class="form-control custom-select" id="product-type-input" name="type">
								<option id="product-type-<%=Constant.productType.BOOK %>" value="<%=Constant.productType.BOOK %>">普通书籍</option>
								<option id="product-type-<%=Constant.productType.TALKING_PEN %>" value="<%=Constant.productType.TALKING_PEN %>">支持点读</option>
								<option id="product-type-<%=Constant.productType.FANDOU %>" value="<%=Constant.productType.FANDOU %>">凡豆机器人</option>
								<option id="product-type-<%=Constant.productType.BOTH %>" value="<%=Constant.productType.BOTH %>">点读加机器人</option>
							</select>
						</div>
						<div class="col-4">
							<select class="form-control custom-select" id="product-bingding-input" name="bingding">
								<option id="product-bingding-<%=Constant.bingding.PAPERBACK %>" value="<%=Constant.bingding.PAPERBACK %>">平装</option>
								<option id="product-bingding-<%=Constant.bingding.HARDCOVER %>" value="<%=Constant.bingding.HARDCOVER %>">精装</option>
							</select>
						</div>
					</div>
					<textarea id="product-introduction-input" name="introduction" class="ckeditor" cols="50">
						${product.introduction }
					</textarea>
				</div>
                <%--保存及取消按钮--%>
				<div class="form-group row mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
					<div class="col-2"></div>
					<button type="button" onclick="javascript:history.go(-1)" class="btn btn-secondary btn-lg col-3">返回</button>
					<div class="col-2"></div>
					<button type="submit" class="btn btn-primary btn-lg col-3">保存</button>
					<div class="col-2"></div>
				</div>
			</div>
		</form>
    </div>

    <script type="text/javascript">
		$(function(){
			 var type=${product.type};
			 if (type != null) {
			 	$("#product-type-" + type).attr("selected", "selected");
			 }
		})
		
		$(function(){
			 var bingding=${product.bingding};
			 if (bingding != null) {
			 	$("#product-bingding-" + bingding).attr("selected", "selected");
			 }
		})

    </script>
</body>
</html>
