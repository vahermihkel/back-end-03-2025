package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.entity.Order;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.model.EveryPayBody;
import ee.mihkel.veebipood.model.EveryPayLink;
import ee.mihkel.veebipood.model.EveryPayResponse;
import ee.mihkel.veebipood.repository.OrderRepository;
import ee.mihkel.veebipood.repository.PersonRepository;
import ee.mihkel.veebipood.repository.ProductRepository;
import ee.mihkel.veebipood.util.EstonianPersonalCodeValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service // teeb beaniks, et teda saaks autowire'dada
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${everypay.url}")
    String everyPayUrl;

    @Value("${everypay.customer-url}")
    String customerUrl;

    @Value("${everypay.username}")
    String username;

    @Value("${everypay.password}")
    String password;

    public Order saveOrder(List<Product> products) {
        Order order = new Order(); //{id: 0, created: null, products: [], person: null, totalSum: 0}
        order.setCreated(new Date());//{id: 0, created: 2025-.., products: [], person: null, totalSum: 0}
        order.setProducts(products);//{id: 0, created: 2025-.., products: [{},{}], person: null, totalSum: 0}

        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //Person person = new Person(); // {id: 0, firstName: null, lastName: null}
        //person.setId(1L); // ID'st piisab, et siduda
        Person person = personRepository.findByEmail(email);
        order.setPerson(person);//{id: 0, created: 2025-.., products: [{},{}], person: {}, totalSum: 0}

        order.setTotalSum(calculateTotalSum(products));//{id: 0, created: 2025-.., products: [{},{}], person: {}, totalSum: 99}
        return orderRepository.save(order);//{id: 12, created: 2025-.., products: [{},{}], person: null, totalSum: 0}
    }

    private double calculateTotalSum(List<Product> products) {
        double sum = 0;
        for (Product product: products) {
            Product dbProduct = productRepository.findById(product.getId()).orElseThrow();
            sum += dbProduct.getPrice();
        }
        return sum;
    }


    public List<Order> getPersonOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        Person person = personRepository.findByEmail(email);
        return orderRepository.findAllByPerson_Email(email);
    }

    public EveryPayLink makePayment(Long id, double totalSum) {

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name("EUR3D1");
        body.setNonce("kfjf" + LocalDateTime.now() + id);
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setAmount(totalSum);
        body.setOrder_reference(id.toString());
        body.setCustomer_url(customerUrl);
        body.setApi_username(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username,password);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EveryPayBody> entity = new HttpEntity<>(body, headers);
        String paymentLink = restTemplate.exchange(everyPayUrl, HttpMethod.POST, entity, EveryPayResponse.class).getBody().getPayment_link();


        EveryPayLink link = new EveryPayLink();
        link.setUrl(paymentLink);
        return link;
    }
}
