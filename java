private static void addItem(Connection conn, String name) {
    String checkSQL = "SELECT is_deleted FROM items WHERE name = ?";
    try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
        checkStmt.setString(1, name);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            boolean isDeleted = rs.getBoolean("is_deleted");
            if (isDeleted) {
                // Item is in the deleted list
                System.out.println("The item is present in the deleted list. Do you want to recover it? (yes/no)");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("yes")) {
                    recoverItem(conn, name);
                } else {
                    System.out.println("Item was not recovered.");
                }
            } else {
                // Item is already active
                System.out.println("Error: Duplicate entry. The item already exists in the active list.");
            }
        } else {
            // Item does not exist; add it
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
