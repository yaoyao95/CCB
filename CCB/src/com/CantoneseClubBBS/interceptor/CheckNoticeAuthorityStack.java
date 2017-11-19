package com.CantoneseClubBBS.interceptor;

import java.util.Map;

import com.CantoneseClubBBS.domain.user.User_;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckNoticeAuthorityStack extends AbstractInterceptor {

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
		if (user.getAuthority().charAt(0) == 'Y' || user.getAuthority().charAt(1) == 'Y') {
			return invocation.invoke();
		} else {
			// 无权限
			ac.put("tips", "您没有权限发布或修改等,管理公告的权限，如有兴趣参与管理，请联系管理员");
			return "knownError";
		}

	}
}
