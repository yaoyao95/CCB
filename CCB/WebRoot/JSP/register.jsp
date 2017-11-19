<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" type="text/css" href="CSS/register.css">
    <link rel="stylesheet" type="text/css" href="CSS/layout.css">
    <style type="text/css">
    	body{background-color:#fcfcfc;}
    	td {height:40px;border-bottom:1px solid #9999ff;border-right:1px solid #9999ff;text-align:left;}
    </style>
  </head>
  
  <body>
     <div class="main">
         <center class="top" align="center">
         	<jsp:include page="./navigation.jsp" flush="true"></jsp:include>
         </center>
         <center>
	          <s:fielderror style="color:red"></s:fielderror>
	          <s:actionerror style="color:red"/>
        </center>
         <div class="form">
             <s:form action="addUser" namespace="/user" method="post" name="form1" theme="simple">
                 <span class="jibenxinxi">基本信息</span><br>
                 <div style="border-top:1px solid #9999ff;border-left:1px solid #9999ff; height: auto;">
	                 <table class="t1" cellspacing="0" cellpadding="0" width=600px;>
	                     <tr>
	                         <td class="zuo"><span><font color="red">*</font></span>用户名</td>
	                         <td>
	                             <span>
	                             <s:textfield type="text" name="user_.name" class="input_default" id="input" value="最多12位半角符号" onClick="clear_input(this)" onBlur="default_input(this)" />
	                             </span>
	                             <span id="buhefa" ></span>
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="zuo">昵称</td>
	                         <td>
	                             <span>
	                             <s:textfield type="text" name="user_.nickname" class="input_default" id="ni_input" value="最多20位半角符号" onClick="ni_clear_input(this)" onBlur="ni_default_input(this)" />
	                             </span>
	                             <span id="nichengbu" ></span>
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="zuo"><span><font color="red">*</font></span>设置密码</td>
	                         <td>
	                             <span id="mamikuang">
	                             <s:textfield type="text" name="user_.password" class="input_default" id="mima_input" 
	                                value="只能包含数字字母和下划线" onClick="mima_clear_input(this)" onBlur="mima_default_input(this)" />
	                             </span>
	                             <span id="mimabu" ></span>
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="zuo"><span><font color="red">*</font></span>确认密码</td>
	                         <td>
	                             <span id="querenmimakuang">
	                             <s:textfield type="text" name="u_psd_queren" class="input_default" id="mima_zaici_input" 
	                                value="请再次输入密码" onClick="mimazaici_clear_input(this)" onBlur="mimazaici_default_input(this)" />
	                             </span>
	                             <span id="mimabuyizhibu" ></span>
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="zuo"><span><font color="red">*</font></span>密保问题1</td>
	                         <td>
	                             <s:textfield type="text" name="user_.password_issue1" id="psd" class="input"/> 
	                             <span style="color:#666">用于找回密码</span>
	                         </td>
	                     </tr>
	                     <tr>
	                          <td class="zuo"><span><font color="red">*</font></span>密保答案1</td>
	                          <td>
	                             <s:textfield type="text" name="user_.passWord_answer1" id="psda" class="input"/> 
	                         </td>
	                     </tr>
	                     <tr>
	                         <td class="zuo">密保问题2</td>
	                         <td>
	                             <s:textfield type="text" name="user_.password_issue2" id="psd2" class="input"/> 
	                             <span style="color:#666">用于找回密码</span>
	                         </td>
	                     </tr>
	                     <tr>
	                          <td class="zuo">密保答案2</td>
	                          <td>
	                             <s:textfield type="text" name="user_.passWord_answer2" id="psda2" class="input"/> 
	                         </td>
	                     </tr>
	                 </table>
                 </div>
                 <input type="submit" value="提 交" class="submit" onClick="return check_register(this.form)"> <%-- --%>
                 <input type="button" value="清 空" class="clear" onClick="_clear()" >
             </s:form>
         </div>
     </div>
    
  </body>
  <script type="text/javascript" src="JS/register.js"  charset="UTF-8" ></script>
</html>