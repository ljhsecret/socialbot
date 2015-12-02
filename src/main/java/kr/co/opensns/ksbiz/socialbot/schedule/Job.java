package kr.co.opensns.ksbiz.socialbot.schedule;

public class Job {
	private String jobId;
	private int channelId;
	private int siteId;
	private String seed;
	private String crawlType;
	private String contentType;
	private String cursor;
	
	public String getJobId() {
		return jobId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	public void setChannelId(String channelId) {
		this.channelId = Integer.parseInt(channelId);
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public void setSiteId(String siteId) {
		this.siteId = Integer.parseInt(siteId);
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(String crawlType) {
		this.crawlType = crawlType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Job(String jobId, int channelId, int siteId, String seed,
			String crawlType, String contentType, String cursor) {
		super();
		this.jobId = jobId;
		this.channelId = channelId;
		this.siteId = siteId;
		this.seed = seed;
		this.crawlType = crawlType;
		this.contentType = contentType;
		this.cursor = cursor;
	}
	
	public Job(String jobId, String channelId, String siteId, String seed,
			String crawlType, String contentType, String cursor) {
		super();
		this.jobId = jobId;
		this.channelId = Integer.parseInt(channelId);
		this.siteId = Integer.parseInt(siteId);
		this.seed = seed;
		this.crawlType = crawlType;
		this.contentType = contentType;
		this.cursor = cursor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + channelId;
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((crawlType == null) ? 0 : crawlType.hashCode());
		result = prime * result + ((cursor == null) ? 0 : cursor.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((seed == null) ? 0 : seed.hashCode());
		result = prime * result + siteId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		return true;
	}
}
