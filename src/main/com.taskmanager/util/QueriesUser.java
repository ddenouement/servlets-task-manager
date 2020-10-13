package util;

public class QueriesUser {
    public static final String SQL_FIND_ALL_USERS = "select * from user where role = 'USER'";

    public static final String SQL_FIND_USER_BY_LOGIN = "select * from user where login=?";
    public static final String SQL_INSERT_USER = "insert into user (login, email, firstname, lastname, password, role)" +
            " values (?, ?, ?, ?, ?, ?)";
    public static final String SQL_FIND_USER_BY_ID = "select * from user where id=?";

    public static final String SQL_FIND_NUM_USERS = "select count(*) as count from user";
}
