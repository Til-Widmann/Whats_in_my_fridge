package application.database.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private int amount;

}
