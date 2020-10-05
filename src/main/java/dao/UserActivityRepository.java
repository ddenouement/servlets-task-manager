package dao;

import model.Activity;
import model.User;
import model.UserActivity;
import util.QueriesTask;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserActivityRepository extends BaseRepository {
    private static final UserActivityRepository instance = new UserActivityRepository();

    private UserActivityRepository() {
        super();
    }

    public static UserActivityRepository getInstance() {
        return instance;
    }



    public List<UserActivity> findTasksByActivityByProgress(int activityId, String progress) {
        List<UserActivity> tasks = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesTask.SQL_FIND_TASKS_ON_ACTIVITY_BY_ID_BY_PROGRESS_NAME);
            pstmt.setInt(1, activityId);
            pstmt.setString(2, progress);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (tasks == null) tasks = new ArrayList<>();
                UserActivity userActivity = mapRow(rs);/*
                UserActivity userActivity = new UserActivity();
                userActivity.setId(rs.getInt("task_id"));
                User u = UserRepository.getInstance().mapRow(rs);
                userActivity.setUser(u);
                userActivity.setProgress(rs.getString("progress"));

                Timestamp timestamp = rs.getTimestamp("date_end");
                if (timestamp != null) {
                    ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                    userActivity.setFinishedOn(time);
                }
                userActivity.setTimeSpentInHours(rs.getInt("time_elapsed_hrs"));
*/
                tasks.add(userActivity);
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            close(rs);
            close(pstmt);
            close(con);
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
        }
        return tasks;
    }




    public List<UserActivity> findTasksByUserId(int user_id) {
        List<UserActivity> tasks = new ArrayList<>();
        ;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesTask.SQL_FIND_TASKS_WITHOUT_CANCELLED);
            pstmt.setInt(2, user_id);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return tasks;
    }


    public boolean finishTask(int taskId, int hoursSpent) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesTask.SQL_SET_TASK_FINISHED);
            st.setInt(1, hoursSpent);
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
            st.setTimestamp(2, ts);
            st.setInt(3, taskId);
            int i = st.executeUpdate();
            if (i <= 0) {
                throw new DaoException("Cannot set finished");
            }
        } catch (SQLException ignored) {
            Logger.getAnonymousLogger().log(Level.WARNING, ignored.getLocalizedMessage());
            throw new DaoException("Cannot set finished");
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return true;
    }


    public UserActivity findTaskById(int id_task) {
        UserActivity task = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesTask.SQL_FIND_TASK_BY_ID);
            pstmt.setInt(1, id_task);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                task = mapRow(rs);
            }
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return task;
    }

    public boolean checkUserHasSameUnfinishedTask(int userId, int activityId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesTask.SQL_FIND_UNFINISHED_USER_TASK_BY_ACTIVITY_ID);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, activityId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return false;
    }

    public UserActivity mapRow(ResultSet rs) {
        UserActivity task = new UserActivity();
        try {
            String progress = rs.getString("progress");
            task.setProgress(progress);
            task.setId(rs.getInt("task_id"));
            Timestamp timestamp = rs.getTimestamp("date_end");
            if (timestamp != null) {
                ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                task.setFinishedOn(time);
            }
            User user = UserRepository.getInstance().findUserById(rs.getInt("id_user"));
            Activity activity = ActivityRepository.getInstance().findActivityById(
                    rs.getInt("id_activity"));
            task.setTimeSpentInHours(rs.getInt("time_elapsed_hrs"));
            task.setActivity(activity);
            task.setUser(user);
        } catch (SQLException throwables) {
            Logger.getAnonymousLogger().log(Level.WARNING, throwables.getMessage());
        }
        return task;
    }


}
