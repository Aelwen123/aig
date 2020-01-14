import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	int counter;
	Snake sneaky;
	
	private final int B_WIDTH = 500;
	private final int B_HEIGHT = 500;
	private final int RAND_POS = 29;
	private int DELAY = 100;
	private int count = 0;
	
	private int food_x;
	private int food_y;
	
	private Image food;
	
	private boolean inGame = true;
	
	private Timer timer;
	
	Snake snake;
	
	public Board() {
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGames();
	}
	
	private void initGames() {
		snake = new Snake();
		
		locateFood();
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		// TODO Auto-generated method stub
		if(inGame) {
			g.drawImage(food, food_x, food_y, this);
			
			snake.paint(g);
		} else {
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {
		// TODO Auto-generated method stub
		String msg = "Game Over";
		String msg2 = "Score : " + counter;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
		g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT);
	}
	
	private void checkFood() {
		if(snake.x[0] == food_x && snake.y[0] == food_y) {
			snake.dots++;
			locateFood();
			counter++;
			DELAY -= 10;
			if(DELAY <= 20) DELAY = 20;
			
			timer.setDelay(DELAY);
			count++;
			System.out.println(count);
		}
	}
	
	private void locateFood() {
		// TODO Auto-generated method stub
		do {
			int r = (int) (Math.random() * RAND_POS);
			food_x = r * snake.DOT_SIZE;
			r = (int) (Math.random() * RAND_POS);
			food_y = r * snake.DOT_SIZE;			
		} while(snake.food_intersect(food_x, food_y));
	}

	private void loadImages() {
		ImageIcon imageFood = new ImageIcon(getClass().getResource("/res/Food.png"));
		food = imageFood.getImage();
	}
	
	public void AI (Snake snake, int max) {
		int best = 0, dist = Integer.MAX_VALUE;
		for (int i = 1; i <= 4; i++) {
			
			Snake virtual = new Snake();
			virtual.clone(snake);
			
			if(virtual.move(i) && virtual.checkCollision(B_HEIGHT, B_WIDTH)) {
				int new_dist = Math.abs(virtual.x[0] - food_x) + Math.abs(virtual.y[0] - food_y);
				if(count > 20 && count < 60) {
					new_dist += AIs(virtual, max, i);
					System.out.println("30 ke 60");
				}
				if(count > 60) new_dist += AIs(virtual, max, i);
				if(new_dist <= dist){
					best = i; 
					dist = new_dist;    
		       }
		    }
	    }
		snake.move(best);
	}
	
	int AIs (Snake snake, int max, int i) {
		int dist = Integer.MAX_VALUE;
			
			Snake virtual = new Snake();
			virtual.clone(snake);
				int new_dist = Math.abs(virtual.x[0] - food_x) + Math.abs(virtual.y[0] - food_y);
				if(max > 1 && new_dist > 0) {
					System.out.println(max);
					new_dist += AIs(virtual, max-1, i);
				}
				
				if(new_dist <= dist){ 
					dist = new_dist;   
		       }
		return dist;
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(inGame) {
			AI(snake,4);
			checkFood();
			inGame = snake.isNotOver();
		}
		
		repaint();
	}

}
