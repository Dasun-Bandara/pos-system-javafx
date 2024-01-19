package lk.ijse.dep11.pos.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.dep11.pos.AppInitializer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController {
    @FXML
    public AnchorPane root;
    @FXML
    public ImageView imgCustomer;
    @FXML
    public ImageView imgItem;
    @FXML
    public ImageView imgOrder;
    @FXML
    public ImageView imgViewOrders;
    @FXML
    public Label lblMenu;
    @FXML
    public Label lblDescription;

    public void initialize() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    public void navigate(MouseEvent e) throws IOException {
       if(e.getSource() instanceof ImageView){
           ImageView icon = (ImageView) e.getSource();
           Parent root = null;
           switch (icon.getId()){
               case "imgCustomer":
                   root = FXMLLoader.load(this.getClass().getResource("/view/ManageCustomerForm.fxml"));
                   break;
               case "imgItem":
                   root = FXMLLoader.load(this.getClass().getResource("/view/ManageItemForm.fxml"));
                   break;
               case "imgOrder":
                   root = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));
                   break;
               case "imgViewOrders":
                   root = FXMLLoader.load(this.getClass().getResource("/view/SearchOrdersForm.fxml"));
                   break;
           }
           if (root != null) {
               Scene subScene = new Scene(root);
               Stage primaryStage = (Stage) this.root.getScene().getWindow();
               primaryStage.setResizable(true);
               primaryStage.setScene(subScene);
               primaryStage.sizeToScene();
               primaryStage.centerOnScreen();
               primaryStage.setOnCloseRequest(Event::consume);

               TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
               tt.setFromX(-subScene.getWidth());
               tt.setToX(0);
               tt.play();

               Platform.runLater(()-> primaryStage.setResizable(false));
           }
       }
    }

    @FXML
    public void playMouseEnterAnimation(MouseEvent e) {
        if (e.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) e.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Orders");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgViewOrders":
                    lblMenu.setText("Search Orders");
                    lblDescription.setText("Click if you want to search orders");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    public void playMouseExitAnimation(MouseEvent e) {
        if (e.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) e.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    public static void navigateToMain(Node rootNode) throws IOException {
        Parent root = FXMLLoader.load(AppInitializer.class.getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (rootNode.getScene().getWindow());
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(null);
        Platform.runLater(()-> primaryStage.setResizable(false));
    }
}
