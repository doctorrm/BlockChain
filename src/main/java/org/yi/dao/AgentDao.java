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

import sun.management.resources.agent_es;

@Component
public class AgentDao {

	private String getStatement = "org.yi.dao.agentMapper.queryAgentWithId";
	public String getStatement1 = "org.yi.dao.agentMapper.queryAll";
	private String addStatement = "org.yi.dao.agentMapper.addAgent";


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
	 * 
	 * @param agent
	 * @return 1表示添加成功，-1表示添加失败
	 */
	public int addNewAgent(Agent agent) {
		AgentDao agentDao = new AgentDao();
		String addStatement = agentDao.addStatement;
		SqlSession session = agentDao.getSession();				
		int res = session.insert(addStatement, agent);
		session.commit();
		if (res > 0) {
			return 1;
		}
		return -1;
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
