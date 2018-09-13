<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Create order successfully</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--gz --%>
<link rel="stylesheet" href="css/cart.css" type="text/css">
<link rel="stylesheet" href="css/buyleo.css" type="text/css">
<%--gz --%>
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
</head>
<body>
<jsp:include page='login?method=header' flush="true"></jsp:include>
	<div class="encircle">
		<img src="images/jstj.jpg" alt="success pic">
		<div class="checkout_order_right">
			<h1>
				Create order successfully. Please pay the order.<span>Amount:<i><fmt:formatNumber
					value="${sessionScope.totalPrice}" type="currency"></fmt:formatNumber></i></span>
			</h1>
			<div class="checkout_order_summary">
			</div>
			<div class="checkout_order_intro">
				<div class="checkout_order_same">
					<p>Order No.:</p>
					<span>${sessionScope.orderNum }</span>
				</div>
				<div class="checkout_order_same">
					<p>Shipping address:</p>
					<i>${sessionScope.address.sendMan }</i> <i>${sessionScope.address.sendPhone }</i> <i>${sessionScope.address.sendPlace }</i>
				</div>

				<div class="checkout_order_tools ">
					<a class="go_pay" href="payorder?orderNum=${sessionScope.orderNum}" target="_self">
						Pay</a>
					<a href="blank.jsp" class="go_continue">Continue shopping</a>
				</div>
			</div>
		</div>
	</div>

	<br /><br /><br /><br /><br /><br />
</body>
</html>