/**
 * 
 */
package com.CantoneseClubBBS.dao.Attachment.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.CantoneseClubBBS.dao.Attachment.AttachmentDao;
import com.CantoneseClubBBS.dao.impl.HibernateDaoImpl;
import com.CantoneseClubBBS.domain.user.Attachment;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月5日
 * @version 1.0
 * @update_date
 */
@Repository("attachmentDao")
public class AttachmentDaoImpl extends HibernateDaoImpl implements AttachmentDao {

	/**
	 * 根据path查找Attachment
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public List<Attachment> getAttachmentsByPath(String path) {
		return this.find("select a from Attachment as a where a.path=" + "'" + path + "'");
	}

	/**
	 * 根据上传时间查询附件
	 * 
	 * @param updateDate
	 * @return
	 */
	@Override
	public Attachment getAttachmentByUploadDate(Date updateDate) {
		System.out.println("select a from Attachment as a where a.updateDate=" + "'" + updateDate + "'");
		return this.findUniqueEntity("select a from Attachment as a where a.updateDate=" + "'" + updateDate + "'");
	}

}
