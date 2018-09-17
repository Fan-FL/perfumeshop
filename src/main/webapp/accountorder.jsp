<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>PerfumeStore_MyOrder</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.mycar-index{
		margin: 0 auto;
		width: 1200px;
	}
	#cart-wrapper{
		width: 1200px;
		/* height:330px; */
	}
	#cart-wrapper table{
		width: 1200px;
		border-left:1px solid #f5f5f5;
		border-right:1px solid #f5f5f5;
	}
	#cart-wrapper table th{
		background: #f5f5f5;
		height: 30px;
		text-align:center;
	}
	#cart-wrapper table td{
		border-bottom: 1px solid #f5f5f5;
		color: #333;
		height: 70px;
		font-size: 14px;
	}
	#page{
		color: #333;
		font-size: 14px;
	}
	a:LINK{
		text-decoration: none;
		color: #555;
	}
	a:hover{
		text-decoration: none;
		cursor:pointer;
		background:#4CB1CA;
	}
	a:VISITED{
		text-decoration: none;
		color: #555;
	}
	.order-blank{  
	    height:100px;
	    line-height:100px;
	    font-size:16px;
	    text-align:center;
	   	width:100%;
	    margin:30px 0px;
    }
    img{
		float: left;
		display: block;
		width: 60px;
		height: 60px;
		cursor: pointer;
		margin-top:2px;
		margin-left: 10px;
	}
    .pname{
		float: left;
		width: 310px;
		margin-top:20px;
		margin-left:10px;
		text-algin: left;
    }
</style>
</head>
<body>
	<div class="mycar-index">
		<c:choose>
			<c:when test="${!(empty ordermsg)}">
				<div id="cart-wrapper">
					<table cellspacing="1.5">
						<thead>
							<tr>
								<th width="100px">OrderNo.</th>
								<th width="400px">Product Name</th>
								<th width="125px">Price</th>
								<th width="125px">Quantity</th>
								<th width="125px">Total price</th>
								<th width="150px">Note</th>
								<th width="175px">Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ordermsg}" var="order">
								<c:if test="${fn:length(order.product)==1}">
									<tr>
										<td align="center">
											${order.orderNum}
										</td>
										<c:forEach items="${order.product}" var="product">
											<td align="left">
												<a target="_blank" 
												   href="viewproductdetail?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="viewproductdetail?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
										</c:forEach>
										<td align="center">
											<c:if test="${!(empty order.note)}">${order.note}</c:if>
											<c:if test="${empty order.note}">None</c:if>
										</td>
										<td align="center"><a
												href="deleteorder?orderNum=${order.orderNum}"
												onclick="return confirm('Are you sure to delte?');">
											Delete</a><br/>
											<c:if test="${order.orderStatus==0}"><a
													target="_blank"
													href="payorder?orderNum=${order.orderNum}"><font
													color="red">Pay</font></a></c:if>
											<c:if test="${order.orderStatus==1}"><a
													href="receiveproduct?orderNum=${order.orderNum}"><font color="red">Confirm receipt</font></a></c:if>
											<c:if test="${order.orderStatus==2}">
												Done<br/>
											</c:if>
										</td>
									</tr>
								</c:if>
								<c:if test="${fn:length(order.product)!=1}">
									<tr>
										<td align="center" rowspan="${fn:length(order.product)}">
											${order.orderNum}
										</td>
										<c:forEach items="${order.product}" var="product" begin="0" end="0">
											<td align="left">
												<a target="_blank" href="viewproductdetail?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="viewproductdetail?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center" rowspan="${fn:length(order.product)}">
												<c:if test="${!(empty order.note)}">${order.note}</c:if>
												<c:if test="${empty order.note}">None</c:if>
											</td>
											<td align="center" rowspan="${fn:length(order.product)}"><a href="deleteorder?orderNum=${order.orderNum}" onclick="return confirm('Are you sure to delete?');">Delete</a><br/>
												<c:if test="${order.orderStatus==0}"><a
														target="_blank"
														href="payorder?orderNum=${order.orderNum}"><font color="red">Pay</font></a></c:if>
												<c:if test="${order.orderStatus==1}"><a
														href="receiveproduct?orderNum=${order.orderNum}"><font color="red">Confirm receipt</font></a></c:if>
												<c:if test="${order.orderStatus==2}">
													Done<br/>
												</c:if>
											</td>
										</c:forEach>
									</tr>
									<c:forEach items="${order.product}" var="product" begin="1" end="${fn:length(order.product)-1}">
										<tr>
											<td align="left">
												<a target="_blank" href="viewproductdetail?productid=${product.productId }">
													<img src="${product.productImagePath }"></a>
												<div class="pname"><a target="_blank" href="viewproductdetail?productid=${product.productId }">${product.productName}</a></div>
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
											<td align="center">
												${product.saleCount}
											</td>
											<td align="center">
												$<fmt:formatNumber
													value="${product.productPrice*product.saleCount}" maxFractionDigits="2"></fmt:formatNumber>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="order-blank">
					You don't have order yet, <a
						onclick="window.parent.location.href='AboutBlank.jsp'"><font
						color="#555">please go for shopping</font></a>吧！
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<br/>
</body>
</html>