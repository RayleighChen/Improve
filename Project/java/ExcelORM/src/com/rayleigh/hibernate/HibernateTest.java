package com.rayleigh.hibernate;

import junit.framework.TestCase;

import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.Transaction;

import com.rayleigh.asm.*;
import com.rayleigh.asm.FreemarkerRender;
import com.rayleigh.asm.render.*;

public class HibernateTest extends TestCase {

	private Configuration config;
	private SessionFactory factory;

	public void setUp() {
		URL url = this.getClass().getResource(
				"/com/rayleigh/hibernate/hibernate.cfg.xml");
		config = new Configuration().configure(url);
		factory = config.buildSessionFactory();
	}

	public void testBuild() throws Exception {
		// 持久类对象描述
		RenderClass rc = new RenderClass();
		ArrayList list = new ArrayList();

		RenderProperty property = new RenderProperty();
		// 添加主键
		property.setName("oid");
		property.setField("oid");
		property.setLength(new Integer(15));
		property.setPrimary(true);
		property.setType(Long.class.getName());
		//property.setSequence("SEQ_PERSON");

		list.add(property);
		// 添加一个name字段
		property = new RenderProperty();
		property.setName("name");
		property.setType(String.class.getName());
		property.setField("name");
		property.setLength(new Integer(20));

		list.add(property);

		rc.setProperties(list);
		// 类名
		rc.setClassName("com.rayleigh.asm.bean.Person");
		rc.setTableName("person1");
		// 开始生成class
		POBuildUtil util = new POBuildUtil();
		util.build(rc.getClassName(),
				//G:\javaSoft\excelutil\bin\com\rayleigh\asm\bean
				"G:\\javaSoft\\excelutil\\bin\\com\\rayleigh\\asm\\bean\\Person.class",
				list);
		// 实例化一个person
		Object person = Class.forName("com.rayleigh.asm.bean.Person").newInstance();// hbmcls.newInstance();

		// 开始生成hbm.xml
		FreemarkerRender render = new FreemarkerRender();
		render.render(rc, Templates.TEMPLATE_HIBERNATE3,
				"G:\\javaSoft\\excelutil\\bin\\com\\rayleigh\\asm\\bean\\person.hbm.xml");
		URL url = this.getClass().getResource("/com/rayleigh/asm/bean/person.hbm.xml");
		config.addURL(url);
		// 更新hibernate.cfg.xml
		HibernateUtil.updateHbmCfg(
				this.getClass().getResource(
						"/com/rayleigh/hibernate/hibernate.cfg.xml"),
				"com/rayleigh/asm/bean/person.hbm.xml");

		PersistentClass model = config.getClassMapping("com.rayleigh.asm.bean.Person");
		// sessionFactory哪下子，快接纳person爷爷进去
		((SessionFactoryImpl) factory).addPersistentClass(model,
				config.getMapping());
		// 生成数据库
		SchemaExport export = new SchemaExport(config,
				((SessionFactoryImpl) factory).getSettings());
		export.execute(true, true, false, true);
		// 测试一下，随便给个名字什么的
		PropertyUtils.setProperty(person, "name", "rayleigh");
		Session session = factory.openSession();
		Transaction tran = session.beginTransaction();
		try {
			// 保存
			session.save(person);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		} finally {
			session.close();
		}
	}

	public void tearDown() {
		factory.close();
	}

}
