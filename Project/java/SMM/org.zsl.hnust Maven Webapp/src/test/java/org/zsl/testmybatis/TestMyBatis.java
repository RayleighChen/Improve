//package org.zsl.testmybatis;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.cn.hnust.pojo.User;
//import com.cn.hnust.service.IUserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)		//琛ㄧず缁ф壙浜哠pringJUnit4ClassRunner绫�
//@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
//
//public class TestMyBatis {
//	private static Logger logger = Logger.getLogger(TestMyBatis.class);
////	private ApplicationContext ac = null;
//	@Resource
//	private IUserService userService = null;
//
////	@Before
////	public void before() {
////		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
////		userService = (IUserService) ac.getBean("userService");
////	}
//
//	@Test
//	public void test1() {
//		User user = userService.getUserById(1);
//		// System.out.println(user.getUserName());
//		// logger.info("鍊硷細"+user.getUserName());
//		logger.info(JSON.toJSONString(user));
//	}
//}
