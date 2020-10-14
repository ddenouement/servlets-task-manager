package service;

import dao.*;
import model.UserActivity;

import java.util.List;
import java.util.stream.Stream;

/**
 * Service for actions with UserActivity
 *
 * @Author Yuliia Aleksandrova
 */
public class TaskService {
    UserActivityRepository dao;

    private static TaskService instance = new TaskService();

    private TaskService() {
        RepositoryFactory repositoryFactory =
                new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
        dao = repositoryFactory.userActivityRepository();
    }

    public static TaskService getInstance() {
        return instance;
    }

    public List<UserActivity> getTasksByActivityByProgress(int activityId, String progress) {
        return dao.findTasksByActivityByProgress(activityId, progress);
    }

    public List<UserActivity> getTasksByUserId(int user_id) {
        return dao.findTasksByUserId(user_id);
    }

    public boolean finishTask(int taskId, int hoursSpent) throws ServiceException {
        try {
            return dao.finishTask(taskId, hoursSpent);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }

    public Stream<UserActivity> getAllTasksStream() {
        return dao.findAllTasks().stream();
    }
}

