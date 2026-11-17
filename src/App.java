import model.Credential;
import storage.VaultStorage;
import util.PasswordGenerator;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        VaultStorage storage = new VaultStorage("vault.dat");
        char[] master = readMasterPassword(storage);
        List<Credential> credentials;

        try {
            credentials = storage.load(master);
        } catch (Exception e) {
            System.out.println("Ana parola yanlis ya da dosya bozuk.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- SIFRE YONETICISI ---");
            System.out.println("1) Kayit ekle");
            System.out.println("2) Kayitlari listele");
            System.out.println("3) Kayit sil");
            System.out.println("4) Rastgele parola uret");
            System.out.println("5) Kaydet ve cik");
            System.out.print("Secim: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addCredential(credentials);
                case "2" -> listCredentials(credentials);
                case "3" -> deleteCredential(credentials);
                case "4" -> System.out.println("Uretilen parola: " + PasswordGenerator.generate(16));
                case "5" -> {
                    storage.save(credentials, master);
                    System.out.println("Kaydedildi. Gule gule!");
                    running = false;
                }
                default -> System.out.println("Gecersiz secim.");
            }
        }
    }

    private static char[] readMasterPassword(VaultStorage storage) {
        if (!storage.exists()) {
            System.out.println("Ilk calistirma: bir ana parola belirle.");
        }
        System.out.print("Ana parola: ");
        return scanner.nextLine().toCharArray();
    }

    private static void addCredential(List<Credential> list) {
        System.out.print("Site: ");
        String site = scanner.nextLine();
        System.out.print("Kullanici adi: ");
        String user = scanner.nextLine();
        System.out.print("Sifre (bos birakirsan uretilir): ");
        String pass = scanner.nextLine();
        if (pass.isBlank()) {
            pass = PasswordGenerator.generate(16);
            System.out.println("Uretilen sifre: " + pass);
        }
        list.add(new Credential(site, user, pass));
        System.out.println("Eklendi.");
    }

    private static void listCredentials(List<Credential> list) {
        if (list.isEmpty()) {
            System.out.println("Kayit yok.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Credential c = list.get(i);
            System.out.println((i + 1) + ") " + c + " -> " + c.getPassword());
        }
    }

    private static void deleteCredential(List<Credential> list) {
        listCredentials(list);
        if (list.isEmpty()) return;
        System.out.print("Silinecek numara: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < list.size()) {
                list.remove(index);
                System.out.println("Silindi.");
            } else {
                System.out.println("Gecersiz numara.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Gecersiz giris.");
        }
    }
}