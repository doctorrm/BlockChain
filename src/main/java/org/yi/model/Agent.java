package org.yi.model;

/**
 * 模拟不同节点的用户（通过不同端口），负责创建区块
 * 
 * @Author doctorrm
 * @Time 2018-05-21 2:01:23 PM
 */

public class Agent {
	int agentId;
	String agentName;
	int agentAddrs;
	int agentBalance;

	public Agent() {

	}

	public Agent(String agentName, int agentAddrs) {
		this.agentName = agentName;
		this.agentAddrs = agentAddrs;
		this.agentBalance = 0;
	}

	public Block createBlock(String previousHash) {
		Block block = new Block(previousHash);
		return block;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int getAgentAddrs() {
		return agentAddrs;
	}

	public void setAgentAddrs(int agentAddrs) {
		this.agentAddrs = agentAddrs;
	}

	public int getAgentBalance() {
		return agentBalance;
	}

	public void setAgentBalance(int agentBalance) {
		this.agentBalance = agentBalance;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	@Override
	public String toString() {
		return "Agent [agentName=" + agentName + ", agentAddrs=" + agentAddrs + ", agentBalance=" + agentBalance + "]";
	}

}
