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
 * 收件用户和邮件表的关系
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
public class Receive_user {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/** 该收件人是否已读 */
	private int isRead = 0;
	@Column(nullable = false)
	private int isDelete = 0;
	// =========================引用其他表===================================
	/** 所属邮件 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Mail2.class)
	@JoinColumn(name = "Mail2_ID", referencedColumnName = "id")
	private Mail2 received_mail;

	/** 收件人之一 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class) // 维护关联关系(从表)
	/** 生成外键列 */
	@JoinColumn(name = "one_receive_user_ID", referencedColumnName = "id")
	private User_ one_receive_user;

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
	 * @return the received_mail
	 */
	public Mail2 getReceived_mail() {
		return received_mail;
	}

	/**
	 * @param received_mail
	 *            the received_mail to set
	 */
	public void setReceived_mail(Mail2 received_mail) {
		this.received_mail = received_mail;
	}

	/**
	 * @return the one_receive_user
	 */
	public User_ getOne_receive_user() {
		return one_receive_user;
	}

	/**
	 * @param one_receive_user
	 *            the one_receive_user to set
	 */
	public void setOne_receive_user(User_ one_receive_user) {
		this.one_receive_user = one_receive_user;
	}

	/**
	 * @return the isRead
	 */
	public int getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead
	 *            the isRead to set
	 */
	public void setIsRead(int isRead) {
		this.isRead = isRead;
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
