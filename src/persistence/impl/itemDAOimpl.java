package persistence.impl;

import domain.item;
import domain.product;
import persistence.DButil;
import persistence.itemDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class itemDAOimpl implements itemDAO {
    private static final String update_quantity = """
    SELECT I.ITEMID, LISTPRICE, UNITCOST,
           SUPPLIER AS supplierId,
           I.PRODUCTID AS "product.productId",
           NAME AS "product.name",
           DESCN AS "product.description",
           CATEGORY AS "product.categoryId",
           STATUS,
           ATTR1 AS attribute1,
           ATTR2 AS attribute2,
           ATTR3 AS attribute3,
           ATTR4 AS attribute4,
           ATTR5 AS attribute5
    FROM ITEM I, PRODUCT P
    WHERE P.PRODUCTID = I.PRODUCTID
    AND I.PRODUCTID = ?""";
    private static final String inventory_Quantity="""
    select
      I.ITEMID,
      LISTPRICE,
      UNITCOST,
      SUPPLIER AS supplierId,
      I.PRODUCTID AS "product.productId",
      NAME AS "product.name",
      DESCN AS "product.description",
      CATEGORY AS "product.categoryId",
      STATUS,
      ATTR1 AS attribute1,
      ATTR2 AS attribute2,
      ATTR3 AS attribute3,
      ATTR4 AS attribute4,
      ATTR5 AS attribute5,
      QTY AS quantity
    from ITEM I, INVENTORY V, PRODUCT P
    where P.PRODUCTID = I.PRODUCTID
      and I.ITEMID = V.ITEMID
      and I.ITEMID = ?
""";
    private static final String item_list="""
    SELECT QTY AS value
    FROM INVENTORY
    WHERE ITEMID = ?
""";
    private static final String items= """
            
            UPDATE INVENTORY SET
                  QTY = QTY - ?
                WHERE ITEMID = ?
           """;

    @Override
    public void updateInventoryQuantity(Map<String, Object> param) {
        try {
            Connection connection= DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(update_quantity);
            String itemId=param.keySet().iterator().next();
            Integer increment =(Integer) param.get(itemId);
            preparedStatement.setInt(1,increment.intValue());
            preparedStatement.executeUpdate();
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInventoryQuantity(String itemId) {
        int result=-1;
        try{
            Connection connection=DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(inventory_Quantity);
            preparedStatement.setString(1,itemId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=resultSet.getInt(1);
                DButil.closeAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<item> getItemListByProduct(String productId) {
        List<item> itemList=new ArrayList<item>();
        try {
            Connection connection=DButil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(item_list);
            preparedStatement.setString(1,productId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                item Item=new item();
                Item.setItemId(resultSet.getString(1));
                Item.setListPrice(resultSet.getBigDecimal(2));
                Item.setUnitCost(resultSet.getBigDecimal(3));
                Item.setSupplierId(resultSet.getInt(4));
                product Product=new product();
                Product.setProductId(resultSet.getString(5));
                Product.setName(resultSet.getString(6));
                Product.setDescription(resultSet.getString(7));
                Product.setCategoryId(resultSet.getString(8));
                Item.setProduct(Product);
                Item.setStatus(resultSet.getString(9));
                Item.setAttribute1(resultSet.getString(10));
                Item.setAttribute2(resultSet.getString(11));
                Item.setAttribute3(resultSet.getString(12));
                Item.setAttribute4(resultSet.getString(13));
                Item.setAttribute5(resultSet.getString(14));
                itemList.add(Item);
            }
            DButil.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return itemList;
    }

    /*getItem未完成*/
    @Override
    public item getItem(String var1) {
        item Item = null;

        try {
            Connection connection = DButil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(items);

            preparedStatement.setString(1,var1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Item = new item();
                Item.setItemId(resultSet.getString(1));
                Item.setUnitCost(resultSet.getBigDecimal(3));
                Item.setSupplierId(resultSet.getInt(4));
                product Product = new product();
                Product.setProductId(resultSet.getString(5));
                Product.setName(resultSet.getString(6));
                Product.setDescription(resultSet.getString(7));
                Product.setCategoryId(resultSet.getString(8));
                Item.setProduct(Product);
                Item.setStatus(resultSet.getString(9));
                Item.setAttribute1(resultSet.getString(10));
                Item.setAttribute2(resultSet.getString(11));
                Item.setAttribute3(resultSet.getString(12));
                Item.setAttribute4(resultSet.getString(13));
                Item.setAttribute5(resultSet.getString(14));
                Item.setQuantity(resultSet.getInt(15));
            }

            DButil.closeAll();

        }catch (Exception e){
            e.printStackTrace();
        }

        return Item;
    }
}
