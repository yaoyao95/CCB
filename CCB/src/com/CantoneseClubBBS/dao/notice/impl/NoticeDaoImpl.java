package com.CantoneseClubBBS.dao.notice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.notice.NoticeDao;
import com.CantoneseClubBBS.domain.Notice;
import com.CantoneseClubBBS.util.PageModel;

@Repository("noticeDao")
public class NoticeDaoImpl extends HibernateDaoImpl implements NoticeDao {

	/**
	 * 查询最近的公告 用于主页
	 * 
	 * @return 返回最近的公告
	 */
	@Override
	public List<Notice> getNotices() {
		// return this.find("from Notice order by id desc ");
		return this.findByPage("from Notice order by id desc", 0, 2);
	}

	@Override
	public int recordCount() {
		return this.count("select count(*) from Notice");
	}

	@Override
	public List<Notice> getNoticesByPage(PageModel pageModel, Notice notice) {
		// 定义hql语句
		StringBuilder hql = new StringBuilder();
		hql.append("select n from Notice as n where 1=1");

		// 定义集合封装查询参数
		List<String> params = new ArrayList<String>();

		// 判断条件notice时候存在
		if (notice != null) {
			// 判断标题条件是否存在
			if (!StringUtils.isEmpty(notice.getTitle())) {
				// 加上标题条件
				hql.append(" and n.title like ?");
				params.add("%" + notice.getTitle() + "%");
			}
			// 判断内容条件是否存在
			if (!StringUtils.isEmpty(notice.getContent())) {
				// 加上内容条件
				hql.append(" and n.content like ?");
				params.add("%" + notice.getContent() + "%");
			}
		}
		hql.append(" order by n.id desc ");
		return this.findByPage(hql.toString(), pageModel, params);
	}

	@Override // 返回一个持久化的notice
	public Notice getNoticeById(int id) {
		// String hql = "select n from Notice as n where n.id = "+id;
		return this.get(Notice.class, id);
	}

	@Override
	public void updateNotice(Notice notice) {
		// 获得指定ID的持久化的notice
		Notice noticeForUpdate = getNoticeById(notice.getId());
		// 在持久化下修改（如果直接用hibernate的update方法会出错，因为Notice表的主键设置了自增长）
		noticeForUpdate.setTitle(notice.getTitle());
		noticeForUpdate.setContent(notice.getContent());
		noticeForUpdate.setUpdateDate(new Date());
	}

}
