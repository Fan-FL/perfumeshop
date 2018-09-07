$(function(){

	toggleBlankCart();// check if cart is empty, if empty show empty div
	setTotalPrice();// calculate total price
	// '+' event
	$(".increase").click(
			function() {
				// get stock
				var storeNum = $(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".storeNum").children(
						":hidden").val();
				// get sale count
				var $saleCount = $(this).prev("input");
				// sale count + 1
				var num = parseInt($saleCount.val()) + 1;
				// show understock when not enough product
				if (num > storeNum) {
					confirm("Sorry, the product is understock");
					//$(this).prev("input").addClass("outOfStoreNum");
					return;
				}
				// reset sale count input box
				$saleCount.val(num);
				// modify stock when changing sale number
				var cartId = $(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".cartId").children(
						":hidden").val();
				var json = {
					"cartId" : cartId,
					"saleCount" : num
				};
				updateCartCount(json);
				// get price
				var price = parseFloat($(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".mktprice1").text());
				var $subPrice = $(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".itemTotal");
				// get subtotal
				$subPrice.html("<b>" + formatCurrency(num * price) + "</b>");
				// get total price
				setTotalPrice();
			});
	// '-' action
	$(".decrease").click(
			function() {
				var $saleCount = $(this).next("input");
				var num = parseInt($saleCount.val()) - 1;
				var cartId = $(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".cartId").children(
						":hidden").val();
				//get stock
				var storeNum = $(this).parent(".Numinput").parent(
				".cart-quantity").siblings(".storeNum").children(
				":hidden").val();
				if(num <= storeNum){
					$(this).next("input").removeClass("outOfStoreNum");
				}
				//show whether delete when decrease to 1
				if (num == 0) {
					if (!confirm("Are you sure to delete?")) {
						return;
					}
					deleteCartTr(cartId, this);
					return;
				}
				$saleCount.val(num);
				var json = {
					"cartId" : cartId,
					"saleCount" : num
				};
				updateCartCount(json);
				// get price
				var price = parseFloat($(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".mktprice1").text());
				var $subPrice = $(this).parent(".Numinput").parent(
						".cart-quantity").siblings(".itemTotal");
				// get subtotal
				$subPrice.html("<b>" + formatCurrency(num * price) + "</b>");
				// get total price
				setTotalPrice();
			});
	// delete action
	$(".delete").click(
		function() {
			if (!confirm("Are you sure to delete?")) {
				return false;
			}
			var cartId = $(this).parent(".cart_last").siblings(".cartId")
					.children(":hidden").val();
			deleteCartTr(cartId, this);
			return false;
		});

	$("#deleteChecked").click(function(){
		if($(":checked").length == 0){
			alert("Please select product！");
			return false;
		}
		if (!confirm("Are you sure to delete?")) {
			return false;
		}
		if($("#checkAll").attr("checked")){
			cleanCart();
			return false;
		}
		$(".checkbox").each(function(){
			if(this.checked){
				var cartId = this.value;
				deleteCartTr(cartId, this);
			}
		});
		return false;
	});

	$(".clean_btn").click(function() {
		if (!confirm("Are you sure to clear cart?")) {
			return;
		}
		cleanCart();
	});
	$(".returnbuy_btn").click(
		function() {
			window.location.href = "blank.jsp";
		});
	$("#goToBuy").click(function() {
		if(validateStoreNum()){
			alert("Some products are understock, please modify before" +
				" submission");
			return false;
		}
		if(validateSaleOut()){
			alert("Some products are off the shelf, please modify before submission");
			return false;
		}
		if($(":checked").length == 0){
			alert("Please select products before checking out");
			return false;
		}
		if(confirm("Are you sure to buy these products?")){
			$("#cartFormSubmit").submit();
		}
	});
	$(".Numinput input").keydown(
			function(event) {
				var kCode = $.browser.msie ? event.keyCode : event.which;
				if (((kCode > 47) && (kCode < 58))
						|| ((kCode > 95) && (kCode < 106)) || (kCode == 8)
						|| (kCode == 39) || (kCode == 37)) {
					return true;
				} else {
					return false;
				}
			}).focus(function() {
		this.style.imeMode = 'disabled';
	}).keyup(
			function() {
				var pBuy = $(this).parent();
				var numObj = pBuy.find("input[name='num']");
				var num = parseInt(numObj.val());
				if (!isNaN(num)) {
					var storeNum = $(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".storeNum").children(
							":hidden").val();
					var num = parseInt(numObj.val());
					if (num > storeNum) {
						confirm("Sorry, Some products are understock.");
						numObj.addClass("outOfStoreNum");
						return;
					}
					numObj.removeClass("outOfStoreNum");
					var cartId = $(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".cartId").children(
							":hidden").val();
					var json = {
						"cartId" : cartId,
						"saleCount" : num
					};
					updateCartCount(json);
					var price = parseFloat($(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".mktprice1").text());
					var $subPrice = $(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".itemTotal");
					$subPrice.html("<b>" + formatCurrency(num * price) + "</b>");
					setTotalPrice();
				}
			});
	$(".checkbox").click(function(){
		var cbl = $(".checkbox").length;
		$("#checkAll").attr("checked",$(".checkbox:checked").length==cbl);
		setTotalPrice();
	});
	$("#checkAll").click(function(){
		$(".checkbox").attr("checked",this.checked);
		setTotalPrice();
	});
});
function cleanCart(){
	var json = {
		"cartId" : 0
	};
	deleteCart(json);
	$("div#cart-wrapper").remove();
	toggleBlankCart();
}
function updateCartCount(json) {
	$.post("updatecartcount", json);
};
function setTotalPrice() {
	var total = 0;
	$(".itemTotal").each(function() {
		var flag = $(this).siblings(".checkboxtd").children(".checkbox:checked");
		if(flag.length > 0){
			total += parseFloat($(this).text().replace(/,/gm,""));
		}
	});
	var mark = $("#total").text().trim().substring(0,1);//获取格式化的金额符号
	$("#total").html(mark + formatCurrency(total));
}
function deleteCart(json) {
	$.post("deletecart", json);
}
function deleteCartTr(cartId, obj) {
	var json = {
		"cartId" : cartId
	};
	deleteCart(json);
	var $tr = $(obj).parents("tr");
	$tr.remove();
	$("div.tag-list ul li ul.subCartList li[id=" + cartId + "]").remove();
	var cartCount = $("#cartbody tr").length;
	$("#xiaocart li a").text("Cart(" + cartCount + ")");
	if ($("#cartbody:has(tr)").length == 0) {
		toggleBlankCart();
	}
	setTotalPrice();
}
function toggleBlankCart() {
	if ($("div#cart-wrapper div.cart-blank").length > 0 || $("div#cart-wrapper #cartbody:has(tr)").length > 0) {
		$("div#blackcart").hide();
	} else {
		$("div#cart-wrapper").remove();
		$("div#blackcart").show();
		$("div.tag-list ul li ul.subCartList").empty().append(
				"<li><p>请点击<a href='blank.jsp'>这里</a>选择产品</p></li>");
		$(".last li a").text("Cart(0)");
	}
}

//
function validateSaleOut(){
	var flag = false;
	$("font").each(function(){
		if($(this).hasClass("productSaleOut")){
			flag = true;
		}
	});
	return flag;
}

function validateStoreNum(){
	var flag = false;
	$("input").each(function(){
		if($(this).hasClass("outOfStoreNum")){
			flag = true;
		}
	})
	return flag;
}
function refresh() {
	window.location.reload();

}
function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num * 100 + 0.50000000001);
	cents = num % 100;
	num = Math.floor(num / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		num = num.substring(0, num.length - (4 * i + 3)) + ','
				+ num.substring(num.length - (4 * i + 3));
	return (((sign) ? '' : '-') + num + '.' + cents);
};