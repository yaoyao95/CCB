/**
 * 
 */
package com.CantoneseClubBBS.dao.user;

import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.user.Attachment;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.util.PageModel;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月25日
 * @version 1.0
 * @update_date
 */
public interface UserDao extends HibernateDao {

	/**
	 * @param pageModel
	 * @param user
	 * @return
	 */
	List<User_> getUsersByPage(PageModel pageModel, User_ user);

	/**
	 * @param user
	 * 
	 */
	void saveUser(User_ user);

	/**
	 * 删除user
	 * 
	 * @param user
	 */
	void deleteUser(User_ user);

	/**
	 * 根据id查找user 持久化的
	 * 
	 * @param id
	 */
	User_ getUserbyId(int id);

	/**
	 * 更新user
	 * 
	 * @param user
	 */
	void updateUser(User_ user);

	/**
	 * 查找user 用于检验登录
	 * 
	 * @param user_
	 * @return
	 */
	User_ findUser(User_ user_);

	/**
	 * 通过name查找user
	 * 
	 * @param name
	 * @return 查找的user
	 */
	User_ getUserByName(String name);

	/**
	 * 储存attachment
	 * 
	 * @param attachment
	 */
	int saveAttachment(Attachment attachment);

	/**
	 * 检查登录的用户是否有新的赞
	 * 
	 * @param uid
	 * @return
	 */
	boolean checkNewZanByLoginUserId(int uid);

	/**
	 * 通过id或nam查找用户
	 * 
	 * @param user_
	 * @return
	 */
	User_ getUserbyIdOrName(User_ user_);

}
