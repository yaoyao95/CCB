package com.CantoneseClubBBS.dao.post.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.post.PostDao;
import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.util.PageModel;

@Repository("postDao")
public class PostDaoImpl extends HibernateDaoImpl implements PostDao {

	/**
	 * （多条件）分页查询所有主题帖子
	 * 
	 * @param pageModel
	 * @param ptc
	 * @param section
	 * @param isDelete
	 * @return 所有主题帖子
	 */
	@Override
	public List<Post_Theme> getPostThemesByPage(PageModel pageModel, Post_Theme ptc, Section section, boolean isDelete,
			boolean order) {
		// 定义hql语句
		StringBuilder hql = new StringBuilder();
		hql.append("select pt from Post_Theme as pt where 1=1");

		if (isDelete) {// 查找被删除的
			hql.append(" and pt.isDelete=1");
		} else {// 查找没有被删除的
			hql.append(" and pt.isDelete in(0,2)");
		}

		// 定义集合封装查询参数
		List<Object> params = new ArrayList<Object>();
		// 判断条件ptc时候存在
		if (ptc != null) {
			// 判断标题条件是否存在
			if (!StringUtils.isEmpty(ptc.getTitle())) {
				// 加上标题条件
				hql.append(" and pt.title like ?");
				params.add("%" + ptc.getTitle() + "%");
			}
			// 判断内容条件是否存在
			if (!StringUtils.isEmpty(ptc.getContent())) {
				// 加上内容条件
				hql.append(" and pt.content like ?");
				params.add("%" + ptc.getContent() + "%");
			}
			// 判断置顶条件是否存在
			if (ptc.getIsToTop() == 1) {
				hql.append(" and pt.isToTop = 1");
			}
			// 判断优秀帖条件是否存在
			if (ptc.getIsGoodTheme() == 1) {
				hql.append(" and pt.isGoodTheme = 1");
			}
			if (ptc.getTotalThatIsGoodNum() == -2) {// 一个月内
				Calendar oneMonthAgo = Calendar.getInstance();
				oneMonthAgo.add(Calendar.DAY_OF_YEAR, -30);
				Date oma = oneMonthAgo.getTime();
				hql.append(" and pt.issuedDate > ?");
				params.add(oma);
			}
			if (ptc.getTotalThatIsGoodNum() == -3) {// 一周内
				Calendar oneMonthAgo = Calendar.getInstance();
				oneMonthAgo.add(Calendar.DAY_OF_YEAR, -7);
				Date oma = oneMonthAgo.getTime();
				hql.append(" and pt.issuedDate > ?");
				params.add(oma);
			}
			if (ptc.getTotalThatIsGoodNum() == -4) {// 一天内
				Calendar oneMonthAgo = Calendar.getInstance();
				oneMonthAgo.add(Calendar.DAY_OF_YEAR, -1);
				Date oma = oneMonthAgo.getTime();
				hql.append(" and pt.issuedDate > ?");
				params.add(oma);
			}
		}

		// 判断section条件是否存在
		if (section != null) {
			// 判断大板块条件是否存在
			if (!StringUtils.isEmpty(section.getRegion_name_abbr())) {
				hql.append(" and pt.section.name in (select s.name from Section as s " + "where s.region_name_abbr = '"
						+ section.getRegion_name_abbr() + "')");
			}
			// 判断小板块条件是否存在
			if (!StringUtils.isEmpty(section.getName()) && !section.getName().equals("请选择")) {
				hql.append(" and pt.section.name = ?");
				params.add(section.getName());
			}
		}

		// 其他条件.....

		// =============排序条件===================
		if (ptc != null) {
			// 判断被赞数条件是否存在
			if (ptc.getTotalThatIsGoodNum() == -1 || ptc.getTotalThatIsGoodNum() == -2
					|| ptc.getTotalThatIsGoodNum() == -3 || ptc.getTotalThatIsGoodNum() == -4) {// 被赞条件存在
				hql.append(" order by pt.totalThatIsGoodNum desc");
				if (order == false) {// 按最后回复时间排序
					hql.append(", pt.the_last_reply_time desc");
				} else {// 按发表时间排序
					hql.append(", pt.issuedDate desc");
				}
			} else {// 被赞条件不存在
				if (order == false) {// 按最后回复时间排序
					hql.append(" order by pt.the_last_reply_time desc");
				} else {// 按发表时间排序
					hql.append(" order by pt.issuedDate desc");
				}
			}

		} else {// ptc == null 没有其他排序条件
			if (order == false) {// 按最后回复时间排序和发帖时间的最大值排序
				hql.append(" order by case when pt.the_last_reply_time is null then"
						+ " pt.issuedDate else pt.the_last_reply_time end desc");
			} else {// 按发表时间排序
				hql.append(" order by pt.issuedDate desc");
			}
		}

		List<Post_Theme> pts = this.findByPage(hql.toString(), pageModel, params);
		if (pts != null) {
			if (pts.size() > 0) {
				for (int i = 0; i < pts.size(); i++) {
					pts.get(i).getUser_().getName(); // 解除延迟加载

					// 当前时间
					Calendar c = Calendar.getInstance();
					// 一天前
					c.add(Calendar.DAY_OF_MONTH, -1);
					// 发布时间
					Calendar postThemeDate = Calendar.getInstance();
					postThemeDate.setTime(pts.get(i).getIssuedDate());

					if (postThemeDate.compareTo(c) > 0) {// 一天以内的主题帖
						pts.get(i).setIsNew(1);
					}
					// 最后回复时间
					Calendar postThemeLastReplyDate = Calendar.getInstance();
					if (pts.get(i).getThe_last_reply_time() != null) {
						postThemeLastReplyDate.setTime(pts.get(i).getThe_last_reply_time());

						if (postThemeLastReplyDate.compareTo(c) > 0) {// 一天以内的回复贴
							pts.get(i).setIsReplyNew(1);
						}
					}
				}
			}
		}

		return pts;
	}

	/**
	 * 查询指定id的主题帖
	 * 
	 * @param id
	 * @return 指定持久化的id的主题帖
	 */
	@Override
	public Post_Theme getPostThemeById(int id) {
		Post_Theme pt = this.get(Post_Theme.class, id);
		if (pt.getIsDelete() == 1) {
			throw new RuntimeException("该帖子已被删除");
		}
		pt.getUser_().getName();// 访问作者的name，使lazy加载策略现在加载。
		if (pt.getPost_Replys() != null) {
			for (Post_Reply eachPr : pt.getPost_Replys()) {
				if (!StringUtils.isEmpty(eachPr.getUser_())) {
					eachPr.getUser_().getName(); // 使lazy加载策略现在加载。 加载所有回帖的所有user
				}
			}
		}
		return pt;
	}

	/**
	 * 添加回复
	 * 
	 * @return
	 */
	@Override
	public int saveTeachingPostReply(Post_Reply teachingPostReply) {
		return (int) this.save(teachingPostReply);

	}

	/**
	 * 查询指定id的主题帖，不要其回复贴
	 * 
	 * @param id
	 * @return
	 */
	public Post_Theme getPTOnlyById(int id) {
		Post_Theme pt = this.get(Post_Theme.class, id);
		if (pt.getIsDelete() == 1) {
			throw new RuntimeException("该帖子已被删除");
		}
		pt.getUser_().getName();// 访问作者的name，使lazy加载策略现在加载。
		pt.getSection().getName();
		return pt;
	}

	/**
	 * 查询指定教学主题帖的回帖内容,多条件，分页。
	 * 
	 * @param id
	 * @param pageModel
	 * @param prc
	 * @param order
	 *            order true,按回帖时间升序，先旧后新，false 按回帖时间降序 先新后旧
	 * @return
	 */
	public List<Post_Reply> getPRsByPT_idByPage(int id, PageModel pageModel, Post_Reply prc, boolean order) {
		// 定义hql语句
		StringBuilder hql = new StringBuilder();
		// 定义集合封装查询参数
		List<String> params = new ArrayList<String>();

		hql.append("select pr from Post_Reply as pr where pr.post_Theme.id =" + id);

		// ================查询条件=========================
		// 判断查询条件是否存在
		if (!StringUtils.isEmpty(prc)) {
			// 判断内容查询条件是否存在
			if (!StringUtils.isEmpty(prc.getContent())) {
				// 存在
				hql.append(" and pr.content like ?");
				params.add("%" + prc.getContent() + "%");
			}
			// 判断用户名查询条件是否存在
			if (prc.getUser_().getName() != null) {
				// 存在
				if (prc.getUser_().getName().equals("游客")) {
					hql.append(" and pr.user_.name = 'visitors'");
				} else {
					hql.append(" and pr.user_.name like ?");
					params.add("%" + prc.getUser_().getName() + "%");
				}
			}
		}

		// =============排序条件===================
		if (order == false) { // order = true,按回帖时间升序，先旧后新，=false 按回帖时间降序 先新后旧
			hql.append(" order by pr.storey desc");// 高楼到低楼
		} else {// true
			hql.append(" order by pr.storey asc");// 低楼到高楼
		}

		List<Post_Reply> prs = this.findByPage(hql.toString(), pageModel, params);

		for (Post_Reply pr : prs) {
			if (!StringUtils.isEmpty(pr.getUser_())) {
				pr.getUser_().getName();// 访问作者的name，使lazy加载策略现在加载。
			}
		}

		// 设置主题浏览数
		Post_Theme pt = (Post_Theme) this.getPostThemeById(id);
		pt.setBrowse_number(pt.getBrowse_number() + 1);

		return prs;
	}

	/****
	 * 添加教学主题
	 * 
	 * @param teachingPostTheme
	 */
	@Override
	public int saveTPT(Post_Theme teachingPostTheme) {
		return (int) this.save(teachingPostTheme);
	}

	/**
	 * 查询指定ids的回帖
	 * 
	 * @param checkedReplyIds
	 * @return
	 */
	@Override
	public List<Post_Reply> getPostReplysByIds(int[] checkedReplyIds) {
		String s = "(";
		for (int i = 0; i < checkedReplyIds.length - 1; i++) {
			s += checkedReplyIds[i] + ",";
		}
		s += checkedReplyIds[checkedReplyIds.length - 1] + ")";
		List<Post_Reply> prs = this.find("select p from Post_Reply as p where p.id in " + s);
		// 解除延迟加载
		for (Post_Reply pr : prs) {
			pr.getUser_().getName();
		}
		return prs;
	}

	/**
	 * 给主题或回帖添加赞
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUserName
	 * @param theAdmireUser
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	@Override
	public int[] addZan(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser, String theAdmireUserName) {
		if (postTheme != null) {
			if (checkZanAdded(postTheme, postReply, theAdmireUser)) {// 已赞过了
				return new int[] { -1, -1, -1 };
			}
			Post_Theme thePostTheme = this.getPTOnlyById(postTheme.getId());// 主表持久化
			ThatIsGood tis = new ThatIsGood();
			tis.setIsRead(0);
			tis.setIssuedDate(new Date());
			tis.setTheAdmireUser(theAdmireUser);
			tis.setTheAdmireUserName(theAdmireUserName);
			tis.setThePostTheme(thePostTheme);
			StringBuilder href = new StringBuilder();
			if (postReply != null) {// 赞了回帖
				List<Post_Reply> thePostReply = this.getPostReplysByIds(new int[] { postReply.getId() });// 主表持久化
				if (thePostReply.get(0).getUser_().getName().equals(theAdmireUserName)) {// 自己赞自己，不能
					return new int[] { -1, -1, -1 };
				}
				tis.setTheAdmiredUser(thePostReply.get(0).getUser_());
				tis.setTheAdmiredUserName(thePostReply.get(0).getUser_().getName());
				tis.setThPostReply(thePostReply.get(0));
				int savedThatIsGoodId = (int) this.save(tis);// 从表持久化
				thePostReply.get(0).setThatIsGoodNum(getZanNum(postTheme, postReply)[2]);
				thePostTheme.setTotalThatIsGoodNum(getZanNum(postTheme, postReply)[0]);
				href.append("post/getPTByIdByPage?pageModel.pageIndex=");
				int pageIndex = (int) Math.ceil(1.0 * thePostReply.get(0).getStorey() / 10);
				href.append(pageIndex + "&pageModel.pageSize=10&postTheme.id=" + thePostTheme.getId()
						+ "&order=true&flag=true&thatIsGood.id=" + savedThatIsGoodId + "&read=true#"
						+ thePostReply.get(0).getId());
			} else {// 赞了主题帖
				if (thePostTheme.getUser_().getName().equals(theAdmireUserName)) {
					return new int[] { -1, -1, -1 };// 自己赞自己，不能
				}
				tis.setTheAdmiredUser(thePostTheme.getUser_());
				tis.setTheAdmiredUserName(thePostTheme.getUser_().getName());
				int savedThatIsGoodId = (int) this.save(tis);// 从表持久化
				href.append("post/getPTByIdByPage.action?postTheme.id=" + thePostTheme.getId() + "&thatIsGood.id="
						+ savedThatIsGoodId + "&read=true");
				thePostTheme.setThatIsGoodNum(getZanNum(postTheme, postReply)[1]);
				thePostTheme.setTotalThatIsGoodNum(getZanNum(postTheme, postReply)[0]);
			}
			tis.setHref(href.toString());
		}
		return this.getZanNum(postTheme, postReply);
	}

	/**
	 * 检查是否赞过主题帖或回帖
	 * 
	 * @param postTheme
	 * @param postReply
	 * @param theAdmireUser
	 * @return true: 已添加，false未添加
	 */
	public boolean checkZanAdded(Post_Theme postTheme, Post_Reply postReply, User_ theAdmireUser) {
		if (postTheme != null) {
			if (postReply != null) {// 赞的是回帖
				if (this.findUniqueEntity("select t from ThatIsGood as t where t.thPostReply.id=" + postReply.getId()
						+ " and t.theAdmireUserName='" + theAdmireUser.getName() + "'") == null) {
					return false;
				} else {
					return true;
				}
			} else {// 赞的是主题帖
				if (this.findUniqueEntity("select t from ThatIsGood as t where t.thePostTheme=" + postTheme.getId()
						+ " and t.thPostReply.id = null" + " and t.theAdmireUserName='" + theAdmireUser.getName()
						+ "'") == null) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 返回赞的个数
	 * 
	 * @param postTheme
	 * @param postReply
	 * @return int[3] index 0 : 总赞数 index 1 : 主题赞数 index 2 ：特定楼层赞数
	 */
	public int[] getZanNum(Post_Theme postTheme, Post_Reply postReply) {
		int[] zanNums = new int[] { -1, -1, -1 };
		if (postTheme != null) {
			zanNums[0] = this
					.count("select count(*) from ThatIsGood as t where t.thePostTheme.id=" + postTheme.getId());
			if (postReply == null) {
				zanNums[1] = this.count("select count(*) from ThatIsGood as t where t.thePostTheme.id="
						+ postTheme.getId() + " and t.thPostReply.id = null");
			} else {
				zanNums[2] = this
						.count("select count(*) from ThatIsGood as t where t.thPostReply.id =" + postReply.getId());
			}
		}
		return zanNums;
	}

}
