package com.CantoneseClubBBS.interceptor;

import java.util.Map;

import com.CantoneseClubBBS.domain.user.User_;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckAdminAuthority extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	// 拦截器处理
	// 所有的action公用的业务操作在这里实现！
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		// 1. 获取ActionContext，再获取Session
		ActionContext ac = invocation.getInvocationContext();
		Map<String, Object> session = ac.getSession();
		// 2. 获取session中登陆用户"loginInfo"
		User_ user = (User_) session.get("loginUserInfo");

		// 3. 判断
		if (user.getAuthority().charAt(0) == 'Y') {
			ac.put("isHighestAdmin", true);
		} else {
			ac.put("isHighestAdmin", false);
		}
		if (user.getAuthority().charAt(1) == 'Y') {
			ac.put("isNoticeAdmin", true);
		} else {
			ac.put("isNoticeAdmin", false);
		}
		if (user.getAuthority().charAt(2) == 'Y') {
			ac.put("isTeachingAdmin", true);
		} else {
			ac.put("isTeachingAdmin", false);
		}
		if (user.getAuthority().charAt(3) == 'Y') {
			ac.put("isTaskAdmin", true);
		} else {
			ac.put("isTaskAdmin", false);
		}
		if (user.getAuthority().charAt(4) == 'Y') {
			ac.put("isFootprintAdmin", true);
		} else {
			ac.put("isFootprintAdmin", false);
		}
		if (user.getAuthority().charAt(5) == 'Y') {
			ac.put("isUserAdmin", true);
		} else {
			ac.put("isUserAdmin", false);
		}
		if (user.getAuthority().charAt(8) == 'Y') {
			ac.put("isWaterAdmin", true);
		} else {
			ac.put("isWaterAdmin", false);
		}
		if (user.getAuthority().charAt(9) == 'Y') {
			ac.put("isBBSPageAdmin", true);
		} else {
			ac.put("isBBSPageAdmin", false);
		}

		if ((boolean) ac.get("isHighestAdmin") == true || (boolean) ac.get("isNoticeAdmin") == true
				|| (boolean) ac.get("isTeachingAdmin") == true || (boolean) ac.get("isTaskAdmin") == true
				|| (boolean) ac.get("isFootprintAdmin") == true || (boolean) ac.get("isUserAdmin") == true
				|| (boolean) ac.get("isWaterAdmin") == true || (boolean) ac.get("isBBSPageAdmin") == true) {
			return invocation.invoke();
		} else {
			// 无权限
			ac.put("tips", "您没有权限管理论坛，如有兴趣参与管理，请联系管理员");
			return "knownError";
		}
	}
}
