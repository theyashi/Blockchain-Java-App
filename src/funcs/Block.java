package funcs;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//import java.util.Date;

public class Block {
	private String seller;
	private String buyer;
	private String timestamp;
	private String hash;
	private String previousHash;
	private Integer nonce;
	
	public Block(String seller, String buyer,String timestamp){
	    this.seller=seller;
	    this.buyer=buyer;
	    this.timestamp=timestamp;
		this.nonce=0;
		this.hash=calculateHash();
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String mineblock(int difficulty) {
		nonce = 0;
		
		 while(!getHash().substring(0, difficulty).equals(Utlis.zeros(difficulty))) 
		 {
			 nonce++;
			 hash =calculateHash();
		 }
		 return hash;
	}

	public String calculateHash(){
			String dataToHash = this.timestamp +this.seller+this.buyer+this.previousHash+this.nonce;
			
			MessageDigest digest;
			String encoded_hash=null;
			
			try{
				digest=MessageDigest.getInstance("SHA-256");
				byte[] hash =digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
				encoded_hash=Base64.getEncoder().encodeToString(hash);
				}
			catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
			this.hash=encoded_hash;
			return encoded_hash;
	}
	
}
