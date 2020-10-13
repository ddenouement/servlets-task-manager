package dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jdk.nashorn.internal.runtime.Context;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public  class BaseRepository {
    protected final DataSource ds;

    public BaseRepository( DataSource dataSource) {
        this.ds = dataSource;
    }
    protected Connection getConnection() throws SQLException {
        Connection r = ds.getConnection();
        r.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        return r;
    }
      void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                  }
        }
    }

    void setAutoCommit(Connection con, boolean b) {
        if (con != null) {
            try {
                con.setAutoCommit(b);
            } catch (SQLException ex) {
            }
        }
    }

    void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
            }
        }
    }
    void commit(Connection con) {
        if (con != null) {
            try {
                con.commit();
            } catch (SQLException ex) {
            }
        }
    }
      void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
      void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {
            }
        }
    }

}
