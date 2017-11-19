package com.CantoneseClubBBS.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.post.PostDao;
import com.CantoneseClubBBS.dao.remind.RemindDao;
import com.CantoneseClubBBS.dao.section.SectionDao;
import com.CantoneseClubBBS.dao.user.UserDao;
import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.service.PostService;
import com.CantoneseClubBBS.util.PageModel;

@Service("postService")

// 加上事务，这个类的所有方法都会生效：
// 方法运行过程中遇到RuntimeException就会对数据库进行rollback，返回到调用方法前的状态
// 事务管理的作用，@transaction管理了session的生命周期，在使用@transaction声明的方法，会在方法执行前后执行执行Aop，
// 在方法前获取现有或new 一个session，方法执行完后会关闭session。
// 具体原理如下
/*
 * =============================================================================
 * ==============================
 * org.springframework.transaction.interceptor.TransactionAspectSupport
 * (invokeWithinTransaction(invocation) { 1.tranInfo =
 * createTransactionIfNecessary(); 2．transactionManager.doBegin(tranInfo); //
 * 包含在源码createTransactionIfNecessary（）中,这里拎出来单说 invocation.proceed();
 * 3.transactionManager.doCommit(tranInfo); //
 * 包含在源码commitTransactionAfterReturning（）中 4.
 * transactionManager.doCleanupAfterCompletion(tranInfo); //
 * 包含在源码commitTransactionAfterReturning（）中 }
 * 上面transactionManager是类HibernateTransactionManager的实例，且doBegin,
 * doCommit与doCleanupAfterCompletion为类中的三个方法。 上面伪代码中的四行代码对session的生命周期至关重要。 1.
 * createTransactionIfNecessary方法会创建一个包装好的transaction，它包含了所有transaction所需要的信息。
 * 但它不是UserTransaction和JDBC transaction，所以它只是一个虚拟的transaction实例。
 * transaction实例会查看是否已经存在session或者connection，如果有，则将其存入自己的域中。 2.
 * 第一步中生成的transaction是否已经存在session, 如果已经存在则跳过。
 * 如果没有，真正调用SessionFactory的openSession方法，flushMode设为AUTO。 3. 先调用session.flush(),
 * 再调用session.commit(). 4. 释放connections。如果session是在第2步中新创建的，则执行session.close()
 * 或者注册一个deffer close（OpenViewInFilter中的isSingleSession=false），否则什么也不做。
 * =============================================================================
 * =================================
 */

@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class PostServiceImpl implements PostService {
	@Resource(name = "postDao")
	private PostDao postDao;
	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "sectionDao")
	private SectionDao sectionDao;
	@Resource(name = "remindDao")
	private RemindDao remindDao;

	/**
	 * （多条件）分页查询教学主题帖子
	 * 
	 * @param pageModel
	 * @param ptc
	 * @param section
	 * @param
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Post_Theme> getPostThemesByPage(PageModel pageModel, Post_Theme ptc, Section section, boolean order) {
		return postDao.getPostThemesByPage(pageModel, ptc, section, false, order);

	}

	/**
	 * 查询指定id的主题帖
	 * 
	 * @param id
	 * @return 指定id的主题帖
	 */
	@Override
	public Post_Theme getPostThemeById(int id) {
		return postDao.getPostThemeById(id);
	}

	/**
	 * 添加回复帖
	 * 
	 * @param postReply
	 * @param postTheme
	 * @param user_
	 */
	@Override
	public int savePostReply(Post_Reply postReply, Post_Theme postTheme, User_ user_) {
		// 查询获取所属主题帖，
		Post_Theme pt = postDao.getPostThemeById(postTheme.getId());

		// 完善帖子内容
		// 添加时间
		postReply.setIssuedDate(new Date());
		// 添加楼层数
		postReply.setStorey(pt.getReply_number() + 1);

		// =====设置主题帖======
		if (user_ != null) {
			if (user_.getId() != 0) {
				// 非游客
				pt.setThe_last_reply_user_name(userDao.getUserbyId(user_.getId()).getName());
			}
		} else {
			// 游客
			pt.setThe_last_reply_user_name("游客");
		}
		pt.setReply_number(pt.getReply_number() + 1);
		pt.setThe_last_reply_time(new Date());
		postReply.setPost_Theme(pt);

		// 添加回帖作者
		if (user_ != null) {
			if (user_.getId() != 0) {
				// 非游客
				postReply.setUser_(userDao.getUserbyId(user_.getId()));
			} else {
				// 游客
				postReply.setUser_(userDao.getUserByName("visitors"));
			}
		} else {
			// 游客
			postReply.setUser_(userDao.getUserByName("visitors"));
		}

		return postDao.saveTeachingPostReply(postReply);
	}

	/**
	 * 查询指定id的主题帖，不要其回复贴
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Post_Theme getPostThemeOnlyById(int id) {
		return postDao.getPTOnlyById(id);
	}

	/**
	 * 查询指定教学主题帖的回帖内容,多条件，分页。
	 * 
	 * @param id
	 * @param pageModel
	 * @param prc
	 * @param order
	 * @return
	 */
	public List<Post_Reply> getPRsByPT_idByPage(int id, PageModel pageModel, Post_Reply prc, boolean order) {
		return postDao.getPRsByPT_idByPage(id, pageModel, prc, order);
	}

	/**
	 * 添加主题
	 * 
	 * @param postTheme
	 * @param u
	 * @param section
	 */
	@Override
	public int savePT(Post_Theme postTheme, User_ u, Section section) {
		// 完善帖子内容

		// 添加时间
		postTheme.setIssuedDate(new Date());

		// 查询获得回帖作者 持久化的
		u = userDao.getUserbyId(u.getId());
		// 添加回帖作者
		postTheme.setUser_(u);

		// 查询section，持久化的
		section = sectionDao.getSectionByName(section.getName());
		// 添加所属板块
		postTheme.setSection(section);
		// 添加主题帖
		return postDao.saveTPT(postTheme);
	}

	/**
	 * 查询指定ids的回帖
	 * 
	 * @param checkedReplyIds
	 * @return
	 */
	@Override
	public List<Post_Reply> getPostReplysByIds(int[] checkedReplyIds) {
		return postDao.getPostReplysByIds(checkedReplyIds);
	}

	/**
	 * 添加回帖(并处理@提醒)
	 * 
	 * @param postReply
	 * @param postTheme
	 * @param author
	 * @param theRemindedUserNames
	 * @param theThemeAuthor
	 * @param theRemindingUserId
	 * @return
	 */
	@Override
	public int addThemeReply(Post_Reply postReply, Post_Theme postTheme, User_ author, String theRemindedUserNames,
			int theThemeAuthor, int theRemindingUserId) {
		postReply.setId(savePostReply(postReply, postTheme, author));
		if (StringUtils.isEmpty(theRemindedUserNames) == false || theThemeAuthor != 0) {
			// throw new RuntimeException("@提醒超出10个用户");
			addReminds(theRemindedUserNames, theThemeAuthor, theRemindingUserId, postTheme.getId(), postReply.getId());
		}
		return postReply.getId();
	}

	/**
	 * 添加提醒
	 * 
	 * @param theRemindedUserNames
	 * @param themeAuthor
	 * @param theRemindedPostReplyId
	 * @param theRemindedPostThemeId
	 * @param theRemindingUserId
	 * @return true 添加提醒成功，false表示无需添加提醒
	 */
	@Override
	public boolean addReminds(String theRemindedUserNames, int themeAuthor, int theRemindingUserId,
			int theRemindedPostThemeId, int theRemindedPostReplyId) {

		if (theRemindedUserNames.equals("visitors")) {// 不用提醒visitors
			theRemindedUserNames = "";
		}

		if ((theRemindedUserNames.split(",")).length > 10) {// 超出10人
			throw new RuntimeException("@提醒超出10个用户");// for rollback
		}
		;

		if (themeAuthor != 0) {// 提醒楼主
			String themeAuthorName = postDao.getPTOnlyById(themeAuthor).getUser_().getName();
			int index = theRemindedUserNames.lastIndexOf(",");
			if (index == -1) {// 不存在“ , ” 说明提醒人为1个或2个
				if (theRemindedUserNames.equals("")) {// 1个提醒人(楼主)
					theRemindedUserNames = themeAuthorName + ",";
				} else {// 2个提醒人
					theRemindedUserNames += "," + themeAuthorName + ",";
				}
			} else {// 有2个或以上提醒人
				theRemindedUserNames += "," + themeAuthorName + ",";
			}
		} else {// 不提醒楼主
			if (StringUtils.isEmpty(theRemindedUserNames)) {
				return false;// 0个提醒人
			} else if (theRemindedUserNames.indexOf(",") == -1) {
				theRemindedUserNames += ",";// 1个提醒人
			} else {// 有2个或以上提醒人
				theRemindedUserNames += ",";
			}
		}

		// 发出提醒的用户
		if (theRemindingUserId == 0) {
			theRemindingUserId = userDao.getUserByName("visitors").getId();
		}
		User_ theRemindingUser = userDao.getUserbyId(theRemindingUserId);
		int fromIndex = 0;
		while (theRemindedUserNames.indexOf(",", fromIndex) != -1) {// 1个提醒人时，只执行一次，不循环。2个或以上提醒人时，需要重复循环
			Reminding r = new Reminding();
			int targetIndexStar = 0;
			int targetIndexEnd = 0;
			String theRemindedUserName = "";
			targetIndexStar = fromIndex;
			targetIndexEnd = theRemindedUserNames.indexOf(",", fromIndex);
			/** 被提醒人的名字 */
			theRemindedUserName = theRemindedUserNames.substring(targetIndexStar, targetIndexEnd);
			fromIndex = targetIndexEnd + 1;

			User_ theRemindedUser = null;
			theRemindedUser = userDao.getUserByName(theRemindedUserName);

			if (theRemindedUser == null) {
				// 查找不到user ，即不用添加提醒，也许是用户名出错了。
				throw new RuntimeException("@提醒的用户不存在，请查证后再试");// for rollback
			}

			// 完善提醒的内容
			// 被提醒的用户
			r.setRemindedUserName(theRemindedUserName);
			r.setRemindedUser(theRemindedUser);

			// 发出提醒的用户
			r.setRemindingUser(theRemindingUser);
			r.setRemindingUserName(theRemindingUser.getName());

			// 时间
			r.setIssuedDate(new Date());

			// 帖子
			Post_Reply theRemindedPostReply = null;
			if (theRemindedPostReplyId != -1) {// 如果是在回帖里提醒
				int[] tRPRI = { theRemindedPostReplyId };
				theRemindedPostReply = postDao.getPostReplysByIds(tRPRI).get(0);
				r.setTheRemindedPostReply(theRemindedPostReply);
			}
			r.setTheRemindedPostTheme(postDao.getPTOnlyById(theRemindedPostThemeId));

			// 链接
			StringBuilder href = new StringBuilder();
			// 保存从表reminding
			int savedReminding = (int) remindDao.save(r);
			if (theRemindedPostReplyId != -1) {
				href.append("post/getPTByIdByPage?pageModel.pageIndex=");
				int pageIndex = (int) Math.ceil(1.0 * theRemindedPostReply.getStorey() / 10);
				href.append(pageIndex + "&pageModel.pageSize=10&postTheme.id=" + theRemindedPostThemeId
						+ "&order=true&flag=true&reminding.id=" + savedReminding + "&read=true#"
						+ theRemindedPostReplyId);
			} else {
				href.append("post/getPTByIdByPage.action?postTheme.id=" + theRemindedPostThemeId + "&reminding.id="
						+ savedReminding + "&read=true");
			}
			r.setHref(href.toString());

		}
		return true;
	}

	/**
	 * 更新主题帖
	 * 
	 * @param isAdmin
	 * @param isTheAuthor
	 * @param section
	 * @param isRock
	 * @param isCancelRock
	 * @param isDelete
	 * @param isGoodTheme
	 * @param postTheme
	 * @param theRemindedUserNames
	 * @param updater
	 */
	@Override
	public void updateTheme(boolean isAdmin, boolean isTheAuthor, Section section, boolean isRock, boolean isDelete,
			boolean isGoodTheme, boolean isToTop, Post_Theme postTheme, String theRemindedUserNames, User_ updater) {
		Post_Theme postThemeToUpdate = postDao.getPTOnlyById(postTheme.getId());
		if (isAdmin) {
			// ===板块====
			if (section != null) {
				if (!section.getName().equals(postThemeToUpdate.getSection().getName())) {
					Section s = sectionDao.getSectionByName(section.getName());
					postThemeToUpdate.setSection(s);
					postThemeToUpdate.setIsChangeSection(1);
					postThemeToUpdate.setIsChangeSectionDate(new Date());
					postThemeToUpdate.setSetIsChangeSectionAdminName(updater.getName());
				}
			}

			// ===========锁=================
			if (isRock) {// 需要锁
				if (postThemeToUpdate.getIsRock() == 0 || postThemeToUpdate.getIsRock() == 2) {// 原来没被锁或取消被锁
					postThemeToUpdate.setIsRock(1);
					postThemeToUpdate.setIsRockDate(new Date());
					postThemeToUpdate.setSetIsRockAdminName(updater.getName());
				}
			} else {// 需要解锁
				if (postThemeToUpdate.getIsRock() == 1) {// 原来被锁
					postThemeToUpdate.setIsRock(2);
					postThemeToUpdate.setIsRockDate(new Date());
					postThemeToUpdate.setSetIsRockAdminName(updater.getName());
				}
			}

			// ==========删除================
			if (isDelete) {// 需要删除
				if (postThemeToUpdate.getIsDelete() == 0 || postThemeToUpdate.getIsDelete() == 2) {
					postThemeToUpdate.setIsDelete(1);
					postThemeToUpdate.setIsDeleteDate(new Date());
					postThemeToUpdate.setSetIsDeleteAdminName(updater.getName());
				}
			} else {
				if (postThemeToUpdate.getIsDelete() == 1) {// 需要解除删除
					postThemeToUpdate.setIsDelete(2);
					postThemeToUpdate.setIsDeleteDate(new Date());
					postThemeToUpdate.setSetIsDeleteAdminName(updater.getName());
				}
			}

			// ==========优秀帖===============
			if (isGoodTheme) {// 设置为优秀帖
				if (postThemeToUpdate.getIsGoodTheme() == 0 || postThemeToUpdate.getIsGoodTheme() == 2) {
					postThemeToUpdate.setIsGoodTheme(1);
					postThemeToUpdate.setIsGoodThemeDate(new Date());
					postThemeToUpdate.setSetIsGoodThemeAdminName(updater.getName());
				}
			} else {// 取消设置为优秀帖
				if (postThemeToUpdate.getIsGoodTheme() == 1) {
					postThemeToUpdate.setIsGoodTheme(2);
					postThemeToUpdate.setIsGoodThemeDate(new Date());
					postThemeToUpdate.setSetIsGoodThemeAdminName(updater.getName());
				}
			}

			// ==========置顶=================
			if (isToTop) {// 需要置顶
				if (postThemeToUpdate.getIsToTop() == 0 || postThemeToUpdate.getIsToTop() == 2) {
					postThemeToUpdate.setIsToTop(1);
					postThemeToUpdate.setIsToTopDate(new Date());
					postThemeToUpdate.setSetIsToTopAdminName(updater.getName());
				}
			} else {// 取消置顶
				if (postThemeToUpdate.getIsToTop() == 1) {
					postThemeToUpdate.setIsToTop(2);
					postThemeToUpdate.setIsToTopDate(new Date());
					postThemeToUpdate.setSetIsToTopAdminName(updater.getName());
				}
			}
		}

		if (isTheAuthor) {
			if (postTheme != null) {
				// ====题目====
				if (!postTheme.getTitle().equals(postThemeToUpdate.getTitle())) {
					postThemeToUpdate.setTitle(postTheme.getTitle());
					postThemeToUpdate.setUpdateDate(new Date());
				}

				// ====内容
				if (!postTheme.getContent().equals(postThemeToUpdate.getContent())) {
					postThemeToUpdate.setContent(postTheme.getContent());
					postThemeToUpdate.setUpdateDate(new Date());
				}

				// ===========新增@提醒
				if (!StringUtils.isEmpty(theRemindedUserNames)) {
					addReminds(theRemindedUserNames, 0, postThemeToUpdate.getUser_().getId(), postThemeToUpdate.getId(),
							-1);
				}
			}
		}

		// 更新日期
		postThemeToUpdate.setUpdateDate(new Date());
	}

	/**
	 * 给主题或回帖添加赞
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUser
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	@Override
	public int[] addZan(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser) {
		theAdmireUser = userDao.getUserbyId(theAdmireUser.getId());
		String theAdmireUserName = theAdmireUser.getName();
		return postDao.addZan(postTheme, postReply, theAdmireUser, theAdmireUserName);
	}

	/** 设置赞已读 */
	@Override
	public void setThatIsGoodRead(int id) {
		ThatIsGood t = postDao.get(ThatIsGood.class, id);
		t.setIsRead(1);
		postDao.update(t);
	}

	/**
	 * 检查私人日记是否能查看
	 * 
	 * @param postTheme
	 * @param theLoginUser
	 * @return
	 */
	@Override
	public boolean checkPersonalPost(Post_Theme postTheme, User_ theLoginUser) {
		// 如果是私人日记，//本人或水区管理员、总管理员能查看
		if (postTheme.getSection().getName().equals("私人日记")) {
			if (theLoginUser == null) {
				return false;// 未登录
			} else if ( theLoginUser.getId() == postTheme.getUser_().getId()) {
				return true;// 作者
				//如果加上theLoginUser.getAuthority().charAt(8) == 'Y' || theLoginUser.getAuthority().charAt(0) == 'Y'
				//||
				//总管理员或水区管理员或者
			} else {
				return false;// 非作者总管理员或水区管理员
			}
		} else {// 非私人日记
			return true;
		}

	}

}


