package persistence.impl;

import domain.product;
import persistence.DButil;
import persistence.productDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productDAOimpl implements productDAO {
    private static final String getProductListByCategoryString="SELECT PRODUCTID,NAME,DESCN as description,CATEGORY as categoryId FROM PRODUCT WHERE CATEGORY=?";
    private static final String getProductString="SELECT PRODUCTID,NAME,DESCN as description,CATEGORY as categoryId FROM PRODUCT WHERE CATEGORY=?";
    private static final String searchProductListString="SELECT PRODUCTID,NAME,DESCN as description,CATEGORY as categoryId FROM PRODUCT WHERE lower(name) like ?";

    @Override
    public List<product> getProductListByCategory(String categoryId) {
        List<product> products=new ArrayList<product>();
        try {
            Connection connection=DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(getProductListByCategoryString);
            preparedStatement.setString(1,categoryId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                product Product=new product();
                Product.setProductId(resultSet.getString(1));
                Product.setName(resultSet.getString(2));
                Product.setDescription(resultSet.getString(3));
                Product.setCategoryId(resultSet.getString(4));
                products.add(Product);
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public product getProduct(String productId) {
        product Product=null;
        try {
            Connection connection=DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(getProductString);
            preparedStatement.setString(1,productId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Product=new product();
                Product.setProductId(resultSet.getString(1));
                Product.setName(resultSet.getString(2));
                Product.setDescription(resultSet.getString(3));
                Product.setCategoryId(resultSet.getString(4));
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Product;
    }

    @Override
    public List<product> searchProductList(String keywords) {
        List<product> products=new ArrayList<product>();
        try {
            Connection connection=DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(searchProductListString);
            preparedStatement.setString(1,keywords);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                product Product=new product();
                Product.setProductId(resultSet.getString(1));
                Product.setName(resultSet.getString(2));
                Product.setDescription(resultSet.getString(3));
                Product.setCategoryId(resultSet.getString(4));
                products.add(Product);
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;    }
}
