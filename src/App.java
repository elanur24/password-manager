
import crypto.EncryptionService;

public class App {
    public static void main(String[] args) throws Exception {
        char[] master = "anaParola123".toCharArray();
        String encrypted = EncryptionService.encrypt("deneme123", master);
        System.out.println(encrypted);
        System.out.println(EncryptionService.decrypt(encrypted, master));

        try {
            EncryptionService.decrypt(encrypted, "yanlisParola".toCharArray());
        } catch (Exception e) {
            System.out.println("Yanlis parola: cozulemedi");
        }
    }
}