/*
 * @(#) ConnectionFactory.java 
 *
 * v1.0.0 / 2015. 7. 29. 
 *
 * Copyright ((c) 2015 by OpenSNS, Inc. All Rights Reserved.
 */

package kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.opensns.ksbiz.socialbot.balancer.seed.SeedDO;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ConnectionFactory {

	public static final String NAME_SPACE_SELECT = "myBatisSelectList.";
	public static final String NAME_SPACE_MANAGE = "myBatisManageList.";
	public static SqlSessionFactory sqlMapper = MyBatisManager.getInstance();

	/*public List<Map<String, Object>> selectSeedInfo() {
		SqlSession session = sqlMapper.openSession();
		List<Map<String, Object>> hmap = null;
		try {
			hmap = session.selectList(NAME_SPACE_SELECT + "selectSeedInfo");
		} finally {
			session.commit();
			session.close();
		}
		return hmap;
	}*/
	
	public List<SeedDO> selectSeedInfo() {
		SqlSession session = sqlMapper.openSession();
		List<SeedDO> hmap = null;
		try {
			hmap = session.selectList(NAME_SPACE_SELECT + "selectSeedInfo");
		} finally {
			session.commit();
			session.close();
		}
		return hmap;
	}

	public void insertSeedInfo(HashMap<String, String> seed) {
		SqlSession session = sqlMapper.openSession();
		try {
			session.insert(NAME_SPACE_MANAGE + "insertSeedInfo", seed);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

	public void updateSeedInfo(HashMap<String, String> seed) {
		SqlSession session = sqlMapper.openSession();
		try {
			session.update(NAME_SPACE_MANAGE + "updateSeedInfo", seed);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}
}
