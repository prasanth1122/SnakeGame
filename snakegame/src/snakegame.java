import java.awt.EventQueue;
import javax.swing.JFrame;
//Main Snake class
public class snakegame extends JFrame {
    public static board B;
    public snakegame() {

        initUI();
    }

    private void initUI() {
        B = new board();
        add(B);

        setResizable(false);
        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new snakegame();
            ex.setVisible(true);

        });
    }
}
