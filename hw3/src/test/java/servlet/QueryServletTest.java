package servlet;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryServletTest extends BaseServletTest {
    private void checkResponse(HttpResponse<String> response, StringBuilder expectedBody) {
        assertEquals(
                new StringBuilder("<html><body>\n").
                        append(expectedBody).
                        append("</body></html>\n").toString(),
                response.body());
    }

    @Test
    public void testSumPrice() throws Exception {
        StringBuilder expectedBody = new StringBuilder();
        expectedBody.append("Summary price: \n0\n");

        HttpResponse<String> response = doQuery("sum");
        checkResponse(response, expectedBody);
    }

    @Test
    public void testUnknownCommand() throws Exception {
        String expected = "Unknown command: abcdef\n";

        HttpResponse<String> response = doQuery("abcdef");
        assertEquals(expected, response.body());
    }

    @Test
    public void testMaxPrice() throws Exception {
        StringBuilder expectedBody = new StringBuilder();
        expectedBody.append("<h1>Product with max price: </h1>\n");
        expectedBody.append("iPhone11\t50000</br>\n");

        assertEquals("OK\n", addProduct(new Product("iPhone11", 50000)).body());
        assertEquals("OK\n", addProduct(new Product("iPad", 35000)).body());
        HttpResponse<String> response = doQuery("max");
        checkResponse(response, expectedBody);
    }

    @Test
    public void testMinPrice() throws Exception {
        StringBuilder expectedBody = new StringBuilder();
        expectedBody.append("<h1>Product with min price: </h1>\n");
        expectedBody.append("iPad\t35000</br>\n");

        assertEquals("OK\n", addProduct(new Product("iPhone11", 50000)).body());
        assertEquals("OK\n", addProduct(new Product("iPad", 35000)).body());
        HttpResponse<String> response = doQuery("min");
        checkResponse(response, expectedBody);
    }

    @Test
    public void testCount() throws Exception {
        StringBuilder expectedBody = new StringBuilder();
        expectedBody.append("Number of products: \n0\n");
        HttpResponse<String> response = doQuery("count");
        checkResponse(response, expectedBody);
    }
}