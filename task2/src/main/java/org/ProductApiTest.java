package org;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductApiTest {

    private static final String API_URL = "https://fakestoreapi.com/products";

    private static Product[] products;

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Status code is not 200");

        ObjectMapper mapper = new ObjectMapper();
        products = mapper.readValue(response.body(), Product[].class);
    }

    @Test
    public void testProductApi() {
        List<Product> defectiveProducts = new ArrayList<>();

        for (Product product : products) {
            boolean hasDefect = false;

            if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
                hasDefect = true;
            }

            if (product.getPrice() < 0) {
                hasDefect = true;
            }

            if (product.getRating() != null && product.getRating().getRate() > 5.0) {
                hasDefect = true;
            }

            if (hasDefect) {
                defectiveProducts.add(product);
            }
        }

        System.out.println("Total products: " + products.length);

        System.out.print("Defective products: ");

        if (defectiveProducts.isEmpty()) {
            System.out.println("0");
        } else {
            for (Product p : defectiveProducts) {
                System.out.printf("ID: %d, Title: \"%s\", Price: %.2f, Rate: %.2f%n",
                        p.getId(), p.getTitle(), p.getPrice(), p.getRating() != null ? p.getRating().getRate() : -1.0);
            }
        }

        assertTrue(defectiveProducts.isEmpty(), "Found defective products");
    }
}
