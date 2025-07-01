
package com.junaid.client.service;

import java.io.Serializable;

/**
 *
 * @author junaidxyz
 */
public class LoginRequest implements Serializable{
    private final String cnic;
    private final String password;
    private boolean status;

    public LoginRequest(String cnic, String password) {
        this.cnic = cnic;
        this.password = password;
    }

    public String getCnic() {
        return cnic;
    }

    public String getPassword() {
        return password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
