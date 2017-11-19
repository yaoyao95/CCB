<%@ page language="java" import="java.util.*,com.CantoneseClubBBS.domain.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
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
	<meta http-equiv="description" content="粤语大讲堂">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	
	
	<script type="text/javascript">
	    //确认删除
	    var deleteFn = function(id){
	     if (confirm("您确定要删除吗？")){
	         location.href = "teaching/delNotice.action?notice.id=" + id;
	     }
	    };
	    
	    var checkSelect = function(region_name_abbr){
	    	var selectObj;
	    	if(document.getElementById("jxs")!=null){
	    		selectObj = document.getElementById("jxs");
	    	}
	    	if(document.getElementById("sqs")!=null){
	    		selectObj = document.getElementById("sqs")
	    	}
	    	if(document.getElementById("zjs")!=null){
	    		selectObj = document.getElementById("zjs")
	    	}
	    	var theIndex = selectObj.selectedIndex; // 选中索引
    	
	    	if(selectObj.options[theIndex].value == "请选择"){
	    		selectObj.options[theIndex].value = "";
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
			<%--显示本页面是什么区 并提供导航 --%>
			<jsp:include page="../locationAndNavigation.jsp" flush="true"></jsp:include>
			
			<div style="border:1px solid #DDDDFF;width: 89%;padding: 3px;" align="left">
				<s:property value="section.description"/>
			</div>
			
			<center style="border:1px solid #DDDDFF; width: 90%">
				<div style="border:1px solid #DDDDFF;padding-top:8px; " >
					<s:form action="select_byPage" namespace="/post" method="post"
						theme="simple" onsubmit="return checkSelect('%{section.region_name_abbr}')">
						<s:if test="section.name == '粤语歌' || section.name == '学粤语' ||section.name == '粤语区饮食' ||section.name == '粤语区杂文化' || section.region_name_abbr == 'jx'">
							<s:hidden name="section.region_name_abbr" value="jx"></s:hidden>
						</s:if>
						<s:if test="section.name == '开放日记' ||section.name == '自由讨论' ||section.name == '私人日记' || section.region_name_abbr == 'sq'">
							<s:hidden name="section.region_name_abbr" value="sq"></s:hidden>
						</s:if>
						<s:if test="section.name == '个人足迹' ||section.name == '社团足迹' ||section.region_name_abbr == 'zj'">
							<s:hidden name="section.region_name_abbr" value="zj"></s:hidden>
						</s:if>
						<s:hidden name="ptc.isGoodTheme"></s:hidden>
						标题：<s:textfield name="ptc.title" size="12" />&nbsp;&nbsp;
						内容：<s:textfield name="ptc.content" size="12"/>&nbsp;&nbsp;
						每页显示：<s:textfield name="pageModel.pageSize" size="2"/>条
						排序：<s:radio list="pttf" name="order" ></s:radio>
						<s:if test="section.name == '粤语歌' || section.name == '学粤语' ||section.name == '粤语区饮食' ||section.name == '粤语区杂文化' || section.region_name_abbr == 'jx'">
							板块：<s:select id="jxs" name="section.name" list="%{#application.tsns}"></s:select>
						</s:if>
						<s:elseif test="section.name == '开放日记' ||section.name == '自由讨论' ||section.name == '私人日记' || section.region_name_abbr == 'sq'">
							板块：<s:select id="sqs" name="section.name" list="%{#application.wsns}"></s:select>
						</s:elseif>
						<s:elseif test="section.name == '个人足迹' ||section.name == '社团足迹' ||section.region_name_abbr == 'zj'">
							板块：<s:select id="zjs" name="section.name" list="%{#application.zjns}"></s:select>
						</s:elseif>
						<s:else>
							#	
						</s:else>
						<s:submit type="submit" value="搜 索"/>&nbsp;&nbsp;
					</s:form>
				</div>
				
				<div>
					<a href = "post/select_byPage?section.name=${section.name}&section.region_name_abbr=${section.region_name_abbr}&ptc.isGoodTheme=1"
								 title="优秀主题帖" style="color: #3399FF;">优秀主题帖
					</a>&nbsp;&nbsp;
					<a href = "post/select_byPage?section.name=${section.name}&section.region_name_abbr=${section.region_name_abbr}&ptc.totalThatIsGoodNum=-1"
								 title="按被赞数排列" style="color: #3399FF;">被赞数最高的贴子
					</a>(
					<a href = "post/select_byPage?section.name=${section.name}&section.region_name_abbr=${section.region_name_abbr}&ptc.totalThatIsGoodNum=-4"
								 title="一天内按被赞数排列" style="color: #3399FF;">一天内
					</a>
					<a href = "post/select_byPage?section.name=${section.name}&section.region_name_abbr=${section.region_name_abbr}&ptc.totalThatIsGoodNum=-3"
								 title="一周内按被赞数排列" style="color: #3399FF;">一周内
					</a>
					<a href = "post/select_byPage?section.name=${section.name}&section.region_name_abbr=${section.region_name_abbr}&ptc.totalThatIsGoodNum=-2"
								 title="一个月内按被赞数排列" style="color: #3399FF;">一个月内
					</a>)
					
				</div>
				
				<!-- 发布教学主题 -需要登录-->
				<s:form action="toAddTheme" namespace="/post" method="post" theme="simple">
				    <%-- <s:if test="#session.loginUserInfo != null">
						 <s:hidden name="author.id" value="%{#session.loginUserInfo.getId()}"/>
					</s:if> --%>
					 <%--判断添加的主题属于什么板块 --%>
						 <%-- 小板块 --%>
				    <s:if test="section.name != null && section.name != ''">
						<s:hidden name="section.name"></s:hidden>
					</s:if>
					<%--大版块 --%>
				    <s:elseif test="section.region_name_abbr != null && section.region_name_abbr != ''">
				 	 	<s:hidden name="section.region_name_abbr"></s:hidden> 
					</s:elseif>
					
				    <input type="submit" value="发表主题" />
				</s:form>
			</center>
            
			<div  style="border:1px solid #DDDDFF;border-right:1px solid #DDDDFF; height: auto; width: 90%; ">
				
				<table class="thread1" cellspacing="0" cellpadding="0" width="100%">
					<tr>
					    <td width="5%">标记</td>
						<td width="60%">标题</td>
						<td width="5%">赞数</td>
						<td width="5%">回复数<br/>浏览数</td>
						<td width="13%">发布者<br/>最后回复者</td>
						<td width="12%">发布时间<br/>最后回复时间</td>
					</tr>
					<%--置顶帖 --%>
					<s:iterator value="topPostThemes">
						<tr>
						    <td style="color: red;">
								<s:if test="isToTop == 1">
									[置顶]
								</s:if>
								<s:elseif test="isGoodTheme == 1">
									[优秀]
								</s:elseif>
								<s:elseif test="isNew == 1">
									[new]
								</s:elseif>
							</td>
						    <!-- 标题  点击到theme_detail -->
							<td style="text-align: left;">
								<a href= "post/getPTByIdByPage.action?postTheme.id=${id}">
									<s:property value="title"  escapeHtml="false"/>
								</a>
							</td>
							<td><s:property value="totalThatIsGoodNum"/> </td>
							<td>
							    <s:property value="reply_number"/><br/>
							    <s:property value="browse_number"/>
							</td> 
							<td>
							    <s:property value="user_.name"/><br/>
							    <s:property value="the_last_reply_user_name"/>
							</td>
							<td>
								<s:if test="isNew == 1">
									<div style="text-align: center;width: auto;color: red;">
										<s:date name="issuedDate" format="yy-MM-dd HH:mm" />
									<!--<s:property value="%{@com.CantoneseClubBBS.util.Display@DateForDisply(issuedDate)}"/>-->
									</div>
								</s:if>
							    <s:else>
							    	<div style="text-align: center;width: auto;">
										<s:date name="issuedDate" format="yy-MM-dd HH:mm" />
									</div>
							    </s:else>
							    <s:if test="isReplyNew == 1">
							    	<div  style="text-align: center;width: auto;color: red;">
							    		<s:date name="the_last_reply_time" format="yy-MM-dd HH:mm" />
							    	</div>
							    </s:if>
							    <s:else>
							    	<div style="text-align: center;width: auto;">
										<s:date name="the_last_reply_time" format="yy-MM-dd HH:mm" />
									</div>
							    </s:else>
						    </td>
						</tr>
					</s:iterator>
					<tr>
								<td colspan="6" style="height: 18px;"></td>
					</tr>
					<s:iterator value="postThemes" status="count">
						<tr>
						    <td style="color: red;">
								<s:if test="isToTop == 1">
									[置顶]
								</s:if>
								<s:elseif test="isGoodTheme == 1">
									[优秀]
								</s:elseif>
								<s:elseif test="isNew == 1">
									[new]
								</s:elseif>
							</td>
						    <!-- 标题  点击到theme_detail -->
							<td style="text-align: left;">
								<a href= "post/getPTByIdByPage.action?postTheme.id=${id}">
									<s:property value="title"  escapeHtml="false"/>
								</a>
							</td>
							<td><s:property value="totalThatIsGoodNum"/> </td>
							<td>
							    <s:property value="reply_number"/><br/>
							    <s:property value="browse_number"/>
							</td> 
							<td>
							    <s:property value="user_.name"/><br/>
							    <s:property value="the_last_reply_user_name"/>
							</td>
							<td>
								<s:if test="isNew == 1">
									<div style="text-align: center;width: auto;color: red;">
										<s:date name="issuedDate" format="yy-MM-dd HH:mm" />
									<!--<s:property value="%{@com.CantoneseClubBBS.util.Display@DateForDisply(issuedDate)}"/>-->
									</div>
								</s:if>
							    <s:else>
							    	<div style="text-align: center;width: auto;">
										<s:date name="issuedDate" format="yy-MM-dd HH:mm" />
									</div>
							    </s:else>
							    <s:if test="isReplyNew == 1">
							    	<div  style="text-align: center;width: auto;color: red;">
							    		<s:date name="the_last_reply_time" format="yy-MM-dd HH:mm" />
							    	</div>
							    </s:if>
							    <s:else>
							    	<div style="text-align: center;width: auto;">
										<s:date name="the_last_reply_time" format="yy-MM-dd HH:mm" />
									</div>
							    </s:else>
						    </td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<%-- <% 
				String sectionNameAbbr = "";
				Section s  =  (Section) request.getAttribute("section");
			   	if(s.getName() != null && s.getName() != ""){
			   		
			   	}
			%> --%>
			<%-- <s:if test="section.name != null &&section.name != ''">
				<my_p:pager pageIndex="${pageModel.pageIndex}"
				pageSize="${pageModel.pageSize}"
				recordCount="${pageModel.recordCount}"
				submitUrl="post/select_byPage.action?pageModel.pageIndex={0}&section.name=${section.name }&pageModel.pageSize=${pageModel.pageSize}&ptc.title=${ptc.title}&ptc.content=${ptc.content}&ptc.isGoodTheme=1" />
			</s:if> --%>
			
				<my_p:pager pageIndex="${pageModel.pageIndex}"
					pageSize="${pageModel.pageSize}"
					recordCount="${pageModel.recordCount}"
					submitUrl="post/select_byPage.action?pageModel.pageIndex={0}&section.name=${section.name }&section.region_name_abbr=${section.region_name_abbr }&pageModel.pageSize=${pageModel.pageSize}&ptc.title=${ptc.title}&ptc.content=${ptc.content}" />
			
			
			
		</center>
		<div class="bottom"></div>
	</div>
	<%-- 加上s:debug会报错，不用管 --%>
	<%-- <s:debug></s:debug> --%>
</body>
</html>
