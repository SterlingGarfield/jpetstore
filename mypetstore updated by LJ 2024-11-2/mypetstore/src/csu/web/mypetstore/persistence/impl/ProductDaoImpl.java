package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final String getProductListByCategoryString =
            "SELECT PRODUCTID, NAME, DESCN as description, CATEGORY as categoryId FROM PRODUCT WHERE CATEGORY = #{value}";
    private static final String getProductString =
            "SELECT PRODUCTID, NAME, DESCN as description, CATEGORY as categoryId FROM PRODUCT WHERE PRODUCTID = ?";
    private static final String searchProductListString =
            "select PRODUCTID, NAME, DESCN as description, CATEGORY as categoryId from PRODUCT WHERE lower(name) like #{value}";

    @Override
    public List<Product> getProductListByCategory(String categoryId) {
        List<Product> products = new ArrayList<Product>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection
                    .prepareStatement(getProductListByCategoryString);
            pStatement.setString(1, categoryId);
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getString(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setCategoryId(resultSet.getString(4));
                products.add(product);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    // 678开非vggybhnuhunjm
    @Override
    public Product getProduct(String productId) {
        Product product = null;
        try{
            Connection connection = DBUtil.getConnection();
        }
    }

    @Override
    public List<Product> searchProductList(String keywords) {
        return List.of();
    }
}
