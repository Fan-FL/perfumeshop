allProduct.jsp
blank.jsp
cart.jsp<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Personal details Management</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="js/jquery1.min.js"></script>
	<style type="text/css">
		#form{
			width:40%;
			height:250px;
			margin-left: 30%;
		}
		#table {
			width:100%;
		}
		#form form input[type="text"], #form form select, #form form input[type="password"] {
			font-size:0.95em;
			color:#777;
			padding: 8px;
			outline: none;
			margin:5px 0;
			width:80%;
			font-family: 'Exo 2', sans-serif;
			border:1px solid #aaa;
			box-shadow:0 0 2px #aaa;
			-webkit-box-shadow:0 0 2px #aaa;
			-moz-box-shadow:0 0 2px #aaa;
			-o-box-shadow:0 0 2px #aaa;
		}
		#form form label {
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
			var passflag = false;
			var phoneflag = false;
			
			$("#password").blur(function(){
				if($("#password").val()!=$("#repassword").val()){
					$("#password").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#repassword").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#la").html("×Passwords do not match!×");
					passflag = false;
				} else {
					$("#password").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#repassword").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#la").html("<br/>");
					passflag = true;
				}
			});
			
			$("#repassword").blur(function(){
				if($("#password").val()!=$("#repassword").val()){
					$("#password").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#repassword").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#la").html("×Passwords do not match!×");
					passflag = false;
				} else {
					$("#password").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#repassword").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#la").html("<br/>");
					passflag = true;
				}
			});
			
			$("#sb").click( function () {
				if($("#password").val()!=$("#repassword").val()){
					$("#password").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#repassword").focus().css({
						border: "1px solid red",
						boxShadow: "0 0 2px red"
					});
					$("#la").html("×Passwords do not match!×");
					passflag = false;
				} else {
					$("#password").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#repassword").focus().css({
						border:"1px solid #aaa",
						boxShadow:"0 0 2px #aaa"
					});
					$("#la").html("<br/>");
					passflag = true;
				}
				if(passflag){
					alert("Modify successfully");
					$("#subform").submit();
				}
			});
		});
	</script>
  </head>
  
  <body>
	<div id="form">
		<form id="subform" action="FrontServlet?module=User&command=UpdateUser" method="post">
   			<table id="table">
   				<tr height="40px">
					<td width="15%">Username</td>
					<td width="85%">${user.username}</td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" id="password" value="${user.password}" name="password"></td>
				</tr>
				<tr>
					<td>Re-enter password</td>
					<td><input type="password" id="repassword" value="${user.password}"></td>
				</tr>
				<tr>
					<td>Real name</td>
					<td><input type="text" name="truename" value="${user.truename}"></td>
				</tr>
				<tr>
					<td>Mobile</td>
					<td><input type="text" name="phone" value="${user.phone}" id="phone"></td>
				</tr>
				<tr>
					<td>Contact address</td>
					<td><input type="text" name="address" value="${user.address}"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><label style="font-size: 1em" id="la"><br/></label></td>
				</tr>
   			</table>
   			<div align="center"><br/></div>
			<input type="reset" value="Reset" class="btn" id="res">
			<input type="button" value="Update" class="btn" id="sb">
		</form>
	</div>
	<br/><br/><br/><br/><br/><br/><br/><br/>
  </body>
</html>
