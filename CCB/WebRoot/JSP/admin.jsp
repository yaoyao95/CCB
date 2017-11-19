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
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/navigation.css">
	<style type="text/css">
		.UserAdmin{
		border: 1px solid #DDDDFF;
		}
	</style>
  </head>
  
  <body>
	<div class="main">
		<div class="top" align="center">
			<jsp:include page="./navigation.jsp" flush="true"></jsp:include>
		</div>
		<div class="middle" style="width: 80%">
			<jsp:include page="./locationAndNavigation.jsp" flush="true"></jsp:include>
	        ${requestScope.tips }
	        <s:fielderror style="color:red"></s:fielderror>
	        <s:actionerror style="color:red"/>
	        <div class="UserAdmin">
	        	用户管理<br/>
	        	<div>
	        		<s:form action="getUserByIdOrName" namespace="/user" method="post"  theme="simple">
	        			用户id:<s:textfield name="user_.id" id="findUserIdInput" title="要查找的用户的id。如果用户id和用户名都输入了，优先使用id来查询，如果查找不到则使用用户名查找"></s:textfield>
	        			用户名：<s:textfield name="user_.name" title="要查找的用户的用户名"></s:textfield>
	        			<s:submit id="findUser" value="查找"></s:submit>
	        		</s:form>
	        	</div>
	        	<div class="UserAdmin">
	        		<table>
						<tr>
							<td rowspan="3">
							     <img src = "<s:property value="user_.picture_path"/>" height="200" width="140"/>
							</td>
							<td>
								用户名：
							</td>
							<td>
								<s:property value="user_.name"/>
							</td>
						<tr>
						<tr>
							<td>权限</td>
							<td>
								<s:property value="userAuthority" escapeHtml="false"/>
							</td>
						</tr>
					</table>
	        	</div>
	        	<div class="UserAdmin">
	        		操作一览：<br/>
	        		<s:form action="userManage" namespace="/user" method="post" theme="simple">
	        			<s:hidden name="user_.id"></s:hidden>
	        			<s:if test="%{#isUserAdmin == true || #isHighestAdmin == true}">
	        				禁言<s:checkbox name="isForbidden_" fieldValue="true" title="禁言则打钩，解除禁言则去掉钩"></s:checkbox><br/>
	        			</s:if>
	        			<s:if test="%{#isHighestAdmin == true}">
	        				权限赋予：<br/>
	        				设为公告区管理员<s:checkbox name="isNoticeAdmin_" fieldValue="true" title="是否设置为公告区管理员"></s:checkbox><br/>
	        				设为教学区管理员<s:checkbox name="isTeachingAdmin_" fieldValue="true" title="是否设置为教学区管理员"></s:checkbox><br/>
	        				设为任务区管理员<s:checkbox name="isTaskAdmin_" fieldValue="true" title="是否设置为任务区管理员"></s:checkbox><br/>
	        				设为足迹区管理员<s:checkbox name="isFootprintAdmin_" fieldValue="true" title="是否设置为足迹区管理员"></s:checkbox><br/>
	        				设为用户管理员<s:checkbox name="isUserAdmin_" fieldValue="true" title="是否设置为用户管理员"></s:checkbox><br/>
	        				设为水区管理员<s:checkbox name="isWaterAdmin_" fieldValue="true" title="是否设置为水区管理员"></s:checkbox><br/>
	        				论坛整体页面内容管理员<s:checkbox name="isBBSPageAdmin_" fieldValue="true" title="是否论坛整体页面内容管理员"></s:checkbox><br/>
	        				设为总管理员<s:checkbox name="isHighestAdmin_" fieldValue="true" title="是否设置为总管理员"></s:checkbox><br/>
	        			</s:if>
	        			<s:if test="%{#isUserAdmin == true || #isHighestAdmin == true}">
	        				<s:submit value="确认"></s:submit>
	        			</s:if>
	        		</s:form>
	        	</div>
	        </div>
	        <div class="pageAdmin">
	        	<div>论坛页面管理</div>
	        	<s:if test="%{#isBBSPageAdmin == true || #isHighestAdmin == true}">
	        		<s:form action="pageAdmin" namespace="/" method="post"  theme="simple">
	        			<div>主页歌曲修改，请把音频外链复制到下面的输入框内。</div>
	        			<s:textarea name="hSong" rows="5" cols="80"></s:textarea>
	        			<div>
	        				<s:submit value="确 认"></s:submit>
	        			</div>
	        		</s:form>
	        	</s:if>
	        </div>
		</div>
		<div class="bottom">
		
		</div>
	</div>
  </body>
  <%-- <s:debug></s:debug> --%>
  <script type="text/javascript">
  	$(document).ready(function(){
  		if('<s:property value = "isUserManageSuccess" />' == 'true'){
  			$("#findUserIdInput").value = '<s:property value = "user_.id"/>';
  			//$("#findUser").click();
  		}
  	})
  </script>
</html>
