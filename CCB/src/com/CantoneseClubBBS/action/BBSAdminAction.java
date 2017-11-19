/**
 * 
 */
package com.CantoneseClubBBS.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年7月25日
 * @updateDate
 * @version 1.0
 */
public class BBSAdminAction extends ActionSupport {

	private static final long serialVersionUID = 5849263298067278020L;
	/** 主页歌曲 */
	private String hSong;

	public String pageAdmin() {
		try {
			Map<String, Object> application = ActionContext.getContext().getApplication();
			hSong = hSong.replaceAll(" width=\"?\\d{1,4}\"?", " width=80 ");
			hSong = hSong.replaceAll(" height=\"?\\d{1,4}\"?", " height=86 ");
			application.put("hSong", hSong);
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("tips", "添加成功，您需要刷新整个页面才能看出效果，点击返回");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/**
	 * @return the hSong
	 */
	public String gethSong() {
		return hSong;
	}

	/**
	 * @param hSong
	 *            the hSong to set
	 */
	public void sethSong(String hSong) {
		this.hSong = hSong;
	}
}
