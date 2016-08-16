/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myoldbooks.service.security;

import com.myoldbooks.model.Cred;
import com.myoldbooks.utils.ByteStringEncoderIF;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author SRVR1
 */
@Singleton
@Lock(LockType.READ)
public class Hasher {

    private final Random RANDOM = new SecureRandom();
    
    @Inject
    private ByteStringEncoderIF encoder;
    
    private static final int CMP_STEPS = 512; //how many steps to make when comparing two passwords

    private static final int MIN_SALT_LEN = 32;
    private static final int OPT_SALT_LEN = 32;

    private static final int MIN_ITERATIONS = 256;
    private static final int OPT_ITERATIONS = 256;

    private static final int MIN_PASS_LEN = 192;
    private static final int OPT_PASS_LEN = 64;

    /**
     * Generates a new Cred object containing the hashed value of a password
     *
     * @param pass the password to be hashed
     * @return randomly generated credentials
     */
    public Cred generateCred(String pass) {

        Cred res;

        //salt has random length and value
        int saltLen = MIN_SALT_LEN + RANDOM.nextInt(OPT_SALT_LEN);
        String salt = generateRandomString(saltLen);
        //random number of iterations
        int iter = MIN_ITERATIONS + RANDOM.nextInt(OPT_ITERATIONS);
        //random keyLength
        int passLen = MIN_PASS_LEN + RANDOM.nextInt(OPT_PASS_LEN);
        
        String hashedPass = hashPassword(pass, salt, iter, passLen);

        res = new Cred();
        res.setSalt(salt);
        res.setIter(iter);
        res.setLen(passLen);
        res.setPass(hashedPass);

        return res;
    }

    /**
     * Compares a password with the real credentials from DB. Performs the same
     * number of steps in order to prevent duration-based attacks.
     *
     * @param cred The real credentials stored in DB
     * @param pass The password a user is trying to use for login
     * @return true if the password is correct
     */
    public boolean verifyPass(Cred cred, String pass) {

        if (pass == null || pass.isEmpty()) {
            return false;
        }

        char[] realPassChars = cred.getPass().toCharArray();
        char[] passChars = hashPassword(pass, cred.getSalt(), cred.getIter(), cred.getLen()).toCharArray();

        int realSteps = realPassChars.length < passChars.length ? realPassChars.length : passChars.length;
        boolean match = true;
        for (int i = 0; i < realSteps; i++) {
            if (realPassChars[i] != passChars[i]) {
                match = false;
            }
        }
        for (int i = realSteps; i < CMP_STEPS; i++) {
            if ('a' == 'a') {
                //do nothing, just add computation cycles to fool duration-based attacks
            }
        }

        return match;
    }

    private String hashPassword(String pass, String salt, int iterations, int keyLength) {
        String res = "";
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), encoder.stringToBytes(salt), iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            res = encoder.bytesToString(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public byte[] generateRandomBytes(int len) {
        byte[] bytes = new byte[len];
        RANDOM.nextBytes(bytes);
        return bytes;
    }
    
    public String generateRandomString(int len) {
        byte[] bytes = generateRandomBytes(encoder.evalByteCount(len));
        return encoder.bytesToString(bytes);
    }

}
