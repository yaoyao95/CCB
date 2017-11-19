/**
 * 
 */
package com.CantoneseClubBBS.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 系统初始化时需要执行的action
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月29日
 * @version 1.0
 * @update_date
 */
public class InitSystemLister implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		System.out.println(
				"====================================进来了initSystem==============================================================");
		System.out.println(
				"=====================================进来了initSystem==============================================");
		// 创建Spring容器 程序员自己管理
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
				"E:\\SoftwareEngineering\\apache-tomcat-8.0.43\\webapps\\CantoneseClubBBS\\WEB-INF\\applicationContext-util.xml");
		// InitAction init = (InitAction)context.getBean("initAction");
		try {
			// do something....
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.close();
	}
}
