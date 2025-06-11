package org.vedruna.reservenow.persistance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Column(nullable = false, unique = true)
    private String restaurantName;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String menuDetails;

    @Column(length = 50)
    private String priceRange;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<TableEntity> tables;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    
}

