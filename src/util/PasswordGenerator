package util;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Parola en az 8 karakter olmali");
        }
        String all = LOWER + UPPER + DIGITS + SYMBOLS;
        StringBuilder sb = new StringBuilder();

        // Her türden en az bir karakter garanti et
        sb.append(pick(LOWER));
        sb.append(pick(UPPER));
        sb.append(pick(DIGITS));
        sb.append(pick(SYMBOLS));

        for (int i = 4; i < length; i++) {
            sb.append(pick(all));
        }
        return shuffle(sb.toString());
    }

    private static char pick(String chars) {
        return chars.charAt(RANDOM.nextInt(chars.length()));
    }

    private static String shuffle(String s) {
        char[] arr = s.toCharArray();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return new String(arr);
    }
}