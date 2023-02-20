package org.roomie.library.data.services;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;


@Service
public class SecureKeysService {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "1234567891234567".getBytes();
 
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public static String encrypt(String valueToEnc, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
  
        byte[] encValue = cipher.doFinal(valueToEnc.getBytes());
        byte[] encryptedByteValue = new Base64().encode(encValue);
        System.out.println("Encrypted Value :: " + new String(encryptedByteValue));
  
        return new String(encryptedByteValue);
    }

    public static String decrypt(String encryptedValue, Key key) throws Exception {
        // Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
          
        byte[] decodedBytes = new Base64().decode(encryptedValue.getBytes());
  
        byte[] enctVal = cipher.doFinal(decodedBytes);
         
        System.out.println("Decrypted Value :: " + new String(enctVal));
        return new String(enctVal);
    }

    public String EncryptString(String inputString) throws Exception {
        Key key = generateKey();
        return encrypt(inputString, key);
    }

    public String DecryptString(String encriptedValue) throws Exception {
        Key key = generateKey();
        return decrypt(encriptedValue,key);
    }
}
