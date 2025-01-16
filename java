import java.sql.*;
import java.util.Scanner;

public class JDBCProgram {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "password"; // Replace with your password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS items ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) UNIQUE NOT NULL, "
                    + "is_deleted BOOLEAN DEFAULT FALSE)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Add data to table");
                System.out.println("2. Delete data from table");
                System.out.println("3. Recover deleted data");
                System.out.println("4. Show data");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter name to add: ");
                        String nameToAdd = scanner.nextLine();
                        addItem(conn, nameToAdd);
                        break;
                    case 2:
                        System.out.print("Enter name to delete: ");
                        String nameToDelete = scanner.nextLine();
                        deleteItem(conn, nameToDelete);
                        break;
                    case 3:
                        System.out.print("Enter name to recover: ");
                        String nameToRecover = scanner.nextLine();
                        recoverItem(conn, nameToRecover);
                        break;
                    case 4:
                        showData(conn);
                        break;
                    case 5:
                        System.out.println("Exiting program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(Connection conn, String name) {
        String insertSQL = "INSERT INTO items (name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Item added successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Duplicate entry. The item already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteItem(Connection conn, String name) {
        String updateSQL = "UPDATE items SET is_deleted = TRUE WHERE name = ? AND is_deleted = FALSE";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, name);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("Error: Item not found or already deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void recoverItem(Connection conn, String name) {
        String updateSQL = "UPDATE items SET is_deleted = FALSE WHERE name = ? AND is_deleted = TRUE";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, name);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item recovered successfully.");
            } else {
                System.out.println("Error: No deleted item found with this name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showData(Connection conn) {
        String selectSQL = "SELECT name, is_deleted FROM items";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("Items in table:");
            while (rs.next()) {
                String name = rs.getString("name");
                boolean isDeleted = rs.getBoolean("is_deleted");
                System.out.println(name + " (Deleted: " + isDeleted + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
