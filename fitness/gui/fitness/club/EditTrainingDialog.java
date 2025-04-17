package fitness.club;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EditTrainingDialog extends JDialog {
    private JComboBox<Trainer> trainerCombo;
    private JComboBox<Client> clientCombo;
    private JSpinner dateSpinner;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JComboBox<String> typeCombo;
    private boolean result = false;
    private int trainingId;
    
    public EditTrainingDialog(JFrame parent, int id, String trainerName, 
                            String clientName, String date, String time, String type) {
        super(parent, "Редактировать тренировку", true);
        this.trainingId = id;
        
        try {
            List<Trainer> trainers = DatabaseManager.getAllTrainers();
            List<Client> clients = DatabaseManager.getAllClients();
            
            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            panel.add(new JLabel("Тренер:"));
            trainerCombo = new JComboBox<>(trainers.toArray(new Trainer[0]));
            for (int i = 0; i < trainers.size(); i++) {
                if (trainers.get(i).getName().equals(trainerName)) {
                    trainerCombo.setSelectedIndex(i);
                    break;
                }
            }
            panel.add(trainerCombo);
            
            panel.add(new JLabel("Клиент:"));
            clientCombo = new JComboBox<>(clients.toArray(new Client[0]));
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getName().equals(clientName)) {
                    clientCombo.setSelectedIndex(i);
                    break;
                }
            }
            panel.add(clientCombo);
            
            panel.add(new JLabel("Дата:"));
            dateSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy");
            dateSpinner.setEditor(dateEditor);
            panel.add(dateSpinner);
            
            panel.add(new JLabel("Время начала:"));
            startTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
            startTimeSpinner.setEditor(startTimeEditor);
            panel.add(startTimeSpinner);
            
            panel.add(new JLabel("Время окончания:"));
            endTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
            endTimeSpinner.setEditor(endTimeEditor);
            panel.add(endTimeSpinner);
            
            panel.add(new JLabel("Тип тренировки:"));
            typeCombo = new JComboBox<>(new String[]{"Персональная", "Групповая"});
            typeCombo.setSelectedItem(type);
            panel.add(typeCombo);
            
            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Отмена");
            
            okButton.addActionListener(e -> {
                try {
                    java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
                    java.util.Date startTime = (java.util.Date) startTimeSpinner.getValue();
                    java.util.Date endTime = (java.util.Date) endTimeSpinner.getValue();
                    
                    LocalDateTime startDateTime = LocalDateTime.of(
                        selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        startTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime()
                    );
                    
                    LocalDateTime endDateTime = LocalDateTime.of(
                        selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        endTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime()
                    );
                    
                    LocalTime start = startDateTime.toLocalTime();
                    LocalTime end = endDateTime.toLocalTime();
                    if (start.isBefore(LocalTime.of(9, 0)) || end.isAfter(LocalTime.of(22, 0))) {
                        JOptionPane.showMessageDialog(this,
                            "Тренировки проводятся с 9:00 до 22:00",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Trainer selectedTrainer = (Trainer) trainerCombo.getSelectedItem();
                    if (!DatabaseManager.isTimeSlotAvailable(
                            selectedTrainer.getId(), startDateTime, endDateTime)) {
                        JOptionPane.showMessageDialog(this,
                            "Выбранное время уже занято",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    DatabaseManager.updateTraining(
                        trainingId,
                        selectedTrainer.getId(),
                        ((Client) clientCombo.getSelectedItem()).getId(),
                        startDateTime,
                        endDateTime,
                        (String) typeCombo.getSelectedItem()
                    );
                    
                    result = true;
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Ошибка при обновлении тренировки: " + ex.getMessage(),
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при загрузке данных: " + ex.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    public boolean getResult() {
        return result;
    }
} 