
import model.Credential;
import storage.VaultStorage;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        char[] master = "anaParola123".toCharArray();
        VaultStorage storage = new VaultStorage("vault.dat");

        storage.save(List.of(
                new Credential("github.com", "elanur", "deneme123"),
                new Credential("gmail.com", "elanur", "sifre,456")), master);

        for (Credential c : storage.load(master)) {
            System.out.println(c + " -> " + c.getPassword());
        }
    }
}