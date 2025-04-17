package fitness.club;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class EditTrainerDialog extends JDialog {
    private JTextField nameField;
    private JTextField specializationField;
    private boolean result = false;
    private int trainerId;
    
    public EditTrainerDialog(JFrame parent, int id, String name, String specialization) {
        super(parent, "Редактировать тренера", true);
        this.trainerId = id;
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Имя:"));
        nameField = new JTextField(name);
        panel.add(nameField);
        
        panel.add(new JLabel("Специализация:"));
        specializationField = new JTextField(specialization);
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
                DatabaseManager.updateTrainer(
                    trainerId,
                    nameField.getText().trim(),
                    specializationField.getText().trim()
                );
                result = true;
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Ошибка при обновлении тренера: " + ex.getMessage(),
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