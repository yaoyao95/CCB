/**
 * 
 */
package com.CantoneseClubBBS.domain.user.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.group.Group;

/**
 * 用户邮箱
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月19日
 * @updateDate
 * @version 2.0
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mail2 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/** 题目 */
	@Column(length = 200)
	private String title;
	/** 内容 */
	@Lob
	@Column(length = 2000)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date send_date;

	// =========================引用其他表===================================
	/** 发件组 多对一 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Group.class)
	@JoinColumn(name = "Send_group_id", referencedColumnName = "id")
	private Group send_group;

	/** 收件组 多对一 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Group.class)
	@JoinColumn(name = "Receive_group_id", referencedColumnName = "id")
	private Group receive_group;

	/** 发件人集合 */
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Send_user.class, // 关联实体类。即本类和哪个类相关联，关联了才可以联合起来查询。
			orphanRemoval = false, mappedBy = "sended_mail")
	private List<Send_user> send_users = new ArrayList<Send_user>();

	/** 收件人集合 */
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Receive_user.class, // 关联实体类。即本类和哪个类相关联，关联了才可以联合起来查询。
			orphanRemoval = false, mappedBy = "received_mail")
	private List<Receive_user> receive_users = new ArrayList<Receive_user>();

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the send_date
	 */
	public Date getSend_date() {
		return send_date;
	}

	/**
	 * @param send_date
	 *            the send_date to set
	 */
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	/**
	 * @return the send_group
	 */
	public Group getSend_group() {
		return send_group;
	}

	/**
	 * @param send_group
	 *            the send_group to set
	 */
	public void setSend_group(Group send_group) {
		this.send_group = send_group;
	}

	/**
	 * @return the receive_group
	 */
	public Group getReceive_group() {
		return receive_group;
	}

	/**
	 * @param receive_group
	 *            the receive_group to set
	 */
	public void setReceive_group(Group receive_group) {
		this.receive_group = receive_group;
	}

	/**
	 * @return the send_users
	 */
	public List<Send_user> getSend_users() {
		return send_users;
	}

	/**
	 * @param send_users
	 *            the send_users to set
	 */
	public void setSend_users(List<Send_user> send_users) {
		this.send_users = send_users;
	}

	/**
	 * @return the receive_users
	 */
	public List<Receive_user> getReceive_users() {
		return receive_users;
	}

	/**
	 * @param receive_users
	 *            the receive_users to set
	 */
	public void setReceive_users(List<Receive_user> receive_users) {
		this.receive_users = receive_users;
	}

}
