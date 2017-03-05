/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.model;

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
    private final StringProperty memory;
    

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
        this.memory = new SimpleStringProperty("99663");
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
    
    
    public StringProperty memoryProperty() {
        return memory;
    }

    public int getMemory() {
        return Integer.parseInt(memory.get());
    }
    
    public void setMemory(Integer memory) {
        this.memory.set(memory.toString());
    }
      
}
