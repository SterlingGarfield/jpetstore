package web.servlet;

import domain.item;
import domain.product;
import service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/productForm")
public class productFormServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String PRODUCT_FORM = "/WEB-INF/jsp/catalog/product.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId=req.getParameter("productId");
        catalogService=new CatalogService();
        product Product=catalogService.getProduct(productId);
        List<item> itemList=catalogService.getItemListByProduct(productId);
        HttpSession session=req.getSession();
        session.setAttribute("product",Product);
        session.setAttribute("itemList",itemList);
        req.getRequestDispatcher(PRODUCT_FORM).forward(req,resp);
    }
}
