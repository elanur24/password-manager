package storage;

import crypto.EncryptionService;
import model.Credential;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class VaultStorage {
    private final Path file;

    public VaultStorage(String fileName) {
        this.file = Path.of(fileName);
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public void save(List<Credential> credentials, char[] masterPassword)
            throws IOException, GeneralSecurityException {
        StringBuilder sb = new StringBuilder();
        for (Credential c : credentials) {
            sb.append(encode(c.getSiteName())).append(",")
              .append(encode(c.getUsername())).append(",")
              .append(encode(c.getPassword())).append("\n");
        }
        String encrypted = EncryptionService.encrypt(sb.toString(), masterPassword);
        Files.writeString(file, encrypted, StandardCharsets.UTF_8);
    }

    public List<Credential> load(char[] masterPassword)
            throws IOException, GeneralSecurityException {
        List<Credential> list = new ArrayList<>();
        if (!exists()) {
            return list;
        }
        String encrypted = Files.readString(file, StandardCharsets.UTF_8);
        String plain = EncryptionService.decrypt(encrypted, masterPassword);
        for (String line : plain.split("\n")) {
            if (line.isBlank()) continue;
            String[] p = line.split(",");
            list.add(new Credential(decode(p[0]), decode(p[1]), decode(p[2])));
        }
        return list;
    }

    private static String encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String s) {
        return new String(Base64.getDecoder().decode(s), StandardCharsets.UTF_8);
    }
}