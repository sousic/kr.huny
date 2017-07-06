package kr.huny.common.crypto;

/**
 * Created by sousic on 2017-05-24.
 */
public interface ICrypto {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
