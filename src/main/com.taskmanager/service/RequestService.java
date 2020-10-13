package service;

import dao.*;
import model.Request;

import java.util.List;

public class RequestService {

    RequestRepository dao ;

    private static RequestService instance = new RequestService();

    private RequestService() {
        RepositoryFactory repositoryFactory =
                new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
        dao = repositoryFactory.requestRepository();
    }

    public static RequestService getInstance() {
        return instance;
    }

    public   int getNumberOfCreatedRequests() {
        return dao.getNumRequestsCreated();
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
        return dao.getNumRequests();
    }

    public List<Request> getAllRequestsPaged(int limit, int page) {
        return dao.findAllRequestsPaged(limit, page);
    }
}
