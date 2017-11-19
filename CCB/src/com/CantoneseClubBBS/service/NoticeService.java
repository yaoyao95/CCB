package com.CantoneseClubBBS.service;

import java.util.List;

import com.CantoneseClubBBS.domain.Notice;
import com.CantoneseClubBBS.util.PageModel;

public interface NoticeService {
	List<Notice> getNotices();

	List<Notice> getNoticesByPage(PageModel pageModel, Notice notice);

	void saveNotice(Notice notice);

	void delNotice(Notice notice);

	Notice getNoticeById(int id);

	void updateNotice(Notice notice);
}
