package PackMan;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PackMan extends JPanel{

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

        // void updateDirection(char direction) {
        //     char prevDirection = this.direction;
        //     this.direction = direction;
        //     updateVelocity();
        //     this.x += this.velocityX;
        //     this.y += this.velocityY;
        //     for (Block wall : walls) {
        //         if (collision(this, wall)) {
        //             this.x -= this.velocityX;
        //             this.y -= this.velocityY;
        //             this.direction = prevDirection;
        //             updateVelocity();
        //         }
        //     }
        // }

        // void updateVelocity() {
        //     if (this.direction == 'U') {
        //         this.velocityX = 0;
        //         this.velocityY = -tileSize/4;
        //     }
        //     else if (this.direction == 'D') {
        //         this.velocityX = 0;
        //         this.velocityY = tileSize/4;
        //     }
        //     else if (this.direction == 'L') {
        //         this.velocityX = -tileSize/4;
        //         this.velocityY = 0;
        //     }
        //     else if (this.direction == 'R') {
        //         this.velocityX = tileSize/4;
        //         this.velocityY = 0;
        //     }
        // }

        // void reset() {
        //     this.x = this.startX;
        //     this.y = this.startY;
        // }
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



    PackMan(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.black);

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
    }
}