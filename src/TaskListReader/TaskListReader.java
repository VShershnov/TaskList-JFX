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

    public String getName() {
        return name;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
 }

class TasksList {
    List<Task> tasksList = new ArrayList<Task>();
    
    // Constructor. list of objects Task (convert String List to Object List)
    TasksList (TaskBundle tsBd){
        List<String> tasksStrList = tsBd.getTasksStrList();
        
        //choose memory unit depends of Locale:
        int unitLength;
        Locale locale = Locale.getDefault();
            if (locale.getLanguage()=="ru"){
                unitLength = 0;
            } else unitLength = 1;
        
        Iterator<String> it = tasksStrList.iterator();
        while (it.hasNext()) {
            // use comma as separator
            String[] taskSplit = it.next().split("\",\"");
                
            //remove specific symbol
            //  "[^\\w\\s]",""      "[-+.^:,]",""
            String memory = taskSplit[4].replaceAll("[^\\w\\s]","");
                
            //get new Length after specific symbol remove
            int memoryLength = memory.length();
                
            //cut unit from memory String 
            memory = memory.substring(0,memoryLength-(unitLength+1));

            Task t = new Task(taskSplit[0],taskSplit[1], Integer.parseInt(memory));
            tasksList.add(t);
        }    
            
    }

    public List<Task> getTasksList() {
        return tasksList;
    }
    
    public List<Task> getGroupedByMem (){
        List<Task> groupedTasksList = new ArrayList<Task>();
        
        Iterator<Task> it1 = tasksList.iterator();
        Iterator<Task> it2 = groupedTasksList.iterator();
        while (it1.hasNext()) {
            Task tsk1 = it1.next();
            if (groupedTasksList.contains(tsk1)){
                while (it2.hasNext()) {
                    Task tsk2 = it2.next();
                    if (tsk2.equals(tsk1)){
                        tsk2.setMemory(tsk1.getMemory()+tsk2.getMemory());
                    }
                }
            }
            else groupedTasksList.add(tsk1);
        }
    return groupedTasksList;
    }
}

//class dump TaskList from tesklist.exe 
class TaskBundle {
    List<String> tasksStrList = new ArrayList<String>();
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
                 tasksStrList.add(line);
             }
         }
          input.close();
        }
        catch (Exception err) {
          err.printStackTrace();
        }
    }
  
    public List<String> getTasksStrList() {
       return tasksStrList;
    }
    
}

public class TaskListReader {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
    TaskBundle bundle = new TaskBundle();
    System.out.println(bundle.getTasksStrList().toString());
    
    TasksList tasks = new TasksList(bundle);
    
    Iterator<Task> it = tasks.getTasksList().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
       }
    
    System.out.println("-----------Grouped Tasks by Memory:------------");    
    Iterator<Task> itg = tasks.getGroupedByMem().iterator();
        while (itg.hasNext()) {
            System.out.println(it.next().toString());  
        
        }
    }
}