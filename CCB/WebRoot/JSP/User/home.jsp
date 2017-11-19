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
	.u_left{
	width: 15%;
	}
	</style>
  </head>
  
  <body>
    <div class="main">
        <div class="top" align="center">
            <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
        </div>
        <div class="middle" align="center">
			<center style="color: red;">
				${requestScope.tips }
				<s:fielderror style="color:red"></s:fielderror>
			</center>
			<div style="color: blue; font-size: 20px;width: 90%;text-align: left;">
				<s:a href="user/getUser?user_.id=%{#session.loginUserInfo.id}">用户主页</s:a><span style="color:black;"> --></span>
				<a href="user/toHome">个人信息修改</a>
			</div>
			<div  class="userInfo"  style="border:1px solid #9999ff;border-right:1px solid #DDDDFF;
                	border-left:1px solid #9999ff; border-top:1px solid #9999ff; height: auto; width: 90%; text-align: center;" >
	            <s:form action="updateUserPic" namespace="/user" method="post" enctype="multipart/form-data" style="width:100%;">
					<s:hidden name="user_.id" value="%{#session.loginUserInfo.getId()}"/>
					<table  class="thread2" cellspacing="0" cellpadding="0" width="100%" align="center">
						<tr>
							<td class="u_left l_pic">头像</td>
							<td width="35%；">
							     <img src = "<s:property value="%{#session.loginUserInfo.getPicture_path()}"/>" height="200"
							     width="140"/>
							    <br>
							    <input type="file" name="file"/>
								<input type="submit" value="上传"/>
							    
							</td>
							<td style="color: #999;">
								只能上传200kb之内的，<br/>格式为jpg、jpeg、png格式的图片
							</td>
						<tr>
					</table>
				</s:form>
            </div>
             <div class="userInfo"  style="border:1px solid #9999ff;border-right:1px solid #DDDDFF;
                	border-left:1px solid #9999ff; border-top:1px solid #9999ff; height: auto; width: 90%; text-align: center; ">
               	<s:form action="update" namespace="/user" method="post" style="width:100%;">
                    <table class="thread2" cellspacing="0" cellpadding="0" width="100%" align="center">
                       <tr>
                           <td class="u_left">原密码</td>
                           <td class="u_right">
                               <s:textfield name="oldpwd" type="password" size="50"></s:textfield>修改密码时用于验证
                           </td>
                       </tr>
                       <tr>
                           <td class="u_left">新密码</td>
                           <td class="u_right">
                               <s:textfield name="newpwd" type="password" size="50"></s:textfield>
                           </td>
                       </tr>
                       <tr>
                           <td class="u_left">确认密码</td>
                           <td class="u_right">
                               <s:textfield name="fnpwd" type="password" size="50"></s:textfield>
                           </td>
                       </tr>
                       <tr>
                           <td class="u_left">昵称</td>
                           <td class="u_right">
                               <s:textfield name="user_.nickname" size="50"></s:textfield>
                           </td>
                       </tr>
                       <tr>
                           <td class="u_left">个性签名</td>
                           <td class="u_right">
                               <s:textfield name="user_.signature_personality" size="50"></s:textfield>
                           </td>
                       </tr> 
                       <tr>
                           <td class="u_left">性别</td>
                           <td class="u_right">
                               <input type="radio" name="user_.sex" value="0"/>男
                               <input type="radio" name="user_.sex" value="1"/>女
                               <input type="radio" name="user_.sex" value="2"/>保密
                           </td>
                       </tr>   
                    </table>
                    <s:submit value="确 认"></s:submit>
                </s:form>
            </div>
        </div>
        <div class="bottom">
        </div>
    </div>
    <s:debug></s:debug>
  </body>
</html>
