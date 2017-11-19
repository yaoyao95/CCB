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
    <!-- 改web图标 -->
    <link rel="shortcut icon" href="http://localhost:8080/CCB/img/guazi.ico" type="image/x-icon" />
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="CSS/layout.css">
    <link rel="stylesheet" type="text/css" href="CSS/login.css">
    
  </head>
  
  <body>
    <div class="main">
      <div class="top" align="center">
      	  <jsp:include page="./navigation.jsp" flush="true"></jsp:include>
  	  </div>
      <div class="middle">
			<center>
				<s:fielderror style="color:red"></s:fielderror>
				<s:actionerror style="color:red" />
			</center>
			<div class="denglu">
              <div class="zuotu">
                  <img src="img/cantonese.jpg">
              </div>
              <div class="denglubiao">
                  <s:form action="login" namespace="/user" method="post" >
                  <div class="usernamediv">
                      <span>用户名</span><s:textfield type="text" style="background-color: #F9FFED"  name="user_.name" id="username" />
                  </div>
                  <div class="userpsddiv">
                      <span>密码</span><s:textfield type="password" class="u_psd" name="user_.password" id="password" />      
                  </div>
                  <div class="autoLogin">
                  		自动登录<s:checkbox name="isAutoLogin" value="true"></s:checkbox>
                  		&nbsp;<span style="color:red;">保存90天</span>
                  </div>
                  <div class="yanzhengma">
                  		
                  			验证码:<s:textfield name="checkCode" size="5"></s:textfield>
                  		
                  		<%-- 若要点击图片刷新，重新得到一个验证码，要在后面加上个随机数，
                  		这样保证每次提交过去的都是不一样的path，防止因为缓存而使图片不刷新 --%>
        				<img  src="createImage.action" onclick="this.src='createImage.action?'+ Math.random()" 
        					title="点击图片刷新验证码"/>
                  </div>
                  <s:hidden name="key"></s:hidden>
                  <s:hidden name="iv"></s:hidden>
                  <div class="submit">
                      <input type="submit" value="登录" onclick="pwden()">
                  </div>
                  </s:form>  
              </div>
          </div>
          <div class="zhucediv">
              <a href="zhuce.jsp" class="zhuce" id="zhuce"></a>
              <div class="zhucefont"><a href="toRegister.action">注册</a></div>
          </div>
      </div>
	  <div class="bottom">
	  </div>
  </div>
  </body>
  <%-- <script type="text/javascript" src="JS/crypto-js-3.1.9/crypto-js.js"></script>
  <script type="text/javascript" src="JS/crypto-js-3.1.9/core.js"></script>
  <script type="text/javascript" src="JS/crypto-js-3.1.9/cipher-core.js"></script> --%>
  
  
  <%-- <script type="text/javascript" src="JS/cryptojs/components/core.js"></script>
  <script type="text/javascript" src="JS/cryptojs/components/cipher-core.js"></script> --%>
  <%-- <script type="text/javascript" src="JS/cryptojs/components/mode-ecb-min.js"></script> --%>
  <%-- <script type="text/javascript" src="JS/crypto-js-3.1.9/aes.js"></script> --%>
  <%-- <script type="text/javascript" src="JS/cryptojs/components/evpkdf.js"></script> --%>
  <%-- <script type="text/javascript" src="JS/crypto-js-3.1.9/pad-zeropadding.js"></script> --%>
  
  <script type="text/javascript" src="JS/aes.js"></script>
  <script type="text/javascript" src="JS/pad-zeropadding-min.js"></script>
  
  <script type="text/javascript">
  /* var data = "888888";
  var srcs  = CryptoJS.enc.Utf8.parse(data);
  var key  = CryptoJS.enc.Utf8.parse('<s:property value="key"/>');
  function Encrypt(word){  

      var srcs = CryptoJS.enc.Utf8.parse(word);  
      var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
      return encrypted.toString();  
  }  
  
  function pwden(){
	  var pwd = $("#password").val();
	  pwd = Encrypt(pwd);
	  var pi = document.getElementById("password");
	  pi.value = pwd;
	  alert("123");
	  return true;
  } */
  
  function encrypt(data) {
      var key  = CryptoJS.enc.Latin1.parse('<s:property value="key"/>');
      var iv   = CryptoJS.enc.Latin1.parse('<s:property value="iv"/>');
      return CryptoJS.AES.encrypt(data, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
  }
  
  function pwden(){
	  var pwd = $("#password").val();
	  pwd = encrypt(pwd);
	  var pi = document.getElementById("password");
	  pi.value = pwd;
	  //alert(pi.value);
	  return true;
  }
  </script>
</html>
