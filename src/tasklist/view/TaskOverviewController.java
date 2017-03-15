/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tasklist.MainApp;
import tasklist.model.Task;
/**
 *
 * @author user
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
    //@FXML
    //private TableColumn<Task, String> pIDColumn1;
    @FXML
    private TableColumn<Task, Integer> memoryColumn1;

    @FXML
    private Label tasksFromFileLabel;
    
    
    //@FXML
    //private TableView<Task> compareTable;
    @FXML
    private TableColumn<Task, String> diffColumn;
    
    //@FXML
    //private Label compareLabel;
    
    
    // Ссылка на главное приложение.
    private MainApp mainApp;

    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public TaskOverviewController() {
    }
    
    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы задач с тремя столбцами.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        pIDColumn.setCellValueFactory(cellData -> cellData.getValue().pIDProperty());
        memoryColumn.setCellValueFactory(cellData -> cellData.getValue().memoryProperty().asObject());
        
        nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //pIDColumn1.setCellValueFactory(cellData -> cellData.getValue().pIDProperty());
        memoryColumn1.setCellValueFactory(cellData -> cellData.getValue().memoryProperty().asObject());
    }
    
    
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    
    // Добавление в таблицу данных из наблюдаемого списка
        taskTable.setItems(mainApp.getTaskData());
        taskFormFileTable.setItems(mainApp.getTaskDataLoad());
        
    }
        
}