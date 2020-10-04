package dao;

import model.*;
import util.DBFields;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository extends BaseRepository {
    private static final String SQL_FIND_ALL_USERS = "select * from user where role = 'USER'";

    private static final String SQL_FIND_USER_BY_LOGIN = "select * from user where login=?";
    private static final String SQL_INSERT_USER = "insert into user (login, email, firstname, lastname, password, role)" +
            " values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_USERS_ON_ACTIVITY = "select user.id, login, email, firstname, lastname, password, role " +
            "from user_activity inner join user on user.id = user_activity.id_user";

    private static final String SQL_FIND_TASKS_ON_ACTIVITY_BY_ID_BY_PROGRESS_NAME =
            "select user_activity.id  as task_id, progress, id_activity,   time_elapsed_hrs, date_end," +
                    " user.id as id, user.role as role, user.password as password, user.firstname as firstname, user.login as login, user.email as email, user.lastname as lastname " +
                    " from user_activity inner join user on (user.id = user_activity.id_user) " +
                    " where id_activity=? AND progress=?";

    private static final String SQL_FIND_USER_BY_ID = "select * from user where id=?";
    private static final String SQL_SET_TASK_FINISHED = "update user_activity set progress='FINISHED', time_elapsed_hrs=?, date_end=? where id=? and progress <> 'CANCELLED' and progress <> 'REQUESTED'";
    private static final String SQL_FIND_TASKS_WITHOUT_CANCELLED =
            "select distinct user_activity.id as task_id, progress, user_activity.id_activity, time_elapsed_hrs, date_end  \n" +
            "            from user_activity  left join request on (request.id_user_activity=user_activity.id)\n" +
            "            where user_activity.id not in \n" +
            "            (select user_activity.id as task_id\n" +
            "            from user_activity  left join request on (request.id_user_activity=user_activity.id)\n" +
            "            where    user_activity.id_user=? and (user_activity.progress = 'CANCELLED' or (request.motif = 'REMOVE' and request.status <> 'REJECTED')))" +
                    "and user_activity.id_user=?";
    private static final String SQL_FIND_TASK_BY_ID = "select  * from user_activity  where id=?";
    private static final String SQL_FIND_UNFINISHED_USER_TASK_BY_ACTIVITY_ID =
            "select distinct user_activity.id as task_id, progress, user_activity.id_activity, time_elapsed_hrs, date_end  \n" +
                    "            from user_activity  " +
                    " where progress = 'ASSIGNED' and id_user=? and id_activity=?";
    private static final UserRepository instance = new UserRepository();

    private UserRepository() {
        super();
    }

    public static UserRepository getInstance() {
        return instance;
    }

    public User register(User user) throws DaoException {

        User found = findUserByLogin(user.getLogin());
        if (found != null) {

            System.out.println(found.getFirstName());
            throw new DaoException("User with this login already Exists");
        }

        Connection con = null;
        String generatedColumns[] = {"id"};
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL_INSERT_USER, generatedColumns);

            String encodedPass = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

            st.setString(1, user.getLogin());
            st.setString(2, user.getEmail());
            st.setString(3, user.getFirstName());
            st.setString(4, user.getLastName());
            st.setString(5, encodedPass);
            st.setString(6, user.getRole().getName());
            int i = st.executeUpdate();
            if (i > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    user.setId(id);
                }
                return user;
            }


        } catch (SQLException ignored) {
            System.out.println(ignored.getLocalizedMessage());

            close(rs);
            close(st);
            close(con);
        }
        return null;
    }


    public User authorizeByPasswordAndLogin(String login, String password) {
        User found = findUserByLogin(login);
        if (found != null) {
            String encodedSentPass = Base64.getEncoder().encodeToString(password.getBytes());
            String encodedActualPass = found.getPassword();
            if (encodedActualPass.equals(encodedSentPass)) {
                return found;
            }
            //throw err

        }
        //throw err
        return null;
    }


    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);

        }
        return user;
    }

/*
    private Profession getProfessionById(int professionId) {
        Profession profession = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PROFESSION_BY_ID);
            pstmt.setInt(1, professionId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                profession.setId(professionId);
                profession.setNameEn(rs.getString("name_en"));
                profession.setNameUa(rs.getString("name_ua"));
                profession.setName(rs.getString("name"));
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {

            close(rs);
            close(pstmt);
            close(con);

        }
        return profession;
    }
*/

    public List<User> findAllUsers() {
        List<User> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ALL_USERS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (found == null) found = new ArrayList<>();
                User user = mapRow(rs);
                found.add(user);
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

    public User mapRow(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt(DBFields.ID));
            user.setLogin(rs.getString(DBFields.USER_LOGIN));
            user.setPassword(rs.getString(DBFields.USER_PASSWORD));
            user.setFirstName(rs.getString(DBFields.USER_FIRST_NAME));
            user.setLastName(rs.getString(DBFields.USER_LAST_NAME));
            user.setEmail(rs.getString("email"));
     //       user.setProfession(getProfessionById(rs.getInt(DBFields.USER_PROFESSION_ID)));
            user.setRole(rs.getString("role"));
            return user;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public User findUserById(int id_user) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            pstmt.setInt(1, id_user);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
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
        return user;
    }

    public List<UserActivity> findUsersByActivityByProgress(int activityId, String progress) {
        List<UserActivity> tasks = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_TASKS_ON_ACTIVITY_BY_ID_BY_PROGRESS_NAME);
            pstmt.setInt(1, activityId);
            pstmt.setString(2, progress);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (tasks == null) tasks = new ArrayList<>();
                UserActivity userActivity = new UserActivity();
                userActivity.setId(rs.getInt("task_id"));
                User u = mapRow(rs);
                //Progress p = findProgressById(progressId);
                userActivity.setUser(u);
                userActivity.setProgress(rs.getString("progress"));

                Timestamp timestamp = rs.getTimestamp("date_end");
                if (timestamp != null) {
                    ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                    userActivity.setFinishedOn(time);
                }
                userActivity.setTimeSpentInHours(rs.getInt("time_elapsed_hrs"));

                tasks.add(userActivity);
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            close(rs);
            close(pstmt);
            close(con);
    Logger.getAnonymousLogger().log(Level.WARNING,ex.getLocalizedMessage());
        }
        return tasks;
    }
/*
    private Progress findProgressById(int progressId) {
        Progress p = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_PROGRESS_BY_ID);
            pstmt.setInt(1, progressId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                p.setId(progressId);
                p.setNameEn(rs.getString("name_en"));
                p.setNameUa(rs.getString("name_ua"));
                p.setName(rs.getString("name"));
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {

            close(rs);
            close(pstmt);
            close(con);

        }
        return p;
    }
*/

    public List<UserActivity> findTasksByUserId(int user_id) {
        List<UserActivity> tasks =  new ArrayList<>();;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_TASKS_WITHOUT_CANCELLED);
            pstmt.setInt(2, user_id);
            pstmt.setInt(1, user_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserActivity userActivity = new UserActivity();
                String progress = rs.getString("progress");
                userActivity.setProgress(progress);
                userActivity.setId(rs.getInt("task_id"));

                Timestamp timestamp = rs.getTimestamp("date_end");
                if (timestamp != null) {
                    ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                    userActivity.setFinishedOn(time);
                }
                Activity activity = ActivityRepository.getInstance().findActivityById(
                        rs.getInt("id_activity"));
                userActivity.setTimeSpentInHours(rs.getInt("time_elapsed_hrs"));
                userActivity.setActivity(activity);
                tasks.add(userActivity);
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
        return tasks;
    }

    public boolean finishTask(int taskId, int hoursSpent) throws DaoException{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(SQL_SET_TASK_FINISHED);
            st.setInt(1, hoursSpent);
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
            st.setTimestamp(2, ts);
            st.setInt(3, taskId);
            int i = st.executeUpdate();
            if (i <= 0) {
                 throw  new DaoException("Cannot set finished");
            }
        } catch (SQLException ignored) {

            Logger.getAnonymousLogger().log(Level.WARNING,ignored.getLocalizedMessage());
            throw  new DaoException("Cannot set finished");
        }
        finally {
            close(rs);
            close(st);
            close(con);
        }
        return true;
    }

    public UserActivity findTaskById(int id_task) {
        UserActivity task =  null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_TASK_BY_ID);
            pstmt.setInt(1, id_task);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                task = new UserActivity();
                String progress = rs.getString("progress");
                task.setProgress(progress);
                task.setId(rs.getInt("id"));
                Timestamp timestamp = rs.getTimestamp("date_end");
                if (timestamp != null) {
                    ZonedDateTime time = timestamp.toLocalDateTime().atZone(ZoneId.of("UTC"));
                    task.setFinishedOn(time);
                }
                User user = findUserById(rs.getInt("id_user")) ;
                Activity activity = ActivityRepository.getInstance().findActivityById(
                        rs.getInt("id_activity"));
                task.setTimeSpentInHours(rs.getInt("time_elapsed_hrs"));
                task.setActivity(activity);
                task.setUser(user);
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
        return task;
    }

    public boolean checkUserHasSameUnfinishedTask(int userId, int activityId) {
         PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_UNFINISHED_USER_TASK_BY_ACTIVITY_ID);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, activityId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
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
        return false;

    }
}
