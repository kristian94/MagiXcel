/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magixcel;

/**
 *
 * @author Kristian Nielsen
 */
public class Validate {
    
    public static boolean pathIsValid(String path) {
        if (path == null) {
            return false;
        }
        int colonIndex = path.indexOf(":");
        if (colonIndex < 1) {
            return false;
        } 
        else {
            char ch = path.charAt(colonIndex - 1);
            if ((ch < 'a' && ch > 'z') || (ch < 'A' && ch > 'Z')) {
                return false;
            }
        }
        if(path.indexOf(".") < 0 || path.indexOf(".") < path.length()-4){
            return false;
        }
        if(!path.contains(".csv") && !path.contains(".txt")){
            return false;
        }

        if (path.length() < 8
                || !path.contains(":")
                || !path.contains("\\")
                && !path.contains("/")) {
            return false;
        }

        return true;
    }
}
