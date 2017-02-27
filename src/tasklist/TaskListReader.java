/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist;

import java.io.*;
import java.util.*;

/**
 *
 * @author vshershnov
 */
public class TaskListReader {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        try {
    String line;
    Process p = Runtime.getRuntime().exec
    (System.getenv("windir") +"\\system32\\"+"tasklist.exe /fo csv /nh");
    
    BufferedReader input =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
    
    String encoding = System.getProperty("console.encoding", "utf-8");
    System.setProperty("console.encoding","utf-8");
   // PrintStream ps = new PrintStream() (System.out, encoding);
    System.out.println("Console Encoding: "+encoding.toString());
     
    Locale locale = Locale.getDefault();
    System.out.println("Default Locale: "+locale);
   
   // Getting all available locales from current instance of the JVM
 /*   Locale []availableLocale = Locale.getAvailableLocales();
    for(Locale aLocale : availableLocale){
        System.out.println("Name of Locale: "+aLocale.getDisplayName());
        System.out.println("Language Code: "+aLocale.getLanguage()+", Language Display Name: "+aLocale.getDisplayLanguage());
        System.out.println("Country Code: "+aLocale.getCountry()+", Country Display Name: "+aLocale.getDisplayCountry());
        if(!aLocale.getScript().equals("")){
            System.out.println("Script Code: "+aLocale.getScript()+", Script Display Name: "+aLocale.getDisplayScript());
        }
        if(!aLocale.getVariant().equals("")){
            System.out.println("Variant Code: "+aLocale.getVariant()+", Variant Display Name: "+aLocale.getDisplayVariant());
        }
        System.out.println("****************************************************************");
    }

  */ 
   
  
    while ((line = input.readLine()) != null) {
        System.out.println(line); //<-- Parse data here.
    }
    input.close();
} catch (Exception err) {
    err.printStackTrace();
}
        
   // Getting a default locale object
   Locale locale = Locale.getDefault();
   System.out.println("Default Locale: "+locale);

    }
    
}
}
