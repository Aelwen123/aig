import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {

	public Main() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame gameframe = new GameFrame();
				gameframe.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
