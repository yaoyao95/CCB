package com.CantoneseClubBBS.domain.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.Section;
import com.CantoneseClubBBS.domain.user.User_;

@Entity
@Table(name = "Post_Theme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post_Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// oracle数据库已经设定好触发器和自增序列了，不需要重复设置，以免调用两次自增
	// @SequenceGenerator(name="notice",sequenceName="notice_sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notice")
	private int id;

	/** 题目 */
	@Column(name = "title", length = 300)
	private String title;

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

	/** 回复数 */
	private int reply_number;

	/** 浏览数 */
	private int browse_number;

	/** 最后回复的用户名 */
	@Column(length = 100)
	private String the_last_reply_user_name;

	/** 最后回复的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date the_last_reply_time;

	/** 是否被锁 0：N 1：Y 2:被取消锁定 */
	private int isRock;

	/** 是否被锁执行的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isRockDate;

	@Column(length = 20)
	private String setIsRockAdminName;

	/** 是否是优秀主题帖0：N 1：Y 2:被取消评为优秀主题帖 */
	private int isGoodTheme;

	/** 是否被评为优秀贴的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isGoodThemeDate;

	@Column(length = 20)
	private String setIsGoodThemeAdminName;

	/** 被赞数 */
	private int thatIsGoodNum;

	/** 楼主和回帖收到的回帖总计 */
	private int totalThatIsGoodNum;

	/** 是否被移动板块0：N 1：Y */
	private int isChangeSection;

	/** 是否被移动板块的执行的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isChangeSectionDate;

	@Column(length = 20)
	private String setIsChangeSectionAdminName;

	/** 是否被删除 0：N 1：Y 2:被取消删除 */
	private int isDelete;

	/** 是否被删除的执行的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isDeleteDate;

	@Column(length = 20)
	private String setIsDeleteAdminName;

	/** 是否置顶 0：N 1：Y 2:被取消置顶 */
	private int isToTop;

	/** 是否置顶的执行的时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isToTopDate;

	@Column(length = 20)
	private String setIsToTopAdminName;

	/** 是否是新的贴（一天以内发的） 0：N 1：Y */
	@Transient
	private int isNew;

	/** 是否是新的被回复的贴（一天以内被回复） 0：N 1：Y */
	@Transient
	private int isReplyNew;

	// =======================引用其他表==============================

	/** 作者 */
	/* 一的一端 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class) // 维护关联关系(从表)
	/* 生成外键列 */
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User_ user_;

	/** 所属板块 */
	/* 一的一端 */
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Section.class) // 维护关联关系(从表)
	/* 生成外键列 */
	@JoinColumn(name = "section_id", referencedColumnName = "id")
	private Section section;

	/** 回帖集合 */
	/* 一的一端 双向多对多 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Post_Reply.class, // 指定关联的持久化
			cascade = CascadeType.REMOVE, // 级联删除(级联到关联的持久化类)
			orphanRemoval = true, // 删除孤儿记录
			mappedBy = "post_Theme") // 指定哪些维护关联关系( 外键列 由 从表维护 ),
										// 写关联的持久化中哪个属性引用了它自己
	// mappedBy确定"维护关系"的一方
	@OrderBy(value = "issuedDate ASC ")
	private List<Post_Reply> post_Replys = new ArrayList<Post_Reply>();

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

	public int getReply_number() {
		return reply_number;
	}

	public void setReply_number(int reply_number) {
		this.reply_number = reply_number;
	}

	public int getBrowse_number() {
		return browse_number;
	}

	public void setBrowse_number(int browse_number) {
		this.browse_number = browse_number;
	}

	public String getThe_last_reply_user_name() {
		return the_last_reply_user_name;
	}

	public void setThe_last_reply_user_name(String the_last_reply_user_name) {
		this.the_last_reply_user_name = the_last_reply_user_name;
	}

	public Date getThe_last_reply_time() {
		return the_last_reply_time;
	}

	public void setThe_last_reply_time(Date the_last_reply_time) {
		this.the_last_reply_time = the_last_reply_time;
	}

	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List<Post_Reply> getPost_Replys() {
		return post_Replys;
	}

	public void setPost_Replys(List<Post_Reply> post_Replys) {
		this.post_Replys = post_Replys;
	}

	/**
	 * 是否被锁 0：N 1：Y 2:被取消锁定
	 * 
	 * @return the isRock
	 */
	public int getIsRock() {
		return isRock;
	}

	/**
	 * 是否被锁 0：N 1：Y 2:被取消锁定
	 * 
	 * @param isRock
	 *            the isRock to set
	 */
	public void setIsRock(int isRock) {
		this.isRock = isRock;
	}

	/**
	 * 是否是优秀主题帖0：N 1：Y 2:被取消评为优秀主题帖
	 * 
	 * @return the isGoodTheme
	 */
	public int getIsGoodTheme() {
		return isGoodTheme;
	}

	/**
	 * 是否是优秀主题帖0：N 1：Y 2:被取消评为优秀主题帖
	 * 
	 * @param isGoodTheme
	 *            the isGoodTheme to set
	 */
	public void setIsGoodTheme(int isGoodTheme) {
		this.isGoodTheme = isGoodTheme;
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

	/**
	 * 是否被移动板块0：N 1：Y
	 * 
	 * @return the isChangeSection
	 */
	public int getIsChangeSection() {
		return isChangeSection;
	}

	/**
	 * 是否被移动板块0：N 1：Y
	 * 
	 * @param isChangeSection
	 *            the isChangeSection to set
	 */
	public void setIsChangeSection(int isChangeSection) {
		this.isChangeSection = isChangeSection;
	}

	/**
	 * @return the isRockDate
	 */
	public Date getIsRockDate() {
		return isRockDate;
	}

	/**
	 * @param isRockDate
	 *            the isRockDate to set
	 */
	public void setIsRockDate(Date isRockDate) {
		this.isRockDate = isRockDate;
	}

	/**
	 * @return the isGoodThemeDate
	 */
	public Date getIsGoodThemeDate() {
		return isGoodThemeDate;
	}

	/**
	 * @param isGoodThemeDate
	 *            the isGoodThemeDate to set
	 */
	public void setIsGoodThemeDate(Date isGoodThemeDate) {
		this.isGoodThemeDate = isGoodThemeDate;
	}

	/**
	 * @return the isChangeSectionDate
	 */
	public Date getIsChangeSectionDate() {
		return isChangeSectionDate;
	}

	/**
	 * @param isChangeSectionDate
	 *            the isChangeSectionDate to set
	 */
	public void setIsChangeSectionDate(Date isChangeSectionDate) {
		this.isChangeSectionDate = isChangeSectionDate;
	}

	/**
	 * 是否被删除0：N 1：Y 2:被取消删除
	 * 
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * 是否被删除 0：N 1：Y 2:被取消删除
	 * 
	 * @param isDelete
	 *            the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the isDeleteDate
	 */
	public Date getIsDeleteDate() {
		return isDeleteDate;
	}

	/**
	 * @param isDeleteDate
	 *            the isDeleteDate to set
	 */
	public void setIsDeleteDate(Date isDeleteDate) {
		this.isDeleteDate = isDeleteDate;
	}

	/**
	 * 是否置顶 0：N 1：Y 2:被取消置顶
	 * 
	 * @return the isToTop
	 */
	public int getIsToTop() {
		return isToTop;
	}

	/**
	 * 是否置顶 0：N 1：Y 2:被取消置顶
	 * 
	 * @param isToTop
	 *            the isToTop to set
	 */
	public void setIsToTop(int isToTop) {
		this.isToTop = isToTop;
	}

	/**
	 * @return the isToTopDate
	 */
	public Date getIsToTopDate() {
		return isToTopDate;
	}

	/**
	 * @param isToTopDate
	 *            the isToTopDate to set
	 */
	public void setIsToTopDate(Date isToTopDate) {
		this.isToTopDate = isToTopDate;
	}

	/**
	 * @return the setIsRockAdminName
	 */
	public String getSetIsRockAdminName() {
		return setIsRockAdminName;
	}

	/**
	 * @param setIsRockAdminName
	 *            the setIsRockAdminName to set
	 */
	public void setSetIsRockAdminName(String setIsRockAdminName) {
		this.setIsRockAdminName = setIsRockAdminName;
	}

	/**
	 * @return the setIsGoodThemeAdminName
	 */
	public String getSetIsGoodThemeAdminName() {
		return setIsGoodThemeAdminName;
	}

	/**
	 * @param setIsGoodThemeAdminName
	 *            the setIsGoodThemeAdminName to set
	 */
	public void setSetIsGoodThemeAdminName(String setIsGoodThemeAdminName) {
		this.setIsGoodThemeAdminName = setIsGoodThemeAdminName;
	}

	/**
	 * @return the setIsChangeSectionAdminName
	 */
	public String getSetIsChangeSectionAdminName() {
		return setIsChangeSectionAdminName;
	}

	/**
	 * @param setIsChangeSectionAdminName
	 *            the setIsChangeSectionAdminName to set
	 */
	public void setSetIsChangeSectionAdminName(String setIsChangeSectionAdminName) {
		this.setIsChangeSectionAdminName = setIsChangeSectionAdminName;
	}

	/**
	 * @return the setIsDeleteAdminName
	 */
	public String getSetIsDeleteAdminName() {
		return setIsDeleteAdminName;
	}

	/**
	 * @param setIsDeleteAdminName
	 *            the setIsDeleteAdminName to set
	 */
	public void setSetIsDeleteAdminName(String setIsDeleteAdminName) {
		this.setIsDeleteAdminName = setIsDeleteAdminName;
	}

	/**
	 * @return the setIsToTopAdminName
	 */
	public String getSetIsToTopAdminName() {
		return setIsToTopAdminName;
	}

	/**
	 * @param setIsToTopAdminName
	 *            the setIsToTopAdminName to set
	 */
	public void setSetIsToTopAdminName(String setIsToTopAdminName) {
		this.setIsToTopAdminName = setIsToTopAdminName;
	}

	/**
	 * @return the isNew
	 */
	public int getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the isReplyNew
	 */
	public int getIsReplyNew() {
		return isReplyNew;
	}

	/**
	 * @param isReplyNew
	 *            the isReplyNew to set
	 */
	public void setIsReplyNew(int isReplyNew) {
		this.isReplyNew = isReplyNew;
	}

	/**
	 * @return the totalThatIsGoodNum
	 */
	public int getTotalThatIsGoodNum() {
		return totalThatIsGoodNum;
	}

	/**
	 * @param totalThatIsGoodNum
	 *            the totalThatIsGoodNum to set
	 */
	public void setTotalThatIsGoodNum(int totalThatIsGoodNum) {
		this.totalThatIsGoodNum = totalThatIsGoodNum;
	}

}
