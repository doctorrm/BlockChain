package org.yi.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yi.dao.AgentDao;
import org.yi.model.Agent;

import sun.management.resources.agent;

public class testAgent {
	@Autowired
	public static AgentDao agentDao;

	public static void main(String[] args ) {
		int agentId = 1;		
		Agent agent = agentDao.queryAgentWithId(agentId);
		System.out.println(agent);
		System.out.println("father");
	}
}
