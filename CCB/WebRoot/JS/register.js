/**用户名flag*/
var flag1 = false;
/**昵称flag*/
var flag2 = false;//昵称，不是必须的
/**身份证flag*/
var flag3 = false;
/**qq flag*/
var flag4 = false;
/**手机 flag*/
var flag5 = false;
/**真实姓名flag*/
var flag6 = false;
/**密码flag*/
var flag7 = false;
/**密码验证flag*/
var flag8 = false;
function $(id){
	return document.getElementById(id);
	}
function clear_input(obj){
	if(obj.value=="最多12位半角符号"){
		obj.value="";
		obj.className="input";}
	}
function default_input(obj){
	var reg = /^[0-9a-z\u4e00-\u9fa5\uff70-\uff9d\uff9e\uff9f\uff67-\uff6f\u0800-\u4e00]{1,12}$/;
	if(obj.value==""){
		obj.value="最多12位半角符号";
		obj.className="input_default";
		$("buhefa").textContent="";
		}
	else if(obj.value != "最多12位半角符号"){
		if(reg.test(obj.value)== false){
			$("buhefa").textContent="用户名格式错误，最多12位半角符号，只能是中日数字和小写英文字符";
			$("buhefa").className="buhefa";
			}
		if(reg.test(obj.value)==true){
			$("buhefa").textContent="恭喜你用户名合法";
			$("buhefa").className="hefa";
			flag1 = true;
		}	
	}
}

function ni_clear_input(obj){
	if(obj.value=="最多20位半角符号"){
		obj.value="";
		obj.className="input";}
	}
function ni_default_input(obj){
	var reg = /^[0-9a-z\u4e00-\u9fa5\uff70-\uff9d\uff9e\uff9f\uff67-\uff6f\u0800-\u4e00]{1,20}$/;
	if(obj.value==""){
		obj.value="最多20位半角符号";
		obj.className="input_default";
		$("nichengbu").textContent="";
		}
	else if(obj.value != "最多20位半角符号"){
		if(reg.test(obj.value)==false){
			$("nichengbu").textContent="昵称格式错误，最多20位半角符号";
			$("nichengbu").className="nichengbu";
			}
		if(reg.test(obj.value)==true){
			$("nichengbu").textContent="恭喜你昵称合法";
			$("nichengbu").className="nicheng";
			flag2=true;
		}	
	}
}

function mima_clear_input(obj){
	if(obj.value=="只能包含数字字母和下划线"){
		$("mamikuang").innerHTML='<input type="password" name="user_.password" '+
		'class="input" id="mima_input" value="" onClick="mima_clear_input(this)" onBlur="mima_default_input(this)" />';
	     }
	    $("mima_input").focus();
	}
function mima_default_input(obj){
	var reg = /^[0-9a-zA-Z_]{1,20}$/;
	if(obj.value==""){
		$("mamikuang").
		innerHTML='<input type="text" name="user_.password" class="input_default" id="mima_input" '+
                   'value="只能包含数字字母和下划线" onClick="mima_clear_input(this)" onBlur="mima_default_input(this)" />';
		$("mima_input").focus();
		$("mimabu").textContent="";
		}
	else if(obj.value != "只能包含数字字母和下划线"){
		if(reg.test(obj.value)==false){
			$("mimabu").textContent="密码格式有误，最多只能包含20位数字字母或下划线";
			$("mimabu").className="mimabu";
			}
		if(reg.test(obj.value)==true){
			$("mimabu").textContent="恭喜你密码合法";
			$("mimabu").className="mima";
			flag7 = true;
		}	
	}
}

function mimazaici_clear_input(obj){
	if(obj.value=="请再次输入密码"){
		$("querenmimakuang").
		   innerHTML='<input type="password" name="u_psd_queren" class="input" id="mima_zaici_input" value="" '+
			  ' onClick="mimazaici_clear_input(this)" onBlur="mimazaici_default_input(this)" />';
		$("mima_zaici_input").focus();
		}
	}
function mimazaici_default_input(obj){
	var mima = $("mima_input").value;
	if(obj.value==""){
		$("querenmimakuang").
		   innerHTML='<input type="text" name="u_psd_queren" class="input_default" id="mima_zaici_input" '+
			   'value="请再次输入密码" onClick="mimazaici_clear_input(this)" onBlur="mimazaici_default_input(this)" />';
		$(mima_zaici_input).focus();
		$("mimabuyizhibu").textContent=""
		}
	else if(obj.value != "请再次输入密码"){
		if(obj.value != mima){
			$("mimabuyizhibu").textContent="两次密码输入不一致";
			$("mimabuyizhibu").className="mimabuyizhibu";
			}
		if(obj.value == mima){
			$("mimabuyizhibu").textContent="";
			$("mimabuyizhibu").className="mimabuyizhi";
			flag8 = true;
		}	
	}
}

function ming_clear_input(obj){
	if(obj.value=="如:张三"){
		obj.value="";
		obj.className="input";}
	}
function ming_default_input(obj){
	if(obj.value==""){
		obj.value="如:张三";
		obj.className="input_default";
		}
	else if(obj.value != "如:张三"){
			flag6 = true;
		}	
}

function shen_clear_input(obj){
	if(obj.value=="请输入18位身份证号"){
		obj.value="";
		obj.className="input";}
	}
function shen_default_input(obj){
	if(obj.value==""){
		obj.value="请输入18位身份证号";
		obj.className="input_default";
		$("shenbu").textContent="";
		}
	else if(obj.value != "请输入18位身份证号"){
		var shen=obj.value.toString();
		var reg = /^[0-9x]{18}$/;
		if(shen.length != 18||reg.test(shen) == false){
			$("shenbu").textContent="身份证号格式错误，请重新输入";
			$("shenbu").className="shenbu";
			}
		if(obj.value.length == 18&&reg.test(shen) == true){
			$("shenbu").textContent="恭喜你身份证号合法";
			$("shenbu").className="shen";
			flag3 = true;
		}	
	}
}



//生日下拉联动
window.onload = function(){  
strYYYY = document.form1.YYYY.outerHTML;  
strMM = document.form1.MM.outerHTML;  
strDD = document.form1.DD.outerHTML;  
MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];  
  
//先给年下拉框赋内容  
var y = new Date().getFullYear();  
var str = strYYYY.substring(0, strYYYY.length - 9);  
for (var i = (y-50); i < (y+10); i++) //以今年为准，前50年，后10年  
{  
str += "<option value='" + i + "'> " + i + "</option>\r\n";  
}  
document.form1.YYYY.outerHTML = str +"</select>";  
  
//赋月份的下拉框  
var str = strMM.substring(0, strMM.length - 9);  
for (var i = 1; i < 13; i++)  
{  
str += "<option value='" + i + "'> " + i + "</option>\r\n";  
}  
document.form1.MM.outerHTML = str +"</select>";  
  
document.form1.YYYY.value = y;  
document.form1.MM.value = new Date().getMonth() + 1;  
var n = MonHead[new Date().getMonth()];  
if (new Date().getMonth() ==1 && IsPinYear(YYYYvalue)) n++;  
writeDay(n); //赋日期下拉框  
document.form1.DD.value = new Date().getDate();  
}  
function YYYYMM(str) //年发生变化时日期发生变化(主要是判断闰平年)  
{  
var MMvalue = document.form1.MM.options[document.form1.MM.selectedIndex].value;  
if (MMvalue == ""){DD.outerHTML = strDD; return;}  
var n = MonHead[MMvalue - 1];  
if (MMvalue ==2 && IsPinYear(str)) n++;  
writeDay(n)  
}  
function MMDD(str) //月发生变化时日期联动  
{  
var YYYYvalue = document.form1.YYYY.options[document.form1.YYYY.selectedIndex].value;  
if (str == ""){DD.outerHTML = strDD; return;}  
var n = MonHead[str - 1];  
if (str ==2 && IsPinYear(YYYYvalue)) n++;  
writeDay(n)  
}  
function writeDay(n) //据条件写日期的下拉框  
{  
var s = strDD.substring(0, strDD.length - 9);  
for (var i=1; i<(n+1); i++)  
s += "<option value='" + i + "'> " + i + "</option>\r\n";  
document.form1.DD.outerHTML = s +"</select>";  
}  
function IsPinYear(year)//判断是否闰平年  
{ return(0 == year%4 && (year%100 !=0 || year%400 == 0))}  




function qq_clear_input(obj){
	if(obj.value=="请输入QQ号码（6~11位数字）"){
		obj.value="";
		obj.className="input";}
	}
function qq_default_input(obj){
	if(obj.value==""){
		obj.value="请输入QQ号码（6~11位数字）";
		obj.className="input_default";
		$("qq").textContent="QQ号格式错误，请重新输入";
		}
	else if(obj.value != "请输入QQ号码（6~11位数字）"){
		var qq=obj.value.toString();
		var reg = /^[0-9]{6,11}$/;
		if(qq.length < 6 || qq.length > 11 ||reg.test(qq) == false){
			$("qq").textContent="QQ号格式错误，请重新输入";
			$("qq").className="qqbu";
			}
		if(qq.length>=6 && qq.length<=11 && reg.test(qq) == true){
			$("qq").textContent="恭喜你QQ号合法";
			$("qq").className="qq";
			flag4 = true;
		}	
	}
}


function phone_clear_input(obj){
	if(obj.value=="请输入11位手机号码"){
		obj.value="";
		obj.className="input";}
	}
function phone_default_input(obj){
	if(obj.value==""){
		obj.value="请输入11位手机号码";
		obj.className="input_default";
		$("phone").textContent="";
		}
	else if(obj.value != "请输入11位手机号码"){
		var phone=obj.value.toString();
		var reg = /^[0-9]{11}$/;
		if(phone.length != 11 ||reg.test(phone) == false){
			$("phone").textContent="手机号格式错误，请重新输入";
			$("phone").className="phonebu";
			}
		if(qq.length = 11 && reg.test(phone) == true){
			$("phone").textContent="恭喜你手机号合法";
			$("phone").className="phone";
			flag5 = true;
		}	
	}
}

function check_register(){
	var userName = $("input").value;
	var nicheng = $("ni_input").value;
	var psd = $("psd").value;
	var psda = $("psda").value;
	var xingbies = document.getElementsByTagName("input");

	if(userName == ""||userName == "最多12位半角符号"){
		alert("用户名不能为空");
		$("input").focus();
		return false;
		}
	if(nicheng == ""||nicheng == "最多20位半角符号"){
		nicheng="";
		flag2 = true;
		}
	
	if(psd == ""){
		alert("请输入密保问题");
	    $("psd").focus();
		return false;
		}
	if(psda == ""){
		alert("请输入密保答案");
	    $("psda").focus();
		return false;
		}
	if(flag1 == true && flag2 == true && flag7 == true && flag8 == true){
		return true;}
		else if(flag1 == false){
			alert("请输入合法用户名");
		    $("input").focus();
		    return false;
			}
			else if(flag2 == false){
				alert("请输入合法昵称");
		        $("ni_input").focus();
		        return false;
				}
			    else if(flag7 == false){
				    alert("请输入合法密码");
		            $("ni_input").focus();
		            return false;
				    }
			         else{
				           alert("密码不一致");
		                   $("ni_input").focus();
		                   return false;
				           }
}

function _clear(){

	 $("input").value="最多12位半角符号"; 
	 $("input").className="input_default"
	 $("buhefa").textContent="";
	 
	 $("ni_input").value="最多20位半角符号"; 
	 $("ni_input").className="input_default"
	 $("nichengbu").textContent="";
	 
//	 $("ming").value="如:张三";
//	 $("ming").className="input_default"
//	 $("mingbu").textContent="";
//	
//	 $("shen_input").value="请输入18位身份证号"; 
//	 $("shen_input").className="input_default"
//	 $("shenbu").textContent="";
//	 
//	 document.form1.YYYY.value=""; 
//	 document.form1.MM.value=""; 
//	 document.form1.DD.value=""; 
//	
//	 $("qq_input").value="请输入QQ号码（6~11位数字）"; 
//	 $("qq_input").className="input_default"
//     $("qq").textContent="";
//	 
//	 $("phone_input").value="请输入11位手机号码"; 
//	 $("phone_input").className="input_default"
//	 $("phone").textContent="";
	 
	 $("psd").value=""; 
	 
	 $("psda").value=""; 
     $("psd2").value=""; 
	 
	 $("psda2").value="";
	}