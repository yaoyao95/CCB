/**
 * 
 */
package com.CantoneseClubBBS.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.post.PostDao;
import com.CantoneseClubBBS.dao.remind.RemindDao;
import com.CantoneseClubBBS.dao.user.UserDao;
import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.service.RemindService;
import com.CantoneseClubBBS.util.PageModel;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月18日
 * @updateDate
 * @version 1.0
 */
@Service("remindService")
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class RemindServiceImpl implements RemindService {
	@Resource(name = "remindDao")
	private RemindDao remindDao;
	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "postDao")
	private PostDao postDao;

	/**
	 * 检查当前登录的用户是否存在未查看的提醒。
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public boolean checkNewRemindingByLoginUserId(int uid) {
		List<Reminding> rs = remindDao
				.find("select r from Reminding as r where r.remindedUser.id =" + uid + "and r.isRead = 0");
		if (rs == null) {
			return false;
		} else if (rs.size() >= 1) {
			return true;
		} else {// size = 0
			return false;
		}
	}

	/**
	 * 查询当前登录用户的@提醒
	 * 
	 * @param LoginUserId
	 * @param pageModel
	 * @param rdc
	 * @return
	 */
	@Override
	public List<Reminding> selectLoginUserRemindings(int LoginUserId, PageModel pageModel, Reminding rdc) {
		/** 条件 */
		List<String> params = new ArrayList<String>();
		StringBuilder hql = new StringBuilder();
		hql.append("select r from Reminding as r where r.remindedUser.id =" + LoginUserId);
		// 判断查询条件是否存在
		if (rdc != null) {
			if (StringUtils.isEmpty(rdc.getRemindingUserName()) == false) {
				hql.append(" and r.remindingUserName like ?");
				params.add("%" + rdc.getRemindingUserName() + "%");
			}
		}
		// 排序:先新再旧
		hql.append(" order by r.issuedDate desc");
		List<Reminding> rs = remindDao.findLoginUserRemindingsByPage(hql.toString(), pageModel, params);

		// 解除延迟
		for (Reminding r : rs) {
			r.getRemindedUser().getName();
			r.getRemindingUser().getName();
			if (r.getTheRemindedPostReply() != null) {
				r.getTheRemindedPostReply().getStorey();
			}
			if (r.getTheRemindedPostTheme() != null) {
				r.getTheRemindedPostTheme().getTitle();
			}
			Calendar c = Calendar.getInstance();// 当前时间
			c.add(Calendar.DAY_OF_MONTH, -1);// 一天前
			Calendar remindingDate = Calendar.getInstance();
			remindingDate.setTime(r.getIssuedDate());
			if (remindingDate.compareTo(c) > 0) {// 一天以内的提醒
				r.setIsNew(true);
			}
		}

		return rs;
	}

	/**
	 * 设置提醒已读
	 * 
	 * @param id
	 */
	@Override
	public void setRemindingRead(int id) {
		// Reminding r = remindDao.getRemindingById(id);
		Reminding r = remindDao.get(Reminding.class, id);
		r.setIsRead(1);
		remindDao.update(r);
	}

	/**
	 * 通过PostTheme的id，查找指定postTheme楼主提醒的用户
	 * 
	 * @param PostThemeId
	 * @return
	 */
	@Override
	public List<Reminding> selectPostThemeRemindingsById(int PostThemeId) {
		List<Reminding> rs = remindDao.find("select r from Reminding as r where r.theRemindedPostTheme.id ="
				+ PostThemeId + " and r.theRemindedPostReply.id = null");

		return rs;
	}

}
