package service;

import dao.DaoException;
import dao.UserRepository;
import model.User;
import model.UserActivity;

import java.util.List;


public class UserService {

    UserRepository dao = UserRepository.getInstance();

    private static UserService instance = new UserService();
    private UserService(){

    }
    public static UserService getInstance(){return instance;}

    public User register (User u) throws ServiceException {

        User registered = null;
        try {
             registered = dao.register(u);
            if(registered == null)
                throw new ServiceException("Wrong input");
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
        return registered;
    }
    public List<User> findAllUsers(){
        return dao.findAllUsers();
    }

    public User findUserById(int id){
        return dao.findUserById(id);
    }

    public User findUserByLogin(String login){
        return dao.findUserByLogin(login);
    }

    public List<UserActivity> getTasksByActivityByProgress(int activityId, String progress) {
        return dao.findUsersByActivityByProgress(activityId,progress);
    }

    public List<UserActivity> getTasksByUserId(int user_id) {
        return  dao.findTasksByUserId(user_id);
    }

    public User login(String login, String password) {
        return dao.authorizeByPasswordAndLogin(login,password);
    }

    public boolean finishTask(int taskId, int hoursSpent) throws ServiceException {
        try {
            return dao.finishTask(taskId, hoursSpent);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }
}
