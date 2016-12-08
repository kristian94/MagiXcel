/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kristian Nielsen
 */
public class Table {

    public static String TABLE_NAME;
    public static String[] COLUMN_NAMES;
    public static String PRIMARY_KEY;
    public static List<JsonObject> ROWS = new ArrayList();

    public static void setTableName(String tableName) {
        TABLE_NAME = tableName;
        System.out.println("Your table name is now: " + tableName);
    }

    public static void setColumnNames(String textLine) {
        COLUMN_NAMES = textLine.split(FileMan.DELIMITER);
        if (Globals.DEVELOPER_MODE == 1) {
            System.out.println("Column Names are: ");
            for (String s : COLUMN_NAMES) {
                System.out.println(s);
            }
        }
    }

    public static String getJoinedColumnNames() {
        String result = "";
        for (String s : COLUMN_NAMES) {
            result += s + ", ";
        }

        return result.substring(0, result.length() - 2);
    }

    public static boolean addRow(String textLine) {
        if (textLine == null || textLine.length() < 1) {
            return false;
        }
        String[] rowArray = textLine.split(FileMan.DELIMITER);
        JsonObject jsonObject = new JsonObject();
        if (rowArray.length != COLUMN_NAMES.length && Globals.DEVELOPER_MODE == 1) {
            System.out.println("UNEVEN LENGTHS, MIGHT CAUSE AN ISSUE");
        }
        for (int i = 0; i < rowArray.length; i++) {
            jsonObject.addProperty(COLUMN_NAMES[i], rowArray[i]);
        }
        if (Globals.DEVELOPER_MODE == 1) {
            System.out.println("Added row:");
            System.out.println(jsonObject.toString());
        }

        ROWS.add(jsonObject);

        return true;
    }

    public static void promptUserToChoosePrimaryKey(Scanner s) {
        boolean doContinue = false;
        System.out.println("Choose a primary key:");
        printColumnNamesNumbered();
        int selectedOption = -1;
        while (!doContinue) {

            if (s.hasNextLine()) {
                selectedOption = Integer.parseInt(s.nextLine());
            }
            doContinue = choosePrimaryKey(selectedOption);
        }

//        while(!doContinue){
//            if(s.hasNextLine()){
//                selectedOption = Integer.parseInt(s.nextLine());
//            }
//            doContinue = OutputFormat.chooseOption(selectedOption, s);
//        }
    }

    public static void printColumnNamesNumbered() {
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            System.out.println(i + ". " + COLUMN_NAMES[i]);
        }
    }
    
    private static boolean choosePrimaryKey(int selectedOption){
        if(selectedOption < 0 || selectedOption >= COLUMN_NAMES.length){
            return false;
        }
        PRIMARY_KEY = COLUMN_NAMES[selectedOption];
        System.out.println("You chose: " + PRIMARY_KEY);
        return true;
    }

}
