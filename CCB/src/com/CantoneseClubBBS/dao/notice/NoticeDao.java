package com.CantoneseClubBBS.dao.notice;

import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.Notice;
import com.CantoneseClubBBS.util.PageModel;

public interface NoticeDao extends HibernateDao {

	List<Notice> getNotices();

	int recordCount();

	List<Notice> getNoticesByPage(PageModel pageModel, Notice notice);

	Notice getNoticeById(int id);

	void updateNotice(Notice notice);

}
