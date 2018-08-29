<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>PerfumeShop_register</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<!-- start menu -->
<link href="css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
<script src="js/jquery.easydropdown.js"></script>
<style type="text/css">
	.submit {
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
	.submit:hover{
		background:#4CB1CA;
	}
	p.msg a, p.note a {
		text-decoration: underline;
		color:#555;
	}
	p.msg {
		float: right;
		font-size: 12px;
		padding: 15px 60px 0 15px;
	}
</style>
<script type="text/javascript" language="javascript">
	   $(function(){
		   var flag = false;
		   var $username = $("#username");
		   var $password = $("#password");
		   var $confirmpass = $("#confirmpass");
		   var $username_msg = $("#username_msg");
		   var $password_msg = $("#password_msg");
		   var $confirmpass_msg = $("#confirmpass_msg");
		   var url = "userregister";
		   
		   $username.focus(function(){
			   this.style.imeMode = 'disabled';// disable input method
			   $username_msg.html("<br/>");
		   }).blur(function(){
			   var username = $username.val();
			   if(!isUsername(username)){
				   $username_msg.text("Length must be within 4-16, initiated with character.");
				   return;
			   }
			   var json = {"username":username};
			   $.post(url,json,function(data){
				   var regStatus = data[0].regStatus;
				   if(regStatus=="hasThisUser"){
					   $username_msg.text("User name already exists！");
				   }else {
					   $username_msg.html("<img src='images/ok.jpg'>");
					   flag = true;
				   }
				   if(regStatus=="regSuccess"){
					   flag = true;
				   }
			   },"json");
			   
		   });
		   $password.focus(function(){
			   this.style.imeMode = 'disabled';
			   $password_msg.html("<br/>");
		   }).blur(function(){
			   var password = $password.val();
			   if(!isPassword(password)){
				   $password_msg.text("Length must be within 6-18, initiated with character.");
				   return;
			   }
			   flag = true;
			   $password_msg.html("<img src='images/ok.jpg'>");
		   });
		   $confirmpass.focus(function(){
			   this.style.imeMode = 'disabled';
			   $confirmpass_msg.html("<br/>");
		   }).blur(function(){
			   var confirmpass = $confirmpass.val();
			   var password = $password.val();
			   if(!isPassword(confirmpass)){
				   $confirmpass_msg.text("Length must be within 6-18, initiated with character.");
				   return;
			   }
			   if(!isSame(confirmpass, password)){
				   $confirmpass_msg.text("Passowords do not match.");
				   return;
			   }
			   flag = true;
			   $confirmpass_msg.html("<img src='images/ok.jpg'>");
		   });
		   $("#registerSubmit").click(function(){
			   var username = $username.val();
			   var password = $password.val();
			   var confirmpass = $confirmpass.val();
			   if($.trim(username)=="" || $.trim(confirmpass)==""){
				   alert("User name and password can not be empty");
				   return false;
			   }
			   if(!isUsername(username)){
				   $username_msg.text("Length must be within 4-16, initiated with character.");
				   return false;
			   }
			   if(!isPassword(password)){
				   $password_msg.text("Length must be within 6-18, initiated with character.");
				   return false;
			   }
			   if(!isSame(confirmpass, password)){
				   $confirmpass_msg.text("Passowords do not match.");
				   return false;
			   }
			   if(!flag){
				   return false;
			   }
			   var json = {"username":username,"password":password};
			   $.post(url,json,function(data){
				   var regStatus = data[0].regStatus;
				   if(regStatus=="hasThisUser"){
					   $username_msg.text("User name already exists！");
				   }
				   if(regStatus=="regSuccess"){
					   if(confirm("Registration successful，Click to enter home page")){
						   window.location.href = "blank.jsp";
					   }
				   }
				   if(regStatus=="regFail"){
					   alert("Registration failed");
				   }
			   },"json");
			   return false;
		   });
	   });
	   
	   function isSame(str1,str2){
		   return str1==str2;
	   }
	   
	   function isUsername(username){
		   var name = new RegExp("^[a-zA-z][a-zA-Z0-9_]{3,15}$");
		   return name.test(username);
	   };
	  
	   function isPassword(password){
		   var word = new RegExp("[a-zA-Z0-9_]{6,18}");
		   return word.test(password);
	   }
	   
</script>
</head>
<body> 
	<jsp:include page='login?method=header' flush="true"></jsp:include>
          <div class="register_account">
          	<div class="wrap">
    	      <h4 class="title">Create an account</h4>
	    		<div class="register_account_form">
	    		   <form action="#" method="post">
		    			<table id="register_account_table">
		    				<tr>
		    					<td width="15%">Username</td>
		    					<td width="100%">
		    					<input value="" type="text" id="username" name="username"><br/>
		    					<span id="username_msg" style="color:red;font-size: 0.8em;line-height: 24px">
		    					<br/></span>
		    					</td>
		    				</tr>
		    				<tr>
		    					<td>password</td>
		    					<td>
		    					<input value="" id="password" type="password" name="password"><br/>
		    					<span id="password_msg" style="color:red;font-size: 0.8em;line-height: 24px"><br/></span>
		    					</td>
		    				</tr>
		    				<tr>
		    					<td>Re-enter password</td>
		    					<td align="left">
		    					<input value="" id="confirmpass" type="password" name="confirmpass"><br/>
		    					<span id="confirmpass_msg" style="color:red;font-size: 0.8em;line-height: 24px"><br/></span>
		    					</td>
		    				</tr>
		    			</table>
		    			<div align="center">
		    				<label style="color: #555">
								User length must be within 4-16, initiated with character.<br/>
								Password length must be within 6-18.
		    				</label>
		    			</div>
		    			<input type="submit" id="registerSubmit" value="Register" class="submit">
					</form>
		    	 </div>
		    <div class="clear"></div>
    	</div>
    </div>
</body>
</html>