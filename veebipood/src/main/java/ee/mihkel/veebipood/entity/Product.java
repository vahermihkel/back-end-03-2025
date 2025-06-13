package ee.mihkel.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3, 4, 5...
    private Long id;
    private String name;
    private double price;
    private boolean active;
    private String image;

    //parempoolne tähistab kas mul on siin tegemist Listiga või mitte
    // @OneToMany          paremal Many -> List
    // @ManyToMany          paremal Many -> List
    // @OneToOne            paremal One -> Üks Entity
    // vasakpoolne tähistab kas ma saan mitmel seda kasutada. kas seos võib korduda.
    // @OneToMany ---> näide: ühel isikul mitu aadressi, mida teistele ei panda
    // @OneToOne ---> näide: tootel koostisosad, mida teistele ei panda
    // @ManyToMany ---> näide: tellimused tooted, mida pannakse ta teistele tellimustele
    @ManyToOne
    private Category category;
}
