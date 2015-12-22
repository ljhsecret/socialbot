package kr.co.opensns.ksbiz.socialbot.balancer.seed;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.config.SeedConfig;
import kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis.ConnectionFactory;

public class DBSeedLoader extends Loadable {

	ConnectionFactory cf;

	public DBSeedLoader() {
		cf = new ConnectionFactory();
	}

	@Deprecated
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

	@Override
	SeedQueue Load(SeedConfig seedConf) {
		SeedQueue queue = new SeedQueue(seedConf.getCrlType());

/*		List<Map<String, Object>> results = cf.selectSeedInfo();
		// System.out.println(results);
		for (Map<String, Object> result : results) {
			queue.put(MapToSeedEntity(result));
		}*/
		
		HashMap<String,String> fields = new HashMap<String,String>();
		
		fields.put("siteId", seedConf.getSite());
		fields.put("seedType", seedConf.getSeedType());
		
		List<SeedDO> results = cf.selectSeedInfo(fields);

		for (SeedDO result : results) {
			queue.put(new SeedEntity(result));
		}

		return queue;	
	}
}
