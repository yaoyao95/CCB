/**
 * 
 */
package com.CantoneseClubBBS.service;

import java.util.List;

import com.CantoneseClubBBS.domain.Section;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
public interface SectionService {

	/**
	 * 查询所有section板块
	 * 
	 * @return
	 */
	List<Section> findSections();

	/**
	 * 通过setionName
	 * 
	 * @param section
	 * @return
	 */
	Section getSectionByName(Section section);

}
