/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.model;

import java.util.Objects;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author vshershnyov
 */

/**
 * Class-model for Task.
 * 
 */

@XmlType(propOrder={"name", "memory"})
public class Task {
    
    private final StringProperty name;
    private final StringProperty pID;
    private final IntegerProperty memory;
    

    /**
     * default constructor
     */
    public Task() {
        this(null, null);
    }
    
    /**
     * constructor with initial data
     * also used by unmarshaller
     * 
     * @param name
     * @param memory
     */
    public Task(String name, String memory) {
        this.name = new SimpleStringProperty(name);
        this.pID = new SimpleStringProperty();
        
        //if constructor used by unmarshaller memory param is null and can't be convert by Integer.valueOf
        //at that case set memory empty value  
        if (memory != null) {
             this.memory = new SimpleIntegerProperty(Integer.valueOf(memory));
        }
        else this.memory = new SimpleIntegerProperty();
    }
    
    public Task(String name, int memory) {
        this.name = new SimpleStringProperty(name);
        this.memory = new SimpleIntegerProperty(memory);
        this.pID = new SimpleStringProperty();
    }
    
    public Task(String name, String pID, Integer memory ){
        this.name = new SimpleStringProperty(name);
        this.pID = new SimpleStringProperty(pID);
        this.memory = new SimpleIntegerProperty(memory);
    }
    
    @Override
    public String toString() {
        return "Task{" + "name=" + name + ", pID=" + pID + ", memory=" + memory + '}';
    }
    
    //Override method for compare Tasks obj by name field
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name.get());
        return hash;
    }
    
    //Override method for compare Tasks obj by name field
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
        if (!Objects.equals(this.name.get(), other.name.get())) {
            return false;
        }
        return true;
    }
    
    
    public StringProperty nameProperty() {
        return name;
    }
    
    //XML annotation
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
    
    //XML annotation
    @XmlElement (name = "memory")
    public Integer getMemory() {
        return memory.get();
    }
    
    public void setMemory(Integer memory) {
        this.memory.set(Integer.valueOf(memory));
    }
    
         
}
