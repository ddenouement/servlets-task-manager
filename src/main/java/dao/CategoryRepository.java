package dao;

import model.Activity;
import model.Category;
import org.apache.logging.log4j.LogManager;
import util.QueriesCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository extends BaseRepository{
    final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(UserActivityRepository.class);

    public CategoryRepository(DataSource dataSource) {
        super(dataSource);
    }

    public Optional<Category> createCategory(Category  category) throws DaoException {
        Connection con = null;
        String generatedColumns[] = {"id"};
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            st = con.prepareStatement(QueriesCategory.SQL_CREATE_CATEGORY, generatedColumns);
            st.setString(1, category.getName());
            st.setString(2, category.getNameEn());
            st.setString(3, category.getNameUa());
            int i = st.executeUpdate();
            if (i > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    category.setId(id);
                }
                return Optional.of(category);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
            throw new DaoException("Error creating category");
        } finally {
            close(rs);
            close(st);
            close(con);
        }
        return Optional.empty();
    }


    public List<Category> findAllCategories() {
        List<Category> found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesCategory.SQL_FIND_ALL_CATEGORIES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (found == null) found = new ArrayList<>();
                found.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            logger.warn(ex.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return found;
    }

    public Optional<Category> findCategoryById(int id) {
        Category found = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(QueriesCategory.SQL_FIND_CATEGORY_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                found = mapRow(rs);
            }
        } catch (SQLException throwables) {
            logger.warn(throwables.getLocalizedMessage());
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return Optional.ofNullable(found);
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        try {
            Category activity = new Category();
            activity.setId(rs.getInt("id"));
            activity.setName(rs.getString("name"));
            activity.setNameEn(rs.getString("nameEn"));
            activity.setNameUa(rs.getString("nameUa"));
            return activity;
        } catch (SQLException e) {
            logger.warn(e.getLocalizedMessage());
            throw e;
        }
    }

}
