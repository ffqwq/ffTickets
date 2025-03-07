package com.yrh.ff_ticket.util;

import cn.hutool.crypto.digest.BCrypt;

//BCrypt加密
public class BCryptUtil {
    //加密
    public static String encrypt(String s){
        return BCrypt.hashpw(s);
    }
    //解密
    public static boolean decrypt(String plaintext ,String ciphertext){
        return BCrypt.checkpw(plaintext,ciphertext);
    }
}
