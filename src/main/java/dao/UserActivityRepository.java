package dao;

public class UserActivityRepository extends BaseRepository{

    private static String SQL_GET_PEOPLE_COUNT =
            "SELECT  count(user_activity.id_activity) as number_of_people " +
            "from activity left join user_activity on (activity.id = user_activity.id_activity) " +
            " group by activity.id";

    private static final String SQL_FIND_TASKS_ON_ACTIVITY_BY_PROGRESS_NAME =
            "select user_activity.id as activity_id, id_progress, id_user, id_activity, user.id as user_id, user.firstname, user.lastname, user.id_profession, " +
            "from user_activity inner join user on (user.id = user_activity.id_user) " +
           " where id_progress in (select progress.id from progress where progress.name=?)";




}
