package com.sabel.JRechnung.model;

import java.util.UUID;

public class UUIDStringGenerator {

    public static String generate(){
        UUID uuid = UUID.randomUUID();
        if(uuid != null){
            return uuid.toString();
        }

        return null;
    }

}
