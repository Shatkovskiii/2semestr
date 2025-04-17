package fitness.club;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddTrainerDialog extends JDialog {
    private JTextField nameField;
    private JTextField specializationField;
    private boolean result = false;
    
    public AddTrainerDialog(JFrame parent) {
        super(parent, "Добавить тренера", true);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Имя:"));
        nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Специализация:"));
        specializationField = new JTextField();
        panel.add(specializationField);
        
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");
        
        okButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || 
                specializationField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Пожалуйста, заполните все поля",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                DatabaseManager.addTrainer(
                    nameField.getText().trim(),
                    specializationField.getText().trim()
                );
                result = true;
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Ошибка при добавлении тренера: " + ex.getMessage(),
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