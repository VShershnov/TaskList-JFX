/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.view;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import tasklist.MainApp;

/**
 * Controller for Root Layout. 
 * Root Layout is a base App Layout, contain Menu string 
 * and place for other JavaFX elements.
 * 
 * @author vshershnov
 */
public class RootLayoutController {
    // link to MainApp
    private MainApp mainApp;

    /**
     * Called by MainApp for give link to itself
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Create new Task List from tasklist.exe
     */
    @FXML
    private void handleNew() {
        mainApp.getTaskData().clear();
        mainApp.initTaskData();
        if (!mainApp.getTaskDataLoad().isEmpty()){
          mainApp.compareTasksListToXMLFile();  
        }
        
    }
    
    /**
     * Open FileChooser, for user choose Tasklist file for load.
     */
    @FXML
    private void handleOpen() {
        
        mainApp.getTaskFilePath();
        
        FileChooser fileChooser = new FileChooser();

        // Set file Extension Filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show file load dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadTaskDataFromFile(file);
        }
        
        //compute diff between current TasksList and loaded file 
        mainApp.compareTasksListToXMLFile();
    }

    /**
     * Save TasksList to Excel 2007 or later (.xlsx)
     * and build chart.
     */
    @FXML
    private void handleSaveXLSX() throws IOException {
        FileChooser fileChooser = new FileChooser();

        // Set file Extension Filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "export to excel", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show Save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xlsx")) {
                file = new File(file.getPath() + ".xlsx");
            }
            
            mainApp.saveTaskDataToXlsxFile(file);
        }
    }
    
    /**
     * Save TasksList to XML file
     */
    @FXML
    private void handleSaveXML() {
        FileChooser fileChooser = new FileChooser();

        // Set file Extension Filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show Save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
        
        //Save TasksList to XML file
        mainApp.saveTaskDataToFile(file);
        }
    }
    
    /**
     * Open dialog window About.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("TaskListApp");
        alert.setHeaderText("About");
        alert.setContentText("Author:Vitalii Shershnov\n Website: https://github.com/VShershnov/TaskList-JFX");

        alert.showAndWait();
    }

    /**
     * Close App.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
