package model;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Model that represents user_activity entity
 *
 * @Author Yuliia Aleksandrova
 */
public class UserActivity {

    private int id;


    private int timeSpentInHours;


    private ZonedDateTime finishedOn;

    private User user;
    private Activity activity;
    private Progress progress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "id=" + id +
                ", timeSpentInHours=" + timeSpentInHours +
                ", finishedOn=" + finishedOn +
                ", user=" + user +
                ", activity=" + activity.getName() +
                '}';
    }

    public Activity getActivity() {
        return activity;
    }


    public int getTimeSpentInHours() {
        return timeSpentInHours;
    }


    public Progress getProgress() {
        return progress;
    }

    public Date getFinishedOn() {
        if(finishedOn==null) return null;
        return Date.from(finishedOn.toInstant());
    }

    public User getUser() {
        return user;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setFinishedOn(ZonedDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }

    public void setProgress(String progress) {
        this.progress = Progress.valueOf(progress.toUpperCase());
    }

    public void setTimeSpentInHours(int timeSpentInHours) {
        this.timeSpentInHours = timeSpentInHours;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
