import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    String username;
    DBHelper DB;

    public Main(String username) {
        DB = new DBHelper();
        this.username = username;
        ImageIcon image = new ImageIcon("src/image/bank.png");
        setIconImage(image.getImage());
        setContentPane(mainPanel);
        setTitle("Simple Banking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(740,520);
        setLocationRelativeTo(null);
        setVisible(true);

        updateDisplay();
        dashBoardButtons();
        accountInfoButtons();
        depositButtons();
        withdrawButtons();
    }
    private void updateDisplay() {
        UserModel user = DB.getUserInfo(username);

        NumberFormat phFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        phFormatter.setCurrency(Currency.getInstance("PHP"));
        String currPHP = phFormatter.format(DB.getBalance(username));

        tvName.setText(user.getFirstname() + " " + user.getLastname());
        tvAccNo.setText(user.getAccountNo());
        tvOpenDate.setText(user.getOpeningDate());
        tvInfoBalance.setText(currPHP);
        tvDepositBalance.setText("Balance: " + currPHP);
        tvWithBalance.setText("Balance: " + currPHP);
        tvBalance.setText("Balance: " + currPHP);
    }
    private void dashBoardButtons() {
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
        btnLogout.addActionListener(e -> {
            dispose();
            new Login();
        });
    }
    private void accountInfoButtons() {
        btnInfoBack.addActionListener(e -> {
            infoPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
    }
    private void depositButtons() {
        btnDepositBack.addActionListener(e -> {
            tfDeposit.setText("");
            depositPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
        btnDeposit.addActionListener(e -> {
            String deposit = tfDeposit.getText().replaceAll("[,_ ]","");
            if (deposit.isBlank()) {
                Toast.makeToast(Main.this,"Please Enter Deposit Value!",3);
            } else {
                if (deposit.matches("\\d+(\\.\\d+)?") && !deposit.matches("0+(\\.0+)?")) {
                    if (JOptionPane.showConfirmDialog(null,"Do you wish to proceed?","Deposit Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                        if (DB.deposit(username, Double.parseDouble(deposit))) {
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
    }
    private void withdrawButtons() {
        btnWithdrawBack.addActionListener(e -> {
            tfWithdraw.setText("");
            withdrawPanel.setVisible(false);
            dashPanel.setVisible(true);
        });
        btnWithdraw.addActionListener(e -> {
            String withdraw = tfWithdraw.getText().replaceAll("[,_ ]","");
            if (withdraw.isBlank()) {
                Toast.makeToast(Main.this,"Please Enter Withdrawal Value!",3);
            } else if (DB.getBalance(username) < Double.parseDouble(withdraw)) {
                Toast.makeToast(Main.this,"Insufficient Balance!",3);
            }
            else {
                if (withdraw.matches("\\d+(\\.\\d+)?") && !withdraw.matches("0+(\\.0+)?")) {
                    if (JOptionPane.showConfirmDialog(null,"Do you wish to proceed?","Withdrawal Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                        if (DB.withdraw(username, Double.parseDouble(withdraw))){
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
}
