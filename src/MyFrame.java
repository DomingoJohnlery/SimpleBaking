import javax.swing.*;

public class MyFrame extends JFrame {
    MyFrame() {
        this.setTitle("Simple Banking");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(720,620);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ImageIcon image = new ImageIcon("bank.png");
        this.setIconImage(image.getImage());

    }
}
