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
	   		margin-top: -2px;
	   		margin-left: 13px;
        	padding: 0;
			list-style: none outside none;
		}
		.mail_ul_2 li:hover {
			background-color:#aed7ff;color:#fff;
		}
		
		.read_ul_1{
			list-style: none outside none;
			display: none;
		}
		.read_ul_1 li:hover {
			background-color:#aed7ff;color:#fff;
		}
		.read_ul_2{
			position: absolute;
	    	width: 100px;
	    	box-shadow: 2px 2px 2px grey;
	   	 	border: 1px #EEEEFF solid;
	    	background-color: #FFFFFE;
	   		margin-top: -2px;
	   		margin-left: 13px;
        	padding: 0;
			list-style: none outside none;
		}
		.read_ul_2 li:hover {
			background-color:#aed7ff;color:#fff;
		}
	</style>
	
	<script type="text/javascript">
		function showMail_ul(ul_id){
			var mail_ul = document.getElementById("mail_ul_"+ul_id);
			mail_ul.className = "mail_ul_2";
		}
	
		function closeMail_ul(ul_id){
			var mail_ul = document.getElementById("mail_ul_"+ul_id);
			mail_ul.className = "mail_ul_1";
		}
		
		function showRead_ul(ul_id){
			var read_ul = document.getElementById("read_ul_"+ul_id);
			read_ul.className = "read_ul_2";
		}
	
		function closeRead_ul(ul_id){
			var read_ul = document.getElementById("read_ul_"+ul_id);
			read_ul.className = "read_ul_1";
		}
	</script>
	
	
	<script type="text/javascript">
	    //跳转到notices/toAddNotice.action
	    var toAddNotice = function(){
	        location.href = "notices/toAddNotice.action";
	    };
	    //确认删除
	    var deleteFn = function(id){
	     if (confirm("您确定要删除吗？")){
	         location.href = "notices/delNotice.action?notice.id=" + id;
	     }
	    };
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
		<div class="top"  align="center">
		    <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<div class="middle" style="width: 100%" align="center">
			<table align="center" width="90%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<!-- 左侧导航，暂时不用
						<td width="155" valign="top">
							<ul>
								<li>1</li>
								<li>2</li>
								<li>3</li>
							</ul>
							<div class="line"></div>
						</td>  -->
						
						<td width="100%" valign="top">
							<%@include file="mailBoxNavigation.jsp"%>
							<s:form action="operationForCheckedMails2" namespace="/user" method="post">
								<s:set var="ms" value="%{sendedMails2}" scope="session"></s:set>
								<div style="width:100%;border:1px solid #9999ff;">
									<table class="thread1" width="100%" cellspacing="0" cellpadding="0" align="center">
										<tbody>
											<tr><!-- 表头 -->
												<td width="5.8%">ID</td>
												<td width="44.8%">标题</td>
												<td width="11.6%">发件人</td>
												<td width="11.6%">收件人</td>
												<td width="13.2%">时间</td>
												<td width="5.8%">对方已读</td>
												<td width="5.8%">选定</td>
											</tr>
											<s:set var="ul_id" scope="action" value="1"></s:set>
											<s:iterator value="sendedMails2" var="mail2" status="re"><!-- 表体 循环递推 -->
												<tr>
													<td><s:property value="#re.getCount"/></td>
													<td>
														<%-- <s:if test="%{title != #request.tempTitle}"> --%>
															<s:a href="user/getMail2ById?mail2.id=%{id}">
																<s:property value="title"/>
															</s:a>
														<%-- </s:if> --%>
														<s:set  value="title" var="tempTitle" scope="request"></s:set>
													</td>
													<td>
														<s:if test="send_users.size > 1">
															自发件组，发件人是
															<s:a href="user/getUser?user_.id=%{send_users.get(0).original_send_user.id}">
																<s:property value="send_users.get(0).original_send_user.name" />
															</s:a>
														</s:if>
														<s:elseif test="send_users.size==1">
															<s:a href="user/getUser?user_.id=%{send_users.get(0).original_send_user.id}">
														    	<s:property value = "send_users.get(0).original_send_user.name" />
														    </s:a>
													    </s:elseif>
													</td>
													<td><%--收件人 --%>
														<s:if test="receive_users.size > 1">
															<span onmouseover="showMail_ul(${ul_id })" onmouseout="closeMail_ul(${ul_id })">
																<s:a href = "user/getUser?user_.id=%{receive_users.get(0).one_receive_user.id}" >
																	<s:property value="receive_users.get(0).one_receive_user.name"/> 等
																	<span style="font-size:14px;">▼</span>
																</s:a>
															</span>
															<ul class = "mail_ul_1" id = "mail_ul_${ul_id }"
																 onmouseover="showMail_ul(${ul_id })" onmouseout="closeMail_ul(${ul_id })">
														    	<s:iterator value="receive_users">
														    		<li >
														    			<s:a href="user/getUser?user_.id=%{one_receive_user.id}">
														    				<s:property value = "one_receive_user.name" />
														    			</s:a>
														    		</li>
																</s:iterator>
													    	</ul>
														</s:if>
														<s:elseif test="receive_users.size == 1">
															<s:a href = "user/getUser?user_.id=%{receive_users.get(0).one_receive_user.id}" >
																<s:property value="receive_users.get(0).one_receive_user.name"/> 
															</s:a>
														</s:elseif>
													    
													</td>
													<td><s:date format="yyyy-MM-dd HH:mm" name="send_date"/></td>
													<td>
														<s:set var="allReadflag" scope="action" value="true"></s:set>
														<s:iterator value="receive_users">
									    					<s:if test="isRead == 0">
									    						<s:set var="allReadflag" scope="action" value="false"></s:set>
									    					</s:if>
														</s:iterator>
														<s:if test="#allReadflag == true">
															是
														</s:if>
														<s:else>
															<s:if test="receive_users.size == 1">
																<font color="red">否</font>
															</s:if>
															<s:if test="receive_users.size > 1">
																<span onmouseover="showRead_ul(${ul_id })" onmouseout="closeRead_ul(${ul_id })">
																	<font color="red">否</font>
																	<span style="font-size:14px;color:blue;">▼</span>
																</span>
															<ul class = "read_ul_1" id = "read_ul_${ul_id }"
																 onmouseover="showRead_ul(${ul_id })" onmouseout="closeRead_ul(${ul_id })">
														    	<s:iterator value="receive_users">
														    		<li >
														    			<s:a href="user/getUser?user_.id=%{one_receive_user.id}">
														    				<s:property value = "one_receive_user.name" />&nbsp;&nbsp;
														    				<s:if test="isRead == 0">
														    					<span style="color:red">未读</span>
														    				</s:if>
														    				<s:else>
														    					已读
														    				</s:else>
														    			</s:a>
														    		</li>
																</s:iterator>
													    	</ul>
															</s:if>
														</s:else>
													</td>
													<td><%-- <s:checkbox></s:checkbox> --%>
														<input type="checkbox" name="checkedMailIds" value="${id}">
													</td>	
												</tr>
												<s:set var="ul_id" scope="action" value="%{#ul_id+1}"></s:set>
											</s:iterator>
												<tr><!-- 表尾 -->
													<td colspan="4" style="height:60px;"></td>
													<td colspan="3" style="height:60px;">
														<input type="button" name="chkall" value="全选" 
															onclick="CheckAll(this.form)">
														<input type="submit" value="提交">
														<input name="operation" type="radio" value="down">下载
														<input name="operation" type="radio" value="del" checked>删除
													</td>
												</tr>
											
										</tbody>
									</table>
								</div>
							</s:form>
							<br><br>
							<s:form action="toSendMailBox" namespace="/user" method="post">
								一页显示<s:textfield type="text" name="pageModel.pageSize" size="2"/>封邮件&nbsp;&nbsp;
								<input type="submit" value="确定">
							</s:form>
						</td>
					</tr>
				</tbody>
			</table>
			<my_p:pager pageIndex="${pageModel.pageIndex}"
				pageSize="${pageModel.pageSize}"
				recordCount="${pageModel.recordCount}"
				submitUrl="user/toSendMailBox?pageModel.pageIndex={0}&pageModel.pageSize=${pageModel.pageSize}" />
			<%-- <s:debug></s:debug> --%>
			<%-- 加上s:debug会报错，不用管 --%>
		</div>
		<div class="bottom"></div>
	</div>
</body>
</html>
