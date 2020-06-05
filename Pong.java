import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.Random;
import java.util.Timer;

public class Pong extends Canvas {
    Point delta;
    Ellipse2D.Double ball;
    Rectangle paddle1;
    // Instantiate a second Rectangle paddle
    Rectangle paddle2;
    // int rand = (int) Math.random() * 5;
    // int rand2 = (int) Math.random() * -5;
    int player1 = 0;
    int player2 = 0;

    public static void main(String[] args) {
        JFrame win = new JFrame("Pong");
        win.setSize(1010, 735);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(new Pong());
        win.setVisible(true);
    }

    public Pong() {
        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
        requestFocus();

        ball = new Ellipse2D.Double(500, 350, 20, 20);
        delta = new Point(5, -5);

        paddle1 = new Rectangle(50, 250, 20, 200);
        // Create second paddle coordinates and width height
        paddle2 = new Rectangle(920, 250, 20, 200);

        Timer t = new Timer(true);
        t.schedule(new java.util.TimerTask() {
            public void run() {
                doStuff();
                repaint();
            }
        }, 20, 20);

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.fill(ball);

        g2.setColor(Color.blue);
        g2.fill(paddle1);

        // Setting paddle 2 color
        g2.setColor(Color.green);
        g2.fill(paddle2);

        // Creating Scoreboard for each side/player
        g2.setColor(Color.black);
        g2.setFont(new Font("Helvetica", 1, 25));
        // converts Integer(score) to a string for draw method
        g2.drawString("Player 1 Score: " + Integer.toString(player1), 50, 100);
        g2.drawString("Player 2 Score: " + Integer.toString(player2), 700, 100);

    }

    public void processKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                paddle1.y -= 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                paddle1.y += 10;
            }
        }
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                paddle2.y -= 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                paddle2.y += 10;
            }
        }
    }

    public void doStuff() {
        ball.x += delta.x;
        ball.y += delta.y;

        // and bounce if we hit a wall
        if (ball.y < 0 || ball.y + 20 > 700)
            delta.y = -delta.y;
        if (ball.x < 0)
            delta.x = -delta.x;

        // check if the ball is hitting the paddle1
        if (ball.intersects(paddle1))
            delta.x = -delta.x;
        // check if the ball is hitting the paddle2
        if (ball.intersects(paddle2))
            delta.x = -delta.x;

        // check for scoring
        if (ball.x > 1000) {
            ball.x = 500;
            ball.y = 350;
            delta = new Point(-5, 5);
            player1++;
        }
        if (ball.x < 0) {
            ball.x = 500;
            ball.y = 350;
            delta = new Point(5, -5);
            player2++;
        }

    }

    public boolean isFocusable() {
        return true;
    }
}