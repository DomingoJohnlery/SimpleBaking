import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register extends JFrame {
    private JPanel registerPanel;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnRegister;
    private JLabel btnSignIn;
    private JTextField tfFirst;
    private JTextField tfLast;
    DBHelper DB;

    public Register() {
        DB = new DBHelper();

        ImageIcon image = new ImageIcon("src/image/bank.png");
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
            } else if (DB.checkUser(username)){
                Toast.makeToast(Register.this,"Username Already Exist!",3);
            } else {
                UserModel user = new UserModel(username,password,fName,lName);
                if (DB.register(user)){
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
}
