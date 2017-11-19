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
				<s:if test="errorMessage != null" >
					<s:if test="errorMessage == 请不要重复提交或通过浏览器后退功能回到本页面  && postTheme.id != 0" >
						<div>点击
							<s:a href="post/getPTByIdByPage?postTheme.id=%{postTheme.id}&saveReplyFlag=false">这里</s:a>跳转
						</div>
					</s:if>
				</s:if>
			</center>
			<center style="width: 95%">
				<s:form namespace="/post" action="updateTheme"  method="post"
					theme="simple">
					<!-- 防表单重复提交的隐藏表单 <input type="hidden"/> -->
					<s:token></s:token>
					<input type="hidden"  name="postTheme.id" value="${postTheme.id }"/>
					<s:hidden name="isAdmin"></s:hidden>
					<s:hidden name="isTheAuthor"></s:hidden>
					<s:if test="isAdmin == true">
						<div style="text-align: left;width: 20%">
							帖子状态：<br/>
							所属区：<s:property value="section.name"/><br/>
							锁定：
							<s:if test="isRock == true">
								是
							 </s:if>
							 <s:else>
							 	否
							 </s:else><br/>
							优秀主题帖：
							<s:if test="isGoodTheme == true">
								是
							</s:if>
							<s:else>
								否
							</s:else><br/>
							置顶：
							<s:if test="isToTop == true">
								是
							</s:if>
							<s:else>
								否
							</s:else><br/>
						</div><br/><br/>
						
						<div  style="text-align: left;width: 20%;background-color: #bbffff;">
							管理员可操作一览：<br/>
							移动到<s:select name="section.name"  list="%{#application.sns}"></s:select>区<br/>
							
							锁定<s:checkbox name="isRock" title="锁定后本帖子只能查看"  fieldValue="true"></s:checkbox>
							<span class="tips" title="锁定后本帖子只能查看">[?]</span><br/>
							
							删除<s:checkbox name="isDelete" title="删除本帖和其回帖"  fieldValue="true"></s:checkbox>
							<span class="tips" title="删除本帖和其回帖">[?]</span><br/>
							
							评为优秀主题帖<s:checkbox name="isGoodTheme" title="标志本主题帖为优秀主题帖"  fieldValue="true"></s:checkbox>
							<span class="tips" title="标志本主题帖为优秀主题帖">[?]</span><br/>
							
							设为置顶<s:checkbox name="isToTop" title="置顶主题帖，后置顶的排第一" fieldValue="true"></s:checkbox>
							<span class="tips" title="置顶主题帖，后置顶的排第一">[?]</span><br/>
							
							<input type="submit" value="确 认" />&nbsp;&nbsp;
							<input type="reset" value="重 置" />&nbsp;&nbsp; 
							<input type="button" value="返 回" onclick="backFn('${postTheme.id}');" />&nbsp;&nbsp;
						</div>
						    			
					</s:if>
					<table align="center" width="1000px">
						<tr>
						    <td width="10%" style="font-size: 13px">
						    	<div>
						    		所属区：<s:property value="section.name"/>
						    	</div>
						    </td>
							<td>
								<div class="title">标题</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<s:if test="isTheAuthor == true">
									<center><s:textfield name="postTheme.title" id="title" size="80" ></s:textfield></center>
								</s:if>
								<s:else>
									<center><s:textfield disabled="true" name="postTheme.title" id="title" size="80" ></s:textfield></center>
									<s:hidden name="postTheme.title"></s:hidden>
								</s:else>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<center>内容</center>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<s:if test="isTheAuthor == true">
									<s:textarea name="postTheme.content" rows="20" id="content" cols="100"></s:textarea>
								</s:if>
								<s:else>
									<fieldset>
										<s:property value="postTheme.content" escapeHtml="false"/>
									</fieldset>
									<s:hidden name="postTheme.content"></s:hidden>
								</s:else>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<%--需要@提醒的用户 --%>
								<span>&nbsp;已@提醒的用户&nbsp;</span>
								<s:property value="theRemindedUserNamesForUpdateJSP"/>
								<s:if test="isTheAuthor == true">
									<div>添加@提醒用户查看：<s:textfield  size="80" maxlength="300" value="" name="theRemindedUserNames" id="remind"  title="特别提醒指定的用户查看，如果有多个用户，请用英文“，”分开，每帖最多提醒10个用户"></s:textfield></div>
								</s:if>
								<s:else>
									<div>添加@提醒用户查看：<s:textfield  size="80" disabled="true" maxlength="300"  value="" name="theRemindedUserNames" id="remind"  title="特别提醒指定的用户查看，如果有多个用户，请用英文“，”分开，每帖最多提醒10个用户"></s:textfield></div>
								</s:else>
							</td>
						</tr>
						<tr align="center">
							<td colspan="2">
							    <input type="submit" value="提 交" />&nbsp;&nbsp; 
							    <input type="reset" value="重 置" />&nbsp;&nbsp; 
							    <input type="button" value="返 回" onclick="backFn('${postTheme.id}');" />&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</s:form>
			</center>
			<!-- 上传图片 -->
			<s:if test="isTheAuthor == true">
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
			</s:if>
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
	    var backFn = function(postThemeId){
	    		location.href = "post/getPTByIdByPage.action?postTheme.id="+postThemeId;
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
	    		  //tinyMCE.execCommand("mceInsertContent", true,content);
	    	  }
	      }
	</script>
<%-- <s:debug></s:debug>  --%>
</html>
