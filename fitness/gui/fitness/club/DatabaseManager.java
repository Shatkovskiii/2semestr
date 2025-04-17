package fitness.club;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class DatabaseManager {
    public static void addTrainer(String name, String specialization) throws SQLException {
        String sql = "INSERT INTO trainers (name, specialization) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Trainer> getAllTrainers() throws SQLException {
        List<Trainer> trainers = new ArrayList<>();
        String sql = "SELECT * FROM trainers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trainers.add(new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization")
                ));
            }
        }
        return trainers;
    }
    
    public static void deleteTrainer(int id) throws SQLException {
        String deleteTrainingsSql = "DELETE FROM trainings WHERE trainer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteTrainingsSql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        
        String deleteTrainerSql = "DELETE FROM trainers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteTrainerSql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Тренер с ID " + id + " не найден");
            }
        }
    }
    
    public static void updateTrainer(int id, String name, String specialization) throws SQLException {
        String sql = "UPDATE trainers SET name = ?, specialization = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, specialization);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }
    
    public static void addClient(String name, String phone, String email) throws SQLException {
        String sql = "INSERT INTO clients (name, phone, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        }
        return clients;
    }
    
    public static void deleteClient(int id) throws SQLException {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public static void updateClient(int id, String name, String phone, String email) throws SQLException {
        String sql = "UPDATE clients SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }
    
    public static void addTraining(int trainerId, int clientId, 
                                 LocalDateTime startTime, LocalDateTime endTime, 
                                 String type) throws SQLException {
        String sql = "INSERT INTO trainings (trainer_id, client_id, start_time, end_time, type) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setInt(2, clientId);
            pstmt.setTimestamp(3, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(endTime));
            pstmt.setString(5, type);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Training> getAllTrainings() throws SQLException {
        List<Training> trainings = new ArrayList<>();
        String sql = "SELECT * FROM trainings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trainings.add(new Training(
                    rs.getInt("id"),
                    rs.getInt("trainer_id"),
                    rs.getInt("client_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    rs.getString("type")
                ));
            }
        }
        return trainings;
    }
    
    public static void deleteTraining(int id) throws SQLException {
        String sql = "DELETE FROM trainings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public static void updateTraining(int id, int trainerId, int clientId, 
                                    LocalDateTime startTime, LocalDateTime endTime, 
                                    String type) throws SQLException {
        String sql = "UPDATE trainings SET trainer_id = ?, client_id = ?, " +
                    "start_time = ?, end_time = ?, type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setInt(2, clientId);
            pstmt.setTimestamp(3, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(endTime));
            pstmt.setString(5, type);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }
    }
    
    public static boolean isTimeSlotAvailable(int trainerId, 
                                            LocalDateTime startTime, 
                                            LocalDateTime endTime) throws SQLException {
        String sql = "SELECT COUNT(*) FROM trainings WHERE trainer_id = ? " +
                    "AND ((start_time <= ? AND end_time > ?) OR " +
                    "(start_time < ? AND end_time >= ?))";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setTimestamp(2, Timestamp.valueOf(endTime));
            pstmt.setTimestamp(3, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(endTime));
            pstmt.setTimestamp(5, Timestamp.valueOf(startTime));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
                return false;
            }
        }
    }
} 