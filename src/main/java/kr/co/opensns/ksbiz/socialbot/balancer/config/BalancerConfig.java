package kr.co.opensns.ksbiz.socialbot.balancer.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

public class BalancerConfig {
	private static final String CONF_DEFAULT_PATH = "config/Configuration.xml";
	private String configFilePath;
	private String access_Token;
	private String logPropertyFile;
	private String resultDir;
	private String targetDir;
	
	private List<SeedConfig> seedInfoList;
	private List<HashMap<String,String>> agentInfoList;
	
	private HashMap<String,String> jobWeight;
	
	Logger logger;

	public BalancerConfig() {
		this(null);
	}
	
	public BalancerConfig(String configXml) {
		if(configXml==null)
			configFilePath = CONF_DEFAULT_PATH;
		else
			configFilePath = configXml;
		init();
	}

	private void init() {
		seedInfoList = new ArrayList<SeedConfig>();
		agentInfoList = new ArrayList<HashMap<String,String>>();
		
		Element element;
		SAXBuilder builder = new SAXBuilder();

		try {
			Document doc = builder.build(new File(configFilePath));

			element = (Element) (XPath.selectSingleNode(doc,
					"/balancer/logger/propertiy-file"));
			logPropertyFile = element.getValue();
			PropertyConfigurator.configure(logPropertyFile);
			
			logger = Logger.getLogger(this.getClass());
			logger.info(element.getName() + " = " + logPropertyFile);

			element = (Element) (XPath.selectSingleNode(doc, "/balancer/seed-info"));
			List<Element> subElements = (List<Element>) element.getChildren();
			
			for(Element e : subElements){
				SeedConfig seedConf = new SeedConfig();
				
				seedConf.setChannel(e.getChild("channel").getValue());
				seedConf.setSite(e.getChild("site").getValue());
				seedConf.setSeedType(e.getChild("type").getValue());
				seedConf.setContentsType(e.getChild("contents-type").getValue());
				seedConf.setCrawlWeight(e.getChild("weight").getValue());
				seedConf.setCrlType(e.getChild("crawl-type").getValue());
				seedConf.setRepository(e.getChild("repository").getChild("type").getValue());
				seedConf.setRepositoryPath(e.getChild("repository").getChild("path").getValue());
				
				seedInfoList.add(seedConf);
			}
			
			element = (Element) (XPath.selectSingleNode(doc, "/balancer/agent-info"));
			subElements = element.getChildren();
			for(Element e : subElements){
				HashMap<String,String> configMap = new HashMap<String,String>();
				configMap.put("ip", e.getChild("ip").getValue());
				configMap.put("port", e.getChild("port").getValue());
				agentInfoList.add(configMap);
			}

		} catch (JDOMException jdome) {
			System.out.println("ERROR Fail to init Config : "+jdome.getMessage());
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR Fail to init Config : FileNotFoundException - filePath:"+configFilePath);
		} catch (IOException e) {
			System.out.println("ERROR Fail to init Config : "+e.getMessage());
		}
	}
	
	public List<SeedConfig> getSeedConfig(){
		return this.seedInfoList;
	}
	
	public List<HashMap<String,String>> getAgentConfig(){
		return this.agentInfoList;
	}
	
	public String getAccessToken() {
		return access_Token;
	}

	public String getResultDir() {
		return resultDir;
	}
	
	public String getTargetDir() {
		return targetDir;
	}
}
