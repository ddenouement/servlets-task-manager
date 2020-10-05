package util;

public class QueriesActivity {
    public static String SQL_GET_PEOPLE_COUNT =
            "SELECT  count(user_activity.id_activity) as number_of_people \n" +
                    "from activity inner join user_activity on (activity.id = user_activity.id_activity)\n" +
                    " group by activity.id WHERE activity.id=?";

    public static final String SQL_UPDATE_ACTIVITY_BY_ID = "update activity set name=?, nameEn=?, nameUa=?, description=?, descriptionEn=?, descriptionUa=?, category=? where id=?";
    public static final String SQL_CREATE_ACTIVITY = "insert into activity(name, nameEn, nameUa, description, descriptionEn, descriptionUa, category) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_FIND_ACTIVITY_BY_ID = "SELECT *\n" +
            "FROM activity\n" +
            "LEFT JOIN (SELECT activity.id as aid, count(user_activity.id_activity) as number_of_people \n" +
            "                    from activity inner join user_activity on (activity.id = user_activity.id_activity)\n" +
            "                    where progress = 'ASSIGNED'\n" +
            "                     group by activity.id) as c on c.aid = activity.id\n" +
            "                     where id = ?;\n" +
            "                     ";

    public static final String SQL_FIND_ALL_ACTIVITIES = "select * from activity";
    public static final String SQL_FIND_ALL_ACTIVITIES_SORTED = "SELECT *\n" +
            "FROM activity\n" +
            "LEFT JOIN (SELECT activity.id as aid, count(user_activity.id_activity) as number_of_people \n" +
            "                    from activity inner join user_activity on (activity.id = user_activity.id_activity)\n" +
            "            where progress = 'ASSIGNED'  " +
            "        group by activity.id) as c on c.aid = activity.id\n" +
            "                     order by %s desc";


}