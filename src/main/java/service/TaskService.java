package service;

import dao.DaoException;
import dao.UserActivityRepository;
import dao.UserRepository;
import javafx.concurrent.Task;
import model.UserActivity;

import java.util.List;

public class TaskService {
    UserActivityRepository dao = UserActivityRepository.getInstance();

    private static TaskService instance = new TaskService();
    private TaskService(){

    }
    public static TaskService getInstance(){return instance;}

    public List<UserActivity> getTasksByActivityByProgress(int activityId, String progress) {
        return dao.findTasksByActivityByProgress(activityId,progress);
    }

    public List<UserActivity> getTasksByUserId(int user_id) {
        return  dao.findTasksByUserId(user_id);
    }

    public boolean finishTask(int taskId, int hoursSpent) throws ServiceException {
        try {
            return dao.finishTask(taskId, hoursSpent);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }

}

