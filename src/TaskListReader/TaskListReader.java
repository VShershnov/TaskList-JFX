/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaskListReader;

import java.io.*;
import java.util.*;
import javax.management.ListenerNotFoundException;

/**
 *
 * @author vshershnov
 */

class Task {
    private String name;
    private String pID;
    private int memory;
    
    public Task(String name, String pID, int memory ){
        this.name = name;
        this.pID = pID;
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Task{" + "name=" + name + ", pID=" + pID + ", memory=" + memory + '}';
    }
    
    
}

//class dump TaskList from tesklist.exe 
class TaskBundle {
    List<String> strListRunningTasks = new ArrayList<String>();
    List<Task> ListRunningTasks = new ArrayList<Task>();
    BufferedReader input;
    
    //dump TaskList from tesklist.exe to csv String List
    public TaskBundle() {
        try {
          String line;
          Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
          input = new BufferedReader
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
    }
    
    public List<String> getStrListRunningTasks() {
       return strListRunningTasks;
    }
    
    //return list of objects Task (convert String List to Object List)
    public List<Task> getListRunningTasks() {
     
            Iterator<String> it = strListRunningTasks.iterator();
            while (it.hasNext()) {
                // use comma as separator
                String[] taskParametr = it.next().split("\",\"");
                int len = taskParametr[4].length();// test func
                String memory = taskParametr[4].replaceAll("[-+.^:,]","");
                int memoryLength = memory.length();
                memory = memory.substring(0,memoryLength-3);                
                int first = Integer.parseInt(memory); //test Integer func
                Task t = new Task(taskParametr[0],taskParametr[1], Integer.parseInt(memory));
                ListRunningTasks.add(t);
            }
            
            return ListRunningTasks;
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
    System.out.println(taskLists.getStrListRunningTasks().toString());
    
    Locale locale = Locale.getDefault();
    System.out.println("Default Locale: "+locale);
    
    List<Task> tsk = new ArrayList<Task>();
    tsk = taskLists.getListRunningTasks();
    Iterator<Task> it = tsk.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
       }
  
    }
}