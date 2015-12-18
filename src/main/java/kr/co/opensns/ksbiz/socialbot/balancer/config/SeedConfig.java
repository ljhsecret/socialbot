package kr.co.opensns.ksbiz.socialbot.balancer.config;

public class SeedConfig {
	String channel;
	String site;
	String repository;
	String repositoryPath;
	String crawlWeight;
	String seedType;
	String crlType;
	String contentsType;
	
	public String getChannel() {
		return channel;
	}
	public String getSite() {
		switch (site.toLowerCase()) {
		case "instagram":
			return "1";
		case "twitter":
			return  "2";
		}
		return null;
	}
	public String getRepository() {
		return repository;
	}
	public String getRepositoryPath() {
		return repositoryPath;
	}
	public String getCrawlWeight() {
		return crawlWeight;
	}
	public String getSeedType() {
		switch (seedType.toLowerCase()) {
		case "id":
			return "1";
		case "docid":
			return  "2";
		}
		return null;
	}
	public String getCrlType() {
		return crlType;
	}
	public String getContentsType() {
		return contentsType;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	public void setCrawlWeight(String crawlWeight) {
		this.crawlWeight = crawlWeight;
	}
	public void setSeedType(String seedType) {
		this.seedType = seedType;
	}
	public void setCrlType(String crlType) {
		this.crlType = crlType;
	}
	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
	}
}
