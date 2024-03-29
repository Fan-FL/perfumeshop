<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${requestScope.product.productName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/form.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/goods.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css">
#commentInfo {
	width: 95%;
}

#commentInfo table {
	width: 95%;
	border-left: 1px solid #f5f5f5;
	border-right: 1px solid #f5f5f5;
}

#commentInfo table th {
	background: #f5f5f5;
	height: 30px;
	text-align: center;
	line-height: 30px;
}

#commentInfo table td {
	border-bottom: 1px solid #f5f5f5;
	color: #333;
	height: 30px;
	font-size: 14px;
	text-align: center;
	line-height: 30px;
}

.span_1_of_single ul li a:link,a:visited,a:active {
	text-decoration: none;
}

.span_1_of_single ul li a:hover {
	color: #000;
	text-decoration: none;
}

.span_1_of_single ul li a {
	color: #777;
}

.rer-quantity {
	height: 30px;
	line-height: 30px;
	width: 100%;
}

.decrease {
	border-right: 0 none;
}

.Numinput {
	display: inline;
	float: left;
}

span.numadjust {
	border: 1px solid #e3e2e2;
	float: left;
	height: 22px;
	line-height: 22px;
	text-align: center;
	width: 23px;
	cursor: pointer;
}

.rer-quantity label {
	float: left;
	height: 22px;
	line-height: 22px;
	width: 80px;
}

.Numinput input {
	border: 1px solid #e3e2e2;
	display: inline;
	float: left;
	height: 24px;
	line-height: 24px;
	text-align: center;
}

.nbs-flexisel-item>a img {
	cursor: pointer;
	margin-bottom: 10px;
	margin-top: 10px;
	max-height: 200px;
	max-width: 150px;
}

.button {
	font-family: 'Exo 2', sans-serif;
	cursor: pointer;
	color: #FFF;
	font-size: 1em;
	outline: none;
	-webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
	transition: all 0.3s;
	background: #7EB123;
	padding: 10px 20px;
	border: none;
	margin-top: 20px;
	-webkit-appearance: none;
}

.button:HOVER {
	background: #555;
}
div#page{
	float: left;
	margin-left: 50%;
}
div#page button{
	background: #fff;
	border: 0px #fff;
	color: #555;
}
div#page button.active{
	cursor: pointer;
	color: blue;
}

</style>
<script src="js/jquery1.min.js"></script>
<script src="js/jquery.easydropdown.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" id="sourcecode">
	$(function()
	{
		$('.scroll-pane').jScrollPane();
	});
</script>
<!-- start details -->
<script src="js/slides.min.jquery.js"></script>
<script>
$(function(){
	$('#products').slides({
		preload: true,
		preloadImage: 'img/loading.gif',
		effect: 'slide, fade',
		crossfade: true,
		slideSpeed: 350,
		fadeSpeed: 500,
		generateNextPrev: true,
		generatePagination: false
	});
});
</script>
<!-- start zoom -->
<link rel="stylesheet" href="css/zoome-min.css" />
<script type="text/javascript" src="js/zoome-e.js"></script>

</head>
<body>
	<jsp:include page='FrontServlet?module=User&command=UserHeader' flush="true"></jsp:include>

<script type="text/javascript">
	$(function(){
		//Whther this product is in cart
		var hasThisProduct = ("${requestScope.hasThisProduct}"=="true");
		var userId = "${sessionScope.userId}";
		//Stock
		var storeNum = "${requestScope.product.storeNum}";
		var productName = "${requestScope.product.productName}";
		//alert(storeNum);
		//purchase quantity
        $(".Numinput input").keydown(function(event){
        	var kCode = $.browser.msie ? event.keyCode : event.which;
            if (((kCode > 47) && (kCode < 58))
                || ((kCode > 95) && (kCode < 106)) 
                || (kCode == 8) || (kCode == 39) 
                || (kCode == 37)) { 
                return true;
            } else{ 
                return false;  
            };
        }).focus(function() {
            this.style.imeMode='disabled';// disable input method
        }).keyup(function(){
        	var pBuy = $(this).parent();// Get parent node
			var numObj = pBuy.find("input[name='number']");// Get input number
			var num = parseInt(numObj.val());
			if (!isNaN(num)) {
				if (num > storeNum) {
					confirm("Sorry, the product is understock.");
					return;
				};
        	};
		});
		$("#addToCart").click(function(){
			if(userId<=0){
				if(confirm("Please login, click Confirm to jump to login page.")){
					window.location.href = "${pageContext.request.contextPath}/login.jsp";
				}
				return false;
			}
			if(hasThisProduct){
				if(confirm("This product has already been in the cart, click Confirm to the cart")){
					window.open("${pageContext.request.contextPath}/FrontServlet?module=Cart&command=ViewCart");
				}
				return;
			}
			var url="/FrontServlet?module=Cart&command=AddToCart";
			var productId = $("#productId").val();
			var saleCount = $("#text1").val();
			if(parseInt(saleCount) > storeNum){
				alert("Sorry, the product is understock, please modify number");
				return;
			}
			var sendData={"productId":productId,"saleCount":saleCount};
			$.getJSON(url,sendData,function(backData){
				if(backData[0].cartId > 0){
					hasThisProduct = true;
					alert("Added successfully!^.^");
					$("#xiaocart li a").text("Cart(" + (backData[0].cartCount) + ")");
					$("#emptyCart").remove();
					$("#smallCartList").append("<li id='"+backData[0].cartId+"'><i><a href='viewproductdetail?productid="+productId+"'>"+productName+"</a></i></li>");
				};
			});
		});
		$("#b2").click(function(){
		var nu1=$("#text1").val();
		var b=Number(nu1);
			b++;
			if (b > storeNum) {
				confirm("Sorry, the product is understock.");
				return;
			};
			$("#text1").val(b);
		});
		$("#b3").click(function(){
		var nu1=$("#text1").val();
		var b=Number(nu1);
			if(b==1){
				return;
			}
			b--;
			$("#text1").val(b);
		});
	});
</script>		

<div class="mens">    
  <div class="main">
     <div class="wrap">
     	<ul class="breadcrumb breadcrumb__t"><a class="home" href="blank.jsp">Home</a> /
			${requestScope.product.productName}</ul>
		<div class="cont span_2_of_3">
		  		<div class="grid images_3_of_2">
						<div id="container">
							<div id="products_example">
								<div id="products">
									<div class="slides_container">
									        <a href="#"><img class="a" id="img1" src="${requestScope.product.productImagePath}" alt="" rel="images/s-img.jpg" /></a>
									</div>
								</div>
							</div>
						</div>
	            </div>
		         <div class="desc1 span_3_of_2">
		         	<h3 class="m_3">${requestScope.product.productName}</h3>
		             <p class="m_5">$${requestScope.product.productPrice} </p>
		         	 <div class="btn_form">
		         	 <em><font color="#878787">${requestScope.product.storeNum} in stock</font></em><br/><br/>
						<form method="get">
							<input id="productId" type="hidden" name="productId"
								   value="${requestScope.product.id}">
							<div style="margin-top:5px;margin-bottom:0" class="rer-quantity">
							<label>Quantity ordered:</label>
								<div class="Numinput">
									<span class="numadjust decrease" id="b3">-</span>
									<input id="text1" type="text" autocomplete="off" name="number" size="5" oldvalue="3" value="1">
									<span class="numadjust increase" id="b2">+</span>
								</div>
							</div>
								
							<br/><br/>
							<input type="button" value="Add to cart" id="addToCart" class="button">
						</form>
					 </div>
			     </div>
		</div>
	 </div>
  </div>
</div>
</body>
</html>