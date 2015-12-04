package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Iterator;
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
		// System.out.println(results);
		for (Map<String, Object> result : results) {
			queue.put(MapToSeedEntity(result));
		}

		return queue;
	}

	private SeedEntity MapToSeedEntity(Map<String, Object> map) {
		SeedEntity seed = new SeedEntity();

		for (Iterator<String> itr = map.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			Object value = map.get(key);
			if (value == null)
				continue;

			switch (key) {
			case "SITE_ID":
				seed.setSite(value.toString());
				break;
			case "SEED_ID":
				seed.setSeed(value.toString());
				break;
			case "VISIT_CNT":
				seed.setCrawlCount(Integer.parseInt(value.toString()));
				break;
			case "DOC_CNT":
				seed.setCrawledDocCount(Integer.parseInt(value.toString()));
				break;
			case "LAST_VISIT_DATE":
				seed.setLastCrawlDate(Long.parseLong(value.toString()));
				break;
			case "FIRST_VISIT_DATE":
				seed.setFirstCrawlDate(Long.parseLong(value.toString()));
				break;
			case "CURSOR":
				seed.setCursor(value.toString());
				break;
			default:
				break;
			}
		}
		
		return seed;
	}

}
