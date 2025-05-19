package com.cricademy.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for password encryption and decryption using AES-GCM with
 * keys derived from passwords via PBKDF2.
 * 
 * Provides methods to encrypt plaintext passwords with an employee ID (or username)
 * as the password-based key and to decrypt back to plaintext.
 * 
 * Uses AES in GCM mode for authenticated encryption.
 * 
 * Author: Arpan Nepal
 * LMUID: 23048647
 */
public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128; // Authentication tag length for GCM (128-bit)
    private static final int IV_LENGTH_BYTE = 12;  // Recommended IV length for GCM mode
    private static final int SALT_LENGTH_BYTE = 16; // Length of salt used for key derivation
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generates a random nonce (byte array) of the specified length using
     * a secure random number generator.
     * 
     * @param numBytes length of the nonce in bytes
     * @return byte array containing random nonce
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a random AES key of the specified size.
     * 
     * @param keysize key size in bits (e.g., 128, 192, 256)
     * @return SecretKey instance representing the AES key
     * @throws NoSuchAlgorithmException if AES algorithm is unavailable
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives a 256-bit AES key from a password and salt using PBKDF2 with HMAC SHA-256.
     * 
     * @param password character array representing the password
     * @param salt byte array representing the salt
     * @return SecretKey derived from the password and salt
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // Iteration count: 65536, key length: 256 bits
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Encrypts a plaintext password using AES-GCM with a key derived from the
     * provided employee ID (used as password).
     * 
     * The encrypted output is base64-encoded and contains IV and salt prefixed.
     * 
     * @param employee_id the password-based key used for key derivation
     * @param password the plaintext password to encrypt
     * @return base64-encoded encrypted password including IV and salt, or null on failure
     */
    public static String encrypt(String employee_id, String password) {
        try {
            // Generate random salt and IV
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

            // Derive AES key from employee_id and salt
            SecretKey aesKeyFromPassword = getAESKeyFromPassword(employee_id.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

            // Initialize cipher in encrypt mode with key and GCM params
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

            // Prefix IV and salt to ciphertext (IV + salt + ciphertext)
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();

            // Base64 encode the result for storage/transmission
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypts a base64-encoded encrypted password using AES-GCM with a key
     * derived from the provided username.
     * 
     * The input string must contain IV and salt prefixed as generated by
     * the encrypt method.
     * 
     * @param encryptedPassword the base64-encoded encrypted password
     * @param username the password-based key used for key derivation
     * @return the decrypted plaintext password, or null on failure
     */
    public static String decrypt(String encryptedPassword, String username) {
        try {
            byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));

            // Extract IV, salt, and ciphertext from the decoded bytes
            ByteBuffer bb = ByteBuffer.wrap(decode);

            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);

            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);

            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            // Derive AES key from username and salt
            SecretKey aesKeyFromPassword = getAESKeyFromPassword(username.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

            // Initialize cipher in decrypt mode with key and GCM params
            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
