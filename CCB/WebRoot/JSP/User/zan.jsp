<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<meta http-equiv="description" content="@提醒">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	<style type="text/css">
	
	</style>
  </head>
  
  <body>
  	<div class="main">
  		<div class="top" align="center">
  			<jsp:include page="../navigation.jsp" flush="true"></jsp:include>
  		</div>
  		<div class="middle" style="width: 90%" align="center">
  			<%--显示本页面是什么区 并提供导航 --%>
			<jsp:include page="../locationAndNavigation.jsp" flush="true"></jsp:include>
			<div style="margin-top: -10px;">
				<s:form action="toZan" namespace="/user" method="post"
						theme="simple">
					<span>点赞用户</span><s:textfield name="tigc.theAdmireUserName" />&nbsp;&nbsp;
					<span>每页显示：</span><s:textfield name="pageModel.pageSize" size="2"/>条
					<s:submit type="submit" value="搜 索"/>&nbsp;&nbsp;
				</s:form>
			</div><br/>
			
			<div  style="border:1px solid #DDDDFF;border-right:1px solid #DDDDFF; height: auto; width: 90%; ">
				
				<table class="thread1" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td width="13%">时间</td>
						<td width="7%">类型</td>
						<td width="60%">标题</td>
						<td width="15%">点赞人</td>
						<td width="5%">已读</td>
					</tr>
					<s:iterator value="thatIsGoods" status="count">
						<tr>
						    <td>
						    	<s:if test="isNew == true">
						    		<span style="color:red">
						    			<s:date name="issuedDate"  format="yy-MM-dd HH:mm" />
						    		</span>
						    	</s:if>
						    	<s:else>
					    			<s:date name="issuedDate"  format="yy-MM-dd HH:mm" />
						    	</s:else>
						    </td>
							<td>
								<s:if test="thPostReply == null">
									<s:if test="thePostTheme != null">
										主题帖
									</s:if>
								</s:if>
								<s:if test="thPostReply != null">
									回帖
								</s:if>
							</td>
							<td style="text-align: left;padding-left: 10px;">
							    <s:a href="%{href}">
							    	<s:property value="thePostTheme.title"/>
							    	<s:if test="thPostReply != null">
							    		&nbsp;&nbsp;<s:property value="thPostReply.storey"/>楼
							    	</s:if>
							    </s:a>
							</td> 
							<td style="color:red;">
							    <s:property value="theAdmireUserName"/>
							</td>
							<td>
							    <s:if test="isRead == 0">
							    	<span style="color:red">否</span>
							    </s:if>
							    <s:if test="isRead == 1">
							    	<span>是</span>
							    </s:if>
						    </td>
						</tr>
					</s:iterator>
				</table>
				<%--需要修改 --%>
				<my_p:pager pageIndex="${pageModel.pageIndex}"
					pageSize="${pageModel.pageSize}"
					recordCount="${pageModel.recordCount}"
					submitUrl="user/toZan?pageModel.pageIndex={0}&pageModel.pageSize=${pageModel.pageSize}" />
			</div>
  		</div>
  		<div class="top">
  		</div>
  	</div>
  </body>
  <%-- <s:debug></s:debug> --%>
</html>
