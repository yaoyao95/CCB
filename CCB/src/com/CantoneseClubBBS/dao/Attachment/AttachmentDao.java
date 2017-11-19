/**
 * 
 */
package com.CantoneseClubBBS.dao.Attachment;

import java.util.Date;
import java.util.List;

import com.CantoneseClubBBS.dao.HibernateDao;
import com.CantoneseClubBBS.domain.user.Attachment;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年5月5日
 * @version 1.0
 * @update_date
 */
public interface AttachmentDao extends HibernateDao {

	/**
	 * 根据path查找Attachment
	 * 
	 * @param path
	 * @return
	 */
	List<Attachment> getAttachmentsByPath(String path);

	/**
	 * 根据上传时间查询附件
	 * 
	 * @param updateDate
	 * @return
	 */
	Attachment getAttachmentByUploadDate(Date updateDate);

}
