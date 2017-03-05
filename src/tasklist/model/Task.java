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


/**
 *
 * @author vshershnyov
 */

/**
 * Класс-модель для адресата (Task).
 * 
 */
public class Task {
    
    private final StringProperty name;
    private final StringProperty pID;
    private final IntegerProperty memory;
    

    /**
     * Конструктор по умолчанию.
     */
    public Task() {
        this(null, null);
    }
    
    /**
     * Конструктор с некоторыми начальными данными.
     * 
     * @param name
     * @param pID
     */
    public Task(String name, String pID) {
        this.name = new SimpleStringProperty(name);
        this.pID = new SimpleStringProperty(pID);

        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.memory = new SimpleIntegerProperty(99663);
    }

    public Task(String name, String pID, int memory ){
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
        final Task other = (Task) obj;
        if (!Objects.equals(this.name.get(), other.name.get())) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty pIDProperty() {
        return pID;
    }

    public String getPID() {
        return pID.get();
    }
    
    public void setPID(String pID) {
        this.pID.set(pID);
    }
    
    
    public IntegerProperty memoryProperty() {
        return memory;
    }

    public int getMemory() {
        return memory.get();
    }
    
    public void setMemory(Integer memory) {
        this.memory.set(memory);
    }
      
}
