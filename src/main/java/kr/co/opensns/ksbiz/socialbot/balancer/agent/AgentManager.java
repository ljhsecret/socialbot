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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.spi.SyncResolver;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.BalancerConfig;

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

	AgentQueue agentQueue;
	String localFilePath = "config/agents.csv";
	Logger logger;

	public AgentManager() {
		agentQueue = new AgentQueue();
		logger = Logger.getLogger(this.getClass());
	}

	public AgentManager(BalancerConfig conf) {
		this();
	}

	public AgentInfo getAgentInfo() {
		return agentQueue.take();
	}

	public void load() throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(localFilePath))));
		String line;
		try {
			logger.info("AgentInfo start loading");
			while ((line = br.readLine()) != null) {
				String[] csv = line.split(",");

				if (csv.length < 5)
					continue;

				AgentInfo agent = new AgentInfo();
				agent.setIp(csv[0]);
				agent.setPort(csv[1]);
				agent.setLastWorkingTime(csv[2].equals("null")?0:Long.parseLong(csv[2]));
				agent.setJobCount(csv[2].equals("null")?0:Integer.parseInt(csv[3]));
				agent.setAvrJobProcessingTime(csv[2].equals("null")?0:Long.parseLong(csv[4]));
				
				agentQueue.put(agent);
				logger.info("agent Load done : "+agent.getIp());
			}

			br.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void save() throws IOException {
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
//				new FileOutputStream(new File(localFilePath))));
//
//		for (AgentInfo agent : AgentList) {
//			bw.write(agent.toCSV());
//			bw.newLine();
//		}
//		bw.close();
//	}
//
//	public void update(String ID, Map<String, String> field) {
//		synchronized (AgentManager.class) {
//			AgentList.get(0);
//			field.get("status");
//			field.get("Processing Time");
//		}
//	}
}
