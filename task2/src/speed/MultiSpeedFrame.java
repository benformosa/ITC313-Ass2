package speed;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MultiSpeedFrame extends JFrame {
  private SpeedometerPanel[] speedometers;

  public static void main(String[] args) {
    JFrame frame = new MultiSpeedFrame();
    frame.setTitle("Speedometers");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(350, 250);
    frame.setVisible(true);
  }

  public MultiSpeedFrame() {
    this.setLayout(new GridLayout(2, 0, 10, 10));
    char keys[][] = { { 'a', 'z' }, { 's', 'x' }, { 'd', 'c' }, { 'f', 'v' } };
    speedometers = MultiSpeedometerFactory.createSpeedometerPanels(keys);

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
        Thread t = new Thread(new SpeedChanger(e));
        t.start();
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }

      @Override
      public void keyTyped(KeyEvent e) {
      }
    });

    for (SpeedometerPanel s : speedometers) {
      this.add(s);
    }
    this.pack();
    this.setVisible(true);
    this.requestFocus();
  }

  class SpeedChanger implements Runnable {
    private KeyEvent e;

    public SpeedChanger(KeyEvent e) {
      this.e = e;
    }

    @Override
    public void run() {
      for (SpeedometerPanel s : speedometers) {
        if (e.getKeyChar() == s.getUpKey()) {
          s.speedUp();
        }
        if (e.getKeyChar() == s.getDownKey()) {
          s.speedDown();
        }
      }
    }
  }
}