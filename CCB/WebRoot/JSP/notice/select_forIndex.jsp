<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="my_p" uri="/pager-tags" %>
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
	<meta http-equiv="description" content="公告">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
  </head>
  
  <body>
	<div class="main">
		<center class="middle">
			<center style="border:1px solid #DDDDFF; width: 100%">
				<table align="center" class="thread1" width="100%">
					<s:iterator value="notices" status="count">
						<tr>
							<td width="30%"><s:property value="title" />
							<td style="text-align: left" width="60%"><s:property value="content" escapeHtml="false" /> 
							     <s:if
									test="updateDate != null">
									<br />
									<span><font color="red">修改于：<s:date
												name="updateDate" format="yy-MM-dd HH时" />
									</font></span>
								</s:if></td>
							<td width="10%"><s:date name="issuedDate" format="MM月-dd日 HH时" /> <!--<s:property value="%{@com.CantoneseClubBBS.util.Display@DateForDisply(issuedDate)}"/>-->
							</td>
						</tr>
					</s:iterator>
			</table>
			</center>
		</center>
	</div>
</body>
</html>
