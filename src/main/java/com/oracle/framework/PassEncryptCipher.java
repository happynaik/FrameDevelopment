package com.oracle.framework;

import java.lang.invoke.MethodHandles;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
//Code For PassWord Encryption & Decryption
public class PassEncryptCipher {
	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public PassEncryptCipher() throws Exception {
		myEncryptionKey = "ThisIsSpartaThisIsSparta";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public String encrypt(String unencryptedString) throws Exception {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Throwable e) {
			log.info(e);
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Throwable e) {
			log.info(e);
		}
		return decryptedText;
	}

	public static void main(String args []) throws Exception 
	{ 
		PassEncryptCipher td= new PassEncryptCipher();
		String target="";
		String encrypted=td.encrypt(target);
		String decrypted=td.decrypt("CNqlwdhMAneFdl9uEyugQw==");
		log.info("String To Encrypt: "+ target); 
		log.info("Encrypted String:" + encrypted); 
		log.info("Decrypted String:" + decrypted); 
	}
	

}