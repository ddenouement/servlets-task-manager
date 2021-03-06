package dao;

import model.*;
import org.apache.logging.log4j.LogManager;
import util.QueriesRequest;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository that performs operations with Requests
 * @Author Yuliia Aleksandrova
 */

public class RequestRepository extends BaseRepository {

    final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UserActivityRepository.class);

    RequestRepository(DataSource d) {
        super(d);
    }

    public List<Request> findAllRequestsByUserId(int userId) {
        List<Request> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesRequest.SQL_FIND_REQUESTS_BY_USER);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (found == null) found = new ArrayList<>();
                Request request = mapRow(rs);
                found.add(request);
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

    private Request mapRow(ResultSet rs) {
        try {
            Request request = new Request();
            request.setId(rs.getInt("id"));
            Timestamp timestamp = (rs.getTimestamp("created_at"));
            if (timestamp != null) {
                ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                request.setCreatedAt(time);
            }
            int id_task = rs.getInt("id_user_activity");
            RepositoryFactory repositoryFactory =
                    new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
            UserActivity task = repositoryFactory.userActivityRepository()
                    .findTaskById(id_task)
                    .orElseThrow(SQLException::new);
            String status = rs.getString("status");
            String motif = rs.getString("motif");
            request.setTask(task);
            request.setStatus(status);
            request.setMotif(motif);
            return request;
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new IllegalStateException();
        }
    }


    public List<Request> findAllRequestsPaged(int limit, int page) {
        List<Request> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            String formattedSql = QueriesRequest.SQL_FIND_ALL_REQUESTS_PAGED
                    .replace("limitParam", "" + limit)
                    .replace("offsetParam", "" + page);
            pstmt = con.prepareStatement(formattedSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (found == null) found = new ArrayList<>();
                Request request = mapRow(rs);
                found.add(request);
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

    public boolean dismissRequestById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement stDel = null;
        boolean result = false;

        try {
            con = getConnection();
            con.setAutoCommit(false);
            //update request status
            st = con.prepareStatement(QueriesRequest.SQL_UPDATE_REQUEST_STATUS_BASED_ON_CURRENT_STATUS);
            st.setString(1, Status.REJECTED.getName());
            st.setString(2, Status.CREATED.getName());
            st.setInt(3, id);
            int update = st.executeUpdate();
            if (update <= 0) {
                rollback(con);
                throw new DaoException("Error!Request is out of date");
            }
            //update task status
            st = con.prepareStatement(QueriesRequest.SQL_SET_TASK_CANCELLED_IF_IS_NOT_FINISHED);
            Request request = findRequestById(id).orElseThrow(() -> new DaoException("Request not found"));
            st.setInt(1, request.getTask().getId());//request.getId());
            st.executeUpdate();
            commit(con);
        } catch (SQLException ex) {
            rollback(con);
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Could not dismiss request");
        } finally {
            close(stDel);
            close(con);
            close(st);
        }
        return result;
    }

    public boolean acceptRequestById(int id) throws DaoException {

        Optional<Request> request = findRequestById(id);
        if (!request.isPresent()) {
            throw new DaoException("Error! This request doesnt exist");

        }

        if (request.get().getMotif() == Motif.ADD) {
            return acceptRequestAndAddToActivity(request.get());
        } else if (request.get().getMotif() == Motif.REMOVE) {
            return acceptRequestAndRemoveFromActivity(request.get());
        }
        return false;
    }

    //transactional
    private boolean acceptRequestAndAddToActivity(Request request) throws DaoException {
        Connection conn = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        ResultSet rsCheck = null;
        PreparedStatement stCheck = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            //firstly, we check if status of task is assigned
            stCheck = conn.prepareStatement(QueriesRequest.SQL_CHECK_TASK_STATUS_ASSIGNED);
            stCheck.setInt(1, request.getTask().getId());
            rsCheck = stCheck.executeQuery();
            if (rsCheck.next()) {
                //Rollback from failure
                rollback(conn);
                throw new DaoException("Error! This request is out of date");
            }

            //Secondly, we perform action with user task
            st2 = conn.prepareStatement(QueriesRequest.SQL_ADD_USER_TO_ACTIVITY);
            st2.setInt(1, request.getTask().getId());
            int update2 = st2.executeUpdate();
            if (update2 <= 0) {
                //Rollback from failure
                rollback(conn);
                throw new DaoException("Error! Could not add to activity");
            }
            //Lastly, we update status, checking if its status was CREATED
            st = conn.prepareStatement(QueriesRequest.SQL_UPDATE_REQUEST_STATUS_BASED_ON_CURRENT_STATUS);
            st.setString(1, Status.ACCEPTED.getName());
            st.setString(2, Status.CREATED.getName());
            st.setInt(3, request.getId());
            int update = st.executeUpdate();
            if (update <= 0) {
                //Rollback from failure
                rollback(conn);
                throw new DaoException("Error! Request is already accepted");
            }
            commit(conn);
        } catch (SQLException ex) {

            logger.warn(ex.getLocalizedMessage());
            rollback(conn);
            throw new DaoException("Error! Could not add to activity and update request");
        } finally {
            setAutoCommit(conn, true);
            close(conn);
            close(st);
            close(st2);
            close(rsCheck);
            close(stCheck);
        }
        return true;
    }

    //transactional
    private boolean acceptRequestAndRemoveFromActivity(Request request) throws DaoException {
        Connection conn = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        PreparedStatement stCheck = null;
        ResultSet rsCheck = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            //Firstly, we update status, checking if its status was CREATED
            st = conn.prepareStatement(QueriesRequest.SQL_UPDATE_REQUEST_STATUS_BASED_ON_CURRENT_STATUS);
            st.setString(1, Status.ACCEPTED.getName());
            st.setString(2, Status.CREATED.getName());
            st.setInt(3, request.getId());
            int update = st.executeUpdate();
            if (update <= 0) {
                //Rollback from failure
                conn.rollback();
                throw new DaoException("Error! Could not accept request");
            }

            //secondly, we check if status of task is still assigned
            stCheck = conn.prepareStatement(QueriesRequest.SQL_CHECK_TASK_STATUS_ASSIGNED);
            stCheck.setInt(1, request.getTask().getId());
            rsCheck = stCheck.executeQuery();
            if (!rsCheck.next()) {
                //Rollback from failure
                rollback(conn);
                throw new DaoException("Error! This request is out of date");
            }


            //Thirdly, we perform action with user task
            st2 = conn.prepareStatement(QueriesRequest.SQL_SET_TASK_CANCELLED_IF_IS_NOT_FINISHED);
            st2.setInt(1, request.getTask().getId());
            int update2 = st2.executeUpdate();
            if (update2 <= 0) {
                //Rollback from failure
                conn.rollback();
                throw new DaoException("Not removing! User finished activity");
            }
            conn.commit();

        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Error! Could not add to activity and update request");
        } finally {
            setAutoCommit(conn, true);
            close(conn);
            close(st);
            close(st2);
            close(rsCheck);
            close(stCheck);
        }
        return true;
    }

    public Optional<Request> findRequestById(int id) {
        Request found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesRequest.SQL_FIND_REQUEST_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                found = mapRow(rs);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return Optional.ofNullable(found);
    }


    public int createAddRequest(int activityId, int userId) throws DaoException {
        Connection con = null;
        PreparedStatement stCheckSimilarRequests = null;
        PreparedStatement stCreateTask = null;
        PreparedStatement st = null;
        int result = 0;
        String generatedColumns[] = {"id"};
        ResultSet rsTask = null;
        ResultSet rs2 = null;
        ResultSet rs = null;
        try {
            //check if he has unfinished same activity
            RepositoryFactory repositoryFactory =
                    new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
            boolean hasUnfinished = repositoryFactory.userActivityRepository().checkUserHasSameUnfinishedTask(userId, activityId);
            if (hasUnfinished) {
                rollback(con);
                throw new DaoException("You already have it unfinished");
            }

            con = getConnection();
            setAutoCommit(con, false);
            //check if the same request exists
            stCheckSimilarRequests = con.prepareStatement(QueriesRequest.SQL_CHECK_SAME_REQUESTS);
            stCheckSimilarRequests.setInt(1, userId);
            stCheckSimilarRequests.setInt(2, activityId);
            rs2 = stCheckSimilarRequests.executeQuery();
            if (rs2.next()) {
                rollback(con);
                throw new DaoException("Error! You have unanswered request on this activity!");
            }
            int taskId = 0;
            int update = 0;

            //create requested task
            stCreateTask = con.prepareStatement(QueriesRequest.SQL_CREATE_REQUESTED_TASK, generatedColumns);
            stCreateTask.setInt(1, userId);
            stCreateTask.setInt(2, activityId);
            update = stCreateTask.executeUpdate();
            if (update > 0) {
                rsTask = stCreateTask.getGeneratedKeys();
                if (rsTask.next()) {
                    taskId = rsTask.getInt(1);
                }
            } else {
                rollback(con);
                throw new DaoException("Could not create requested task");
            }

            //create the request
            st = con.prepareStatement(QueriesRequest.SQL_CREATE_REQUEST, generatedColumns);
            st.setString(1, Status.CREATED.getName());
            st.setString(2, Motif.ADD.getName());
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
            st.setTimestamp(3, ts);
            st.setInt(4, userId);
            st.setInt(5, taskId);
            update = st.executeUpdate();
            if (update > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                    commit(con);
                }
            } else {
                rollback(con);
                throw new DaoException("Looks like you already made a request on this activity");
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Looks like you already made a request on this activity");
        } finally {
            setAutoCommit(con, true);
            close(con);
            close(stCreateTask);
            close(rsTask);
            close(st);
            close(stCheckSimilarRequests);
            close(rs2);
            close(rs);
        }
        return result;
    }


    public int createRemoveRequest(int taskId, int userId) throws DaoException {
        Connection con = null;
        PreparedStatement stCheckSimilarRequests = null;
        PreparedStatement stFindTask = null;
        PreparedStatement st = null;
        int result = 0;
        String generatedColumns[] = {"id"};
        ResultSet rsTask = null;
        ResultSet rs2 = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            setAutoCommit(con, false);

            //find requested task
            RepositoryFactory repositoryFactory =
                    new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
            Optional<UserActivity> task = repositoryFactory.userActivityRepository().findTaskById(taskId);
            if (!task.isPresent()) {
                rollback(con);
                throw new DaoException("You can not remove a task if you are not assigned to it");
            }

            //check if the same request exists
            stCheckSimilarRequests = con.prepareStatement(QueriesRequest.SQL_CHECK_SAME_REQUESTS);
            stCheckSimilarRequests.setInt(1, userId);
            stCheckSimilarRequests.setInt(2, task.get().getActivity().getId());
            rs2 = stCheckSimilarRequests.executeQuery();
            if (rs2.next()) {
                rollback(con);
                throw new DaoException("Error! You have unanswered request on this activity!");
            }
            int update = 0;
            //create the request
            st = con.prepareStatement(QueriesRequest.SQL_CREATE_REQUEST, generatedColumns);
            st.setString(1, Status.CREATED.getName());
            st.setString(2, Motif.REMOVE.getName());
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
            st.setTimestamp(3, ts);
            st.setInt(4, userId);
            st.setInt(5, taskId);
            update = st.executeUpdate();
            if (update > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                    commit(con);
                }
            } else {
                rollback(con);
                throw new DaoException("Looks like you already made a request on this activity");
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Looks like you already made a request on this activity");
        } finally {
            setAutoCommit(con, true);
            close(con);
            close(stFindTask);
            close(rsTask);
            close(st);
            close(stCheckSimilarRequests);
            close(rs2);
            close(rs);
        }
        return result;
    }


    public int getNumRequests() {
        int cnt = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesRequest.SQL_FIND_COUNT_ALL_REQUESTS);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("count");
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
        return cnt;
    }

    public int getNumRequestsCreated() {
        int cnt = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesRequest.SQL_FIND_NUM_CREATED_QUERIES);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("count");
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
        return cnt;
    }
}