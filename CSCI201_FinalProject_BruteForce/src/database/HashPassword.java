/**
 * Group 14
 * 
 * CP: Aya Shimizu (ashimizu@usc.edu)
 * Yiyang Hou (yiyangh@usc.edu)
 * Sean Syed (seansyed@usc.edu)
 * Eric Duguay (eduguay@usc.edu)
 * Xing Gao (gaoxing@usc.edu)
 * Sangjun Lee (sangjun@usc.edu)
 * 
 */

package database;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

//utility class to hash passwords, use Base64 to hash
public class HashPassword {
	
    private static final int iterations = 30000;
    private static final int saltLength = 32;
    private static final int keyLength = 256;

    //a function that returns a salted hash given a password (string)
    public static String getSaltedHash(String password) throws Exception {
    	//get salt in form of byte 
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength);
        // return a salt+$+hashed form of hashed password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    //function to check if the passed in password and hashed password are the same
    public static boolean check(String password, String stored) throws Exception{
        String[] saltHash = stored.split("\\$");
        if (saltHash.length != 2) {
            throw new IllegalStateException("Format invalid. ");
        }
        String hashed = hash(password, Base64.decodeBase64(saltHash[0]));
        return hashed.equals(saltHash[1]);
    }

    //hash function to hash the given password with a byte salt
    private static String hash(String password, byte[] salt) throws Exception {
        if (password.length() == 0||password == null) {
        	throw new IllegalArgumentException("Cannot use empty passwords. ");
        }
        SecretKeyFactory SFK = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = SFK.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength));
        return Base64.encodeBase64String(key.getEncoded());
    }
}
