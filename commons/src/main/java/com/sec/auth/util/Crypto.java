package com.sec.auth.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Crypto {
    private Crypto(){
    }

    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final String PASSWORD_BASED_KEY_DERIVATION_ALG= "PBKDF2WithHmacSHA256";

    private static final byte[] AES_IV = intIv();

    private static final String SALT = "@pQc+qa#$_ -M|;8Z!|2wsxe dcrftg[yhuji}o346m.;:'*7$rfj@kjhk";


    public static String decrypt(final String strToDecrypt, final String password) {
        try{
            SecretKey secretKey = getSecretKey(password);
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(AES_IV));
            byte[] cleanBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.getBytes()));
            return new String(cleanBytes, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException
        | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String encrypt(String strToEncrypt, String password) {
        try{
            SecretKey secretKey = getSecretKey(password);

            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(AES_IV));
            byte[] encryptBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encryptBytes));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException
                | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static SecretKey getSecretKey(String password) {
        try{
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PASSWORD_BASED_KEY_DERIVATION_ALG);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey key = keyFactory.generateSecret(spec);
            return new SecretKeySpec(key.getEncoded(), AES_ALG);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static byte[] intIv() {
        int blockSize = 16;
        try {
            blockSize = Cipher.getInstance(Crypto.AES_CBC_PCK_ALG).getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i=0; i < blockSize; ++i) {
                iv[i]=0;
            }
            return iv;
        } catch (Exception e) {
            byte[] iv = new byte[blockSize];
            for (int i=0; i < blockSize; ++i) {
                iv[i]=0;
            }
            return iv;
        }

    }
}
