package com.axreng.backend;

public class Validate {

    public static boolean isValidId(String id) throws NumberFormatException {
        try {
            Integer.valueOf(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return id.length() == 8;
    }

    public static boolean isValidKeyword(String keyword) {
        return keyword.length() > 4 && keyword.length() <= 32;
    }

}
