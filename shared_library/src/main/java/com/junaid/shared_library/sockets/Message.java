package com.junaid.client.controller;

// @author junaidxyz

import java.io.Serializable;


public class Message implements Serializable{
    public Object message;
    
    public Message (Object obj){
        this.message = obj;
    }
}
