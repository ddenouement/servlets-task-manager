package dao;

import model.Activity;
import model.Category;
import org.apache.logging.log4j.LogManager;
import util.QueriesActivity;
import util.QueriesTask;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Repository that performs operations with Activity
 * @Author Yuliia Aleksandrova
 */
public class ActivityRepository extends BaseRepository {
    final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UserActivityRepository.class);

    ActivityRepository(DataSource d) {
        super(d);
    }

    public Optional<Activity> findActivityById(int id) {
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
        } catch (SQLException throwables) {
            logger.warn(throwables.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return Optional.ofNullable(found);
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

            RepositoryFactory repositoryFactory = new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
            activity.setCategory(repositoryFactory.categoryRepository()
                    .findCategoryById(rs.getInt("id_category"))
                    .orElse(new Category()));
            activity.setPeopleAmount(rs.getInt("number_of_people"));
            return activity;
        } catch (SQLException e) {
            logger.warn(e.getLocalizedMessage());
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
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
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
                if (found == null) found = new ArrayList<>();
                found.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
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
            st.setInt(7, activity.getCategory().getId());
            st.setInt(8, activity.getId());
            int i = st.executeUpdate();
            if (i <= 0) {
                throw new DaoException("Error updating activity");
            }
        } catch (SQLException ex) {
            close(rs);
            close(st);
            close(con);
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Error updating activity");
        }
        return false;
    }

    public Optional<Activity> createActivity(Activity activity) throws DaoException {
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
            st.setInt(7, activity.getCategory().getId());
            int i = st.executeUpdate();
            if (i > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    activity.setId(id);
                }
                return Optional.of(activity);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Error creating activity");
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return Optional.empty();
    }

    public List<Activity> findActivitiesSortedPaged(String sortBy, int limit, int offset) {
        List<Activity> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            String formattedRequest =
                   String.format(QueriesActivity.SQL_FIND_ALL_ACTIVITIES_SORTED_PAGED, sortBy)
                    .replace("limitParam", "" + limit)
                    .replace("offsetParam", "" + offset);
            pstmt = con.prepareStatement(formattedRequest);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (found == null) found = new ArrayList<>();
                found.add(mapRow(rs));
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
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
            logger.warn(ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return res;
    }

    public boolean deleteActivityCascade(int activityId)  throws DaoException{
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesActivity.SQL_DELETE_ACTIVITY);
            st.setInt(1,activityId);
            int i = st.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Error deleting activity");
        } finally {
            close(st);
            close(con);
        }
        return false;
    }
}


