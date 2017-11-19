/**
 * 
 */
package com.CantoneseClubBBS.service;

import java.util.List;

import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.util.PageModel;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月18日
 * @updateDate
 * @version 1.0
 */
public interface RemindService {

	/**
	 * 检查当前登录的用户是否存在未查看的提醒。
	 * 
	 * @param uid
	 * @return
	 */
	boolean checkNewRemindingByLoginUserId(int uid);

	/**
	 * 查询当前登录用户的@提醒
	 * 
	 * @param LoginUserId
	 * @param pageModel
	 * @param rdc
	 *            查询条件
	 * @return
	 */
	List<Reminding> selectLoginUserRemindings(int LoginUserId, PageModel pageModel, Reminding rdc);

	/**
	 * 设置提醒已读
	 * 
	 * @param id
	 */
	void setRemindingRead(int id);

	/**
	 * 通过PostTheme的id，查找指定postTheme楼主提醒的用户
	 * 
	 * @param PostThemeId
	 * @return
	 */
	List<Reminding> selectPostThemeRemindingsById(int PostThemeId);

}
