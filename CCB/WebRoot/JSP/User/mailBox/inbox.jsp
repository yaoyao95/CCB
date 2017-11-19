<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
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
				if(e.type=='checkbox' && e.name!='ifsave') 
					e.checked = ifcheck;
			}
			ifcheck = ifcheck == true ? false : true;
		}
		function checkset(){
			if(confirm("将删除收件箱和发件箱内所有消息，请确认！")){
				window.location=('xxxxxx');
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
							<s:form action="operationForCheckedMails" namespace="/user" method="post">
								<div style="width:100%;border:1px solid #9999ff;">
									<table class="thread1" width="100%" cellspacing="0" cellpadding="0" align="center">
										<tbody>
											<tr><!-- 表头 -->
												<td width="5.8%">ID</td>
												<td width="34.8%">标题</td>
												<td width="11.6%">发件人</td>
												<td width="11.6%">收件人</td>
												<td width="23.2%">时间</td>
												<td width="5.8%">已读</td>
												<td width="5.8%">选定</td>
											</tr>
											<s:iterator value="receivedMails" status="re"><!-- 表体 循环递推 -->
											<tr>
												<td><s:property value="#re.getCount"/></td>
												<td><a href="user/getMailById?mail.id=${id}">
														<s:property value="title"/>
													</a>
												</td>
												<td>
													<s:a href="user/getUser?user_.id=%{sended_user.id}">
												    	<s:property value = "sended_user.name" />
												    </s:a>
												</td>
												<td>
													<s:a href="user/getUser?user_.id=%{received_user.id}">
												    	<s:property value = "received_user.name" />
												    </s:a>
												</td>
												<td><s:date format="yyyy-MM-dd HH:mm" name="send_date"/></td>
												<td>
													<s:if test="read == true">是</s:if>
													<s:else><font color="red">否</font></s:else>
												</td>
												<td><%-- <s:checkbox></s:checkbox> --%>
												<input type="checkbox" name="checkedMailIds" value="${id}"></td>	
											</tr>
											</s:iterator>
											
											<tr><!-- 表尾 -->
												<td colspan="4" style="height:60px;"></td>
												<td colspan="3" style="height:60px;">
													<input type="button" name="chkall" value="全选" 
														onclick="CheckAll(this.form)">
													<input type="submit" value="提交">
													<!-- <input name="towhere" type="hidden" value="sendbox"> -->
													<input name="operation" type="radio" value="down">下载
													<input name="operation" type="radio" value="del" checked>删除
													<input name="operation" type="radio" value="unread">设为未读
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</s:form>
							<br><br>
						</td>
					</tr>
				</tbody>
			</table>
			<my_p:pager pageIndex="${pageModel.pageIndex}"
				pageSize="${pageModel.pageSize}"
				recordCount="${pageModel.recordCount}"
				submitUrl="notices/select_byPage.action?pageModel.pageIndex={0}&notice.title=${notice.title}&notice.content=${notice.content}" />
			<%-- <s:debug></s:debug> --%>
			<%-- 加上s:debug会报错，不用管 --%>
		</div>
		<div class="bottom"></div>
	</div>
</body>
</html>
