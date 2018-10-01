<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/ckform.js"></script>
<script type="text/javascript" src="js/common.js"></script>

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
	<button type="button" class="btn btn-success" id="addproducts">Add Product </button>
	<table class="table table-bordered table-hover definewidth m10">
		<thead>
			<tr>
				<th></th>
				<th>Product Picture</th>
				<th>Product Name</th>
				<th>Product Price</th>
				<th>Storage</th>
				<th style="min-width: 70px">Edit</th>
			</tr>
		</thead>
		<c:if test="${empty products}">
		<tbody>
			<tr>
				<td colspan="7">No Data</td>
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
					">Edit</a>&nbsp;/&nbsp;
					<a class="deleteOneProduct nohref" 
					href="${pageContext.request.contextPath}/FrontServlet?module=Product&command=DeleteProduct&
					productId=${product.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
		</c:if>
	</table>
</body>
</html>
<script>
	$(function() {
		//Adding events to checkbox
		$(".checkedProduct").click(function(){
			var cbl = $(".checkedProduct").length;//.checkedProduct's length
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
				alert("Please choose the products you want to operate! ");
				return false;
			}
			if(!confirm("Are you sure you want to delete the selected products?")){
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
		//Delete the product
		$(".deleteOneProduct").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			if(confirm("Are you sure you want to delete this product?")){
				window.location.href = href;
			}
			return false;
		});
		$('#addproducts').click(function() {
			var criteriaProduct = $("#criteriaProductName").serialize();
			window.location.href = "/managerAddproducts.jsp";
		});
		//Add an event to the 'a' connection with the query information 
		$("a").not(".nohref").click(function(){
			var href = this.href;
			var criteriaProduct = $("#criteriaProductName").serialize();
			href = href + "&" +criteriaProduct;
			window.location.href = href;
			return false;
		});
	});
</script>