package com.isep.musify;

public class Credentials {
    private static final String ClientID = "7ecba07b6d0f409da5ca9d8839456be9";
    private static final String RedirectURI = "musify://callback";

    public static String getClientID() {
        return ClientID;
    }

    public static String getRedirectURI() {
        return RedirectURI;
    }
}
