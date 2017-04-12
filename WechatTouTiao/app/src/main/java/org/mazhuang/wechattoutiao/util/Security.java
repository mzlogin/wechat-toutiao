package org.mazhuang.wechattoutiao.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mazhuang on 2017/1/22.
 */

public class Security {

    static {
        System.loadLibrary("jni-encrypt");
    }

    public static native String getAlgorithm();
    public static native String getKey();
    public static native String getTransformation();
    public static native String getEncoding();

    private static String algorithm = getAlgorithm();
    private static String transformation = getTransformation();
    private static String key = getKey();
    private static String encoding = getEncoding();

    public static native String encMethod11(String input);
    public static native String encMethod12(String input);
    public static native String encMethod13(String input);

    public static String aesEnc(String str) throws Exception {
        return aesEnc(str, key);
    }

    public static String aesEnc(String str, String key) throws Exception {
        Cipher localCipher = Cipher.getInstance(transformation);
        localCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm), getIv());
        return new String(Base64.encode(localCipher.doFinal(str.getBytes(encoding)), Base64.NO_WRAP), encoding);
    }

    public static String aesEnc(byte[] content) throws Exception {
        Cipher localCipher = Cipher.getInstance(transformation);
        localCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm), getIv());
        return new String(Base64.encode(localCipher.doFinal(content), Base64.NO_WRAP), encoding);
    }

    public static String aesDec(String str) throws Exception {
        return aesDec(str, key);
    }

    public static String aesDec(String str, String key) throws Exception {
        Cipher localCipher = Cipher.getInstance(transformation);
        localCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm), getIv());
        return new String(localCipher.doFinal(Base64.decode(str, Base64.NO_WRAP)), encoding);
    }

    public static IvParameterSpec getIv() {
        return new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }
}
