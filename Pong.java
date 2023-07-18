import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends JFrame implements KeyListener {

    // Constants
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 60;
    private static final int BALL_SIZE = 15;
    private static final int PADDLE_SPEED = 10;
    private static final int BALL_SPEED = 4;

    // Instance variables
    private int paddle1Y;
    private int paddle2Y;
    private int ballX;
    private int ballY;
    private int ballXSpeed;
    private int ballYSpeed;
    private int scorePlayer1;
    private int scorePlayer2;

    public Pong() {
        // Initialize game window and variables
        setTitle("Pong Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);

        // Center the game window on the screen
        setLocationRelativeTo(null);

        // Initialize paddles and ball position
        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = BALL_SPEED;
        ballYSpeed = BALL_SPEED;
        scorePlayer1 = 0;
        scorePlayer2 = 0;
    }

    // Paddle Movement Methods
    public void movePaddle1Up() {
        paddle1Y -= PADDLE_SPEED;
        if (paddle1Y < 0) {
            paddle1Y = 0;
        }
    }

    public void movePaddle1Down() {
        paddle1Y += PADDLE_SPEED;
        if (paddle1Y > HEIGHT - PADDLE_HEIGHT) {
            paddle1Y = HEIGHT - PADDLE_HEIGHT;
        }
    }

    public void movePaddle2Up() {
        paddle2Y -= PADDLE_SPEED;
        if (paddle2Y < 0) {
            paddle2Y = 0;
        }
    }

    public void movePaddle2Down() {
        paddle2Y += PADDLE_SPEED;
        if (paddle2Y > HEIGHT - PADDLE_HEIGHT) {
            paddle2Y = HEIGHT - PADDLE_HEIGHT;
        }
    }

    
    // Ball Movement and Collision Detection 
    public void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Check collision with paddles
        if (ballX <= PADDLE_WIDTH && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = BALL_SPEED;
        } else if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -BALL_SPEED;
        }

        // Check collision with walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }

        // Check if ball is out of bounds
        if (ballX < 0 || ballX > WIDTH - BALL_SIZE) {
            resetGame();
        }
    }

    // Rendering the Game
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw the score and player names
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Player 1: " + scorePlayer1, WIDTH / 4, 30);
        g.drawString("Player 2: " + scorePlayer2, WIDTH / 2 + WIDTH / 4, 30);

        // Draw the paddles and ball
        g.setColor(Color.WHITE);
        g.fillRect(PADDLE_WIDTH, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 2 * PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        Toolkit.getDefaultToolkit().sync();
    }


    // KeyListener Methods 
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Control Player 1 (Left-side paddle)
        if (key == KeyEvent.VK_W) {
            movePaddle1Up();
        } else if (key == KeyEvent.VK_S) {
            movePaddle1Down();
        }

        // Control Player 2 (Right-side paddle)
        if (key == KeyEvent.VK_UP) {
            movePaddle2Up();
        } else if (key == KeyEvent.VK_DOWN) {
            movePaddle2Down();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }


    // Reset the Game and Score
    public void resetGame() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = BALL_SPEED;
        ballYSpeed = BALL_SPEED;

        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;

        // Reset the scores to 0
        scorePlayer1 = 0;
        scorePlayer2 = 0;

        // Repaint the game window
        repaint();
    }

    public static void main(String[] args) {
        Pong game = new Pong();
        game.setVisible(true);

        // Start the game loop
        while (true) {
            game.moveBall();
            game.repaint();

            try {
                Thread.sleep(20); // A small delay between each iteration to control the game speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
