import java.security.*;
import java.util.ArrayList;

public class Transaction {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public PublicKey reciepient; // Recipients address/public key.
	public float value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private static int sequence = 0; // a rough count of how many transactions have been generated.

	/**
	 * Constructor
	 * 
	 * @param from
	 * @param to
	 * @param value
	 * @param inputs
	 */
	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence);
	}

	/**
	 * Signs all the data we dont wish to be tampered with.
	 * 
	 * 生成签名
	 * 
	 * @param privateKey
	 */
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}

	/**
	 * Verifies the data we signed hasnt been tampered with
	 * 
	 * 验证签名
	 * 
	 * @return
	 */
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	/**
	 * Returns true if new transaction could be created.
	 * 
	 * @return
	 */
	public boolean processTransaction() {

		if (verifiySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}

		// gather transaction inputs (Make sure they are unspent):
		for (TransactionInput i : inputs) {
			i.UTXO = BlockChain.UTXOs.get(i.transactionOutputId);
		}

		// check if transaction is valid:
		if (getInputsValue() < BlockChain.minimumTransaction) {
			System.out.println("#Transaction Inputs too small: " + getInputsValue());
			return false;
		}

		// generate transaction outputs:
		float leftOver = getInputsValue() - value; // get value of inputs then the left over change:
		transactionId = calulateHash();
		outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); // send value to recipient
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); // send the left over 'change' back to
																					// sender

		// add outputs to Unspent list
		for (TransactionOutput o : outputs) {
			BlockChain.UTXOs.put(o.id, o);
		}

		// remove transaction inputs from UTXO lists as spent:
		for (TransactionInput i : inputs) {
			if (i.UTXO == null)
				continue; // if Transaction can't be found, skip it
			BlockChain.UTXOs.remove(i.UTXO.id);
		}

		return true;
	}

	/**
	 * returns sum of inputs(UTXOs) values
	 * 
	 * 根据inputs计算一次事务中支出的金币集合
	 */
	public float getInputsValue() {
		float total = 0;
		for (TransactionInput i : inputs) {
			if (i.UTXO == null)
				continue; // if Transaction can't be found skip it
			total += i.UTXO.value;
		}
		return total;
	}

	/**
	 * returns sum of outputs:
	 * 
	 * 拥有的金币集合（在本类暂时没用到）
	 * 
	 * @return
	 */
	public float getOutputsValue() {
		float total = 0;
		for (TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}

}