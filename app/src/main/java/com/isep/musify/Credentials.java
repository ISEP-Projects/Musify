package com.isep.musify;

public class Credentials {
    private static final String ClientID = "e6d14eb0bc4242f69c7dd46cbc02b74b";
    private static final String RedirectURI = "musify://callback";

    public static String getClientID() {
        return ClientID;
    }

    public static String getRedirectURI() {
        return RedirectURI;
    }
}
