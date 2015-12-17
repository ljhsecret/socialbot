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

public class AgentInfo {
	private String ip;
	private String port;
	private static int MAX_JOB_COUNT = 5;
	
	private long jobCount;
	private State status;
	private long avrJobProcessingTime;
	private long lastWorkingTime;

	public String url(String context) {
		return "http://" + ip + ":" + port + "/" + context + "/";
	}

	public boolean isAlive(){
		return status.equals(State.ALIVE);
	}
	
	public void dead(){
		this.status = State.DEAD; 
	}
	
	public double getPriority() {
		if(jobCount == MAX_JOB_COUNT || !isAlive())
			return Double.MAX_VALUE;
		return jobCount * 100000000 + avrJobProcessingTime;
	}

	public long getJobCount() {
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

	public void setJobCount(long jobCount) {
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
	
	public void update(){
		
	}
	
	public String toCSV(){
		return ip+","+port+","+lastWorkingTime+","+jobCount+","+avrJobProcessingTime;
	}
	
	public State getStatus(){
		return status;
	}
	
	enum State {
		ALIVE, DEAD
	};
}
