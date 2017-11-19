/**
 * 
 */
package com.CantoneseClubBBS.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.post.Post_Reply;
import com.CantoneseClubBBS.domain.post.Post_Theme;
import com.CantoneseClubBBS.domain.user.User_;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月18日
 * @updateDate
 * @version 1.0
 */
@Entity
@Table(name = "Reminding")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reminding {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** 是否已读 0未读 1已读 */
	private int isRead = 0;

	/** 发出提醒的时间 */
	private Date issuedDate;

	/** 提醒的链接 */
	@Column(length = 2000)
	private String href;

	/** 被提醒人用户名 */
	@Column(length = 50)
	private String remindedUserName;

	/** 发出提醒的用户名 */
	@Column(length = 50)
	private String remindingUserName;

	@Transient
	private boolean isNew;

	// ========================= 引用其他表 ===========================
	/** 被提醒人id */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	@JoinColumn(name = "Reminded_User_Id", referencedColumnName = "id")
	private User_ remindedUser;

	/** 发出提醒人的id */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	@JoinColumn(name = "Reminding_User_Id", referencedColumnName = "id")
	private User_ remindingUser;

	/** 被提醒的回帖id */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Post_Reply.class)
	@JoinColumn(name = "The_Reminded_Post_Reply", referencedColumnName = "id")
	private Post_Reply theRemindedPostReply;

	/** 被提醒的回帖的所属主题帖或被提醒的主题帖 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Post_Theme.class)
	@JoinColumn(name = "The_Reminded_Post_Theme", referencedColumnName = "id")
	private Post_Theme theRemindedPostTheme;

	// ============getter and setter===============
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
	 * @return the issuedDate
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @param issuedDate
	 *            the issuedDate to set
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the remindedUserName
	 */
	public String getRemindedUserName() {
		return remindedUserName;
	}

	/**
	 * @param remindedUserName
	 *            the remindedUserName to set
	 */
	public void setRemindedUserName(String remindedUserName) {
		this.remindedUserName = remindedUserName;
	}

	/**
	 * @return the remindingUserName
	 */
	public String getRemindingUserName() {
		return remindingUserName;
	}

	/**
	 * @param remindingUserName
	 *            the remindingUserName to set
	 */
	public void setRemindingUserName(String remindingUserName) {
		this.remindingUserName = remindingUserName;
	}

	/**
	 * @return the remindedUser
	 */
	public User_ getRemindedUser() {
		return remindedUser;
	}

	/**
	 * @param remindedUser
	 *            the remindedUser to set
	 */
	public void setRemindedUser(User_ remindedUser) {
		this.remindedUser = remindedUser;
	}

	/**
	 * @return the remindingUser
	 */
	public User_ getRemindingUser() {
		return remindingUser;
	}

	/**
	 * @param remindingUser
	 *            the remindingUser to set
	 */
	public void setRemindingUser(User_ remindingUser) {
		this.remindingUser = remindingUser;
	}

	/**
	 * @return the theRemindedPostReply
	 */
	public Post_Reply getTheRemindedPostReply() {
		return theRemindedPostReply;
	}

	/**
	 * @param theRemindedPostReply
	 *            the theRemindedPostReply to set
	 */
	public void setTheRemindedPostReply(Post_Reply theRemindedPostReply) {
		this.theRemindedPostReply = theRemindedPostReply;
	}

	/**
	 * @return the theRemindedPostTheme
	 */
	public Post_Theme getTheRemindedPostTheme() {
		return theRemindedPostTheme;
	}

	/**
	 * @param theRemindedPostTheme
	 *            the theRemindedPostTheme to set
	 */
	public void setTheRemindedPostTheme(Post_Theme theRemindedPostTheme) {
		this.theRemindedPostTheme = theRemindedPostTheme;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

}
