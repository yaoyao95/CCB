/**
 * 
 */
package com.CantoneseClubBBS.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户所有帖子的情况 应该没什么用处
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月24日
 * @version 1.0
 * @update_date
 */
// @Entity
// @Table
public class User_Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	// private String url;
}
