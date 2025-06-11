package org.vedruna.reservenow.persistance.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tables")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(nullable = false)
    private int tableNumber;

    @Column(nullable = false)
    private boolean available;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName; // 🔥 Ahora se almacena en la BD
}
