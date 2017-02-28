/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskListReader;

import java.io.*;
import java.util.*;

/**
 *
 * @author vshershnov
 */

class Task {
    private String name;
    private String pID;
    private int memory;
    
}

class TaskBundle {
    List<String> strListRunningTasks = new ArrayList<String>();
    List<Task> ListRunningTasks = new ArrayList<Task>();
    public List<String> getListRunningTasks() {
    
    try {
      String line;
      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
         if (!line.trim().equals("")) {
             // keep only the process name
             line = line.substring(1);
             strListRunningTasks.add(line);
         }
     }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    return strListRunningTasks;
  }
    
}

public class TaskListReader {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
    String encoding = System.getProperty("console.encoding", "utf-8");
    System.setProperty("console.encoding","utf-8");
   // PrintStream ps = new PrintStream() (System.out, encoding);
    System.out.println("Console Encoding: "+encoding.toString());
     
    TaskBundle taskLists = new TaskBundle();
    System.out.println(taskLists.getListRunningTasks().toString());
    
    Locale locale = Locale.getDefault();
    System.out.println("Default Locale: "+locale);
  
    }
}