/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.utils;


/**
 *
 * @author SRVR1
 */
public interface ByteStringEncoderIF {
    
    /**
     * Calculates how many bytes are required to encode a String in Base64
     *
     * @param charCount The number of symbols to be encoded
     * @return Number of bytes needed to store the symbols
     */
    public int evalByteCount(int charCount);
    
    public byte[] stringToBytes(String str);
    
    public String bytesToString(byte[] bytes);
    
}
