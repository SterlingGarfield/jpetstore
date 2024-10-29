package persistence;

import domain.product;

import java.io.Serializable;
import java.util.List;

public interface productDAO{
    List<product> getProductListByCategory(String var1);

    product getProduct(String var1);

    List<product> searchProductList(String var1);
}
