/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist;

import java.io.File;
import tasklist.model.*;
import tasklist.view.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author vshershnov
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //Data in view of Observable List tasks.
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private ObservableList<Task> taskDataLoad = FXCollections.observableArrayList();
    
    //Constructor, initialize tasklist.exe 
    public MainApp() {
        initTaskData();
    }
    
    
    /* 
    * Return tasklist.exe data as ObservableList of Tasks
    * @return taskData
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }
    
    /* 
    * Return data loaded from xml as ObservableList of Tasks
    * @return taskDataLoad
     */
    public ObservableList<Task> getTaskDataLoad() {
        return taskDataLoad;
    }
    
    /**
     * Initialize data from tasklist.exe to ObservableList of Tasks
     */
    public void initTaskData() {
        TaskListReader tskRdr = new TaskListReader();
        List<Task> taskList = tskRdr.getGroupedTaskList();
        for (Task taskL : taskList ){
            taskData.add(taskL);
        }
    }
    
    void ObservableListToString(List<Task> taskData){
        for (Task taskD : taskData ){
                System.out.println(taskD.toString());  
            }
    }
    
     @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TaskListApp");

        initRootLayout();
        showTaskOverview();
    }
    
    /**
     * Initialize Root Layout 
     * and try to open last opened xml file with Tasks
     */
    public void initRootLayout() {
        try {
            // Load Root Layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show scene, that containe Root Layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            // give controller permit to MainApp.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //* not testet part
        // Try to open last opened xml file with Tasks.
        File file = getTaskFilePath();
        if (file != null) {
            loadTaskDataFromFile(file);
            compareTasksListToXMLFile();
        }
        
    }

    /**
     * Show in Root Layout Tasks info 
     */
    public void showTaskOverview() {
        try {
            // Load Tasks info.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TaskOverview.fxml"));
            AnchorPane taskListOverview = (AnchorPane) loader.load();

            // Set Tasks info to center of Root Layout.
            rootLayout.setCenter(taskListOverview);
            
            // give controller permit to MainApp.
            TaskOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return Primary Stage.
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
     /**
     * Return preference Tasks file - last opened file.
     * This preference read from register, specific for current OS.
     * If preference wasn't find - return null.
     * 
     * @return
     */
    public File getTaskFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        return new File(filePath);
    }
    
    /**
 * Set path to loaded file. This [ath saved in register, specific for current OS.
 * 
 * @param file - file or null, to del path
 */
    public void setTaskFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // update Stage Title.
            primaryStage.setTitle("TaskListApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // update Stage Title.
            primaryStage.setTitle("TaskListApp");
        }
    }
    
    /**
    * Load Tasks info from file.
    * Current Tasks info would be replaced.
    * 
    * @param file
    */
    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Read XML from file and unmarshalling.
            TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);

            taskDataLoad.clear();
            taskDataLoad.addAll(wrapper.getTasks());

            // Save path to file in reg.
            setTaskFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    
    public void compareTasksListToXMLFile(){
        List<Task> compariedTasksList = new ArrayList<Task>();
         
        //Choose Tasks missing in the current TasksList taskData
        List<Task> tasksDiffNameList = new ArrayList<Task>(taskDataLoad);
        tasksDiffNameList.removeAll(taskData);

        System.out.println("\n-----------------------TASKS DIFFER FROM TASKLIST:------------------------");
        ObservableListToString(tasksDiffNameList);
          
        //iterate current TasksList
        for (Task taskD : taskData ){
             
            //if taken task from current TasksList exist in XML list
            //add it to compare list with diff of memory at pID
            if (taskDataLoad.contains(taskD)){
                for (Task taskDL : taskDataLoad ){
                
                    //if taken task name equal XML list task name 
                    //add it to compare list with diff of memory at pID field
                    if (taskDL.equals(taskD)){
                        compariedTasksList.add(new Task(taskDL.getName(),Integer.toString(taskD.getMemory()-taskDL.getMemory()), taskDL.getMemory()));
                        break;
                    }
                }
            }
             
            //if taken task from current TasksList missing in XML list
            //add empty string with memory at pID field
            else {
                Task tsk = new Task(null,Integer.toString(taskD.getMemory()),0); 
                compariedTasksList.add(tsk);
                System.out.println(tsk.toString() + " ---------------add empty string ");
            }
        }
          
        //to compare TasksLst add tasks missing in current TasksList taskData
        System.out.println("\n ---------------add tasksDiffNameList to  compariedTasksList ");
        for (Task taskDiffNL : tasksDiffNameList ){
            compariedTasksList.add(new Task(taskDiffNL.getName(),Integer.toString(-(taskDiffNL.getMemory())),taskDiffNL.getMemory() ));
            System.out.println(Integer.toString(-taskDiffNL.getMemory()) + "---------------add empty string ");
        }
          
        //send to XML list compare TasksLst
        System.out.println("\n ---------------CLEAR and ADD compariedTasksList to taskDataLoad");
        taskDataLoad.clear();
        taskDataLoad.addAll(compariedTasksList);
        System.out.println("-----------------------TASKS FROM XML:------------------------\n");
        ObservableListToString(taskDataLoad);
}
     

    
    /**
    * Save current configuration of TasksList to file.
    * 
    * @param file
    */
    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
            // Wrapp Task data.
            TaskListWrapper wrapper = new TaskListWrapper();
            wrapper.setTasks(taskData);
         
            // Marshalling and save to XML file.
            m.marshal(wrapper, System.out);
            m.marshal(wrapper, file);

            // Save path to file in reg.
            setTaskFilePath(file);
            
        } catch (JAXBException e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("JAXBException.....Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    public void saveTaskDataToXlsxFile (File file) throws IOException{
        ExcelWriterChart excelWC = new ExcelWriterChart(taskData, file);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
