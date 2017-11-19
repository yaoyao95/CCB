package com.CantoneseClubBBS.dao.post;

import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.util.PageModel;

public interface PostDao extends HibernateDao {

	/**
	 * 查询指定id的教学主题帖
	 * 
	 * @param id
	 * @return 持久化的指定id的教学主题帖
	 */
	Post_Theme getPostThemeById(int id);

	/**
	 * 添加回复
	 * 
	 * @return
	 */
	int saveTeachingPostReply(Post_Reply teachingPostReply);

	/****
	 * 添加教学主题
	 * 
	 * @param teachingPostTheme
	 * @return
	 */
	int saveTPT(Post_Theme teachingPostTheme);

	// =============new===========

	/**
	 * 查询指定id的主题帖，不要其回复贴
	 * 
	 * @param id
	 * @return
	 */
	Post_Theme getPTOnlyById(int id);

	/**
	 * 查询指定主题帖的回帖内容,多条件，分页。
	 * 
	 * @param id
	 * @param pageModel
	 * @param prc
	 * @param order
	 *            order true,按回帖时间升序，先旧后新，false 按回帖时间降序 先新后旧
	 * @return
	 */
	List<Post_Reply> getPRsByPT_idByPage(int id, PageModel pageModel, Post_Reply prc, boolean order);

	/**
	 * （多条件）分页查询所有主题帖子
	 * 
	 * @param pageModel
	 * @param ptc
	 * @param section
	 * @param isDelete
	 * @return 所有主题帖子
	 */
	List<Post_Theme> getPostThemesByPage(PageModel pageModel, Post_Theme ptc, Section section, boolean isDelete,
			boolean order);

	/**
	 * 查询指定ids的回帖
	 * 
	 * @param checkedReplyIds
	 * @return
	 */
	List<Post_Reply> getPostReplysByIds(int[] checkedReplyIds);

	/**
	 * 给主题或回帖添加赞
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUserName
	 * @param theAdmireUser
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	int[] addZan(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser, String theAdmireUserName);

	/**
	 * 检查是否赞过主题帖或回帖
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUser
	 * @return true: 已添加，false未添加
	 */
	boolean checkZanAdded(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser);

	/**
	 * 返回赞的个数
	 * 
	 * @param postTheme
	 * @param postReply
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	int[] getZanNum(Post_Theme postTheme, Post_Reply postReply);
	//
	// int recordCount();
	//
	// List<Notice> getNoticesByPage(PageModel pageModel, Notice notice);
	//
	// Notice getNoticeById(int id);
	//
	// void updateNotice(Notice notice);

}
