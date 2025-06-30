
package com.junaid.client.service;

/**
 *
 * @author junaidxyz
 */
public class LoginRequest {
    private int cnic;
    private int password;
    private boolean status;

    public LoginRequest(int cnic, int password) {
        this.cnic = cnic;
        this.password = password;
    }

    public int getCnic() {
        return cnic;
    }

    public int getPassword() {
        return password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
