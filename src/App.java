import util.PasswordGenerator;

public class App {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(PasswordGenerator.generate(16));
        }
    }
}