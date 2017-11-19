<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path9 = request.getContextPath();
String basePath9 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path9+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath9%>">
    
    <title>西大粤语社</title>
    <!-- 改web图标 -->
    <link rel="shortcut icon" href="http://localhost:8080/CCB/img/nwuccc.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="CSS/navigation.css">
	<script type="text/javascript" src="JS/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="JS/jquery-migrate-1.2.1.js"></script>
	
	<style type="text/css">
		.font{
			font-size: 14px;
			color: blue;
		}
	</style>
	
	<script type="text/javascript">
	var refreshtime = 1000;
	
	$(document).ready(function focusbody(){
	    refreshtime = 1200000;
	    setTimeout("simulateclick()",0);
	    var h = window.parent.document.getElementById("H");
		if(h != null){
			document.getElementById("navH").style.display="none";
		}
	} );
	function simulateclick(){
		if(refreshtime>0){
			document.getElementById("checkNewMessage").click();
	        setTimeout("simulateclick()",refreshtime); //循环计数
	    }
	}
	$(function(){
		
		$("#checkNewMessage").click(function(){
			// 发送get请求
			$.getJSON("user/checkNewMessage.action", function(data, status){
				// status : success|error
				//alert(status + "==" + data);
				if (status == "success"){
					$("#newMessage").html(data.newMessage);
					$("#newMail").html(data.newMail);
					$("#newReminding").html(data.newReminding);
					$("#newZan").html(data.newZan);
				}
			}, "text");
		});
	});
	
	/* window.onload = function showH(){
		
	  } */
	</script>
	
	<script type="text/javascript">
		var showUserMenu = function(){
			document.getElementById("showUserMenu").className = 'u2_1';
			
		}
		var closeUserMenu = function(){
			document.getElementById("showUserMenu").className = 'u2_0';
		}
		var showWaterMenu = function(){
			document.getElementById("showWaterMenu").className = 'u2_1';
			
		}
		var closeWaterMenu = function(){
			document.getElementById("showWaterMenu").className = 'u2_0';
		}
		var showTeachingMenu = function(){
			document.getElementById("showTeachingMenu").className = 'u2_1';
			
		}
		var closeTeachingMenu = function(){
			document.getElementById("showTeachingMenu").className = 'u2_0';
		}
		var showFootprintMenu = function(){
			document.getElementById("showFootprintMenu").className = 'u2_1';
			
		}
		var closeFootprintMenu = function(){
			document.getElementById("showFootprintMenu").className = 'u2_0';
		}
		
		var showOtherMenu = function(){
			document.getElementById("showOtherMenu").className = 'u2_1';
			
		}
		var closeOtherMenu = function(){
			document.getElementById("showOtherMenu").className = 'u2_0';
		}
	</script> 
  </head>
  
  <body>
  	<div id="navH" style="width: 3%; top:1%; left:1%; position: absolute;" >
		<a href="#" title="点击返回外主页，从外主页进来可也使用左侧边栏的功能" style="cursor:pointer;">
			<img src="img/home.jpg" width="70px;" height="70px;"/>
		</a>
	</div>
	<div class="main1">
		<div class="navigation">
			<div class="left">
				<div class="navigationo">
					<ul class="u1">
					    <li class="l1">
						    <center id="otherMenu" onmouseover="showOtherMenu()" onmouseout="closeOtherMenu()">
								<a href="toIndex">西大粤语社</a>
							</center>
							<ul class="u2_0" id="showOtherMenu"  onmouseover="showOtherMenu()" onmouseout="closeOtherMenu()">
				                <li>
				                    <center>
						                <span class="font"><a href="user/toAdmin">管理</a></span>
					                </center>
				                </li>
							</ul>
						</li>
						<li class="l1">
						    <center style="z-index: 10000;">
								<a href="notices/select_byPage.action">公告区</a>
							</center>
						</li>
						<li class="l1" id="teachingMenu" >
						    <center onmouseover="showTeachingMenu()" onmouseout="closeTeachingMenu()">
						    		<%--post/select_byPage.action?section.region_name_abbr=jx --%>
								    <a href="javascript:void(0);" style="text-decoration: none;">教学区 </a>
							</center>
						    <ul class="u2_0" id="showTeachingMenu"  onmouseover="showTeachingMenu()" onmouseout="closeTeachingMenu()">
				                 <li>
				                    <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=粤语歌">粤语歌</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=学粤语">学粤语</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=粤语区饮食">粤语区饮食</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=粤语区杂文化">粤语区杂文化</a></span>
					                </center>
				                </li>
							</ul>
						</li>
						<li class="l1">
						    <center>
								<a href="#">任务区</a>
							</center>
						</li>
						<li class="l1" id="footprintMenu" >
						    <center onmouseover="showFootprintMenu()" onmouseout="closeFootprintMenu()">
					    		<!-- post/select_byPage.action?section.region_name_abbr=zj -->
							    <a href="javascript:void(0);" style="text-decoration: none;">足迹区 </a>
							</center>
						    <ul class="u2_0" id="showFootprintMenu"  onmouseover="showFootprintMenu()" onmouseout="closeFootprintMenu()">
				                 <li>
				                    <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=社团足迹">社团足迹</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=个人足迹">个人足迹</a></span>
					                </center>
				                </li>
							</ul>
						</li>
						<li class="l1" id="waterMenu" >
						    <center  onmouseover="showWaterMenu()" onmouseout="closeWaterMenu()">
						    	<!-- post/select_byPage.action?section.region_name_abbr=sq -->
							    <a href="javascript:void(0);" style="text-decoration: none;">总水区 </a>
							</center>
						    <ul class="u2_0" id="showWaterMenu"  onmouseover="showWaterMenu()" onmouseout="closeWaterMenu()">
				                 <li>
				                    <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=开放日记">开放日记</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=私人日记">私人日记</a></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="post/select_byPage.action?section.name=自由讨论">自由讨论</a></span>
					                </center>
				                </li>
							</ul>
						</li>
						<li class="l1" id="userMenu" >
							<s:if test="loginUserId != null">
								<s:if test="loginUserId != 0">
									<center onmouseover="showUserMenu()" onmouseout="closeUserMenu()">
										<input type="button" value="newMessage" id = "checkNewMessage" style="display:none;" ><!-- style="display:none;" -->
					                	<span id="loginUser" style="color:red;font-size:14;">
					                		<s:property value="loginUserName"/>
					                	</span>
					                	<span id="newMessage" style="color:#ff00ff;font-size:65%;margin-left:3px;">
					                	</span>
								    </center>
								</s:if>
								<s:elseif test="#session.loginUserInfo != null">
									<center onmouseover="showUserMenu()" onmouseout="closeUserMenu()">
										<input type="button" value="newMessage" id = "checkNewMessage" style="display:none;" ><!-- style="display:none;" -->
					                	<span id="loginUser" style="color:red;font-size:14;">
					                		<s:property value="#session.loginUserInfo.getName()"/>
					                	</span>
					                	<span id="newMessage" style="color:#ff00ff;font-size:65%;margin-left:3px;">
					                	</span>
								    </center>
								</s:elseif>
								<s:else>
								    <center>
										    <a href="tologin.action">登录|注册 </a>
									</center>
								</s:else>
							</s:if>
							<s:elseif test="#session.loginUserInfo != null">
								<center onmouseover="showUserMenu()" onmouseout="closeUserMenu()">
									<input type="button" value="newMessage" id = "checkNewMessage" style="display:none;" ><!-- style="display:none;" -->
				                	<span id="loginUser" style="color:red;font-size:14;">
				                		<s:property value="#session.loginUserInfo.getName()"/>
				                	</span>
				                	<span id="newMessage" style="color:#ff00ff;font-size:50%;margin-left:3px;">
				                	</span>
							    </center>
							</s:elseif>
						    <s:else>
							    <center>
									    <a href="tologin.action">登录|注册 </a>
								</center>
							</s:else>
						    <ul class="u2_0" id="showUserMenu"  onmouseover="showUserMenu()" onmouseout="closeUserMenu()">
				                 <li>
				                    <center>
						                <span class="font">
						                	<s:if test="loginUserId != null">
						                		<s:if test="loginUserId != 0">
						                			<s:a href="user/getUser?user_.id=%{loginUserId}">个人中心</s:a>
						                		</s:if>
						                		<s:elseif test="#session.loginUserInfo != null">
						                			<s:a href="user/getUser?user_.id=%{#session.loginUserInfo.getId()}">个人中心</s:a>
						                		</s:elseif>
						                	</s:if>
						                	<s:elseif test="#session.loginUserInfo != null">
						                		<s:a href="user/getUser?user_.id=%{#session.loginUserInfo.getId()}">个人中心</s:a>
						                	</s:elseif>
						                </span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span id="mail" class="font"><a href="user/toMailBox2">邮箱</a></span>
						                <span id="newMail" style="font-size:65%;color:#ff00ff;"></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span id="reminding" class="font"><a href="user/toReminding">@提醒</a></span>
						                <span id="newReminding" style="font-size:65%;color:#ff00ff;"></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span id="NavigationZan" class="font"><a href="user/toZan">赞</a></span>
						                <span id="newZan" style="font-size:65%;color:#ff00ff;"></span>
					                </center>
				                </li>
				                <li>
						            <center>
						                <span class="font"><a href="logout.action">安全退出</a></span>
					                </center>
				                </li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="line"></div>
		<div style="height:50px;">&nbsp;</div>
	</div>
	<!-- <div style="position: absolute;top: 7%;left: 4%;">
		<iframe src="/CCB/JSP/bgm.jsp">
		</iframe>
	</div> -->
	<%--横批图片 --%>
	<div style="width: 100%;" align="center">
		<div style="width: 90.25%;" align="center">
		<img src="img/1.jpg" style="width: 30%;">
		<img src="img/2.jpg" style="width: 28%;">
		<img src="img/3.jpg" style="width: 40%;">
		</div>
	</div>
</body>
</html>
