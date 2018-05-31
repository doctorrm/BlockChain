package org.yi.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yi.dao.AgentDao;
import org.yi.model.Agent;
import org.yi.model.Block;
import org.yi.service.AgentService;

import com.google.gson.GsonBuilder;
import com.sun.crypto.provider.AESKeyGenerator;

@Controller
public class BlockController {
	/*
	 * @Autowired public static AgentDao agentDao;
	 */
	@PutMapping(value = "/get", produces = "application/json")
	@ResponseBody
	public void newAgent(@RequestParam String agentName,@RequestParam int agentAddrs) {
		Agent agent=new Agent(agentName,agentAddrs);
		AgentService agentService=new AgentService();
		int res=agentService.addAgent(agent);			
	}

	
	
	
	
	
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
		AgentDao agentDao = new AgentDao();
		Agent agent = agentDao.queryAgentWithId(agentId);
		System.out.println(agent);
		String res = new GsonBuilder().setPrettyPrinting().create().toJson(agent);
		return res;
	}
}
