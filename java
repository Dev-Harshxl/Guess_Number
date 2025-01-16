public static void main(String[] args) {
    try {
        Class.forName("com.mysql.jdbc.Driver");  // Corrected syntax
        String url = "jdbc:mysql://localhost:3306/cart";
        String username = "root";
        String password = "Asdas@12";
        
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
        
        Scanner val = new Scanner(System.in);
        String table = val.nextLine();  // Input table name
        
        String sql = "CREATE TABLE " + table + " (Id INT NOT NULL, Item VARCHAR(255), PRIMARY KEY(Item))";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();  // Use executeUpdate for DDL statements
        
        System.out.println("Table created successfully");
    } catch (Exception e) {
        System.out.println("nope");
        e.printStackTrace();
    }
}
