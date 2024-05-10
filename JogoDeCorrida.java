import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JogoDeCorrida extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int GROUND_Y = 300;

    private int playerY = GROUND_Y;
    private int playerHeight = 50;
    private boolean isJumping = false;
    private int jumpSpeed = -15;
    private int jumpHeight = 150;
    private int jumpCount = 0;

    private int obstacleX = WIDTH;
    private int obstacleY = GROUND_Y - 20;
    private int obstacleWidth = 20;
    private int obstacleHeight = 20;
    private int obstacleSpeed = 5;

    private Timer timer;

    public JogoDeCorrida() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                    isJumping = true;
                    jumpCount = 0;
                }
            }
        });
        setFocusable(true);
    }

    private void update() {
        if (isJumping) {
            playerY += jumpSpeed;
            jumpCount++;
            if (jumpCount >= jumpHeight) {
                isJumping = false;
            }
        } else {
            if (playerY < GROUND_Y) {
                playerY -= jumpSpeed;
                jumpCount--;
                if (jumpCount <= 0) {
                    playerY = GROUND_Y;
                }
            }
        }

        obstacleX -= obstacleSpeed;
        if (obstacleX + obstacleWidth < 0) {
            obstacleX = WIDTH;
        }

        if (playerY + playerHeight >= GROUND_Y && obstacleX < WIDTH / 2 && obstacleX + obstacleWidth > WIDTH / 2) {
            // Colisão com obstáculo no chão
            gameOver();
        }
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Desenhar jogador
        g2d.setColor(Color.BLUE);
        g2d.fillRect(WIDTH / 4, playerY, 50, playerHeight);

        // Desenhar obstáculo no chão
        g2d.setColor(Color.RED);
        g2d.fillRect(obstacleX, obstacleY, obstacleWidth, obstacleHeight);

        // Desenhar linha divisória
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, GROUND_Y, WIDTH, GROUND_Y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Jogo de Corrida");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new JogoDeCorrida());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
