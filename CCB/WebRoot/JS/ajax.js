(function(){
			// msie7+、firefox、chrome、opera
			if (window.XMLHttpRequest){
				window.xhr = new XMLHttpRequest();
			}else{// msie7-
				// ActiveXObject();
				// 定义msie浏览器版本号
				var xmls = ["MSXML2.XMLHTTP.7.0",
							"MSXML2.XMLHTTP.6.0",
							"MSXML2.XMLHTTP.5.0",
							"Microsoft.XMLHTTP"];
				for (var i = 0; i < xmls.length; i++){
					try{
						window.xhr = new ActiveXObject(xmls[i]);
						break;
					}catch(e){
						alert(e);
					}
				}
			}
		})();



//window.onload = function(){
//	// 获取按钮为它绑定点击事件
//	document.getElementById("uploadBtn").onclick = function(){
//		// 获取表单中的参数
//		var file = document.getElementById("file").value;
//		
//		// 对中文进行编码处理 unicode 变为utf-8格式
//		// userId = window.encodeURIComponent(userId);
//		
//		// 调用xhr.open()方法打开服务器之间的连接.
//	    // 第一个参数：请求方式post|get.
//	    // 第二个参数：请求的URL
//	    // 第三个参数：true:　异步请求的异步  false: 异步请求的同步. (看响应数据)
//	    xhr.open("post", "teaching/uploadPostFile.action", true);
//	    // 设置请求头，用来告诉浏览器对我的请求进行form-urlencode编码
//	    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
//		// 发送异步请求
//	    xhr.send("file="+file);
//	    // 获取响应数据(绑定当读到状态发生改变的事件)
//	    xhr.onreadystatechange = function(){
//	    	if (xhr.readyState == 4){ // 读取服务器端响数据完成
//	    		if (xhr.status == 200){ // 代表服务器响应正常
//	    			// 获取响应文本
//	    			var res = xhr.responseText;
//	    			// dom编程
//	    			document.getElementById("uploadMessage").innerHTML = res;
//	    		}
//	    	}
//	    };
//	};
//};
