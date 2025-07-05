package com.junaid.generator.model;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class VoteGenerator {

    static final String[] PARTY_CODES = {
        "PTI", "PMLN", "PPP", "MQM", "ANP", "JUIF", "BNP", "JI", "TLP", "GDA"
    };

    static final Random rand = new Random();

    public static void generate(long electionId, long startingCnic, long endingCnic, String startingTime, String endingTime, String filePath){

        List<String> voteRows = new ArrayList<>();

        for (long cnic = startingCnic; cnic <= endingCnic; cnic++) {
            boolean votedNA = rand.nextBoolean();
            boolean votedPA = rand.nextBoolean();

            if (votedNA) {
                String vote = generateVote(electionId, cnic, "NA", startingTime, endingTime);
                voteRows.add(vote);
            }

            if (votedPA) {
                String vote = generateVote(electionId, cnic, "PA", startingTime, endingTime);
                voteRows.add(vote);
            }
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("INSERT INTO vote (election_id, cnic, party_code, vote_type, vote_time) VALUES\n");

            for (int i = 0; i < voteRows.size(); i++) {
                writer.write(voteRows.get(i));
                writer.write((i != voteRows.size() - 1) ? ",\n" : ";\n");
            }

            System.out.println("vote_data.sql generated successfully with " + voteRows.size() + " votes.");

        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }

    private static String generateVote(long election_id, long cnic, String voteType, String startTime, String endTime) {
        String party = PARTY_CODES[rand.nextInt(PARTY_CODES.length)];
        Timestamp randomTime = generateRandomTimestamp(startTime, endTime);
        return String.format("(%d, '%013d', '%s', '%s', '%s')",
                election_id, cnic, party, voteType, randomTime.toString());
    }

    private static Timestamp generateRandomTimestamp(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);

            long randomMillis = startDate.getTime() + (long) (rand.nextDouble() * (endDate.getTime() - startDate.getTime()));
            return new Timestamp(randomMillis);

        } catch (Exception e) {
            throw new RuntimeException("Timestamp parsing failed: " + e.getMessage());
        }
    }
}
