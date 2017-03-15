/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.Objects;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author vshershnyov
 */

/**
 * Класс-модель для адресата (Task).
 * 
 */


@XmlType(propOrder={"name", "memory"})
//@XmlRootElement(name = "task1")
public class Task1 {
    
    private  StringProperty name;
    private  StringProperty pID;
    private  IntegerProperty memory;
    

    /**
     * Конструктор по умолчанию.
     */
    
    public Task1() {
        this(null, null);
    }
    
    /**
     * Конструктор с некоторыми начальными данными.
     * 
     * @param name
     * @param memory
     */
    
    public Task1(String name, String memory) {
        this.name = new SimpleStringProperty(name);
        
        this.pID = new SimpleStringProperty();
        this.pID = new SimpleStringProperty(memory);
        // Какие-то фиктивные начальные данные для удобства тестирования.
        if (memory != null) {
            this.memory = new SimpleIntegerProperty(Integer.valueOf(memory));
        }
        else this.memory = new SimpleIntegerProperty();
        
        //this.memory = new SimpleIntegerProperty(99354);
    }

    public Task1(String name, int memory) {
        this.name = new SimpleStringProperty(name);
        
        this.pID = new SimpleStringProperty();
        //this.pID = new SimpleStringProperty(memory);
        // Какие-то фиктивные начальные данные для удобства тестирования.
        
        this.memory = new SimpleIntegerProperty(memory);
        //this.memory = new SimpleIntegerProperty(99354);
    }
    
    public Task1(String name, String pID, Integer memory ){
        this.name = new SimpleStringProperty(name);
        this.pID = new SimpleStringProperty(pID);
        this.memory = new SimpleIntegerProperty(memory);
    }
    
    
    @Override
    public String toString() {
        return "Task{" + "name=" + name + ", pID=" + pID + ", memory=" + memory + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name.get());
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
        final Task1 other = (Task1) obj;
        if (!Objects.equals(this.name.get(), other.name.get())) {
            return false;
        }
        return true;
    }
    
    
    
    public StringProperty nameProperty() {
        return name;
    }

    @XmlElement (name = "name")
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty pIDProperty() {
        return pID;
    }

    @XmlTransient
    public String getPID() {
        return pID.get();
    }
    
    public void setPID(String pID) {
        this.pID.set(pID);
    }
    
    
    public IntegerProperty memoryProperty() {
        return memory;
    }

    @XmlElement (name = "memory")
    public Integer getMemory() {
        return memory.get();
    }
    
    
    
    public void setMemory(Integer memory) {
        this.memory.set(memory);
    }
    
    /*
    public void setMemory(String memory) {
        System.out.println(memory+" -----------------------set String Memory------------------------");
        this.memory.set(Integer.valueOf(memory));
    }
     */
}
