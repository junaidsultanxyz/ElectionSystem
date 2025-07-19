
package com.junaid.shared_library.sockets;

import static com.junaid.shared_library.sockets.LoginRequest.LoginStatus.*;
import java.io.Serializable;

/**
 *
 * @author junaidxyz
 */
public class LoginRequest implements Serializable{
    String cnic, password;
    LoginStatus status;

    public LoginRequest(String cnic, String password) {
        this.cnic = cnic;
        this.password = password;
        status = PENDING;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public LoginStatus getStatus() {
        return status;
    }

    public void setStatus(LoginStatus status) {
        this.status = status;
    }
    
    public enum LoginStatus {
        REJECTED,
        PENDING,
        APPROVED
    }
}
