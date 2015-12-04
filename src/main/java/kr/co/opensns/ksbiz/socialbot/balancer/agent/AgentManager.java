package kr.co.opensns.ksbiz.socialbot.balancer.agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;

import org.apache.log4j.Logger;

/**
 * 클래스 설명
 *
 * <pre>
 * <br>
 * <b>History:</b>
 * 		mhyoo, v1.0.0, 2015. 10. 20., 최초작성
 * </pre>
 * 
 * @since 2015. 10. 20., mhyoo, v1.0.0, Created
 * @version 1.0.0
 * @author Min-Ho, Yoo
 *
 */

public class AgentManager {

	private static AgentManager instance;
	private PriorityTable agentQueue;
	private String localFilePath = "config/agents.csv";
	private Logger logger;
	private BalancerConfig conf;

	private AgentManager() {
		agentQueue = new PriorityTable(new AgentComprator());
		logger = Logger.getLogger(this.getClass());
	}

	public static AgentManager getInstance() {
		if (instance == null) {
			synchronized (AgentManager.class) {
				instance = new AgentManager();
			}
		}
		return instance;
	}

	public void setConfig(BalancerConfig conf) {
		this.conf = conf;
		try {
			load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AgentInfo getAgentInfo() {
		return agentQueue.take();
	}

	public void load() throws FileNotFoundException {
		List<HashMap<String, String>> a = conf.getAgentConfig();

		Map<String, Map<String, String>> backupData = loadBackUpDataToMap();
		
		for (HashMap<String, String> m : a) {
			AgentInfo agent = new AgentInfo();
			agent.setIp(m.get("ip"));
			agent.setPort(m.get("port"));
			
			if(backupData.containsKey(agent.getIp())){
				Map<String,String> tmpMap = backupData.get(agent.getIp());
				
				String AverJobProcessingTime =tmpMap.get("AvrJobProcessingTime");
				String JobCount = tmpMap.get("JobCount");
				String LastWorkingTime = tmpMap.get("LastWorkingTime");
				
				agent.setAvrJobProcessingTime(AverJobProcessingTime.equals("null")?0:Long.parseLong(AverJobProcessingTime));
				agent.setJobCount(JobCount.equals("null")?0:Long.parseLong(JobCount));
				agent.setLastWorkingTime(LastWorkingTime.equals("null")?0:Long.parseLong(LastWorkingTime));
			}
			logger.info("agent Load done : " + agent.getIp());
			agentQueue.put(agent);
		}
		
		a=null;
		
		logger.info("Seed Queue Loading Done");
		
	}

	public Map<String, Map<String, String>> loadBackUpDataToMap() {
		Map<String, Map<String, String>> tmp = new HashMap<String, Map<String, String>>();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(localFilePath))));
			String line;

			logger.info("AgentInfo start loading");
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(",");
				if (csv.length < 5)
					continue;

				HashMap<String, String> fields = new HashMap<String, String>();

				fields.put("port", csv[1]);
				fields.put("LastWorkingTime", csv[2].equals("null") ? "0"
						: csv[2]);
				fields.put("JobCount", csv[3].equals("null") ? "0" : csv[3]);
				fields.put("AvrJobProcessingTime", csv[4].equals("null") ? "0"
						: csv[4]);

				tmp.put(csv[0], fields);
			}

			br.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tmp = null;
		}

		return tmp;
	}

	public void save() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(localFilePath))));

//		while(agentQueue.)
		
		bw.close();
	}

	//
	public void update(String ID, Map<String, String> field) {
		synchronized (AgentManager.class) {
			field.get("status");
			field.get("Processing Time");
		}
	}
}
