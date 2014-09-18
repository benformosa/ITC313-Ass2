package speed;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SpeedometerPanel extends JPanel {
	private static final int MAX_SIZE = 200;
	private int speed = 0;
	private JLabel speedLabel;

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new SpeedometerPanel());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	public SpeedometerPanel() {
		super(true);
		this.setPreferredSize(new Dimension(MAX_SIZE, MAX_SIZE));
		speedLabel = new JLabel(Integer.toString(speed));
		this.add(speedLabel);
	}

	public void speedUp() {
		this.speedChange(1);
	}

	private void speedChange(int i) {
		this.speed += i;
		speedLabel.setText(Integer.toString(speed));
		// this.revalidate();
		// this.repaint();
		// System.out.println(speed);
	}

	public void speedDown() {
		this.speedChange(-1);
	}
}
