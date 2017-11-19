/**
 * 
 */
package com.CantoneseClubBBS.dao.remind;

import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.Reminding;
import com.CantoneseClubBBS.util.PageModel;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月18日
 * @updateDate
 * @version 1.0
 */
public interface RemindDao extends HibernateDao {

	/**
	 * 通过id查找提醒
	 * 
	 * @param id
	 * @return
	 */
	Reminding getRemindingById(int id);

	/**
	 * @param string
	 * @param pageModel
	 * @param params
	 * @return
	 */
	List<Reminding> findLoginUserRemindingsByPage(String string, PageModel pageModel, List<String> params);

}
