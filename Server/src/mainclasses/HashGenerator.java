package mainclasses;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
public class HashGenerator {
    public static String generateHash(String input) throws NoSuchAlgorithmException
    {
        String hash = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(input.getBytes());
        BigInteger num = new BigInteger(1,bytes);
        hash = num.toString(16);
        while(hash.length()<32)
            hash = "0"+hash;
        return hash;
    }
}
