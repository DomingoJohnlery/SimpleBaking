import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JPanel loginPanel;
    private JLabel btnSignUp;
    private DBHelper DB;

    public Login() {
        DB = new DBHelper();

        ImageIcon image = new ImageIcon("src/image/bank.png");
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

            if (username.isBlank() || password.isBlank()) {
                Toast.makeToast(Login.this,"Please Fill All The Fields!",3);
            } else if (DB.checkLoginInfo(username,password)) {
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
}
