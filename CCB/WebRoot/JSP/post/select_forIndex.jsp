<%@ page language="java" import="java.util.*,com.CantoneseClubBBS.domain.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
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
	<meta http-equiv="description" content="粤语大讲堂">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	
	<style type="text/css">
		table{font-size: 14px;}
	</style>
  </head>
  
  <body>
	<div class="main">
		<center class="middle">       
			<div  style="border:1px solid #DDDDFF;border-right:1px solid #DDDDFF; height: auto; width: 100%; ">
				<table class="thread2" cellspacing="0" cellpadding="0" width="100%">
					<s:iterator value="postThemes" status="count">
						<s:if test="section.name_abbr != 'srrj'">
							<tr>
								<td style="text-align: left;" width="70%">
									<a href= "post/getPTByIdByPage.action?postTheme.id=${id}">
										<s:property value="title"  escapeHtml="false"/>
									</a>
								</td>
								<td width="15%" valign="top" style="color: #999;">
						        	<s:property value="user_.getName()"/><br/>
						        	<s:property value="the_last_reply_user_name"/>
								</td>
								<td width="15%" valign="top" style="color: #999;">
									<br/>
									<s:if test="the_last_reply_time == null">
										<s:date name="issuedDate" format="MM-dd HH:mm"/>
									</s:if>
									<s:else>
										<s:date name="the_last_reply_time" format="MM-dd HH:mm"/>
									</s:else>
								</td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
			</div>
		</center>
	</div>
</body>
</html>
