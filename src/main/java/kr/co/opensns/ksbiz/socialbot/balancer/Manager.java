package kr.co.opensns.ksbiz.socialbot.balancer;

import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.config.BalancerConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.exception.BalancerException;

public interface Manager {
	public abstract void load() throws BalancerException;
	public abstract void update(String key, Map<String, String> fields);
	public abstract String monitor();
	public abstract void setConfig(BalancerConfig conf);
}
