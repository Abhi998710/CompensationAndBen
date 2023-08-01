package com.compAndBen.helper;

public class EncryptDecrypt {
	 byte[] iv = new byte[16];
	    @Autowired
	    private Environment env;
	    private SecretKeySpec secretKey;

	    public void setKey(String myKey) throws NoSuchAlgorithmException {
	        byte[] key;
	        MessageDigest sha = null;
	        key = myKey.getBytes(StandardCharsets.UTF_8);
	        sha = MessageDigest.getInstance("SHA-512");
	        key = sha.digest(key);
	        key = Arrays.copyOf(key, 16);
	        secretKey = new SecretKeySpec(key, "AES");
	    }

	    public String encryption(String e) throws NoSuchPaddingException, NoSuchAlgorithmException,
	            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
	            IllegalBlockSizeException {
	        byte[] dataInBytes = e.getBytes();
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
	        setKey(env.getProperty("encryptDecrypt.setkey.value"));
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
	        byte[] encryptedBytes = cipher.doFinal(dataInBytes);
	        return encode(encryptedBytes);

	    }

	    private String encode(byte[] data) {
	        return Base64.getEncoder().encodeToString(data);
	    }

	    private byte[] decode(String data) {
	        return Base64.getDecoder().decode(data);
	    }

	    public String decryption(String a) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	            setKey(env.getProperty("encryptDecrypt.setkey.value"));
	            byte[] dataInBytes = decode(a);
	            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
	            byte[] decryptedBytes = cipher.doFinal(dataInBytes);
	            return new String(decryptedBytes);
	        } catch (Exception e) {
	            throw new InvalidInputException("Invalid access code");
	        }
	    }


	}