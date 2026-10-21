
package model;

public record CoffeeOrder(
        int id,
        String customerName,
        String drinkItem,
        double price,
        String status
) {}
