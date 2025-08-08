package com.proyecto.demo.common.encryption.impl;

import com.proyecto.demo.common.encryption.Encryption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service("EncryptionAES")
@Slf4j
public class EncryptionAES implements Encryption {


    @Value("${settings.application.name}")
    private String applicationName;

    @Value("${settings.encryption.aes.key}")
    private String keyAes;

    @Value("${settings.encryption.aes.iv}")
    private String iv;

    @Override
    public String encrypt(String stringToEncrypt, String key) {
        try {
            Key secretKey = new SecretKeySpec(keyAes.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] cipherText = cipher.doFinal(stringToEncrypt.getBytes());
            return base64UrlEncode(Base64.getEncoder().encodeToString(cipherText));
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException exception) {
            log.error("Error encrypting with AES the new string ({}); Exception {} ({})", stringToEncrypt, applicationName, exception.getMessage());
        }
        return null;
    }

    @Override
    public String decrypt(String stringToDecrypt, String key) {
        try {
            if(stringToDecrypt==null){
                return null;
            }
            Key secretKey = new SecretKeySpec(keyAes.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(base64UrlDecode(stringToDecrypt)));
            return new String(plainText);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException exception) {
            log.error("Error decrypting with AES the new string ({}); Exception {} ({})", stringToDecrypt, applicationName, exception.getMessage());
        }
        return null;
    }

    private String base64UrlEncode(String stringToEncode) {
        return stringToEncode.replace('+', '-').replace('/', '_');
    }

    private String base64UrlDecode(String stringToDecode) {
        return stringToDecode.replace('-', '+').replace('_', '/');
    }
}
