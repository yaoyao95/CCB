/**
 * 
 */
package com.CantoneseClubBBS.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.CantoneseClubBBS.domain.Section;

/**
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017年4月30日
 * @version 1.0
 * @update_date
 */
public class SystemInitializeAndDestroy implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 系统初始化后的各种操作

		// 模拟action请求
		// String url_str =
		// "http://localhost:8056//CantoneseClubBBS/init/initOrUpdateSections";
		// try{
		// URL url = new URL(url_str);
		// HttpURLConnection connection = (HttpURLConnection)
		// url.openConnection();
		// connection.connect();
		// connection.disconnect();
		// //connection.getResponseCode();
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		// new InitAction().initOrUpdateSections();

		/*
		 * 要与数据库链接，就要开启（建立）新会话session，要开启新会话，就需要用到sessionFactory工厂，
		 * 要得到sessionFactory可以创建也可以获取环境中已存在的sessionFactory，
		 * Spring容器中配置的（初始化的）sessionFactory默认是单例模式(可以修改)，所以需要获取而不是创建
		 * 所以要先获取Spring容器，而Spring容器也是可以创建或获取环境中已存在的。
		 * B/S架构中，一般系统会管理一个Spring容器，系统启动时创建，系统关闭时销毁。其他Spring容器，程序员可以自己创建和管理。
		 * 所以，我们要根据需要，选择用哪个Spring容器.
		 * 系统管理的Spring容器一般是主要的Spring容器，已经配置好，不需要我们再去配置.
		 * 所以一般可以用系统的Spring容器去获取sessionFactory来开启session
		 * 要获取系统的Spring容器可以通过WebApplicationContextUtils工具类和必须的参数ServletContext
		 * 
		 * 关于ServletContext
		 * 我们知道一个ServletContext对象对应一个web应用，我们也可以称ServletContext是web应用的上下文对象。
		 * 它能够实现web应用中Servlet之间的数据通信，以及获取全局初始化参数等功能。下面我们就来一一介绍这两种功能。
		 */

		// 获取系统的ApplicationContext(Spring 容器)
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		// 获取sessionFactory
		SessionFactory sessionFactory = ac.getBean("sessionFactory", SessionFactoryImpl.class);
		// 建立会话session
		Session session = sessionFactory.openSession();
		// 开启事务
		Transaction transaction = session.beginTransaction();

		// =====================利用 Session完成所有的持久化操作===================

		List<Section> sections = new ArrayList<Section>();

		sections = session.createQuery("select s from Section as s order by s.id").list();

		/** section集合 */
		List<Section> ss = new ArrayList<Section>();

		/** section各小版块名字 */
		List<String> sns = new ArrayList<String>();

		/** section各小板块描述 */
		List<String> sds = new ArrayList<String>();

		/** 教学区section集合 */
		List<Section> tss = new ArrayList<Section>();

		/** 教学区各小板块名字 */
		List<String> tsns = new ArrayList<String>();
		tsns.add("请选择");

		/** 教学区各小板块描述 */
		List<String> tsds = new ArrayList<String>();

		/** 水区section集合 */
		List<Section> wss = new ArrayList<Section>();

		/** 水区各小板块名字 */
		List<String> wsns = new ArrayList<String>();
		wsns.add("请选择");

		/** 水区各小板块描述 */
		List<String> wsds = new ArrayList<String>();

		/** 足迹区集合 */
		List<Section> zjs = new ArrayList<Section>();

		/** 足迹区各小板块名字 */
		List<String> zjns = new ArrayList<String>();
		zjns.add("请选择");

		/** 足迹区各小板块描述 */
		List<String> zjds = new ArrayList<String>();

		// 对sections进行分类。
		for (Section section : sections) {
			sns.add(section.getName());
			sds.add(section.getDescription());
			ss.add(section);

			if (section.getRegion_name().equals("教学")) {
				tsns.add(section.getName());
				tsds.add(section.getDescription());
				tss.add(section);
			}
			if (section.getRegion_name().equals("水区")) {
				wsns.add(section.getName());
				wsds.add(section.getDescription());
				wss.add(section);
			}
			if (section.getRegion_name().equals("足迹")) {
				zjns.add(section.getName());
				zjds.add(section.getDescription());
				zjs.add(section);
			}

		}
		// 获取application
		ServletContext application = sce.getServletContext();
		// 把数据加入到application里
		application.setAttribute("sns", sns);
		application.setAttribute("sds", sds);
		application.setAttribute("ss", ss);
		application.setAttribute("tsns", tsns);
		application.setAttribute("tsds", tsds);
		application.setAttribute("tss", tss);
		application.setAttribute("wsns", wsns);
		application.setAttribute("wsds", wsds);
		application.setAttribute("wss", wss);
		application.setAttribute("zjns", zjns);

		// ====================主页默认歌曲====================
		// 默认为海阔天空，非自动播放
		String hSong = null;
		hSong = "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=80 height=86;"
				+ " src=\"//music.163.com/outchain/player?type=2&id=347230&auto=0&height=66\"></iframe>";
		application.setAttribute("hSong", hSong);

		// 事务提交或回滚
		transaction.commit();
		// 关闭Session与SessionFactory
		session.close();
		// sessionFactory.close();
	}
}
