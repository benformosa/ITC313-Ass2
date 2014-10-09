package speed;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class SpeedometerPanel extends JPanel implements Speedometer,
    ActionListener {

  public static void main(String args[]) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SpeedometerPanel(KeyEvent.VK_A, KeyEvent.VK_Z));
        frame.pack();
        frame.setVisible(true);
      }
    });
  }

  public int downKey;
  public int speed = 0;
  private JButton speedDownButton;
  private JLabel speedLabel;
  private JButton speedUpButton;
  private JProgressBar speedBar;
  public int upKey;

  public SpeedometerPanel(int upKey, int downKey) {
    this.upKey = upKey;
    this.downKey = downKey;

    speedLabel = new JLabel(Integer.toString(this.getSpeed()));
    speedUpButton = new JButton("↑");
    speedUpButton.addActionListener(this);
    speedDownButton = new JButton("↓");
    speedDownButton.addActionListener(this);
    speedBar = new JProgressBar(-100, 100);
    speedBar.setStringPainted(true);
    speedBar.setValue(speed);

    this.add(speedLabel);
    this.add(speedUpButton);
    this.add(speedDownButton);
    this.add(speedBar);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == speedUpButton) {
      speedUp();
    } else if (e.getSource() == speedDownButton) {
      speedDown();
    }
  }

  @Override
  public int getDownKey() {
    return downKey;
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public int getUpKey() {
    return upKey;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  private synchronized void speedChange(int i) {
    this.speed += i;
    speedLabel.setText(Integer.toString(this.getSpeed()));
    speedBar.setValue(speed);
    System.out.println(speed);
  }

  @Override
  public synchronized void speedDown() {
    this.speedChange(-1);
  }

  @Override
  public synchronized void speedUp() {
    this.speedChange(1);
  }
}
