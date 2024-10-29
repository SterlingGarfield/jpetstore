package service;

import domain.Category;
import domain.item;
import domain.product;
import persistence.CategoryDAO;
import persistence.impl.CategoryDAOimpl;
import persistence.impl.itemDAOimpl;
import persistence.impl.productDAOimpl;
import persistence.itemDAO;
import persistence.productDAO;

import java.util.List;

public class CatalogService {
    private CategoryDAO categoryDAO;
    private productDAO productDAO;
    private itemDAO itemDAO;
    public CatalogService(){
        this.categoryDAO=new CategoryDAOimpl();
        this.productDAO=new productDAOimpl();
        this.itemDAO=new itemDAOimpl();
    }
    public List<Category> getCategoryList() {
        return this.categoryDAO.getCategoryList();
    }

    public Category getCategory(String categoryId) {
        return this.categoryDAO.getCategory(categoryId);
    }

    public product getProduct(String productId) {
        return this.productDAO.getProduct(productId);
    }

    public List<product> getProductListByCategory(String categoryId) {
        return this.productDAO.getProductListByCategory(categoryId);
    }

    public List<product> searchProductList(String keyword) {
        return this.productDAO.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<item> getItemListByProduct(String productId) {
        return this.itemDAO.getItemListByProduct(productId);
    }

    public item getItem(String itemId) {
        return this.itemDAO.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return this.itemDAO.getInventoryQuantity(itemId) > 0;
    }

}
