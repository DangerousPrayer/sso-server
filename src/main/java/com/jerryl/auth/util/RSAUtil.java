package com.jerryl.auth.util;

import com.alibaba.fastjson.JSON;
import com.jerryl.auth.service.dto.SessionUser;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuruijie on 2017/4/8.
 */
public class RSAUtil {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    public static final int KEY_SIZE = 1024;

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 生成密钥对(公钥和私钥)
     */
    public static Map<String, Object> genKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(data);
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] data, PrivateKey privateKey){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(data);
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> keyMap = new ConcurrentHashMap<>();
    public static Map<String, Object> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        keyMap.putAll(RSAUtil.genKeyPair());

        SessionUser user = new SessionUser();
        user.setPassportId("admin");
        user.setNickName("JerryL");
        user.setRoleIds(Arrays.asList(1,2));

        String json = JSON.toJSONString(user);
        String token = createToken(json);
        System.out.println(token);

        String sessionId = "123";

        sessions.get(token);
//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] data = decoder.decodeBuffer(name);
//
//        long currentTimeMillis = System.currentTimeMillis();
//        String source = name+currentTimeMillis;
//
//        Map<String, Object> keyMap = RSAUtil.genKeyPair();
//        byte[] encrypt = RSAUtil.encrypt(source.getBytes(), (PublicKey) keyMap.get(PUBLIC_KEY));
//        BASE64Encoder encoder = new BASE64Encoder();
//        String s = encoder.encode(encrypt);
//
//        BASE64Decoder decoder = new BASE64Decoder();
//        decoder.decodeBuffer(s);
//
//        System.out.println("RSA encrypt:");
//        System.out.println(s);
//        System.out.println();
//        byte[] decrypt = RSAUtil.decrypt(encrypt, (PrivateKey) keyMap.get(PRIVATE_KEY));
//
//        System.out.println("RSA decrypt:");
//        System.out.println(new String(decrypt));


    }

    public static String createToken(String info){
        PublicKey publicKey = (PublicKey) keyMap.get(RSAUtil.PUBLIC_KEY);

        byte[] token = RSAUtil.encrypt(info.getBytes(), publicKey);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(token);
    }

    public static boolean checkToken(String token){
        Object o = sessions.get("123");
        return token.equals(o);
    }
}
