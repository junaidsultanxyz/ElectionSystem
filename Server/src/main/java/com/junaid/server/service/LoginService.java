package com.junaid.server.service;

// @author junaidxyz

import com.junaid.server.model.Voter;
import com.junaid.server.repository.DAO;
import java.sql.SQLException;


public class LoginService {
    public static Voter validateLogin (String cnic, String password){
        try {
            Voter voter = DAO.validateLogin(cnic, password);
            if (voter != null){
                System.out.println("user accepted at services");
                return voter;
            }
            else {
                System.out.println("user rejected at services");
                return null;
            }
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static String sendApproved(){
        return "LOGIN,APPROVED";
    }
    
    public static String sendRejected(){
        return "LOGIN,REJECTED";
    }
}
