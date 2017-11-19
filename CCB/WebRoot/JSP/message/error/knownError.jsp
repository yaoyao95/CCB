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
		    		
	    			<s:if test="%{#request.operation == 'toHomePage'}">
		    			 <a href="javascript:;" onclick="location.href='#'">
		    	    		<s:actionerror/>${requestScope.tips}<br>
		    	    		点击返回主页
		    	    	 </a>
	    			</s:if>
		    		
		    		
		    		<s:elseif test="%{#request.operation == null}">
		    	    	<a href="javascript:;" onclick="location.href='javascript:history.go(-1);'">
			    	    	<s:actionerror/>${requestScope.tips}<br>
			    	    	<s:fielderror></s:fielderror>
			    	    	点击返回
			    	    </a>
		    	    </s:elseif>
		    	    
		    	</font>
		    </center>
	    <div class="bottom"></div> 
	    </div>
	    
  </body>
  <%-- <s:debug></s:debug> --%>
</html>
