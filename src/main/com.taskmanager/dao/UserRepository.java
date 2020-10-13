package dao;

import model.*;
import org.apache.logging.log4j.LogManager;
import util.DBFields;
import util.QueriesUser;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class UserRepository extends BaseRepository {
    final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UserActivityRepository.class);

    UserRepository(DataSource ds) {
        super(ds);
    }

    public Optional<User> register(User user) throws DaoException {

        Optional<User> found = findUserByLogin(user.getLogin());
        if (found.isPresent()) {
            throw new DaoException("User with this login already Exists");
        }
        Connection con = null;
        String generatedColumns[] = {"id"};
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesUser.SQL_INSERT_USER, generatedColumns);

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getPassword().getBytes());
            byte[] digest = md.digest();
            String encodedPass  = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();

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
                return Optional.of(user);
            }
        } catch (SQLException throwables) {
            logger.warn(throwables.getLocalizedMessage());
            close(rs);
            close(st);
            close(con);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<User> authorizeByPasswordAndLogin(String login, String password) {
        Optional<User> found = findUserByLogin(login);
        if (found.isPresent()) {
            MessageDigest md = null;
            String hashedSentPass = "";
            try {
                md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                hashedSentPass = DatatypeConverter
                        .printHexBinary(digest).toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            String encodedActualPass = found.get().getPassword();
            if (encodedActualPass.equals(hashedSentPass)) {
                return (found);
            }
        }
        return Optional.empty();
    }


    public Optional<User> findUserByLogin(String login) {
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
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return Optional.ofNullable(user);
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

            logger.warn(ex.getLocalizedMessage());
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


    public Optional<User> findUserById(int id_user) {
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
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return Optional.ofNullable(user);
    }


    public int getNumberOfAllUsers() {
        int res = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesUser.SQL_FIND_NUM_USERS);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                res = rs.getInt("count");
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
        return res;
    }
}
