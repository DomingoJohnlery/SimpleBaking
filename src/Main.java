import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Main extends JFrame{
    private JPanel mainPanel;
    private JPanel dashPanel;
    private JLabel btnInfo;
    private JLabel btnToDeposit;
    private JLabel btnToWithdraw;
    private JButton btnLogout;
    private JLabel tvBalance;
    private JPanel infoPanel;
    private JPanel depositPanel;
    private JPanel withdrawPanel;
    private JButton btnDepositBack;
    private JButton btnWithdrawBack;
    private JButton btnInfoBack;
    private JLabel tvAccNo;
    private JLabel tvName;
    private JLabel tvInfoBalance;
    private JLabel tvOpenDate;
    private JLabel tvDepositBalance;
    private JTextField tfDeposit;
    private JButton btnDeposit;
    private JButton btnWithdraw;
    private JTextField tfWithdraw;
    private JLabel tvWithBalance;
    String accountNo;
    String username;
    String openingDate;

    public Main(String username) {
        this.username = username;

        ImageIcon image = new ImageIcon("src/bank.png");
        setIconImage(image.getImage());
        setContentPane(mainPanel);
        setTitle("Simple Banking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(740,520);
        setLocationRelativeTo(null);
        setVisible(true);

        updateDisplay();

        btnLogout.addActionListener(e -> {
            dispose();
            new Login();
        });
        btnInfoBack.addActionListener(e -> {
            infoPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
        btnDepositBack.addActionListener(e -> {
            tfDeposit.setText("");
            depositPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
        btnWithdrawBack.addActionListener(e -> {
            tfWithdraw.setText("");
            withdrawPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
        btnInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                infoPanel.setVisible(true);
            }
        });

        btnToDeposit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                depositPanel.setVisible(true);
            }
        });
        btnToWithdraw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dashPanel.setVisible(false);
                withdrawPanel.setVisible(true);
            }
        });
        btnDeposit.addActionListener(e -> {
            String deposit = tfDeposit.getText().replaceAll("[,_ ]","");
            if (deposit.isBlank()) {
                Toast.makeToast(Main.this,"Please Enter Deposit Value!",3);
            } else {
                if (deposit.matches("\\d+(\\.\\d+)?") && !deposit.matches("0+(\\.0+)?")) {
                    if (JOptionPane.showConfirmDialog(null,"Do you wish to proceed?","Deposit Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                        if (deposit(username, Double.parseDouble(deposit))) {
                            if (JOptionPane.showConfirmDialog(null,"Do you wish deposit again?","Transaction Successful!", JOptionPane.YES_NO_OPTION) == 1) {
                                depositPanel.setVisible(false);
                                dashPanel.setVisible(true);
                            }
                            updateDisplay();
                            tfDeposit.setText("");
                        }
                    }
                } else {
                    Toast.makeToast(Main.this,"Invalid Value, Try Again!",3);
                }
            }
        });
        btnWithdraw.addActionListener(e -> {
            String withdraw = tfWithdraw.getText().replaceAll("[,_ ]","");
            if (withdraw.isBlank()) {
                Toast.makeToast(Main.this,"Please Enter Withdrawal Value!",3);
            } else if (getBalance(username) < Double.parseDouble(withdraw)) {
                Toast.makeToast(Main.this,"Insufficient Balance!",3);
            }
            else {
                if (withdraw.matches("\\d+(\\.\\d+)?") && !withdraw.matches("0+(\\.0+)?")) {
                    if (JOptionPane.showConfirmDialog(null,"Do you wish to proceed?","Withdrawal Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                        if (withdraw(username, Double.parseDouble(withdraw))){
                            if (JOptionPane.showConfirmDialog(null,"Do you wish withdraw again?","Transaction Successful!", JOptionPane.YES_NO_OPTION) == 1) {
                                withdrawPanel.setVisible(false);
                                dashPanel.setVisible(true);
                            }
                            updateDisplay();
                            tfWithdraw.setText("");
                        }
                    }
                } else {
                    Toast.makeToast(Main.this,"Invalid Value, Try Again!",3);
                }
            }
        });
    }

    private double getBalance(String username) {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

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

    private String getFullName() {
        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "SELECT accountNo,firstname,lastname,opening FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                accountNo = String.valueOf(rs.getString("accountNo"));
                openingDate = String.valueOf(rs.getDate("opening"));
                return rs.getString("firstname") + " " + rs.getString("lastname");
            }
            else return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean deposit(String username, double amount){
        double balance = getBalance(username);

        if (balance == -1) {
            System.out.println("User not found or error occurred.");
            return false;
        }

        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

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

    private boolean withdraw(String username, double amount) {
        double balance = getBalance(username);

        if (balance == -1) {
            System.out.println("User not found or error occurred.");
            return false;
        }

        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPass = "johnlol0909";

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

    private void updateDisplay() {
        String name = getFullName();

        NumberFormat phFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        phFormatter.setCurrency(Currency.getInstance("PHP"));
        String currPHP = phFormatter.format(getBalance(username));

        tvName.setText(name);
        tvAccNo.setText(accountNo);
        tvOpenDate.setText(openingDate);
        tvInfoBalance.setText(currPHP);
        tvDepositBalance.setText("Balance: " + currPHP);
        tvWithBalance.setText("Balance: " + currPHP);
        tvBalance.setText("Balance: " + currPHP);
    }
}
