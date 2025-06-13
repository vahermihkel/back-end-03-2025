package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

// Nii Order kui ka User tabeli nimetused on hõivatud

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SequenceGenerator(name = "order_seq", allocationSize = 1, initialValue = 5131200)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    private Long id; // genereeritakse ise
    private Date created; // ei küsi front-endilt - anname ise
    private double totalSum; // saame arvutada toodetelt

    @ManyToOne
    private Person person; // hiljem saame võtta Auth tokenist

    @ManyToMany
    private List<Product> products;
}
