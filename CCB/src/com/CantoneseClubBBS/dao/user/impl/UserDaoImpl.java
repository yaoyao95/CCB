package com.CantoneseClubBBS.dao.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.user.UserDao;
import com.CantoneseClubBBS.domain.user.Attachment;
import com.CantoneseClubBBS.domain.user.Game_Account;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.User_Item;
import com.CantoneseClubBBS.util.PageModel;
import com.CantoneseClubBBS.util.*;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoImpl implements UserDao {
	/** 分页查询所有user */
	@Override
	public List<User_> getUsersByPage(PageModel pageModel, User_ user) {
		return this.findByPage("select u from User_ as u where 1=1 order by u.id ", pageModel, new ArrayList<User_>());
	}

	/** 添加user */
	@Override
	public void saveUser(User_ user) {

		User_Item userItem = new User_Item();
		Game_Account game_Account = new Game_Account();
		userItem.setMoney(100);

		// 添加并持久化user相关实体
		this.save(userItem);// 保存主表

		this.save(game_Account);// 保存主表

		user.setUser_item(userItem);

		user.setGame_account(game_Account);
		// 加密密码
		user.setPassword(EncryptUtil.encrypt(user.getPassword(), EncryptUtil.DB_Input));
		// 保存从表user
		this.save(user);
	}

	/** 删除user */
	@Override
	public void deleteUser(User_ user) {
		this.delete(user);
	}

	/** 根据ID查找用户 持久化的 */
	@Override
	public User_ getUserbyId(int id) {
		return this.get(User_.class, id);
	}

	/** 更新user */
	@Override
	public void updateUser(User_ user) {

	}

	/** 查找user 用于登录 */
	@Override
	public User_ findUser(User_ user_) {
		// 定义hql语句
		StringBuilder hql = new StringBuilder();
		hql.append("select u from User_ as u where 1=1");

		// 定义集合封装查询参数
		List<String> params = new ArrayList<String>();

		// 判断条件user_时候存在
		if (user_ != null) {
			// 判断标题条件是否存在
			if (!StringUtils.isEmpty(user_.getName())) {
				// 加上标题条件
				hql.append(" and u.name = ? ");
				params.add(user_.getName());
			}
			// 判断内容条件是否存在
			if (!StringUtils.isEmpty(user_.getPassword())) {
				// 加上内容条件
				hql.append(" and u.password = ?");
				System.out.println(EncryptUtil.encrypt(user_.getPassword(), EncryptUtil.DB_Input));
				params.add(EncryptUtil.encrypt(user_.getPassword(), EncryptUtil.DB_Input));
			}
		} else {
			return null;
		}
		// return
		// this.findUniqueEntity(hql.toString(),user_.getName(),user_.getPassword()
		// );
		Object[] temps = (params == null || params.isEmpty()) ? new Object[] {} : params.toArray();
		return this.findUniqueEntity(hql.toString(), temps);
	}

	/**
	 * 通过name查找user
	 * 
	 * @param name
	 * @return 查找的user
	 */
	@Override
	public User_ getUserByName(String name) {
		return findUniqueEntity("select u from User_ as u where u.name=" + "'" + name + "'");
	}

	/**
	 * 储存attachment
	 * 
	 * @param attachment
	 */
	@Override
	public int saveAttachment(Attachment attachment) {
		return (int) this.save(attachment);
	}

	/**
	 * 检查登录的用户是否有新的赞
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public boolean checkNewZanByLoginUserId(int uid) {
		List<Object> find = find("select t from ThatIsGood as t where t.theAdmiredUser.id=" + uid + "and isRead=0");
		if (find.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 通过id或nam查找用户
	 * 
	 * @param user_
	 * @return
	 */
	@Override
	public User_ getUserbyIdOrName(User_ user_) {
		if (user_ != null) {
			User_ theUser = null;
			if (user_.getId() != 0) {
				theUser = this.get(User_.class, user_.getId());
				if (theUser != null) {
					return theUser;
				} else {
					return this.getUserByName(user_.getName());
				}
			} else if (!StringUtils.isEmpty(user_.getName())) {
				return this.getUserByName(user_.getName());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
