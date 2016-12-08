/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Kristian Nielsen
 */
public class OutputFormat {

    private static String SQL_NULL = "NULL";
    
    private static String[] OUTPUT_OPTIONS = {
        "INSERT",
        "UPDATE"
    };
    private static int CURRENT_OUTPUT_OPTION = -1;
    private static List<String> OUTPUT_LINES = new ArrayList();

    public static void getSqlInsert() {

        OUTPUT_LINES.add("INSERT INTO " + Table.TABLE_NAME);
        OUTPUT_LINES.add("VALUES (" + Table.getJoinedColumnNames() + ")");
        boolean isFirstRow = true;

        for (JsonObject jsonObject : Table.ROWS) {
            String row = "(";
            if (!isFirstRow) {
                row = ",(";
            }
            isFirstRow = false;
            for (String columnName : Table.COLUMN_NAMES) {
                row += makeSqlFriendly(jsonObject.get(columnName).getAsString());
                row += ", ";
            }
            row = row.substring(0, row.length() - 2);
            row += ")";

            OUTPUT_LINES.add(row);
        }
        if (Globals.DEVELOPER_MODE == 1) {
            printOutputLines();
        }

    }

    private static void getSqlUpdate() {
        for (JsonObject jsonObject : Table.ROWS) {
            OUTPUT_LINES.add("UPDATE " + Table.TABLE_NAME + " SET");
            boolean firstRow = true;
            for (String columnName : Table.COLUMN_NAMES) {
                if (!columnName.equals(Table.PRIMARY_KEY)) {

                    String line = "";
                    if (!firstRow) {
                        line = ",";
                    }
                    line += columnName;
                    line += " = ";
                    line += makeSqlFriendly(jsonObject.get(columnName).getAsString());

                    firstRow = false;
                    OUTPUT_LINES.add(line);
                }
            }
            OUTPUT_LINES.add("WHERE "
                    + Table.PRIMARY_KEY
                    + " = "
                    + makeSqlFriendly(jsonObject.get(Table.PRIMARY_KEY).getAsString()));
        }
        if (Globals.DEVELOPER_MODE == 1) {
            printOutputLines();
        }
    }

    public static String makeSqlFriendly(String input) {
        if(input.equals(SQL_NULL)){
            return input;
        }
        
        try {
            int i = Integer.parseInt(input);
            return input;
        } catch (Exception e) {
            
            return "'" + input.replaceAll("'", "''") + "'";
        }

    }

    public static List<String> getOutputLines() {
        return OUTPUT_LINES;
    }

    public static void printOutputLines() {
        for (String s : OUTPUT_LINES) {
            System.out.println(s);
        }
    }

    public static void printOutputOptionsNumbered() {
        for (int i = 0; i < OUTPUT_OPTIONS.length; i++) {
            System.out.println(i + ". " + OUTPUT_OPTIONS[i]);
        }
        System.out.println("(write number from 0 to " + Integer.toString(OUTPUT_OPTIONS.length - 1) + ")");
    }

    static boolean chooseOption(int selectedOption, Scanner s) {
        if (selectedOption < 0 || selectedOption >= OUTPUT_OPTIONS.length) {
            System.out.println("Invalid option: " + selectedOption);
            return false;
        }
        CURRENT_OUTPUT_OPTION = selectedOption;
        System.out.println("You chose: " + OUTPUT_OPTIONS[CURRENT_OUTPUT_OPTION]);
        if (OUTPUT_OPTIONS[CURRENT_OUTPUT_OPTION].equals("UPDATE")) {
            Table.promptUserToChoosePrimaryKey(s);
        }
        return true;
    }

    static void format() {
        String optionLiteral = OUTPUT_OPTIONS[CURRENT_OUTPUT_OPTION];

        switch (optionLiteral) {
            case "INSERT":
                getSqlInsert();
                break;
            case "UPDATE":
                getSqlUpdate();
                break;
        }
    }

}
