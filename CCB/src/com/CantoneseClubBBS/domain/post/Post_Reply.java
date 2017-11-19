
package com.CantoneseClubBBS.domain.post;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.User_;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月26日
 * @version 1.0
 * @update_date
 */
@Entity
@Table(name = "Post_Reply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post_Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// oracle数据库已经设定好触发器和自增序列了，不需要重复设置，以免调用两次自增
	// @SequenceGenerator(name="notice",sequenceName="notice_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notice")
	private int id;

	/** 内容 */
	@Column(length = 100000)
	@Lob
	private String content;

	/** 发布时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date issuedDate;

	/** 更新时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	/** 楼层 */
	private int storey;

	/** 被赞数 */
	private int thatIsGoodNum;
	// =======================引用其他表==============================

	/** 作者 */
	/* 一的一端 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class) // 维护关联关系(从表)
	/* 生成外键列 */ // unique表示该表（teaching_post_reply）的该字段（user_id）是唯一的，不能重复
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User_ user_;

	/** 所属主题帖子 */
	/* 多的一端 */
	@ManyToOne(fetch = FetchType.LAZY, // 延迟加载
			// cascade=CascadeType.REMOVE, // 级联删除
			targetEntity = Post_Theme.class) // 指定关联的持久化类
	/** 生成外键列 */
	@JoinColumn(name = "Post_Theme_ID", referencedColumnName = "id")
	private Post_Theme post_Theme;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getIssuedDate() {
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

	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

	public Post_Theme getPost_Theme() {
		return post_Theme;
	}

	public void setPost_Theme(Post_Theme post_Theme) {
		this.post_Theme = post_Theme;
	}

	/**
	 * @return the storey
	 */
	public int getStorey() {
		return storey;
	}

	/**
	 * @param storey
	 *            the storey to set
	 */
	public void setStorey(int storey) {
		this.storey = storey;
	}

	/**
	 * @return the thatIsGoodNum
	 */
	public int getThatIsGoodNum() {
		return thatIsGoodNum;
	}

	/**
	 * @param thatIsGoodNum
	 *            the thatIsGoodNum to set
	 */
	public void setThatIsGoodNum(int thatIsGoodNum) {
		this.thatIsGoodNum = thatIsGoodNum;
	}

}
