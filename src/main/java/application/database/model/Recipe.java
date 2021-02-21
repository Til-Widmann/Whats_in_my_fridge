package application.database.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients;

}
