/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.utils;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author SRVR1
 */
@Stateless
@Alternative
public class ByteStringEncoderUTF32 implements ByteStringEncoderIF{

    @Override
    public int evalByteCount(int charCount) {
        return charCount*4;
    }

    @Override
    public byte[] stringToBytes(String str) {
        byte[] bytes = null;
        try {
            bytes = str.getBytes("UTF-32BE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    @Override
    public String bytesToString(byte[] bytes) {
        String res = null;
        try {
            res = new String(bytes, "UTF-32BE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    
}
