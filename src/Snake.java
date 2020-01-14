import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Snake extends JLabel {
	
	int DOT_SIZE = 10;
	int ALL_DOT = 900;
	int x[] = new int[ALL_DOT];
	int y[] = new int[ALL_DOT];
	
	int dots;
	
	Image body;
	Image head;
	
	int forbidden_direction = 0;
	
	public Snake() {
		loadImages();
		
		dots = 1;
		
		for (int i = 0; i < dots; i++) {
			x[i] = 50 - i * 10;
			y[i] = 50;
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		doDrawing(g);
	}
	
	public void doDrawing(Graphics g) {
		for (int i = 0; i < dots; i++) {
			if(i == 0) g.drawImage(head, x[i], y[i], this);
			else g.drawImage(body, x[i], y[i], this);
		}
	}
	
	public boolean move(int direction) {
		boolean isValid = true;
		
		if(direction == forbidden_direction) {
			isValid = false;
			forbidden_direction = direction;
			if(direction == 4){
				direction -=2;
			}
			else if(direction == 1){
				direction +=2;
			}
			else if(direction == 2){
				direction +=2;
			}
			else if(direction == 3){
				direction -=2;
			}
			
			update(direction);
		}
		switch(direction) {
		case 1:
			update(direction);
			forbidden_direction = 2;
			break;
		case 2:
			update(direction);
			forbidden_direction = 1;
			break;
		case 3:
			update(direction);
			forbidden_direction = 4;
			break;
		case 4:
			update(direction);
			forbidden_direction = 3;
			break;		
		}
		
		return true;
	}

	public void update(int direction) {
		for (int i = dots; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 1:
			x[0] += DOT_SIZE;
			break;
		case 2:
			x[0] -= DOT_SIZE;
			break;
		case 3:
			y[0] += DOT_SIZE;
			break;
		case 4:
			y[0] -= DOT_SIZE;
			break;		
		}
	}
	
	public void clone(Snake s) {
		dots = s.dots;
		for (int i = dots; i >= 0; i--) {
			x[i] = s.x[i];
			y[i] = s.y[i];
		}
	}
	
	public boolean checkCollision(int height, int width) {
		for (int i = dots; i > 0; i--) {
			if(x[0] == x[i] && y[0] == y[i]) return false;
		}
		
		if(y[0] >= height) return false;
		if(y[0] < 0) return false;
		if(x[0] >= width) return false;
		if(x[0] < 0) return false;
		
		return true;
	}
	
	public boolean isBody(int mx, int my) {
		for(int i = dots; i > 0; i--) {
			if(x[i] == mx && y[i] == my) return true;
		}
		
		return false;
	}
	
	public boolean isNotOver() {
		
		if(isBody(x[0] - 10, y[0]) && isBody(x[0] + 10, y[0]) && isBody(x[0], y[0] - 10) && isBody(x[0], y[0] + 10)) return false;
		
		return true;
	}
	
	boolean food_intersect(int mx, int my) {
		for(int i = dots; i >= 0; i--) {
			if(x[i] == mx && y[i] == my) return true;
		}
		
		return false;
	  }
	
	private void loadImages() {
		ImageIcon imageHead = new ImageIcon(getClass().getResource("/res/Head.png"));
		head = imageHead.getImage();
		ImageIcon imageBody = new ImageIcon(getClass().getResource("/res/Body.png"));
		body = imageBody.getImage();
	}
}
