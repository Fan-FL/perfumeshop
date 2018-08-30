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
	/**
	 * 为删除选中的购物车商品添加事件
	 * ①当点击删除时，提示用户 是否确认删除选中的商品
	 * ②当删除了某条数据时，同步更新 购物车下拉菜单中相应的li节点，并更改购物车显示的数量
	 * ③当Select all被选中时，点击该删除链接，执行清空购物车方法即可
	 */
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
	// 为 清空购物车 按钮添加事件
	$(".clean_btn").click(function() {
		if (!confirm("Are you sure to clear cart?")) {
			return;
		}
		cleanCart();
	});
	// 为Continue shopping添加事件
	$(".returnbuy_btn").click(
		function() {
			window.location.href = "blank.jsp";// 重定向到主页
		});
	// 为结算添加事件
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
	// 手动输入商品数量
	$(".Numinput input").keydown(
			function(event) {
				var kCode = $.browser.msie ? event.keyCode : event.which;
				// 判断键值
				if (((kCode > 47) && (kCode < 58))
						|| ((kCode > 95) && (kCode < 106)) || (kCode == 8)
						|| (kCode == 39) || (kCode == 37)) {
					return true;
				} else {
					return false;
				}
			}).focus(function() {
		this.style.imeMode = 'disabled';// 禁用输入法,禁止输入中文字符
	}).keyup(
			function() {
				var pBuy = $(this).parent();// 获取父节点
				var numObj = pBuy.find("input[name='num']");// 获取当前商品数量
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
					// 获取单价
					var price = parseFloat($(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".mktprice1").text());
					var $subPrice = $(this).parent(".Numinput").parent(
							".cart-quantity").siblings(".itemTotal");
					// 设置Subtotal(num * price).toFixed(2)
					$subPrice.html("<b>" + formatCurrency(num * price) + "</b>");
					// 设置总价
					setTotalPrice();
				}
			});
	//为checkbox添加事件
	$(".checkbox").click(function(){
		var cbl = $(".checkbox").length;//.checkbox的长度
		$("#checkAll").attr("checked",$(".checkbox:checked").length==cbl);
		setTotalPrice();
	});
	$("#checkAll").click(function(){
		$(".checkbox").attr("checked",this.checked);
		setTotalPrice();
	});
});
//清空购物车
function cleanCart(){
	//用cartId = 0 标记 清空购物车
	var json = {
		"cartId" : 0
	};
	deleteCart(json);
	$("div#cart-wrapper").remove();
	toggleBlankCart();
}
// 更新购买数量
function updateCartCount(json) {
	$.post("updatecartcount", json);
};
// 设置总计价格
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
// 删除购物车中的一条数据 或 清空购物车 json{"cartId" : cartId} cartId 为 0 是清空购物车 其它时候 删除一条数据
function deleteCart(json) {
	$.post("deletecart", json);
}
// 删除某条购物车数据后 删除该条数据所在的 tr节点 同时删除购物车下拉菜单的某一条数据
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
// 购物车清空时显示此DIV 同时将购物车 下拉菜单恢复
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

//查看当前页面商品库存
function validateStoreNum(){
	var flag = false;
	$("input").each(function(){
		if($(this).hasClass("outOfStoreNum")){
			flag = true;
		}
	})
	return flag;
}
// 刷新页面
function refresh() {
	window.location.reload();// 刷新当前页面.

	// 或者下方刷新方法
	// parent.location.reload()刷新父亲对象（用于框架）--需在iframe框架内使用
	// opener.location.reload()刷新父窗口对象（用于单开窗口
	// top.location.reload()刷新最顶端对象（用于多开窗口）
}
// 格式化金额
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