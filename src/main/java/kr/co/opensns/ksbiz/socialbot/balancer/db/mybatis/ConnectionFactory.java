/*
 * @(#) ConnectionFactory.java 
 *
 * v1.0.0 / 2015. 7. 29. 
 *
 * Copyright ((c) 2015 by OpenSNS, Inc. All Rights Reserved.
 */

package kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ConnectionFactory {

	public static final String NAME_SPACE_SELECT = "myBatisSelectList.";
	public static final String NAME_SPACE_MANAGE = "myBatisManageList.";
	public static SqlSessionFactory sqlMapper = MyBatisManager.getInstance();

	public List<Map<String, Object>> selectSeedInfo() {
		SqlSession session = sqlMapper.openSession();
		List<Map<String, Object>> hmap = null;
		try {
			hmap = session.selectList(NAME_SPACE_SELECT + "selectSeedInfo");
		} finally {
			session.commit();
			session.close();
		}
		return hmap;
	}
	
	/*
	public Map<String, Object> selectTopicData(int top_seq) {
		SqlSession session = sqlMapper.openSession();
		Map<String, Object> hmap = null;
		try {
			hmap = session.selectOne(NAME_SPACE_SELECT + "selectTopicData",
					top_seq);
		} finally {
			session.commit();
			session.close();
		}
		return hmap;
	}

	public int selectCategorybyTopic(int top_seq) {
		SqlSession session = sqlMapper.openSession();
		int categorySN;
		try {
			categorySN = session.selectOne(NAME_SPACE_SELECT
					+ "selectCategorybyTopic", top_seq);
		} finally {
			session.commit();
			session.close();
		}
		return categorySN;
	}

	public List<Object> selectRelTermInfo(int top_seq) {
		SqlSession session = sqlMapper.openSession();
		List<Object> categorySN;
		try {
			categorySN = session.selectList(NAME_SPACE_SELECT
					+ "selectRelTermInfo", top_seq);
		} finally {
			session.commit();
			session.close();
		}
		return categorySN;
	}

	public void insertAnalHistory(AnalWorkManager tpic) {
		SqlSession session = sqlMapper.openSession();

		try {
			session.insert(NAME_SPACE_MANAGE + "insertAnalHistory", tpic);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

	public void updateAnalStatus(AnalWorkManager tpic) {
		SqlSession session = sqlMapper.openSession();

		try {
			session.update(NAME_SPACE_MANAGE + "updateAnalStatus", tpic);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

	public void updateEndStatus(AnalWorkManager tpic) {
		SqlSession session = sqlMapper.openSession();

		try {
			session.update(NAME_SPACE_MANAGE + "updateEndStatus", tpic);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
			System.out.println("End State Update done");
		}
	}*/
}
