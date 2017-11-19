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
	<style type="text/css">
		.mail_ul_1{
			list-style: none outside none;
			display: none;
		}
		.mail_ul_1 li:hover {
			background-color:#aed7ff;color:#fff;
		}
		.mail_ul_2{
			position: absolute;
	    	width: 100px;
	    	box-shadow: 2px 2px 2px grey;
	   	 	border: 1px #EEEEFF solid;
	    	background-color: #FFFFFE;
	   		margin-top: -5px;
	   		margin-left: 5px;
        	padding: 0;
			list-style: none outside none;
		}
		.mail_ul_2 li:hover {
			background-color:#aed7ff;color:#fff;
		}
		.pic{
	    width: 140px;
	    height: 200px;}
	    .userName{
	      font-size:medium;
	      font-weight: bold;
	      color: red; }
	      
	      
	     .receivedUserNumberExceptLoginUser{
	     height: 60px;
	     width: 42px;
	     }
	     
	     .receivedUserNumberImg{
	     height:90px;
	     width:63px;}
	</style>
	
	
	<script type="text/javascript">
		var showMail_ul = function(){
			var mail_ul = document.getElementById("mail_ul");
			mail_ul.className = "mail_ul_2";
		}
	
		var closeMail_ul = function(){
			var mail_ul = document.getElementById("mail_ul");
			mail_ul.className = "mail_ul_1";
		}
	</script>
	
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
										    <td rowspan="7" width="15%" style="text-align: center;">
										        <%--判断该邮件是否是 该登录用户 的接受邮件 --%>
												<s:set var="isLoginUserReceivedMail" value="false"></s:set>
												<s:iterator value="mail2.receive_users">
													<s:if test="%{#session.loginUserInfo.getId() == one_receive_user.id }">
														<s:set var="isLoginUserReceivedMail" value="true"></s:set>
													</s:if>
												</s:iterator>
										        <s:if test="%{#isLoginUserReceivedMail == true}">
										        	
										        	
										        	
											        	<!-- 收件人头像 -->
											        <%--头像--%>
											        <span style="color:blue;">收件人</span><br/>
											        <%--收件人 --%>
													<s:if test="%{mail2.receive_users.size > 1}">
														<%--判断该邮件是否是 该登录用户 的接受邮件 --%>
														<s:set var="isLoginUserReceivedMail" value="false"></s:set>
														<s:iterator value="mail2.receive_users">
															<s:if test="%{#session.loginUserInfo.getId() == one_receive_user.id }">
																<s:set var="isLoginUserReceivedMail" value="true"></s:set>
																<span>
																	<img class="pic" src='<s:property value="%{#session.loginUserInfo.getPicture_path()}"/>' /> <br/> 
								       								<font class="userName"><s:property value="%{#session.loginUserInfo.getName()}"/></font> <br/>
																</span>
															</s:if>
														</s:iterator>
														<s:if test="%{#isLoginUserReceivedMail == true}">
															<%--除了该登录用户，收件人的个数 --%>
															<s:set var="receivedUserNumberExceptLoginUser" value="%{mail2.receive_users.size - 1}"></s:set>
															<s:if test="%{mail2.receive_users.size > 10}">
																<s:set var="end" value="10"></s:set>
															</s:if>
															<s:else>
																<s:set var="end" value="%{mail2.receive_users.size - 1}"></s:set>
															</s:else>
															<s:iterator value="mail2.receive_users" status="re" begin="0" step="1" end="%{#end}">
																<s:if test="%{one_receive_user.id != #session.loginUserInfo.getId()}">
																	<span>
																		<img class="receivedUserNumberExceptLoginUser" 
																		id="receivedUserImg_<s:property value="#re.getCount"/>"
																		 src='<s:property value="one_receive_user.picture_path"/>'
																		 title="<s:property value="one_receive_user.name"/>" />  
																	</span>
																</s:if>
																<s:if test="%{#re.getCount == 10 & mail2.receive_users.size > 10}">
																	等
																</s:if>
															</s:iterator>
														</s:if>
														<s:if test="%{#isLoginUserReceivedMail == false}">
															<%--收件人的个数 --%>
															<s:set var="receivedUserNumber" value="%{mail2.receive_users.size}"></s:set>
															<s:if test="%{mail2.receive_users.size > 10}">
																<s:set var="end" value="10"></s:set>
															</s:if>
															<s:else>
																<s:set var="end" value="%{mail2.receive_users.size - 1}"></s:set>
															</s:else>
															<s:iterator value="mail2.receive_users" status="re" end="%{#end}">
																<span>
																	<img id="receivedUserImg_<s:property value="#re.getCount"/>"
																	 src='<s:property value="one_receive_user.picture_path"/>'
																	 title="<s:property value="one_receive_user.name"/>"
																	 class="receivedUserNumberImg" />  
																</span>
															</s:iterator>
														</s:if>
													</s:if>
													<s:if test="%{mail2.receive_users.size == 1}">
														<s:if test="%{mail2.receive_users.get(0).one_receive_user.id == #session.loginUserInfo.getId()}">
															<img class="pic" src='<s:property value="mail2.receive_users.get(0).one_receive_user.picture_path"/>'/><br/>
															<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
																<span class="userName"><s:property value="%{#session.loginUserInfo.getName()}"/></span>
															</s:a><br/>
														</s:if>
														<s:else>
															<img class="pic" src='<s:property value="mail2.receive_users.get(0).one_receive_user.picture_path"/>'/><br/>
															<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
																<span class="userName">
																	<s:property value="mail2.receive_users.get(0).one_receive_user.name"/>
																</span>
															</s:a><br/>
														</s:else>
													</s:if>
										        	
										        	
										        	
										        </s:if>
										        
										        <s:else>
										        
										        
										        	<%-- 原始发件人头像 --%>
											    	<span style="color:blue;">发件人</span><br/>
											   		<img class="pic" src='<s:property value="mail2.send_users.get(0).original_send_user.getPicture_path()"/>' /> <br/> 
							       					<font class="userName"><s:property value="%{mail2.send_users.get(0).original_send_user.getName()}"/></font>
												   		
													
										        
										        
										        </s:else>
										        
										        
										        
												
												
												
												
												
										    </td>
											<td colspan="2">查看信息</td>
											<td rowspan="7" width="15%" style="text-align: center;">
											    
											    <s:if test="%{#isLoginUserReceivedMail == true}">
											    
												    <%-- 原始发件人头像 --%>
												    <span style="color:blue;">发件人</span><br/>
												    <img class="pic" src='<s:property value="mail2.send_users.get(0).original_send_user.getPicture_path()"/>' /> <br/> 
								       				<font class="userName"><s:property value="%{mail2.send_users.get(0).original_send_user.getName()}"/></font>
											
												</s:if>
												
												<s:else>
												
													<!-- 收件人头像 -->
											        <%--头像--%>
											        <span style="color:blue;">收件人</span><br/>
											        <%--收件人 --%>
													<s:if test="%{mail2.receive_users.size > 1}">
														<%--判断该邮件是否是 该登录用户 的接受邮件 --%>
														<s:set var="isLoginUserReceivedMail" value="false"></s:set>
														<s:iterator value="mail2.receive_users">
															<s:if test="%{#session.loginUserInfo.getId() == one_receive_user.id }">
																<s:set var="isLoginUserReceivedMail" value="true"></s:set>
																<span>
																	<img class="pic" src='<s:property value="%{#session.loginUserInfo.getPicture_path()}"/>' /> <br/> 
								       								<font class="userName"><s:property value="%{#session.loginUserInfo.getName()}"/></font> <br/>
																</span>
															</s:if>
														</s:iterator>
														<s:if test="%{#isLoginUserReceivedMail == true}">
															<%--除了该登录用户，收件人的个数 --%>
															<s:set var="receivedUserNumberExceptLoginUser" value="%{mail2.receive_users.size - 1}"></s:set>
															<s:if test="%{mail2.receive_users.size > 10}">
																<s:set var="end" value="10"></s:set>
															</s:if>
															<s:else>
																<s:set var="end" value="%{mail2.receive_users.size - 1}"></s:set>
															</s:else>
															<s:iterator value="mail2.receive_users" status="re" begin="0" step="1" end="%{#end}">
																<s:if test="%{one_receive_user.id != #session.loginUserInfo.getId()}">
																	<span>
																		<img class="receivedUserNumberExceptLoginUser" 
																		id="receivedUserImg_<s:property value="#re.getCount"/>"
																		 src='<s:property value="one_receive_user.picture_path"/>'
																		 title="<s:property value="one_receive_user.name"/>" />  
																	</span>
																</s:if>
																<s:if test="%{#re.getCount == 10 & mail2.receive_users.size > 10}">
																	等
																</s:if>
															</s:iterator>
														</s:if>
														<s:if test="%{#isLoginUserReceivedMail == false}">
															<%--收件人的个数 --%>
															<s:set var="receivedUserNumber" value="%{mail2.receive_users.size}"></s:set>
															<s:if test="%{mail2.receive_users.size > 10}">
																<s:set var="end" value="10"></s:set>
															</s:if>
															<s:else>
																<s:set var="end" value="%{mail2.receive_users.size - 1}"></s:set>
															</s:else>
															<s:iterator value="mail2.receive_users" status="re" end="%{#end}">
																<span>
																	<img id="receivedUserImg_<s:property value="#re.getCount"/>"
																	 src='<s:property value="one_receive_user.picture_path"/>'
																	 title="<s:property value="one_receive_user.name"/>"
																	 class="receivedUserNumberImg" />  
																</span>
															</s:iterator>
														</s:if>
													</s:if>
													<s:if test="%{mail2.receive_users.size == 1}">
														<s:if test="%{mail2.receive_users.get(0).one_receive_user.id == #session.loginUserInfo.getId()}">
															<img class="pic" src='<s:property value="mail2.receive_users.get(0).one_receive_user.picture_path"/>'/><br/>
															<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
																<span class="userName"><s:property value="%{#session.loginUserInfo.getName()}"/></span>
															</s:a><br/>
														</s:if>
														<s:else>
															<img class="pic" src='<s:property value="mail2.receive_users.get(0).one_receive_user.picture_path"/>'/><br/>
															<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
																<span class="userName">
																	<s:property value="mail2.receive_users.get(0).one_receive_user.name"/>
																</span>
															</s:a><br/>
														</s:else>
													</s:if>
												
												</s:else>
											
											</td>
										</tr>
										<tr>
											<td width="11.6%">
												<font color="#5A6633">
														发件人:
												</font>
											</td>
											<td width="58.1%" style="font-size:14px;" >
												<s:if test="%{mail2.send_users.size>1}">
													自发件组，发件人是
													<s:a href="user/getUser?user_.id=%{mail2.send_users.get(0).original_send_user.id}">
														<s:property value="mail2.send_users.get(0).original_send_user.name" />
													</s:a>
												</s:if>
												<s:elseif test="mail2.send_users.size==1">
													<s:a href="user/getUser?user_.id=%{mail2.send_users.get(0).original_send_user.id}">
												    	<s:property value = "mail2.send_users.get(0).original_send_user.name" />
												    </s:a>
											    </s:elseif>
											</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">标题:</font>
											</td>
											<td>
												<s:property value="mail2.title"/>
											</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">时间:</font>
											</td>
											<td>
												<s:date name="mail2.send_date" format="yyyy-MM-dd HH:mm:ss"/>
											</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">内容:</font>
											</td>
											<td style="text-align: left;">
												<s:property value="mail2.content" escapeHtml="false"/>
											</td>
										</tr>
										<tr>
											<td>
												<font color="#5A6633">收件人:</font>
											</td>
											<td>
												<%--收件人 --%>
												<s:if test="mail2.receive_users.size > 1">
													<s:set var="isLoginUserMail" value="false"></s:set>
													<s:iterator value="mail2.receive_users">
														<s:if test="%{one_receive_user.id} == %{#session.loginUserInfo.getId()}">
															<s:set var="isLoginUserMail" value="true"></s:set>
															<span onmouseover="showMail_ul()" onmouseout="closeMail_ul()">
																<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
																	<s:property value="%{#session.loginUserInfo.getName()}"/> 等
																	<span style="font-size:14px;">▼</span>
																</s:a>
															</span>
														</s:if>
													</s:iterator>
													<s:if test="%{#isLoginUserMail == false}">
														<span onmouseover="showMail_ul()" onmouseout="closeMail_ul()">
															<s:a href = "user/getUser?user_.id=%{mail2.receive_users.get(0).one_receive_user.id}" >
																<s:property value="mail2.receive_users.get(0).one_receive_user.name"/> 等
																<span style="font-size:14px;">▼</span>
															</s:a>
														</span>
													</s:if>
													<ul class = "mail_ul_1" id = "mail_ul"
														 onmouseover="showMail_ul()" onmouseout="closeMail_ul()">
												    	<s:iterator value="mail2.receive_users">
												    		<li >
												    			<s:a href="user/getUser?user_.id=%{one_receive_user.id}">
												    				<s:property value = "one_receive_user.name" />
												    			</s:a>
												    		</li>
														</s:iterator>
											    	</ul>
												</s:if>
												<s:elseif test="mail2.receive_users.size == 1">
													<s:a href = "user/getUser?user_.id=%{#session.loginUserInfo.getId()}" >
														<s:property value="%{#session.loginUserInfo.getName()}"/>
													</s:a>
												</s:elseif>
											</td>
										</tr>
										<tr>
											<td>
											    <font color="#5A6633">选项:</font>
											</td>
											<td>
												[<a href="message.php?action=write&amp;touid=96458">另发</a>]
												[<a href="message.php?action=del&amp;towhere=sendbox&amp;delids=947388">删除</a>]			
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
		<%-- <s:debug></s:debug> --%>
		<div class= "bottom"></div>
	</div>
  </body>
  <script type="text/javascript">
  window.onload = function locate(){
		var imgs = document.getElementsByTagName("img");
	    for(var i = 0; i < imgs.length;i++){
	    	var img = imgs.item(i);
	    	if(img.width > 700){
	    		if(img.height > 20000){
		    		img.width = img.width * 20000 / img.height;
		    		img.height = 20000;
		    	}
	    		img.height = img.height * 700 / img.width;
	    		img.width = 700;
	    	}
	    }
  };
  </script>
</html>
