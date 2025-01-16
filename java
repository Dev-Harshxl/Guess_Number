import java.sql.*;
import java.util.Scanner;

public class JDBCProgram {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "password"; // Replace with your password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS items ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) UNIQUE NOT NULL, "
                    + "is_deleted BOOLEAN DEFAULT FALSE)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }

            // Start program execution
            runProgram(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runProgram(Connection conn) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nChoose an option:");
        System.out.println("1. Add data to table");
        System.out.println("2. Delete data from table");
        System.out.println("3. Recover deleted data");
        System.out.println("4. Show non-deleted items");
        System.out.println("5. Exit");
        System.out.println("6. Show deleted items");

        try {
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);

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
                    showNonDeletedItems(conn);
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    return; // Exit the program
                case 6:
                    showDeletedItems(conn);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
        }

        // Call the function recursively for the next iteration
        runProgram(conn);
    }

    private static void addItem(Connection conn, String name) {
        String checkSQL = "SELECT is_deleted FROM items WHERE name = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                boolean isDeleted = rs.getBoolean("is_deleted");
                if (isDeleted) {
                    System.out.println("The item is present in the deleted list. Do you want to recover it? (yes/no)");
                    Scanner scanner = new Scanner(System.in);
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (choice.equals("yes")) {
                        recoverItem(conn, name);
                    } else {
                        System.out.println("Item was not recovered.");
                    }
                } else {
                    System.out.println("Error: Duplicate entry. The item already exists in the active list.");
                }
            } else {
                String insertSQL = "INSERT INTO items (name) VALUES (?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, name);
                    pstmt.executeUpdate();
                    System.out.println("Item added successfully.");
                }
            }
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

    private static void showNonDeletedItems(Connection conn) {
        String selectSQL = "SELECT name FROM items WHERE is_deleted = FALSE";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\nNon-Deleted Items:");
            boolean hasItems = false;
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println("- " + name);
                hasItems = true;
            }
            if (!hasItems) {
                System.out.println("No non-deleted items found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showDeletedItems(Connection conn) {
        String selectSQL = "SELECT name FROM items WHERE is_deleted = TRUE";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\nDeleted Items:");
            boolean hasItems = false;
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println("- " + name);
                hasItems = true;
            }
            if (!hasItems) {
                System.out.println("No deleted items found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
