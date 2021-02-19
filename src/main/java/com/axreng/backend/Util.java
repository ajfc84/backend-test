package com.axreng.backend;

import java.net.MalformedURLException;
import java.net.URL;

public class Util {

    public static boolean isUrlAbsoluteBaseOrRelative(java.net.URL baseUrl, String parsedUrl) {
        return parsedUrl.startsWith(baseUrl.toString()) ||
                parsedUrl.startsWith("//" + baseUrl.getHost() + baseUrl.getPath())  ||
                parsedUrl.startsWith("/") ||
                parsedUrl.startsWith("./") ||
                parsedUrl.matches("[^#:]+");
    }

    public static URL anchorLinkToAbsoluteUrl(URL baseUrl, String parsedUrl) {
        URL result = null;
        try {
            if (parsedUrl.startsWith(baseUrl.getProtocol())) result = new URL(parsedUrl);
            else if(parsedUrl.startsWith("//")) result = new URL(baseUrl.getProtocol() + ":" + parsedUrl);
            else if(parsedUrl.startsWith("/")) result = new URL(baseUrl + parsedUrl);
            else if(parsedUrl.startsWith("./")) result = new URL(baseUrl + parsedUrl.substring(1));
            else if(parsedUrl.matches("[^#:]+")) result = new URL(baseUrl + "/" + parsedUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String requestHashTo8DigitId(int hash) {
        return String.valueOf(hash).substring(0, 8);
    }

    public static String stripLastForwardSlash(String baseUrl) {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

}
