package mainclasses;
import java.security.SecureRandom;
public class SaltGenerator {
    static SecureRandom RANDOM = new SecureRandom();
    public static String generateSalt()
    {
        byte[] salt= new byte[32];
        RANDOM.nextBytes(salt);
        return new String(salt);
    }
}
