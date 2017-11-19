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
	<meta http-equiv="description" content="粤语大讲堂">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	<link rel="stylesheet" type="text/css" href="CSS/pager.css">
	<script type="text/javascript" src="JS/jquery-1.8.3.js"></script>
	<style type="text/css">
	    .pic{
	    width: 140px;
	    height: 200px;}
	    
	    .picc{
	      width: 140px;
	      height: 140px;
	    }
	    /*用户名*/
	    .userName{
	      font-size:medium;
	      font-weight: bold;
	      color: red; }
	    .guestName{
	    font-size: medium;
	    }
	    .showQuoteDetail{
	    position: absolute;
	    	width: auto;
	    	box-shadow: 2px 2px 2px grey;
	   	 	border: 1px #EEEEFF solid;
	    	background-color: #FFFFFE;
        	margin-left:15px;
        	margin-top: 15px;
        	/* height:80px; */
        	padding: 20px;
			z-index:9999;
	    }
	    .closeQuoteDetail{
		display: none;
	    }
	    .reminding{
			color: red;
			}
		.remindingField{
		margin-left: 10px;
		max-width: 500px;
		}
		.reminding_2{
			 color:red;
			}
	    .reminding_3{
			width:95%; text-align:right; color:grey;
			}
		.quoteField{
		margin-left: 10px;
		}
		
		.postTheme_ul{
			list-style-type:none;
			margin:0;
			padding:0;
		}
		.postTheme_ul li{
		    float: right;
    		margin-right: 3px;
    		list-style-type: none;
			}
		.postTheme_ul li a ,.zan{
		    display: block;
		    font-size: 16px;
		    line-height: 30px;
		    font-weight: bold;
		    border: 1px solid #DDDDFF;
		    padding: 0 10px;
		}
	</style>
	<script type="text/javascript">
	    //确认删除
	    var deleteFn = function(id){
	     if (confirm("您确定要删除吗？")){
	         location.href = "teaching/delNotice.action?notice.id=" + id;
	     }
	    };
	</script>
	  </head>
  
  <body>
	<div class="main">
		<div class="top" align="center">
		    <s:action name="showNavigation" namespace="/" executeResult="true"></s:action>
		</div>
		<center class="middle" style="width: 90%;">
			<jsp:include page="../locationAndNavigation.jsp" flush="true"></jsp:include>
			<center style="border:1px solid #9999ff; width:95%;" >
				<s:form action="getPTByIdByPage" namespace="/post" method="post"
					theme="simple">
				<s:hidden name="postTheme.id"/> 
				 回帖内容：<s:textfield name="prc.content" />&nbsp;&nbsp;
				回帖用户：<s:textfield name="prc.user_.name"/> &nbsp;&nbsp;
				每页显示：<s:textfield name="pageModel.pageSize" size="2"/>条
				排序：<s:radio list="tf" name="order" ></s:radio>
				<input type="submit" value="搜 索" />&nbsp;&nbsp;&nbsp;&nbsp;
				</s:form>
				
				
			</center>
            <center style="border:1px solid #DDDDFF;width: 95%">
				<s:form action="toAddThemeReply" namespace="/post" method="post" theme="simple">
					
					<table class="thread1" cellspacing="0" cellpadding="0" width="100%">
						<tr>
						    <td colspan="3">
						    	<span style="font-size:18px;line-height:35px;font-weight:bold;max-width:90%;">
						    		<s:property value="postTheme.title"/>
						    	</span>
						    	
						    	<span class="tips" style="float: right;" title="楼主和回帖收到的赞的总计">
						    		[?]
						    	</span>
						    	<span id="totalZan" style="color: #ff7777;font-size:18px;line-height:35px;font-weight:bold;float: right;" >
						    		全贴共收到赞 <s:property value="postTheme.totalThatIsGoodNum"/>
						    	</span>
						    </td>
						</tr>
						<tr>
							<%--  <td width="75%" height="50" colspan="2">
							</td>--%>
							<td colspan="3" style="text-align:right;">
								<ul class="postTheme_ul">
									<li>
										<a href = "post/toAddThemeReply?postTheme.id=${postTheme.id}&pageModel.pageSize=${pageModel.pageSize}&order=${order }"
											 title="回复楼主" style="color: #3399FF;">回复主题
										</a>
									</li>
									<li>
										<s:a class="tips" title="总之就是想给楼主点个赞（ps不能自己给自己赞）" href="javascript:;" onclick="thatIsGood('%{postTheme.id}',-1)" style="color: #ff7777;" id="zan">
											赞！<s:property value="postTheme.thatIsGoodNum"/> 
										</s:a>
									</li>
								</ul>
							</td>
						</tr>
						<tr>
						    <td style="text-align:left;width:220px ;">
						    <span style="font-size:15px;font-weight: bolder;">楼主</span>
						    <span style="color:#999999;"> 发表于：<s:date name="postTheme.issuedDate" format="yy-MM-dd HH:mm:ss"/></span>
						    </td>
						    <td style="text-align: left;color:#999999;cursor: help;"  title="个性签名">
						    	&nbsp; <s:property value="postTheme.user_.signature_personality" />
						    </td>
						    <td  style="width: 50px;">
						    	<s:a href="post/toUpdateTheme?postTheme.id=%{postTheme.id}">编辑</s:a>
						    </td>
						</tr>
						<tr>
						    <td style="height:220px;padding-top: 20px;"valign="top">
						        <%--头像 --%>
						        <img class="pic" src='<s:property value="postTheme.user_.getPicture_path()" />' /><br/>
						        <s:a href = "user/getUser?user_.id=%{postTheme.user_.id}" >
						        	<font class="userName"><s:property value="postTheme.user_.getName()"/></font>
						        </s:a>
						    </td>
							<td colspan="2" style="text-align:left;padding: 10px;">
							    <s:if test="postTheme.isRock == 1">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isRockDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsRockAdminName"/>锁住，只能查看，无法修改或回帖
							    	</div>
							    </s:if>
							    <s:elseif test="postTheme.isRock ==2">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isRockDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsRockAdminName"/>解锁，恢复正常功能
							    	</div>
							    </s:elseif>
							    
							    <s:if test="postTheme.isGoodTheme == 1">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isGoodThemeDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsGoodThemeAdminName"/>设置为优秀帖
							    	</div>
							    </s:if>
							    <s:elseif test="postTheme.isGoodTheme ==2">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isGoodThemeDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsGoodThemeAdminName"/>取消优秀帖资格
							    	</div>
							    </s:elseif>
							    
							    <s:if test="postTheme.isChangeSection == 1">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isChangeSectionDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsChangeSectionAdminName"/>移动到本区
							    	</div>
							    </s:if>
							    
							    <s:if test="postTheme.isDelete == 1">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isDeleteDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsDeleteAdminName"/>删除
							    	</div>
							    </s:if>
							    <s:elseif test="postTheme.isDelete ==2">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isDeleteDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsDeleteAdminName"/>取消删除
							    	</div>
							    </s:elseif>
							    
							    <s:if test="postTheme.isToTop == 1">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isToTopDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsToTopAdminName"/>置顶
							    	</div>
							    </s:if>
							    <s:elseif test="postTheme.isToTop ==2">
							    	<div style="color:red;">
							    		该贴于<s:date name="postTheme.isToTopDate" format="yy-MM-dd HH:mm:ss"/>
							    		被管理员<s:property value="postTheme.setIsToTopAdminName"/>取消置顶
							    	</div>
							    </s:elseif>
							    
							    <s:property value="postTheme.content" escapeHtml="false"/><br>
							    <s:if test="postTheme.updateDate != null">
								        <div>
								            <font color="red">修改于：<s:date name="postTheme.updateDate" format="yy-MM-dd HH:mm:ss" /></font>
								        </div>
							    </s:if>
							           关键词：<br/>
							           我近期的其他帖子（本版内）：
							</td>
						</tr>
						<tr>
								<td colspan="3" style="height: 10px;"></td>
						</tr>
						<s:iterator value="postReplys" status="count">
							<tr>
								<td  id=${id }>
									<span>
										<span style="font-size:15px;font-weight: bolder;"><s:property value="storey"/>楼&nbsp;</span>
										<span style="color:#999999;">发表于:<s:date name="issuedDate" format="yy-MM-dd HH:mm:ss"/></span>
									</span>
								</td>
								<%--个性签名 --%>
								<td style="text-align: left;color:#999999; cursor: help;" title="个性签名" >
						    		&nbsp; <s:property value="user_.signature_personality" />
						    	</td>
								<td style="width: 200px;">
									<%--post/toUpdateThemeReply?postTheme.id=%{postTheme.id}&pageModel.pageSize=%{pageModel.pageSize}&order=%{order }&postReply.id=%{id} --%>
						    		<s:a href="javascript:return false;" title="暂时不支持编辑回帖">编辑</s:a>
						    		&nbsp;<s:a id="replyZanId%{id}"   class="zan"  href="javascript:;" onclick="thatIsGood('%{postTheme.id}',%{id})" style="color: #ff7777;display:inline-block;">
						    			赞！<s:property value="thatIsGoodNum"/> 
						    		</s:a>
						    		<span class="tips" title="I 服了  You，给层主点个赞(ps不能自己给自己赞)">
						    			[?]
						    		</span>
						    	</td>
							</tr>
							<tr>
								<td style="height:220px;padding-top: 20px;"valign="top">
								    <s:if test="user_ == null || user_.name == 'visitors'">
								        <%--头像--%>
								        <img class="picc" src="img/cantonese.jpg" /><br/>
								        <div class = "guestName">游客</div>
								    </s:if>
								    <s:else>
								        <%--头像--%>
								        <img class="pic" src='<s:property value="user_.getPicture_path()"/>' /> <br/> 
								        <s:a href = "user/getUser?user_.id=%{user_.id}" ><font class="userName"><s:property value="user_.name"/></font></s:a>
								    </s:else>
								    
								</td>
								
								<td colspan="2" style="text-align:left;padding: 10px;">
								    <span><s:property value="content" escapeHtml="false"/></span><br/><br/>
								    
								    <s:date name="issuedDate" format="yy-MM-dd HH:mm:ss" /><br> <%--<s:property value="%{@com.CantoneseClubBBS.util.Display@DateForDisply(issuedDate)}"/>--%>
								    <s:if test="updateDate != null">
								        <span>
								            <font color="red">修改于：<s:date name="updateDate" format="yy-MM-dd HH:mm:ss" /></font>
								        </span>
								    </s:if>
								    
								    <a href="post/toAddThemeReply?postTheme.id=${postTheme.id}&pageModel.pageSize=${pageModel.pageSize}&order=${order }&postReply.id=${id}" title="点击回复本楼">回复该楼</a>
								    <input type="checkbox" name="checkedReplyIds" value="${id }" title="勾上可以多选需要回复的楼层" 
								    	style="margin-top: 3px;margin-left:3px; position: absolute;" />
							    </td>
							</tr>
							<tr>
								<td colspan="3" style="height: 10px;"></td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="3">
								<s:hidden name="postTheme.id"></s:hidden>
								<s:hidden name="pageModel.pageSize"></s:hidden>
								<s:hidden name="order"></s:hidden>
								<span><s:submit value="回复所选楼层"></s:submit></span>
							</td>	
						</tr>
					</table>
				</s:form>
			</center>
			<my_p:pager pageIndex="${pageModel.pageIndex}"
				pageSize="${pageModel.pageSize}"
				recordCount="${pageModel.recordCount}"
				submitUrl="post/getPTByIdByPage?pageModel.pageIndex={0}&pageModel.pageSize=${pageModel.pageSize}&postTheme.id=${postTheme.id}&order=${order }" />
			
		</center>
		<div class="bottom">
		</div>
		<!-- <div style="left: 100px; top: 200px;position: relative; border: 1px; border-color: black;" onmouseover="show('quoteDetail_354')" > 123</div> -->
	</div>
	<%-- pageIndex = <s:property value="pageModel.pageIndex"/> --%>
	<%-- 加上s:debug会报错，不用管 --%>
	<%-- <s:debug></s:debug>  --%>
	
</body>
 
<script type="text/javascript">

//var j = 0;
function show(obj){
	var theClosedivs = document.getElementsByClassName("closeQuoteDetail");
	var toShow=[];
	var ts = 0;
	for( var j = theClosedivs.length ; j > 0; j--){
			if(theClosedivs.item(j-1).id == obj){
				toShow[ts++]=theClosedivs.item(j-1);
			}
	}
	setTimeout(function(){
		for(var i = 0 ; i < toShow.length ; i++){
			toShow[i].className= "showQuoteDetail";
		}
	},800);
}
function closeShow(a){
	
}
$(document).bind("click",function(event){  
    var e = event || window.event;   
    var elem = e.srcElement||e.target;   
    while(elem)   
    {   
        if(elem.className == "showQuoteDetail")   
        {   
            return;   
        }   
        elem = elem.parentNode;        
    }   
   var forCloseNumber = document.getElementsByClassName("showQuoteDetail").length
   for(var i = 0 ; i < forCloseNumber ; i++){
	   document.getElementsByClassName("showQuoteDetail").item(0).className="closeQuoteDetail";
   }
 });


function thatIsGood(themeId,replyId){
	if(replyId != -1){
		$.getJSON('post/addPostZan?postTheme.id='+themeId+'&postReply.id='+replyId, function(data, status){
			if (status == "success"){
				if(data.totalZan != '全贴共收到赞 -1'){
					$("#totalZan").html(data.totalZan);
				}
				if(data.replyZan !='已赞 -1'){
					$("#replyZanId"+replyId).html(data.replyZan);
				}
			}
		}, "text");
	}else{
		$.getJSON('post/addPostZan?postTheme.id='+themeId, function(data, status){
			if (status == "success"){
				if(data.zan != '已赞 -1'){
					$("#zan").html(data.zan);
				}
				if(data.totalZan != '全贴共收到赞 -1'){
					$("#totalZan").html(data.totalZan);
				}
			}
		}, "text");
	}
	
}

$(document).ready(function(){
/* 	var ii =  new Array();
	 ii = $(".quote"); 
	ii[2].mouseover(
			 
			 function(){
				 ii = $(".quote");
				 ii[2].mouseover(function(){
					 ii[2].style.display = "none";
				 }
						 )
				  }		 
	 ); */
/* 	  var quotes = $(".quote"); 
	  for(var i = 0; i < quotes.length;i++){
		  //alert(quotes[i].getAttribute("id"));
		  quotes[i].mouseover(
				  (function(){
					  alert("123");
					  var postId = quotes[i].getAttribute("id");
					  postId = postId.substr( (postId.indexOf("_")+1) );
					  postId = window.encodeURIComponent(userId);
					  // 发送get请求
					  $.get("post/showQuoteReplyDetail.action", "postReply.id=" + postId, function(data, status){
						  // status : success|error
						  //alert(status + "==" + data);
						  if (status == "success"){
							  var x,y; 
							  x = event.clientX; 
							  y = event.clientY; 
							  quotes[i].style.left = x; 
							  quotes[i].style.top = y; 
							  quotes[i].innerHTML = data; 
							  quotes[i].style.display = "block"; 
							  
						  }
					}, "text");
					  
					  
					quotes[i].onmouseout(function(){
						quotes[i].innerHTML = ""; 
						quotes[i].style.display = "none";
					});
				  })
		  );
	  } */
	 /*  $(".quote").click=aa(); */
/*  	  $(".quote").onmouseover(function(){
		  
		  setTimeout("showQuoteDetail()",400);
	  })  */
});

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
    var replyId = '<s:property value="postReply.id"/>';
    if(replyId != ''){
    	$('html,body').animate({scrollTop:$('#'+'<s:property value="postReply.id"/>').offset().top}, 800);
        setTimeout("fix_locate()",800);
    }
    
    
    
    var url = window.location.href;//获取当前浏览器的url  
    index = url.indexOf("flag=true");
	if(index != -1){
		var index = document.body.scrollTop;
	    $('html,body').animate({scrollTop:(index-50)}, 800);
	}
	
	
}



function fix_locate(){
	var index = document.body.scrollTop;
    $('html,body').animate({scrollTop:(index-50)}, 500);
}

</script>
<%-- <script type="text/javascript" src="JS/com-init.js"></script> --%>
</html>
