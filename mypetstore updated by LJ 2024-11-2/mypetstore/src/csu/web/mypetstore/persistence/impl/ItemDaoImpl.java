package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.domain.Product;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.ItemDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDaoImpl implements ItemDao {

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
            Connection connection= DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(update_quantity);
            String itemId=param.keySet().iterator().next();
            Integer increment =(Integer) param.get(itemId);
            preparedStatement.setInt(1,increment.intValue());
            preparedStatement.setString(2,itemId);
            preparedStatement.executeUpdate();
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInventoryQuantity(String itemId) {
        int result=-1;
        try{
            Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(inventory_Quantity);
            preparedStatement.setString(1,itemId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=resultSet.getInt(1);
                DBUtil.closeConnection(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList=new ArrayList<Item>();
        try {
            Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(item_list);
            preparedStatement.setString(1,productId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                Item Item=new Item();
                Item.setItemId(resultSet.getString(1));
                Item.setListPrice(resultSet.getBigDecimal(2));
                Item.setUnitCost(resultSet.getBigDecimal(3));
                Item.setSupplierId(resultSet.getInt(4));
                Product product=new Product();
                product.setProductId(resultSet.getString(5));
                product.setName(resultSet.getString(6));
                product.setDescription(resultSet.getString(7));
                product.setCategoryId(resultSet.getString(8));
                Item.setProduct(product);
                Item.setStatus(resultSet.getString(9));
                Item.setAttribute1(resultSet.getString(10));
                Item.setAttribute2(resultSet.getString(11));
                Item.setAttribute3(resultSet.getString(12));
                Item.setAttribute4(resultSet.getString(13));
                Item.setAttribute5(resultSet.getString(14));
                itemList.add(Item);
            }
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return itemList;
    }

    /*getItem未完成*/
    @Override
    public Item getItem(String var1) {
        Item item=null;
        try {
            Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(items);

            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }
}
