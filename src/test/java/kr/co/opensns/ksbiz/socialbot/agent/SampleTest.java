package kr.co.opensns.ksbiz.socialbot.agent;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SampleTest {
	
	public void test() {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
				"config/*-context.xml");

		AgentBean agent = context.getBean(AgentBean.class);
		ArrayBean arrayBean = (ArrayBean) context.getBean("arrayBean");
		
		

		PropertyConfigurator.configure(agent.val("logger.property_file"));
		
		Logger logger = Logger.getLogger(this.getClass());
		
		logger.info("hi");
		logger.debug("error");
		
		System.out.println(agent);
		System.out.println(arrayBean);
	}
	public static void main(String[] args) {
		SampleTest test = new SampleTest();
		test.test();
	}

}
