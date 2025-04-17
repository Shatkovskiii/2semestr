package fitness.club;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FitnessClubGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel trainersPanel;
    private JPanel clientsPanel;
    private JPanel trainingsPanel;
    private JTable trainersTable;
    private JTable clientsTable;
    private JTable trainingsTable;
    
    public FitnessClubGUI() {
        setTitle("Фитнес-клуб - Административная панель");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        trainersPanel = createTrainersPanel();
        clientsPanel = createClientsPanel();
        trainingsPanel = createTrainingsPanel();
        
        tabbedPane.addTab("Тренеры", trainersPanel);
        tabbedPane.addTab("Клиенты", clientsPanel);
        tabbedPane.addTab("Тренировки", trainingsPanel);
        
        add(tabbedPane);
        
        refreshData();
    }
    
    private JPanel createTrainersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Имя", "Специализация"};
        Object[][] data = {};
        trainersTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(trainersTable);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить тренера");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        
        addButton.addActionListener(e -> {
            AddTrainerDialog dialog = new AddTrainerDialog(this);
            dialog.setVisible(true);
            if (dialog.getResult()) {
                refreshData();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Имя", "Телефон", "Email"};
        Object[][] data = {};
        clientsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить клиента");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        
        addButton.addActionListener(e -> {
            AddClientDialog dialog = new AddClientDialog(this);
            dialog.setVisible(true);
            if (dialog.getResult()) {
                refreshData();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTrainingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Тренер", "Клиент", "Дата", "Время", "Тип"};
        Object[][] data = {};
        trainingsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(trainingsTable);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Записать на тренировку");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Отменить");
        
        addButton.addActionListener(e -> {
            AddTrainingDialog dialog = new AddTrainingDialog(this);
            dialog.setVisible(true);
            if (dialog.getResult()) {
                refreshData();
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshData() {
        try {
            List<Trainer> trainers = DatabaseManager.getAllTrainers();
            Object[][] trainersData = new Object[trainers.size()][3];
            for (int i = 0; i < trainers.size(); i++) {
                Trainer trainer = trainers.get(i);
                trainersData[i][0] = trainer.getId();
                trainersData[i][1] = trainer.getName();
                trainersData[i][2] = trainer.getSpecialization();
            }
            trainersTable.setModel(new javax.swing.table.DefaultTableModel(
                trainersData, new String[]{"ID", "Имя", "Специализация"}));
            
            List<Client> clients = DatabaseManager.getAllClients();
            Object[][] clientsData = new Object[clients.size()][4];
            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);
                clientsData[i][0] = client.getId();
                clientsData[i][1] = client.getName();
                clientsData[i][2] = client.getPhone();
                clientsData[i][3] = client.getEmail();
            }
            clientsTable.setModel(new javax.swing.table.DefaultTableModel(
                clientsData, new String[]{"ID", "Имя", "Телефон", "Email"}));
            
            List<Training> trainings = DatabaseManager.getAllTrainings();
            Object[][] trainingsData = new Object[trainings.size()][6];
            for (int i = 0; i < trainings.size(); i++) {
                Training training = trainings.get(i);
                trainingsData[i][0] = training.getId();
                trainingsData[i][1] = getTrainerName(training.getTrainerId());
                trainingsData[i][2] = getClientName(training.getClientId());
                trainingsData[i][3] = training.getStartTime().toLocalDate();
                trainingsData[i][4] = training.getStartTime().toLocalTime() + " - " + 
                                    training.getEndTime().toLocalTime();
                trainingsData[i][5] = training.getType();
            }
            trainingsTable.setModel(new javax.swing.table.DefaultTableModel(
                trainingsData, new String[]{"ID", "Тренер", "Клиент", "Дата", "Время", "Тип"}));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при обновлении данных: " + ex.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getTrainerName(int trainerId) throws SQLException {
        List<Trainer> trainers = DatabaseManager.getAllTrainers();
        for (Trainer trainer : trainers) {
            if (trainer.getId() == trainerId) {
                return trainer.getName();
            }
        }
        return "Неизвестный тренер";
    }
    
    private String getClientName(int clientId) throws SQLException {
        List<Client> clients = DatabaseManager.getAllClients();
        for (Client client : clients) {
            if (client.getId() == clientId) {
                return client.getName();
            }
        }
        return "Неизвестный клиент";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FitnessClubGUI().setVisible(true);
        });
    }
} 