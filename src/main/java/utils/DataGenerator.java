package utils;

import java.util.Random;

public class DataGenerator {

    public static String getRandomEmail() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        // generate random 7 letters
        for (int i = 0; i < 7; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        // add random number
        int number = 1000 + random.nextInt(9000);

        return sb.toString() + number + "@mailsac.com";
    }
    /**
     * Extracts the username part from an email address.
     * Example: "john.doe@example.com" -> "john.doe"
     */
    public static String getUsernameFromEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        return email.split("@")[0];
    }
}
