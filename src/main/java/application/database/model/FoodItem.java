package application.database.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name= "foodItem")
 public class FoodItem implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "foodItem")
    private Set<History> history;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "expireDate")
    private LocalDate expireDate;

}