// Snip basico, então não vale a pena escrever um readme.md

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor {
    JFrame frame;
    JTextField textField;

    public TextEditor() {
        // Explicar
        frame = new JFrame("Text Editor");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField();
        frame.add(new JSCrollPane(textField));

        JButton button = new JButton("Save");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        }

        frame.add(button, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void save() {
        JFileChooser dialog = new JFileChooser();
        int result = dialog.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = dialog.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter( new FileWriter(file))) {
                writer.write(textField.getText());
                JOptionPane.showMessageDialog( frame, "Salvo");
            } catch (IOException e) {
                JOptionPane.showMessageDialog( frame, "Erro", e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}


// O que é o Jframe? O que é javax.swing? 