/**
 * 
 */
package com.CantoneseClubBBS.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 阻止直接访问jsp 跳转到首页面
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月27日
 * @version 1.0
 * @update_date
 */
public class PreventAccessJspDirectly implements Filter {
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String url = httpServletRequest.getRequestURI();
		if (url.endsWith(".jsp") && url != null) {
			if (url.endsWith("bgm.jsp")) {
				// chain.doFilter(request, response);
			} else {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
				System.out.println(httpServletRequest.getContextPath());
				return;
			}

		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
