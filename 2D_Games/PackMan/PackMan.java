package PackMan;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PackMan extends JPanel implements ActionListener,KeyListener{

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U'; // U D L R
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -cellSize/4;
            }
            else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = cellSize/4;
            }
            else if (this.direction == 'L') {
                this.velocityX = -cellSize/4;
                this.velocityY = 0;
            }
            else if (this.direction == 'R') {
                this.velocityX = cellSize/4;
                this.velocityY = 0;
            }
        }

        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    private int rowCount = 21;
    private int colCount = 19;
    private int cellSize = 32;//tile size
    private int boardWidth = colCount * cellSize;
    private int boardHeight = rowCount * cellSize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image orangeGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;

    private Image packmanUpImage;
    private Image packmanDownImage;
    private Image packmanLeftImage;
    private Image packmanRightImage;

    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block packman;
    Timer gameLoop;
    char[] directions = {'U','D','L','R'};
    Random random = new Random();
    int score = 0;
    int lives = 4;
    boolean gameOver = false;


    PackMan(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //load images
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();

        packmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        packmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        packmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        packmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();

        loadMap();
        for(Block ghost: ghosts){
            ghost.updateDirection(directions[random.nextInt(4)]);

        }
        gameLoop = new Timer(50,this);
        gameLoop.start();
        System.out.println("Map Loaded");
    }

    public void loadMap(){
         walls = new HashSet<Block>();
         foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for(int i = 0;i<rowCount;i++){
            for(int j = 0;j<colCount;j++){
                String row = tileMap[i];
                char tile = row.charAt(j);

                int x = j * cellSize;
                int y = i * cellSize;
                if(tile == 'X'){
                    Block wall = new Block(wallImage, x, y, cellSize, cellSize);
                    walls.add(wall);
                } else if(tile == ' '){//food storage
                    Block food = new Block(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                } else if(tile == 'b'){
                    Block blueGhost = new Block(blueGhostImage, x, y, cellSize, cellSize);
                    ghosts.add(blueGhost);
                } else if(tile == 'o'){
                    Block orangeGhost = new Block(orangeGhostImage, x, y, cellSize, cellSize);
                    ghosts.add(orangeGhost);
                } else if(tile == 'p'){
                    Block pinkGhost = new Block(pinkGhostImage, x, y, cellSize, cellSize);
                    ghosts.add(pinkGhost);
                } else if(tile == 'r'){
                    Block redGhost = new Block(redGhostImage, x, y, cellSize, cellSize);
                    ghosts.add(redGhost);
                } else if(tile == 'P'){
                    packman = new Block(packmanRightImage, x, y, cellSize, cellSize);
                }
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(packman.image, packman.x, packman.y, packman.width, packman.height, null);

        // Draw walls
        for(Block wall : walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        // Draw foods
        g.setColor(Color.YELLOW);
        for(Block food : foods){
            g.fillOval(food.x, food.y, food.width, food.height);
        }

        // Draw ghosts
        for(Block ghost : ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), cellSize/2, cellSize/2);
        }
        else {
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), cellSize/2, cellSize/2);
        }

        //draw game over message:
        if (gameOver) {
        g.drawString("Game Over: " + score, cellSize/2, cellSize/2);
        g.drawString("Press any key to play again", cellSize/2, cellSize);
        } else {
            g.drawString("x" + lives + " Score: " + score, cellSize/2, cellSize/2);
        }

    }

    public void move() {
        packman.x += packman.velocityX;
        packman.y += packman.velocityY;
        if (packman.x < 0) {
            packman.x = boardWidth - packman.width;
        } else if (packman.x + packman.width > boardWidth) {
            packman.x = 0;
        }
        //check wall collisions
        for (Block wall : walls) {
            if (collision(packman, wall)) {
                packman.x -= packman.velocityX;
                packman.y -= packman.velocityY;
                break;
            }
        }

        //check ghost collisions
        for (Block ghost : ghosts) {
            if (collision(ghost, packman)) {
                lives -= 1;
                if (lives == 0) {
                    gameOver = true;
                    return;
                }
                resetPositions();
            }

            if (ghost.y == cellSize*9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        //check food collision
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(packman, food)) {
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

        public boolean collision(Block a, Block b) {
            return  a.x < b.x + b.width &&
                    a.x + a.width > b.x &&
                    a.y < b.y + b.height &&
                    a.y + a.height > b.y;
        }

    public void resetPositions() {
        packman.reset();
        packman.velocityX = 0;
        packman.velocityY = 0;
        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            System.out.println("Game Over,Your Total Score is: " + score);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("left")
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            packman.updateDirection('U');
            packman.image = packmanUpImage;
        }  else if (key == KeyEvent.VK_DOWN) {
            packman.updateDirection('D');
            packman.image = packmanDownImage;
        } else if (key == KeyEvent.VK_LEFT) {
            packman.updateDirection('L');
            packman.image = packmanLeftImage;
        } else if (key == KeyEvent.VK_RIGHT) {
            packman.updateDirection('R');
            packman.image = packmanRightImage;
        }
    }
}