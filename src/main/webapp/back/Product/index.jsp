<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/back/Css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="/back/Css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="/back/Css/style.css" />
<script type="text/javascript" src="/back/Js/jquery.js"></script>
<script type="text/javascript" src="/back/Js/bootstrap.js"></script>
<script type="text/javascript" src="/back/Js/ckform.js"></script>
<script type="text/javascript" src="/back/Js/common.js"></script>

<style type="text/css">
table{
	overflow: auto;
	overflow-x:auto;
}
table tbody tr td.checkedtd{
	min-width: 5px;
}
table tbody tr td.productName a:link, a:visited, a:active {
    text-decoration: none;
}
table tbody tr td.productName a:hover { color: #D93600; text-decoration: none;}
table tbody tr td.productName a {
    outline: medium none;
    text-decoration: none;
	display: block;
	color: #333;
	font-size: 16px;
	line-height: 18px;
	margin-left: 5px;
	text-overflow: ellipsis;
	width: 200px;
	height: 36px;
	overflow: hidden;
}


#checkedAllProduct {
    display: block;
    float: left;
    margin: 4px 0 5px 33px;
    width: 0;
}
.changeProductStatus{
	background-color: white;
	border: 0px solid white;
}


body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}
</style>
</head>
<body>
<jsp:include page='/FrontServlet?module=manager&command=ManagerHeader' flush="true"></jsp:include>
	<form class="form-inline definewidth m20" action="${pageContext.request.contextPath}/back/Product/
	queryProducts.bg" method="post">
		<button type="button" class="btn btn-success" id="addproducts">添加商品</button>
	</form>
	<table class="table table-bordered table-hover definewidth m10">
		<thead>
			<tr>
				<th></th>
				<th>商品图片</th>
				<th>商品名称</th>
				<th>商品单价</th>
				<th>库存</th>
				<th style="min-width: 70px">编辑</th>
			</tr>
		</thead>
		<c:if test="${empty products}">
		<tbody>
			<tr>
				<td colspan="7">没有数据</td>
			</tr>
		</tbody>
		</c:if>
		<c:if test="${not (empty products)}">
		<tbody>
			<c:forEach items="${products}" var="product" varStatus="vs">
				<tr>
					<td class="checkedtd"><input class="checkedProduct" type="checkbox" name="productId" 
					value="${product.id}"></td>
					<td>
					<img src="${pageContext.request.contextPath }/${product.productImagePath }" 
					style="cursor: pointer;height: 40px;width: 40px" 
					></td>
					<td class="productName">
					${product.productName}</td>
					<td>${product.productPrice}</td>
					<td>${product.storeNum}</td>
					<td><a
							href="${pageContext.request.contextPath}/FrontServlet?module=Product&command=EditProduct&productId=${product.id}
					">编辑</a>&nbsp;/&nbsp;
					<a class="deleteOneProduct nohref" 
					href="${pageContext.request.contextPath}/FrontServlet?module=Product&command=DeleteProduct&
					productId=${product.id}">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
		</c:if>
	</table>
</body>
</html>
<script>
	$(function() {
		//为checkbox添加事件
		$(".checkedProduct").click(function(){
			var cbl = $(".checkedProduct").length;//.checkedProduct的长度
			$("#checkedAllProduct").prop("checked",$(".checkedProduct:checked").length==cbl);
		});
		$("#checkedAllProduct").click(function(){
			$(".checkedProduct").prop("checked",this.checked);
		});
		$("#resetQuery").click(function(){
			$("#criteriaProductName").val("");
		});
		$("#deleteCheckedProduct").click(function(){
			var cbl = $(".checkedProduct:checked").length;
			if(cbl <= 0){
				alert("请选择要操作的商品！");
				return false;
			}
			if(!confirm("确认删除选中的商品吗？")){
				return false;
			}
			var href = this.href;
			var checkedProduct = $(".checkedProduct").serialize();
			href = href + "&" +checkedProduct;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			//alert(href);
			return false;
		});
		//删除某一件商品
		$(".deleteOneProduct").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			if(confirm("确认删除该商品吗？")){
				window.location.href = href;
			}
			return false;
		});
		$('#addproducts').click(function() {
			var criteriaProduct = $("#criteriaProductName").serialize();
			window.location.href = "/back/Product/addproducts.jsp";
		});
		//给a连接添加事件，将查询信息带上
		$("a").not(".nohref").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			return false;
		});
	});
</script>