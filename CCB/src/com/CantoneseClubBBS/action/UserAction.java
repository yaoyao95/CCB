package com.CantoneseClubBBS.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail;
import com.CantoneseClubBBS.domain.user.mail.Mail2;
import com.CantoneseClubBBS.domain.user.mail.Receive_user;
import com.CantoneseClubBBS.interceptor.LoginInterceptor;
import com.CantoneseClubBBS.service.RemindService;
import com.CantoneseClubBBS.service.UserService;
import com.CantoneseClubBBS.util.AesEncryptUtil;
import com.CantoneseClubBBS.util.CookieUtils;
import com.CantoneseClubBBS.util.PageModel;
import com.CantoneseClubBBS.util.StringRandom;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	/** 定义业务处理对象 */
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("remindService")
	private RemindService remindService;

	/** 用户 */
	private User_ user_;
	/** 用户收到的邮件集合 */
	private List<Mail> receivedMails;
	/** 用户收到的邮件集合 */
	private List<Mail2> receivedMails2;
	/** 用户收到的邮件集合及相关信息 */
	private List<Receive_user> receive_users;
	/** 用户发出的邮件集合 */
	private List<Mail2> sendedMails2;
	/** 邮件实体 */
	private Mail mail;
	/** 邮件实体v2 */
	private Mail2 mail2;
	/** 已选mail的id */
	private int[] checkedMailIds;
	/** 是否选中del删除按钮 */
	private String operation;
	/** 分页实体 */
	private PageModel pageModel;
	/** 当前登录用户的@提醒集合 */
	private List<Reminding> remindings;
	/** 当前登录用户收到的赞集合 */
	private List<ThatIsGood> thatIsGoods;

	/** 用户名 */
	private String names;
	/** 记录提醒的查询条件 */
	private Reminding rdc;
	/** 记录赞的查询条件 */
	private ThatIsGood tigc;

	// 封装上传的文件相关数据
	private File[] file; // 保存上传的文件对象,file对应表单元素名称，File为固定格式，拦截器会解析这个格式！
	private String[] fileFileName; // 文件名
	private String[] fileContentType;// 文件类型

	/** 用户权限描述 */
	private String userAuthority;
	// 用户权限设置相关
	private boolean isNoticeAdmin_;
	private boolean isTeachingAdmin_;
	private boolean isTaskAdmin_;
	private boolean isFootprintAdmin_;
	private boolean isUserAdmin_;
	private boolean isHighestAdmin_;
	private boolean isForbidden_;
	private boolean isUserManageSuccess;
	private boolean isWaterAdmin_;
	private boolean isBBSPageAdmin_;
	/** 操作提示 */
	private String tips;

	/** 用户信息更新相关 */
	private String oldpwd;
	private String newpwd;
	private String fnpwd;

	/** 是否自动登录 */
	private boolean isAutoLogin;

	private String loginUserName;
	private int loginUserId;

	/** 前端密码加密用的key */
	private String key;
	/** 前端密码加密用的vi偏移量 */
	private String iv;
	/** 验证码 */
	private String checkCode;

	/** 用户注册 */
	public String addUser() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			// 用户信息合法性检查
			boolean isIllegal = checkUser(user_);
			if (isIllegal == false) {
				addActionError("输入信息不合法，请检查");
				return "input";
			}
			boolean isUserNameExist = userService.saveUser(user_);
			// 判断用户名是否存在 不存在则可以添加
			if (isUserNameExist == false) {
				User_ newUser = userService.getUserByName(user_.getName());
				session.put("loginUserInfo", newUser);
				return SUCCESS;// 不存在
			} else {
				addActionError("用户名已经存在，请输入其他用户名");
				return "input";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/**
	 * 用户合法性检查
	 * 
	 * @param user_2
	 * @return
	 */
	@SkipValidation
	private boolean checkUser(User_ user_2) {
		return true;
	}

	public String toLogin() {
		key = StringRandom.getStringRandom(16);
		iv = StringRandom.getStringRandom(16);
		return SUCCESS;
	}

	/** 用户登录 */
	@SkipValidation
	public String login() {
		try {

			// 验证码
			if (StringUtils.isEmpty(checkCode)) {
				addActionError("请输入验证码");
				return "loginError";
			} else {
				String code = (String) ActionContext.getContext().getSession().get("checkCode");
				// code = code.toLowerCase();
				if (!checkCode.equalsIgnoreCase(code)) {
					addActionError("验证码错误");
					return "loginError";
				}
			}

			// 解密
			if (user_ != null) {
				user_.setPassword(AesEncryptUtil.desEncrypt(user_.getPassword(), key, iv).trim());
			}
			// 1. 调用service
			User_ loginUserInfo = userService.login(user_);

			// 2. 判断，如果返回的结果不为null, 说明登陆成功，保存数据到session！
			if (loginUserInfo != null) {// 登录成功
				// 获取session map
				Map<String, Object> session = ActionContext.getContext().getSession();
				session.put("loginUserInfo", loginUserInfo);
				if (isAutoLogin) {
					CookieUtils cookieUtils = new CookieUtils();
					// 记入cookie前，把key记入数据库，以便自动登录
					loginUserInfo.setAttachments(null);
					userService.updateUser(loginUserInfo, null, null, null);
					;
					Cookie cookie = cookieUtils.addCookie(loginUserInfo);
					ServletActionContext.getResponse().addCookie(cookie);
				}
				/*
				 * Cookie[] cookies =
				 * ServletActionContext.getRequest().getCookies(); for (Cookie
				 * cookie : cookies) { System.out.println(cookie.getMaxAge()); }
				 */
				// userId = loginUserInfo.getU_id();
				return "success";
			} else {
				// 否则，登陆失败，跳转到登陆页面
				addActionError("用户名或密码错误");
				return "loginError";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "input";
		}

	}

	@SkipValidation
	public String logout() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			if (session != null) {
				session.remove("loginUserInfo");
			}
			// 删除cookie
			CookieUtils cookieUtils = new CookieUtils();
			Cookie cookie = cookieUtils.delCookie(ServletActionContext.getRequest());
			if (cookie != null) {
				ServletActionContext.getResponse().addCookie(cookie);
			}
			ServletActionContext.getRequest().setAttribute("tips", "您已经注销了");
			ServletActionContext.getRequest().setAttribute("operation", "toHomePage");
			return "showMessage";
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
		// return "success";
	}

	@SkipValidation
	public String getLoginUser() {
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User_ user = (User_) session.getAttribute(LoginInterceptor.USER_SESSION);
			if (user != null) {
				if (!StringUtils.isEmpty(user.getName())) {
					loginUserName = user.getName();
				}
				if (!StringUtils.isEmpty(user.getId())) {
					loginUserId = user.getId();
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/** 更新user头像 */
	@SkipValidation
	public String updateUserPic() {
		// 获取上传的upload目录 路径
		String basePath = ServletActionContext.getServletContext().getRealPath("/upload") + "/img/userPictures";
		System.out.println(basePath);
		// 遍历
		for (int i = 0; i < file.length; i++) {
			// 上传的文件对象
			File file = new File(basePath, (user_.getId() + "_" + this.fileFileName[i]));
			// 上传
			try {
				FileUtils.copyFile(this.file[i], file);
				// 设置头像路径
				user_.setPicture_path(("upload/img/userPictures/" + user_.getId() + "_" + this.fileFileName[i]));

				// 更新user，
				userService.updateUser(user_, null, null, null);
				// 获得session
				Map<String, Object> session = ActionContext.getContext().getSession();
				// 查询新user信息
				user_ = userService.getUserById(((User_) session.get("loginUserInfo")).getId());
				// 更新user_登录信息
				session.put("loginUserInfo", user_);
			} catch (IOException e) {
				e.printStackTrace();
				return "UploadError";
			}
		}
		return SUCCESS;
	}

	/** 更新user信息 */
	public String update() {
		try {
			// 更新user，
			user_.setId(((User_) ActionContext.getContext().getSession().get("loginUserInfo")).getId());
			userService.updateUser(user_, oldpwd, newpwd, fnpwd);
			// 获得session
			Map<String, Object> session = ActionContext.getContext().getSession();
			// 查询新user信息
			user_ = userService.getUserById(((User_) session.get("loginUserInfo")).getId());
			// 更新user_登录信息
			session.put("loginUserInfo", user_);
			ServletActionContext.getRequest().setAttribute("tips", "更新成功");
			return SUCCESS;
		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("tips", "发生错误：" + e.getMessage());
				return "knownError";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}

	}

	/**
	 * 分页查询用户收到的用户的邮件
	 */
	@SkipValidation
	public String selectReceivedMailsByPage() {
		try {
			User_ u = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			receivedMails = userService.findUserReceivedMailsByIdByPage(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 分页查询用户收到的用户的邮件 version2
	 */
	@SkipValidation
	public String selectReceivedMailsByPage2() {
		try {
			User_ u = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			if (pageModel == null) {
				pageModel = new PageModel();
				pageModel.setPageSize(10);
			}
			receivedMails2 = userService.findUserReceivedMails2ByIdByPage(u.getId(), pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SkipValidation
	public String selectSendedMailsByPage() {
		try {
			User_ u = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			if (pageModel == null) {
				pageModel = new PageModel();
				pageModel.setPageSize(10);
			}
			sendedMails2 = userService.findUserSendedMails2ByIdByPage(u.getId(), pageModel);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/** 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件 */
	@SkipValidation
	public String getMailById() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			int loginedUserId = ((User_) session.get("loginUserInfo")).getId();
			Mail m = userService.getUserMailById(mail.getId(), loginedUserId);
			if (m == null) {
				addActionError("没有查找到这封邮件，可能已经被删除了。不过更有可能这不是你的邮件，别偷窥！ ╮(￣▽￣)╭");
				return "noSuchMailOrIsNotYourMail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "input";
		}
		return SUCCESS;
	}

	/** 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件 version2 */
	@SkipValidation
	public String getMailById2() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			int loginedUserId = ((User_) session.get("loginUserInfo")).getId();
			mail2 = userService.getUserMailById2(mail2.getId(), loginedUserId);
			if (mail2 == null) {
				addActionError("没有查找到这封邮件，可能已经被删除了。不过更有可能这不是你的邮件，别偷窥！ ╮(￣▽￣)╭");
				return "noSuchMailOrIsNotYourMail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "input";
		}
		return SUCCESS;
	}

	/** 对用户的邮件进行各种操作 */
	@SkipValidation
	public String operationForCheckedMails() {
		try {// 如果选择删除
			if (operation.equals("del")) {
				return delCheckedMails();
			} else if (operation.equals("down")) {
				return downCheckedMails();
			} else {
				return setMailUnread();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/** 对用户的邮件进行各种操作version2 */
	@SkipValidation
	public String operationForCheckedMails2() {
		try {// 如果选择删除
			if (operation.equals("del")) {
				return delCheckedMails2();
			} else if (operation.equals("down")) {
				return downCheckedMails();
			} else {
				return setMailUnread();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	/** 标志邮件未读 */
	@SkipValidation
	private String setMailUnread() {
		try {
			int uid = ((User_) ActionContext.getContext().getSession().get("loginUserInfo")).getId();
			userService.setMailUnRead(checkedMailIds, uid);
			addActionMessage("设置成功，点击返回");
			return "setMailUnreadSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}

	}

	/** 下载用户邮件 */
	@SkipValidation
	public String downCheckedMails() {
		try {
			// do download.....
			addActionMessage("该下载邮件功能还未实现，点击返回");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}

	}

	/** 删除用户选择的邮件 */
	@SkipValidation
	public String delCheckedMails() {
		try {
			boolean success = userService.delUserCheckedMailsByIds(checkedMailIds);
			if (success == true) {
				addActionMessage("删除邮件成功，点击返回");
				return SUCCESS;
			} else {
				addActionError("邮件删除失败，可能该邮件之前就被删除了，操作无效");
				return "unknownError";
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("未知错误，邮件删除失败，可能该邮件之前就被删除了，操作无效");
			return "unknownError";
		}
	}

	/** 删除用户选择的邮件 */
	@SkipValidation
	public String delCheckedMails2() {
		try {
			List<Mail2> ms = (List<Mail2>) ActionContext.getContext().getSession().get("ms");
			User_ u = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			boolean success = userService.delUserCheckedMailsByIds2(checkedMailIds, u, ms);
			if (success == true) {
				addActionMessage("删除邮件成功，点击返回");
				return SUCCESS;
			} else {
				addActionError("邮件删除失败，可能该邮件之前就被删除了，操作无效");
				return "unknownError";
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("未知错误，邮件删除失败，可能该邮件之前就被删除了，操作无效");
			return "unknownError";
		}
	}

	@SkipValidation
	public String getUserById() {
		try {
			user_ = userService.getUserById(user_.getId());
			userAuthority = userService.getUserAuthorityDescription(user_);
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
		return SUCCESS;
	}

	@SkipValidation
	public String getUser() {
		try {
			user_ = userService.getUserByIdOrName(user_);
			userAuthority = userService.getUserAuthorityDescription(user_);
			isFootprintAdmin_ = user_.getAuthority().charAt(4) == 'Y' ? true : false;
			isHighestAdmin_ = user_.getAuthority().charAt(0) == 'Y' ? true : false;
			isNoticeAdmin_ = user_.getAuthority().charAt(1) == 'Y' ? true : false;
			isTaskAdmin_ = user_.getAuthority().charAt(3) == 'Y' ? true : false;
			isTeachingAdmin_ = user_.getAuthority().charAt(2) == 'Y' ? true : false;
			isUserAdmin_ = user_.getAuthority().charAt(5) == 'Y' ? true : false;
			isForbidden_ = user_.getAuthority().charAt(7) == 'Y' ? true : false;
			isWaterAdmin_ = user_.getAuthority().charAt(8) == 'Y' ? true : false;
			isBBSPageAdmin_ = user_.getAuthority().charAt(9) == 'Y' ? true : false;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	public String addMail2() {
		try {
			int loginUserId = ((User_) ActionContext.getContext().getSession().get("loginUserInfo")).getId();
			List<String> messages = userService.addMail2(mail2, loginUserId, names);
			if (messages.get(0).equals("success")) {
				addActionMessage("发送成功，点击返回");
				return SUCCESS;
			} else if (messages.get(0).equals("fail")) {
				if (messages.get(1).equals("receiveUserDoesNotExist")) {
					addActionError("发送失败。用户：" + messages.get(2) + " 不存在，请查证后重试");
					return "receiveUserDoesNotExist";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
		addActionMessage("发送成功，点击返回");
		return SUCCESS;

	}

	@SkipValidation
	public String checkNewMessage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Object LoginUser = ActionContext.getContext().getSession().get("loginUserInfo");
		if (LoginUser == null) {
			return ERROR;
		}
		int uid = ((User_) ActionContext.getContext().getSession().get("loginUserInfo")).getId();

		boolean isExistNewMail = userService.checkNewMail2ByLoginUserId(uid);
		boolean isExistNewRemind = remindService.checkNewRemindingByLoginUserId(uid);
		boolean isExistNewZan = userService.checkNewZanByLoginUserId(uid);

		StringBuilder message = new StringBuilder();
		message.append("{");
		if (isExistNewMail == true) {
			message.append("\"newMessage\": \"新消息\",");
			message.append("\"newMail\": \"new\",");
		}
		if (isExistNewRemind == true) {
			message.append("\"newMessage\": \"新消息\",");
			message.append("\"newReminding\": \"new\",");
		}
		if (isExistNewZan == true) {
			message.append("\"newMessage\": \"新消息\",");
			message.append("\"newZan\": \"new\",");
		}
		int indexEnd = message.lastIndexOf(",");
		if (indexEnd != -1) {
			message = new StringBuilder(message.subSequence(0, message.lastIndexOf(",")));
		}
		message.append("}");
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().println(message.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/** 显示登录的用户的@提醒 */
	@SkipValidation
	public String toReminding() {
		if (pageModel == null) {
			pageModel = new PageModel();
			pageModel.setPageSize(50);
		}
		User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
		remindings = remindService.selectLoginUserRemindings(theLoginUser.getId(), pageModel, rdc);
		return SUCCESS;
	}

	/** 显示登录的用户的收到的zan */
	@SkipValidation
	public String toZan() {
		if (pageModel == null) {
			pageModel = new PageModel();
			pageModel.setPageSize(50);
		}
		User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
		thatIsGoods = userService.selectLoginUserZans(theLoginUser.getId(), pageModel, tigc);
		return SUCCESS;
	}

	@SkipValidation
	public String toAdmin() {
		try {
			if (user_ != null) {
				if (user_.getId() != 0) {
					user_ = userService.getUserByIdOrName(user_);
					userAuthority = userService.getUserAuthorityDescription(user_);
					isFootprintAdmin_ = user_.getAuthority().charAt(4) == 'Y' ? true : false;
					isHighestAdmin_ = user_.getAuthority().charAt(0) == 'Y' ? true : false;
					isNoticeAdmin_ = user_.getAuthority().charAt(1) == 'Y' ? true : false;
					isTaskAdmin_ = user_.getAuthority().charAt(3) == 'Y' ? true : false;
					isTeachingAdmin_ = user_.getAuthority().charAt(2) == 'Y' ? true : false;
					isUserAdmin_ = user_.getAuthority().charAt(5) == 'Y' ? true : false;
					isForbidden_ = user_.getAuthority().charAt(7) == 'Y' ? true : false;
					isWaterAdmin_ = user_.getAuthority().charAt(8) == 'Y' ? true : false;
					isBBSPageAdmin_ = user_.getAuthority().charAt(9) == 'Y' ? true : false;
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	@SkipValidation
	public String userManage() {
		try {
			userService.userManage(user_, isFootprintAdmin_, isHighestAdmin_, isNoticeAdmin_, isTaskAdmin_,
					isTeachingAdmin_, isUserAdmin_, isForbidden_, isWaterAdmin_, isBBSPageAdmin_);
			isUserManageSuccess = true;
			ServletActionContext.getRequest().setAttribute("tips", "操作成功！");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	// getter and setter
	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<Mail> getReceivedMails() {
		return receivedMails;
	}

	public void setReceivedMails(List<Mail> receivedMails) {
		this.receivedMails = receivedMails;
	}

	/**
	 * @return the sendedMails2
	 */
	public List<Mail2> getSendedMails2() {
		return sendedMails2;
	}

	/**
	 * @param sendedMails2
	 *            the sendedMails2 to set
	 */
	public void setSendedMails2(List<Mail2> sendedMails2) {
		this.sendedMails2 = sendedMails2;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public int[] getCheckedMailIds() {
		return checkedMailIds;
	}

	public void setCheckedMailIds(int[] checkedMailIds) {
		this.checkedMailIds = checkedMailIds;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the receivedMails2
	 */
	public List<Mail2> getReceivedMails2() {
		return receivedMails2;
	}

	/**
	 * @param receivedMails2
	 *            the receivedMails2 to set
	 */
	public void setReceivedMails2(List<Mail2> receivedMails2) {
		this.receivedMails2 = receivedMails2;
	}

	/**
	 * @return the receive_users
	 */
	public List<Receive_user> getReceive_users() {
		return receive_users;
	}

	/**
	 * @param receive_users
	 *            the receive_users to set
	 */
	public void setReceive_users(List<Receive_user> receive_users) {
		this.receive_users = receive_users;
	}

	/**
	 * @return the mail2
	 */
	public Mail2 getMail2() {
		return mail2;
	}

	/**
	 * @param mail2
	 *            the mail2 to set
	 */
	public void setMail2(Mail2 mail2) {
		this.mail2 = mail2;
	}

	/**
	 * @return the pageModel
	 */
	public PageModel getPageModel() {
		return pageModel;
	}

	/**
	 * @param pageModel
	 *            the pageModel to set
	 */
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	/**
	 * @return the names
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return the remindings
	 */
	public List<Reminding> getRemindings() {
		return remindings;
	}

	/**
	 * @param remindings
	 *            the remindings to set
	 */
	public void setRemindings(List<Reminding> remindings) {
		this.remindings = remindings;
	}

	/**
	 * @return the rdc
	 */
	public Reminding getRdc() {
		return rdc;
	}

	/**
	 * @param rdc
	 *            the rdc to set
	 */
	public void setRdc(Reminding rdc) {
		this.rdc = rdc;
	}

	/**
	 * @return the thatIsGoods
	 */
	public List<ThatIsGood> getThatIsGoods() {
		return thatIsGoods;
	}

	/**
	 * @param thatIsGoods
	 *            the thatIsGoods to set
	 */
	public void setThatIsGoods(List<ThatIsGood> thatIsGoods) {
		this.thatIsGoods = thatIsGoods;
	}

	/**
	 * @return the tigc
	 */
	public ThatIsGood getTigc() {
		return tigc;
	}

	/**
	 * @param tigc
	 *            the tigc to set
	 */
	public void setTigc(ThatIsGood tigc) {
		this.tigc = tigc;
	}

	/**
	 * @return the userAuthority
	 */
	public String getUserAuthority() {
		return userAuthority;
	}

	/**
	 * @param userAuthority
	 *            the userAuthority to set
	 */
	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	/**
	 * @return the isNoticeAdmin_
	 */
	public boolean getIsNoticeAdmin_() {
		return isNoticeAdmin_;
	}

	/**
	 * @param isNoticeAdmin_
	 *            the isNoticeAdmin_ to set
	 */
	public void setIsNoticeAdmin_(boolean isNoticeAdmin_) {
		this.isNoticeAdmin_ = isNoticeAdmin_;
	}

	/**
	 * @return the isTeachingAdmin_
	 */
	public boolean getIsTeachingAdmin_() {
		return isTeachingAdmin_;
	}

	/**
	 * @param isTeachingAdmin_
	 *            the isTeachingAdmin_ to set
	 */
	public void setIsTeachingAdmin_(boolean isTeachingAdmin_) {
		this.isTeachingAdmin_ = isTeachingAdmin_;
	}

	/**
	 * @return the isTaskAdmin_
	 */
	public boolean getIsTaskAdmin_() {
		return isTaskAdmin_;
	}

	/**
	 * @param isTaskAdmin_
	 *            the isTaskAdmin_ to set
	 */
	public void setIsTaskAdmin_(boolean isTaskAdmin_) {
		this.isTaskAdmin_ = isTaskAdmin_;
	}

	/**
	 * @return the isFootprintAdmin_
	 */
	public boolean getIsFootprintAdmin_() {
		return isFootprintAdmin_;
	}

	/**
	 * @param isFootprintAdmin_
	 *            the isFootprintAdmin_ to set
	 */
	public void setIsFootprintAdmin_(boolean isFootprintAdmin_) {
		this.isFootprintAdmin_ = isFootprintAdmin_;
	}

	/**
	 * @return the isUserAdmin_
	 */
	public boolean getIsUserAdmin_() {
		return isUserAdmin_;
	}

	/**
	 * @param isUserAdmin_
	 *            the isUserAdmin_ to set
	 */
	public void setIsUserAdmin_(boolean isUserAdmin_) {
		this.isUserAdmin_ = isUserAdmin_;
	}

	/**
	 * @return the isHighestAdmin_
	 */
	public boolean getIsHighestAdmin_() {
		return isHighestAdmin_;
	}

	/**
	 * @param isHighestAdmin_
	 *            the isHighestAdmin_ to set
	 */
	public void setIsHighestAdmin_(boolean isHighestAdmin_) {
		this.isHighestAdmin_ = isHighestAdmin_;
	}

	/**
	 * @return the isForbidden_
	 */
	public boolean getIsForbidden_() {
		return isForbidden_;
	}

	/**
	 * @param isForbidden_
	 *            the isForbidden_ to set
	 */
	public void setIsForbidden_(boolean isForbidden_) {
		this.isForbidden_ = isForbidden_;
	}

	/**
	 * @return the userManageSuccess
	 */
	public boolean getIsUserManageSuccess() {
		return isUserManageSuccess;
	}

	/**
	 * @param userManageSuccess
	 *            the userManageSuccess to set
	 */
	public void setIsUserManageSuccess(boolean isUserManageSuccess) {
		this.isUserManageSuccess = isUserManageSuccess;
	}

	/**
	 * @return the tips
	 */
	public String getTips() {
		return tips;
	}

	/**
	 * @param tips
	 *            the tips to set
	 */
	public void setTips(String tips) {
		this.tips = tips;
	}

	/**
	 * @return the isWaterAdmin_
	 */
	public boolean getIsWaterAdmin_() {
		return isWaterAdmin_;
	}

	/**
	 * @param isWaterAdmin_
	 *            the isWaterAdmin_ to set
	 */
	public void setIsWaterAdmin_(boolean isWaterAdmin_) {
		this.isWaterAdmin_ = isWaterAdmin_;
	}

	/**
	 * @return the oldpwd
	 */
	public String getOldpwd() {
		return oldpwd;
	}

	/**
	 * @param oldpwd
	 *            the oldpwd to set
	 */
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	/**
	 * @return the newpwd
	 */
	public String getNewpwd() {
		return newpwd;
	}

	/**
	 * @param newpwd
	 *            the newpwd to set
	 */
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	/**
	 * @return the fnpwd
	 */
	public String getFnpwd() {
		return fnpwd;
	}

	/**
	 * @param fnpwd
	 *            the fnpwd to set
	 */
	public void setFnpwd(String fnpwd) {
		this.fnpwd = fnpwd;
	}

	/**
	 * @return the isAutoLogin
	 */
	public boolean getIsAutoLogin() {
		return isAutoLogin;
	}

	/**
	 * @param isAutoLogin
	 *            the isAutoLogin to set
	 */
	public void setIsAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	/**
	 * @return the loginUserName
	 */
	public String getLoginUserName() {
		return loginUserName;
	}

	/**
	 * @param loginUserName
	 *            the loginUserName to set
	 */
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	/**
	 * @return the loginUserId
	 */
	public int getLoginUserId() {
		return loginUserId;
	}

	/**
	 * @param loginUserId
	 *            the loginUserId to set
	 */
	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	/**
	 * 前端密码加密用的key
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 前端密码加密用的key
	 * 
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the iv
	 */
	public String getIv() {
		return iv;
	}

	/**
	 * @param iv
	 *            the iv to set
	 */
	public void setIv(String iv) {
		this.iv = iv;
	}

	/**
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * @param checkCode
	 *            the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * @return the isBBSPageAdmin_
	 */
	public boolean getIsBBSPageAdmin_() {
		return isBBSPageAdmin_;
	}

	/**
	 * @param isBBSPageAdmin_
	 *            the isBBSPageAdmin_ to set
	 */
	public void setIsBBSPageAdmin_(boolean isBBSPageAdmin_) {
		this.isBBSPageAdmin_ = isBBSPageAdmin_;
	}

}
