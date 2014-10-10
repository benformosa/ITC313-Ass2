package speed;

import java.util.ArrayList;

public class MultiSpeedometerFactory {

  public static AbstractSpeedometer[] createSpeedometers() {
    ArrayList<AbstractSpeedometer> speedos = new ArrayList<AbstractSpeedometer>();
    speedos.add(new AbstractSpeedometer('a', 'z'));
    speedos.add(new AbstractSpeedometer('s', 'x'));
    return speedos.toArray(new AbstractSpeedometer[speedos.size()]);
  }

  public static SpeedometerPanel[] createSpeedometerPanels() {
    char[][] keys = { { 'a', 'z' }, { 's', 'x' } };
    return createSpeedometerPanels(keys);
  }

  public static SpeedometerPanel[] createSpeedometerPanels(char[][] keys) {
    ArrayList<SpeedometerPanel> speedos = new ArrayList<SpeedometerPanel>();

    for (int i = 0; i < keys.length; i++) {
      speedos.add(new SpeedometerPanel(keys[i][0], keys[i][1]));
    }
    return speedos.toArray(new SpeedometerPanel[speedos.size()]);
  }
}
