package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.model.Supplier1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    RestTemplate restTemplate;

    public List<Supplier1> getSupplier1Products() {
        String url = "https://fakestoreapi.com/products";
//        RestTemplate restTemplate = new RestTemplate();
        Supplier1[] body = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1[].class).getBody();
        List<Supplier1> supplierProducts = List.of(body);
        supplierProducts = supplierProducts.stream()
                .filter(e -> e.getRating().getRate() > 2.5)
                .peek(e -> e.setRetailPrice(e.getPrice() * 1.2))
                .toList();
        return supplierProducts;
    }
}
