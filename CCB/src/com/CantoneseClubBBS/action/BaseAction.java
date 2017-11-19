/**
 * 
 */
package com.CantoneseClubBBS.action;

/**
 * @author  AoiHoshino
 * @email   AoiHoshino999@gmail.com
 * @date    2017年7月22日
 * @updateDate
 * @version 1.0
 */

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 1L;

	public HttpServletRequest request;

	public HttpServletResponse response;

	public Map<String, Object> sessionData;

	@Override
	public void setSession(Map<String, Object> sessionData) {
		this.sessionData = sessionData;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}
}