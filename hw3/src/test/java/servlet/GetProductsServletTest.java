package servlet;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest extends BaseServletTest {
    private void checkResponse(HttpResponse<String> response, List<Product> productList) {
        StringBuilder s = new StringBuilder();
        s.append("<html><body>\n");
        for (Product product : productList) {
            s.append(product.name).append("\t").append(product.price).append("</br>\n");
        }
        s.append("</body></html>\n");
        assertEquals(s.toString(), response.body());
    }

    @Test
    public void testEmpty() throws Exception {
        HttpResponse<String> response = request("/get-products");
        checkResponse(response, new ArrayList<>());
    }

    @Test
    public void testGetProducts() throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("iPhone11", 50000));
        products.add(new Product("iPhone12ProMax", 100000));
        products.add(new Product("MacBookProM1", 150000));

        for (Product product : products) {
            assertEquals("OK\n", addProduct(product).body());
        }

        HttpResponse<String> response = request("/get-products");
        checkResponse(response, products);
    }
}
