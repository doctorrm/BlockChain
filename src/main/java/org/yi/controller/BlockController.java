package org.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yi.dao.AgentDao;
import org.yi.model.Agent;
import org.yi.model.Block;

import com.google.gson.GsonBuilder;

@Controller
public class BlockController {
	/*@Autowired 
	public static AgentDao agentDao;*/

	@GetMapping(value = "/read", produces = "application/json")
	@ResponseBody // 不加的话无法找到这里，404
	public String test() {
		Block block = new Block(null);
		String blockJson = new GsonBuilder().setPrettyPrinting().create().toJson(block);
		return blockJson;
	}

	@GetMapping(value = "/get", produces = "application/json")
	@ResponseBody
	public String queryAgent() {
		int agentId = 1;
		AgentDao agentDao=new AgentDao();
		Agent agent = agentDao.queryAgentWithId(agentId);
		System.out.println(agent);
		String res = new GsonBuilder().setPrettyPrinting().create().toJson(agent);
		return res;
	}
}
