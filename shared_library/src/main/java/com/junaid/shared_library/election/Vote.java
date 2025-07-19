package com.junaid.server.model;

// @author junaidxyz

import java.io.Serializable;
import java.time.LocalDateTime;


public class Vote implements Serializable{
    private int id;
    private String voterCNIC;
    private String partyCode;
    private VoteType voteType;
    private LocalDateTime timestamp;

    public Vote(String voterCNIC, String partyCode, VoteType voteType) {
        this.voterCNIC = voterCNIC;
        this.partyCode = partyCode;
        this.voteType = voteType;
    }

    public Vote(int id, String voterCNIC, String partyCode, VoteType voteType, LocalDateTime timestamp) {
        this.id = id;
        this.voterCNIC = voterCNIC;
        this.partyCode = partyCode;
        this.voteType = voteType;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoterCNIC() {
        return voterCNIC;
    }

    public void setVoterCNIC(String voterCNIC) {
        this.voterCNIC = voterCNIC;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
