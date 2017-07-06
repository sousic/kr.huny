package kr.huny.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by sousic on 2017-05-24.
 */
public class AESImpl implements ICrypto {
    public static final String ALGORITHM = "AES";

    private static final int KEY_SIZE_IN_BITS = 128;
    private final String key;
    private final String initVector;

    public AESImpl(String key, String initVector) {
        this.key = key;
        if (key.length() != 256 / 8) {
            throw new IllegalArgumentException("'secretKey' must be 256 bit");
        }
        this.initVector = initVector;
    }

    public AESImpl(String key) {
        if (key.length() != 256 / 8) {
            throw new IllegalArgumentException("'secretKey' must be 256 bit");
        }
        this.key = key;
        this.initVector = "qwerasdfzxcv1234";
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return crypt(data, Cipher.ENCRYPT_MODE);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return crypt(data, Cipher.DECRYPT_MODE);
    }

    private byte[] crypt(byte[] data, int encryptMode)
    {
        try {
            SecretKey secretKey = generateSecretKey();
            byte[] secretKeyByteRepresentation = secretKey.getEncoded();

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyByteRepresentation, ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encryptMode, secretKeySpec, iv);

            return cipher.doFinal(data);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); //"AES/CBC/PKCS5Padding"
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(KEY_SIZE_IN_BITS, secureRandom);

            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
