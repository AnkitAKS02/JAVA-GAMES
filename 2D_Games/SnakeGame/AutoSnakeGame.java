import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class AutoSnakeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int DELAY = 150;

    private javax.swing.Timer timer;
    private Point prey;
    private Snake snake1, snake2;
    private Random random = new Random();

    static class Snake {
        LinkedList<Point> body = new LinkedList<>();
        Color color;
        int score = 0;

        public Snake(Point start, Color color) {
            body.add(start);
            this.color = color;
        }

        void move(Point prey, List<Point> otherSnakeBody) {
            Point head = body.getFirst();
            int tx = prey.x - head.x;
            int ty = prey.y - head.y;

            int nextX = head.x + Integer.signum(tx);
            int nextY = head.y + Integer.signum(ty);

            Point nextPos = new Point(nextX, nextY);

            // Avoid self and other snake collisions
            if (body.contains(nextPos) || otherSnakeBody.contains(nextPos)
                || nextX < 0 || nextX >= WIDTH / UNIT_SIZE || nextY < 0 || nextY >= HEIGHT / UNIT_SIZE) {

                List<Point> options = Arrays.asList(
                    new Point(head.x + 1, head.y),
                    new Point(head.x - 1, head.y),
                    new Point(head.x, head.y + 1),
                    new Point(head.x, head.y - 1)
                );
                Collections.shuffle(options);

                for (Point p : options) {
                    if (p.x >= 0 && p.x < WIDTH / UNIT_SIZE && p.y >= 0 && p.y < HEIGHT / UNIT_SIZE
                        && !body.contains(p) && !otherSnakeBody.contains(p)) {
                        nextPos = p;
                        break;
                    }
                }
            }

            body.addFirst(nextPos);

            if (nextPos.equals(prey)) {
                score += 10;
            } else {
                body.removeLast();
            }
        }

        boolean collidesWith(Snake other) {
            Point head = body.getFirst();
            return other.body.contains(head) || body.subList(1, body.size()).contains(head);
        }
    }

    public AutoSnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        snake1 = new Snake(new Point(5, 5), Color.GREEN);
        snake2 = new Snake(new Point(WIDTH / UNIT_SIZE - 6, HEIGHT / UNIT_SIZE - 6), Color.BLUE);

        spawnPrey();

        timer = new javax.swing.Timer(DELAY, this);
        timer.start();
    }

    private void spawnPrey() {
        prey = new Point(random.nextInt(WIDTH / UNIT_SIZE), random.nextInt(HEIGHT / UNIT_SIZE));
        while (snake1.body.contains(prey) || snake2.body.contains(prey)) {
            prey = new Point(random.nextInt(WIDTH / UNIT_SIZE), random.nextInt(HEIGHT / UNIT_SIZE));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake1.move(prey, snake2.body);
        snake2.move(prey, snake1.body);

        if (snake1.body.getFirst().equals(prey) || snake2.body.getFirst().equals(prey)) {
            spawnPrey();
        }

        if (snake1.collidesWith(snake2) || snake2.collidesWith(snake1)
            || snake1.body.getFirst().equals(snake2.body.getFirst())) {
            timer.stop();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw prey
        g.setColor(Color.RED);
        g.fillOval(prey.x * UNIT_SIZE, prey.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

        // Draw snakes
        g.setColor(snake1.color);
        for (Point p : snake1.body) {
            g.fillRect(p.x * UNIT_SIZE, p.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        g.setColor(snake2.color);
        for (Point p : snake2.body) {
            g.fillRect(p.x * UNIT_SIZE, p.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        // Display scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Snake 1 (Green): " + snake1.score, 10, 20);
        g.drawString("Snake 2 (Blue): " + snake2.score, WIDTH - 180, 20);

        // Display Game Over message
        if (!timer.isRunning()) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Autonomous Competitive Snake Game");
        AutoSnakeGame gamePanel = new AutoSnakeGame();
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
