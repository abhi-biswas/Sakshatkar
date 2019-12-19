package mainclasses;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {
    static SecureRandom RANDOM = new SecureRandom();
    public static String generateSalt()
    {
        byte[] salt= new byte[32];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
