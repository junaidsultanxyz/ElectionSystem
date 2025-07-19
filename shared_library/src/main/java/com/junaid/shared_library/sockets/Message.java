package com.junaid.shared_library.sockets;

// @author junaidxyz

import java.io.Serializable;


public class Message implements Serializable{
    private String type;
    private Object message;
    
    public Message (String type, Object obj){
        this.type = type;
        this.message = obj;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
