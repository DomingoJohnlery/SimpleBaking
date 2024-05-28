import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Toast extends JFrame {
    private final float MAX_OPACITY = 0.8f;
    private final float OPACITY_INCREMENT = 0.05f;
    private final int FADE_REFRESH_RATE = 20;

    public Toast(JFrame owner, String toastText) {
        setTitle("Transparent JFrame Demo");
        setLayout(new GridBagLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setFocusableWindowState(false);

        setOpacity(0.4f);

        // setup the toast lable
        JLabel b1 = new JLabel(toastText);
        b1.setForeground(Color.WHITE);
        b1.setOpaque(false);
        add(b1);

        int CHARACTER_LENGTH_MULTIPLIER = 9;
        int toastWidth = toastText.length() * CHARACTER_LENGTH_MULTIPLIER;
        setSize(toastWidth, 50);

        int x = (int) (owner.getLocation().getX() + (owner.getWidth() / 2) - (toastWidth / 2));
        int DISTANCE_FROM_PARENT_BOTTOM = 100;
        int y = (int) (owner.getLocation().getY() + owner.getHeight() - DISTANCE_FROM_PARENT_BOTTOM);
        setLocation(new Point(x, y));

        // configure frame
        int WINDOW_RADIUS = 15;
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
        getContentPane().setBackground(new Color(0, 0, 0, 170));

    }
    public void fadeIn() {
        setOpacity(0);
        setVisible(true);
        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += OPACITY_INCREMENT;
                setOpacity(Math.min(opacity, MAX_OPACITY));
                if (opacity >= MAX_OPACITY) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }


    public void fadeOut() {
        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = MAX_OPACITY;
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= OPACITY_INCREMENT;
                setOpacity(Math.max(opacity, 0));
                if (opacity <= 0) {
                    timer.stop();
                    setVisible(false);
                    dispose();
                }
            }
        });
        setOpacity(MAX_OPACITY);
        timer.start();
    }


    public static void makeToast(final JFrame owner, final String toastText, final int durationSec) {
        new Thread(() -> {
            try {
                Toast toastFrame = new Toast(owner, toastText);
                toastFrame.fadeIn();
                Thread.sleep(durationSec * 1000L);
                toastFrame.fadeOut();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}