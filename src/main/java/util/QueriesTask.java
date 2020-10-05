package util;

public class QueriesTask {
    public static final String SQL_FIND_TASKS_ON_ACTIVITY_BY_ID_BY_PROGRESS_NAME =
            "select user_activity.id  as task_id, progress, id_activity,   time_elapsed_hrs, date_end," +
                    " user.id as id_user, user.role as role, user.password as password, user.firstname as firstname, user.login as login, user.email as email, user.lastname as lastname " +
                    " from user_activity inner join user on (user.id = user_activity.id_user) " +
                    " where id_activity=? AND progress=?";

    public static final String SQL_SET_TASK_FINISHED = "update user_activity set progress='FINISHED', time_elapsed_hrs=?, date_end=? where id=? and progress <> 'CANCELLED' and progress <> 'REQUESTED'";
    public static final String SQL_FIND_TASKS_WITHOUT_CANCELLED =
            "select distinct user_activity.id as task_id, user_activity.id_user, progress, user_activity.id_activity, time_elapsed_hrs, date_end  \n" +
                    "            from user_activity  left join request on (request.id_user_activity=user_activity.id)\n" +
                    "            where user_activity.id not in \n" +
                    "            (select user_activity.id as task_id\n" +
                    "            from user_activity  left join request on (request.id_user_activity=user_activity.id)\n" +
                    "            where    user_activity.id_user=? and (user_activity.progress = 'CANCELLED' or (request.motif = 'REMOVE' and request.status <> 'REJECTED')))" +
                    "and user_activity.id_user=?";
    public static final String SQL_FIND_TASK_BY_ID = "select id as task_id, progress, id_activity, id_user, time_elapsed_hrs, date_end from user_activity  where id=?";
    public static final String SQL_FIND_UNFINISHED_USER_TASK_BY_ACTIVITY_ID =
            "select distinct user_activity.id as task_id, progress, user_activity.id_activity, time_elapsed_hrs, date_end  \n" +
                    "            from user_activity  " +
                    " where progress = 'ASSIGNED' and id_user=? and id_activity=?";

}
