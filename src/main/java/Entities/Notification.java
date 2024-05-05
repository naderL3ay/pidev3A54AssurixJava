package Entities;

import Entities.Reclamation;

import java.util.Date;

public class Notification {
    private int id;
    private int reclamationId;
    public Reclamation reclamation;

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", reclamationId=" + reclamationId +
                ", reclamation=" + reclamation +
                ", message='" + message + '\'' +
                ", link='" + link + '\'' +
                ", createdAt=" + createdAt +
                ", seen=" + seen +
                ", userId=" + userId +
                '}';
    }

    private String message;
    private String link;
    private Date createdAt;
    private Boolean seen = false;

    public Notification(int id, int reclamationId, String message, String link, Date createdAt, Boolean seen) {
        this.id = id;
        this.reclamationId = reclamationId;
        this.message = message;
        this.link = link;
        this.createdAt = createdAt;
        this.seen = seen;
    }

    public Notification() {

    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
    public int userId;
}
