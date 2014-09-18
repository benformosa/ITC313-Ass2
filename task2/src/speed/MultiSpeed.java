package speed;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MultiSpeed extends JFrame {
	private SpeedometerPanel a = new SpeedometerPanel();
	private SpeedometerPanel b = new SpeedometerPanel();

	public static void main(String[] args) {
		JFrame frame = new MultiSpeed();
		frame.setTitle("Speedometers");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	private void processKeys() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() == KeyEvent.KEY_PRESSED) {
							if (e.getKeyCode() == KeyEvent.VK_Q) {
								System.exit(1);
							}
							handleKeyPress(e.getKeyCode());
						}
						return false;
					}
				});
	}

	public MultiSpeed() {
		this.add(a, BorderLayout.NORTH);
		this.add(b, BorderLayout.SOUTH);
		this.pack();

		processKeys();

		this.setVisible(true);
	}

	private void handleKeyPress(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			a.speedUp();
			break;
		case KeyEvent.VK_Z:
			a.speedDown();
			break;
		}
		// this.revalidate();
		// this.repaint();
	}
	// start some speedometers
	// listen for keyboard input

}