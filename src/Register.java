import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

        btnSignIn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Login();
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
        btnRegister.addActionListener(e -> {
            String fName = tfFirst.getText();
            String lName = tfLast.getText();
            String username = tfUsername.getText();
            String password = String.valueOf(tfPassword.getPassword());

            dispose();
            new Login();
            Toast.makeToast(Register.this,"Registered Successfully!",3);
        });
    }

}
