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

    public   int getCountOfUsers() {
        return dao.getNumberOfAllUsers();
    }

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

    public User login(String login, String password) {
        return dao.authorizeByPasswordAndLogin(login,password);
    }

}
