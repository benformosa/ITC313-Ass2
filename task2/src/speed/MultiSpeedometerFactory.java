package speed;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MultiSpeedometerFactory {

  public static AbstractSpeedometer[] createSpeedometers() {
    ArrayList<AbstractSpeedometer> speedos = new ArrayList<AbstractSpeedometer>();
    speedos.add(new AbstractSpeedometer(KeyEvent.VK_A, KeyEvent.VK_Z));
    speedos.add(new AbstractSpeedometer(KeyEvent.VK_S, KeyEvent.VK_X));
    return speedos.toArray(new AbstractSpeedometer[speedos.size()]);
  }

  public static SpeedometerPanel[] createSpeedometerPanels() {
    ArrayList<SpeedometerPanel> speedos = new ArrayList<SpeedometerPanel>();
    speedos.add(new SpeedometerPanel(KeyEvent.VK_A, KeyEvent.VK_Z));
    speedos.add(new SpeedometerPanel(KeyEvent.VK_S, KeyEvent.VK_X));
    speedos.add(new SpeedometerPanel(KeyEvent.VK_D, KeyEvent.VK_C));
    speedos.add(new SpeedometerPanel(KeyEvent.VK_F, KeyEvent.VK_V));
    return speedos.toArray(new SpeedometerPanel[speedos.size()]);
  }
}
