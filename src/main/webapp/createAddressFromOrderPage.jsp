<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Shipping address</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery1.min.js"></script>
<style type="text/css">
.form {
	width: 500px;
	height: 250px;
	padding-left: 10%;
}

.table {
	width: 500px;
}

.form form input[type="text"] {
	font-size: 0.95em;
	color: #777;
	padding: 8px;
	outline: none;
	margin: 10px 0;
	width: 300px;
	font-family: 'Exo 2', sans-serif;
	border: 1px solid #aaa;
	box-shadow: 0 0 2px #aaa;
	-webkit-box-shadow: 0 0 2px #aaa;
	-moz-box-shadow: 0 0 2px #aaa;
	-o-box-shadow: 0 0 2px #aaa;
}
.form form label {
	font-size: 0.8em;
	color: red;
	line-height: 2.5em;
}
.btn {
	padding: 10px 30px;
	color: #FFF;
	cursor: pointer;
	background: #555;
	border: none;
	outline: none;
	font-family: 'Exo 2', sans-serif;
	font-size: 1em;
	margin-left: 90px;
}

.btn:hover {
	background: #4CB1CA;
}

i {
	color: red;
	font-size: 0.85em;
}
.table tr{
	width: 360px;
}
.table tr td.textTd{
	width: 60px;
}
.table tr td.inputTd{
	width: 300px;
}
</style>
<script type="text/javascript" src="js/layer/layer.js"></script>
<script type="text/javascript">
    $(function() {
        $("#asb").click( function () {
            var phone = $("#phone").val();
            var sendplace = $("#sendplace").val();
            var sendman = $("#sendman").val();
            if($.trim(sendplace) == "" || $.trim(sendman) == ""){
                alert("Please fill up the form.");
                return;
            }
			var json = {"sendphone":phone,"sendplace":sendplace,"sendman":sendman};
			$.post("addaddress", json,function(data){
				if(data[0].addressId != 0){
					//parent.window.location.reload();
					$("#createAddressDiv",window.parent.document).before("<input name='addressId' "
						+"checked='checked' type='radio' value='"+data[0].addressId+"'>"
						+"<label>"+json.sendplace+"&nbsp;("+json.sendman+"&nbsp;collect)"
						+"&nbsp;"+json.sendphone+"</label><br/>");
					alert("Added successfully!");
					var index = parent.layer.getFrameIndex(window.name); //get iframe index
					parent.layer.close(index); //close
				}
			},"json" );
        });
    });
</script>
</head>

<body>
	<div class="form">
		<form id="addform" action="addaddress" method="post">
			<table class="table">
				<tr>
					<td class="textTd">Shipping address</td>
					<td class="inputTd"><input type="text" name="sendplace" id="sendplace">
					</td>
				</tr>
				<tr>
					<td>Consignee</td>
					<td><input type="text" name="sendman" id="sendman">
					</td>
				</tr>
				<tr>
					<td>Mobile</td>
					<td><input type="text" name="sendphone" id="phone">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><label style="font-size: 1em" id="la"><br/></label></td>
				</tr>
			</table>
			<div align="center">
				<br />
			</div>
			<input type="reset" value="Reset" class="btn"> <input
				type="button" value="Confirm" class="btn" id="asb">
		</form>
	</div>
</body>
</html>
