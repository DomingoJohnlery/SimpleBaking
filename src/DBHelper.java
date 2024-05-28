import java.sql.*;
import java.time.LocalDate;

public class DBHelper {
    private final String url = "jdbc:mysql://localhost:3306/java";
    private final String dbUser = "root";
    private final String dbPass = "johnlol0909";

    public boolean checkLoginInfo(String username,String password) {
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT * FROM users WHERE username = ? AND pass = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean checkUser(String username) {
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean register(UserModel userModel) {
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "INSERT INTO users (username, pass, firstname,lastname,balance,opening) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userModel.getUsername());
            stmt.setString(2, userModel.getPassword());
            stmt.setString(3, userModel.getFirstname());
            stmt.setString(4, userModel.getLastname());
            stmt.setInt(5, 0);
            stmt.setDate(6, Date.valueOf(LocalDate.now()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public double getBalance(String username) {
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT balance FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
            else return -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    public UserModel getUserInfo(String username) {
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT accountNo,firstname,lastname,opening FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String accountNo = String.valueOf(rs.getInt("accountNo"));
                Date openingDate = rs.getDate("opening");
                return new UserModel(firstname,lastname,accountNo,openingDate);
            }
            else return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public boolean deposit(String username, double amount){
        double balance = getBalance(username);

        if (balance == -1) {
            System.out.println("User not found or error occurred.");
            return false;
        }

        double newBalance = balance + amount;

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "UPDATE users SET balance = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, newBalance);
            stmt.setString(2, username);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean withdraw(String username, double amount) {
        double balance = getBalance(username);

        if (balance == -1) {
            System.out.println("User not found or error occurred.");
            return false;
        }

        double newBalance = balance - amount;

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "UPDATE users SET balance = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDouble(1, newBalance);
            stmt.setString(2, username);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
