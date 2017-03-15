/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;


import java.io.File;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.fxml.FXMLLoader;
import javafx.collections.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author vshershnov
 */
public class MainApp1 {
    
    /**
     * Данные, в виде наблюдаемого списка tasks.
     */
    private ObservableList<Task1> taskData = FXCollections.observableArrayList();
    private ObservableList<Task1> taskDataLoad = FXCollections.observableArrayList();
    
    /**
     * Конструктор
     */
    public MainApp1() {
        // В качестве образца добавляем некоторые данные
        TaskListReader1 tskRdr = new TaskListReader1();
        List<Task1> taskList = tskRdr.getGroupedTaskList();
        Iterator<Task1> itg = taskList.iterator();
        while (itg.hasNext()) {
                taskData.add(itg.next());  
            }
    }
    
    void ObservableListToString(List taskData){
        Iterator<Task1> itg = taskData.iterator();
            while (itg.hasNext()) {
                System.out.println(itg.next().toString());  
            }
    }
    
    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Task1> getTaskData() {
        return taskData;
    }
    
    public ObservableList<Task1> getTaskDataLoad() {
        return taskDataLoad;
    }

    
    /**
    * Загружает информацию об адресатах из указанного файла.
    * Текущая информация об адресатах будет заменена.
    * 
    * @param file
    */
    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper1.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            TaskListWrapper1 wrapper = (TaskListWrapper1) um.unmarshal(file);

            taskDataLoad.clear();
            taskDataLoad.addAll(wrapper.getTasks());
            
            System.out.println("-----------------------TASKS FROM XML:------------------------");
            ObservableListToString (taskDataLoad);
            
            
            

        } catch (Exception e) { // catches ANY exception
            System.out.println("Could not load data from file: " + file.getPath());

        }
    }
    
    void compareTasksListToXMLFile (){
        List<Task1> compariedTasksList = new ArrayList<Task1>();
        
        //Опеределяем задачи отсутствовавшие текущем списке задач taskData
        List<Task1> tasksDiffNameList = new ArrayList<Task1>(taskDataLoad);
        tasksDiffNameList.removeAll(taskData);
        System.out.println("\n-----------------------TASKS DIFFER FROM TASKLIST:------------------------");
        ObservableListToString(tasksDiffNameList);
        
        //Перебираем текущий список задач
        Iterator<Task1> itTD = taskData.iterator();
        while (itTD.hasNext()) {
            Task1 tsk1 = itTD.next();
            
            //если взятая задача из текущего списка существует в XML списке
            //в список сравнительного списка задач добвляем ее с разницей памяти в поле pID
            if (taskDataLoad.contains(tsk1)){
                Iterator<Task1> itTDL = taskDataLoad.iterator();
                while (itTDL.hasNext()) {
                    Task1 tsk2 = itTDL.next();
                    
                    //при совпадении с именем задачи из XML списка условие соответвия
                    //в список сравнительного списка задач добвляем ее с разницей памяти в поле pID
                    if (tsk2.equals(tsk1)){
                        compariedTasksList.add(new Task1(tsk2.getName(),Integer.toString(tsk1.getMemory()-tsk2.getMemory()), tsk2.getMemory()));
                        break;
                    }
                }
            }
            
            //если взятая задача из текущего списка jncendetn в XML списке
            //добавляем пустую строку с занчением памяти в поле pID
            else {
                compariedTasksList.add(new Task1(null,Integer.toString(tsk1.getMemory())));
                System.out.println(Integer.toString(tsk1.getMemory()) + "---------------add empty string ");
            }
        }
        
        //в сравнительный список задач
        //добавляем задачи отсутствовавшие в текущем списке задач taskData
        System.out.println("\n ---------------add tasksDiffNameList to  compariedTasksList ");
        Iterator<Task1> it = tasksDiffNameList.iterator();
        while (it.hasNext()) {
            Task1 tsk2 = it.next();
            compariedTasksList.add(new Task1(tsk2.getName(),Integer.toString(-(tsk2.getMemory()))));
            System.out.println(Integer.toString(-tsk2.getMemory()) + "---------------add empty string ");
        }
        
        //Передаем XML списку сравнительный список задач
        System.out.println("\n ---------------CLEAR and ADD compariedTasksList to taskDataLoad");
        taskDataLoad.clear();
        taskDataLoad.addAll(compariedTasksList);
        System.out.println("-----------------------TASKS FROM XML:------------------------\n");
        ObservableListToString(taskDataLoad);
    }
    
    void TaskDataListCompare (List<Task1> taskData, List<Task1> taskDataLoad){
        
    }
    

    /**
    * Сохраняет текущую информацию об адресатах в указанном файле.
    * 
    * @param file
    */
    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper1.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
            // Обёртываем наши данные об адресатах.
            TaskListWrapper1 wrapper = new TaskListWrapper1();
            wrapper.setTasks(taskData);
            
            
            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, System.out);
            m.marshal(wrapper, file);
        
        } catch (JAXBException e) { // catches ANY exception
            
            System.out.println("tester.MainApp1.saveTaskDataToFile()\n" +"JAXBException.....Could not save data to file:\n");

        }
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       MainApp1 MainApp = new MainApp1();
       File file = new File("tasks.xml");
       MainApp.saveTaskDataToFile(file);
       File file2 = new File("tasks3.xml");
       MainApp.loadTaskDataFromFile(file2);
       MainApp.compareTasksListToXMLFile();
       
       
    }
    
}
