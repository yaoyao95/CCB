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
	<meta http-equiv="keywords" content="西北大学粤语社,粤语社,西北大学">
	<meta http-equiv="description" content="西北大学粤语文化社是一个粤语学习，聊天，交友的地方">
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
	
	<style type="text/css">
	.copy-tips{position:fixed;z-index:999;bottom:70%;left:50%;margin:0 0 -20px -80px;background-color:rgba(0, 0, 0, 0.2);filter:progid:DXImageTransform.Microsoft.Gradient(startColorstr=#30000000, endColorstr=#30000000);padding:6px;}
	.copy-tips-wrap{padding:10px 20px;text-align:center;border:1px solid #F4D9A6;background-color:#FFFDEE;font-size:14px;}
	</style>
	
	<style type="text/css">
		.HUl{
			position: absolute;
			 
			top: 3%;
		    
		    width: 70px;
		    z-index: 10000;
		    box-shadow: 2px 2px 2px grey;
		    border: 1px #EEEEFF solid;
		    background-color: #aed7ff;
		    margin: 0;
	        padding: 0;
		    display: block;
		}
		.HUl li{
		 list-style-type: none;
		 max-height: 10%;
	     font-weight: bold;
         position: relative;
	     background-color: #aed7ff;
	     border-top: 1px #EEEEEE solid;
	     display: list-item;
         z-index: 10000;
		}
	</style>
	<script type="text/javascript" src="JS/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="JS/jquery.zclip.min.js"></script> 
	<script type="text/javascript" src="JS/dist/clipboard.min.js"></script>
  </head>
  
  <body>
  	<ul class="HUl">
  		<li>
  			<div id="H">
				<a href="/CCB" title="点击返回外主页，从外主页进来可也使用左侧边栏的功能" style="cursor:pointer;">
					<img src="img/home.jpg" width="70px;" height="70px;"/>
				</a>
			</div>
  		</li>
  		<li>
  			<div id="HS" style="margin-left: -10px; margin-top:-11px; width: 100%; height:77px;" >
				<s:property value="#application.hSong" escapeHtml="no"/>
			</div>
  		</li>
  		<li>
  			<div style="width: 100%;">
					<%-- <a href="javascript:;" style="color: #ff7777;font-size:18px;line-height:35px;
					font-weight:bold;text-decoration: none;" onclick="showCTIUrl()">本页地址</a>
					<br> --%>
		
		 	<%-- <span id="copyBtn" style="color: #ff7777;font-size:18px;line-height:35px;
					font-weight:bold;text-decoration: none;">
					本页地址
			</span>
			<span class="tips" style="font-size:5px;"
				 title="复制本页面的真实地址到剪切板，使用该地址，能跳转到本页面，但播放歌曲的功能无法使用。">
				 [?]
			</span> --%>
		 
			 <!-- <br> 
			 <textarea id="bar">Mussum ipsum cacilds...</textarea>-->
			
				<div  class="btn" data-clipboard-action="copy" style="color: #ff7777; cursor:pointer;
				 font-size:18px;font-weight:bold; width: 70px; line-height: 35px;" 
				 title="点击复制本页面的真实地址到剪切板，使用该地址，能跳转到本页面，但播放歌曲的功能无法使用。"
				  align="center">
					本页<br/>地址
				</div>
			
			
			</div>
  		</li>
  		<li>
  				<div style="color: #ff7777; cursor:pointer;
				 font-size:18px;font-weight:bold; width: 70px; line-height: 35px;" 
				 title="刷新内部页面" onclick="document.getElementById('CTI').contentWindow.location.reload(true);"
				  align="center">
					 刷新
				</div>
  		</li>
  	</ul>
  	<%-- <div id="H" style="width: 3%; top:1%; left:1%; position: absolute;" >
		<a href="/CCB" title="点击返回外主页，从外主页进来可也使用左侧边栏的功能" class="tips">
			<img src="img/home.jpg" width="70px;" height="70px;"/>
		</a>
	</div>
    <div style="width: 3%; top:10%; position: absolute;" >
		<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=80 height=86 
		src="//music.163.com/outchain/player?type=2&id=347230&auto=0&height=66"></iframe>
	</div>
	
	<div style="position: absolute; left:1%; top: 25%">
		<!-- <a href="javascript:;" style="color: #ff7777;font-size:18px;line-height:35px;
		font-weight:bold;text-decoration: none;" onclick="showCTIUrl()">本页地址</a>
		<br> -->
		
		 <span id="copyBtn" style="color: #ff7777;font-size:18px;line-height:35px;
		font-weight:bold;text-decoration: none;">本页地址</span>
		<span class="tips" style="font-size:5px;"
		 title="复制本页面的真实地址到剪切板，使用该地址，能跳转到本页面，但播放歌曲的功能无法使用。">[?]</span>
		 
		 <!-- <br> 
		 <textarea id="bar">Mussum ipsum cacilds...</textarea>-->

		<a href="javascriot:;" class="btn" data-clipboard-action="copy" style="color: #ff7777;
		 font-size:18px;line-height:35px;font-weight:bold;
		 text-decoration: none;" >本页地址</a>
		 <span class="tips" style="font-size:5px;"
		 title="复制本页面的真实地址到剪切板，使用该地址，能跳转到本页面，但播放歌曲的功能无法使用。">[?]</span>
	</div> --%>
	<iframe id="CTI" src="toIndex.action" style="width: 100%;height: 100%;" frameborder="0">
	</iframe>
  </body>
  
  <script type="text/javascript">
  	function showCTIUrl(){
  		var url = parent.document.getElementById("CTI").contentWindow.location.href;
  		alert(url);
  	}
  
  </script>

  
   <script> 
  $("#copyBtn").zclip({
      path: "JS/ZeroClipboard.swf",
      copy: function(){
      return parent.document.getElementById("CTI").contentWindow.location.href;
      },
      beforeCopy:function(){/* 按住鼠标时的操作 */
          //$(this).css("color","orange");
      },
      afterCopy:function(){/* 复制成功后的操作 */
          var $copysuc = $("<div class='copy-tips'><div class='copy-tips-wrap'>☺ 复制成功</div></div>");
          $("body").find(".copy-tips").remove().end().append($copysuc);
          $(".copy-tips").fadeOut(3000);
      }
  });
  
  
  
  	/* var clipboard = new Clipboard('.btn');
  	new Clipboard('#btn', {
  		target: function(trigger) {
  	        return trigger.parent.document.getElementById("CTI").contentWindow.location.href;}
	}); */
	
	
	
	var clipboard = new Clipboard('.btn', {
        text: function() {
            return parent.document.getElementById("CTI").contentWindow.location.href;
        }
    });
	
	   clipboard.on('success', function(e) {
		   var $copysuc = $("<div class='copy-tips'><div class='copy-tips-wrap'>☺ 复制成功</div></div>");
	          $("body").find(".copy-tips").remove().end().append($copysuc);
	          $(".copy-tips").fadeOut(3000);
	    });
	 
	  clipboard.on('error', function(e) {
	         console.log(e);
	    });
</script>  


<script language="JavaScript">
<!-- Target -->

 
</script>

</html>
