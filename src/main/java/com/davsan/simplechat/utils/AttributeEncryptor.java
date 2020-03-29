package com.davsan.simplechat.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputFilter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;
import java.util.Scanner;

// TODO: Very basic encryption, should revisit and implement a better encryption strategy when necessary.
@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String FILE_PATH = "src/main/java/com/davsan/simplechat/encryption-key.txt";
    private static final String AES = "AES";
    private static String SECRET;

    private final Key key;
    private final Cipher cipher;

    public AttributeEncryptor() throws Exception {
        SECRET = getSecret();
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    // TODO: This is temporary so the secret isn't exposed in the code
    private String getSecret() {
        try {
            File file = new File(FILE_PATH);
            Scanner reader = new Scanner(file);
            if (reader.hasNextLine()) {
                return reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }
}
