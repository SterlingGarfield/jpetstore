package persistence;

import domain.item;

import java.util.List;
import java.util.Map;

public interface itemDAO {
    void updateInventoryQuantity(Map<String, Object> var1);

    int getInventoryQuantity(String var1);

    List<item> getItemListByProduct(String var1);

    item getItem(String var1);
}
