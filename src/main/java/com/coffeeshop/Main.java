
import dao.DatabaseConnection;
import ui.CoffeeShopUI;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();

            CoffeeShopUI ui = new CoffeeShopUI();
            ui.start();

        } catch (SQLException e) {
            System.out.println(
                    "Unable to initialize database: "
                            + e.getMessage()
            );
        }
    }
}
