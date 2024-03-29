package lk.ijse.dep11.pos.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.pos.db.OrderDataAccess;
import lk.ijse.dep11.pos.tm.Order;

import java.io.IOException;
import java.sql.SQLException;

public class SearchOrdersFormController {
    public AnchorPane root;
    public TextField txtSearch;
    public TableView<Order> tblOrders;

    public void initialize() throws IOException {
        String[] colNames = {"orderId", "orderDate", "customerId", "customerName", "orderTotal"};
        for (int i = 0; i < colNames.length; i++) {
            tblOrders.getColumns().get(i).setCellValueFactory(new PropertyValueFactory<>(colNames[i]));
        }
        try {
            tblOrders.getItems().addAll(OrderDataAccess.findOrders(""));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load orders, try later").show();
            e.printStackTrace();
            navigateToHome(null);
        }
        txtSearch.textProperty().addListener((ov, prev, cur)->{
            tblOrders.getItems().clear();
            try {
                tblOrders.getItems().addAll(OrderDataAccess.findOrders(cur));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        MainFormController.navigateToMain(root);
    }
}
