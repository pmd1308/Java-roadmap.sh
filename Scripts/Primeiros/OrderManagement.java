// This script is a order management system implemented in JavaFX. It allows users to update the status of orders stored in a SQLite database. The application features a praphial user interface where users can input the order number and select a status from dropdown menu. Upon updating the status, the operation is logged to a CSV file along with a timestamp. The application utilizes multithreading to execute database operations assynchronously for improved responsive.

// Importing packages
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Enum for order status
enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    READY,
    DELIVERED
}

// Main class for order management
public class OrderManagement extends Application {
    private final String csvFileName = "operations_history.csv";

    // Method to update the order status in the database
    private void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Thread thread = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:orders.db");
                 PreparedStatement stmt = conn.prepareStatement("UPDATE orders " +
                                                                "SET status = ? " +
                                                                "WHERE id = ?")) {
                stmt.setString(1, newStatus.toString());
                stmt.setString(2, orderId);
                stmt.executeUpdate();
                // Append operation to CSV file
                appendToCSV(orderId, newStatus);
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    // Method to append operation to CSV file
    private void appendToCSV(String orderId, OrderStatus status) {
        try (FileWriter writer = new FileWriter(csvFileName, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.append(timestamp).append(",").append(orderId).append(",").append(status.toString()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Creating a grid pane for layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Creating labels and input fields
        Label lblOrderNumber = new Label("Order Number:");
        grid.add(lblOrderNumber, 0, 0);
        TextField txtOrderNumber = new TextField();
        grid.add(txtOrderNumber, 1, 0);

        Label lblStatus = new Label("Status:");
        grid.add(lblStatus, 0, 1);
        ComboBox<OrderStatus> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll(OrderStatus.values());
        cbStatus.setValue(OrderStatus.PENDING);
        grid.add(cbStatus, 1, 1);

        TextArea txtStatus = new TextArea();
        grid.add(txtStatus, 0, 3, 2, 1);

        // Creating a button to update the status
        Button btnUpdate = new Button("Update Status");
        grid.add(btnUpdate, 0, 2);
        btnUpdate.setOnAction(e -> {
            String orderNumber = txtOrderNumber.getText();
            OrderStatus status = cbStatus.getValue();
            updateOrderStatus(orderNumber, status);
            txtStatus.appendText("Order Number: " + orderNumber + " - Status: " + status + "\n");
        });

        // Creating the scene and setting up the stage
        Scene scene = new Scene(grid, 600, 300);
        primaryStage.setTitle("Order Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
