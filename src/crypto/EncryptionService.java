package crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {
    private static final int ITERATIONS = 210_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    public static String encrypt(String plainText, char[] masterPassword)
            throws GeneralSecurityException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(salt);
        random.nextBytes(iv);

        SecretKey key = deriveKey(masterPassword, salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(iv) + ":"
                + Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String stored, char[] masterPassword)
            throws GeneralSecurityException {
        String[] parts = stored.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] iv = Base64.getDecoder().decode(parts[1]);
        byte[] encrypted = Base64.getDecoder().decode(parts[2]);

        SecretKey key = deriveKey(masterPassword, salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static SecretKey deriveKey(char[] password, byte[] salt)
            throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }
}