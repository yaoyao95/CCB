/**
 * 
 */
package com.CantoneseClubBBS.domain.post;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.User_;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年6月25日
 * @updateDate
 * @version 1.0
 */
@Entity
@Table(name = "That_Is_Good")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThatIsGood {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** 是否已读 0未读 1已读 */
	private int isRead = 0;

	/** 发出赞的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date issuedDate;

	/** 赞的帖子的链接 */
	@Column(length = 2000)
	private String href;

	/** 被赞人用户名 */
	@Column(length = 50)
	private String theAdmiredUserName;

	/** 发出赞的用户名 */
	@Column(length = 50)
	private String theAdmireUserName;

	/** 被赞的回帖的所属主题帖或被赞的主题帖 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Post_Theme.class)
	@JoinColumn(name = "the_Post_Theme_id", referencedColumnName = "id")
	private Post_Theme thePostTheme;

	/** 被赞的回帖 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Post_Reply.class)
	@JoinColumn(name = "The_Post_Reply_id", referencedColumnName = "id")
	private Post_Reply thPostReply;

	/** 被赞人 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	@JoinColumn(name = "the_Admired_User_Id", referencedColumnName = "id")
	private User_ theAdmiredUser;

	/** 发出赞的用户的 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	@JoinColumn(name = "the_Admire_User_Id", referencedColumnName = "id")
	private User_ theAdmireUser;

	@Transient
	private boolean isNew;

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
	 * @return the theAdmiredUserName
	 */
	public String getTheAdmiredUserName() {
		return theAdmiredUserName;
	}

	/**
	 * @param theAdmiredUserName
	 *            the theAdmiredUserName to set
	 */
	public void setTheAdmiredUserName(String theAdmiredUserName) {
		this.theAdmiredUserName = theAdmiredUserName;
	}

	/**
	 * @return the theAdmireUserName
	 */
	public String getTheAdmireUserName() {
		return theAdmireUserName;
	}

	/**
	 * @param theAdmireUserName
	 *            the theAdmireUserName to set
	 */
	public void setTheAdmireUserName(String theAdmireUserName) {
		this.theAdmireUserName = theAdmireUserName;
	}

	/**
	 * @return the thePostTheme
	 */
	public Post_Theme getThePostTheme() {
		return thePostTheme;
	}

	/**
	 * @param thePostTheme
	 *            the thePostTheme to set
	 */
	public void setThePostTheme(Post_Theme thePostTheme) {
		this.thePostTheme = thePostTheme;
	}

	/**
	 * @return the thPostReply
	 */
	public Post_Reply getThPostReply() {
		return thPostReply;
	}

	/**
	 * @param thPostReply
	 *            the thPostReply to set
	 */
	public void setThPostReply(Post_Reply thPostReply) {
		this.thPostReply = thPostReply;
	}

	/**
	 * @return the theAdmiredUser
	 */
	public User_ getTheAdmiredUser() {
		return theAdmiredUser;
	}

	/**
	 * @param theAdmiredUser
	 *            the theAdmiredUser to set
	 */
	public void setTheAdmiredUser(User_ theAdmiredUser) {
		this.theAdmiredUser = theAdmiredUser;
	}

	/**
	 * @return the theAdmireUser
	 */
	public User_ getTheAdmireUser() {
		return theAdmireUser;
	}

	/**
	 * @param theAdmireUser
	 *            the theAdmireUser to set
	 */
	public void setTheAdmireUser(User_ theAdmireUser) {
		this.theAdmireUser = theAdmireUser;
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
