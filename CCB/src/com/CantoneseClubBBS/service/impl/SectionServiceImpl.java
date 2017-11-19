/**
 * 
 */
package com.CantoneseClubBBS.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.section.SectionDao;
import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.service.SectionService;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
@Service("sectionService")
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class SectionServiceImpl implements SectionService {
	@Resource(name = "sectionDao")
	private SectionDao sectionDao;

	/**
	 * 查询所有section板块
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Section> findSections() {
		return sectionDao.findSections();
	}

	/**
	 * 通过setionName查找section
	 * 
	 * @param section
	 * @return
	 */
	@Override
	public Section getSectionByName(Section section) {
		if (!StringUtils.isEmpty(section.getName())) {
			return sectionDao.findUniqueEntity("select s from Section as s where s.name ='" + section.getName() + "'");
		} /*
			 * else if(!StringUtils.isEmpty(section.getRegion_name_abbr())){
			 * List<Section> ss = sectionDao.
			 * find("select s from Section as s where s.region_name_abbr ='"
			 * +section.getRegion_name_abbr()+"'");
			 * 
			 * if(ss.size()>=1){ return ss.get(0); } }
			 */
		return new Section();
	}

}
