package com.isep.musify;

public class Credentials {


    private static final String ClientID = "Enter Client ID";

    private static final String RedirectURI = "musify://callback";

    public static String getClientID() {
        return ClientID;
    }

    public static String getRedirectURI() {
        return RedirectURI;
    }
}
