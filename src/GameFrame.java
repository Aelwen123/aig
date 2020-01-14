import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public GameFrame() {
		add(new Board());
		setResizable(false);
		pack();
		
		setTitle("Snake");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
