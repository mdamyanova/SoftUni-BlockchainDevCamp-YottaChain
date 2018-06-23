import java.util.Date; 
import java.security.MessageDigest; //to access SHA256 algorithm

public class Block {
	public String previousHash;
	public String currentHash; 
	private String transaction; //the transaction we made (a class defined somewhere)
	private long timeStamp; //as number of milliseconds 
	private int nonce; //random 
	
	//Block Constructor  
	public Block(String transaction,String previousHash ) {
		this.transaction = transaction;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.currentHash = calculateHash(); 
		
		
	}

	//A method to calculate currentHash 
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		return calculatedhash;
		
	//A simple method for mining 
	public void miner(int difficulty) {
		String desired = new String(new char[difficulty]).replace('\0', '0'); 
		while(!currentHash.substring( 0, difficulty).equals(desired)) {
			nonce ++;
			currentHash = calculateHash();
		}
		System.out.println("New Block mined with hash : " + hash);
	}
}

