package kr.co.opensns.ksbiz.socialbot.balancer.agent;

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

public class AgentInfo implements Comparable<AgentInfo> {
	private int jobCount;
	private String ip;
	private String port;

	private enum State {
		ALIVE, DEAD
	};

	private State status;
	private long avrJobProcessingTime;
	private long lastWorkingTime;

	@Override
	public int compareTo(AgentInfo o) {
		if (o.status != this.status)
			return this.status == State.ALIVE ? 1 : -1;
		
		double thisPri = this.getPriority();
		double oPri = o.getPriority();
		
		if (thisPri < oPri)
			return -1;
		else if (thisPri > oPri)
			return 1;
		else
			return 0;

	}

	public String url(String context) {
		return "http://" + ip + ":" + port + "/" + context + "/";
	}

	private double getPriority() {
		return jobCount * 100000000 + avrJobProcessingTime;
	}

	public int getJobCount() {
		return jobCount;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public long getAvrJobProcessingTime() {
		return avrJobProcessingTime;
	}

	public long getLastWorkingTime() {
		return lastWorkingTime;
	}

	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setAvrJobProcessingTime(long avrJobProcessingTime) {
		this.avrJobProcessingTime = avrJobProcessingTime;
	}

	public void setLastWorkingTime(long lastWorkingTime) {
		this.lastWorkingTime = lastWorkingTime;
	}
	
	public String toCSV(){
		return ip+","+port+","+lastWorkingTime+","+jobCount+","+avrJobProcessingTime;
	}
}
