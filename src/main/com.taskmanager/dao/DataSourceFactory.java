package dao;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
/**
 * Class that can return multiple DataSources
 * @Author Yuliia Aleksandrova
 */
public class DataSourceFactory {

    public static DataSource getMySqlDatasource() {
        MysqlDataSource ds ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        ds = new MysqlConnectionPoolDataSource();
        ds.setURL("jdbc:mysql://localhost/tmdb2?useEncoding=true&characterEncoding=UTF-8&serverTimezone=UTC");
        ds.setUser("julia");
        ds.setPassword("julia");
        return ds;
    }
}
