package com.CantoneseClubBBS.service;

import java.util.List;

import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail;
import com.CantoneseClubBBS.domain.user.mail.Mail2;
import com.CantoneseClubBBS.util.PageModel;

public interface UserService {
	/** 分页获取所有的user */
	List<User_> getUsersByPage(PageModel pageModel, User_ user);

	/** 添加user */
	boolean saveUser(User_ user);

	/** 删除user */
	void delUser(User_ user);

	/** 根据id获取user */
	User_ getUserById(int id);

	/**
	 * 更新user信息
	 * 
	 * @param fnpwd
	 * @param newpwd
	 * @param oldpwd
	 */
	void updateUser(User_ user, String oldpwd, String newpwd, String fnpwd);

	/**
	 * 用户登录
	 * 
	 * @param user_
	 * @return 返回登录的user，不存在就为空
	 */
	User_ login(User_ user_);

	/**
	 * 通过收件人id，分页查询用户收到的邮件
	 * 
	 * @param id
	 *            收件人id
	 * @return 用户收到的邮件list（分页）
	 */
	List<Mail> findUserReceivedMailsByIdByPage(int id);

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	Mail getUserMailById(int id, int loginedUserId);

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件version2
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	Mail2 getUserMailById2(int id, int loginedUserId);

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @return 成功true，失败false
	 */
	boolean delUserCheckedMailsByIds(int[] checkedMailIds);

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @param u
	 * @param ms
	 * @return 成功true，失败false
	 */
	boolean delUserCheckedMailsByIds2(int[] checkedMailIds, User_ u, List<Mail2> ms);

	/**
	 * 通过用户名查找用户
	 * 
	 * @param string
	 * @return 查找的user
	 */
	User_ getUserByName(String string);

	/**
	 * 根据user的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件 version2
	 * 
	 * @param id
	 * @param pageModel
	 * @return
	 */
	List<Mail2> findUserReceivedMails2ByIdByPage(int id, PageModel pageModel);

	/**
	 * 根据user的id获取用户邮件检查是否是该用户发送的邮件 version2
	 * 
	 * @param id
	 * @param pageModel
	 * @return
	 */
	List<Mail2> findUserSendedMails2ByIdByPage(int id, PageModel pageModel);

	/**
	 * 添加邮件
	 * 
	 * @param mail2
	 * @param loginUserId
	 * @param names
	 * @retrun
	 */
	List<String> addMail2(Mail2 mail2, int loginUserId, String names);

	/**
	 * 检查登录的用户是否存在新的短信邮件
	 * 
	 * @param uid
	 * @return
	 */
	boolean checkNewMail2ByLoginUserId(int uid);

	/**
	 * 设置mail为未读
	 * 
	 * @param checkedMailIds
	 * @param uid
	 */
	void setMailUnRead(int[] checkedMailIds, int uid);

	/**
	 * 检查登录的用户是否有新的赞
	 * 
	 * @param uid
	 * @return
	 */
	boolean checkNewZanByLoginUserId(int uid);

	/**
	 * 查询登录用户收到的赞
	 * 
	 * @param id
	 * @param pageModel
	 * @param tigc
	 * @return
	 */
	List<ThatIsGood> selectLoginUserZans(int id, PageModel pageModel, ThatIsGood tigc);

	/**
	 * 通过id或nam查找用户
	 * 
	 * @param user_
	 * @return
	 */
	User_ getUserByIdOrName(User_ user_);

	/**
	 * 获取用户的权限描述
	 * 
	 * @param user_
	 * @return
	 */
	String getUserAuthorityDescription(User_ user_);

	/**
	 * @param user_
	 * @param isFootprintAdmin_
	 * @param isHighestAdmin_
	 * @param isNoticeAdmin_
	 * @param isTaskAdmin_
	 * @param isTeachingAdmin_
	 * @param isUserAdmin_
	 * @param isForbidden_
	 * @param isBBSPageAdmin_
	 * @param isWaterAdmin_
	 */
	void userManage(User_ user_, boolean isFootprintAdmin_, boolean isHighestAdmin_, boolean isNoticeAdmin_,
			boolean isTaskAdmin_, boolean isTeachingAdmin_, boolean isUserAdmin_, boolean isForbidden_,
			boolean isWaterAdmin_, boolean isBBSPageAdmin_);
}
