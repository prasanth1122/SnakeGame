import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
//This defines the board
public class board extends JPanel implements ActionListener {
    //Declare all dimensions related to Board panel
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
    private final int DOT_SIZE = 10;
    private final int TOTAL_DOTS = 1600;
    private final int RANDOM_POSITION = 39;
    private final int DELAY = 120;

    //Declaring position of each dots of Snake

    private final int x[] = new int[TOTAL_DOTS];
    private final int y[] = new int[TOTAL_DOTS];

    //Declaring Snake size and Apple positions
    public int dots;
    private int apple_x;
    private int apple_y;

    //Declaring snake direction
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    //Declaring inGame
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public board() {

        initBoard();
    }

    //Initialize Board Panel
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    //Load images of dots, head and Apple
    private void loadImages() {

        ImageIcon BALL_IMAGE = new ImageIcon("src/resources/dot.png");
        ball = BALL_IMAGE.getImage();

        ImageIcon APPLE_IMAGE = new ImageIcon("src/resources/apple.png");
        apple = APPLE_IMAGE.getImage();

        ImageIcon HEAD_IMAGE = new ImageIcon("src/resources/head.png");
        head = HEAD_IMAGE.getImage();
    }

    //Initialize Game
    private void initGame() {

        dots = 3;

        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int j = 0; j < dots; j++) {
                if (j == 0) {
                    g.drawImage(head, x[j], y[j], this);
                } else {
                    g.drawImage(ball, x[j], y[j], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        int score = (dots-3)*100;
        String scoremsg = "\nScore: "+ Integer.toString(score);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2)-10);
        g.drawString(scoremsg, (B_WIDTH - metr.stringWidth(scoremsg)) / 2,(B_HEIGHT / 2)+10 );
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    //Checks collision of head with any obstacle
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    //Randomize Apple position every time
    private void locateApple() {

        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}