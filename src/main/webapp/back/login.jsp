<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Manager</title>
<link rel="stylesheet" type="text/css" href="Css/login.css" />
<script type="text/javascript" src="/back/Js/jquery-1.8.1.min.js"></script>
<script language="JavaScript">
	if(window != top){
		top.location.href=location.href;
	}
</script>
<script type="text/javascript">
	$(function(){
		$("#managerName").focus();
		$("body").keydown(function(event) {
            if (event.keyCode == "13") {
                $("#loginSubmit").click();
            }
        });
		$("#loginSubmit").click(function(){
			var managerName = $("#managerName").val();
			var managerPassword = $("#managerPassword").val();
			if($.trim(managerName)=="" || $.trim(managerPassword)==""){
				alert("Please input username and password!");
				return;
			}
			var json = {"managerName":managerName,"managerPassword":managerPassword};
			$.post("/FrontServlet?module=manager&command=ManagerLogin",json,function(data){
				var check = data[0].check;
				if(check == "checkin"){
					if(confirm("Login successfully, click 'OK' to jump to System Management Page.")){
						window.location.href = "/FrontServlet?module=Product&command=ManagerViewAllProduct";
					}
				}else {
					alert("Incorrect user name or password! ");
				}
			},"json");
		});
	});
</script>
</head>
<body>
	<div id="loginpanelwrap">
		<div class="loginheader">
			<div class="logintitle">
				<a>PerfumeStore</a>
			</div>
		</div>
		<div class="loginform">
			<div class="loginform_row">
				<label>account management:</label> <input id="managerName" name="managerName" type="text" class="loginform_input"
					/>
			</div>
			<div class="loginform_row">
				<label>password:</label> <input id="managerPassword" name="managerPassword"
										 type="password" class="loginform_input"
					/>
			</div>
			<div class="loginform_row">
				<a id="user" href="/blank.jsp">Buyer page</a>
				<input id="loginSubmit" type="submit" class="loginform_submit" value="Login" />
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>