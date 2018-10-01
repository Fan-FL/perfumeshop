<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
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
</head>
<body>
<jsp:include page='/FrontServlet?module=manager&command=ManagerHeader' flush="true"></jsp:include>
<form action="${pageContext.request.contextPath}/FrontServlet?module=Product&command=UpdateProduct" method="post"
      class="definewidth m20">
<!-- <input type="hidden" name="id" value="{$menu.id}" /> -->
<input type="hidden" name="productId" value="${requestScope.product.id}" />
<table class="table table-bordered table-hover m10">
    <tr>
        <td class="tableleft">Name</td>
        <td><input type="text" name="productName" value="${requestScope.product.productName}" /></td>
    </tr>
    <tr>
        <td class="tableleft">Product Price</td>
        <td><input class="productPrice" type="text" name="productPrice"
        value="${requestScope.product.productPrice}" /></td>
    </tr>
    <tr>
        <td class="tableleft">Storage</td>
        <td><input class="storeNum" type="text" name="storeNum"
        value="${requestScope.product.storeNum}" /></td>
    </tr>
     <tr>
        <td class="tableleft">Description</td>
        <td >
            <textarea rows="5" name="productDesc">${requestScope.product.productDesc}</textarea>
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" id="editProductSubmit" class="btn btn-primary" type="button">Save</button> &nbsp;&nbsp;
            <button type="button" class="btn btn-success" name="backid" id="backid">Return</button>
        </td>
    </tr>
</table>
</form>
</body>
</html>
<script>
    $(function () {       
		$('#backid').click(function(){
				window.location.href="${pageContext.request.contextPath}/FrontServlet?module=Product&command=ManagerViewAllProduct";
		 });
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
					$(this).parent("td").append(
							"<font color='red'></br><i>Please enter the correct price of product, and at least two bits after the decimal point.</i><font>")
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
					$(this).parent("td").append("<font color='red'></br><i>Product inventory can only be a non-negative integer</i><font>")
				}
			}
		},".storeNum");

    });
</script>