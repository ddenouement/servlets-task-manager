package service;

import dao.ActivityRepository;
import dao.DaoException;
import dao.DataSourceFactory;
import dao.RepositoryFactory;
import model.Activity;

import java.util.List;
import java.util.Optional;

public class ActivityService {
    ActivityRepository dao;

    private static ActivityService instance = new ActivityService();

    private ActivityService() {
        RepositoryFactory repositoryFactory =
                new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
        dao = repositoryFactory.activityRepository();
    }

    public static ActivityService getInstance() {
        return instance;
    }

    public List<Activity> getAllActivities() {
        return dao.findAllActivities();
    }

    public Optional<Activity> getActivityById(int id) {
        return dao.findActivityById(id);
    }


    public Activity createActivity(Activity activity) throws ServiceException {
        try {
            return dao.createActivity(activity).orElseThrow(() -> new ServiceException("Can not create activity"));
        } catch (DaoException e) {
            throw new ServiceException("Can not create activity");
        }

    }

    public boolean checkAndUpdateActivity(Activity activity) throws ServiceException {
        try {
            return dao.updateActivity(activity);
        } catch (DaoException e) {
            throw new ServiceException("Can not update activity");
        }
    }

    public List<Activity> getAllActivitiesSortedPaged(String sortBy, int recordsPerPage, int page) {
        return dao.findActivitiesSortedPaged(sortBy, recordsPerPage, page);
    }

    public int getCountOfActivities() {
        return dao.getCountActivities();
    }

    public boolean deleteActivity(int activityId) throws ServiceException {
        try {
            return dao.deleteActivityCascade(activityId);
        } catch (DaoException e) {
            throw  new ServiceException(e.getMsg());
        }
    }
}
