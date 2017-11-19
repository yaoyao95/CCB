<%@page import="org.apache.struts2.components.Include"%>
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
	<script type="text/javascript" src="JS/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="JS/tinymce/configuration/my_tinymce.js"></script>
    <%-- <script type="text/javascript" src="JS/jquery-3.2.1.js"></script> --%>
	
 	
 	
  </head>
    <body>
	<div class="main">
		<div class="top" align="center">
			<%-- <s:action name="showNavigation" namespace="/" executeResult="true"></s:action> --%>
			<jsp:include page="../navigation.jsp" flush="true"></jsp:include>
		</div> 
		<center>
			<jsp:include page="../locationAndNavigation.jsp" flush="true"></jsp:include>
	        <s:fielderror style="color:red"></s:fielderror>
	        <s:actionerror style="color:red"/>
	        <div style="color: red;">
					<s:property value="errorMessage" escapeHtml="false"/>
			</div>
			<s:if test="errorMessage == 请不要重复提交或通过浏览器后退功能回到本页面  && postTheme.id != 0 && postReply.id != 0 && saveReplyFlag == true" >
				<div>
					点击<s:a href="post/getPTByIdByPage?postTheme.id=%{postTheme.id}&saveReplyFlag=false&postReply.id=%{postReply.id}">这里</s:a>跳转
				</div>
			</s:if>
		</center>
		<center class="middle">
			<center>
				<s:fielderror style="color:red"></s:fielderror>
				<s:actionerror style="color:red" />
				<div style="color: red;"><s:property value="errorMessage" escapeHtml="false"/></div>
			</center>
			<s:form namespace="/post" action="addThemeReply"  method="post"
				theme="simple" ><!-- onsubmit="return addNoticeFn()" -->
				
				<!-- 防表单重复提交的隐藏表单 <input type="hidden"/> -->
				<s:token></s:token>
				
				<s:hidden name="postTheme.id"></s:hidden>
				
				<!-- 这些都是 input 标签，value 的值是  字符串类型的 ，
				           所以无法赋值给转换不了的类型，比如赋值给user_对象是不能的。
				          只能赋值给user_里的id，name，字符串可以转换的类型 -->
				
				<s:hidden name="author.id" value="%{#session.loginUserInfo.getId()}"></s:hidden>
				<s:hidden name="pageModel.pageSize"></s:hidden>
				<%-- <s:hidden name="saveReplyFlag" value="true"></s:hidden> --%>
				<table align="center" width="1000px">
					<tr>
						<td>
							<center>内容</center>
						</td>
					</tr>
					<tr>
						<td align="center">
							<s:textarea name="postReply.content" rows="20" id="content"  cols="100"></s:textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
							<%--提醒楼主 --%>
							<input type="hidden" name="themeAuthor" value="<s:property value="postTheme.id"/>"/>
							不提醒楼主<input type="checkbox" name="themeAuthor" value="0"/>
							<%--需要@提醒的用户 --%>
							<span>&nbsp;@提醒用户查看</span><s:textfield  size="80" maxlength="300" name="theRemindedUserNames" id="remind" title="特别提醒指定的用户查看，如果有多个用户，请用英文“，”分开，每个贴最多提醒10个用户(不包括楼主)"></s:textfield>
						</td>
					</tr>
					<tr align="center">
						<td>
						    <input type="submit" value="提交" />&nbsp;&nbsp; 
						    <input type="reset" value="重置" />&nbsp;&nbsp; 
						    <input type="button" value="返回" onclick="backFn(${postTheme.id});" />&nbsp;&nbsp;
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
				<span><font color="red" id="uploadMessage"></font></span>
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
		/*  // 第一种：
		 $("#fileForm").on("submit", function(e) {
           e.preventDefault(); // 取消事件默认行为
           $(this).ajaxSubmit({
               url : "teaching/uploadPostFile.action",
               type : "post",
               dataType : "text",
               async : true,
               success : function(imgUrl){
               	$("<img/>").attr("src", imgUrl).width(200).height(200).appendTo("#imgs");
               },
               error : function(){
               	alert("数据加载失败！");
               }
           });
       }); */
       // 第二种:
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
	
    </script>
    
    <script type="text/javascript">
	    //绑定返回事件
	    var backFn = function(id){
	        location.href = "post/getPTByIdByPage.action?postTheme.id="+id;
	    };
	    //绑定提交验证事件
	    
	    
	    var addNoticeFn = function(){
	        /*
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
	        */
	        return true;
	    }
	    
	</script>
  <script type="text/javascript">
  function heredoc(fn) {
	    return fn.toString().split('\n').slice(1,-1).join('\n') + '\n'
	}   
  
  window.onload = function initContent(){
    	  var s = heredoc(function(){/*
    		  <s:iterator value="postReplys">
    		  <br/>
    		  <div id="quoteDetail_${id}" class="closeQuoteDetail" style="display: none;">
    		  		<s:property value = "content"  escapeHtml="false"/>
    		  </div>
			  <fieldset class ="quoteField">
			   <legend>
			   引用<s:a  id="reply_%{id}" class="quote" href="post/getPTByIdByPage?pageModel.pageIndex=%{@java.lang.Math@ceil(1.0*storey/pageModel.pageSize)}&pageModel.pageSize=%{pageModel.pageSize}&postTheme.id=%{postTheme.id}&order=true&flag=true#%{id}"><s:property value="storey"/>楼</s:a>
			   </legend>
			   		<div style="max-height:500px;max-width:90%;overflow: auto;">
			   			<s:property value = "content"  escapeHtml="false"/>
			   		</div>
			  </fieldset>
			  <br/>
			  <br/>
			  </s:iterator>
    		  */});
    	  tinyMCE.execCommand("mceInsertContent", true,s);
    	  var content = heredoc(function(){/*
    		  <s:property value="originalPostContent"  escapeHtml="false" />
    		  */ });
    	  if(content != ''){
    		  tinyMCE.execCommand("mceInsertContent", true,content);
    	  }
      }
  </script>
  <%-- <s:debug></s:debug> --%>   
</html>
