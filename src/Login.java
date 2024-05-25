import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Login extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JPanel loginPanel;
    private JLabel btnSignUp;

    public Login() {
        ImageIcon image = new ImageIcon("src/bank.png");
        setIconImage(image.getImage());
        setContentPane(loginPanel);
        setTitle("Simple Banking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(740,520);
        setLocationRelativeTo(null);
        setVisible(true);
        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = String.valueOf(tfPassword.getPassword());

            if (checkLoginInfo(username,password)) {
                dispose();
                new Main(username);
                Toast.makeToast(Login.this,"Login Successfully!",3);
            } else {
                Toast.makeToast(Login.this,"Incorrect Login Info!",3);
            }


        });

        btnSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new Register();
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }

    private boolean checkLoginInfo(String username,String password) {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

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

}
