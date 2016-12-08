/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

import java.nio.charset.Charset;
import java.util.Scanner;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Kristian Nielsen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        setup();
    }
    
    private static void setup(){
        Scanner s = new Scanner(System.in);
        boolean doContinue = false;
        
        
        
//        Intro
        printLogo();
        System.out.println("Welcome to MagiXcel");
        System.out.println("Current encoding is " + Charset.defaultCharset());
        
//        Table name
        while(!doContinue){
            System.out.println("Type your desired table name:");
            if(s.hasNextLine()){
                Table.setTableName(s.nextLine());
            }
            
            doContinue = isThisOk(s);
        }
        doContinue = false;
        
//        File path
        String path = "";
        while(!doContinue){
            System.out.println("Enter FULL path of .csv or .txt file you wish to convert");
            if(s.hasNextLine()){
                path = StringEscapeUtils.escapeJava(s.nextLine());
            }
            FileMan.setPath(path);
            if(FileMan.currentPathIsValid()){
                doContinue = isThisOk(s);
            }else{
                System.out.println("ERROR: Invalid Path");
                doContinue = false;
            }
            
        }
        doContinue = false;
        
        
        
        
        
//        File setup
        FileMan.getLinesFromFile();
        Table.setColumnNames(FileMan.takeFirstRow());
        FileMan.addRowsToTable();
        
//        Choose output mode
        System.out.println("Choose output mode:");
        OutputFormat.printOutputOptionsNumbered();
        int selectedOption = -1;
        while(!doContinue){
            if(s.hasNextLine()){
                selectedOption = Integer.parseInt(s.nextLine());
            }
            doContinue = OutputFormat.chooseOption(selectedOption);
        }
        doContinue = false;
        OutputFormat.format();
        
        FileMan.writeToFile();
        
        
        System.out.println("Thank you for using MagiXcel!");
        System.out.println("Press Enter to exit");
        s.nextLine();
        s.close();
        
    }
    
    private static boolean isThisOk(Scanner s){
        System.out.println("Is this ok? (y/n)");
        if(s.nextLine().equals("n")){
            return false;
        }
        return true;
    }
    
    
    private static void printLogo(){
        System.out.println("** ** **** **** * * * *** *** *");
        System.out.println("* * * *  * *    *  *  *   *** *");
        System.out.println("*   * **** *  * *  *  *   *   *");
        System.out.println("*   * *  * **** * * * *** *** ***");
        System.out.println("");
    }
    
    
}
