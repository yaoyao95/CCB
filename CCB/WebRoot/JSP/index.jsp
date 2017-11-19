<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>西大粤语社</title>
    <link rel="shortcut icon" href="http://localhost:8080/CCB/img/nwuccc.ico" type="image/x-icon" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/navigation.css">
	
	
	
	<script type="text/javascript">
	    var jump = function(){
	    	alert("jump to");
	    }
	</script>
  </head>
  
  <body>
	<div class="main">
		<div class="top" align="center">
			<s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<%-- <hr><br><br>${application.test }<br>${application.tsnsT[0] }<br>${application.tsnsT[1] }<br>
		<s:select list="%{#application.tsnsT}"/><br><hr> --%>
		<center class="middle">
			<%-- 
			<div style="width: 90.25%;" align="center">
				<img src="img/1.jpg" style="width: 30%;">
				<img src="img/2.jpg" style="width: 28%;">
				<img src="img/3.jpg" style="width: 40%;">
			</div> --%>
			<div style="height: 10px;"></div>
		    <div class="notice">
		        <div class="notice_1" align="center">
		            <span>公告栏</span>
		        </div>
		        <div>
		            <s:action name="select_forIndex" namespace="/notices" executeResult="true"></s:action>
		        </div>
		    </div>
		    <div style="height: 10px;"></div>
		    
		    <div class="teaching">
		        <div class="teaching_1" align="center">
		            <span>教学区</span>
		        </div>
		        <div>
		            <s:action name="select_forIndex" namespace="/post" executeResult="true">
		            	<s:param name="section.region_name_abbr">jx</s:param>
		            </s:action>
		        </div>
		    </div>
		    
		    <%-- <div class="task">
		        <div class="task_1" align="center">
		            <span>任务区</span>
		        </div>
		        <div>
		            <s:action name="select_forIndex" namespace="/notices" executeResult="true"></s:action>
		        </div>
		    </div>
		    <br/> --%>
		    <div style="height: 10px;"></div>
		    <div class="history">
		        <div class="history_1" align="center">
		            <span>足迹区</span>
		        </div>
		        <div>
		           <s:action name="select_forIndex" namespace="/post" executeResult="true">
		            	<s:param name="section.region_name_abbr">zj</s:param>
		            </s:action>
		        </div>
		    </div>
		    
		    <div style="height: 10px;"></div>
		    <div class="water">
		        <div class="water_1" align="center">
		            <span>水区</span>
		        </div>
		        <div>
		            <s:action name="select_forIndex" namespace="/post" executeResult="true">
		            	<s:param name="section.region_name_abbr">sq</s:param>
		            </s:action>
		        </div>
		    </div>
		    <br/>
		</center>
	</div>
	<br/>
	<div class="bottom">
	    <div class="Link_exchange">
	        <div class="Link_exchange_1" align="center">
		            <span>友情链接</span>
		    </div>
	    </div>
	    <div class="message">
	        <div class="message_1" align="center">
		            <span>版本 1.0</span>
		    </div>
		    <div class="message_2" align="center">
		            <span>联系方式：AoiHoshino999@gmail.com</span>
		    </div>
	    </div>
	</div>
	<%-- <a href="totext.action">totest</a> --%>
</body>
</html>
