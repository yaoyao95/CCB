<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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
	<meta http-equiv="description" content="已知错误">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" type="text/css" href="CSS/layout.css">
  </head>
  
  <body>
    <div class="main">
	    <div class="top" align="center">
	    	<s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
	    </div>
		    <center class="middle">
		    	<font color="red">
			    	<a href="javascript:" onclick="self.location=document.referrer;">
			    	   <s:actionmessage/>${requestScope.tips}
			    	</a>
		    	</font>
		    </center>
	    <div class="bottom"></div> 
	    </div>
	    <%-- <s:debug></s:debug> --%>
  </body>
</html>
