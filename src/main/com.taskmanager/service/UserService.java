package service;

import dao.DaoException;
import dao.DataSourceFactory;
import dao.RepositoryFactory;
import dao.UserRepository;
import model.User;
import model.UserActivity;

import java.util.List;
import java.util.Optional;


public class UserService {

    UserRepository dao ;//= UserRepository.getInstance();

    private static UserService instance = new UserService();
    private UserService(){
        RepositoryFactory repositoryFactory =
                new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
        dao = repositoryFactory.userRepository();
    }
    public static UserService getInstance(){return instance;}

    public   int getCountOfUsers() {
        return dao.getNumberOfAllUsers();
    }

    public User register (User u) throws ServiceException {

        User registered ;
        try {
             registered = dao.register(u).orElseThrow(()->
                     new ServiceException("Wrong input"));
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
        return registered;
    }
    public List<User> findAllUsers(){
        return dao.findAllUsers();
    }

    public Optional<User> findUserById(int id){
        return dao.findUserById(id);
    }

    public Optional<User> findUserByLogin(String login){
        return dao.findUserByLogin(login);
    }

    public User login(String login, String password) throws ServiceException {
        return dao.authorizeByPasswordAndLogin(login,password).orElseThrow(() -> new ServiceException("Invalid Credentials"));
    }

}
