
package com.CantoneseClubBBS.dao.section.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.section.SectionDao;
import com.CantoneseClubBBS.domain.Section;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
@Repository("sectionDao")
public class SectionDaoImpl extends HibernateDaoImpl implements SectionDao {

	/**
	 * 根据id获取持久化的板块
	 * 
	 * @param id
	 * @return 持久化的板块
	 */
	@Override
	public Section getSectionById(int id) {
		return this.get(Section.class, id);
	}

	/**
	 * 查询所有section板块
	 * 
	 * @return
	 */
	@Override
	public List<Section> findSections() {
		return this.find("select s from Section as s order by s.id");
	}

	/**
	 * 根据name获取持久化的板块
	 * 
	 * @param name
	 * @return 持久化的板块
	 */
	@Override
	public Section getSectionByName(String name) {
		// "select s from Section as s where s.name = "+name+" order by s.id"
		return this.findUniqueEntity("select s from Section as s where s.name = '" + name + "' order by s.id");
	}
}
