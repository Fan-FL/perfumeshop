<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>PerfumeShop_cart</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<%--gz --%>
<link rel="stylesheet" href="css/cart2.css" type="text/css">
<script type="text/javascript" src="js/checkout.js"></script>
<%--gz --%>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<jsp:include page='FrontServlet?module=User&command=UserHeader' flush="true"></jsp:include>
	<div class="clear"></div>
	<div class="mycar-index">
		<h1>Cart</h1>
		<div
			style="width: 1200px; height: 1px; overflow: hidden; clear: both;"></div>


		<div id="cart-wrapper">
			<c:if test="${empty requestScope.cartProductMap }">
				<div class="cart-blank">
					Your cart is empty. please go <a href="blank.jsp">shopping</a>！
				</div>
			</c:if>
			<c:if test="${not (empty requestScope.cartProductMap)}">
				<form action="/FrontServlet?module=order&command=AddOrder" method="post"
					  id="cartFormSubmit">
					<table>
						<thead>
							<tr>
								<th></th>
								<th style="width: 600px;">Name</th>
								<th>price</th>
								<th>Quantity</th>
								<th style="width: 100px;">Subtotal</th>
								<th class="cart_last" style="width: 100px;">Delete</th>
							</tr>
						</thead>
						<tbody id="cartbody">
							<c:forEach items="${requestScope.cartProductMap}" var="cartProduct">
								<tr>
									<td class="checkboxtd">
											<input checked="checked" name="cartId" value="${cartProduct.key.id}" class="checkbox" type="checkbox" >
									</td>
									<td style="width: 600px;"><a target="_blank"
										href="/FrontServlet?module=Product&command=ViewProductDetail&productid=${cartProduct.value.id }"
										class="cart_list_img"><img
											src="${cartProduct.value.productImagePath }"
											style="cursor: pointer;" height="50" width="36"></a>
										<p>
											<a target="_blank"
												href="/FrontServlet?module=Product&command=ViewProductDetail&productid=${cartProduct.value.id }">${cartProduct.value.productName }</a>
										</p></td>
									<td class="mktprice1" style="font-weight: bold; font-size: 14px;">
										<b>${cartProduct.value.productPrice }</b>
									</td>
									<td class="cart-quantity" style="text-align: center;">
										<div class="Numinput">
										<c:if test="${cartProduct.value.productStatus==1}">
											<span class="numadjust decrease">-</span>
											<c:if test="${cartProduct.key.saleCount<=cartProduct.value.storeNum}">
											<input name="num" size="5" value="${cartProduct.key.saleCount}" type="text">
											</c:if> 
											<c:if test="${cartProduct.key.saleCount>cartProduct.value.storeNum}">
											<input class="outOfStoreNum" name="num" size="5" value="${cartProduct.key.saleCount}" type="text">
											</c:if> 
											<span class="numadjust increase">+</span>
										</c:if>
										<c:if test="${cartProduct.value.productStatus==0}">
											<font class="productSaleOut" color="red">off the shelf</font>
										</c:if>
										</div>
									</td>
									<td class="itemTotal"
										style="color: #ff6700; font-size: 14px; font-weight: bold; width: 100px;">
										<b>
										<fmt:formatNumber type="number" value="${cartProduct.key.saleCount*cartProduct.value.productPrice}" maxFractionDigits="2"/>
										</b>
										</td>
									<td class="cart_last" style="width: 100px;"><a href="#"
										class="delete"></a></td>
									<td class="cartId"><input type="hidden"
										value="${cartProduct.key.id }"></td>
									<td class="storeNum"><input type="hidden"
										value="${cartProduct.value.storeNum }"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<!-- total price -->
					<div class="cart-wrapper">
						<div class="yes_bonded">
							<p align="left">&nbsp;&nbsp;<input checked="checked" id="checkAll" type="checkbox">Select all
							&nbsp;&nbsp;<a id="deleteChecked" href="#">Delete</a> </p>
							<span class="c08"> Total price <span class="color03" id="total">
							<fmt:formatNumber value="1" type="currency"></fmt:formatNumber>
							</span>
							</span>
						</div>
						<div class="cart_tools">
							<div class="btn">
								<input class="clean_btn white-btn" value="Clear" type="button">
							</div>
							<div class="btn">
								<input class="returnbuy_btn yellow-btn" value="Continue shopping"
									type="button">
							</div>

							<div class="btn">
								<input id="goToBuy" class="checkout-btn green-btn"
									   value="Check out"
									   type="button">
							</div>
						</div>
					</div>
				</form>
			</c:if>
		</div>
	</div>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
</body>
</html>