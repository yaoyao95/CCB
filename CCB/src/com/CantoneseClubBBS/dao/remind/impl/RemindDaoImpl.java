/**
 * 
 */
package com.CantoneseClubBBS.dao.remind.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.remind.RemindDao;
import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.util.PageModel;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月18日
 * @updateDate
 * @version 1.0
 */
@Repository("remindDao")
public class RemindDaoImpl extends HibernateDaoImpl implements RemindDao {

	/**
	 * 通过id查找提醒
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Reminding getRemindingById(int id) {
		return this.findUniqueEntity("select r from Reminding as r where r.id=" + id);
	}

	/**
	 * @param string
	 * @param pageModel
	 * @param params
	 * @return
	 */
	@Override
	public List<Reminding> findLoginUserRemindingsByPage(String string, PageModel pageModel, List<String> params) {
		return this.findByPage(string, pageModel, params);
	}

}
