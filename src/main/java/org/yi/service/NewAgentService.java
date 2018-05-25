package org.yi.service;

import java.util.HashMap;
import java.util.Random;

import org.yi.model.Agent;
import org.yi.model.Block;
import org.yi.model.Transaction;
import org.yi.model.TransactionOutput;
import org.yi.model.Wallet;

public class NewAgentService {

	public static Transaction genesisTransaction;// 第一个区块中的事务
	// list of all unspent transactions.
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

	public void addNewAgent() {
		Agent agent = new Agent();

		Wallet wallet = new Wallet();
		Wallet coinbase = new Wallet();

		// create genesis transaction, which sends 100 Coins to walletA:
		// 给加入的新用户节点随机分配一定数目的金币金额
		genesisTransaction = new Transaction(coinbase.publicKey, wallet.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinbase.privateKey); // manually sign the genesis transaction
		genesisTransaction.transactionId = "0"; // manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value,
				genesisTransaction.transactionId)); // manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); // its important to store

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		//agent.setMoney(100f);
		
		//addNewAgent(agent);插入数据库
	}
}
