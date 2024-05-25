import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class Main extends JFrame{
    private JPanel mainPanel;
    private JPanel dashPanel;
    private JLabel btnInfo;
    private JLabel btnDeposit;
    private JLabel btnWithdraw;
    private JButton btnLogout;
    private JLabel tvBalance;
    private JPanel infoPanel;
    private JPanel depositPanel;
    private JPanel withdrawPanel;

    public Main(String username) {
        String balance = getBalance(username);

        ImageIcon image = new ImageIcon("src/bank.png");
        setIconImage(image.getImage());
        setContentPane(mainPanel);
        setTitle("Simple Banking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(740,520);
        setLocationRelativeTo(null);
        setVisible(true);

        tvBalance.setText(balance);
        btnLogout.addActionListener(e -> {
            dispose();
            new Login();
        });
        btnInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                infoPanel.setVisible(true);
            }
        });

        btnDeposit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                depositPanel.setVisible(true);
            }
        });
        btnWithdraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                withdrawPanel.setVisible(true);
            }
        });
    }

    private String getBalance(String username) {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT balance FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return String.valueOf(rs.getInt("balance"));
            else return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
