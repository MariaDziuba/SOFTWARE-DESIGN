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
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends ProductServlet {

    public QueryServlet(ProductDAO productDAO) {
        super(productDAO);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String command = request.getParameter("command");
        PrintWriter printWriter = response.getWriter();
        HTMLProductPrinter productPrinter = new HTMLProductPrinter(printWriter);

        switch (command) {
            case "max": {
                Optional<Product> maxPriceProduct = productDAO.findMaxPrice();
                productPrinter.printProduct(maxPriceProduct, "Product with max price: ");
                printWriter.println("</body></html>");
            }
            case "min": {
                Optional<Product> minPriceProduct = productDAO.findMinPrice();
                printWriter.println("<html><body>");
                printWriter.println("<h1>Product with min price: </h1>");
                productPrinter.printProduct(minPriceProduct, "Product with min price: ");
                printWriter.println("</body></html>");
            }
            case "sum": {
                long sum = productDAO.getSum();
                productPrinter.print(sum, "Summary price: ");
            }
            case "count": {
                int count = productDAO.getCount();
                productPrinter.print(count, "Number of products: ");
            }
            default: {
                response.getWriter().println("Unknown command: " + command);
            }
        }
    }
}
