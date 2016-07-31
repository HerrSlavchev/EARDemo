/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.utils;

import java.util.Base64;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author SRVR1
 */
@Singleton
@Lock(LockType.READ)
public class ByteStringEncoderBase64 implements ByteStringEncoderIF {

    @Override
    public int evalByteCount(int charCount) {
        int len = (int) (3 * (charCount / 4));
        int rem = len % 3;
        if (rem != 0) { //add padding to make it multiple of 4
            len += (3 - rem);
        }

        return len;
    }

    @Override
    public byte[] stringToBytes(String str) {
        return Base64.getDecoder().decode(str);
    }

    @Override
    public String bytesToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
