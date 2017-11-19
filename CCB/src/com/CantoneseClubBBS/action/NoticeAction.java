package com.CantoneseClubBBS.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.domain.Notice;
import com.CantoneseClubBBS.service.NoticeService;
import com.CantoneseClubBBS.util.PageModel;
import com.opensymphony.xwork2.ActionSupport;

public class NoticeAction extends ActionSupport {

	private static final long serialVersionUID = 2022243222008418409L;
	/** 定义业务处理对象 */
	@Autowired
	@Qualifier("noticeService")
	private NoticeService noticeService;
	/** 定义公告集合 */
	private List<Notice> notices;
	/** 定义分页实体 */
	private PageModel pageModel;
	/** 定义公告实体 */
	private Notice notice;

	/**
	 * 查询指定id的公告
	 * 
	 * @return
	 */
	@SkipValidation // 跳过后台NoticeAction-validation校验
	public String getNoticeById() {
		try {
			notice = noticeService.getNoticeById(notice.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询最近的公告 用于主页
	 * 
	 * @return 返回最近的公告
	 */
	@SkipValidation // 跳过后台NoticeAction-validation校验
	public String notices_select_forIndex() {
		try {
			notices = noticeService.getNotices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "select_forIndex";
	}

	/**
	 * 分页查询公告
	 * 
	 * @return 返回相应页的公告
	 */
	@SkipValidation // 跳过后台NoticeAction-validation校验
	public String notices_select_byPage() {
		try {
			if (pageModel == null) {
				pageModel = new PageModel();
			}
			// notice = toUTF8(notice);//转码，因为服务器自动转码，不需要调用
			notices = noticeService.getNoticesByPage(pageModel, notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "select_byPage";
	}

	/**
	 * 添加公告，并返回到select_byPage.action
	 * 
	 * @return
	 */
	public String addNotice() {
		try {
			notice.setIssuedDate(new Date());
			noticeService.saveNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 通过ID删除公告
	 * 
	 * @return
	 */
	@SkipValidation // 跳过后台NoticeAction-validation校验
	public String delNotice() {

		try {
			noticeService.delNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String update() {
		try {
			noticeService.updateNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 用于中文字符转码，服务器tomcat7或以下需要转码， tomcat8或以上会自动转码，则不该调用该方法。
	 *
	 */
	@SkipValidation // 跳过后台NoticeAction-validation校验
	public Notice toUTF8(Notice notice) throws UnsupportedEncodingException {
		// 判断是否为get请求
		if (ServletActionContext.getRequest().getMethod().equalsIgnoreCase("get")) {
			if (notice != null) {
				if (!StringUtils.isEmpty(notice.getTitle())) {
					// get请求中文转码
					String title = new String(notice.getTitle().getBytes("iso8859-1"), "utf-8");
					notice.setTitle(title);
				}
				if (!StringUtils.isEmpty(notice.getContent())) {
					// get请求中文转码
					String content = new String(notice.getContent().getBytes("iso8859-1"), "utf-8");
					notice.setContent(content);
				}
			}
		}
		return notice;
	}

	// getter and setter
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

}
