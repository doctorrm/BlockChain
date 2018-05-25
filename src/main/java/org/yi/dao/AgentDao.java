package org.yi.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yi.model.Agent;

@Component
public class AgentDao {

	private String getStatement = "org.yi.dao.agentMapper.queryAgentWithId";
	public  String getStatement1 = "org.yi.dao.agentMapper.queryAll";

	public Agent queryAgentWithId(int agentId) {
		AgentDao agentDao = new AgentDao();
		String statement = agentDao.getStatement;
		SqlSession session = agentDao.getSession();
		Object agent = session.selectOne(statement, agentId);
		return (Agent) agent;
	}

	public List<Agent> queryAll() {
		AgentDao agentDao = new AgentDao();
		String statement1 = agentDao.getStatement1;
		SqlSession session = agentDao.getSession();
		List<Agent> agents = session.selectList(statement1);
		return agents;

	}

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public SqlSession getSession() {
		String resource = "mybatis-config.xml";
		InputStream is = AgentDao.class.getClassLoader().getResourceAsStream(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		return session;
	}
}
