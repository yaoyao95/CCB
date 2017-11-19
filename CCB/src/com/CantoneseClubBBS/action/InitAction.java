
package com.CantoneseClubBBS.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.service.SectionService;
import com.CantoneseClubBBS.service.impl.SectionServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
@SuppressWarnings("unused")
public class InitAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("sectionService")
	private SectionService sectionService;

	private Section section;

	private List<Section> sections;

	/** 教学板块集合 */

	private List<Section> tss;

	public void initOrUpdateSections() {

		// 获取application
		// ServletContext application =
		// ServletActionContext.getServletContext();

		// 获取所有section板块
		sections = sectionService.findSections();

		// 对板块进行分类 如 教学区类，水区类等（待后续修改....
		tss = sections;
		// 定义teachingSection板块名字的集合
		List<String> tsnsT = new ArrayList<String>();
		for (Section section : tss) {
			if (section.getRegion_name().equals("教学")) {
				tsnsT.add("123456");
				tsnsT.add(section.getName());
			}
			;
		}
		// application.setAttribute("tsnsT", tsnsT);
		// application.setAttribute("test", "initTesting");
	}

	// getter and setter
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}
