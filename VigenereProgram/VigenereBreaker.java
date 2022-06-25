import java.util.*;
import edu.duke.*;
import java.io.*;
public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sliced = new StringBuilder();
        for(int i = whichSlice; i < message.length(); i += totalSlices){
            char curr = message.charAt(i);
            sliced.append(curr);
        }
        String slice = sliced.toString();
        return slice;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //slice

        for(int i = 0; i < klength; i ++){
            //use ceasar decript and add to key
            String slice = sliceString(encrypted, i, klength);
            CaesarCracker crack = new CaesarCracker(mostCommon);
            int currKey = crack.getKey(slice);
            key[i] = currKey;
        }
        return key;
    }
    
    public HashSet <String> readDictionary(FileResource fr){
        HashSet<String> set = new HashSet<String>();
        for (String line : fr.lines()) {
            String word = line.toLowerCase();
            set.add(word);
        }
        return set;
    }
    
    public int countWords(String message, HashSet <String> dictionary){
        int count = 0;
        String [] words = message.split("\\W+");
        for(int i = 0; i < words.length; i++){
            if(dictionary.contains(words[i])){
             count = count + 1;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
         String decryption = "";
         int highestCount = 0;
         int keyLength = 0;
         char c = mostCommonCharIn(dictionary);
         for(int i = 1; i < 101; i++){
            int [] key = tryKeyLength(encrypted, i, c);
            VigenereCipher vc = new VigenereCipher(key);
            String decript = vc.decrypt(encrypted);
            int count = countWords(decript, dictionary);
            if(count > highestCount){
                decryption = decript;
                highestCount = count;
                keyLength = i;
            }
         }
         return decryption;
    }

    public void breakVigenere () {
        HashMap <String, HashSet <String>>  languages = new HashMap <String, HashSet <String>>();
        FileResource file = new FileResource();
        String encryptedUpperCase = file.asString();
        String encrypted = encryptedUpperCase.toLowerCase();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource dFile = new FileResource(f);
            HashSet<String> dictionary = readDictionary(dFile);
            String language = f.getName();
            languages.put( language, dictionary);
        }
        

    }
    
    public char mostCommonCharIn(HashSet <String> dictionary ){
        HashMap <Character, Integer> letterCount = new HashMap <Character, Integer> ();
        char most = '0';
        int highest = 0;
        for (String word : dictionary) {
            word = word.toLowerCase();
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                if(letterCount.containsKey(c)){
                    Integer currValue = letterCount.get(c) + 1;
                    letterCount.put(c, currValue);
                    if(currValue > highest){
                        most = c;
                        highest = currValue;
                    }
                } else {
                        letterCount.put(c, 1);
                }
            }
        }
      
        return most;
    }
    
    public void testmostCommonCharIn(){
        HashSet <String> dictionary = new HashSet <String>();
        dictionary.add("AutoA");
        dictionary.add("Peeesaa");
        System.out.println(mostCommonCharIn(dictionary));
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet <String>> languages){
        int count = 0;
        String language = "";
        String decripted = "";
        //loop over languages and get each language
        for(String lang : languages.keySet()){
            //run breake for language
            HashSet<String> dictionary = languages.get(lang);
            String message = breakForLanguage(encrypted, dictionary);
            //run count Words and save to count and language if is bigger
            int currCount = countWords(message, dictionary);
            if(currCount > count){
                count = currCount;
                language = lang;
                decripted = message;
            }
        }
        //print language and decripted
        System.out.println(decripted);
        System.out.println("language is: " + language);
        
    }
}

