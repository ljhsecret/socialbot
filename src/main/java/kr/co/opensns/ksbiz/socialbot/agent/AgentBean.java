package kr.co.opensns.ksbiz.socialbot.agent;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgentBean {
	// Spring EL 을 쓰기 때문에 자유롭게 메소드 호출도 가능함. String 의 concat 메소드 호출
	@Value("#{agent['ip']}") private String ip;
	@Value("#{agent['num']}") private int num;
	@Value("#{agent['logger.property_file']}") private String loggerProperties;
	
	
	// util:properties 로 생성된 빈은 java.util.Properties 의 인스턴스이기 때문에
	// 요렇게 Autowired 해서 쓸 수 있다.
	@Autowired Properties agent;
	
	public String val(String key){
		return agent.getProperty(key);
	}
	
	public String toString(){
		return String.format("agent.ip: %s, agent num: %d, logger: $s", ip, num, loggerProperties);
	}
}
