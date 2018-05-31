package org.yi;

import org.yi.dao.AgentDao;
import org.yi.model.Agent;


public class Apple {

	/*
	 * @Autowired // 无法自动注入？？？ public static AgentDao agentDao;
	 */

	/*
	 * @Autowired public static Pear pear;
	 */
	public static void main(String[] args) {
		AgentDao agentDao = new AgentDao();
		Agent agent = new Agent("张三", 3003);
		int res = agentDao.addNewAgent(agent);
		System.out.println(res);

	}
}
