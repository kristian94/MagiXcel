/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.Charsets;

/**
 *
 * @author Kristian Nielsen
 */
public class FileMan {

    private static String INPUT_FILE_PATH;
    private static String OUTPUT_FILE_DIRECTORY;
    private static List<String> LINES_FROM_INPUT_FILE = new ArrayList();
    public static String DELIMITER = "\t";

    public static boolean setPath(String path) {
        if (!Validate.pathIsValid(path)) {
            return false;
        }
        INPUT_FILE_PATH = path;
        OUTPUT_FILE_DIRECTORY = getDirectoryFromPath(path);
        System.out.println("Input file path is now " + INPUT_FILE_PATH);
        System.out.println("Output directory is now " + OUTPUT_FILE_DIRECTORY);
        return true;
    }

    public static void setInputFile() {

    }

    public static boolean currentPathIsValid() {
        return Validate.pathIsValid(INPUT_FILE_PATH);
    }

    public static String getDirectoryFromPath(String path) {
        int index = path.lastIndexOf("\\");
        if (index < 0) {
            index = path.lastIndexOf("/");
        }
        return path.substring(0, index + 1);
    }

    public static String takeFirstRow() {
        if (LINES_FROM_INPUT_FILE == null || LINES_FROM_INPUT_FILE.size() < 1) {
            return null;
        }
        String firstRow = LINES_FROM_INPUT_FILE.get(0);
        LINES_FROM_INPUT_FILE.remove(0);
        return firstRow;
    }

    public static boolean addRowsToTable() {
        if (LINES_FROM_INPUT_FILE == null || LINES_FROM_INPUT_FILE.size() == 0) {
            return false;
        }
        for (String row : LINES_FROM_INPUT_FILE) {
            Table.addRow(row);
        }
        return true;
    }

    public static boolean getLinesFromFile() {
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = new FileInputStream(INPUT_FILE_PATH);
            reader = new InputStreamReader(inputStream, Charsets.ISO_8859_1);
            bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            while (line != null && line.length() > 0) {
                LINES_FROM_INPUT_FILE.add(line);
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                inputStream.close();
                reader.close();
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static void printInputFile() {
        try {

            InputStream is = new FileInputStream(INPUT_FILE_PATH);
            Reader isr = new InputStreamReader(is, Charsets.ISO_8859_1);
            BufferedReader buffReader = new BufferedReader(isr);

            // read lines from input file
            String line = buffReader.readLine();
            while (line != null && line.length() > 0) {
                System.out.println(line);
                LINES_FROM_INPUT_FILE.add(line);
                line = buffReader.readLine();

            }

            is.close();
            isr.close();
            buffReader.close();

//            write lines to new file
//            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE_DIRECTORY + "testOutput.txt");
//            OutputStreamWriter osw = new OutputStreamWriter(fos, Charsets.ISO_8859_1);
//            BufferedWriter bw = new BufferedWriter(osw);
//            for (String lineOut : LINES_FROM_FILE) {
//                System.out.println("Writing line: " + lineOut);
//                bw.write(lineOut);
//
//            }
//            bw.close();
//            fos.close();
//            osw.close();
        } catch (FileNotFoundException ex) {
//            todo bedre håndtering
            System.out.println("File not found... restart the application and supply valid file");
        } catch (IOException ex) {
            System.out.println("Something went wrong parsing the file... restart the application and supply valid file");
        }

    }

    static void writeToFile() {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        String fileNameFull = OUTPUT_FILE_DIRECTORY + "output.txt";
        
        System.out.println("writing to: " + fileNameFull);
        
        
        try {
            fos = new FileOutputStream(fileNameFull);
            osw = new OutputStreamWriter(fos, Charsets.ISO_8859_1);
            bw = new BufferedWriter(osw);
            for (String lineOut : OutputFormat.getOutputLines()) {
                if(Globals.DEVELOPER_MODE == 1){
                    System.out.println("Writing line: " + lineOut);
                }
                bw.write(lineOut + System.lineSeparator());
                

            }
            System.out.println("DONE");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileMan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
