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
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 * 
 * @author vshersnov
 */
@XmlRootElement(name = "tasks")
public class TaskListWrapper {
    private List tasks;

   @XmlElements({ @XmlElement(name = "task", type = Task.class) })
   //@XmlElement(name = "task", type = Task.class)
    public List getTasks() {
        return tasks;
    }

    public void setTasks(List tasks) {
        this.tasks = tasks;
    }
}
