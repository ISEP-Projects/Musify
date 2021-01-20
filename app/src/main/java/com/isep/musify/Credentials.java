package com.isep.musify;

public class Credentials {

    private static final String ClientID = "client id";
    private static final String RedirectURI = "callback";

    public static String getClientID() {
        return ClientID;
    }

    public static String getRedirectURI() {
        return RedirectURI;
    }
}
