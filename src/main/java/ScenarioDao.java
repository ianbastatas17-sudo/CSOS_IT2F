
package dao;

import model.CoffeeOrder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void addOrder(String name, String drink,
                         double price, String status)
            throws SQLException {

        String sql = """
                INSERT INTO coffee_orders
                (customer_name, drink_item, price, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, drink);
            ps.setDouble(3, price);
            ps.setString(4, status);
            ps.executeUpdate();
        }
    }

    public List<CoffeeOrder> getAllOrders()
            throws SQLException {

        List<CoffeeOrder> orders = new ArrayList<>();

        String sql = "SELECT * FROM coffee_orders ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new CoffeeOrder(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("drink_item"),
                        rs.getDouble("price"),
                        rs.getString("status")
                ));
            }
        }

        return orders;
    }
}
