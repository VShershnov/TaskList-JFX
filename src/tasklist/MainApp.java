/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasklist;

import tasklist.model.*;
import tasklist.view.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 *
 * @author vshershnov
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * Данные, в виде наблюдаемого списка tasks.
     */
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    
    /**
     * Конструктор
     */
    public MainApp() {
        // В качестве образца добавляем некоторые данные
        taskData.add(new Task("Hans", "1"));
        taskData.add(new Task("Ruth", "2"));
        taskData.add(new Task("Heinz", "3"));
        taskData.add(new Task("Cornelia", "4"));
        
    }
    
    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }

     @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TaskListApp");

        initRootLayout();

        showTaskOverview();
    }
    
    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
    public void showTaskOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TaskOverview.fxml"));
            AnchorPane taskListOverview = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(taskListOverview);
            
            // Даём контроллеру доступ к главному приложению.
            TaskOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает главную сцену.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
