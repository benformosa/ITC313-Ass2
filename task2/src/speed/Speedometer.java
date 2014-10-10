package speed;

public interface Speedometer {
  char keyUp = 'a';
  char keyDown = 'z';

  public void speedDown();

  public void speedUp();

  public int getSpeed();

  public void setSpeed(int speed);

  public char getUpKey();

  public char getDownKey();

}
