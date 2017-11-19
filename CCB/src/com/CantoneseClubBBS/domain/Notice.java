package com.CantoneseClubBBS.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// oracle数据库已经设定好触发器和自增序列了，不需要重复设置，以免调用两次自增
	// @SequenceGenerator(name="notice",sequenceName="notice_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notice")
	private int id;
	@Column(name = "title", length = 250)
	private String title;
	@Column(name = "content", length = 5000)
	@Lob
	private String content;
	@Column(name = "issued_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issuedDate;
	@Column(name = "UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getIssuedDate() {
		// DateFormat.getDateInstance().
		// new SimpleDateFormat("yyyy-MM-dd HH").format(issuedDate);
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
