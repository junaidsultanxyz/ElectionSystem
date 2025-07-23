package com.junaid.client.service;

// @author junaidxyz

import com.junaid.client.Main;
import com.junaid.client.SessionData;
import com.junaid.shared_library.election.*;
import com.junaid.shared_library.sockets.*;


public class Services {
    public static void sendLoginRequest (String cnic, String password){
        LoginRequest request = new LoginRequest(cnic, password);
        Main.getClient().sendMessage(new Message("LOGIN", request));
        SessionData.setLoginRequest(request);
    }
    
    public static void sendCastVoteRequest (Vote[] votes){
//        SessionData.setVoteData(votes);
        Main.getClient().sendMessage(new Message("VOTE", votes));
    }
    
    public static void sendPartyInfoRequest(){
        Main.getClient().sendMessage(new Message("PARTY", null));
        System.out.println("party request sent");
    }
    
    public static void sendCountryResultRequest(){
        Main.getClient().sendMessage(new Message("RESULT_COUNTRY", null));
        System.out.println("country request sent");
    }
    
    public static void sendAreaResultRequest(int div){
        Main.getClient().sendMessage(new Message("RESULT_AREA", div));
        System.out.println("area request sent");
    }
}
