import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        setSize(720,520);
        setLocationRelativeTo(null);
        setVisible(true);
        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = String.valueOf(tfPassword.getPassword());

            Toast.makeToast(Login.this,"Login Successfully!",3);
        });
        btnSignUp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
