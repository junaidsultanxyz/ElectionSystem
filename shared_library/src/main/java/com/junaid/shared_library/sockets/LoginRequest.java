
package com.junaid.client.service;

import static com.junaid.client.service.LoginRequest.LoginStatus.*;
import java.io.Serializable;

/**
 *
 * @author junaidxyz
 */
public class LoginRequest implements Serializable{
    static String cnic;
    static String password;
    static LoginStatus status;

    public static String sendRequest(String cnic, String password) {
        LoginRequest.cnic = cnic;
        LoginRequest.password = password;
        status = PENDING;
        
        return "LOGIN," + cnic + "," + password;
    }

    public static String getCnic() {
        return cnic;
    }

    public static String getPassword() {
        return password;
    }

    public static LoginStatus getStatus() {
        return status;
    }

    public static void setStatus(LoginStatus status) {
        LoginRequest.status = status;
    }
    
    public enum LoginStatus {
        INVALID,
        PENDING,
        APPROVED
    }
}
