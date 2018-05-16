package org.yi.model;

import java.util.ArrayList;
import java.util.Date;

import org.yi.util.StringUtil;

/**
 * 单一区块
 * 
 * @Author doctorrm
 * @Time 2018-05-13 8:23:44 PM
 */
public class Block {

	public String hash;
	public String previousHash;

	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // our data will be a simple message.

	private long timeStamp; // as number of milliseconds since 1/1/1970.
	private int nonce;

	// Block Constructor.
	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); // Making sure we do this after we set the other values.
	}

	// Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
		return calculatedhash;
	}

	/**
	 * Increases nonce value until hash target is reached.
	 * 
	 * 挖矿：整出（一直尝试计算）头部前difficult（5）位都是0的hash。很大程度依赖于时间戳和nonce
	 * 困惑：为何要挖矿？不就是整出一个新的hash？起规整作用？ 答：1.可产生金币并确保网络算力不会被入侵者占据；2.矿工负责记录事务
	 * 
	 * @param difficulty
	 */
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty *"0"
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	/**
	 * Add transactions to this block 将事务集合（即为保存的数据）添加到区块中
	 * 
	 * @param transaction
	 * @return
	 */
	public boolean addTransaction(Transaction transaction) {
		// process transaction and check if valid, unless block is genesis block then
		// ignore.
		if (transaction == null)
			return false;
		if ((previousHash != "0")) {
			if ((transaction.processTransaction() != true)) {// 非头部事务检查事务合法性
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

}
