//package com.example.trytosmth.action;
//
//import com.example.trytosmth.dao.exception.DbException;
//import com.example.trytosmth.model.FireExtinguisherData;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Alert;
//
//import java.util.List;
//
//public class DeleteFireExtinguisherAction implements EventHandler<ActionEvent> {
//
//    @Override
//    public void handle(ActionEvent actionEvent) {
//        FireExtinguisherData extinguisher = getTableView().getItems().get(getIndex());
//        try {
//            List<FireExtinguisherData> updatedList = fireExtinguisherService.delete(extinguisher); // Видалення вогнегасника з бази даних
//            fireExtinguishers.clear();
//            fireExtinguishers.addAll(updatedList);
//            tableView.setItems(FXCollections.observableArrayList(updatedList)); // Оновлення таблиці
//            tableView.refresh();
//        } catch (DbException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Помилка");
//            alert.setHeaderText(null);
//            alert.setContentText("Помилка при видаленні вогнегасника з бази даних.");
//            alert.showAndWait();
//        }
//    }
//}
