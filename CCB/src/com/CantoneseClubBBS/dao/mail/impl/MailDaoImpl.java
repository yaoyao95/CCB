/**
 * 
 */
package com.CantoneseClubBBS.dao.mail.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.dao.mail.MailDao;
import com.CantoneseClubBBS.domain.user.User_;
import com.CantoneseClubBBS.domain.user.mail.Mail;
import com.CantoneseClubBBS.domain.user.mail.Mail2;
import com.CantoneseClubBBS.domain.user.mail.Receive_user;
import com.CantoneseClubBBS.domain.user.mail.Send_user;
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
public class MailDaoImpl extends HibernateDaoImpl implements MailDao {

	/**
	 * 通过收件人id，分页查询用户收到的邮件v2.0 及收件人相关信息
	 * 
	 * @param id
	 *            收件人id
	 * @param pageModel
	 * @return 用户收到的邮件list（分页）
	 */
	@Override
	public List<Mail2> findMails2ByReceivedUserId(int id, PageModel pageModel) {
		List<Mail2> ms = this
				.findByPage(
						("select m from Mail2 as m , Receive_user as r "
								+ "where m.id = r.received_mail.id and r.one_receive_user.id = " + id
								+ " and r.isDelete = '0' order by m.send_date desc"),
						pageModel, new ArrayList<Receive_user>());
		for (int i = 0; i < ms.size(); i++) {
			for (int j = 0; j < ms.get(i).getReceive_users().size(); j++) {
				ms.get(i).getReceive_users().get(j).getOne_receive_user().getName();
			}
			for (int k = 0; k < ms.get(i).getSend_users().size(); k++) {
				ms.get(i).getSend_users().get(k).getOne_send_user().getName();
				ms.get(i).getSend_users().get(k).getOriginal_send_user().getName();
			}
		}

		return ms;
	}

	/**
	 * 通过收件人id，分页查询用户收到的邮件
	 * 
	 * @param id
	 *            收件人id
	 * @return 用户收到的邮件list（分页）
	 */
	@Override
	public List<Mail> findMailsByReceivedUserId(int id) {
		List<Mail> mails = this.findByPage(
				("select m from Mail as m where m.received_user.id=" + id + "order by send_date desc"),
				new PageModel() {
					@Override
					public void setPageSize(int pageSize) {
						pageSize = 10;
						super.setPageSize(pageSize);
					}
				}, new ArrayList<Mail>());

		for (Mail mail : mails) {
			mail.getReceived_user().getName();
			mail.getSended_user().getName();
		}
		return mails;
	}

	/**
	 * 根据mail的id获取用户邮件,要进行安全监测，检查是否是该用户的邮件
	 * 
	 * @param mailId
	 * @param loginedUserId
	 * @return
	 */
	@Override
	public Mail getUserMailById(int mailId, int loginedUserId) {
		return this.findUniqueEntity(("select m from Mail as m where m.id = " + mailId + " and (m.sended_user.id = "
				+ loginedUserId + " or m.received_user.id = " + loginedUserId + ")"));
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
		return this.findUniqueEntity(("select m from Mail2 as m,Send_user as s,Receive_user as r where m.id = " + mailId
				+ "and s.sended_mail.id = m.id and r.received_mail.id = m.id" + " and ( s.one_send_user = "
				+ loginedUserId + " or r.one_receive_user.id = " + loginedUserId + ")"));
	}

	/**
	 * 根据mail的id获取邮件
	 * 
	 * @param mailId
	 * @return
	 */
	@Override
	public Mail2 getUserMailById3(int mailId) {
		return this.findUniqueEntity("select m from Mail2 as m where m.id = " + mailId);
	}

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @return 成功true，失败false
	 */
	@Override
	public boolean delUserCheckedMailsByIds(int[] checkedMailIds) {
		try {
			List<Mail> checkedMails = new ArrayList<Mail>();
			for (int i = 0; i < checkedMailIds.length; i++) {
				Mail m = new Mail();
				m.setId(checkedMailIds[i]);
				checkedMails.add(m);
			}
			this.deleteAll(checkedMails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();// 要rollBack，必须抛出异常给业务层捕捉来rollback。所以没必要在这里try
											// catch
		}
	}

	/**
	 * 删除用户选择的邮件
	 * 
	 * @param checkedMailIds
	 * @param u
	 * @paramList mss
	 * @return 成功true，失败false
	 */
	@Override
	public boolean delUserCheckedMailsByIds2(int[] checkedMailIds, User_ u, List<Mail2> mss) {
		try {
			List<Mail2> ms = mss;
			// 获取被选中的邮件。
			for (Mail2 mail2 : ms) {
				for (int ci : checkedMailIds) {
					if (ci == mail2.getId()) {// 被选中的邮件
						for (Receive_user ru : mail2.getReceive_users()) {
							if (ru.getOne_receive_user().getId() == u.getId()) {// 该邮件的该用户的收件信息
								Receive_user ruForUpdate = this
										.findUniqueEntity("select r from Receive_user as r where r.id =" + ru.getId());
								ruForUpdate.setIsDelete(1);
							}
						}
						for (Send_user su : mail2.getSend_users()) {
							if (su.getOne_send_user().getId() == u.getId()) {// 该邮件的该用户的发件信息
								Send_user suForUpdate = this
										.findUniqueEntity("select s from Send_user as s where s.id =" + su.getId());
								suForUpdate.setIsDelete(1);
							}
						}
					}
				}
			}

			// 数据库是否删除该邮件
			for (int ci : checkedMailIds) {
				Mail2 m = getUserMailById3(ci);
				// 是否要删除
				boolean flag = true;
				List<Receive_user> receive_users = m.getReceive_users();
				for (Receive_user r : receive_users) {
					if (r.getIsDelete() == 0) {
						flag = false;
					}
				}
				List<Send_user> send_users = m.getSend_users();
				for (Send_user s : send_users) {
					if (s.getIsDelete() == 0) {
						flag = false;
					}
				}

				if (flag == true) {
					this.deleteAll(m.getReceive_users());
					this.deleteAll(m.getSend_users());
					this.delete(m);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();// 要rollBack，必须抛出异常给业务层捕捉来rollback。所以没必要在这里try
											// catch
		}
	}

	/**
	 * 通过发件人id，分页查询用户收到的邮件v2.0 及收件人相关信息
	 * 
	 * @param id
	 *            收件人id
	 * @param pageModel
	 * @return 用户发的邮件list（分页）
	 */
	@Override
	public List<Mail2> findSendedMails2BySendedUserId(int id, PageModel pageModel) {
		List<Mail2> ms = this
				.findByPage(
						("select m from Mail2 as m , Send_user as s "
								+ "where m.id = s.sended_mail.id and s.one_send_user.id = " + id
								+ " and s.isDelete = '0' order by m.send_date desc"),
						pageModel, new ArrayList<Send_user>());
		for (int i = 0; i < ms.size(); i++) {
			for (int j = 0; j < ms.get(i).getReceive_users().size(); j++) {
				ms.get(i).getReceive_users().get(j).getOne_receive_user().getName();
			}
			for (int k = 0; k < ms.get(i).getSend_users().size(); k++) {
				ms.get(i).getSend_users().get(k).getOne_send_user().getName();
				ms.get(i).getSend_users().get(k).getOriginal_send_user().getName();
			}

		}
		return ms;
	}

	/**
	 * 添加邮件
	 * 
	 * @param mail2
	 * @param theSendUser
	 * @param theReceiveUsers
	 */
	@Override
	public void addMail2(Mail2 mail2, User_ theSendUser, List<User_> theReceiveUsers) {
		// 保存邮件
		mail2.setSend_date(new Date());
		this.save(mail2);
		// 保存发件人信息
		Send_user s = new Send_user();
		s.setSended_mail(mail2);
		s.setOriginal_send_user(theSendUser);
		s.setOne_send_user(theSendUser);
		this.save(s);

		// 保存收件人信息
		for (User_ tru : theReceiveUsers) {
			Receive_user r = new Receive_user();
			r.setReceived_mail(mail2);
			r.setOne_receive_user(tru);
			this.save(r);
		}
	}

	/**
	 * 检查登录的用户是否存在新的短信邮件
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public boolean checkNewMail2ByLoginUserId(int uid) {
		List<Receive_user> rus = this.find("select r from Receive_user as r where r.one_receive_user.id =" + uid
				+ " and r.isRead = 0 and r.isDelete = 0");
		if (rus.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 设置mail为未读
	 * 
	 * @param checkedMailIds
	 * @param uid
	 */
	@Override
	public void setMailUnRead(int[] checkedMailIds, int uid) {
		for (int i : checkedMailIds) {
			Receive_user r = findUniqueEntity("select r from Receive_user as r " + "where r.received_mail =" + i
					+ " and r.one_receive_user.id =" + uid);
			if (r != null) {
				r.setIsRead(0);
			}
		}
	}
}
