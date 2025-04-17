package fitness.club;

import java.time.LocalDateTime;

public class Training {
    private int id;
    private int trainerId;
    private int clientId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String type;
    
    public Training(int id, int trainerId, int clientId, LocalDateTime startTime, 
                   LocalDateTime endTime, String type) {
        this.id = id;
        this.trainerId = trainerId;
        this.clientId = clientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTrainerId() { return trainerId; }
    public void setTrainerId(int trainerId) { this.trainerId = trainerId; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
} 