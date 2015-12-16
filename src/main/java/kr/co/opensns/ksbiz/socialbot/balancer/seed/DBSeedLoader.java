package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Date;
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
		SeedQueue queue = new SeedQueue(01);

/*		List<Map<String, Object>> results = cf.selectSeedInfo();
		// System.out.println(results);
		for (Map<String, Object> result : results) {
			queue.put(MapToSeedEntity(result));
		}*/
		
		List<SeedDO> results = cf.selectSeedInfo();

		for (SeedDO result : results) {
			queue.put(new SeedEntity(result));
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
				seed.setSiteId(Integer.parseInt(value.toString()));
				break;
			case "SEED_ID":
				seed.setSeedId(value.toString());
				break;
			case "VISIT_CNT":
				seed.setVisitCnt(Integer.parseInt(value.toString()));
				break;
			case "DOC_CNT":
				seed.setDocCount(Integer.parseInt(value.toString()));
				break;
			case "LAST_VISIT_DATE":
				seed.setLastVisitDate((Date) value);
				break;
			case "FIRST_VISIT_DATE":
				seed.setFirstVisitDate((Date)value);
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
