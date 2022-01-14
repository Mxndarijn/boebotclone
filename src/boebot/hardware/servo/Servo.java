package boebot.hardware.servo;

import boebot.interfaces.Updateable;

public class Servo implements Updateable {
    private TI.Servo servo;

    public TI.Servo getServo() {
        return servo;
    }

    /**
     * Makes the servo move.
     * @param data The pulse width that the update method uses for the servo.
     */
    public void move(int data) {
        servo.update(data);
    }

    public Servo(int pin) {
        servo = new TI.Servo(pin);
    }

    /**
     * Starts the servo.
     */
    public void start() {
        servo.start();
    }

    /**
     * Stops the servo.
     */
    public void stop() {
        servo.stop();
    }

    public int getPulseWidth() {
        return servo.getPulseWidth();
    }

    public void update() {

    }
}
