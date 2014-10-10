package speed;

public class AbstractSpeedometer implements Speedometer {
  public char downKey;
  public int speed = 0;
  public char upKey;

  public AbstractSpeedometer(char upKey, char downKey) {
    this.upKey = upKey;
    this.downKey = downKey;
  }

  public AbstractSpeedometer(int startSpeed, char upKey, char downKey) {
    this(upKey, downKey);
    this.speed = startSpeed;
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
  public synchronized void setSpeed(int speed) {
    this.speed = speed;
  }

  private synchronized void speedChange(int i) {
    this.speed += i;
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