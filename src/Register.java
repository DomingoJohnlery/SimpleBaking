import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.time.LocalDate;

public class Register extends JFrame {
    private JPanel registerPanel;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnRegister;
    private JLabel btnSignIn;
    private JTextField tfFirst;
    private JTextField tfLast;

    public Register() {
        ImageIcon image = new ImageIcon("src/bank.png");
        setIconImage(image.getImage());
        setContentPane(registerPanel);
        setTitle("Simple Banking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(740,520);
        setLocationRelativeTo(null);
        setVisible(true);

        btnRegister.addActionListener(e -> {
            String fName = tfFirst.getText();
            String lName = tfLast.getText();
            String username = tfUsername.getText();
            String password = String.valueOf(tfPassword.getPassword());

            if (fName.isBlank() || lName.isBlank() || username.isBlank() || password.isBlank()) {
                Toast.makeToast(Register.this,"Please Fill All The Fields!",3);
            } else if (checkUser(username)){
                Toast.makeToast(Register.this,"Username Already Exist!",3);
            } else {
                if (register(username,password,fName,lName,0)){
                    dispose();
                    new Login();
                    Toast.makeToast(Register.this,"Registered Successfully!",3);
                } else {
                    Toast.makeToast(Register.this,"Registration Failed!",3);
                }
            }
        });
        btnSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new Login();
            }
        });
    }

    private boolean register(String username, String password, String firstname, String lastname, int balance) {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "INSERT INTO users (username, pass, firstname,lastname,balance,opening) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, firstname);
            stmt.setString(4, lastname);
            stmt.setInt(5, balance);
            stmt.setDate(6, Date.valueOf(LocalDate.now()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean checkUser(String username) {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

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
}
