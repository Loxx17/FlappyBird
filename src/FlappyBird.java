import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //Imagens
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //Bird Class
    int birdX = boardWidth/8;
    int birdY = boardWidth/2;
    int birdWidth = 34;
    int birdHeight = 24;

    // Criar Bird 

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;  //scaled by 1/6
    int pipeHeight = 512;
    
    // Classe Barreiras
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    class Pipe2 {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe2(Image img) {
            this.img = img;
        }
    }

    // Logica do Jogo
    Bird bird;
    int velocityX = -4; // velocidade do passarinho
    int velocityY = 0; // move passaro pra cima e pra baixo
    int gravity = 1; // gravidade 
    double bestScore;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0;

    // JOGO
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Passaro 
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();;

        //place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
     
              placePipes();
            }
        });
        placePipeTimer.start();
        
		// Tempo do Jogo
		gameLoop = new Timer(1000/60, this);
        gameLoop.start();
	}
    
    void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
    
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
    
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y  + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
        
        if(score<=5) {
            // Carregar as Imagens 
             backgroundImg = new ImageIcon(getClass().getResource("./flbgf1.png")).getImage();
             birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
             topPipeImg = new ImageIcon(getClass().getResource("./tpf1.png")).getImage();
             bottomPipeImg = new ImageIcon(getClass().getResource("./btpf1.png")).getImage();

            // Fase 1
            g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);
            g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
            }
        } else if (score <=7) {
            Image backgroundImg2;
            Image birdImg2;
        
            backgroundImg2 = new ImageIcon(getClass().getResource("./flbgf2.jpeg")).getImage();
            birdImg2 = new ImageIcon(getClass().getResource("./flappybird2.png")).getImage();
            topPipeImg = new ImageIcon(getClass().getResource("./btpf2.JPG")).getImage();
            bottomPipeImg = new ImageIcon(getClass().getResource("./tpf2.JPG")).getImage();

            // Fase 2
            g.drawImage(backgroundImg2, 0, 0, this.boardWidth, this.boardHeight, null);
            g.drawImage(birdImg2, bird.x, bird.y, bird.width, bird.height, null);

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
            }

            velocityX = -7;
        } else if (score <=10) {
            Image backgroundImg3;
            Image birdImg3;
        
            backgroundImg3 = new ImageIcon(getClass().getResource("./bg3.png")).getImage();
            birdImg3 = new ImageIcon(getClass().getResource("./flappybird3.png")).getImage();
            topPipeImg = new ImageIcon(getClass().getResource("./flbtf3.png")).getImage();
            bottomPipeImg = new ImageIcon(getClass().getResource("./fltpf1.png")).getImage();

            // Fase 3
            g.drawImage(backgroundImg3, 0, 0, this.boardWidth, this.boardHeight, null);
            g.drawImage(birdImg3, bird.x, bird.y, bird.width, bird.height, null);

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
            }

            velocityX = -10;
        } else if (score == 50) {
            // tela de quando ganha o jogo

            
        }
        // Pontos
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
            if(bestScore < score) {
                bestScore = score;
                g.drawString("Best Score: " + String.valueOf((int) bestScore), 10, 70);
            } else {
                g.drawString("Best Score: " + String.valueOf((int) bestScore), 10, 70);
            }
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
        
	}

    public void move() {
        // passaros
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); 
        // barreiras
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; 
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   
               a.x + a.width > b.x &&   
               a.y < b.y + b.height &&  
               a.y + a.height > b.y;   
    }

    // Evento quando tem colisao (game over)

    @Override
    public void actionPerformed(ActionEvent e) { 
        move();
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }  
    
    // Evento do click do espaco do teclado para pular

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
        
            velocityY = -9;

            if (gameOver) {
                velocityX = -4;
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
