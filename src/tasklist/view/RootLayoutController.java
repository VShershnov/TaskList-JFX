/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import tasklist.MainApp;

/**
 * Контроллер для корневого макета. Корневой макет предоставляет базовый
 * макет приложения, содержащий строку меню и место, где будут размещены
 * остальные элементы JavaFX.
 * 
 * @author vshershnov
 */
public class RootLayoutController {
    // Ссылка на главное приложение
    private MainApp mainApp;

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Создаёт пустую адресную книгу.
     */
    @FXML
    private void handleNew() {
        mainApp.getTaskData().clear();
        mainApp.setTaskFilePath(null);
    }
    
    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать адресную книгу для загрузки.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadTaskDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void handleSave() {
        File taskFile = mainApp.getTaskFilePath();
        if (taskFile != null) {
            mainApp.saveTaskDataToFile(taskFile);
        } else {
            handleSaveAs();
        }
    }
    
    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveTaskDataToFile(file);
        }
    }
    
    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("TaskListApp");
        alert.setHeaderText("About");
        alert.setContentText("Author:Vitalii Shershnov\nWebsite: https://github.com/VShershnov/TaskList-JFX");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
