package com.junaid.generator.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DivisionGenerator {

    static final int[] CITY_IDS = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
        16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
        32, 33, 34, 35
    };

    static final Random rand = new Random();

    public static void generate(int startingDiv, int endingDiv, int totalCities, String filePath){
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("INSERT INTO division (id, city_id) VALUES\n");

            for (int i = startingDiv; i <= endingDiv; i++) {
                int cityId = rand.nextInt(totalCities - 1) + 1;

                String row = String.format("(%d, %d)", i, cityId);

                if (i != endingDiv) {
                    row += ",\n";
                } else {
                    row += ";\n";
                }

                writer.write(row);
            }

            System.out.println("division_data.sql generated with 350 random divisions.");

        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }
}

