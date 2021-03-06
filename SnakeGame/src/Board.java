import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

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

public class Board extends JPanel implements ActionListener {
	//Timer
    private Timer timer;
    
    //Images
    private Image dot;
    private Image apple;
    private Image head;
	
	//integers
	//Constants
	private final int BOARD_WIDTH = 300;
	private final int BOARD_HEIGHT = 300;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int DELAY = 135;
	
	//Dots
	private final int[] x = new int[ALL_DOTS];
	private final int[] y = new int[ALL_DOTS];
	
	//Apple pos
	private int dots;
	private int appleXPos;
	private int appleYPos;
	
	//Booleans
	//Directions and game continuity
	private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean gameOn = true;
    
    public Board() {
    	addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    	setBackground(Color.black);
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    
    public void loadImages(){
    	ImageIcon iiDot = new ImageIcon("dots.png");
    	dot = iiDot.getImage();
    	
    	ImageIcon iiApple = new ImageIcon("apple.png");
    	apple = iiApple.getImage();
    	
    	ImageIcon iiHead = new ImageIcon("head.png");
    	head = iiHead.getImage();
    }
    
    public void initGame(){
    	timer = new Timer(DELAY, this);
        timer.start();
        
    	dots = 3;
    	
    	for(int i = 0;i < dots;i++){
    		x[i] = 50 - (i * 10);
    		y[i] = 50;
    	}
    	
    	findApple();
    }
    
    public void findApple(){
    	Random rg = new Random();
    	
    	int aux = rg.nextInt(139) % RAND_POS;
    	appleXPos = aux * DOT_SIZE;
    	
    	aux = rg.nextInt(139) % RAND_POS;
    	appleYPos = aux * DOT_SIZE;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (gameOn) {

            g.drawImage(apple, appleXPos, appleYPos, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                    
                } else {
                    g.drawImage(dot, x[z], y[z], this);
                    
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }
    
    public void gameOver(Graphics g){
    	Font gOver = new Font("Arial", Font.BOLD, 20);
    	FontMetrics metric = getFontMetrics(gOver);
    	
    	String gameOver = "GAME OVER";
    	
    	g.setFont(gOver);
    	g.setColor(Color.WHITE);
    	g.drawString(gameOver, (BOARD_WIDTH - metric.stringWidth(gameOver)) / 2, BOARD_HEIGHT / 2);
    }
    
    private void checkApple(){
    	
    	if ((x[0] == appleXPos) && (y[0] == appleYPos)) {

             dots++;
             
             findApple();
         }
    }
    
    private void move(){
    	for (int i = dots; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
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
    
    private void collisorCheck(){
    	
    	for (int i = dots; i > 0; i--) {

            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                gameOn = false;
            }
            
        }

        if (y[0] >= BOARD_HEIGHT) {
            gameOn = false;
        }

        if (y[0] < 0) {
            gameOn = false;
        }

        if (x[0] >= BOARD_WIDTH) {
            gameOn = false;
        }

        if (x[0] < 0) {
            gameOn = false;
        }
        
        if(!gameOn) {
            timer.stop();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameOn) {

            checkApple();
            collisorCheck();
            move();
        }

        repaint();
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {


            if ((e.getKeyCode() == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((e.getKeyCode() == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
