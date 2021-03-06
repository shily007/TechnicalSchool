package com.ts.utils.jasypt;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class EncryptConfigUtil {
	public static void main(String[] args) {

		String password = JasyptConstants.JASYPT_ENCRYPTOR_PASSWORD;
//		String data = "http://admin:123456@localhost:7001/eureka/";
		String data = "http://admin:123456@139.155.40.178:7001/eureka/";

		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(password);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName(null);
		config.setProviderClassName(null);
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		String encrypt = encryptor.encrypt(data);
		String decrypt = encryptor.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decrypt);

	}

}
