/**
 * 通用初始化操作
 */

/**图像初始化*/
window.onload = function initImg(){
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
	    
	}  