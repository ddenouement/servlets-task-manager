package service;

import dao.DaoException;
import dao.RequestRepository;
import dao.UserRepository;
import model.Request;

import java.util.List;

public class RequestService {

    RequestRepository dao = RequestRepository.getInstance();

    private static RequestService instance = new RequestService();

    private RequestService() {

    }

    public static RequestService getInstance() {
        return instance;
    }


    public List<Request> getRequestsByUserId(int id) {
        return dao.findAllRequestsByUserId(id);
    }
    public boolean dismissRequestById(int id) throws ServiceException {
        try {
            return dao.dismissRequestById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }
    public boolean acceptRequestById(int id) throws ServiceException {
        try {
            return dao.acceptRequestById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }

    public int createRemoveRequest(int taskId, int userId ) throws ServiceException {
        try {
            return dao.createRemoveRequest(taskId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }
    public int createAddRequest(int actId, int userId ) throws ServiceException {
        try {
            return dao.createAddRequest(actId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMsg());
        }
    }
    public int getNumRequests(){
        return RequestRepository.getInstance().getNumRequests();
    }

    public List<Request> getAllRequestsPaged(int limit, int page) {
        return RequestRepository.getInstance().findAllRequestsPaged(limit, page);
    }
}
