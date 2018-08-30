<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>PerfumeStore</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--gz --%>
<link rel="stylesheet" href="css/cart2.css" type="text/css">
<link rel="stylesheet" href="css/buyleo2.css" type="text/css">
<link href="css/mystyle.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css">
.cart_name p i a:link, a:visited, a:active {
    text-decoration: none;
}
.cart_name p i a:hover { color: #D93600; text-decoration: none;}
.cart_name p i a{
color: #333;
    outline: medium none;
    text-decoration: none;
	display: block;
	color: #333;
	font-size: 16px;
	line-height: 18px;
	margin-left: 15px;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 700px;
	margin-left:20px;
	overflow: hidden;
	text-align: left;
}
</style>
<%--gz --%>
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
<script type="text/javascript">
	$(function(){
		 setTotalPrice();
		 $("[name='addressId']").first().prop("checked", true);
		 $("#createBtn").click(function(){
			var radioLength = $("[name='addressId']:checked").length;
			if(radioLength < 1){
				alert("Please add shipping information！");
				return;
			}
			if($(".itemTotal").length == 0){
				alert("Please select prodcuts before submitting！");
				return;
			}
			$("#multiFormSubmit").submit();
		 });
	});
	//Total price
	function setTotalPrice(){
		var total = 0;
		$(".itemTotal").each(function() {
			total += parseFloat($(this).text());
		});
		var mark = $(".total ul li p").text().trim().substring(0,1);//format price
		$(".total ul li p").html(mark + formatCurrency(total));
	}
    //format price
	function formatCurrency(num) {    
	    num = num.toString().replace(/\$|\,/g,'');    
	    if(isNaN(num))    
	    num = "0";    
	    sign = (num == (num = Math.abs(num)));    
	    num = Math.floor(num*100+0.50000000001);    
	    cents = num%100;    
	    num = Math.floor(num/100).toString();    
	    if(cents<10)    
	    cents = "0" + cents;    
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)    
	    num = num.substring(0,num.length-(4*i+3))+','+    
	    num.substring(num.length-(4*i+3));    
	    return (((sign)?'':'-') + num + '.' + cents);    
	}
	/** 
 * iframe pop up. Example：openDialogByIframe(550,450,'new','add.do');
 *  
 * @param width 
 * @param height
 * @param tit
 *            title
 * @param url 
 *            iframe URL
 */  
	function openDialogByIframe(width, height, tit, url) {  
    var winWinth = $(window).width(), winHeight = $(document).height(); 
    $("body").append("<div class='yz_popIframeDiv'></div>");  
    $("body")  
            .append(  
                    "<div class='yz_popTanChu'><div class='yz_popTanChutit'><span class='yz_popTanChuTxt'>"  
                            + tit  
                            + "</span><span class='yz_popTanChuClose'>关闭</span></div><iframe class='winIframe' scrolling='no' frameborder='0' hspace='0' src="  
                            + url + "></iframe></div>");  
    $(".yz_popIframeDiv").css({  
        width : winWinth,  
        height : winHeight,  
        background : "#000",  
        position : "absolute",  
        left : "0",  
        top : "0"  
    });  
    $(".yz_popIframeDiv").fadeTo(0, 0.5);  
    var yz_popTanChuLeft = $(window).width() / 2 - width / 2;
    var yz_popTanChuTop = "300px";
    $(".yz_popTanChu").css({
        width : width,  
        height : height,  
        border : "3px #ccc solid",  
        left : yz_popTanChuLeft,  
        top : yz_popTanChuTop,  
        background : "#fff",  
        position : "absolute"  
    });  
    $(".yz_popTanChutit").css({  
        width : width,  
        height : "25px",  
        "border-bottom" : "1px #ccc solid",  
        background : "#eee",  
        "line-height" : "25px"  
    });  
    $(".yz_popTanChuTxt").css({  
        "text-indent" : "5px",  
        "float" : "left",  
        "font-size" : "14px"  
    });  
    $(".yz_popTanChuClose").css({  
        "width" : "40px",  
        "float" : "right",  
        "font-size" : "12px",  
        "color" : "#667",  
        "cursor" : "pointer"  
    });  
    var winIframeHeight = height - 26;  
    $(".winIframe").css({  
        width : width,  
        height : winIframeHeight,  
        "border-bottom" : "1px #ccc solid",  
        background : "#ffffff"  
    });  
    $(".yz_popTanChuClose").hover(function() {  
        $(this).css("color", "#334");  
    }, function() {  
        $(this).css("color", "#667");  
    });  
    $(".yz_popTanChuClose").click(function() {  
        $(".yz_popIframeDiv").remove();  
        $(".yz_popTanChu").remove();  
    });  
} 
</script>
<%--layer--%>
<script type="text/javascript" src="js/layer/layer.js"></script>
<script>
;!function(){
	
	//load css, module completely
	layer.ready(function(){ 
		$('#createAddress').on('click', function(){
			layer.open({
		        type: 2,
		        offset: '180px',
		        //skin: 'layui-layer-lan',
		        title: 'Add address',
		        fix: true,
		        shadeClose: false,
		        maxmin: false,
		        area: ['600px', '400px'],
		        content: 'createAddressFromOrderPage.jsp',
		    });
		});
	});

}();
</script>
</head>
<body>
<jsp:include page='login?method=header' flush="true"></jsp:include>
	<div class="clear"></div>
	<div class="register_account">
		<form action="submitorder" method="post" id="multiFormSubmit">
			<c:set scope="page" var="tokenValue" value="<%=UUID.randomUUID().toString()%>"></c:set>
			<c:set scope="session" var="token" value="${pageScope.tokenValue }"></c:set>
			<input type="hidden" name="token" value="${pageScope.tokenValue }">
			<div class="wrap">
				<h4 class="title">Confirm shipping address</h4>
				<c:forEach items="${requestScope.addresses }" var="address">
					<input name="addressId" type="radio" value="${address.addressId }">
					<label> ${address.sendPlace }&nbsp;(${address.sendMan
						}&nbsp;收)&nbsp;${address.sendPhone }</label><br/>
				</c:forEach>
				<div id="createAddressDiv" class="button-wrapper">
					<span class="btn">
						<button id="createAddress" type="button" class="grey"><font
								size="2px">Add shipping address</font></button>
					</span>
				</div>
				<br/>
			</div>
			<div class="wrap">
				<h4 class="title">&nbsp;
				</h4>
			</div>
			<br/>
			<div class="wrap">
				<h4 class="title">Product list<a href="viewcart">[Return to cart]</a></h4>
			</div>
			<div class="mycar-index">
			<!-- Product info -->
			<div class="goods_wrapper">
				<table cellspacing="0" cellpadding="3" width="100%">
					<thead>
						<tr>
							<th style="width:100px;">Picture</th>
							<th style="width:500px;">Name</th>
							<th style="width:120px;">Price</th>
							<th style="width:100px;">Quantity</th>
							<th style="width:120px;">Subtotal</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sessionScope.cartProductMap}" var="cartProduct">
						<tr>
							<td style="width:100px;">
								<a target="_blank" href="viewproductdetail?productid=${cartProduct.value.productId }">
									<span class="cart-product-img">
										<img src="${cartProduct.value.productImagePath }" height="50" style="cursor: pointer;">
									</span>
								</a>				
							</td>
							<td >
								<div class="cart_name" ><p>
								<i><a target="_blank" href="viewproductdetail?productid=${cartProduct.value.productId }">
								${cartProduct.value.productName }</a></i></p></div>							
							</td>
							<!-- <td>0</td> -->
							<td class="mktprice1" style="width:120px;">
							<div class="cart_pricenumber" ><p>${cartProduct.value.productPrice }</p></div></td>
							<td style="width:100px;" class="productquantity">
								<div class="cart_number" ><p>${cartProduct.key.saleCount}</p></div>
								<input type="hidden" value="${cartProduct.value.storeNum }">
							</td>
							<td class="itemTotal" style="width:120px;">
								<div class="cart_pricenumber" ><p>
								${cartProduct.key.saleCount*cartProduct.value.productPrice}</p></div>
							</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="total_box">
				<div class="remark">
					Note:
					<input type="text" class="remark_intro" name="ordernote" maxlength="30">
				</div>
				<div class="total">
				<ul>
					<li>
						<p><fmt:formatNumber value="1" type="currency"></fmt:formatNumber></p>
						<h2>Total price:</h2>
					</li>
				</ul></div>
			</div>
			<div class="order_total btn">
				<input type="button" id="createBtn" class="enable green-btn" value="Submit">
			</div>
</div>

		</form>
	</div>
	
	<br />
</body>
</html>