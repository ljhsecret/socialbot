package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.spi.SyncResolver;

/**
 * 클래스 설명
 *
 *<pre><br>
 *<b>History:</b>
 *		mhyoo, v1.0.0, 2015. 10. 20., 최초작성  
 *</pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class AgentManager {
	
	List<AgentInfo> AgentList;
	
	public AgentManager(){
		AgentList = new ArrayList<AgentInfo>();
	}
	
	public AgentInfo getAgentInfo(){
		synchronized (AgentManager.class) {
			Collections.sort(AgentList);
		}	
	
		return AgentList.get(0);
	}
	
	public void load(){
		
	}

	public void update(String ID, Map<String, String> field) {
		synchronized (AgentManager.class) {
			AgentList.get(0);
			field.get("status");
			field.get("Processing Time");
		}
	}
}
