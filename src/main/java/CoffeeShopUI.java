
package ui;

import dao.ScenarioDao;
import model.CoffeeOrder;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CoffeeShopUI {

    private final Scanner sc = new Scanner(System.in);
    private final ScenarioDao dao = new ScenarioDao();

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("       COFFEE SHOP ORDERS SYSTEM");
            System.out.println("========================================");
            System.out.println("[1] Add New Order");
            System.out.println("[2] View Active Orders");
            System.out.println("[3] Exit System");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addOrder();
                    case "2" -> viewOrders();
                    case "3" -> {
                        running = false;
                        System.out.println("System closed. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private void addOrder() throws SQLException {
        System.out.println("\n--- ADD NEW ORDER ---");

        System.out.print("Customer Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Drink Item: ");
        String drink = sc.nextLine().trim();

        if (name.isEmpty() || drink.isEmpty()) {
            System.out.println("Name and drink cannot be empty.");
            return;
        }

        double price;

        try {
            System.out.print("Price: ");
            price = Double.parseDouble(sc.nextLine().trim());

            if (!Double.isFinite(price) || price < 0) {
                System.out.println("Enter a valid non-negative price.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        System.out.print("Status (PENDING/PREPARING/COMPLETED): ");
        String status = sc.nextLine().trim().toUpperCase();

        if (!status.equals("PENDING")
                && !status.equals("PREPARING")
                && !status.equals("COMPLETED")) {
            System.out.println("Invalid status.");
            return;
        }

        dao.addOrder(name, drink, price, status);
        System.out.println("SUCCESS: Order added to queue!");
    }

    private void viewOrders() throws SQLException {
        List<CoffeeOrder> orders = dao.getAllOrders();

        System.out.println("\n--- ACTIVE COFFEE ORDERS ---");
        System.out.printf("%-4s | %-20s | %-20s | %10s | %-12s%n",
                "ID", "CUSTOMER NAME", "DRINK ITEM", "PRICE", "STATUS");

        System.out.println("-".repeat(80));

        double total = 0;

        for (CoffeeOrder order : orders) {
            System.out.printf("%-4d | %-20.20s | %-20.20s | %10.2f | %-12s%n",
                    order.id(),
                    order.customerName(),
                    order.drinkItem(),
                    order.price(),
                    order.status());

            total += order.price();
        }

        System.out.println("-".repeat(80));
        System.out.println("Total Records: " + orders.size());
        System.out.printf("Total Order Value: PHP %.2f%n", total);
    }
}
