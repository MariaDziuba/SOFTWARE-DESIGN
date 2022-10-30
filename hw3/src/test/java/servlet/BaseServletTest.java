package servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static ru.akirakozov.sd.refactoring.database.SQLiteDatabase.*;

public class BaseServletTest {
    private static final int PORT = 8081;
    private static final Server server = new Server(PORT);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ProductDAO productDao = new ProductDAO();

    protected static class Product {
        String name;
        int price;

        Product(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }

    @BeforeAll
    protected static void init() throws Exception {
        createTables();

        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDao)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDao)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDao)), "/query");
        server.start();
    }

    @AfterEach
    protected void clear() throws Exception {
        deleteTables();
    }

    @AfterAll
    protected static void finish() throws Exception {
        dropTables();
        server.stop();
    }

    public HttpResponse<String> request(String query) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + PORT + query)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> addProduct(Product product) throws Exception {
        return request("/add-product?name=" + product.name + "&price=" + product.price);
    }

    protected HttpResponse<String> doQuery(String command) throws Exception {
        return request("/query?command=" + command);
    }
}
