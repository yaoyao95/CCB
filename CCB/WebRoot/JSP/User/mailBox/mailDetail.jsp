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
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
    <link rel="stylesheet" type="text/css" href="CSS/mailBoxLayout.css">
	
	<script language="JavaScript">
	cnt = 0;
	function checkCnt(){
		document.FORM.Submit.disabled=true;
		cnt++;
		if (cnt!=1){
			alert('Submission Processing. Please Wait');
			return false;
		}
	}
	ifcheck = true;
	function CheckAll(form){
		for(var i=0;i<form.elements.length-2;i++){
			var e = form.elements[i];
			if(e.type=='checkbox' && e.name!='ifsave') e.checked = ifcheck;
		}
		ifcheck = ifcheck == true ? false : true;
	}
	function checkset(){
		if(confirm("将删除收件箱和发件箱内所有消息，请确认！")){
			window.location=('http://2dkf.com/message.php?action=clear&ckcode=3f3ebd6e');
		} else{
			return false;
		}
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
    	<div class= "top"  align="center">
    		<s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
    	</div>
   		<div class= "middle" style="width: 100%" align="center">
			<table align="center" width="90%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td width="100%" valign="top">
							<%@include file="mailBoxNavigation.jsp" %>
							<div style="width:100%;border:1px solid #9999ff;">
								<table class="thread2" width="100%" cellspacing="0" cellpadding="0" align="center">
									<tbody>
										<tr>
										    <td rowspan="5" width="15%">
										        <!-- 头像 -->
										    </td>
											<td colspan="2">查看信息</td>
											<td rowspan="5" width="15%"></td>
										</tr>
										<tr>
											<td width="11.6%">
												<font color="#5A6633">
														收件人:
												</font>
											</td>
											<td width="58.1%" style="font-size:14px;" >bch</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">标题:</font>
											</td>
											<td>求补资源 護くんに女神の祝福を！第01-12巻+番外編</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">时间:</font>
											</td>
											<td>2015-06-29 12:44</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">内容:</font>
											</td>
											<td>  谢谢啦</td>
										</tr>
										<tr>
										    <td>
										        <font> 发件人:bch</font>
										    </td>
											<td>
											    <font color="#5A6633">选项:</font>
											</td>
											<td>
												[<a href="message.php?action=write&amp;touid=96458">另发</a>]
												[<a href="message.php?action=del&amp;towhere=sendbox&amp;delids=947388">删除</a>]			
											</td>
											<td>
										        <font> 收件人:aoi</font>
										    </td>
									    </tr>
									</tbody>
								</table>
							</div>
							<br><br>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class= "bottom"></div>
	</div>
  </body>
</html>
