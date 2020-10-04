package service;

import dao.ActivityRepository;
import dao.DaoException;
import model.Activity;

import java.util.List;

public class ActivityService {
    ActivityRepository dao = ActivityRepository.getInstance();

    private static ActivityService instance = new ActivityService();
    private ActivityService(){

    }
    public static ActivityService getInstance(){return instance;}

    public List<Activity> getAllActivities(){return dao.findAllActivities();}

    public Activity getActivityById(int id){
        return dao.findActivityById(id);
    }


    public Activity createActivity(Activity activity) throws ServiceException {
         try {
            return dao.createActivity(activity);
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

    public List<Activity> getAllActivitiesSorted(String sortBy) {
        return dao.findActivitiesSorted(sortBy);
    }
}