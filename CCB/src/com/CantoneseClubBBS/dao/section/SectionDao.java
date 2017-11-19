/**
 * 
 */
package com.CantoneseClubBBS.dao.section;

import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.Section;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
public interface SectionDao extends HibernateDao {

	/**
	 * 根据id获取持久化的板块
	 * 
	 * @param id
	 * @return 持久化的板块
	 */
	Section getSectionById(int id);

	/**
	 * 查询所有section板块
	 * 
	 * @return
	 */
	List<Section> findSections();

	/**
	 * 根据name获取持久化的板块
	 * 
	 * @param name
	 * @return 持久化的板块
	 */
	Section getSectionByName(String name);

}
