package persistence.impl;

import domain.item;
import persistence.itemDAO;

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
                  QTY = QTY - #{increment}
                WHERE ITEMID = #{itemId}
           """;

    @Override
    public void updateInventoryQuantity(Map<String, Object> var1) {

    }

    @Override
    public int getInventoryQuantity(String var1) {
        return 0;
    }

    @Override
    public List<item> getItemListByProduct(String var1) {
        return null;
    }

    @Override
    public item getItem(String var1) {
        return null;
    }
}
