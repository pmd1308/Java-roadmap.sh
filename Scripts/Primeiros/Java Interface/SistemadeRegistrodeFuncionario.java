import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

interface Employee {
  string getEmployeeType();
  string calculateSalary();
}

class FullTimeEmployee implements Employee {
  private String name;
  private double salary;


  public FullTimeEmployee(String name, double salary) {
    this.name = name;
    this.salary = salary;
  }

  @Override
  public string getEmployeeType() {
    return "F";
  }

  @Override
  public string calculateSalary() {
    return String.format("%.2f", salary);
  }

}

class PartTimeEmployee implements Employee {
  private String name;
  private double hourlyRate;
  private int hoursWorked;

  public PartTimeEmployee(String name, double hourlyRate, int hoursWorked) {
    this.name = name;
    this.hourlyRate = hourlyRate;
    this.hoursWorked = hoursWorked;
  }

  @Override
  public string getEmployeeType() {
    return "P";
  }

  @Override
  public string calculateSalary() {
    return String.format("%.2f", hourlyRate * hoursWorked);
  }
}

public class EmployeeRegistration extends Application {
  private final String url = "jdbc:sqlite:employees.db";

  private void createTableIfNotExists() {
    try (Connection conn = DriverManager.getConnection(url);
      PreparedStatement stmt = conn.createStatement()) {
        stmt.execute("CREATE TABLE IF NOT EXISTS employees (\n"
        + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
        + " name TEXT NOT NULL,\n"
        + " salary REAL NOT NULL,\n"
        + " hourly_rate REAL NOT NULL,\n"
        + " hours_worked INTEGER NOT NULL\n"
        + ")");
        stmt.close();
        conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    private void insertEmployee(String name, String type, double salary, int hours_worked) throws SQLException {
      connection conn = DriverManager.getConnection(url);
      PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (name, type, salary, hours_worked) VALUES (?,?,?,?)");
      stmt.setString(1, name);
      stmt.setString(2, type);
      stmt.setDouble(3, salary);
      stmt.setInt(4, hours_worked);
      stmt.execute();

      stmt.close();
      conn.close();
    }

    // Method to show employees on database
    private void displayEmployees(TextArea resultArea) {
      try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {
        
        resultArea.clear();
        while (rs.next()) {
          resultArea.appendText("Nome: " + rs.getString("name") + "\n");
          resultArea.appendText("Tipo: " + rs.getString("type") + "\n");
          resultArea.appendText("Salário: $" + rs.getDouble("salary") + "\n\n");
          stmt.close();
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void start(Stage primaryStage) {
      createTableIfNotExists();

      // Create GUI elements
      Label nameLabel = new Label("Nome");
      TextField nameField = new TextField();
      Label typeLabel = new Label("Tipo:");
      ChoiceBox<String> typeBox = new ChoiceBox<>();
      typeBox.getItems().addAll("Full Time", "Part Time");
      Label salaryLabel = new Label("Salário/Hora:");
      TextField salaryField = new TextField();
      Label hoursWorkedLabel = new Label("Horas trabalhadas:");
      TextField hoursWorkedField = new TextField();
      Button addButton = new Button("Adicionar");
      TextArea resultArea = new TextArea();

      // Settings GUI layout
      GridPane gridPane = new GridPane();
      gridPane.setHgap(10);
      gridPane.setVgap(10);
      gridPane.setPadding(new Insets(25, 25, 25, 25));

      gridPane.add(nameLabel, 0, 0);
      gridPane.add(nameField, 1, 0);
      gridPane.add(typeLabel, 0, 1);
      gridPane.add(typeBox, 1, 1);
      gridPane.add(salaryLabel, 0, 2);
      gridPane.add(salaryField, 1, 2);
      gridPane.add(hoursWorkedLabel, 0, 3);
      gridPane.add(hoursWorkedField, 1, 3);
      gridPane.add(addButton, 0, 4, 2, 1);
      gridPane.add(resultArea, 0, 5, 2, 1);

      addButton.setOnAction(event -> {
        try {
          insertEmployee(nameField.getText(), typeBox.getValue(), Double.parseDouble(salaryField.getText()), Integer.parseInt(hoursWorkedField.getText()));

          resultArea.appendText("Adicionado");
          nameField.clear();
          typeBox.getSelectionModel().clearSelection();
          salaryField.clear();
          hoursWorkedField.clear();
        } catch (Exception e) {
          resultArea.appendText("Não foi possivel adicionar usuario");
        }
      });

      Scene scene = new Scene(gridPane, 400, 300);
      primaryStage.setTitle("Registro de Funcionários");
      primaryStage.setScene(scene);
      primaryStage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }
  }
}
