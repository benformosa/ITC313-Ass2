package speed;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class SpeedometerPanel extends JPanel implements Speedometer {

  public static void main(String args[]) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SpeedometerPanel('a', 'z'));
        frame.pack();
        frame.setVisible(true);
      }
    });
  }

  public char downKey;
  public int speed = 0;
  public int maxSpeed = 100;
  public int minSpeed = 0;

  private JLabel speedLabel;
  private JProgressBar speedBar;
  public char upKey;

  public SpeedometerPanel(char upKey, char downKey) {
    this.upKey = upKey;
    this.downKey = downKey;

    speedLabel = new JLabel(speed + "/" + maxSpeed);

    speedBar = new JProgressBar(0, maxSpeed);
    speedBar.setStringPainted(true);
    speedBar.setValue(speed);

    this.add(speedLabel);

    this.add(speedBar);
    this.add(new JLabel("Up: " + upKey + " Down: " + downKey));
  }

  @Override
  public char getDownKey() {
    return downKey;
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public char getUpKey() {
    return upKey;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  private synchronized void speedChange(int i) {
    if (this.speed + i <= maxSpeed & this.speed + i >= minSpeed) {
      this.speed += i;
      speedLabel.setText(speed + "/" + maxSpeed);
      speedBar.setValue(speed);
      System.out.println(this.toString());
    }
  }

  @Override
  public synchronized void speedDown() {
    this.speedChange(-1);
  }

  @Override
  public synchronized void speedUp() {
    this.speedChange(1);
  }

  public String toString() {
    return upKey + "," + downKey + ":" + speed;
  }
}
