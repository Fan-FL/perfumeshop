<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/ckform.js"></script>
    <script type="text/javascript" src="js/common.js"></script>

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
		//Validate the input price format
		$("body").on({
			keydown:function(event) {
				var kCode = event.which;
				//Determine key values
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
					$(this).parent("td").append("<font color='red'></br><i>Please enter the correct price of product, and at least two bits after the decimal point.</i><font>")
				}
			}
		},".productPrice");
		//Validate the input quantity format
		$("body").on({
			keydown:function(event) {
				var kCode = event.which;
				//Determine key values
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
					$(this).parent("td").append("<font color='red'></br><i>Product inventory can only be a non-negative integer.</i><font>")
				}
			}
		},".storeNum");
		
		//Save the event of product adding
		$("#addProducts").click(function(){
			var flag = true;
			$("input").not(":hidden").each(function(){
				if($.trim($(this).val())==""){
					alert(this.class);
					alert("Please improve product information.");
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
        <td class="tableleft">Name</td>
        <td><input type="text" name="productName" value="" /></td>
    </tr>
    <tr>
        <td class="tableleft">Product Price</td>
        <td><input onfocus="this.style.imeMode='disabled'" class='productPrice' type="text" name="productPrice" value="" /></td>
    </tr>
    <tr>
        <td class="tableleft">Storage</td>
        <td><input onfocus="this.style.imeMode='disabled'" class="storeNum" type="text" name="storeNum" value="" /></td>
    </tr>
     <tr>
        <td class="tableleft">Description</td>
        <td >
            <textarea rows="5" name="productDesc"></textarea>
        </td>
    </tr>
</table>
	<div align="center" id="btn">
        <button type="button" id="addProducts" class="btn btn-primary" type="button">Save</button> &nbsp;&nbsp;
        <button type="button" class="btn btn-success" name="backid" id="backid">Return</button>
	</div>
</form>
</body>
</html>
