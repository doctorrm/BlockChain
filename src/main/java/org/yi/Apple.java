package org.yi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yi.dao.AgentDao;
import org.yi.model.Agent;

public class Apple {

	@Autowired // 无法自动注入？？？
	public static AgentDao agentDao;

	/*
	 * @Autowired public static Pear pear;
	 */
	public static void main(String[] args) {
		// System.out.println(pear.name);
		// AgentDao agentDao = new AgentDao();
		// System.out.println(agentDao.getStatement1);
		System.out.println(agentDao);
		Agent agent = agentDao.queryAgentWithId(2);
		System.out.println(agent);

	}
}
