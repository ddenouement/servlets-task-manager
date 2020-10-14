package dao;

import model.Category;

import javax.sql.DataSource;
/**
 * Repository that can return multiple Repositories
 * @Author Yuliia Aleksandrova
 */
public class RepositoryFactory {

    private DataSource ds;

    public RepositoryFactory(DataSource dataSource) {
        this.ds = dataSource;
        init();
    }

    private UserRepository USER_REPOSITORY;
    private CategoryRepository CATEGORY_REPOSITORY;
    private UserActivityRepository USER_ACTIVTITY_REPOSITORY;
    private RequestRepository REQUEST_REPOSITORY;
    private ActivityRepository ACTIVITY_REPOSITORY;

    private void init() {
        USER_REPOSITORY =
                new UserRepository(ds);
        USER_ACTIVTITY_REPOSITORY =
                new UserActivityRepository(ds);
        REQUEST_REPOSITORY =
                new RequestRepository(ds);
        ACTIVITY_REPOSITORY =
                new ActivityRepository(ds);
        CATEGORY_REPOSITORY =
                new CategoryRepository(ds);

    }

    public UserRepository userRepository() {
        return USER_REPOSITORY;
    }

    public CategoryRepository categoryRepository() {
        return CATEGORY_REPOSITORY;
    }

    public UserActivityRepository userActivityRepository() {
        return USER_ACTIVTITY_REPOSITORY;
    }

    public RequestRepository requestRepository() {
        return REQUEST_REPOSITORY;
    }

    public ActivityRepository activityRepository() {
        return ACTIVITY_REPOSITORY;
    }


}
