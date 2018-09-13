<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<style type="text/css">
ul.subCartList li i a:link, a:visited, a:active {
    text-decoration: none;
}
ul.subCartList li i a:hover { color: #D93600; text-decoration: none;}
ul.subCartList li i a {
    color: #333;
    outline: medium none;
    text-decoration: none;
	display: block;
	font-size: 16px;
	line-height: 18px;
	margin-left: 15px;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 250px;
	height:30px;
	overflow: hidden;
	text-align: left;
}
ul.subCartList li {
	width: 300px;
}
</style>
</head>
<script type="text/javascript">
    $(function(){

        $(".goCart").click(function(){
            var userId = "${sessionScope.userId}";
            if(userId > 0){
                return true;
            }
            if(confirm("Please log in, click OK to the login page!")){
                window.location.href = "${pageContext.request.contextPath}/login.jsp";
            }
            return false;
        });
        $("#logout").click(function(){
            return confirm('Are you sure to log out？');
        });
    });

</script>
<body>
	 <div class="header-top">
			<div class="wrap"> 
			 <div class="cssmenu">
				<ul class="my_index_menu">
					<c:if test="${sessionScope.userId eq null}">
						<li><a href="login.jsp">Login</a></li>
						<li><a href="register.jsp">Register</a></li>
					</c:if>
					<c:if test="${not(sessionScope.userId eq null)}">
						<li><a href="account.jsp">
						<dir id="test1">${sessionScope.userName}</dir>
						</a></li>
						<li><a class="goCart" href="viewcart">Cart</a></li>
						<li><a id="logout" href="logout">Logout</a></li>
					</c:if>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="header-bottom">
	    <div class="wrap">
			<div class="header-bottom-left">
				<div class="logo">
					<a href="blank.jsp"><img src="images/logo.png" alt=""/></a>
				</div>
				<div class="menu">
	            <ul class="megamenu skyblue">
						<li class="active grid"><a href="viewallproduct">All products</a></li>
					</ul>
				</div>
			</div>
	 		<div class="header-bottom-right">
				<div class="tag-list">
					<ul class="icon1 sub-icon1 profile_img">
						<li><a class="active-icon c2" href="viewcart"> </a>
						</li>
					</ul>
					<ul id="xiaocart" class="last">
						<li><a class="goCart" href="viewcart">Cart(${requestScope.cartCount})</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>