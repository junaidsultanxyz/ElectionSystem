package com.junaid.server.repository;

// @author junaidxyz
import com.junaid.server.model.Division;
import com.junaid.server.model.Election;
import com.junaid.server.model.Party;
import com.junaid.server.model.Voter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DAO {

    public static boolean addElection(Election election) throws SQLException {
        String sql = "{CALL insert_election(?, ?, ?, ?, ?, ?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, election.getId());
            stmt.setString(2, election.getName());
            stmt.setTime(3, Time.valueOf(election.getStartingTime().toLocalTime()));
            stmt.setTime(4, Time.valueOf(election.getEndingTime().toLocalTime()));
            stmt.setDate(5, Date.valueOf(election.getStartingTime().toLocalDate()));
            stmt.setDate(6, Date.valueOf(election.getEndingTime().toLocalDate()));
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateElection(int id, Election election) throws SQLException {
        String sql = "{CALL update_election(?, ?, ?, ?, ?, ?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, election.getName());
            stmt.setTime(3, Time.valueOf(election.getStartingTime().toLocalTime()));
            stmt.setTime(4, Time.valueOf(election.getEndingTime().toLocalTime()));
            stmt.setDate(5, Date.valueOf(election.getStartingTime().toLocalDate()));
            stmt.setDate(6, Date.valueOf(election.getEndingTime().toLocalDate()));
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteElection(int id) throws SQLException {
        String sql = "{CALL delete_election(?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ArrayList<Election> searchElection(String keyword) throws SQLException {
        ArrayList<Election> elections = new ArrayList<>();
        String sql = "{CALL search_election(?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");

                    LocalDateTime startingTime = LocalDateTime.of(
                            rs.getDate("starting_date").toLocalDate(),
                            rs.getTime("starting_time").toLocalTime()
                    );

                    LocalDateTime endingTime = LocalDateTime.of(
                            rs.getDate("ending_date").toLocalDate(),
                            rs.getTime("ending_time").toLocalTime()
                    );

                    Election election = new Election(id, name, startingTime, endingTime);
                    elections.add(election);
                }
            }
        } catch (SQLException e) {

        }
        return elections;
    }

    public static ArrayList<Election> displayAllElections() throws SQLException {
        ArrayList<Election> elections = new ArrayList<>();
        String sql = "{CALL display_all_elections()}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                LocalDateTime startingTime = LocalDateTime.of(
                        rs.getDate("starting_date").toLocalDate(),
                        rs.getTime("starting_time").toLocalTime()
                );

                LocalDateTime endingTime = LocalDateTime.of(
                        rs.getDate("ending_date").toLocalDate(),
                        rs.getTime("ending_time").toLocalTime()
                );

                Election election = new Election(id, name, startingTime, endingTime);
                elections.add(election);
            }

        } catch (SQLException e) {
        }

        return elections;
    }

    public static boolean addParty(Party party) throws SQLException {
        String sql = "{CALL insert_party(?, ?, ?, ?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, party.getCode());
            stmt.setString(2, party.getName());
            stmt.setString(3, party.getSymbolImagePath());
            stmt.setString(4, party.getFlagImagePath());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteParty(String code) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "{CALL delete_party(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, code);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateParty(String code, Party updatedParty) throws SQLException {
        String sql = "{CALL update_party(?, ?, ?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, updatedParty.getSymbolImagePath());
            stmt.setString(3, updatedParty.getFlagImagePath());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ArrayList<Party> searchParty(String keyword) throws SQLException {
        ArrayList<Party> result = new ArrayList<>();
        String sql = "{CALL search_party(?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    String symbol = rs.getString("symbol");
                    String flag = rs.getString("flag");

                    Party party = new Party(code, name, flag, symbol);
                    result.add(party);
                }
            }
        } catch (SQLException e) {
        }

        return result;
    }

    public static ArrayList<Party> displayAllParties() throws SQLException {
        ArrayList<Party> result = new ArrayList<>();
        String sql = "{CALL display_all_parties()}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                String symbol = rs.getString("symbol");
                String flag = rs.getString("flag");

                Party party = new Party(code, name, flag, symbol);
                result.add(party);
            }
        } catch (SQLException e) {
        }

        return result;
    }

    public static ArrayList<String[]> getCountryResult() throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        String sql = "CALL country_result()";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[5];
                row[0] = rs.getString("party_name");
                row[1] = rs.getString("party_code");
                row[2] = rs.getString("province_name");
                row[3] = rs.getString("vote_type");
                row[4] = String.valueOf(rs.getInt("total_votes"));
                results.add(row);
            }
        }
        return results;
    }

    public static ArrayList<String[]> getCurrentVotes() throws SQLException {
        ArrayList<String[]> votes = new ArrayList<>();
        String sql = "CALL current_votes()";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[6];
                row[0] = rs.getString("voter_name");
                row[1] = rs.getString("voter_cnic");
                row[2] = rs.getString("party_code");
                row[3] = rs.getString("division");
                row[4] = rs.getString("vote_type");
                row[5] = rs.getTimestamp("vote_time").toString();
                votes.add(row);
            }
        }

        return votes;
    }

    public static ArrayList<String[]> getMNAWinners() throws SQLException {
        ArrayList<String[]> winners = new ArrayList<>();
        String sql = "CALL mna_winners()";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("party_name");
                row[1] = rs.getString("party_code");
                row[2] = rs.getString("division");
                row[3] = String.valueOf(rs.getInt("votes"));
                winners.add(row);
            }
        }
        return winners;
    }

    public static ArrayList<String[]> getMPAWinners() throws SQLException {
        ArrayList<String[]> winners = new ArrayList<>();
        String sql = "CALL mpa_winners()";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("party_name");
                row[1] = rs.getString("party_code");
                row[2] = rs.getString("division");
                row[3] = String.valueOf(rs.getInt("votes"));
                winners.add(row);
            }
        }

        return winners;
    }

    public static ArrayList<Voter> getTotalVoters() throws SQLException {
        ArrayList<Voter> voters = new ArrayList<>();
        String sql = "CALL getTotalVoters()";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String cnic = rs.getString("cnic");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String divisionId = rs.getString("division");
                Division division = new Division(divisionId);

                Voter voter = new Voter(cnic, name, age, division, null);
                voters.add(voter);
            }
        }
        return voters;
    }

    public static ArrayList<String[]> searchCurrentVotes(String keyword) throws SQLException {
        ArrayList<String[]> votes = new ArrayList<>();
        String sql = "{CALL search_current_votes(?)}";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[6];
                    row[0] = rs.getString("voter_name");
                    row[1] = rs.getString("voter_cnic");
                    row[2] = rs.getString("party_code");
                    row[3] = rs.getString("division");
                    row[4] = rs.getString("vote_type");
                    row[5] = rs.getTimestamp("vote_time").toString();
                    votes.add(row);
                }
            }
        }
        return votes;
    }

    public static ArrayList<Voter> searchTotalVoters(String keyword) throws SQLException {
        ArrayList<Voter> voters = new ArrayList<>();
        String sql = "{CALL search_total_voters(?)}";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String cnic = rs.getString("cnic");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String divisionId = rs.getString("division");

                    Division division = new Division(divisionId);

                    Voter voter = new Voter(cnic, name, age, division, null);
                    voters.add(voter);
                }
            }
        }
        return voters;
    }

    public static ArrayList<String[]> searchMNAWinners(String keyword) throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        String sql = "{CALL search_mna_winners(?)}";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[4];
                    row[0] = rs.getString("party_name");
                    row[1] = rs.getString("party_code");
                    row[2] = rs.getString("division");
                    row[3] = String.valueOf(rs.getInt("votes"));
                    results.add(row);
                }
            }
        }

        return results;
    }

    public static ArrayList<String[]> searchMPAWinners(String keyword) throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        String sql = "{CALL search_mpa_winners(?)}";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[4];
                    row[0] = rs.getString("party_name");
                    row[1] = rs.getString("party_code");
                    row[2] = rs.getString("division");
                    row[3] = String.valueOf(rs.getInt("votes"));
                    results.add(row);
                }
            }
        }

        return results;
    }

    public static ArrayList<String[]> searchCountryResult(String keyword) throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        String sql = "CALL search_country_result(?)";
        Connection conn = DBConnection.getConnection();

        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[5];
                    row[0] = rs.getString("party_name");
                    row[1] = rs.getString("party_code");
                    row[2] = rs.getString("province_name");
                    row[3] = rs.getString("vote_type");
                    row[4] = String.valueOf(rs.getInt("total_votes"));
                    results.add(row);
                }
            }
        }

        return results;
    }

}
