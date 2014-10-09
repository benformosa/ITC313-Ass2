package speed;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MultiSpeedFrame extends JFrame implements FocusListener {
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
    speedometers = MultiSpeedometerFactory.createSpeedometerPanels();

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
    this.addFocusListener(this);
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
        if (e.getKeyCode() == s.getUpKey()) {
          s.speedUp();
        }
        if (e.getKeyCode() == s.getDownKey()) {
          s.speedDown();
        }
      }
    }
  }

  @Override
  public void focusGained(FocusEvent e) {

  }

  @Override
  public void focusLost(FocusEvent e) {
    this.requestFocus();
  }
}