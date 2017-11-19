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
	
	
	<script type="text/javascript">
	    //跳转到notices/toAddNotice.action
	    var toAddNotice = function(){
	        location.href = "notices/toAddNotice.action";
	    };
	    //确认删除
	    var deleteFn = function(id){
	     if (confirm("您确定要删除吗？")){
	         location.href = "notices/delNotice.action?notice.id=" + id;
	     }
	    };
	</script>
  </head>
  
  <body>
	<div class="main">
		<div class="top" align="center">
		    <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<center class="middle">
			<center style="border:1px solid #DDDDFF; width: 90%; height: 40px;">
				<div style="margin-top: 10px">
					<s:form action="select_byPage" namespace="/notices" method="post"
						theme="simple">
					标题：<s:textfield name="notice.title" />&nbsp;&nbsp;
					内容：<s:textfield name="notice.content" />&nbsp;&nbsp;
					一页显示公告条数：<s:textfield name="pageModel.pageSize" size="2"/>
					<input type="submit" value="查 询" />&nbsp;&nbsp;
					<input type="button" value="发布公告" onclick="toAddNotice();" />
					</s:form>
				</div>
			</center>
			<center style="border:1px solid #DDDDFF; width: 90%">
				<table align="center" class="thread1" width="100%">
					<tr>
					    <td width="4%">编号</td>
						<td width="15%">题目</td>
						<td width="63%">内容</td>
						<td width="12%">发布时间</td>
						<td width="6%">操作</td>
					</tr>
					<s:iterator value="notices" status="count">
						<tr>
						    <td><s:property value="#count.getCount" /></td>
							<td><s:property value="title" />
							<td style="text-align: left"><s:property value="content" escapeHtml="false" /> 
							     <s:if
									test="updateDate != null">
									<br />
									<span><font color="red">修改于：<s:date
												name="updateDate" format="yy-MM-dd HH时" />
									</font></span>
								</s:if></td>
							<td><s:date name="issuedDate" format="yy年-MM月-dd日 HH时" /> <!--<s:property value="%{@com.CantoneseClubBBS.util.Display@DateForDisply(issuedDate)}"/>-->
							</td>
							<td><a href="javascript:;" onclick="deleteFn(${id})">删除</a>|
								<a href="notices/toUpdateJSP.action?notice.id=${id}">修改</a></td>
						</tr>
					</s:iterator>
			</table>
			</center>
			<my_p:pager pageIndex="${pageModel.pageIndex}"
				pageSize="${pageModel.pageSize}"
				recordCount="${pageModel.recordCount}"
				submitUrl="notices/select_byPage.action?pageModel.pageSize=${pageModel.pageSize}&pageModel.pageIndex={0}&notice.title=${notice.title}&notice.content=${notice.content}" />
			<%-- <s:debug></s:debug> --%>
			<%-- 加上s:debug会报错，不用管 --%>
		</center>
		<div class="bottom"></div>
	</div>
</body>
</html>
