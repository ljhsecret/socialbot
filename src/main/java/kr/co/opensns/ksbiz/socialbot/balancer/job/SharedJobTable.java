package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;

public class SharedJobTable {
	private static final short JOB_TABLE_SIZE_MAX = 100;

	private static HashMap<String, JobEntity> jobTable;
	private static SharedJobTable instance;

	private Logger logger;

	private SharedJobTable() {
		SharedJobTable.jobTable = new HashMap<String, JobEntity>();
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
			int curSize = jobTable.size();
			return JOB_TABLE_SIZE_MAX - curSize;
		}
	}

	public void update(String jobId, JobStatus status)
			throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (jobTable == null)
				throw new NullPointerException();
			if (jobTable.containsKey(jobId)) {

				if (status == JobStatus.DONE) {
					jobTable.remove(jobId);
					logger.info("Job was done : Job ID - " + jobId);

					return;
				}

				if (status == JobStatus.ERROR) {
					jobTable.remove(jobId);
					logger.info("An error has occurred on the job : Job ID - "
					 +jobId);
					return;
				}

				jobTable.get(jobId).setStatus(status);

			} else {
				throw new SharedJobTableException(
						"Job key Not Found from JobTable  : key - " + jobId);
			}
		}
	}

	public JobEntity get(String jobId) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (!jobTable.containsKey(jobId)) {
				throw new SharedJobTableException("this job(" + jobId
						+ ") is not exist in JobTable");
			} else {
				return jobTable.get(jobId);
			}
		}
	}

	public void put(String jobId, JobEntity job) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (jobTable == null)
				throw new NullPointerException();
			if (jobTable.containsKey(jobId)) {
				throw new SharedJobTableException(
						"Duplicate JobTable key : key - " + jobId);
			}
			jobTable.put(jobId, job);
			return;
		}
	}

	public Set<String> JobIdSet(){
		return jobTable.keySet();
	}
	
	public int getSize(){
		return jobTable.size();
	}
	
	public int getMaxSize(){
		return SharedJobTable.JOB_TABLE_SIZE_MAX;
	}
	
}
