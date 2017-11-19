/**
 * 
 */
package com.CantoneseClubBBS.domain.user.mail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.User_;

/**
 * 发件用户和邮件表的关系
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月19日
 * @updateDate
 * @version 1.0
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Send_user {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private int isDelete = 0;

	// =========================引用其他表===================================
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Mail2.class)
	@JoinColumn(name = "Mail2_ID", referencedColumnName = "id")
	private Mail2 sended_mail;

	/** 原始发件人 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class) // 维护关联关系(从表)
	/* 生成外键列 */
	@JoinColumn(name = "original_send_user_ID", referencedColumnName = "id")
	private User_ original_send_user;

	/** 发件人之一 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class) // 维护关联关系(从表)
	/* 生成外键列 */
	@JoinColumn(name = "one_send_user_ID", referencedColumnName = "id")
	private User_ one_send_user;

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
	 * @return the sended_mail
	 */
	public Mail2 getSended_mail() {
		return sended_mail;
	}

	/**
	 * @param sended_mail
	 *            the sended_mail to set
	 */
	public void setSended_mail(Mail2 sended_mail) {
		this.sended_mail = sended_mail;
	}

	/**
	 * @return the original_send_user
	 */
	public User_ getOriginal_send_user() {
		return original_send_user;
	}

	/**
	 * @param original_send_user
	 *            the original_send_user to set
	 */
	public void setOriginal_send_user(User_ original_send_user) {
		this.original_send_user = original_send_user;
	}

	/**
	 * @return the one_send_user
	 */
	public User_ getOne_send_user() {
		return one_send_user;
	}

	/**
	 * @param one_send_user
	 *            the one_send_user to set
	 */
	public void setOne_send_user(User_ one_send_user) {
		this.one_send_user = one_send_user;
	}

	/**
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

}
