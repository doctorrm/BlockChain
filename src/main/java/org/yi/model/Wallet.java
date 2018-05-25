package org.yi.model;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 钱包
 * 
 * @Author doctorrm
 * @Time 2018-05-13 8:25:45 PM
 */
public class Wallet {

	public PrivateKey privateKey;
	public PublicKey publicKey;

	// only UTXOs owned by this wallet.可以看作是钱包中钱币的集合
	public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); 

	public Wallet() {//钱包初始化时即生成钥匙
		generateKeyPair();
	}

	/**
	 * 生存公钥（地址）和私钥（唯一标识）
	 */
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); // 256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * returns balance and stores the UTXO's owned by this wallet in this.UTXOs
	 * 计算钱包里还剩多少钱
	 * 
	 * @return
	 */
	public float getBalance() {
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : BlockChain.UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			if (UTXO.isMine(publicKey)) { // if output belongs to me ( if coins belong to me )
				UTXOs.put(UTXO.id, UTXO); // add it to our list of unspent transactions.
				total += UTXO.value;
			}
		}
		return total;
	}

	/**
	 * Generates and returns a new transaction from this wallet.
	 * 
	 * 根据目标支出，计算一次支出，返回该支出对应的新事务
	 * 
	 * @param _recipient
	 * @param value
	 * @return
	 */
	public Transaction sendFunds(PublicKey _recipient, float value) {
		if (getBalance() < value) { // gather balance and check funds.不幸透支，出错
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}

		// create array list of inputs（inputs，变量，里面所有的值的和就是要消耗的钱币）
		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if (total > value)// value为要消耗的钱币，inputs的和达到之后就不用再往inputs里面加钱币了，避免浪费
				break;
		}

		Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
		newTransaction.generateSignature(privateKey);

		for (TransactionInput input : inputs) {// inputs都是被花的了
			UTXOs.remove(input.transactionOutputId);
		}
		return newTransaction;
	}

}