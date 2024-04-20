import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class TodoCrudSwing extends JFrame {
    private JTextField taskField;
    private DefaultListModel<String> taskListModel;
    private JList<String> tasksList;
    private JButton addButton;
    private JButton removeButton;

    private LinkedList<String> tasks;
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;

    public TodoCrudSwing() {
        setTitle("Task Manager");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text field to add tasks
        taskField = new JTextField();
        add(taskField, BorderLayout.NORTH);

        // List to show tasks
        taskListModel = new DefaultListModel<>();
        tasksList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(tasksList);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                if (task.length() > 0) {
                    try {
                        tasks.add(task);
                        taskListModel.addElement(task);
                        taskField.setText("");
                        insertStatement.setString(1, task);
                        insertStatement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = tasksList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    try {
                        tasks.remove(selectedIndex);
                        taskListModel.remove(selectedIndex);
                        deleteStatement.setInt(1, selectedIndex); // Deve-se deletar pelo id, não pelo índice
                        deleteStatement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // Using GridLayout for button arrangement
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialization
        tasks = new LinkedList<>();
        connectionToDatabase();
        loadTasksFromDatabase();
        setVisible(true);
    }

    // Establish connection to the database
    private void connectionToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tasks.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT)"); // Create table if not exists
            statement.close();

            insertStatement = connection.prepareStatement("INSERT INTO tasks (task) VALUES (?)");
            deleteStatement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?"); // Delete by id
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load tasks from the database
    private void loadTasksFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                tasks.add(resultSet.getString("task"));
                taskListModel.addElement(resultSet.getString("task"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoCrudSwing();
            }
        });
    }
}
