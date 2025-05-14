package com.nikitos.homeflow.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DataParser {
    public static String parseWhoIsNeededString(String input) {
        String output = "";

        if (!input.isEmpty()) {
            if (input.contains("0")) output += "• газовщик ";
            if (input.contains("1")) output += "• сантехник ";
            if (input.contains("2")) output += "• слесарь ";
            if (input.contains("3")) output += "• электрик ";

        }

        return output;
    }

    public static ArrayList<Integer> parseWhoIsNeededIntArray(String input) {
        ArrayList<Integer> output = new ArrayList<>();

        if (!input.isEmpty()) {
            if (input.contains("0")) output.add(0);
            if (input.contains("1")) output.add(1);
            if (input.contains("2")) output.add(2);
            if (input.contains("3")) output.add(3);

        }

        return output;
    }
}
