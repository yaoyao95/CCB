/**
 * 
 */
package com.CantoneseClubBBS.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 帖子所属的板快
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月26日
 * @version 1.0
 * @update_date
 */

@Entity
@Table(name = "Section")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// oracle数据库已经设定好触发器和自增序列了，不需要重复设置，以免调用两次自增
	// @SequenceGenerator(name="notice",sequenceName="notice_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notice")
	private int id;
	/** 小板块名字 */
	@Column(length = 15, unique = true)
	private String name;
	/** 小板块短名 */
	@Column(length = 15, unique = true)
	private String name_abbr;
	/** 板块所属的区域 */
	@Column(length = 20)
	private String region_name;
	/** 小板快说明 */
	@Column(length = 1000)
	@Lob
	private String description;
	/** 大板块所属区域的短名 */
	@Column(length = 20)
	private String region_name_abbr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** get板块所属的区域 */
	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_name_abbr() {
		return region_name_abbr;
	}

	public void setRegion_name_abbr(String region_name_abbr) {
		this.region_name_abbr = region_name_abbr;
	}

	public String getName_abbr() {
		return name_abbr;
	}

	public void setName_abbr(String name_abbr) {
		this.name_abbr = name_abbr;
	}

}
