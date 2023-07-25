package org.zly.utils.calculate;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author zhanglianyu
 * @date 2023-07-17 17:18
 */
public class SymmetricEncryptionFace {

    /**
     * 生成密钥
     *
     * @param salt 盐
     * @return 将密钥混合盐在进行base61编码后的字符
     */
    public static String generateSecretKey(String salt) {
        if (StringUtils.isBlank(salt)) throw new IllegalArgumentException("盐不能为空");
        final byte[] secretKeyByte = SymmetricEncryption.generateSecretKey().getEncoded();
        final Encryptor encryptor = new Encryptor(salt.getBytes(StandardCharsets.UTF_8));
        try {
            return Base64.getEncoder().encodeToString(encryptor.encode(secretKeyByte));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密字符串
     *
     * @param salt              原始的盐
     * @param key               进行混合盐在Base64后的钥匙
     * @param encryptionContent 加密的内容
     * @return 进行加密后的字符
     */
    public static String encryption(String salt, String key, String encryptionContent) {
        if (StringUtils.isBlank(salt)) throw new IllegalArgumentException("盐不能为空");
        if (StringUtils.isBlank(key)) throw new IllegalArgumentException("key不能为空");
        if (StringUtils.isBlank(encryptionContent)) throw new IllegalArgumentException("加密内容不能为空");
        try {
            Encryptor encryptor = new Encryptor(salt.getBytes(StandardCharsets.UTF_8));
            final byte[] decode = encryptor.decode(Base64.getDecoder().decode(key));
            final SymmetricEncryption symmetricEncryption = new SymmetricEncryption(decode);
            return symmetricEncryption.encryptionToBase64(encryptionContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密字符串
     *
     * @param salt          原始的盐
     * @param key           进行混合盐在Base64后的钥匙
     * @param decodeContent 解密的内容
     * @return 进行解密后的字符
     */
    public static String decode(String salt, String key, String decodeContent) {
        if (StringUtils.isBlank(salt)) throw new IllegalArgumentException("盐不能为空");
        if (StringUtils.isBlank(key)) throw new IllegalArgumentException("key不能为空");
        if (StringUtils.isBlank(decodeContent)) throw new IllegalArgumentException("解密内容不能为空");
        try {
            Encryptor encryptor = new Encryptor(salt.getBytes(StandardCharsets.UTF_8));
            final byte[] decode = encryptor.decode(Base64.getDecoder().decode(key));
            SymmetricEncryption symmetricEncryption = new SymmetricEncryption(decode);
            return new String(symmetricEncryption.decode(Base64.getDecoder().decode(decodeContent.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
