package ee.mihkel.veebipood.controller;

import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;
import ee.mihkel.veebipood.entity.Order;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.model.EveryPayLink;
import ee.mihkel.veebipood.repository.OrderRepository;
import ee.mihkel.veebipood.repository.PersonRepository;
import ee.mihkel.veebipood.service.OrderService;
import ee.mihkel.veebipood.util.EstonianPersonalCodeValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
public class OrderController {

//    @Autowired
//    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

//    @Autowired
//    EstonianPersonalCodeValidator validator;

//    private final EstonianPersonalCodeValidator validator = new EstonianPersonalCodeValidator();

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getPersonOrders();
    }

    @PostMapping("/orders")
    public EveryPayLink addOrder(@RequestBody List<Product> products) {
        Order order = orderService.saveOrder(products);
        return orderService.makePayment(order.getId(), order.getTotalSum());
    }

    // Controlleris ei tohiks olla ühtegi funktsiooni, millel pole @GetMappingut/@PostMapping jne
}
