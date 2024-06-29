import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class FileDownloaderGUI extends JFrame {

    // Parameters
    private JTextField urlField;
    private JButton downloadButton;
    private JProgressBar progressBar;

    public FileDownloaderGUI() {
        // Initialize GUI window
        setTitle("File Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // URL label and field
        JLabel urlLabel = new JLabel("URL:");
        urlField = new JTextField(20);

        add(urlLabel);
        add(urlField);

        // Download button
        downloadButton = new JButton("Download");
        add(downloadButton);

        // Action listener for download button
        downloadButton.addActionListener(e -> {
            String url = urlField.getText();
            if (url != null && !url.isEmpty()) {
                try {
                    URL fileUrl = new URL(url);
                    URLConnection connection = fileUrl.openConnection();
                    int fileSize = connection.getContentLength();

                    // Extract filename from URL
                    String filename = fileUrl.getFile();
                    filename = filename.substring(filename.lastIndexOf("/") + 1);

                    // Create output file
                    File outputFile = new File(filename);
                    outputFile.createNewFile();

                    // Create progress bar
                    progressBar = new JProgressBar(0, fileSize);
                    progressBar.setStringPainted(true);
                    progressBar.setString(filename);
                    add(progressBar);

                    // Create output stream
                    try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                        // Download file
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            progressBar.setValue(progressBar.getValue() + bytesRead);
                        }
                    }

                    // Ensure proper resource management (closing connection)
                    connection.getInputStream().close();

                    JOptionPane.showMessageDialog(this, "File downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    String message = "Error downloading file: ";
                    if (ex instanceof UnknownHostException) {
                        message += "Invalid URL or network issue.";
                    } else {
                        message += ex.getMessage();
                    }
                    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Remove progress bar after download or error
                    if (progressBar != null) {
                        remove(progressBar);
                        revalidate();
                        repaint();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid URL", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileDownloaderGUI downloaderGUI = new FileDownloaderGUI();
            downloaderGUI.setVisible(true);
        });
    }
}
