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
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="JS/jquery-3.2.1.js"></script>
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
		//var obj = tinymce.activeEditor.getContent();
		//document.getElementById("imgs").innerHTML='<img src=' + url + 'onload=' + 'resizeImage(this)' +'/>';
		//tinyMCE.execCommand("mceInsertContent", true,'<img src=' + url + '/>'); 
		
	}
    </script>

  </head>
  
  <body>
   <jsp:include page="../navigation.jsp" flush="true"></jsp:include>
   <form id="fileForm" action="post/uploadPostFile.action" method="post" enctype="multipart/form-data">
					<%-- <s:hidden name="teachingPostTheme.id"></s:hidden> --%>
					<%-- <input type="hidden"  name="postTheme.id" value="${postTheme.id }"/> --%>
					<input type="file" name="file"/>
					<input type="submit" value="上传"/>
	</form>
	
	<span><font color="red" id="uploadMessage"></font></span>
				<input type="button" id="insertToContent" 
					value="点击把标记插入到光标处" onclick="insertToContent()">
  </body>
</html>
