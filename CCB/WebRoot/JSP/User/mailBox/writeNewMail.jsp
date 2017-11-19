<%@ page language="java" import="java.util.*,com.CantoneseClubBBS.domain.user.*,com.CantoneseClubBBS.domain.user.mail.*" pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" type="text/css" href="CSS/mailBoxLayout.css">
	
	<script type="text/javascript" src="JS/tinymce/tinymce.min.js"></script>
	<script type="text/javascript" src="JS/jquery-3.2.1.js"></script>
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
    	
    	 //绑定提交验证事件
	  function  addMailFn(){
	        
	    	var title = document.getElementById("title").value;
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
	        }else{
	        return true;
	        }
	    }
    	 
	  tinymce.init({
		    selector : '#content',
		    language: 'zh_CN',
		    theme: 'modern',
          	width: 800,
          	height: 300,
		    plugins: [
                    'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                    'searchreplace wordcount visualblocks visualchars code fullscreen',
                    'insertdatetime media nonbreaking save table contextmenu directionality',
                    'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc'
                   ],
          
          toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
          toolbar2: 'print preview media | forecolor backcolor emoticons | codesample'
	     });
    </script>

	
	
	<script type="text/javascript">
		cnt = 0;
		function checkCnt(){
			document.FORM.Submit.disabled=true;
			cnt++;
			if (cnt!=1){
				alert('Submission Processing. Please Wait');
				return false;
			}
			addMailFn();
		}

		function quickpost(event){
			if((event.ctrlKey && event.keyCode == 13) || (event.altKey && event.keyCode == 83)){
				document.FORM.Submit.click();
			}
		}
	</script>	
  </head>
  
  <body>
	<div class="main">
		<div class="top"  align="center">
		    <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<div class="middle" style="width: 100%" align="center">
			<center style="color: red;">
				<s:actionerror/>
			</center>
			<table align="center" width="90%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr align="center">
						<td width="100%" valign="top">
							<%@include file="mailBoxNavigation.jsp"%>
							 <s:form action="addMail2" namespace="/user" method="post" onsubmit="return checkCnt();">
								<div style="width:100%;border:1px solid #9999ff;">
									<table class="thread1" width="100%" cellspacing="0"
										cellpadding="0" align="center">
										<tbody>
											<tr>
												<td colspan="2">短消息</td>
											</tr>
											<tr>
												<td width="20%"><b>用户名</b>&nbsp; <span>(若多用户，用英文,分开)</span></td>
												<td width="85%" style="text-align:left;">
													<input class="input" type="text"
														name="names" maxlength="100" size="80">
												</td>
											</tr>
											<tr>
												<td><b>标题</b></td>
												<td style="text-align:left;">
													<input class="input" type="text" name="mail2.title"
														maxlength="100" size="80" value="">
													</td>
											</tr>
											<tr style="text-align: center;">
												<td><b>内容</b></td>
												<td>
													<s:textarea  name="mail2.content" id="content"></s:textarea>
													<!-- <div style="margin:4px;">
														<input type="checkbox" name="ifsave" value="Y">保存到发件箱中
													</div> -->
												</td>
											</tr>
											<tr>
												<td colspan="2"><input type="submit"
													value="提 交">
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</s:form>
							
						</td>
					</tr>
				</tbody>
			</table>
			<%-- <s:debug></s:debug> --%>
			<%-- 加上s:debug会报错，不用管 --%>
		</div>
		<div class="bottom"></div>
	</div>
</body>
</html>
