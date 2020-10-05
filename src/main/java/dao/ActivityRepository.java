package dao;

import model.Activity;
import util.QueriesActivity;
import util.QueriesTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityRepository extends BaseRepository {

    private ActivityRepository() {
        super();
    }

    private static ActivityRepository instance = new ActivityRepository();

    public static ActivityRepository getInstance() {
        return instance;
    }

    public Activity findActivityById(int id) {
        Activity found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesActivity.SQL_FIND_ACTIVITY_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                found = mapRow(rs);
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException throwables) {
            Logger.getAnonymousLogger().log(Level.WARNING,throwables.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);
        }
        return found;
    }

    private Activity mapRow(ResultSet rs) {
        try {
            Activity activity = new Activity();
            activity.setId(rs.getInt("id"));
            activity.setName(rs.getString("name"));
            activity.setNameEn(rs.getString("nameEn"));
            activity.setNameUa(rs.getString("nameUa"));
            activity.setDescription(rs.getString("description"));
            activity.setDescriptionEn(rs.getString("descriptionEn"));
            activity.setDescriptionUa(rs.getString("descriptionUa"));
            activity.setCategory(rs.getString("category"));
            activity.setPeopleAmount(rs.getInt("number_of_people"));
            return activity;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.WARNING,e.getLocalizedMessage());
            throw new IllegalStateException(e);
        }
    }

    private int getPeopleCountInActivityById(int activityId) {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesActivity.SQL_GET_PEOPLE_COUNT);
            pstmt.setInt(1, activityId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("number_of_people");
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);
        }
        return result;
    }


    public List<Activity> findAllActivities() {
        List<Activity> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesActivity.SQL_FIND_ALL_ACTIVITIES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if(found == null) found = new ArrayList<>();
                found .add( mapRow(rs));
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);
        }
        return found;
    }

    public boolean updateActivity(Activity activity) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesActivity.SQL_UPDATE_ACTIVITY_BY_ID);
            st.setString(1, activity.getName());
            st.setString(2, activity.getNameEn());
            st.setString(3, activity.getNameUa());
            st.setString(4, activity.getDescription());
            st.setString(5, activity.getDescriptionEn());
            st.setString(6, activity.getDescriptionUa());
            st.setString(7, activity.getCategory().getName());
            st.setInt(8, activity.getId());
            int i = st.executeUpdate();
            if (i <= 0) {
               throw new DaoException("Error updating activity");
            }
        } catch (SQLException ex) {
            close(rs);
            close(st);
            close(con);
            Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
            throw new DaoException("Error updating activity");
        }
        return false;
    }

    public Activity createActivity (Activity activity) throws DaoException {
        Connection con = null;
        String generatedColumns[] = {"id"};
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesActivity.SQL_CREATE_ACTIVITY, generatedColumns);
            st.setString(1, activity.getName());
            st.setString(2, activity.getNameEn());
            st.setString(3, activity.getNameUa());
            st.setString(4, activity.getDescription());
            st.setString(5, activity.getDescriptionEn());
            st.setString(6, activity.getDescriptionUa());
            st.setString(7, activity.getCategory().getName());
            int i = st.executeUpdate();
            if (i > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    activity.setId(id);
                }
                return activity;
            }
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
            throw new DaoException("Error creating activity");
        }
        finally {
            close(rs);
            close(st);
            close(con);
        }
        return null;
    }

    public List<Activity> findActivitiesSorted(String sortBy) {
        List<Activity> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            String sql = String.format(QueriesActivity.SQL_FIND_ALL_ACTIVITIES_SORTED, sortBy);
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if(found == null) found = new ArrayList<>();
                found .add( mapRow(rs));
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);
        }
        return found;
    }

    public int getCountActivities() {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            Connection con = null;
            int res = 0;
            try {
                con = getConnection();
                pstmt = con.prepareStatement(QueriesTask.SQL_GET_NUM_ACTIVITIES);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    res = rs.getInt("count");
                }
            } catch (SQLException ex) {
                Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
            } finally {
                close(rs);
                close(pstmt);
                close(con);
            }
            return res;
        }

}


