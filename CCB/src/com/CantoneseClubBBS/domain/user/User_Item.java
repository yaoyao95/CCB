/**
 * 
 */
package com.CantoneseClubBBS.domain.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户的物品
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月24日
 * @version 1.0
 * @update_date
 */
@Entity
@Table
public class User_Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*
	 * @SequenceGenerator(name="user_item",sequenceName="user_item_sequence",
	 * allocationSize = 1)
	 * 
	 * @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_item")
	 */
	private int id;
	/** 论坛货币 */
	private long money;

	/** 粤语歌道具，使用后可以得到若干论坛货币 */
	/*
	 * 一的一端
	 * 
	 * @OneToOne(fetch=FetchType.LAZY, targetEntity=User_Item.class) //
	 * 维护关联关系(从表) 生成外键列
	 * 
	 * @JoinColumn(name="CantoneSong_id", unique=true,
	 * referencedColumnName="id") private CantoneSong CantoneSong;
	 */

	/** 对于user来说，这是主表 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class, // 关联实体类。即本类和哪个类相关联，关联了才可以联合起来查询。
			mappedBy = "user_item") // 不维护关联关系(主表)//mappdeBy:被引用，student表被引用，所以student是从表，teacher是主表
	// mappedBy：(Optional) The field that owns the relationship.
	// This element is only specified on the inverse (non-owning)(没有外键约束的表，主表)
	// side of the association.
	// mappedBy
	// 原理应该是：要查询本类时，要到targetEntity目标实体类--Teacher.class类里找student（mappedBy被映射的）属性。
	// 所以Teacher类包含Student类的实例student（属性），Teacher类是从表，Student是主表。
	private User_ user_;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

}
