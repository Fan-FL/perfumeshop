<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML>
<html>
<head>
<title>Backstage Management System</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/back/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/back/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/back/assets/css/main-min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/back/Css/dpl.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/back/Css/bui.css" rel="stylesheet"/>
</head>
<body>

	<div class="header">

		<div class="dl-title">
		</div>

		<div class="dl-log">
			Welcome You，<span class="dl-log-user">${sessionScope.manager.username }</span><a
				href="/FrontServlet?module=manager&command=ManagerLogout" title="Quit System"
				class="dl-log-quit" onclick="return confirm('Are you sure you want to quit?');">[Quit]</a>
		</div>
	</div>

    <ul class="nav nav-tabs" id="myTab">
        <li><a href="/FrontServlet?module=Product&command=ManagerViewAllProduct"
               >products</a></li>
    </ul>
</body>
</html>