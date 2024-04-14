package com.example.trytosmth;
import com.example.trytosmth.model.FireExtinguisherData;
import com.example.trytosmth.service.FireExtinguisherService;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import com.example.trytosmth.dao.exception.DbException;
import com.example.trytosmth.dao.FireExtinguisherDao;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Paths;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private ObservableList<FireExtinguisherData> fireExtinguishers = FXCollections.observableArrayList();

    final ObservableList<String> expiredFireExtinguishers = FXCollections.observableArrayList();


    private final FireExtinguisherService fireExtinguisherService = new FireExtinguisherService();
    final TextArea resultTextArea = new TextArea();
    TableView<FireExtinguisherData> tableView; // Поле для TableView



    @Override
    public void start(Stage stage) {
        // Створення панелі вкладок
        TabPane tabPane = new TabPane();
        // Створення вкладки "Info"
        Tab infoTab = new Tab("Info");

        // Встановлення текстового вмісту для вкладки "Info"
        Label infoLabel = new Label("Курсова робота з ІПЗ\nВиконав студент 2-го курсу з групи ІО-24\nСевастьянов Максим");
        infoLabel.setStyle("-fx-font-size: 16px;"); // Задання розміру шрифту
        infoLabel.setPadding(new Insets(20)); // Задання відступу

        // Створення контейнера для вмісту вкладки "Info"
        VBox infoContent = new VBox(infoLabel);
        infoContent.setAlignment(Pos.CENTER); // Центрування вмісту

        // Встановлення вмісту вкладки "Info"
        infoTab.setContent(infoContent);


        // Створення вкладки "Menu"
        Tab menuTab = new Tab("Menu");
        menuTab.setContent(createMenuContent());

        // Створення вкладки "List"
        Tab listTab = new Tab("List");
        listTab.setContent(createListContent());

        // Додавання вкладок до панелі
        tabPane.getTabs().addAll(infoTab, menuTab, listTab);

        // Створення основної панелі
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        // Створення сцени та відображення вікна
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(Paths.get("src/styles.css").toUri().toString());
        stage.setScene(scene);
        stage.setTitle("Перевірка справності вогнегасників");
        stage.show();
    }

    private Node createMenuContent() {
        // Створення панелі з кнопками
        VBox menuPane = new VBox(10);
        menuPane.setPadding(new Insets(10));

        // Додавання кнопок та результуючого поля
        menuPane.getChildren().addAll(checkButton, replaceButton, addButton, resultTextArea);

        // Налаштування обробників подій кнопок
        addButton.setOnAction(event -> createAddFireExtinguisherWindow());
        checkButton.setOnAction(event -> checkExpirationDate());
        replaceButton.setOnAction(event -> replaceExpiredFireExtinguishers());

        return menuPane;
    }

    private BorderPane createListContent() {

        // Створення таблиці
        tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(fireExtinguisherService.getAll()));

        // Створення стовпців
        TableColumn<FireExtinguisherData, String> locationColumn = new TableColumn<>("Місцезнаходження");
        locationColumn.setCellValueFactory(new PropertyValueFactory<FireExtinguisherData, String>("location"));
        locationColumn.setPrefWidth(200);

        TableColumn<FireExtinguisherData, String> expirationDateColumn = new TableColumn<>("Термін придатності");
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        expirationDateColumn.setPrefWidth(150);

        // Створення стовпця для кнопки видалення
        TableColumn<FireExtinguisherData, Void> deleteColumn = new TableColumn<>("Дії");
        deleteColumn.setPrefWidth(100);
        Callback<TableColumn<FireExtinguisherData, Void>, TableCell<FireExtinguisherData, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<FireExtinguisherData, Void> call(final TableColumn<FireExtinguisherData, Void> param) {
                final TableCell<FireExtinguisherData, Void> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Видалити");

                    {
                        deleteButton.setOnAction((event) -> {
                            FireExtinguisherData extinguisher = getTableView().getItems().get(getIndex());
                            try {
                                List<FireExtinguisherData> updatedList = fireExtinguisherService.delete(extinguisher); // Видалення вогнегасника з бази даних
                                fireExtinguishers.clear();
                                fireExtinguishers.addAll(updatedList);
                                tableView.setItems(FXCollections.observableArrayList(updatedList)); // Оновлення таблиці
                                tableView.refresh();
                            } catch (DbException e) {
                                e.printStackTrace();
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Помилка");
                                alert.setHeaderText(null);
                                alert.setContentText("Помилка при видаленні вогнегасника з бази даних.");
                                alert.showAndWait();
                            }
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };
        deleteColumn.setCellFactory(cellFactory);

        tableView.getColumns().addAll(locationColumn, expirationDateColumn, deleteColumn);

        // Додавання таблиці до панелі
        BorderPane listPane = new BorderPane();
        listPane.setCenter(tableView);

        return listPane;
    }


    private void checkExpirationDate() {
        // Очищення попереднього результату
        expiredFireExtinguishers.clear();

        // Перевірка терміну придатності
        for (FireExtinguisherData extinguisher : fireExtinguisherService.getAll()) {
            if (isExpired(extinguisher.getExpirationDate())) {
                expiredFireExtinguishers.add(extinguisher.getLocation());
            }
        }

        // Оновлення тексту у TextArea
        if (!expiredFireExtinguishers.isEmpty()) {
            resultTextArea.setText("Прострочені вогнегасники: \n" + String.join("\n", expiredFireExtinguishers));
        } else {
            resultTextArea.setText("Прострочених вогнегасників немає.");
        }
    }




    private boolean isExpired(LocalDate expirationDate) {
        // Перевірка чи прострочений термін придатності
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(expirationDate);
    }

    private void replaceExpiredFireExtinguishers() {
        // Заміна прострочених вогнегасників та оновлення даних у таблиці
        List<FireExtinguisherData> updatedList = new ArrayList<>();

        for (FireExtinguisherData extinguisher : fireExtinguisherService.getAll()) {
            if (isExpired(extinguisher.getExpirationDate())) {
                extinguisher.setExpirationDate(LocalDate.now().plusYears(1));
                fireExtinguisherService.update(extinguisher);
            }
            updatedList.add(extinguisher); // Додаємо оновлені дані до списку
        }

        // Оновлюємо список fireExtinguishers
        fireExtinguishers.clear();
        fireExtinguishers.addAll(updatedList);

        // Оновлення тексту у TextArea
        resultTextArea.setText("Вогнегасники замінені.");

        // Оновлення таблиці
        if (tableView != null) {
            tableView.setItems(FXCollections.observableArrayList(updatedList));
        }
    }

    private void createAddFireExtinguisherWindow() {
        Stage stage = new Stage();
        stage.setTitle("Додати вогнегасник");

        // Створення полів для введення даних
        TextField locationField = new TextField();
        DatePicker expirationDatePicker = new DatePicker();

        // Створення кнопки "Ок"
        Button addButton = new Button("Додати");
        addButton.setOnAction(event -> {
            // Отримання даних з полів
            String location = locationField.getText();
            LocalDate expirationDate = expirationDatePicker.getValue();

            // Валідація введених даних
            if (location.isEmpty() || expirationDate == null) {
                // Повідомлення про помилку
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть всі поля.");
                alert.showAndWait();
            } else {
                // Створення нового вогнегасника та додавання його до бази даних
                FireExtinguisherData newExtinguisher = new FireExtinguisherData(location, expirationDate);
                try {
                    fireExtinguisherService.insert(newExtinguisher);

                    // Оновлення таблиці
                    fireExtinguishers.add(newExtinguisher);
                    tableView.setItems(FXCollections.observableArrayList(fireExtinguisherService.getAll()));
                    tableView.refresh();

                    // Закриття вікна
                    stage.close();
                } catch (DbException e) {
                    // Обробка помилки бази даних
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка бази даних");
                    alert.setHeaderText(null);
                    alert.setContentText("Помилка при додаванні вогнегасника.");
                    alert.showAndWait();
                }
            }
        });

        // Створення макету та встановлення його для вікна
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Місцезнаходження:"),
                locationField,
                new Label("Термін придатності:"),
                expirationDatePicker,
                addButton
        );
        layout.setPadding(new Insets(10));
        stage.setScene(new Scene(layout));
        stage.show();
    }
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}