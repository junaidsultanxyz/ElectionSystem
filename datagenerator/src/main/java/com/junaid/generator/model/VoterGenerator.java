package com.junaid.generator.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class VoterGenerator {

    static final String[] FIRST_NAMES = {
        "Aysha", "Ahmed", "Ali", "Ayesha", "Fatima", "Hassan", "Imran", "Iqra", "Kiran", "Laiba", "Zainab",
        "Bilal", "Nadia", "Sana", "Sara", "Usman", "Hamza", "Hina", "Anum", "Zeeshan", "Shazia",
        "Asad", "Yasir", "Rabia", "Tariq", "Noman", "Farhan", "Maham", "Mehwish", "Umar", "Fawad",
        "Fahad", "Lubna", "Taha", "Neha", "Haris", "Muneeb", "Sadia", "Junaid", "Shehryar", "Komal",
        "Rashid", "Beenish", "Nimra", "Fiza", "Jawad", "Waleed", "Shahzaib", "Nashit", "Sohail", "Saad"
    };

    static final String[] LAST_NAMES = {
        "Khan", "Malik", "Butt", "Raja", "Sultan", "Shaikh", "Chaudhry", "Farooq", "Qureshi", "Hussain",
        "Zaman", "Rafiq", "Mehmood", "Mirza", "Tariq", "Naeem", "Nawaz", "Iqbal", "Ansari", "Shah",
        "Yousaf", "Rehman", "Bashir", "Akram", "Riaz", "Siddiqui", "Shafiq", "Dar", "Sabir", "Latif",
        "Kazmi", "Nisar", "Afzal", "Masood", "Hanif", "Najeeb", "Waheed", "Javed", "Rashid", "Hameed",
        "Habib", "Idrees", "Tufail", "Abid", "Tahir", "Qamar", "Waqas", "Imtiaz", "Aslam", "Zafar"
    };

    static final Random rand = new Random();

    public static void generate(String cnicPrefix, long startingRecord, long endingRecord, int maxDivisions, String filePath){
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("INSERT INTO voter(cnic, name, age, division_id, password) VALUES\n");

            for (long i = startingRecord; i <= endingRecord; i++) {
                String cnic = generateCnic(cnicPrefix, i);
                String fullName = generateFullName();
                int age = rand.nextInt(60) + 18;
                int division = rand.nextInt(maxDivisions) + 1;
                String password = generatePassword(6);

                String row = String.format("('%s', '%s', %d, %d, '%s')", cnic, fullName, age, division, password);

                if (i != endingRecord) {
                    row += ",\n";
                } else {
                    row += ";\n";
                }

                writer.write(row);
            }

            System.out.println("File 'voter_data.sql' created using CNIC prefix: " + cnicPrefix);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String generateCnic(String prefix, long id) {
        String suffix = String.format("%08d", id);
        return prefix + suffix;
    }

    private static String generateFullName() {
        String firstName = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    private static String generatePassword(int minLength) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#_.@";
        int length = minLength + rand.nextInt(4); // 6 to 9 characters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
