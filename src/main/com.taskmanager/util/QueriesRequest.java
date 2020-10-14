package util;
/**
 * Class to store Request Queries
 * @Author Yuliia Aleksandrova
 */
public class QueriesRequest {

    public static final String SQL_FIND_NUM_CREATED_QUERIES = "select count(*) as count from request where status='CREATED'";
    public static final String SQL_FIND_REQUEST_BY_ID = "select * from request where id=?";


    public static final String SQL_FIND_COUNT_ALL_REQUESTS = "select count(*) as count from request";
    public static final String SQL_FIND_ALL_REQUESTS_PAGED = "select * from request order by created_at desc LIMIT limitParam OFFSET offsetParam";
    public static final String SQL_CREATE_REQUEST = "insert into request (status, motif, created_at, id_user, id_user_activity) values (?, ?, ?, ?, ?)";
    public static final String SQL_CREATE_REQUESTED_TASK = "insert into user_activity (id_user, id_activity, progress) values (?, ?, 'REQUESTED') " ;
    public static final String SQL_ADD_USER_TO_ACTIVITY = "update user_activity set progress='ASSIGNED' where id=?";
    public static final String SQL_REMOVE_UER_FROM_ACTIVITY = "update user_activity set progress='CANCELLED' where id=?";
    public static final String SQL_CHECK_SAME_REQUESTS = "SELECT request.id from request where   EXISTS ( SELECT  request.id \n" +
            "                FROM request inner join user_activity on (request.id_user_activity=user_activity.id) \n" +
            "                WHERE user_activity.id_user =? and user_activity.id_activity=? and (status='CREATED' or status='ASSIGNED') )";
    public static String SQL_FIND_REQUESTS_BY_USER = "select * from request where id_user=? order by created_at desc";
    public static final String SQL_CHECK_TASK_STATUS_ASSIGNED = "  SELECT  id FROM user_activity  WHERE  id=? and progress='ASSIGNED'";
    public static final String SQL_UPDATE_REQUEST_STATUS_BASED_ON_CURRENT_STATUS = "" +
            "UPDATE   request  SET status=? WHERE  status=? AND id=?";
    public static final String SQL_SET_TASK_CANCELLED_IF_IS_NOT_FINISHED = "" +
            "UPDATE   user_activity  SET progress='CANCELLED' where id=?";


}
