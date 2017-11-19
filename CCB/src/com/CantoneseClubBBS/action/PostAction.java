package com.CantoneseClubBBS.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.Attachment;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.service.PostService;
import com.CantoneseClubBBS.service.RemindService;
import com.CantoneseClubBBS.service.SectionService;
import com.CantoneseClubBBS.service.UserService;
import com.CantoneseClubBBS.util.PageModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PostAction extends ActionSupport {

	private static final long serialVersionUID = 2022243222008418409L;
	/* ================定义业务处理对象 ================== */
	@Autowired
	@Qualifier("postService")
	private PostService postService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("sectionService")
	private SectionService sectionService;
	@Autowired
	@Qualifier("remindService")
	private RemindService remindService;

	/* ===========集合========================== */
	/** 定义主题贴集合 */
	private List<Post_Theme> postThemes;
	/** 定义回帖集合 */
	private List<Post_Reply> postReplys;
	/** 定义置顶集合 */
	private List<Post_Theme> topPostThemes;

	/* ===========实体========================== */
	/** 定义分页实体 */
	private PageModel pageModel;
	/** 定义教学主题实体 */
	private Post_Theme postTheme;
	/** 定义教学回复实体 */
	private Post_Reply postReply;
	/** 定义User实体 */
	private User_ author;
	/** 定义所属板块实体 */
	private Section section;
	/** 提醒实体 */
	private Reminding reminding;
	

	private List<Reminding> remindings;
	private List<Reminding> remindingsForUpdate;

	private boolean saveReplyFlag;
	/* ===========查询条件========================== */
	/** 定义教学主题查询条件 */
	private Post_Theme ptc;
	/** 定义教学回复查询条件 */
	private Post_Reply prc;
	private ThatIsGood thatIsGood;

	/* ===========排序条件========================== */
	/** 定义排序条件 */
	private boolean order = true;
	/** 排序true false */
	private Map<Boolean, String> tf = new HashMap<Boolean, String>();
	private Map<Boolean, String> pttf = new HashMap<Boolean, String>();

	// ============上传相关==========================================
	// 封装上传的文件相关数据
	private File file;
	private String fileFileName;
	private String fileContentType;

	// =========== 其他=============
	/** 定义已选回帖ids */
	private int[] checkedReplyIds;
	/** 定义提醒的用户名 */
	private String theRemindedUserNames;
	/** 定义提醒的用户名Update相关jsp专用的 */
	private String theRemindedUserNamesForUpdateJSP;
	/** 提醒是否已读 */
	private boolean read;
	/** 回帖是否提醒楼主 0：不提醒 ！0提醒 */
	private int[] themeAuthor;
	/** 错误信息 */
	private String errorMessage;
	/** 原始帖子内容 */
	private String originalPostContent;
	/** 是否是管理员 */
	private boolean isAdmin;
	/** 是否是作者 */
	private boolean isTheAuthor;
	/** 是否锁帖 */
	private boolean isRock;
	/** 是否删除帖子 */
	private boolean isDelete;
	/** 是否设置为优秀帖子 */
	private boolean isGoodTheme;
	/** 是否置顶 */
	private boolean isToTop;

	/**
	 * 查询指定id的教学主题帖
	 * 
	 * @return
	 */
	@SkipValidation // 跳过后台postAction-validation校验
	public String getPostThemeById() {
		try {
			postTheme = postService.getPostThemeById(postTheme.getId());
			return SUCCESS;
		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				addActionError(e.getMessage());
				return "knownError";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}

	}

	@SkipValidation
	public String getPTByIdByPage() {
		try {
			if (pageModel == null) {
				pageModel = new PageModel();
				pageModel.setPageSize(10);
			}
			// 只要pt，不要其pr s
			postTheme = postService.getPostThemeOnlyById(postTheme.getId());

			User_ theLoginUser  = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			boolean flag = postService.checkPersonalPost(postTheme, theLoginUser);
			if (flag == false) {
				ServletActionContext.getRequest().setAttribute("tips", "私人日记只能作者本人查看、回复、修改");// 不能看
				return "knownError";
			}

			// 板块赋值
			section = postTheme.getSection();

			// 查询该pt的pr s，分页 ，多条件
			if (saveReplyFlag == false) {
				postReplys = postService.getPRsByPT_idByPage(postTheme.getId(), pageModel, prc, order);
			} else {
				postReplys = postService.getPostReplysByIds(new int[] { postReply.getId() });
				postReply = postReplys.get(0);
				pageModel.setPageIndex(Math.ceil(1.0 * postReplys.get(0).getStorey() / pageModel.getPageSize()));
				postReplys = postService.getPRsByPT_idByPage(postTheme.getId(), pageModel, prc, true);
			}

			if (read == true) {
				if (reminding != null) {
					remindService.setRemindingRead(reminding.getId());
				}
				if (thatIsGood != null) {
					postService.setThatIsGoodRead(thatIsGood.getId());
				}
			}

			return SUCCESS;
		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				addActionError(e.getMessage());
				if (e.getMessage().contentEquals("该帖子已被删除")) {
					ServletActionContext.getRequest().setAttribute("operation", "toHomePage");
					return "knownError";
				}
				return "knownError";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}

	}

	/**
	 * 分页查询教学主题帖
	 * 
	 * @return 返回相应页的教学主题帖
	 */
	@SkipValidation
	public String select_byPage() {
		try {
			if (pageModel == null) {
				pageModel = new PageModel();
				pageModel.setPageSize(20);
			}

			/*
			 * Cookie[] cookies =
			 * ServletActionContext.getRequest().getCookies(); for (Cookie
			 * cookie : cookies) { System.out.println(cookie.getValue()); }
			 */

			section.setDescription(sectionService.getSectionByName(section).getDescription());
			// 转码，因为服务器自动转码，不需要调用
			postThemes = postService.getPostThemesByPage(pageModel, ptc, section, order);
			Post_Theme ptcForTop = new Post_Theme();
			ptcForTop.setIsToTop(1);
			topPostThemes = postService.getPostThemesByPage(new PageModel(), ptcForTop, section, order);
			return "select_byPage";
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}

	}

	@SkipValidation
	public String select_forIndex() {
		try {
			if (pageModel == null) {
				pageModel = new PageModel();
				pageModel.setPageSize(8);
			}
			postThemes = postService.getPostThemesByPage(pageModel, ptc, section, false);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknowError";
		}

	}

	/**
	 * 携带主题id，跳转到回复页面
	 * 
	 * @return
	 */
	@SkipValidation
	public String toAddThemeReply() {
		try {
			if (checkedReplyIds == null) {
				if (postReply != null) {
					checkedReplyIds = new int[] { postReply.getId() };
				}
			}
			if (checkedReplyIds != null) {
				postReplys = postService.getPostReplysByIds(checkedReplyIds);// 查找需要回复的帖子
			}
			postTheme = postService.getPostThemeOnlyById(postTheme.getId());// 回帖所属的主题帖

			User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			boolean flag = postService.checkPersonalPost(postTheme, theLoginUser);
			if (flag == false) {
				ServletActionContext.getRequest().setAttribute("tips", "私人日记只能作者本人查看、回复、修改");// 不能看
				return "knownError";
			}

			theRemindedUserNames = "";
			if (postReplys != null) {
				// 需要@提醒的用户
				if (StringUtils.isEmpty(theRemindedUserNames)) {
					for (Post_Reply pr : postReplys) {
						theRemindedUserNames += pr.getUser_().getName() + ",";
					}
					theRemindedUserNames = theRemindedUserNames.substring(0, theRemindedUserNames.length() - 1);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				addActionError(e.getMessage());
				return "knownError";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}

	}

	/** 显示回帖 */
	@SkipValidation
	public String showQuoteReplyDetail() {
		try {

			int[] postReplysIds = { postReply.getId() };
			postReplys = postService.getPostReplysByIds(postReplysIds);
			postReply = postReplys.get(0);
			/*
			 * userId = new String(userId.getBytes("iso8859-1"), "utf-8");
			 * System.out.println(userId);
			 */
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(postReply.getContent());
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknowError";
		}

	}

	/**
	 * 添加回复
	 * 
	 * @return
	 */
	public String addThemeReply() {
		try {
			// 如果是私人日记，//本人或水区管理员、总管理员能查看
			User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			postTheme = postService.getPostThemeOnlyById(postTheme.getId());
			boolean flag = postService.checkPersonalPost(postTheme, theLoginUser);
			if (flag == false) {
				ServletActionContext.getRequest().setAttribute("tips", "私人日记只能作者本人查看、回复、修改");// 不能看
				return "knownError";
			}

			// 处理回帖内容
			String rc = postReply.getContent();
			// 原始content
			originalPostContent = new String(rc);
			if (rc.length() > 50000) {
				ServletActionContext.getRequest().setAttribute("tips", "字符数超过50000，请分开发帖");
				return "knownError";
			}
			int fromIndex = 0;
			while (rc.indexOf("id=\"reply_", fromIndex) != -1) {
				int targetIndexStar = rc.indexOf("id=\"reply_", fromIndex);
				int targetIndexEnd = rc.indexOf("\" class=\"quote\"", targetIndexStar);
				String quoteReplyId = rc.substring(targetIndexStar + 10, targetIndexEnd);
				rc = rc.substring(0, targetIndexEnd + 2) + "onmouseover=\"show('quoteDetail_" + quoteReplyId + "')\""
						+ rc.substring(targetIndexEnd + 1);
				fromIndex = targetIndexStar + 10;
			}
			fromIndex = 0;
			while (rc.indexOf("id=\"quoteDetail_", fromIndex) != -1) {
				int targetIndexStar = rc.indexOf("id=\"quoteDetail_", fromIndex);
				int targetIndexEnd = rc.indexOf("\" class", targetIndexStar);
				String quoteReplyId = rc.substring(targetIndexStar + 16, targetIndexEnd);
				rc = rc.substring(0, targetIndexEnd + 2) + "onmouseout=\"closeShow('quoteDetail_" + quoteReplyId
						+ "')\"" + rc.substring(targetIndexEnd + 1);
				fromIndex = targetIndexStar + 10;
			}
			// 删掉必定隐藏
			rc = rc.replace("style=\"display: none;\"", "style=\"\"");

			// 内容里添加提醒栏
			if (!StringUtils.isEmpty(theRemindedUserNames) && !theRemindedUserNames.equals("visitors")) {
				rc += "<br/><br/><br/>" + "<fieldset class=\"remindingField\">" + "<legend>"
						+ "<span class = \"reminding\">@提醒</span>" + "</legend>" + "<div class = \"reminding_2\">"
						+ theRemindedUserNames + "</div>" + "</fieldset>";
			}

			// 处理themeAuthor
			int theThemeAuthor = -1;
			if (themeAuthor.length == 2) {
				theThemeAuthor = 0;// 不提醒楼主
			} else {// (themeAuthor.length == 1)
				theThemeAuthor = themeAuthor[0];
			}
			if (theThemeAuthor == 0) {
				rc += "<br/>" + "<div class = \"reminding_3\">(不提醒楼主)</div>";
			}
			postReply.setContent(rc);

			Object userSession = ServletActionContext.getContext().getSession().get("loginUserInfo");
			int theRemindingUserId = 0;
			if (userSession == null) {// 没登录
				// 是否使用了@提醒
				if (!StringUtils.isEmpty(theRemindedUserNames)) {
					if (!theRemindedUserNames.equals("visitors")) {
						addActionError("您还未登录，不能使用提醒用户查看的功能");
						return "knownError";
					}
				} else {
					// 其他
				}

			} else {// 登录了
				theRemindingUserId = ((User_) userSession).getId();
			}

			postReply.setId(postService.addThemeReply(postReply, postTheme, author, theRemindedUserNames,
					theThemeAuthor, theRemindingUserId));
			saveReplyFlag = true;

			return SUCCESS;

		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				errorMessage = "发生错误：" + e.getMessage() + "<br/><br/>" + "本页面规则：内容不能超过80000个字符，图片支持jpg，png格式。"
						+ "请确认@提醒的用户名是否正确，多用户情况，中间要用英文“,”分开，" + "每次最多输入10个要提醒的用户，如果想提醒更多用户查看，可以新开一贴提醒，"
						+ "或发布帖子后，每次修改帖子，都可以再提醒10个用户";
				return "AddRemindingErrorAndThenToAddThemeReply";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}
	}

	/** 跳转到添加主题页面 ，携带用户id，section。 */
	@SkipValidation
	public String toAddTheme() {
		return SUCCESS;
	}

	/** 添加主题 */
	public String addTheme() {
		try {
			// 获取session
			ActionContext ac = ActionContext.getContext();
			Map<String, Object> session = ac.getSession();
			// 获取user
			User_ u = (User_) session.get("loginUserInfo");
			if (StringUtils.isEmpty(u)) {
				addActionError("请登录");
				return "pleaseLogin";
			}
			if (postTheme.getContent().length() > 50000) {
				addActionError("字符数超过50000，请分开发帖");
				return "knownError";
			}

			// 内容里添加提醒栏
			String tc = postTheme.getContent();
			originalPostContent = new String(tc);

			if (!StringUtils.isEmpty(theRemindedUserNames)) {
				tc += "<br/><br/><br/>" + "<fieldset class=\"remindingField\">" + "<legend>"
						+ "<span class = \"reminding\">@提醒</span>" + "</legend>" + "<div class = \"reminding_2\">"
						+ theRemindedUserNames + "</div>" + "</fieldset>";
			}
			postTheme.setContent(tc);
			postTheme.setId(postService.savePT(postTheme, u, section));

			/** 添加提醒 */
			if (StringUtils.isEmpty(theRemindedUserNames) == false) {
				Object userSession = ServletActionContext.getContext().getSession().get("loginUserInfo");
				if (userSession == null) {
					addActionError("您还未登录，不能使用提醒用户查看的功能");
					return "knownError";
				}
				int theRemindingUserId = ((User_) userSession).getId();
				postService.addReminds(theRemindedUserNames, 0, theRemindingUserId, postTheme.getId(), -1);
				/*
				 * addActionError("提醒指定用户查看时发生错误，请确认用户名是否正确，多用户情况，中间要用英文“,”分开，"
				 * + "每次最多输入10个要提醒的用户，如果想提醒更多用户查看，可以新开一贴提醒，" +
				 * "或发布帖子后，每次修改帖子，都可以再提醒10个用户");
				 */
			}
			return SUCCESS;

		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				errorMessage = "发生错误：" + e.getMessage() + "<br/><br/>"
						+ "本页面规则：题目不能超过250个字符，内容不能超过80000个字符，图片支持jpg，png格式。" + "请确认@提醒的用户名是否正确，多用户情况，中间要用英文“,”分开，"
						+ "每次最多输入10个要提醒的用户，如果想提醒更多用户查看，可以新开一贴提醒，" + "或发布帖子后，每次修改帖子，都可以再提醒10个用户";
				return "AddRemindingErrorAndThenToAddTheme";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}
	}

	@SkipValidation
	public String toUpdateTheme() {
		try {
			// 判断是否是管理员
			Object objIsAdmin = ActionContext.getContext().get("isAdministrator");
			if (objIsAdmin != null) {
				isAdmin = (boolean) objIsAdmin;
			}
			// 判断是否是作者
			// 登录的用户
			User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			// 作者
			User_ theAuthor = postService.getPostThemeOnlyById(postTheme.getId()).getUser_();
			if (theLoginUser.getId() == theAuthor.getId()) {
				isTheAuthor = true;
			} else {
				isTheAuthor = false;
			}
			if (!isAdmin && !isTheAuthor) {// 若不是管理员也不是作者，则没有任何修改权限
				// ActionContext.getContext().getSession().put("tips",
				// "您无权修改他人的帖子");
				ServletActionContext.getRequest().setAttribute("tips", "您无权修改他人的帖子");
				return "knownError";
			}

			// 查询需要update的postTheme
			postTheme = postService.getPostThemeOnlyById(postTheme.getId());

			// 如果是私人日记，//本人或水区管理员、总管理员能查看
			User_ theLoginUser_ = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			boolean flag = postService.checkPersonalPost(postTheme, theLoginUser_);
			if (flag == false) {
				ServletActionContext.getRequest().setAttribute("tips", "私人日记只能作者本人查看、回复、修改");// 不能看
				return "knownError";
			}

			// postTheme的section
			section = postTheme.getSection();

			isRock = postTheme.getIsRock() == 1 ? true : false;

			isGoodTheme = postTheme.getIsGoodTheme() == 1 ? true : false;

			isDelete = postTheme.getIsDelete() == 1 ? true : false;

			isToTop = postTheme.getIsToTop() == 1 ? true : false;

			// 处理原始postTheme content
			originalPostContent = new String(postTheme.getContent());
			// 查找本主题帖已经提醒的用户
			remindingsForUpdate = remindService.selectPostThemeRemindingsById(postTheme.getId());
			// 已提醒的用户
			theRemindedUserNamesForUpdateJSP = "";
			if (remindingsForUpdate != null) {
				if (remindingsForUpdate.size() > 0) {
					for (Reminding r : remindingsForUpdate) {
						theRemindedUserNamesForUpdateJSP += r.getRemindedUserName() + ",";
					}
					theRemindedUserNamesForUpdateJSP = theRemindedUserNamesForUpdateJSP.substring(0,
							theRemindedUserNamesForUpdateJSP.length() - 1);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "unknownError";
		}
	}

	public String updateTheme() {
		try {
			// 如果是私人日记，//本人或水区管理员、总管理员能查看

			Post_Theme thepostTheme = postService.getPostThemeOnlyById(postTheme.getId());
			User_ theLoginUser = (User_) ActionContext.getContext().getSession().get("loginUserInfo");
			boolean flag = postService.checkPersonalPost(thepostTheme, theLoginUser);
			if (flag == false) {
				ServletActionContext.getRequest().setAttribute("tips", "私人日记只能作者本人查看、回复、修改");// 不能看
				return "knownError";
			}
			String tc = postTheme.getContent();
			// originalPostContent = new String(tc);
			// 内容里添加提醒栏
			if (!StringUtils.isEmpty(theRemindedUserNames)) {
				tc += "<br/>" + "<fieldset class=\"remindingField\">" + "<legend>"
						+ "<span class = \"reminding\">@提醒</span>" + "</legend>" + "<div class = \"reminding_2\">"
						+ theRemindedUserNames + "</div>" + "</fieldset>";
			}
			postTheme.setContent(tc);

			User_ updater = (User_) (ActionContext.getContext().getSession().get("loginUserInfo"));
			postService.updateTheme(isAdmin, isTheAuthor, section, isRock, isDelete, isGoodTheme, isToTop, postTheme,
					theRemindedUserNames, updater);
			return SUCCESS;
		} catch (Exception e) {
			if (!StringUtils.isEmpty(e.getMessage())) {
				errorMessage = "发生错误：" + e.getMessage() + "<br/><br/>"
						+ "本页面规则：题目不能超过250个字符，内容不能超过80000个字符，图片支持jpg，png格式。" + "请确认@提醒的用户名是否正确，多用户情况，中间要用英文“,”分开，"
						+ "每次最多输入10个要提醒的用户，如果想提醒更多用户查看，可以新开一贴提醒，" + "或发布帖子后，每次修改帖子，都可以再提醒10个用户";
				return "AddRemindingErrorAndThenToUpdateTheme";
			} else {
				e.printStackTrace();
				return "unknownError";
			}
		}
	}

	@SkipValidation
	public String uploadPostFile() {
		try {
			// 获取request,response
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			// 获取上传的upload目录 路径
			String basePath = ServletActionContext.getServletContext().getRealPath("/upload") + "/img/Post";

			// 获取用户信息
			Map<String, Object> user_session = ActionContext.getContext().getSession();
			// =================路径加工====================
			// 上传文件所属大板块短名
			String postRegionNameAbbr = "";
			// 小版块短名
			String postSectionNameAbbr = "";

			if (postTheme != null) {
				if (postTheme.getId() != 0) {
					postRegionNameAbbr = postService.getPostThemeById(postTheme.getId()).getSection()
							.getRegion_name_abbr();
					postSectionNameAbbr = postService.getPostThemeById(postTheme.getId()).getSection().getName_abbr();
					basePath += "/" + postRegionNameAbbr + "/" + postSectionNameAbbr;
				}
			}

			// ================文件名加工===============
			// 目标：大板块短名_小板块短名_t帖子主题id_r帖子回帖id_u用户id_原文件名
			String newFileName = "";

			// 加上所属主题帖的id
			String themeId = "";
			// 回帖的id
			String replyId = "";
			if (postTheme != null) {
				if (postTheme.getId() != 0) {
					themeId = "t" + postTheme.getId() + "_";
				}
			}
			if (postReply != null) {
				if (postReply.getId() != 0) {
					replyId = "r" + postReply.getId() + "_";
				}
			}
			// 加上用户id
			String uploadUserId = "";
			// 用于更新
			User_ user = new User_();
			if (user_session.get("loginUserInfo") == null) {
				// 游客
				user.setId(userService.getUserByName("visitors").getId());
				;
				uploadUserId = "visitors_";
			} else {
				// 一般用户
				user.setId(((User_) user_session.get("loginUserInfo")).getId());
				uploadUserId = "u" + ((User_) user_session.get("loginUserInfo")).getId() + "_";
			}
			newFileName = postRegionNameAbbr + "_" + postSectionNameAbbr + "_" + themeId + replyId + uploadUserId
					+ this.fileFileName;

			// 上传的文件对象
			File file = new File(basePath, newFileName);
			// 上传
			FileUtils.copyFile(this.file, file);

			// ===================更新用户上传的附件列表=================

			Attachment attachment = new Attachment();
			// 设置附件在磁盘的路径
			attachment.setPath(basePath + "/" + newFileName);
			// 获取url
			String url = request.getRequestURI();
			attachment.setUrl(url);
			user.getAttachments().add(attachment);
			// 更新user
			userService.updateUser(user, null, null, null);

			response.setContentType("text/html;charset=utf-8");
			// 返回图片地址
			response.getWriter()
					.println("upload/img/Post/" + postRegionNameAbbr + "/" + postSectionNameAbbr + "/" + newFileName);
		} catch (Exception e) {
			HttpServletResponse response2 = ServletActionContext.getResponse();
			try {
				response2.setContentType("text/html;charset=utf-8");
				response2.getWriter().println("上传错误，请检查图片是否符合要求");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return NONE;
	}

	@SkipValidation
	public String addPostZan() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		int[] zans = new int[] { -1, -1, -1 };// index 0 : 总赞数 index 1 : 主题赞数
												// index 2 ：特定楼层赞数
		User_ theAdmireUser = (User_) (ActionContext.getContext().getSession().get("loginUserInfo"));
		zans = postService.addZan(postTheme, postReply, theAdmireUser);
		StringBuilder message = new StringBuilder();
		message.append("{");
		message.append("\"totalZan\": \"全贴共收到赞 " + zans[0] + "\",");
		message.append("\"zan\": \"已赞 " + zans[1] + "\",");
		message.append("\"replyZan\": \"已赞 " + zans[2] + "\",");
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

	// ==============================getter and
	// setter===================================

	public List<Post_Reply> getPostReplys() {
		return postReplys;
	}

	public List<Post_Theme> getPostThemes() {
		return postThemes;
	}

	public void setPostThemes(List<Post_Theme> postThemes) {
		this.postThemes = postThemes;
	}

	public void setPostReplys(List<Post_Reply> postReplys) {
		this.postReplys = postReplys;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public Post_Theme getPostTheme() {
		return postTheme;
	}

	public void setPostTheme(Post_Theme postTheme) {
		this.postTheme = postTheme;
	}

	public Post_Reply getPostReply() {
		return postReply;
	}

	public void setPostReply(Post_Reply postReply) {
		this.postReply = postReply;
	}

	public User_ getAuthor() {
		return author;
	}

	public void setAuthor(User_ author) {
		this.author = author;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Post_Theme getPtc() {
		return ptc;
	}

	public void setPtc(Post_Theme ptc) {
		this.ptc = ptc;
	}

	public Post_Reply getPrc() {
		return prc;
	}

	public void setPrc(Post_Reply prc) {
		this.prc = prc;
	}

	public boolean getOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the checkedReplyIds
	 */
	public int[] getCheckedReplyIds() {
		return checkedReplyIds;
	}

	/**
	 * @param checkedReplyIds
	 *            the checkedReplyIds to set
	 */
	public void setCheckedReplyIds(int[] checkedReplyIds) {
		this.checkedReplyIds = checkedReplyIds;
	}

	/**
	 * @return the tf
	 */
	public Map<Boolean, String> getTf() {
		tf.put(true, "升序");
		tf.put(false, "降序");
		return tf;
	}

	/**
	 * @param tf
	 *            the tf to set
	 */
	public void setTf(Map<Boolean, String> tf) {
		this.tf = tf;
	}

	/**
	 * @return the saveReplyFlag
	 */
	public boolean getSaveReplyFlag() {
		return saveReplyFlag;
	}

	/**
	 * @param saveReplyFlag
	 *            the saveReplyFlag to set
	 */
	public void setSaveReplyFlag(boolean saveReplyFlag) {
		this.saveReplyFlag = saveReplyFlag;
	}

	/**
	 * @return the theRemindedUserNames
	 */
	public String getTheRemindedUserNames() {
		return theRemindedUserNames;
	}

	/**
	 * @param theRemindedUserNames
	 *            the theRemindedUserNames to set
	 */
	public void setTheRemindedUserNames(String theRemindedUserNames) {
		this.theRemindedUserNames = theRemindedUserNames;
	}

	/**
	 * @return the read
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}

	/**
	 * @return the reminding
	 */
	public Reminding getReminding() {
		return reminding;
	}

	/**
	 * @param reminding
	 *            the reminding to set
	 */
	public void setReminding(Reminding reminding) {
		this.reminding = reminding;
	}

	/**
	 * @return the themeAuthor
	 */
	public int[] getThemeAuthor() {
		return themeAuthor;
	}

	/**
	 * @param themeAuthor
	 *            the themeAuthor to set
	 */
	public void setThemeAuthor(int[] themeAuthor) {
		this.themeAuthor = themeAuthor;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the originalPostContent
	 */
	public String getOriginalPostContent() {
		return originalPostContent;
	}

	/**
	 * @param originalPostContent
	 *            the originalPostContent to set
	 */
	public void setOriginalPostContent(String originalPostContent) {
		this.originalPostContent = originalPostContent;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isTheAuthor
	 */
	public boolean getIsTheAuthor() {
		return isTheAuthor;
	}

	/**
	 * @param isTheAuthor
	 *            the isTheAuthor to set
	 */
	public void setIsTheAuthor(boolean isTheAuthor) {
		this.isTheAuthor = isTheAuthor;
	}

	/**
	 * @return the isRock
	 */
	public boolean getIsRock() {
		return isRock;
	}

	/**
	 * @param isRock
	 *            the isRock to set
	 */
	public void setIsRock(boolean isRock) {
		this.isRock = isRock;
	}

	/**
	 * @return the isDelete
	 */
	public boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the isGoodTheme
	 */
	public boolean getIsGoodTheme() {
		return isGoodTheme;
	}

	/**
	 * @param isGoodTheme
	 *            the isGoodTheme to set
	 */
	public void setIsGoodTheme(boolean isGoodTheme) {
		this.isGoodTheme = isGoodTheme;
	}

	/**
	 * @return the isToTop
	 */
	public boolean getIsToTop() {
		return isToTop;
	}

	/**
	 * @param isToTop
	 *            the isToTop to set
	 */
	public void setIsToTop(boolean isToTop) {
		this.isToTop = isToTop;
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
	 * @return the remindingsForUpdate
	 */
	public List<Reminding> getRemindingsForUpdate() {
		return remindingsForUpdate;
	}

	/**
	 * @param remindingsForUpdate
	 *            the remindingsForUpdate to set
	 */
	public void setRemindingsForUpdate(List<Reminding> remindingsForUpdate) {
		this.remindingsForUpdate = remindingsForUpdate;
	}

	/**
	 * @return the theRemindedUserNamesForUpdateJSP
	 */
	public String getTheRemindedUserNamesForUpdateJSP() {
		return theRemindedUserNamesForUpdateJSP;
	}

	/**
	 * @param theRemindedUserNamesForUpdateJSP
	 *            the theRemindedUserNamesForUpdateJSP to set
	 */
	public void setTheRemindedUserNamesForUpdateJSP(String theRemindedUserNamesForUpdateJSP) {
		this.theRemindedUserNamesForUpdateJSP = theRemindedUserNamesForUpdateJSP;
	}

	/**
	 * @return the thatIsGood
	 */
	public ThatIsGood getThatIsGood() {
		return thatIsGood;
	}

	/**
	 * @param thatIsGood
	 *            the thatIsGood to set
	 */
	public void setThatIsGood(ThatIsGood thatIsGood) {
		this.thatIsGood = thatIsGood;
	}

	/**
	 * @return the pttf
	 */
	public Map<Boolean, String> getPttf() {
		pttf.put(false, "按最后回复时间");
		pttf.put(true, "按发帖时间");
		return pttf;
	}

	/**
	 * @param pttf
	 *            the pttf to set
	 */
	public void setPttf(Map<Boolean, String> pttf) {
		this.pttf = pttf;
	}

	/**
	 * @return the topPostThemes
	 */
	public List<Post_Theme> getTopPostThemes() {
		return topPostThemes;
	}

	/**
	 * @param topPostThemes
	 *            the topPostThemes to set
	 */
	public void setTopPostThemes(List<Post_Theme> topPostThemes) {
		this.topPostThemes = topPostThemes;
	}

}
