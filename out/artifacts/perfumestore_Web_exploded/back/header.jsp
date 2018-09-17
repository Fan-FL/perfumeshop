<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath}/back/">

<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/back/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/back/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/back/assets/css/main-min.css" rel="stylesheet" type="text/css" />
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
</head>
<body>

	<div class="header">

		<div class="dl-title">
			<img width="100px" height="20px" src="assets/img/logo.png">
		</div>

		<div class="dl-log">
			欢迎您，<span class="dl-log-user">${sessionScope.manager.username }</span><a
				href="logout.bg" title="退出系统"
				class="dl-log-quit" onclick="return confirm('您确定退出吗？');">[退出]</a>
		</div>
	</div>

    <div class="demo-content">
        <div id="tab"></div>


        <script src="http://g.tbcdn.cn/fi/bui/jquery-1.8.1.min.js"></script>
        <script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
        <script src="http://g.alicdn.com/bui/bui/1.1.21/config.js"></script>

        <!-- script start -->
        <script type="text/javascript">

            BUI.use('bui/tab',function(Tab){

                var tab = new Tab.Tab({
                    render : '#tab',
                    elCls : 'link-tabs',
                    autoRender: true,
                    children:[
                        {text:'All product',value:'1',href:'/manager_viewallproduct'},
                        {text:'标签二',value:'2',href:'#'},
                        {text:'标签三',value:'3',href:'#'}
                    ],
                    itemTpl : '<a href="{href}">{text}</a>'
                });
                // tab.setSelected(tab.getItemAt(0));

            });

        </script>
        <!-- script end -->
    </div>
</body>
</html>