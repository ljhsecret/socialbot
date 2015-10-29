package kr.co.opensns.ksbiz.socialbot.balancer.job;

import java.util.HashMap;

import kr.co.opensns.ksbiz.socialbot.balancer.exception.SharedJobTableException;

public class SharedJobTable {
	private static final short JOB_TABLE_SIZE_MAX = 20;
	
	private static HashMap<String, JobEntity> SharedJobTable;
	private static SharedJobTable instance;
	

	private SharedJobTable() {
		this.SharedJobTable = new HashMap<String, JobEntity>();
	}

	public static SharedJobTable getInstance() {
		if (instance == null) {
			synchronized (SharedJobTable.class) {
				instance = new SharedJobTable();
				return instance;
			}
		} else {
			return instance;
		}
	}

	public int checkRequireJob() {
		synchronized (SharedJobTable.class) {
			int curSize = SharedJobTable.size();
			return JOB_TABLE_SIZE_MAX - curSize;
		}
	}

	public void registJobToTable(JobEntity job) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if(SharedJobTable==null)
				throw new NullPointerException();
			if (SharedJobTable.containsKey(job.ID)) {
				throw new SharedJobTableException("");
			}
			SharedJobTable.put(job.ID, job);
			return;
		}
	}

	public void update(String jobId, HashMap<String, String> field) throws SharedJobTableException {
		synchronized (SharedJobTable.class) {
			if(SharedJobTable==null)
				throw new NullPointerException();
			if (SharedJobTable.containsKey(jobId)) {
				SharedJobTable.get(jobId);
			} else {
				throw new SharedJobTableException("");
			}
			// SharedJobTable.put(jobKey, field);
		}
	}
}
