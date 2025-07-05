package com.junaid.generator;

import com.junaid.generator.model.DivisionGenerator;
import com.junaid.generator.model.VoteGenerator;
import com.junaid.generator.model.VoterGenerator;

public class Main {
    public static void main(String[] args) {
        DivisionGenerator.generate(1, 350, 67, "datagenerator/out/division_data.sql");

        
        VoterGenerator.generate("42301", 1, 1000, 350, "datagenerator/out/voter_data_42301.sql");
        VoterGenerator.generate("35202", 1, 1000, 350, "datagenerator/out/voter_data_35202.sql");


        VoteGenerator.generate(328123321, 3520200000001L, 3520200001000L, "2025-07-05 00:00:00", "2025-07-12 00:00:00", "datagenerator/out/vote_data_35202.sql");
        VoteGenerator.generate(328123321, 4230100000001L, 4230100001000L, "2025-07-05 00:00:00", "2025-07-12 00:00:00", "datagenerator/out/vote_data_42301.sql");
    }
}