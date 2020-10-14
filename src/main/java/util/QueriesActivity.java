package util;
/**
 * Class to store Activity Queries
 * @Author Yuliia Aleksandrova
 */
public class QueriesActivity {
    public static final String SQL_DELETE_ACTIVITY = "DELETE FROM activity where id=?";
    public static String SQL_GET_PEOPLE_COUNT =
            "SELECT  count(user_activity.id_activity) as number_of_people \n" +
                    "from activity inner join user_activity on (activity.id = user_activity.id_activity)\n" +
                    " group by activity.id WHERE activity.id=?";

    public static final String SQL_UPDATE_ACTIVITY_BY_ID = "update activity set name=?, nameEn=?, nameUa=?, description=?, descriptionEn=?, descriptionUa=?, id_category=? where id=?";
    public static final String SQL_CREATE_ACTIVITY = "insert into activity(name, nameEn, nameUa, description, descriptionEn, descriptionUa, id_category) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_FIND_ACTIVITY_BY_ID = "SELECT *\n" +
            "FROM activity\n" +
            "LEFT JOIN (SELECT activity.id as aid, count(user_activity.id_activity) as number_of_people \n" +
            " from activity inner join user_activity on (activity.id = user_activity.id_activity)\n" +
            " where progress = 'ASSIGNED'\n" +
            " group by activity.id) as c on c.aid = activity.id " +
            " where activity.id=?";

    public static final String SQL_FIND_ALL_ACTIVITIES = "select * from activity";
    public static final String SQL_FIND_ALL_ACTIVITIES_SORTED_PAGED = "SELECT *\n" +
            "FROM activity " +
            "LEFT JOIN " +
            " (SELECT activity.id as aid, count(user_activity.id_activity) as number_of_people \n" +
            " FROM activity INNER JOIN user_activity on (activity.id = user_activity.id_activity)\n" +
            " WHERE progress = 'ASSIGNED'  " +
            " group by activity.id) as c on c.aid = activity.id\n" +
            " order by %s desc LIMIT limitParam OFFSET offsetParam";


}
