/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tasklist.MainApp;
import tasklist.model.Task;
/**
 *
 * @author vshershnyov
 */
public class TaskOverviewController {
    
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> pIDColumn;
    @FXML
    private TableColumn<Task, Integer> memoryColumn;
    
    @FXML
    private Label tasksLabel;
    
    
    @FXML
    private TableView<Task> taskFormFileTable;
    @FXML
    private TableColumn<Task, String> nameColumn1;
    @FXML
    private TableColumn<Task, String> diffColumn;
    @FXML
    private TableColumn<Task, Integer> memoryColumn1;

    @FXML
    private Label tasksFromFileLabel;
    
    
    // Link to MainApp.
    private MainApp mainApp;

    /**
     * Constructor.
     * Called before method initialize().
     */
    public TaskOverviewController() {
    }
    
    /**
     * Init class-controller. Called automatically
     * after fxml-file loaded .
     */
    @FXML
    private void initialize() {
        // Init TasksList Table with 3 column.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        pIDColumn.setCellValueFactory(cellData -> cellData.getValue().pIDProperty());
        memoryColumn.setCellValueFactory(cellData -> cellData.getValue().memoryProperty().asObject());
        
        // Init Tasks loaded from XML with 3 column.
        nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        diffColumn.setCellValueFactory(cellData -> cellData.getValue().pIDProperty());
        memoryColumn1.setCellValueFactory(cellData -> cellData.getValue().memoryProperty().asObject());
    }
    
    /**
     * Called by MainApp and give link to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    
    // Add Tasks data to Table from ObservedList
        taskTable.setItems(mainApp.getTaskData());
        taskFormFileTable.setItems(mainApp.getTaskDataLoad());
        
    }
        
}