package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.models.Product;
import ru.akirakozov.sd.refactoring.printers.HTMLProductPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends ProductServlet {

    public GetProductsServlet(ProductDAO productDAO) {
        super(productDAO);
    }
    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HTMLProductPrinter productPrinter = new HTMLProductPrinter(response.getWriter());
        productPrinter.printProductsList(productDAO.getAll());
    }
}
