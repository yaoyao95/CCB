
package com.CantoneseClubBBS.domain.user;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.mail.Mail;

/**
 * 用户
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月24日
 * @version 1.0
 * @update_date
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User_ {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name="user",sequenceName="user__sequence",allocationSize
	// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user")
	private int id;

	/** 用户名，不能为空，唯一 */
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	/** 昵称 */
	@Column(length = 50, nullable = true, unique = false)
	private String nickname;

	/** 密码，不能为空，只能是字母和数字组合,长度必须大于或等于6 */
	@Column(name = "password", length = 65, nullable = false)
	private String password;

	/** 密保问题1 */
	@Column(name = "password_issue1", nullable = false, length = 100)
	private String password_issue1;

	/** 密保答案1 */
	@Column(name = "passWord_answer1", nullable = false, length = 100)
	private String passWord_answer1;

	/** 密保问题2 */
	@Column(name = "password_issue2", nullable = true, length = 100)
	private String password_issue2;

	/** 密保答案2 */
	@Column(name = "passWord_answer2", nullable = true, length = 100)
	private String passWord_answer2;

	/** 头像路径 */
	@Column(name = "picture_path", nullable = true)
	private String picture_path;

	/** 用户等级与game_account挂钩 */
	@Column(nullable = true)
	private int level_ = 1;

	/**
	 * 用户权限等级 权限，每一位表示一个权限，Y/N表示权限有无。 0位：总版主，1位：公告区，2位教学区，3位：任务区，4：足迹区，5：用户管理
	 * 6:普通用户 7:列入黑名单，8：水区.9:论坛整体页面内容管理员
	 */
	@Column(length = 200)
	private String authority = "NNNNNNYNNN";

	/** 用户注册时间 */
	@Column(nullable = true)
	private Date register_date = new Date();

	/** 用户在线时间 毫秒数 */
	@Column(nullable = true)
	private long Online_time = 0;

	/** 用户个性签名 */
	@Column(name = "signature_personality", nullable = true, length = 200)
	private String signature_personality;

	/** 用户联系方式 */
	@Column(name = "contact_information", nullable = true, length = 250)
	private String contact_information;

	/** 用户性别 1=男，2=女,3=保密，3=.... */
	@Column(nullable = false)
	private int sex = 3;

	/** 用户生日 */
	@Column(nullable = true)
	private Date birthday;

	/** 用户主页 */
	@Column(name = "personal_home", nullable = true, length = 350)
	private String personal_home;

	/** 是否有未读邮件 user表 */
	@Column(nullable = false)
	private boolean fn_read_mail = false;
	/** 记录邮件数量 */
	@Column(nullable = false)
	private int mail_count = 0;
	/** 自动登录时解密加密用的key */
	@Column(length = 100)
	private String cookie_AutoLogin_Key;
	/** 自动登录时解密加密用的Iv */
	@Column(length = 100)
	private String cookie_AutoLogin_Iv;

	// ===========================引用其他表=========================================

	/** 用户发出的邮件 */
	/* 一的一端 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Mail.class, // 指定关联的持久化
			cascade = CascadeType.REMOVE, // 级联删除(级联到关联的持久化类)
			orphanRemoval = true, // 删除孤儿记录
			mappedBy = "sended_user") // 指定哪些维护关联关系( 外键列 由 从表维护 ),
										// 写关联的持久化中哪个属性引用了它自己
	// mappedBy确定"维护关系"的一方
	private List<Mail> sended_user = new ArrayList<Mail>();

	/** 用户收到的邮件 双向1-N */
	/* 一的一端 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Mail.class, // 指定关联的持久化
			cascade = CascadeType.REMOVE, // 级联删除(级联到关联的持久化类)
			orphanRemoval = true, // 删除孤儿记录
			mappedBy = "received_user") // 指定哪些维护关联关系( 外键列 由 从表维护 ),
										// 写关联的持久化中哪个属性引用了它自己
	// mappedBy确定"维护关系"的一方
	private List<Mail> received_mails = new ArrayList<Mail>();

	/** 用户附件 双向1-N */
	/* 一的一端 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Attachment.class, // 指定关联的持久化
			cascade = CascadeType.REMOVE, // 级联删除(级联到关联的持久化类)
			orphanRemoval = true, // 删除孤儿记录
			mappedBy = "user_") // 指定哪些维护关联关系( 外键列 由 从表维护 ), 写关联的持久化中哪个属性引用了它自己
	// mappedBy确定"维护关系"的一方
	private List<Attachment> Attachments = new ArrayList<Attachment>();

	/** 用户物品 双向1-1 */
	/* 一的一端 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_Item.class)
	/* 生成外键列 */
	@JoinColumn(name = "Item_id", unique = true, referencedColumnName = "id")
	private User_Item user_item;

	// /**用户所有帖子的情况*/
	// /* 一的一端 */
	// @OneToMany(fetch=FetchType.LAZY, // 延迟加载
	// targetEntity=User_Post.class, // 指定关联的持久化
	// cascade=CascadeType.REMOVE, // 级联删除(级联到关联的持久化类)
	// orphanRemoval=true // 删除孤儿记录
	// )
	// @JoinColumn(name="User_ID", referencedColumnName="id")
	// private Set<User_Post> user_posts = new HashSet<User_Post>();

	/** 用户游戏账号1-1 双向 */
	/* 一的一端 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Game_Account.class) // 维护关联关系(从表)
	/* 生成外键列 */
	@JoinColumn(name = "Game_Account_id", unique = true, referencedColumnName = "id")
	private Game_Account game_account;

	// getter and setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword_issue1() {
		return password_issue1;
	}

	public void setPassword_issue1(String password_issue1) {
		this.password_issue1 = password_issue1;
	}

	public String getPassWord_answer1() {
		return passWord_answer1;
	}

	public void setPassWord_answer1(String passWord_answer1) {
		this.passWord_answer1 = passWord_answer1;
	}

	public String getPassword_issue2() {
		return password_issue2;
	}

	public void setPassword_issue2(String password_issue2) {
		this.password_issue2 = password_issue2;
	}

	public String getPassWord_answer2() {
		return passWord_answer2;
	}

	public void setPassWord_answer2(String passWord_answer2) {
		this.passWord_answer2 = passWord_answer2;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public int getLevel_() {
		return level_;
	}

	public void setLevel_(int level_) {
		this.level_ = level_;
	}

	/**
	 * 用户权限等级 权限，每一位表示一个权限，Y/N表示权限有无。
	 * 0位：总版主，1位：公告区，2位教学区，3位：任务区，4：足迹区，5：用户....6:普通用户 7：被拉入黑名单 禁言
	 * 
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * 用户权限等级 权限，每一位表示一个权限，Y/N表示权限有无。
	 * 0位：总版主，1位：公告区，2位教学区，3位：任务区，4：足迹区，5：用户....6:普通用户
	 * 
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public long getOnline_time() {
		return Online_time;
	}

	public void setOnline_time(long online_time) {
		Online_time = online_time;
	}

	public String getSignature_personality() {
		return signature_personality;
	}

	public void setSignature_personality(String signature_personality) {
		this.signature_personality = signature_personality;
	}

	public String getContact_information() {
		return contact_information;
	}

	public void setContact_information(String contact_information) {
		this.contact_information = contact_information;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPersonal_home() {
		return personal_home;
	}

	public void setPersonal_home(String personal_home) {
		this.personal_home = personal_home;
	}

	public boolean isFn_read_mail() {
		return fn_read_mail;
	}

	public void setFn_read_mail(boolean fn_read_mail) {
		this.fn_read_mail = fn_read_mail;
	}

	public int getMail_count() {
		return mail_count;
	}

	public void setMail_count(int mail_count) {
		this.mail_count = mail_count;
	}

	// public Set<User_Post> getUser_posts() {
	// return user_posts;
	// }
	//
	// public void setUser_posts(Set<User_Post> user_posts) {
	// this.user_posts = user_posts;
	// }

	public List<Attachment> getAttachments() {
		return Attachments;
	}

	public List<Mail> getSended_user() {
		return sended_user;
	}

	public void setSended_user(List<Mail> sended_user) {
		this.sended_user = sended_user;
	}

	public List<Mail> getReceived_mails() {
		return received_mails;
	}

	public void setReceived_mails(List<Mail> received_mails) {
		this.received_mails = received_mails;
	}

	public void setAttachments(List<Attachment> attachments) {
		Attachments = attachments;
	}

	public Game_Account getGame_account() {
		return game_account;
	}

	public void setGame_account(Game_Account game_account) {
		this.game_account = game_account;
	}

	public User_Item getUser_item() {
		return user_item;
	}

	public void setUser_item(User_Item user_item) {
		this.user_item = user_item;
	}

	/**
	 * @return the cookie_AutoLogin_Key
	 */
	public String getCookie_AutoLogin_Key() {
		return cookie_AutoLogin_Key;
	}

	/**
	 * @param cookie_AutoLogin_Key
	 *            the cookie_AutoLogin_Key to set
	 */
	public void setCookie_AutoLogin_Key(String cookie_AutoLogin_Key) {
		this.cookie_AutoLogin_Key = cookie_AutoLogin_Key;
	}

	/**
	 * @return the cookie_AutoLogin_Iv
	 */
	public String getCookie_AutoLogin_Iv() {
		return cookie_AutoLogin_Iv;
	}

	/**
	 * @param cookie_AutoLogin_Iv
	 *            the cookie_AutoLogin_Iv to set
	 */
	public void setCookie_AutoLogin_Iv(String cookie_AutoLogin_Iv) {
		this.cookie_AutoLogin_Iv = cookie_AutoLogin_Iv;
	}

}
