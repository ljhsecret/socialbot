package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.util.HashMap;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;

public class SharedJobTable {
	private static final short JOB_TABLE_SIZE_MAX = 20;

	private static HashMap<String, JobEntity> SharedJobTable;
	private static SharedJobTable instance;

	private Logger logger;

	private SharedJobTable() {
		this.SharedJobTable = new HashMap<String, JobEntity>();
		logger = Logger.getLogger(SharedJobTable.class);
	}

	public static SharedJobTable getInstance() {
		if (instance == null) {
			synchronized (SharedJobTable.class) {
				instance = new SharedJobTable();
			}
		} 
		return instance;
	}

	public int checkRequireJob() {
		synchronized (SharedJobTable.class) {
			int curSize = SharedJobTable.size();
			return JOB_TABLE_SIZE_MAX - curSize;
		}
	}

	public void update(String jobId, JobStatus status)
			throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (SharedJobTable == null)
				throw new NullPointerException();
			if (SharedJobTable.containsKey(jobId)) {
				
				if(status == JobStatus.DONE){
					SharedJobTable.remove(jobId);
					logger.info("Job was done : Job ID - " +jobId);
					return;
				}
				
				if(status == JobStatus.ERROR){
					SharedJobTable.remove(jobId);
					logger.info("An error has occurred on the job : Job ID - " +jobId);
					return;
				}
				
				SharedJobTable.get(jobId).setStatus(status);
				
				
			} else {
				throw new SharedJobTableException("Duplicate JobTable key : key - "+jobId);
			}
			// SharedJobTable.put(jobKey, field);
		}
	}

	public void put(String jobId, JobEntity job)
			throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (SharedJobTable == null)
				throw new NullPointerException();
			if (SharedJobTable.containsKey(jobId)) {
				throw new SharedJobTableException("");
			}
			SharedJobTable.put(jobId, job);
			return;
		}
	}
}
