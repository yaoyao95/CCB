package com.CantoneseClubBBS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CantoneseClubBBS.dao.notice.NoticeDao;
import com.CantoneseClubBBS.domain.Notice;
import com.CantoneseClubBBS.service.NoticeService;
import com.CantoneseClubBBS.util.PageModel;

@Service("noticeService")
// 加上事务，这个类的所有方法都会生效：
// 方法运行过程中遇到RuntimeException就会对数据库进行rollback，返回到调用方法前的状态
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class NoticeServiceImpl implements NoticeService {
	@Resource(name = "noticeDao")
	private NoticeDao noticeDao;

	/**
	 * 查询最近的公告 用于主页
	 * 
	 * @return 返回最近的公告
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Notice> getNotices() {
		return noticeDao.getNotices();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Notice> getNoticesByPage(PageModel pageModel, Notice notice) {
		return noticeDao.getNoticesByPage(pageModel, notice);
	}

	@Override
	public void saveNotice(Notice notice) {
		noticeDao.save(notice);
	}

	@Override
	public void delNotice(Notice notice) {
		noticeDao.delete(notice);
	}

	@Transactional(readOnly = true)
	@Override
	public Notice getNoticeById(int id) {
		return noticeDao.getNoticeById(id);
	}

	@Override
	public void updateNotice(Notice notice) {
		noticeDao.updateNotice(notice);
	}

	@Transactional(readOnly = true)
	/** Spring4的setter注入 */
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

}
