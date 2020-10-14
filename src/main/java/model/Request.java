package model;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Model that represents Request entity
 *
 * @Author Yuliia Aleksandrova
 */
public class Request {

    private int id;
    private ZonedDateTime createdAt;
    private User user;
    private Status status;
    private Motif motif;
    private UserActivity task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(UserActivity task) {
        this.task = task;
    }

    public User getUser() {
        return task.getUser();
    }

    public Activity getActivity() {
        return task.getActivity();
    }

    public UserActivity getTask() {
        return task;
    }

    public Motif getMotif() {
        return motif;
    }

    public Status getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return Date.from(createdAt.toInstant());
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMotif(String motif) {
        this.motif = Motif.valueOf(motif.toUpperCase());
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status.toUpperCase());
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", status=" + status +
                ", motif=" + motif  +
                '}';
    }
}
