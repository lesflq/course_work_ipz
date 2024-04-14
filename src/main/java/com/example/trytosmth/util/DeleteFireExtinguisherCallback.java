//package com.example.trytosmth.util;
//
//import com.example.trytosmth.dao.exception.DbException;
//import com.example.trytosmth.model.FireExtinguisherData;
//import javafx.collections.FXCollections;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.util.Callback;
//
//import java.util.List;
//
//public class DeleteFireExtinguisherCallback implements Callback<TableColumn<FireExtinguisherData, Void>, TableCell<FireExtinguisherData, Void>> {
//
//    @Override
//    public TableCell<FireExtinguisherData, Void> call(final TableColumn<FireExtinguisherData, Void> param) {
//        final TableCell<FireExtinguisherData, Void> cell = new TableCell<>() {
//            private final Button deleteButton = new Button("Видалити");
//
//            {
//                deleteButton.setOnAction((event) -> {
//                    FireExtinguisherData extinguisher = getTableView().getItems().get(getIndex());
//                    try {
//                        List<FireExtinguisherData> updatedList = fireExtinguisherService.delete(extinguisher); // Видалення вогнегасника з бази даних
//                        fireExtinguishers.clear();
//                        fireExtinguishers.addAll(updatedList);
//                        tableView.setItems(FXCollections.observableArrayList(updatedList)); // Оновлення таблиці
//                        tableView.refresh();
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("Помилка");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Помилка при видаленні вогнегасника з бази даних.");
//                        alert.showAndWait();
//                    }
//                });
//
//            }
//
//            @Override
//            public void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(deleteButton);
//                }
//            }
//        };
//        return cell;
//    }
//}
