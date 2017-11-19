/**
 * 
 */
package com.CantoneseClubBBS.domain.user.group;

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
 * 用户组成员
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
public class Group_menber {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/** 所属组 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Group.class)
	@JoinColumn(name = "Group_id", referencedColumnName = "id")
	private Group group;

	/** 成员信息 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class)
	@JoinColumn(name = "Menber_id", referencedColumnName = "id")
	private User_ user_;

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
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the user_
	 */
	public User_ getUser_() {
		return user_;
	}

	/**
	 * @param user_
	 *            the user_ to set
	 */
	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

}
