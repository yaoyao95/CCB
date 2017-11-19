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
	<meta http-equiv="description" content="位置和导航">
	

  </head>
  
  <body>
   <div align="left" style="margin-left: 50px; max-height: 200px; padding: 5px;" >
		<span style="font-size: large;font-weight: bold; color: blue; margin-top:6px">
			<s:if test="ThatIsGoods != null">
				<a href="user/toZan">收到的赞</a>
			</s:if>
			<s:if test="remindings != null">
				<a href="user/toReminding">@提醒</a>
			</s:if>
			<s:if test="section.name == '粤语歌' || section.name == '学粤语' ||section.name == '粤语区饮食' ||section.name == '粤语区杂文化' ||section.region_name_abbr == 'jx'">
				<a href="post/select_byPage.action?section.region_name_abbr=jx">教学大区</a>
			</s:if>
			<s:if test="section.name == '开放日记' ||section.name == '自由讨论' ||section.name == '私人日记' ||section.region_name_abbr == 'sq'">
				<a href="post/select_byPage.action?section.region_name_abbr=sq">总水区</a>
			</s:if>
			<s:if test="section.name == '个人足迹' ||section.name == '社团足迹' ||section.region_name_abbr == 'zj'">
				<a href="post/select_byPage.action?section.region_name_abbr=zj">足迹区</a>
			</s:if>
			<s:if test="section.name != null && section.name !='' ">
				--><a href="post/select_byPage.action?section.name=${section.name }"><s:property value="section.name"/>区</a>
			</s:if>
		</span>
		
	</div>
  </body>
</html>
