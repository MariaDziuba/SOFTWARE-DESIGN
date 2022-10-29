package servlet;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProductServletTest extends BaseServletTest {
    @Test
    public void testAddProduct() throws Exception {
        HttpResponse<String> response = addProduct(new Product("iPhone11", 50000));
        assertEquals("OK\n", response.body());
    }
}
