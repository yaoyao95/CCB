<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String npath = request.getContextPath();
String nbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+npath+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=nbasePath%>">
    
    <title>西大粤语社</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="CSS/maiBoxLayout.css">

  </head>
	 <div style="width:100%;border:1px solid #9999ff;">
		<table width="100%" cellspacing="0" cellpadding="0" align="center">
			<tbody>
				<tr>
					<td style="line-height:30px;text-align:center;">
						信箱状态：目前有短消息<b> 200 </b>条；最多可存消息 <b>200</b> 条；使用率 <b>100.000%</b>
					</td>
				</tr>
				<tr>
					<td style="line-height:30px;text-align:center;font-size:14px;">
						<a href="user/toMailBox2">收件箱</a> | 
						<a href="user/toSendMailBox">发件箱</a> | 
						<a href="#">消息跟踪</a> | 
						<a href="user/toWriteMail">写新消息</a> | 
						<a href="message.php?action=banned">屏蔽列表</a> | 
						<a style="cursor:pointer;" onclick="return checkset();">清空</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="line"></div>
</html>
