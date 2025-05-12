package com.nikitos.homeflow.Util;

public class DataParser {
    public static String parseWhoIsNeeded(String input) {
        String output = "";

        if (!input.isEmpty()) {
            if (input.contains("0")) output += "• газовщик ";
            if (input.contains("1")) output += "• сантехник ";
            if (input.contains("2")) output += "• слесарь ";
            if (input.contains("3")) output += "• сварщик ";

        }

        return output;
    }
}
