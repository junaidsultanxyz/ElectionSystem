package com.junaid.client;

// @author junaidxyz


import com.junaid.shared_library.election.*;
import com.junaid.shared_library.sockets.LoginRequest;
import java.util.ArrayList;


public class SessionData {
    private static Voter currentUser;
    private static Vote[] voteData;
    private static ArrayList<Party> partyData;
    private static LoginRequest loginRequest;
    
    public static void LoadPartyData(){
        
    }

    public static Voter getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Voter currentUser) {
        SessionData.currentUser = currentUser;
    }

    public static Vote[] getVoteData() {
        return voteData;
    }

    public static void setVoteData(Vote[] voteData) {
        SessionData.voteData = voteData;
    }

    public static ArrayList<Party> getPartyData() {
        return partyData;
    }

    public static void setPartyData(ArrayList<Party> partyData) {
        SessionData.partyData = partyData;
    }

    public static LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public static void setLoginRequest(LoginRequest loginRequest) {
        SessionData.loginRequest = loginRequest;
    }
    
    
}
