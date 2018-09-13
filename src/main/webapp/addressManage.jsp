<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>PerfumeStore_MyOrder</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.mycar-index{
		margin: 0 auto;
		width: 800px;
	}
	#cart-wrapper{
		width: 800px;
		height:330px;
	}
	#cart-wrapper table{
		width: 800px;
		border-left:1px solid #f5f5f5;
		border-right:1px solid #f5f5f5;
	}
	#cart-wrapper table th{
		background: #f5f5f5;
		height: 30px;
		text-align:center;
	}
	#cart-wrapper table td{
		border-bottom: 1px solid #f5f5f5;
		color: #333;
		height: 70px;
		font-size: 14px;
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
		margin-left: 75%;
	}
	.btn:hover{
		background:#4CB1CA;
	}
	a:LINK{
		text-decoration: none;
		color: #555;
	}
	a:hover{
		text-decoration: none;
		cursor:pointer;
		background:#4CB1CA;
	}
	a:VISITED{
		text-decoration: none;
		color: #555;
	}
	.order-blank{  
	    height:100px;
	    line-height:100px;
	    font-size:16px;
	    text-align:center;
	   	width:100%;
	    margin:30px 0px;
    }
</style>
</head>
<body>
	<div class="mycar-index">
		<c:choose>
			<c:when test="${!(empty addresses)}">
				<div id="cart-wrapper">
					<form action="singleAddress.jsp">
						<table cellspacing="1.5">
							<thead>
								<tr>
									<th width="550px">Shipping address</th>
									<th width="150px">Operation</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${addresses}" var="add" varStatus="statu">
									<tr>
										<td>${add.sendPlace}&nbsp;&nbsp;(&nbsp;${add.sendMan}&nbsp;&nbsp;&nbsp;collect&nbsp;)&nbsp;&nbsp;${add.sendPhone}</td>
										<td align="center"><a
												href="deleteaddress?addId=${add.id}"
												onclick="return confirm('Are you sure to delete?');">Delete</a>
											&nbsp;&nbsp;&nbsp;&nbsp;<a
													href="viewsingleaddress?addId=${add.id}">
												Modify</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div><br/></div>
						<input type="submit" class="btn" value="Add Shipping address">
					</form>
				</div>
				<div><br/><br/><br/></div>
			</c:when>
			<c:otherwise>
				<div class="order-blank">
					You don't have sipping address information, please <a
						href="singleAddress.jsp"><font
						color="#555">add</font></a>!
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<br/>
</body>
</html>