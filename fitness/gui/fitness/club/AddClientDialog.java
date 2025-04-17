package fitness.club;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddClientDialog extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private boolean result = false;
    
    public AddClientDialog(JFrame parent) {
        super(parent, "Добавить клиента", true);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Имя:"));
        nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Телефон:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);
        
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");
        
        okButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || 
                phoneField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Пожалуйста, заполните обязательные поля",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                DatabaseManager.addClient(
                    nameField.getText().trim(),
                    phoneField.getText().trim(),
                    emailField.getText().trim()
                );
                result = true;
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Ошибка при добавлении клиента: " + ex.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    public boolean getResult() {
        return result;
    }
} 