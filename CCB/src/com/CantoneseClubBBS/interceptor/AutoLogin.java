/**
 * 
 */
package com.CantoneseClubBBS.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.CantoneseClubBBS.action.BaseAction;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.service.UserService;
import com.CantoneseClubBBS.util.CookieUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年7月22日
 * @updateDate
 * @version 1.0
 */
public class AutoLogin extends AbstractInterceptor {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	private static final long serialVersionUID = -1107278582293293872L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		CookieUtils cookieUtils = new CookieUtils();

		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext()
				.get(StrutsStatics.HTTP_REQUEST);
		HttpSession session = request.getSession(false);
		User_ user = null;
		if (session != null) {
			user = (User_) session.getAttribute(LoginInterceptor.USER_SESSION);
		}
		if (user != null) {// session中有用户信息，用户已登录
			return invocation.invoke();
		} else {// session中无用户信息，用户未登录,查看cookie有无用户信息，有则把用户信息加入到session
			cookieUtils.getCookie(request, userService);
			return invocation.invoke();
		}
	}
}
