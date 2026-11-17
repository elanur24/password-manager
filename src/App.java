
import crypto.PasswordHasher;

public class App {
    public static void main(String[] args) throws Exception {
        String stored = PasswordHasher.hash("anaParola123".toCharArray());
        System.out.println(stored);
        System.out.println(PasswordHasher.verify("anaParola123".toCharArray(), stored));
        System.out.println(PasswordHasher.verify("yanlisParola".toCharArray(), stored));
    }
}