package org.vedruna.reservenow.dto;

import org.vedruna.reservenow.persistance.model.TableEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TableDTO {
    private Long tableId;
    private int tableNumber;
    private boolean available;
    private String restaurantName; 

    public TableDTO(TableEntity table) {
        this.tableId = table.getTableId();
        this.tableNumber = table.getTableNumber();
        this.available = table.isAvailable();
        this.restaurantName = table.getRestaurant().getRestaurantName(); // Accede al campo correcto
    }
}
