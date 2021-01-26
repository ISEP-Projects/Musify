package com.isep.musify;

public class Credentials {

    private static final String ClientID = "393ffdcc7a754669956775f8ef8daf39";
    private static final String RedirectURI = "musify://callback";

    public static String getClientID() {
        return ClientID;
    }

    public static String getRedirectURI() {
        return RedirectURI;
    }
}
