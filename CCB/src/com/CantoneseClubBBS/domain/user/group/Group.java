/**
 * 
 */
package com.CantoneseClubBBS.domain.user.group;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail2;

/**
 * 用户组
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月19日
 * @updateDate
 * @version 1.0
 */
@Entity
@Table(name = "Group_")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/** 组名 */
	@Column(length = 20)
	private String name;
	@Lob
	private String description;
	/** 权限 */
	private int authority;

	// =========================引用其他表===================================
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	/** 生成外键列 */
	@JoinColumn(name = "Leader_ID", referencedColumnName = "id")
	private User_ leader;

	/** 组员 */
	/* 一的一端 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Group_menber.class, // 指定关联的持久化
			orphanRemoval = false, // 删除孤儿记录
			mappedBy = "group") // 指定哪些维护关联关系( 外键列 由 从表维护 ), 写关联的持久化中哪个属性引用了它自己
	// mappedBy确定"维护关系"的一方
	private List<Group_menber> group_menbers = new ArrayList<Group_menber>();

	/** 发的邮件 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Mail2.class, // 指定关联的持久化
			mappedBy = "send_group") // 指定哪些维护关联关系( 外键列 由 从表维护 ),
										// 写关联的持久化中哪个属性引用了它自己
	private List<Mail2> send_mails = new ArrayList<Mail2>();

	/** 收的邮件 */
	@OneToMany(fetch = FetchType.LAZY, // 延迟加载
			targetEntity = Mail2.class, // 指定关联的持久化
			mappedBy = "receive_group") // 指定哪些维护关联关系( 外键列 由 从表维护 ),
										// 写关联的持久化中哪个属性引用了它自己
	private List<Mail2> receive_mails = new ArrayList<Mail2>();

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
	 * @return the leader
	 */
	public User_ getLeader() {
		return leader;
	}

	/**
	 * @param leader
	 *            the leader to set
	 */
	public void setLeader(User_ leader) {
		this.leader = leader;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the authority
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * @return the group_menbers
	 */
	public List<Group_menber> getGroup_menbers() {
		return group_menbers;
	}

	/**
	 * @param group_menbers
	 *            the group_menbers to set
	 */
	public void setGroup_menbers(List<Group_menber> group_menbers) {
		this.group_menbers = group_menbers;
	}

	/**
	 * @return the send_mails
	 */
	public List<Mail2> getSend_mails() {
		return send_mails;
	}

	/**
	 * @param send_mails
	 *            the send_mails to set
	 */
	public void setSend_mails(List<Mail2> send_mails) {
		this.send_mails = send_mails;
	}

	/**
	 * @return the receive_mails
	 */
	public List<Mail2> getReceive_mails() {
		return receive_mails;
	}

	/**
	 * @param receive_mails
	 *            the receive_mails to set
	 */
	public void setReceive_mails(List<Mail2> receive_mails) {
		this.receive_mails = receive_mails;
	}

}
