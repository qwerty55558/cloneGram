package com.jsy.clonegram.dao;

public enum EmitterType {
    NOTIFICATION("notifications"),
    MESSAGE("messages");

    private final String type;

    EmitterType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
