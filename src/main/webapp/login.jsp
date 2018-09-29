<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="JavaScript">
	if(window != top){
		top.location.href=location.href;
	}
</script>
<title>PerfumeShop_Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<!-- dropdown -->
<script src="js/jquery.easydropdown.js"></script>
<script type="text/javascript">
	$(function(){
		var responseMsg = "${param.responseMsg}";
		if(responseMsg == "userIsNotLogin"){
			$("#errorMsg").text("Please log in!");
		}
		$("#loginSubmit").click(function(){
			var username = $.trim($("#login_username").val());
			var password = $("#login_passwd").val();
			if(username==""||password==""){
				$("#errorMsg").text("Please enter username and password");
				return false;
			}else{
				$("#errorMsg").text("");
			}
			var json = {"username":username,"password":password};
			var url = "FrontServlet?module=User&command=UserLogin";
			$.post(url,json,function(data){
				var logStatus = (data[0].logStatus=="true");
				$("#errorMsg").text("");
				if(logStatus){
					window.location.href = "blank.jsp";
				}else {
					$("#errorMsg").text("Wrong username or password");
				}
			},"json");
			return false; 
		 });
	});
</script>

</head>
<body>
	<jsp:include page='FrontServlet?module=User&command=UserHeader' flush="true"></jsp:include>
        <div class="login">
          	<div class="wrap">
				<div class="col_1_of_login span_1_of_login">
					<h4 class="title">New user register</h4>
					<p>You can sign up for free if you don't have an account！</p>
					<div class="button1">
					   <a
							   href="register.jsp"><input type="button" name="Submit"
														  value="Register"></a>
					 </div>
					 <div class="clear"></div>
				</div>
				<div class="col_1_of_login span_1_of_login">
				<div class="login-title">
	           		<h4 class="title">login</h4>
					<div id="loginbox" class="loginbox">
						<form action="FrontServlet?module=User&command=UserLogin" method="post" name="login"
							  id="login-form">
						  <fieldset class="input">
						    <p id="login-form-username">
						      <label>Username</label>
						      <input id="login_username" type="text" name="username" class="inputbox" size="18" autocomplete="off"><input type="hidden" id="verify_result" value="false">
						    </p>
						    <p id="login-form-password">
						      <label>Password</label>
						      <input id="login_passwd" type="password" name="password" class="inputbox" size="18" autocomplete="off">
						    </p>
						    <div class="remember">
							    <p id="login-form-remember">
							      <label><font id="errorMsg" style="font-style:italic;font-size:1.5em;color: red"></font></label>
							   </p>
							    <input id="loginSubmit" type="submit" name="Submit"
                                       class="loginbtn" value="Log in">
                            </div>
						  </fieldset>
						 </form>
					</div>
			    </div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
</body>
</html>