/*
 * @(#) MyBatisManager.java 
 *
 * v1.0.0 / 2015. 7. 29. 
 *
 * Copyright ((c) 2015 by OpenSNS, Inc. All Rights Reserved.
 */

package kr.co.opensns.ksbiz.socialbot.balancer.db.mybatis;


import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisManager {

	public static SqlSessionFactory sqlMapper = null;
	
	public static SqlSessionFactory getInstance(){
		if(sqlMapper == null){
			try{
				String resource = "kr/co/opensns/bo/biga/mybatis/Configuration.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
}
