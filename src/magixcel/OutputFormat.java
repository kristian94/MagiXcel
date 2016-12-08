/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristian Nielsen
 */
public class OutputFormat {
    
    private static String[] OUTPUT_OPTIONS = {
        "INSERT",
        "UPDATE"
    };
    private static int CURRENT_OUTPUT_OPTION = -1;
    private static List<String> OUTPUT_LINES = new ArrayList();
    
    
    public static void getSqlInsert(){

        OUTPUT_LINES.add("INSERT INTO " + Table.TABLE_NAME);
        OUTPUT_LINES.add("VALUES (" + Table.getJoinedColumnNames() + ")");
        boolean isFirstRow = true;
        
        for(JsonObject jsonObject: Table.ROWS){
            String row = "(";
            if(!isFirstRow){
                row = ",(";
            }
            isFirstRow = false;
            for(String columnName: Table.COLUMN_NAMES){
                row += jsonObject.get(columnName);
                row += ", ";
            }
            row = row.substring(0, row.length()-2);
            row += ")";
            
            OUTPUT_LINES.add(row);
        }
        if(Globals.DEVELOPER_MODE == 1){
            printOutputLines();
        }
        

    }
    
    private static void getSqlUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static List<String> getOutputLines(){
        return OUTPUT_LINES;
    }
    
    public static void printOutputLines(){
        for(String s: OUTPUT_LINES){
            System.out.println(s);
        }
    }
    
    public static void printOutputOptionsNumbered(){
        for (int i = 0; i < OUTPUT_OPTIONS.length; i++) {
            System.out.println(i + ". " + OUTPUT_OPTIONS[i]);
        }
        System.out.println("(write number from 0 to " + Integer.toString(OUTPUT_OPTIONS.length-1) + ")");
    }

    static boolean chooseOption(int selectedOption) {
        if(selectedOption < 0 || selectedOption >= OUTPUT_OPTIONS.length){
            System.out.println("Invalid option: " + selectedOption);
            return false;
        }
        CURRENT_OUTPUT_OPTION = selectedOption;
        System.out.println("You chose: " + OUTPUT_OPTIONS[CURRENT_OUTPUT_OPTION]);
        return true;
    }

    static void format() {
        String optionLiteral = OUTPUT_OPTIONS[CURRENT_OUTPUT_OPTION];
        
        switch(optionLiteral){
            case "INSERT":
                getSqlInsert();
                break;
            case "UPDATE":
                getSqlUpdate();
                break;
        }
    }

    
    
}
