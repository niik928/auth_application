package com.example.auth_application.helpers;

import java.util.UUID;
public class UserHelper {

    public static UUID parseUUID(String uuid){
        return UUID.fromString(uuid);
    }

}