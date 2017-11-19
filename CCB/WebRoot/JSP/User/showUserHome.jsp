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
	<meta http-equiv="description" content="用户中心">
	
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/navigation.css">
	<style type="text/css">
		.userHome_ul{
			list-style-type:none;
			margin:0;
			padding:0;
		}
		.userHome_ul li{
		    float: right;
    		margin-right: 3px;
    		list-style-type: none;
			}
		.userHome_ul li a{
		    display: block;
		    font-size: 16px;
		    line-height: 30px;
		    font-weight: bold;
		    border: 1px solid #DDDDFF;
		    padding: 4px;
		}
	</style>
  </head>
  
  <body>
    <div class="main">
        <div class="top" align="center">
            <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
        </div>
        <center style="color: blue; font-size: 20px;">
        	<s:a href="user/getUser?user_.id=%{#session.loginUserInfo.id}">用户主页</s:a>
        </center>
        <center class="middle">
				<div style="border-left: 1px solid #9999ff;border-top: 1px solid #9999ff;width: 95%">
					<table  class="thread1" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								头像
							</td>
							<td>
								<div>
					        		<ul class="userHome_ul" style="margin-right: 5px;">
										<li>
											<a href = "user/toHome" style="color: #3399FF;">修改个人信息
											</a>
										</li>
										<li>
											<s:a title="管理员管理用户" href="user/toAdmin?user_.id=%{user_.id}" >
												管理
											</s:a>
										</li>
									</ul>
   								</div>
							     <img src = "<s:property value="user_.picture_path"/>" height="200"
							     width="140"/>
							</td>
						<tr>
						<tr align="center">
							<td class="u_right r_pic">
								用户名：
							</td>
							<td>
								<s:property value="user_.name"/>
							</td>
						</tr>
						<tr align="center">
							<td class="u_right r_pic">
								昵称
							</td>
							<td>
								<s:property value="user_.nickname"/>
							</td>
						</tr>
						<tr align="center">
							<td>
								个性签名
							</td>
							<td>
								<s:property value="user_.signature_personality"/>
							</td>
						</tr>
						<tr align="center">
							<td>
								性别
							</td>
							<td>
								<s:if test="user_.sex == 1">男</s:if>
								<s:elseif test="user_.sex == 2">女</s:elseif>
								<s:elseif test="user_.sex == 3">保密</s:elseif>
							</td>
						</tr>
						<tr align="center">
							<td>权限</td>
							<td>
								<s:property value="userAuthority" escapeHtml="false"/>
							</td>
						</tr>
					</table>
				</div>
        </center>
        <div class="bottom">
        </div>
    </div>
    <%-- <s:property value="%{#session.loginUserInfo.getPicture_path()}"/>
    <s:debug></s:debug> --%>
  </body>
</html>
