/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.utils;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author SRVR1
 */
@Stateless
@Default
public class ByteStringEncoderHex implements ByteStringEncoderIF{

    private static final char LEAD_TOKEN = '0';
    
    @Override
    public int evalByteCount(int charCount) {
        int res = charCount / 2;
        return charCount % 2 == 0 ? res : (res+1);
    }

    @Override
    public byte[] stringToBytes(String str) {
        if(str.length() % 2 != 0) {
            str = LEAD_TOKEN + str;
        }
        return DatatypeConverter.parseHexBinary(str);
    }

    @Override
    public String bytesToString(byte[] bytes) {
        String res = DatatypeConverter.printHexBinary(bytes);
        return res;
    }
    
}
