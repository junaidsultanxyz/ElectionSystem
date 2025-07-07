package com.junaid.generator.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DivisionGenerator {

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

