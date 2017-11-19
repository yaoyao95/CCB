package com.CantoneseClubBBS.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.CantoneseClubBBS.dao.Attachment.AttachmentDao;
import com.CantoneseClubBBS.dao.mail.MailDao;
import com.CantoneseClubBBS.dao.user.UserDao;
import com.CantoneseClubBBS.domain.post.ThatIsGood;
import com.CantoneseClubBBS.domain.user.Attachment;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail;
import com.CantoneseClubBBS.domain.user.mail.Mail2;
import com.CantoneseClubBBS.domain.user.mail.Receive_user;
import com.CantoneseClubBBS.domain.user.mail.Send_user;
import com.CantoneseClubBBS.service.UserService;
import com.CantoneseClubBBS.util.EncryptUtil;
import com.CantoneseClubBBS.util.PageModel;

@Service("userService")
// 加上事务，这个类的所有方法都会生效：
// 方法运行过程中遇到RuntimeException就会对数据库进行rollback，返回到调用方法前的状态
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {
	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "mailDao")
	private MailDao mailDao;
	@Resource(name = "attachmentDao")
	private AttachmentDao attachmentDao;

	/** 分页查询所有的user */
	@Transactional(readOnly = true)
	@Override
	public List<User_> getUsersByPage(PageModel pageModel, User_ user) {
		return userDao.getUsersByPage(pageModel, user);
	}

	/** 添加user */
	@Override
	public boolean saveUser(User_ user) {
		User_ isUserNameExist = userDao
				.findUniqueEntity("select u from User_ as u where u.name = '" + user.getName() + "'");
		if (isUserNameExist == null) {// 用户名不存在，可以添加
			userDao.saveUser(user);
			return false;
		} else // 用户名已存在，不能添加
			return true;
	}

	/** 删除user */
	@Override
	public void delUser(User_ user) {
		userDao.deleteUser(user);
	}

	/** 根据id查找user */
	@Transactional(readOnly = true)
	@Override
	public User_ getUserById(int id) {
		return userDao.getUserbyId(id);
	}

	/**
	 * 更新user信息
	 * 
	 * @param fnpwd
	 * @param newpwd
	 * @param oldpwd
	 */
	public void updateUser(User_ user, String oldpwd, String newpwd, String fnpwd) {
		// 持久化的user ,用于更新
		User_ ufu = userDao.getUserbyId(user.getId());
		// ================更新=================================
		// =======更新密码=======
		if (!StringUtils.isEmpty(oldpwd) && !StringUtils.isEmpty(newpwd) && !StringUtils.isEmpty(fnpwd)) {
			try {
				if (oldpwd.equals(EncryptUtil.decrypt(ufu.getPassword(), EncryptUtil.DB_Input))) {
					if (newpwd.equals(fnpwd)) {
						ufu.setPassword(EncryptUtil.encrypt(newpwd, EncryptUtil.DB_Input));
					} else {
						throw new RuntimeException("新密码和确认密码栏不相同，请重新输入");
					}
				} else {
					throw new RuntimeException("原密码输入不正确，请查证后再试");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 判断头像路径是否存在
		if (!StringUtils.isEmpty(user.getPicture_path())) {
			ufu.setPicture_path(user.getPicture_path());
		}

		// 判断是否添加了附件
		if (user.getAttachments() != null) {
			int a_size = user.getAttachments().size();
			if (a_size > 0) {
				for (int i = 0; i < a_size; i++) {

					Attachment aForUpdate = user.getAttachments().get(i);

					aForUpdate.setUser_(ufu);

					// 储存
					userDao.saveAttachment(aForUpdate);
				}
			}
		}

		// 判断.....
		// if(user.getAttachments().size() > 0 ){
		// //先saveAttachment才能add
		// //ufu.getAttachments().add(e);
		// }
		// ....
		// ufu.setAuthority_level(user.getAuthority_level());
		// ufu.setBirthday(user.getBirthday());
		// ufu.setContact_information(user.getContact_information());
		// ufu.setFn_read_mail(user.isFn_read_mail());
		// ufu.setGame_account(user.getGame_account());
		// ufu.setId(user.getId());
		// ufu.setLevel_(user.getLevel_());
		// ufu.setMail_count(user.getMail_count());
		// ufu.setMails(user.getMails());
		// ufu.setName(user.getName());
		if (!StringUtils.isEmpty(user.getNickname())) {
			ufu.setNickname(user.getNickname());
		}
		// ufu.setOnline_time(user.getOnline_time());
		// ufu.setPassword(user.getPassword());
		// ufu.setPassWord_answer1(user.getPassWord_answer1());
		// ufu.setPassWord_answer2(user.getPassWord_answer2());
		// ufu.setPassword_issue1(user.getPassword_issue1());
		// ufu.setPassword_issue2(user.getPassword_issue2());
		// ufu.setPersonal_home(user.getPersonal_home());
		// ufu.setPicture_path(user.getPicture_path());
		// ufu.setRegister_date(user.getRegister_date());
		if (user.getSex() != 0) {
			ufu.setSex(user.getSex());
		}
		if (!StringUtils.isEmpty(user.getSignature_personality())) {
			ufu.setSignature_personality(user.getSignature_personality());
		}

		if (!StringUtils.isEmpty(user.getCookie_AutoLogin_Key())) {
			ufu.setCookie_AutoLogin_Key(user.getCookie_AutoLogin_Key());
		}

		if (!StringUtils.isEmpty(user.getCookie_AutoLogin_Iv())) {
			ufu.setCookie_AutoLogin_Iv(user.getCookie_AutoLogin_Iv());
		}
		// ufu.setUser_items(user.getUser_items());
	}

	/** 用户登录 */
	@Transactional(readOnly = true)
	@Override
	public User_ login(User_ user_) {
		return userDao.findUser(user_);
	}

	/**
	 * 通过收件人id，分页查询用户收到的邮件
	 * 
	 * @param id
	 *            收件人id
	 * @return 用户收到的邮件list（分页）
	 */
	@Override
	public List<Mail> findUserReceivedMailsByIdByPage(int id) {
		return mailDao.findMailsByReceivedUserId(id);
	}

	/**
	 * 根据uswe的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	@Override
	public Mail getUserMailById(int mailId, int loginedUserId) {
		Mail m = mailDao.getUserMailById(mailId, loginedUserId);
		if (m != null) {
			m.setIs_read(true);
		}
		return m;
	}

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件version2
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	@Override
	public Mail2 getUserMailById2(int mailId, int loginedUserId) {
		Mail2 m = mailDao.getUserMailById2(mailId, loginedUserId);

		if (m != null) {
			List<Receive_user> rus = m.getReceive_users();
			for (Receive_user ru : rus) {
				if (ru.getOne_receive_user().getId() == loginedUserId) {
					ru.setIsRead(1);// 标为已读
				}
				ru.getOne_receive_user().getName();// 解除延迟加载
			}
			List<Send_user> sus = m.getSend_users();
			for (Send_user su : sus) {// 解除延迟加载
				su.getOne_send_user().getName();
				su.getOriginal_send_user().getName();
			}
		}

		return m;
	}

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @return 成功true，失败false
	 */
	@Override
	public boolean delUserCheckedMailsByIds(int[] checkedMailIds) {
		boolean success = mailDao.delUserCheckedMailsByIds(checkedMailIds);
		return success;
	}

	/**
	 * 通过用户名查找用户
	 * 
	 * @param string
	 * @return 查找的user
	 */
	@Override
	public User_ getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件 version2
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @param pageModel
	 * @return
	 */
	@Override
	public List<Mail2> findUserReceivedMails2ByIdByPage(int id, PageModel pageModel) {
		return mailDao.findMails2ByReceivedUserId(id, pageModel);
	}

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @param u
	 * @param ms
	 * @return 成功true，失败false
	 */
	@Override
	public boolean delUserCheckedMailsByIds2(int[] checkedMailIds, User_ u, List<Mail2> ms) {
		boolean success = mailDao.delUserCheckedMailsByIds2(checkedMailIds, u, ms);
		return success;
	}

	/**
	 * 根据登录用户的id获取用户发送的邮件,要进行安全监测
	 * 
	 * @param userId
	 * @param pageModel
	 * @return
	 */
	@Override
	public List<Mail2> findUserSendedMails2ByIdByPage(int id, PageModel pageModel) {
		return mailDao.findSendedMails2BySendedUserId(id, pageModel);
	}

	/**
	 * 添加邮件
	 * 
	 * @param mail2
	 * @param loginUserId
	 * @param names
	 */
	@Override
	public List<String> addMail2(Mail2 mail2, int loginUserId, String names) {
		// 处理页面传来的（多）names。
		String[] theNames = names.split(",");
		User_ theSendUser = userDao.getUserbyId(loginUserId);
		List<User_> theReceiveUsers = new ArrayList<User_>();
		List<String> returnMessages = new ArrayList<String>();
		returnMessages.add("success");
		for (int i = 0; i < theNames.length; i++) {
			User_ u = userDao.getUserByName(theNames[i]);
			if (u != null) {
				theReceiveUsers.add(userDao.getUserByName(theNames[i]));
			} else {
				returnMessages.set(0, "fail");
				returnMessages.add(1, "receiveUserDoesNotExist");
				returnMessages.add(2, theNames[i]);
				return returnMessages;
			}
		}
		mailDao.addMail2(mail2, theSendUser, theReceiveUsers);
		return returnMessages;
	}

	/**
	 * 检查登录的用户是否存在新的短信邮件
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public boolean checkNewMail2ByLoginUserId(int uid) {
		return mailDao.checkNewMail2ByLoginUserId(uid);
	}

	/**
	 * 设置mail为未读
	 * 
	 * @param checkedMailIds
	 * @param uid
	 */
	@Override
	public void setMailUnRead(int[] checkedMailIds, int uid) {
		mailDao.setMailUnRead(checkedMailIds, uid);
	}

	/** 检查登录的用户是否有新的赞 */
	@Override
	public boolean checkNewZanByLoginUserId(int uid) {
		return userDao.checkNewZanByLoginUserId(uid);
	}

	/**
	 * 查询登录用户收到的赞
	 * 
	 * @param id
	 * @param pageModel
	 * @param tigc
	 * @return
	 */
	@Override
	public List<ThatIsGood> selectLoginUserZans(int LoginUserId, PageModel pageModel, ThatIsGood tigc) {
		/** 条件 */
		List<String> params = new ArrayList<String>();
		StringBuilder hql = new StringBuilder();
		hql.append("select t from ThatIsGood as t where t.theAdmiredUser.id =" + LoginUserId);
		// 判断查询条件是否存在
		if (tigc != null) {
			if (!StringUtils.isEmpty(tigc.getTheAdmireUserName())) {
				hql.append(" and t.theAdmireUserName like ?");
				params.add("%" + tigc.getTheAdmireUserName() + "%");
			}
		}
		// 排序:先新再旧
		hql.append(" order by t.issuedDate desc");
		List<ThatIsGood> ts = userDao.findByPage(hql.toString(), pageModel, params);

		// 解除延迟
		for (ThatIsGood t : ts) {
			t.getTheAdmiredUser().getName();
			t.getTheAdmireUser().getName();
			if (t.getThPostReply() != null) {
				t.getThPostReply().getStorey();
			}
			if (t.getThePostTheme() != null) {
				t.getThePostTheme().getTitle();
			}
			Calendar c = Calendar.getInstance();// 当前时间
			c.add(Calendar.DAY_OF_MONTH, -1);// 一天前
			Calendar ThatIsGoodDate = Calendar.getInstance();
			ThatIsGoodDate.setTime(t.getIssuedDate());
			if (ThatIsGoodDate.compareTo(c) > 0) {// 一天以内的提醒
				t.setIsNew(true);
			}
		}

		return ts;
	}

	/**
	 * 通过id或nam查找用户
	 * 
	 * @param user_
	 * @return
	 */
	@Override
	public User_ getUserByIdOrName(User_ user_) {
		return userDao.getUserbyIdOrName(user_);
	}

	/**
	 * 获取用户的权限描述
	 * 
	 * @param user_
	 * @return
	 */
	@Override
	public String getUserAuthorityDescription(User_ user) {
		StringBuilder userAuthority = new StringBuilder();
		if (user.getAuthority().charAt(0) == 'Y') {
			userAuthority.append("总管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(1) == 'Y') {
			userAuthority.append("公告区管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(2) == 'Y') {
			userAuthority.append("教学区管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(3) == 'Y') {
			userAuthority.append("任务区管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(4) == 'Y') {
			userAuthority.append("足迹区管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(5) == 'Y') {
			userAuthority.append("用户管理员：是<br/> ");
		}
		if (user.getAuthority().charAt(6) == 'Y') {
			userAuthority.append("拥有普通用户的权限<br/>");
		}
		if (user.getAuthority().charAt(7) == 'Y') {
			userAuthority.append("被列入黑名单，禁言中<br/>");
		}
		if (user.getAuthority().charAt(8) == 'Y') {
			userAuthority.append("水区管理员：是");
		}
		return userAuthority.toString();
	}

	/**
	 * @param user_
	 * @param isFootprintAdmin_
	 * @param isHighestAdmin_
	 * @param isNoticeAdmin_
	 * @param isTaskAdmin_
	 * @param isTeachingAdmin_
	 * @param isUserAdmin_
	 * @param isForbidden_
	 */
	@Override
	public void userManage(User_ user_, boolean isFootprintAdmin_, boolean isHighestAdmin_, boolean isNoticeAdmin_,
			boolean isTaskAdmin_, boolean isTeachingAdmin_, boolean isUserAdmin_, boolean isForbidden_,
			boolean isWaterAdmin_, boolean isBBSPageAdmin_) {
		User_ ufm = userDao.getUserbyId(user_.getId());

		if (isFootprintAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 4) + "Y" + ufm.getAuthority().substring(5));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 4) + "N" + ufm.getAuthority().substring(5));
		}

		if (isHighestAdmin_) {
			ufm.setAuthority("Y" + ufm.getAuthority().substring(1));
		} else {
			ufm.setAuthority("N" + ufm.getAuthority().substring(1));
		}

		if (isNoticeAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 1) + "Y" + ufm.getAuthority().substring(2));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 1) + "N" + ufm.getAuthority().substring(2));
		}

		if (isTaskAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 3) + "Y" + ufm.getAuthority().substring(4));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 3) + "N" + ufm.getAuthority().substring(4));
		}

		if (isTeachingAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 2) + "Y" + ufm.getAuthority().substring(3));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 2) + "N" + ufm.getAuthority().substring(3));
		}

		if (isUserAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 5) + "Y" + ufm.getAuthority().substring(6));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 5) + "N" + ufm.getAuthority().substring(6));
		}
		if (isForbidden_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 7) + "Y" + ufm.getAuthority().substring(8));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 7) + "N" + ufm.getAuthority().substring(8));
		}
		if (isWaterAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 8) + "Y" + ufm.getAuthority().substring(9));
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 8) + "N" + ufm.getAuthority().substring(9));
		}
		if (isBBSPageAdmin_) {
			ufm.setAuthority(ufm.getAuthority().substring(0, 9) + "Y");
		} else {
			ufm.setAuthority(ufm.getAuthority().substring(0, 9) + "N");
		}
	}

}
