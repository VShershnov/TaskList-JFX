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
import javafx.scene.layout.StackPane;
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
    
    /**
     * Данные, в виде наблюдаемого списка tasks.
     */
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private ObservableList<Task> taskDataLoad = FXCollections.observableArrayList();
    
    /**
     * Конструктор
     */
    public MainApp() {
        initTaskData();
    }
    
    /**
     * Возвращает данные в виде наблюдаемого списка задач.
     * @return
     */
    public ObservableList<Task> getTaskData() {
        return taskData;
    }
    
    public ObservableList<Task> getTaskDataLoad() {
        return taskDataLoad;
    }
    
    /**
     * Инициализирует данные в виде наблюдаемого списка задач.
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
     * Инициализирует корневой макет 
     * ????? и пытается загрузить последний открытый
     * файл с задачами.
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
            
            // Даём контроллеру доступ к главному прилодению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        /*
        // Пытается загрузить последний открытый файл с задачами.
        File file = getTaskFilePath();
        if (file != null) {
            loadTaskDataFromFile(file);
        }
        */
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
    public void showTaskOverview() {
        try {
            // Загружаем сведения о задачах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TaskOverview.fxml"));
            AnchorPane taskListOverview = (AnchorPane) loader.load();

            // Помещаем сведения о задачах в центр корневого макета.
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
     * Возвращает preference файла задач, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     * 
     * @return
     */
    public File getTaskFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    /**
 * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
 * в реестре, специфичном для конкретной операционной системы.
 * 
 * @param file - файл или null, чтобы удалить путь
 */
    public void setTaskFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            primaryStage.setTitle("TaskListApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            primaryStage.setTitle("TaskListApp");
        }
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
                    .newInstance(TaskListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);

            //taskData.clear();
            taskDataLoad.addAll(wrapper.getTasks());

            // Сохраняем путь к файлу в реестре.
            setTaskFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
    * Сохраняет текущую информацию об адресатах в указанном файле.
    * 
    * @param file
    */
    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
            // Обёртываем наши данные об адресатах.
            TaskListWrapper wrapper = new TaskListWrapper();
            wrapper.setTasks(taskData);
         
            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, System.out);
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setTaskFilePath(file);
        } catch (JAXBException e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("JAXBException.....Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
