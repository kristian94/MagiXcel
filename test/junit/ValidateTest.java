/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junit;

import magixcel.FileMan;
import magixcel.Validate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristian Nielsen
 */
public class ValidateTest {

    public ValidateTest() {

    }

//    Skal flyttes?
    @Test
    public void testGetDirectoryFromPath() {

        String pathIn = "C:\\Program Files\\Microsoft Silverlight";
        String pathOutExpected = "C:\\Program Files\\";
        String pathOutActual = FileMan.getDirectoryFromPath(pathIn);
        
        assertEquals(pathOutExpected, pathOutActual);
        
        pathIn = "C:/Program Files/Microsoft Silverlight";
        pathOutExpected = "C:/Program Files/";
        pathOutActual = FileMan.getDirectoryFromPath(pathIn);
        
        assertEquals(pathOutExpected, pathOutActual);
    }
    
    @Test
    public void testPathIsValid(){
        String validPathA = "C:\\Program Files\\Microsoft Silverlight\\xapauthenticodesip.csv";
        String validPathB = "C:/Program Files/Microsoft Silverlight/xapauthenticodesip.csv";
        String validPathC = "C:/Hello.txt";
        String validPathD = "C:\\Users\\Kristian Nielsen\\Desktop\\MagiXcel mock\\Sprog Forbruger Test 02.txt";
        String invalidPathA = "Hello";
        String invalidPathB = "";
        String invalidPathC = null;
        String invalidPathD = "C:";
        String invalidPathE = ":/Program Files/Microsoft Silverlight";
        String invalidPathF = "C:\\Program Files\\Microsoft Silverlight\\xapauthenticodesip";
        String invalidPathG = "C:\\Program Files\\Microsoft Silverlight\\xapauthenticodesip.dll";
        
        assertTrue(Validate.pathIsValid(validPathA));
        assertTrue(Validate.pathIsValid(validPathB));
        assertTrue(Validate.pathIsValid(validPathC));
        assertTrue(Validate.pathIsValid(validPathD));
        assertFalse(Validate.pathIsValid(invalidPathA));
        assertFalse(Validate.pathIsValid(invalidPathB));
        assertFalse(Validate.pathIsValid(invalidPathC));
        assertFalse(Validate.pathIsValid(invalidPathD));
        assertFalse(Validate.pathIsValid(invalidPathE));
        assertFalse(Validate.pathIsValid(invalidPathF));
        assertFalse(Validate.pathIsValid(invalidPathG));
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
