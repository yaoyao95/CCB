/**
 * 
 */
package com.CantoneseClubBBS.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 用户游戏账号状态
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月25日
 * @version 1.0
 * @update_date
 */
@Entity
@Table
public class Game_Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // @SequenceGenerator(name="game_account",sequenceName="Game_Account_sequence",allocationSize
													// = 1)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="game_account")
	private int id;
	/** 用于强化能力值 */
	@Column(nullable = false)
	private int strengthen_number = 0;
	/** 体力 */
	@Column(nullable = false)
	private int vit = 0;
	/** 力量 */
	@Column(nullable = false)
	private int str = 0;
	/** 防御 */
	@Column(nullable = false)
	private int def = 0;
	/** 一的一端 */
	/** 相对于user，这是主表 */
	@OneToOne(fetch = FetchType.LAZY, targetEntity = User_.class, // 关联实体类。即本类和哪个类相关联，关联了才可以联合起来查询。
			mappedBy = "game_account") // 不维护关联关系(主表)//mappdeBy:被引用，student表被引用，所以student是从表，teacher是主表
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

	public int getVit() {
		return vit;
	}

	public void setVit(int vit) {
		this.vit = vit;
	}

	public int getStr() {
		return str;
	}

	public void setStr(int str) {
		this.str = str;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getStrengthen_number() {
		return strengthen_number;
	}

	public void setStrengthen_number(int strengthen_number) {
		this.strengthen_number = strengthen_number;
	}

	public User_ getUser_() {
		return user_;
	}

	public void setUser_(User_ user_) {
		this.user_ = user_;
	}

}
