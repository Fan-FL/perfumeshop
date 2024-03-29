<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">

</style>
<title>perfume store</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/form.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css"
	media="all" />
<style type="text/css">
	#div1{
		margin-left: 450px;
		margin-bottom: 10px;
	}
	.cont {
    display: block;
    float: left;
    margin: 0 0;
}
</style>
<script type="text/javascript" src="js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<!--start slider -->
<link rel="stylesheet" href="css/fwslider.css" media="all">
<script src="js/jquery-ui.min.js"></script>
<script src="js/css3-mediaqueries.js"></script>
<script src="js/fwslider.js"></script>
<!--end slider -->
<script src="js/jquery.easydropdown.js"></script>
<script type="text/javascript">
	$(function(){
	
	});

</script>
</head>
<body>
<!-- include header -->
	<jsp:include page='FrontServlet?module=User&command=UserHeader' flush="true"></jsp:include>
	<div class="main" style="margin: 0">
		<div class="wrap">
			<div class="section group">
				<div class="cont span_2_of_3" style="width: 100%" >
					<h2 class="head">All products...</h2>
					<c:forEach items="${products}" var="out" varStatus="outvs">
						<c:if test="${(outvs.count-1)%4==0 }">
							<div class="top-box"> 
							<c:forEach items="${products}" var="a" varStatus="vs" begin="${outvs.count - 1}" end="${outvs.count + 2}" >
							<div class="col_1_of_3 span_1_of_3" style="width: 23%">
									<a href="/FrontServlet?module=Product&command=ViewProductDetail&productid=${a.id}" >
										<div class="inner_content clearfix">
											<div class="product_image">
												<img src="${a.productImagePath}"/>
											</div>
											<div class="sale-box">
												<span class="on_sale title_shop">New</span>
											</div>
											<div class="price">
												<div class="cart-left">
													<p class="title">${fn:substring(a.productName,0,20)}...</p>
													<br />
													<div class="price1">
														<span class="actual">$${a.productPrice}</span>
													</div>
												</div>
												<div class="cart-right"></div>
												<div class="clear"></div>
											</div>
										</div>
									 </a>
									</div>
								</c:forEach>
								<div class="clear"></div>
							</div> 
						</c:if>
				</c:forEach>
				<br/><br/><br/>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>