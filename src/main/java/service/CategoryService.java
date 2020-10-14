package service;

import dao.CategoryRepository;
import dao.DaoException;
import dao.DataSourceFactory;
import dao.RepositoryFactory;
import model.Activity;
import model.Category;

import java.util.List;
import java.util.Optional;

public class CategoryService {

    CategoryRepository dao;

    private static CategoryService instance = new CategoryService();

    private CategoryService() {
        RepositoryFactory repositoryFactory =
                new RepositoryFactory(DataSourceFactory.getMySqlDatasource());
        dao = repositoryFactory.categoryRepository();
    }

    public static CategoryService getInstance() {
        return instance;
    }

    public List<Category> getAllCategories() {
        return dao.findAllCategories();
    }

    public Optional<Category> getCategoryById(int id) {
        return dao.findCategoryById(id);
    }

    public Category createCategory(Category category) throws ServiceException {
        try {
            return dao.createCategory(category).orElseThrow(() -> new ServiceException("Can not create category"));
        } catch (DaoException e) {
            throw new ServiceException("Can not create category");
        }
    }

}
