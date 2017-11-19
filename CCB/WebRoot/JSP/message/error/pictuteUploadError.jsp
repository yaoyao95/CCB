<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>西大粤语社</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
    .main{ width:1000px;
	background-color:#0FF;
	margin-left:auto;
	margin-right:auto;
	height:750px;}
	.tips{
	width:500px;
	margin-left:auto;
	margin-right:auto;
	position:relative;
	top:200px;
	}
    </style>
  </head>
  
  <body>
      <div class="main">
          <div id="tips" class="tips">
              <span><a href="javascript:;" onclick="location.href='javascript:history.go(-1);'">${requestScope.tips},点击返回</a></span>
          </div>
      </div>
  </body>
</html>
