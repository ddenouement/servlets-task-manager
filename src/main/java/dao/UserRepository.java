package dao;

import model.*;
import util.DBFields;
import util.QueriesTask;
import util.QueriesUser;

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
            st = con.prepareStatement(QueriesUser.SQL_INSERT_USER, generatedColumns);

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
        } catch (SQLException throwables) {
            Logger.getAnonymousLogger().log(Level.WARNING, throwables.getLocalizedMessage());
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
        }
        return null;
    }


    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesUser.SQL_FIND_USER_BY_LOGIN);
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

    public List<User> findAllUsers() {
        List<User> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesUser.SQL_FIND_ALL_USERS);
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

            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
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
            pstmt = con.prepareStatement(QueriesUser.SQL_FIND_USER_BY_ID);
            pstmt.setInt(1, id_user);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
            close(rs);
            close(pstmt);
            close(con);
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, ex.getLocalizedMessage());
            close(rs);
            close(pstmt);
            close(con);

        }
        return user;
    }



}
