package org.zly.utils.calculate;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 对称加解密
 *
 * @author zhanglianyu
 * @date 2023-07-17 9:43
 */
public class SymmetricEncryption {
    private final String algorithm;
    private final Cipher cipher;
    private final Key key;
    public static final String ALGORITHM="AES";
    public SymmetricEncryption(String algorithm, Key key) {
        this.algorithm = algorithm;
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.key = key;
    }

    public SymmetricEncryption(String algorithm, byte[] key) {
        this(algorithm, new SecretKeySpec(key, algorithm));
    }

    //    public static void main(String[] args) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(256);
//        final String algorithm = keyGenerator.getAlgorithm();
//        final SecretKey secretKey = keyGenerator.generateKey();
//        KeyStore keyStore = KeyStore.getInstance("")
//    }
    public SecretKey generateSecretKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(this.algorithm);
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String encryptionToBase64(byte[] encryptedContent) {
        return Base64.getEncoder().encodeToString(encryption(encryptedContent));
    }

    /**
     * 使用对称算法加密
     *
     * @param encryptedContent 解密内容
     * @return 加密后的字节数组
     */
    public byte[] encryption(byte[] encryptedContent) {

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(encryptedContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(byte[] encryptedContent) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encryptedContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public Key getKey() {
        return key;
    }
}
