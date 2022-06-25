import java.util.*;
import edu.duke.*;

/**
 * Write a description of tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tester {
    public void sliceString(){
        VigenereBreaker tester = new VigenereBreaker();
        //System.out.println(tester.sliceString("abcdefghijklm", 1, 3));
        //FileResource file = new FileResource("athens_keyflute.txt");
        //String t = file.asString();
        //int[] tryKeyLength = tester.tryKeyLength(t, 5, 'e');
        //System.out.println(tryKeyLength);
        tester.breakVigenere ();
    }
}
