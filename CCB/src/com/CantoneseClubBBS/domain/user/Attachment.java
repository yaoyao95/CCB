/**
 * 
 */
package com.CantoneseClubBBS.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 附件表 存储了所有的附件
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月24日
 * @version 1.0
 * @update_date
 */
@Entity
@Table
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name="attachment",sequenceName="attachment_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="attachment")
	private int id;
	/** 附件所存储的磁盘路径 */
	@Column(length = 500, nullable = true)
	private String path;
	/** 附件所在的帖子地址 */
	@Column(length = 500, nullable = true)
	private String url;
	@Column(length = 100)
	private String updateDate;

	/* 多的一端 */
	@ManyToOne(fetch = FetchType.LAZY, // 延迟加载
			// cascade=CascadeType.REMOVE, // 级联删除
			targetEntity = User_.class) // 指定关联的持久化类
	/* 生成外键列 */
	@JoinColumn(name = "User_ID", referencedColumnName = "id")
	private User_ user_;

	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	/** 设置附件在磁盘的路径 */
	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
