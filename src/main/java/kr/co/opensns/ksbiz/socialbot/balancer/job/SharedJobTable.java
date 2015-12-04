package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.util.HashMap;

import org.apache.log4j.Logger;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;

public class SharedJobTable {
	private static final short JOB_TABLE_SIZE_MAX = 5;

	private static HashMap<String, JobEntity> SharedJobTable;
	private static SharedJobTable instance;

	private JobStatusListener listener;

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

	public void setJobStatusListener(JobStatusListener listener) {
		this.listener = listener;
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

				if (status == JobStatus.DONE) {
					SharedJobTable.remove(jobId);
					logger.info("Job was done : Job ID - " + jobId);

					return;
				}

				if (status == JobStatus.ERROR) {
					SharedJobTable.remove(jobId);
					// logger.info("An error has occurred on the job : Job ID - "
					// +jobId);
					return;
				}

				SharedJobTable.get(jobId).setStatus(status);

			} else {
				throw new SharedJobTableException(
						"Job key Not Found from JobTable  : key - " + jobId);
			}
		}
	}

	public JobEntity get(String jobId) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (SharedJobTable.containsKey(jobId)) {
				throw new SharedJobTableException("this job(" + jobId
						+ ") is not exist in JobTable");
			} else {
				return SharedJobTable.get(jobId);
			}
		}
	}

	public void put(String jobId, JobEntity job) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if (SharedJobTable == null)
				throw new NullPointerException();
			if (SharedJobTable.containsKey(jobId)) {
				throw new SharedJobTableException(
						"Duplicate JobTable key : key - " + jobId);
			}
			SharedJobTable.put(jobId, job);
			return;
		}
	}
}
