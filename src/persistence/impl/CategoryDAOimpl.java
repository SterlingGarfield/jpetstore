package persistence.impl;

import domain.Category;
import persistence.CategoryDAO;
import persistence.DButil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryDAOimpl implements CategoryDAO {
    private static final String GET_CATRGORY_LIST=
            "SELECT CATID AS categoryId,DESCN AS description FROM CATEGORY";
    private static final String GET_CATRGORY=
        "SELECT CATID AS categoryId, NAME, DESCN AS description FROM CATEGORY WHERE CATID = ?;";
    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        try{
            Connection connection= DButil.getConnection();
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(GET_CATRGORY_LIST);
            while(resultSet.next()){
                Category category= new Category();
                category.setCategoryId(resultSet.getString("categoryId"));
                category.setName(resultSet.getString("NAME"));
                category.setDescription(resultSet.getString("description"));
                categoryList.add(category);
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    @Override
    public Category getCategory(String categoryId) {
        Category category=null;
        try{
            Connection connection= DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(GET_CATRGORY);
            preparedStatement.setString(1,categoryId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                category= new Category();
                category.setCategoryId(resultSet.getString("categoryId"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }
}
