/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Auxiliary class for wrapp Tasks List.
 * ObservedList can't be marshaller to XML dirrectly
 * Used for seve Tasks List to XML
 * 
 * @author vshersnov
 */
@XmlRootElement(name = "tasks")
public class TaskListWrapper {
    private List<Task> tasks;

   @XmlElements({ @XmlElement(name = "task", type = Task.class) })
   public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
