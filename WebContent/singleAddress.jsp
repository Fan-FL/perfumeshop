<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Shipping address</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="js/jquery1.min.js"></script>
	<style type="text/css">
		.form{
			width:40%;
			height:250px;
			margin-left: 30%;
		}
		.table {
			width:100%;
		}
		.form form input[type="text"], .form form select, .form form input[type="password"] {
			font-size:0.95em;
			color:#777;
			padding: 8px;
			outline: none;
			margin:10px 0;
			width:80%;
			font-family: 'Exo 2', sans-serif;
			border:1px solid #aaa;
			box-shadow:0 0 2px #aaa;
			-webkit-box-shadow:0 0 2px #aaa;
			-moz-box-shadow:0 0 2px #aaa;
			-o-box-shadow:0 0 2px #aaa;
		}
		.form form label {
			font-size: 0.8em;
			color: red;
			line-height: 2.5em;
		}
		.btn {
			padding:10px 30px;
			color: #FFF;
			cursor: pointer;
			background:#555;
			border:none;
			outline:none;
			font-family: 'Exo 2', sans-serif;
			font-size:1em;
			margin-left: 80;
		}
		.btn:hover{
			background:#4CB1CA;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#usb").click( function () {
                alert("修改成功");
                $("#updateform").submit();
			});
			$("#asb").click( function () {
                alert("Added successfully!");
                $("#addform").submit();
			});
		});
	</script>
  </head>
  
  <body>
  	<c:choose>
  		<c:when test="${empty address}">
	  		<div class="form">
				<form id="addform" action="addaddress" method="post">
		   			<table class="table">
						<tr>
							<td width="15%">Shipping address</td>
							<td width="85%"><input type="text" name="sendplace"></td>
						</tr>
						<tr>
							<td>Consignee</td>
							<td><input type="text" name="sendman"></td>
						</tr>
						<tr>
							<td>Mobile</td>
							<td><input type="text" name="sendphone" id="sendphone"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label style="font-size: 1em" id="la"><br/></label></td>
						</tr>
		   			</table>
		   			<div align="center"><br/></div>
					<input type="reset" value="Reset" class="btn">
					<input type="button" value="Confirm" class="btn" id="asb">
				</form>
			</div>
  		</c:when>
  		<c:otherwise>
	  		<div class="form">
				<form id="updateform" action="updateaddress?addId=${address.addressId}" method="post">
		   			<table class="table">
		   				<tr>
							<td width="15%">Shipping address</td>
							<td width="85%"><input type="text" name="sendplace" value="${address.sendPlace}"></td>
						</tr>
						<tr>
							<td>Consignee</td>
							<td><input type="text" name="sendman" value="${address.sendMan}"></td>
						</tr>
						<tr>
							<td>Mobile</td>
							<td><input type="text" name="sendphone" value="${address.sendPhone}" id="sendphone"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label style="font-size: 1em" id="la"><br/></label></td>
						</tr>
		   			</table>
		   			<div align="center"><br/></div>
					<input type="reset" value="Reset" class="btn">
					<input type="button" value="Modify" class="btn" id="usb">
				</form>
			</div>
  		</c:otherwise>
  	</c:choose>
  	<br/><br/><br/><br/>
  </body>
</html>
