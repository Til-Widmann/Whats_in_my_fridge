package application.database.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * @author Til-W
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name= "history")
public class History {
    @Id
    @Column(name = "id",unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fooditem_id")
    private FoodItem foodItem;

    @Column(name = "useTimeStamp")
    private  LocalDateTime useTimeStamp;

    @Column(name = "amount")
    private int amount;

    public History(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    @PrePersist
    void preInsert(){
        useTimeStamp = LocalDateTime.now();
        amount = foodItem.getAmount();
    }
}




