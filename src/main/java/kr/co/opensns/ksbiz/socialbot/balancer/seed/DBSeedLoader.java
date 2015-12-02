package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.List;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis.ConnectionFactory;

public class DBSeedLoader extends Loadable {

	ConnectionFactory cf;

	public DBSeedLoader() {
		cf = new ConnectionFactory();
	}

	@Override
	SeedQueue Load(String path, String type) {
		SeedQueue queue = new SeedQueue(type);
		
		List<Map<String, Object>> results = cf.selectSeedInfo();

		for (Map<String, Object> result : results) {
			queue.put(MapToSeedEntity(result));
		}

		return null;
	}

	private SeedEntity MapToSeedEntity(Map<String, Object> map) {
		SeedEntity seed = new SeedEntity();

		seed.setSite(map.get("SITE_ID").toString());
		seed.setSeed(map.get("SEED_ID").toString());
		seed.setCursor(map.get("CURSOR").toString());
		seed.setCrawlCount(Integer.parseInt(map.get("VISIT_CNT").toString()));
		seed.setCrawledDocCount(Integer.parseInt(map.get("DOC_CNT").toString()));
		seed.setLastCrawlDate(Integer.parseInt(map.get("LAST_VISIT_DATE").toString()));

		return seed;
	}

}
