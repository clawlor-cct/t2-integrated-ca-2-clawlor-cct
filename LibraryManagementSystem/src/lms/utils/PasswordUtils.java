package lms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

// This gives good examples on the hashing process using java.security (https://www.javaguides.net/2020/02/java-sha-256-hash-with-salt-example.html)
public class PasswordUtils {
    // Used for hashing passwords so no raw passwords are stored in the database
    public static String hashPassword(String password) {
        try {
            // Generate random salt securely
            byte[] salt = generateSalt();

            // Create SHA-256 digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);  // Update digest with salt

            // Hash password and store in byte form
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Combine the salt and hashed password (so that we can store it in one field)
            byte[] saltAndHash = new byte[salt.length + hashedBytes.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedBytes, 0, saltAndHash, salt.length, hashedBytes.length);

            // Convert saltAndHash array into a base64 encoded string for string compatible storage
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("PasswordUtils: NoSuchAlorithmException."); // An exception should never be thrown since "SHA-256" is hardcoded, but .getInstance demands a catch...
            return null;
        }
    }
    
    // Verify password against hashed password
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Decode from base64
            byte[] decodedHash = Base64.getDecoder().decode(storedHash);

            // Extract the salt (First 16 bytes of the decoded hash)
            byte[] salt = new byte[16];
            System.arraycopy(decodedHash, 0, salt, 0, salt.length);

            // Hash the entered password with the same salt
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            
            // Hash the password so we can compare their hashed versions against eachother
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Compare the newly hashed password with the stored hash
            for (int i = 0; i < hashedBytes.length; i++) {
                if (decodedHash[i + salt.length] != hashedBytes[i]) {
                    return false;  // Password does not match
                }
            }

            return true;  // Password matches

        } catch (NoSuchAlgorithmException e) {
            System.err.println("PasswordUtils: NoSuchAlorithmException."); // An exception should never be thrown since "SHA-256" is hardcoded, but .getInstance demands a catch...
            return false;
        }
    }
    private static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom(); // Create instance of SecureRandom
        byte[] salt = new byte[16]; // Allocate a 16 byte wide array for salt
        sr.nextBytes(salt); // Place bytes into the array and return after
        return salt;
    }
}
