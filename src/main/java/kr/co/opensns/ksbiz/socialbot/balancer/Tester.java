package kr.co.opensns.ksbiz.socialbot.balancer;

import kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis.ConnectionFactory;
import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedDO;

public class Tester {
	public static void main(String[] args) {
		ConnectionFactory cf = new ConnectionFactory();
		
		SeedDO seed = new SeedDO();
		
		seed.setSeedId("1234");
		seed.setActiveYn("N");
		seed.setCursor("123444");
		seed.setDocCnt(3);
		seed.setVisitCnt(0);
		seed.setChannelId(01);
		seed.setSeedType(01);
		seed.setSiteId(01);
		
		
//		cf.insertSeedInfo(seed);
		cf.updateSeedInfo(seed);
	}
}
