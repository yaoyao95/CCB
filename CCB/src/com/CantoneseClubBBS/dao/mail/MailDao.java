/**
 * 
 */
package com.CantoneseClubBBS.dao.mail;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail;
import com.CantoneseClubBBS.domain.user.mail.Mail2;
import com.CantoneseClubBBS.util.PageModel;

/**
 * 实现对Mails的操作
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月1日
 * @version 1.0
 * @update_date
 */
@Repository("mailDao")
public interface MailDao extends HibernateDao {

	/**
	 * @return
	 */
	List<Mail> findMailsByReceivedUserId(int id);

	/**
	 * 通过收件人id，分页查询用户收到的邮件v2.0 及收件人相关信息
	 * 
	 * @param id
	 *            收件人id
	 * @param pageModel
	 * @return 用户收到的邮件list（分页）
	 */
	List<Mail2> findMails2ByReceivedUserId(int id, PageModel pageModel);

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	Mail getUserMailById(int mailId, int loginedUserId);

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	Mail2 getUserMailById2(int mailId, int loginedUserId);

	/**
	 * 根据mail的id获取邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	Mail2 getUserMailById3(int mailId);

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
	 * 通过发件人id，分页查询用户收到的邮件v2.0 及收件人相关信息
	 * 
	 * @param id
	 *            收件人id
	 * @param pageModel
	 * @return 用户发的邮件list（分页）
	 */
	List<Mail2> findSendedMails2BySendedUserId(int id, PageModel pageModel);

	/**
	 * 添加邮件
	 * 
	 * @param mail2
	 * @param theSendUser
	 * @param theReceiveUsers
	 */
	void addMail2(Mail2 mail2, User_ theSendUser, List<User_> theReceiveUsers);

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

}
