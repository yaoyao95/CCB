/**
 * 
 */
package com.CantoneseClubBBS.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.interceptor.LoginInterceptor;
import com.CantoneseClubBBS.service.UserService;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年7月22日
 * @updateDate
 * @version 1.0
 */
public class CookieUtils {
	public static final String USER_COOKIE = "user.cookie";

	/** 添加当前user的cookie信息 */
	public Cookie addCookie(User_ user) {
		String value = user.getName() + "," + user.getPassword();
		System.out.println("======================================================" + value);
		Cookie cookie = new Cookie(USER_COOKIE, user.getName() + "#" + user.getPassword());
		// 添加cookie有效时间
		cookie.setMaxAge(60 * 60 * 24 * 90);// 保存3个月
		cookie.setPath("/");
		return cookie;
	}

	// 获取cookie
	/**
	 * 获取cookies，并判断cookies有无当前用户的信息（用户名，密码）
	 * 如果cookie有，并且用户名和密码匹配，则自动登录，并把用户信息记入session
	 * 
	 * @return boolean true,自动登录成功，false自动登录失败
	 */
	public boolean getCookie(HttpServletRequest request, UserService userService) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (CookieUtils.USER_COOKIE.equals(cookie.getName())) {
					String co_Val = cookie.getValue();
					if (!StringUtils.isEmpty(co_Val)) {
						String[] splits = co_Val.split("#");
						if (splits.length == 2) {
							// String userName = splits[0];
							// String passWord = splits[1];
							User_ user = new User_();
							user.setName(splits[0]);

							// 先获取key，然后解密再设值
							User_ tempU = userService.getUserByName(splits[0]);
							String key = null;
							if (tempU != null) {
								key = tempU.getCookie_AutoLogin_Key();
							}
							try {
								user.setPassword(EncryptUtil.aesDecrypt(splits[1], key));
							} catch (Exception e) {
								e.printStackTrace();
							}

							user = userService.login(user);
							if (user != null) {
								HttpSession session = request.getSession();
								// 添加用户信息到session中
								session.setAttribute(LoginInterceptor.USER_SESSION, user);
								return true;
							}
						}

					}
				}
			}
		}
		return false;
	}

	/**
	 * 删除cookie中当前用户的信息
	 * 
	 */
	public Cookie delCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (USER_COOKIE.equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					cookie.setPath("/");
					return cookie;
				}
			}
		}
		return null;
	}
}
