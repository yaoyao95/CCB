package com.CantoneseClubBBS.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	/** loginUserInfo */
	public static final String USER_SESSION = "loginUserInfo";

	// 拦截器处理
	// 所有的action公用的业务操作在这里实现！
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		// 1. 获取ActionContext，再获取Session
		ActionContext ac = invocation.getInvocationContext();
		Map<String, Object> session = ac.getSession();
		// 2. 获取session中登陆用户"loginInfo"
		Object user = session.get("loginUserInfo");

		// 3. 判断
		if (user == null) {
			// 没有登陆, 返回“input”视图对应的登陆页面
			ac.put("tips", "该页面或功能需要登录才能访问");
			return "pleaseLogin";
		} else {
			// 已经登陆, 放行，去到当前访问的action类的execute方法！
			return invocation.invoke();
		}

	}
}
