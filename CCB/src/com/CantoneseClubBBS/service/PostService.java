package com.CantoneseClubBBS.service;

import java.util.List;

import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.util.PageModel;

public interface PostService {

	/**
	 * 查询指定id的主题帖
	 * 
	 * @param id
	 * @return 指定id的主题帖
	 */
	Post_Theme getPostThemeById(int id);

	/**
	 * 查询指定id的主题帖，不要其回复贴
	 * 
	 * @param id
	 * @return
	 */
	Post_Theme getPostThemeOnlyById(int id);

	/**
	 * 查询指定主题帖的回帖内容,多条件，分页。
	 * 
	 * @param id
	 * @param pageModel
	 * @param prc
	 * @param order
	 * @return
	 */
	List<Post_Reply> getPRsByPT_idByPage(int id, PageModel pageModel, Post_Reply prc, boolean order);

	/**
	 * （多条件）分页查询主题帖子
	 * 
	 * @param pageModel
	 * @param ptc
	 * @param section
	 * @param order
	 * @return
	 */
	List<Post_Theme> getPostThemesByPage(PageModel pageModel, Post_Theme ptc, Section section, boolean order);

	/**
	 * 添加回复帖
	 * 
	 * @param postReply
	 * @param postTheme
	 * @param author
	 */
	int savePostReply(Post_Reply postReply, Post_Theme postTheme, User_ author);

	/**
	 * 添加主题
	 * 
	 * @param postTheme
	 * @param u
	 * @param section
	 * @return
	 */
	int savePT(Post_Theme postTheme, User_ u, Section section);

	/**
	 * 查询指定ids的回帖
	 * 
	 * @param checkedReplyIds
	 * @return
	 */
	List<Post_Reply> getPostReplysByIds(int[] checkedReplyIds);

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
	int addThemeReply(Post_Reply postReply, Post_Theme postTheme, User_ author, String theRemindedUserNames,
			int theThemeAuthor, int theRemindingUserId);

	/**
	 * @param theRemindedUserNames
	 * @param themeAuthor
	 * @param theRemindingUserId
	 * @param theRemindedPostThemeId
	 * @param theRemindedPostReplyId
	 * @return
	 */
	boolean addReminds(String theRemindedUserNames, int themeAuthor, int theRemindingUserId, int theRemindedPostThemeId,
			int theRemindedPostReplyId);

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
	 * @param isToTop
	 * @param postTheme
	 * @param theRemindedUserNames
	 * @param updater
	 */
	void updateTheme(boolean isAdmin, boolean isTheAuthor, Section section, boolean isRock, boolean isDelete,
			boolean isGoodTheme, boolean isToTop, Post_Theme postTheme, String theRemindedUserNames, User_ updater);

	/**
	 * 给主题或回帖添加赞
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUser
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	int[] addZan(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser);

	/**
	 * 设置赞已读
	 * 
	 * @param id
	 */
	void setThatIsGoodRead(int id);

	/**
	 * 检查私人日记是否能查看
	 * 
	 * @param postTheme
	 * @param theLoginUser
	 * @return
	 */
	boolean checkPersonalPost(Post_Theme postTheme, User_ theLoginUser);

}
