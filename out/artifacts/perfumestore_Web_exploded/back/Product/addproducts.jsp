<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/back/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="/back/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="/back/Css/style.css" />
    <script type="text/javascript" src="/back/Js/jquery.js"></script>
    <script type="text/javascript" src="/back/Js/bootstrap.js"></script>
    <script type="text/javascript" src="/back/Js/ckform.js"></script>
    <script type="text/javascript" src="/back/Js/common.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
    <script type="text/javascript">
	$(function() {
		//验证输入的价格格式
		$("body").on({
			keydown:function(event) {
				var kCode = event.which;
				// 判断键值
				if (((kCode > 47) && (kCode < 58))
						|| ((kCode > 95) && (kCode < 106)) || (kCode == 8)
						|| (kCode == 39) || (kCode == 37) ||(kCode == 190)) {
					return true;
				} else {
					return false;
				}
			},
			blur:function() {
				var reg = /^([1-9]\d*|0)(\.\d{1,2})?$/;
				var bool = reg.test($(this).val());
				$(this).parent("td").children("font").remove();
				if(!bool){
					$(this).parent("td").append("<font color='red'></br><i>请输入正确的商品价格,小数点后最多两位</i><font>")
				}
			}
		},".productPrice");
		//验证输入的库存数量格式
		$("body").on({
			keydown:function(event) {
				var kCode = event.which;
				// 判断键值
				if (((kCode > 47) && (kCode < 58))
						|| ((kCode > 95) && (kCode < 106)) || (kCode == 8)
						|| (kCode == 39) || (kCode == 37)) {
					return true;
				} else {
					return false;
				}
			},
			blur:function() {
				var reg = /^\d+$/;
				var bool = reg.test($(this).val());
				$(this).parent("td").children("font").remove();
				if(!bool){
					$(this).parent("td").append("<font color='red'></br><i>商品库存只能为非负的整数</i><font>")
				}
			}
		},".storeNum");
		
		//保存商品添加事件
		$("#addProducts").click(function(){
			var flag = true;
			$("input").not(":hidden").each(function(){
				if($.trim($(this).val())==""){
					alert(this.class);
					alert("请完善商品信息");
					flag = false;
				}
			});
			if(flag){
				$("#addProductsForm").submit();
			}
		});

	});

</script>
</head>
<body>
<jsp:include page='/FrontServlet?module=manager&command=ManagerHeader' flush="true"></jsp:include>
<form id="addProductsForm" action="/FrontServlet?module=Product&command=AddProduct"
 method="post" class="definewidth m20">
<table class="table table-bordered table-hover m10">
    <tr>
        <td rowspan="6" class="tableleft" width="10px" align="center">1</td>
        <td class="tableleft">名称</td>
        <td><input type="text" name="productName" value="" /></td>
    </tr>
    <tr>
        <td class="tableleft">商品单价</td>
        <td><input onfocus="this.style.imeMode='disabled'" class='productPrice' type="text" name="productPrice" value="" /></td>
    </tr>
    <tr>
        <td class="tableleft">库存</td>
        <td><input onfocus="this.style.imeMode='disabled'" class="storeNum" type="text" name="storeNum" value="" /></td>
    </tr>
     <tr>
        <td class="tableleft">描述</td>
        <td >
            <textarea rows="5" name="productDesc"></textarea>
        </td>
    </tr>
</table>
	<div align="center" id="btn">
        <button type="button" id="addProducts" class="btn btn-primary" type="button">保存</button> &nbsp;&nbsp;
        <button type="button" class="btn btn-success" name="backid" id="backid">返回</button>
	</div>
</form>
</body>
</html>
