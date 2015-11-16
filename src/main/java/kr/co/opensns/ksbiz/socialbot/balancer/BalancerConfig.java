package kr.co.opensns.ksbiz.socialbot.balancer;

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
	
	private List<HashMap<String,String>> seedInfoList;
	private List<HashMap<String,String>> agentInfoList;
	
	private HashMap<String,String> jobWeight;
	
	Logger logger;

	public BalancerConfig() {
		// TODO Auto-generated constructor stub
		configFilePath = CONF_DEFAULT_PATH;
		init();
	}
	
	public BalancerConfig(String configXml) {
		// TODO Auto-generated constructor stub
		configFilePath = configXml;
		init();
	}

	private void init() {
		seedInfoList = new ArrayList<HashMap<String,String>>();
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
				HashMap<String,String> configMap = new HashMap<String,String>();
				configMap.put("channel", e.getChild("channel").getValue());
				configMap.put("site", e.getChild("site").getValue());
				configMap.put("repository", e.getChild("repository").getValue());
				configMap.put("weight", e.getChild("weight").getValue());
				configMap.put("type", e.getChild("type").getValue());
				configMap.put("result-type", e.getChild("result-type").getValue());
				configMap.put("path", e.getChild("path").getValue());
				seedInfoList.add(configMap);
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
			System.out.println("ERROR Fail to init Config");
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR Fail to init Config : FileNotFoundException - filePath:"+configFilePath);
		} catch (IOException e) {
			System.out.println("ERROR Fail to init Config");
		}
	}
	
	public List<HashMap<String,String>> getSeedConfig(){
		return this.seedInfoList;
	}
	
	public List<HashMap<String,String>> getAgentConfig(){
		return this.agentInfoList;
	}
	
	public String getAccessToken() {
		return access_Token;
	}

	public String getResultDir() {
		// TODO Auto-generated method stub
		return resultDir;
	}
	
	public String getTargetDir() {
		// TODO Auto-generated method stub
		return targetDir;
	}
}
