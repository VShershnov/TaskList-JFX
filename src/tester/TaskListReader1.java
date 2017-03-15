/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;


import java.io.*;
import java.util.*;
import javax.management.ListenerNotFoundException;


/**
 *
 * @author vshershnov
 */

class TasksList {
    List<Task1> tasksList = new ArrayList<Task1>();
    
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

            Task1 t = new Task1(taskSplit[0],taskSplit[1], Integer.parseInt(memory));
            tasksList.add(t);
        }    
            
    }

    public List<Task1> getTasksList() {
        return tasksList;
    }
    
    public List<Task1> getGroupedByMem (){
        List<Task1> groupedTasksList = new ArrayList<Task1>();
        
        Iterator<Task1> it1 = tasksList.iterator();
        
        while (it1.hasNext()) {
            Task1 tsk1 = it1.next();
            if (groupedTasksList.contains(tsk1)){
                Iterator<Task1> it2 = groupedTasksList.iterator();
                while (it2.hasNext()) {
                    Task1 tsk2 = it2.next();
                    if (tsk2.equals(tsk1)){
                        tsk2.setMemory(tsk1.getMemory()+tsk2.getMemory());
                        break;
                    }
                }
            }
            else {
                groupedTasksList.add(tsk1);
                System.out.println(tsk1.toString() + " --------added");
            }
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

public class TaskListReader1 {
    List<Task1> groupedTasksList = new ArrayList<Task1>();
    
    public TaskListReader1() {
        
        TaskBundle bundle = new TaskBundle();
        System.out.println(bundle.getTasksStrList().toString());

        TasksList tasks = new TasksList(bundle);
        groupedTasksList = tasks.getGroupedByMem();
        
        Iterator<Task1> itg = groupedTasksList.iterator();
        System.out.println("-----------------------Grouped Tasks by Memory:------------------------");
            while (itg.hasNext()) {
                System.out.println(itg.next().toString());  
            }
        
        groupedTasksList.sort((o1, o2) -> o2.getMemory()-o1.getMemory());
        
        Iterator<Task1> itg2 = groupedTasksList.iterator();
        System.out.println("-----------------------Sorted Tasks by Memory:------------------------");
            while (itg2.hasNext()) {
                System.out.println(itg2.next().toString());  
            }
        
    }
    
    public List<Task1> getGroupedTaskList() {
        return this.groupedTasksList;
    }
    
} 
