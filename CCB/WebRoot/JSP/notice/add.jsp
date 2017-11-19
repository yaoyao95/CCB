<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
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
	<!-- <link rel="stylesheet" type="text/css" href="CSS/navigation.css"> -->
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	
	<script type="text/javascript" src="JS/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="JS/tinymce/configuration/my_tinymce.js"></script>
    <script type="text/javascript">
	    //绑定返回事件
	    var backFn = function(){
	        location.href = "notices/select_byPage.action";
	    };
	    //绑定提交验证事件
	    
	    
	    var addNoticeFn = function(){
	        var title = document.getElementById("title").value
	        var content = tinymce.activeEditor.getContent();
	        //return true; //for test
	        if(title == null || title == ""){
	            alert("标题不能为空");
	            title.focus();
	            return false;
	        }else if(content == null || content ==""){
	            alert("内容不能为空");
	            content.focus();
	            return false;
	        }else{
	            return true; 
	        }
	    }
	</script>
  </head>
  
  <body>
      <div class="main">
	      <div class="top" align="center">
				<s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
	      </div>
	      <center class="middle">
		      <center>
		          <s:fielderror style="color:red"></s:fielderror>
		          <s:actionerror style="color:red"/>
		      </center>
	      	  <s:form action="/notices/add.action" method="post" theme="simple"  onsubmit="return addNoticeFn()">
			  <!-- 防表单重复提交的隐藏表单 <input type="hidden"/> -->
	          	  <s:token></s:token>
		          <div>
			          <table align="center" width="1000px">
			              <tr align="center">
			                  <td>公告题目 </td>
			              </tr>
			              <tr align="center">
			                  <td> <s:textfield name="notice.title" size="80" id="title"/></td>
			              </tr>
			              <tr align="center">
			                  <td>内容</td>
			              </tr>
			              <tr align="center">
			                  <td><s:textarea name="notice.content" rows="20" id="content" cols="100"></s:textarea></td>
			              </tr>
			              <tr align="center">
			                  <td colspan="2">
			                      <input type="submit" value="提交" />&nbsp;&nbsp;
			                      <input type="reset" value="重置"/>&nbsp;&nbsp;
			                      <input type="button" value="返回" onclick="backFn();"/>&nbsp;&nbsp;
			                  </td>
			              </tr>
			          </table>
		          </div>
	        </s:form>
      </center>
      <dir class="bottom">
      </dir>
      </div>              
  </body>
</html>
