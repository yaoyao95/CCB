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
	<meta http-equiv="description" content="回复教学主题">
	
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/navigation.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	<style type="text/css">
	    .title{
	    margin-left: 380px;}
	</style>
	<script type="text/javascript" src="JS/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="JS/tinymce/configuration/my_tinymce.js"></script>
    
    
  </head>
    <body>
	<div class="main">
		<div class="top" align="center">
			<s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<center class="middle">
			<center>
				<%--显示本页面是什么区 并提供导航 --%>
				<jsp:include page="../locationAndNavigation.jsp" flush="true"></jsp:include>
				
				<s:fielderror style="color:red"></s:fielderror>
				<s:actionerror style="color:red" />
				<div style="color: red;">
					<s:property value="errorMessage" escapeHtml="false"/>
				</div>
				<s:if test="errorMessage == 请不要重复提交或通过浏览器后退功能回到本页面  && postTheme.id != 0" >
					<div>点击
						<s:a href="post/getPTByIdByPage?postTheme.id=%{postTheme.id}&saveReplyFlag=false">这里</s:a>跳转
					</div>
				</s:if>
			</center>
			<s:form namespace="/post" action="addTheme"  method="post"
				theme="simple" onsubmit="return addThemeFn()">
				<!-- 防表单重复提交的隐藏表单 <input type="hidden"/> -->
				<s:token></s:token>
				
				<%-- <s:hidden name="postTheme.id"></s:hidden> --%>
				
				<%-- 这些都是 input 标签，value 的值是  字符串类型的 ，
				           所以无法赋值给转换不了的类型，比如赋值给user_对象是不能的。
				          只能赋值给user_里的id，name，字符串可以转换的类型
				<s:hidden name="author.id" value="%{#session.loginUserInfo.getId()}"></s:hidden> --%>
				
				<table align="center" width="1000px">
					<tr>
					    <td width="10%" style="font-size: 13px">
					    	<s:if test="section.region_name_abbr=='jx'">
					    		<s:select name = "section.name" id="section" list="%{#application.tsns}"></s:select>
					    	</s:if>
					    	<s:if test="section.region_name_abbr=='sq'">
					    		<s:select name = "section.name" id="section" list="%{#application.wsns}"></s:select>
					    	</s:if>
					    	<s:if test="section.region_name_abbr=='zj'">
					    		<s:select name = "section.name" id="section" list="%{#application.zjns}"></s:select>
					    	</s:if>
					    	<s:if test="section.name != null">
					    		<s:hidden name="section.name"></s:hidden>
					    		<s:property value="section.name"/>区
					    	</s:if>
					    </td>
						<td>
							<div class="title">标题</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<center><s:textfield name="postTheme.title" id="title" size="80" ></s:textfield></center>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<center>内容</center>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<s:textarea name="postTheme.content" rows="20" id="content" cols="100"></s:textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<%--需要@提醒的用户 --%>
							<span>&nbsp;@提醒用户查看</span><s:textfield  size="80" maxlength="300" name="theRemindedUserNames" id="remind" title="特别提醒指定的用户查看，如果有多个用户，请用英文“，”分开，每帖最多提醒10个用户"></s:textfield>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2">
						    <input type="submit" value="提 交" />&nbsp;&nbsp; 
						    <input type="reset" value="重 置" />&nbsp;&nbsp; 
						    <input type="button" value="返 回" onclick="backFn('${section.name}','${section.region_name_abbr }');" />&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</s:form>
			<!-- 上传图片 -->
			<div>
			    <span>上传图片：</span>&nbsp; <span style="color:#999;">只能上传1MB之内的，格式为jpg、jpeg、png格式的图片</span>
			    <form id="fileForm" action="post/uploadPostFile.action" method="post" enctype="multipart/form-data">
					<%-- <s:hidden name="teachingPostTheme.id"></s:hidden> --%>
					<input type="hidden"  name="postTheme.id" value="${postTheme.id }"/>
					<input type="file" name="file"/>
					<input type="submit" value="上传"/>
				</form>
				<div id="imgs"></div>
				<!-- <input type="button" id="uploadBtn" value="上传"> -->
				<span><font color="red" id="uploadMessage">
					<s:fielderror></s:fielderror>
				</font></span>
				<input type="button" id="insertToContent" 
					value="点击把标记插入到光标处" onclick="insertToContent()">
			</div>
		</center>
		<div class="bottom"></div>
	</div>
  </body>
  <script type="text/javascript" src="JS/jquery-form.js"></script>
    <script type="text/javascript">
    $(function(){
		
       $("#fileForm").ajaxForm({
               url : "post/uploadPostFile.action",
               type : "post",
               dataType : "text",
               async : true,
               success : function(imgUrl){
               	//$("<img/>").attr("src", imgUrl).width(200).height(200).appendTo("#imgs");
               	//$("<hr/>").appendTo("#imgs");
               	document.getElementById("uploadMessage").innerHTML=imgUrl;
               	//alert(imgUrl);
               },
               error : function(){
            	document.getElementById("uploadMessage").innerHTML="11111";
               	alert("数据加载失败！");
               }
         });
       
	});
    
    
	function insertToContent(){
		var url = document.getElementById("uploadMessage").textContent;
		var obj = tinymce.activeEditor.getContent();
		//document.getElementById("imgs").innerHTML='<img src=' + url + 'onload=' + 'resizeImage(this)' +'/>';
		tinyMCE.execCommand("mceInsertContent", true,'<img src=' + url + '/>'); 
		
	}
    
    
    
    
    	//绑定返回事件
	    var backFn = function(name,region_name_abbr){
	    	if(name != null && name != ""){
	    		location.href = "post/select_byPage?section.name="+name;
	    	}
	        if(region_name_abbr != "" && region_name_abbr != null){
	        	location.href = "post/select_byPage?section.region_name_abbr="+region_name_abbr;
	        }
	    };
	    
	    //绑定提交验证事件
	    var addThemeFn = function(){
	        
	    	var title = document.getElementById("title").value;
	    	var section = document.getElementById("section");
	        var content = tinymce.activeEditor;
	        //return true; //for test
	        if(title == null || title == ""){
	            alert("标题不能为空");
	            title.focus();
	            return false;
	        }else if(content.getContent() == null || content.getContent() ==""){
	            alert("内容不能为空");
	            content.focus();
	            return false;
	        }else if(section.value == null || section.value == "请选择"){
	            alert("请选择主题类型");
	            section.focus();
	        	return false; 
	        }else{
	        return true;
	        }
	    }
	    
	    
	    function heredoc(fn) {
		    return fn.toString().split('\n').slice(1,-1).join('\n') + '\n'
		}   
	  
	  window.onload = function initContent(){
	    	  var content = heredoc(function(){/*
	    		  <s:property value="originalPostContent"  escapeHtml="false" />
	    		  */ });
	    	  if(content != ''){
	    		  tinyMCE.execCommand("mceInsertContent", true,content);
	    	  }
	      }
	</script>
<%-- <s:debug></s:debug>  --%>
</html>
