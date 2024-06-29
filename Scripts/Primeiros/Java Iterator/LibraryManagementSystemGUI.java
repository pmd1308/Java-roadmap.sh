// Project thats impplements a lybrary management system thats includes a CRUD operations in mySQL database, a GUI using swing, caching techniques and functionality for generate a CSV file.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class LibraryManagementSystemGUI extends JFrame {
	// Parameters
    private Connection conn;
    private JTextArea textArea;

	public LibraryManagementSystemGUI() {
		connectToDatabase();

		// GUI windows
      setTitle("Library Management System");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      textArea = new JTextArea();
      JScrollPane scrollPane = new JScrollPane(textArea);
      add(scrollPane, BorderLayout.CENTER);
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new FlowLayout());

		// Buttons
      JButton btnCreate = new JButton("Create");
      btnCreate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String title = JOptionPane.showInputDialog(null, "Enter book title: ");
          if (title != null && !title.isEmpty()) {
            try {
              PreparedStatement ps = conn.prepareStatement("INSERT INTO books (title) VALUES (?)");
              ps.setString(1, title);
              ps.executeUpdate();
              ps.close();
            } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error: ");
              ex.printStackTrace();
            }
            updateTextArea();
          }
        }
      });

      JButton btnRead = new JButton("Export CSV");
      btnRead.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();
            FileWriter writer = new FileWriter("books.csv");
            writer.append("Title\n");
            while (rs.next()) {
              writer.append(rs.getString("title") + "\n");
            }
            writer.close();
            rs.close();
            ps.close();
          } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: ");
            ex.printStackTrace();
          }
        }
      });

      JButton btnDelete = new JButton("Delete");
      btnDelete.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String title = JOptionPane.showInputDialog(null, "Enter book title to delete: ");
          if (title != null && !title.isEmpty()) {
            try {
              PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE title = ?");
              ps.setString(1, title);
              ps.executeUpdate();
              ps.close();
            } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error: ");
              ex.printStackTrace();
            }
            updateTextArea();
          }
        }
      });

      JButton btnUpdate = new JButton("Update");
      btnUpdate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String oldTitle = JOptionPane.showInputDialog(null, "Enter current book title: ");
          String newTitle = JOptionPane.showInputDialog(null, "Enter new book title: ");
          if (oldTitle != null && !oldTitle.isEmpty() && newTitle != null && !newTitle.isEmpty()) {
            try {
              PreparedStatement ps = conn.prepareStatement("UPDATE books SET title = ? WHERE title = ?");
              ps.setString(1, newTitle);
              ps.setString(2, oldTitle);
              ps.executeUpdate();
              ps.close();
            } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error: ");
              ex.printStackTrace();
            }
            updateTextArea();
          }
        }
      });

      inputPanel.add(btnCreate);
      inputPanel.add(btnRead);
      inputPanel.add(btnUpdate);
      inputPanel.add(btnDelete);

      add(inputPanel, BorderLayout.SOUTH);

      updateTextArea();

	}

	// Function to establish connection to the database
    private void connectToDatabase() {
      try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
        PreparedStatement ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTO_INCREMENT, title VARCHAR(255))");
        ps.execute();
        ps.close();
      } catch (ClassNotFoundException | SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error: ");
        ex.printStackTrace();
      }
    }

	// Function to update the text area with book titles
    private void updateTextArea() {
      textArea.setText("");
      try {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM books");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
          String title = rs.getString("title");
          textArea.append(title + "\n");
        }
        ps.close();
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error: ");
        ex.printStackTrace();
      }
    }

	// Main method to run the GUI
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI().setVisible(true));
    }
}
