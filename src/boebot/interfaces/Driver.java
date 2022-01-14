package boebot.interfaces;

import TI.Timer;
import boebot.config.Configuration;
import boebot.hardware.Buzzer;
import boebot.hardware.led.BlinkingLed;
import boebot.hardware.led.BlinkingNeoPixel;
import boebot.hardware.led.NeoPixel;
import boebot.hardware.servo.Servo;
import boebot.hardware.servo.ServoStatus;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Driver implements Updateable {
    private Configuration config;
    private Servo leftServo;
    private Servo rightServo;
    private ServoStatus servoStatus;
    private int speed;
    private int accelerationSpeed;
    private BlinkingLed leftLed;
    private BlinkingLed rightLed;
    private ArrayList<BlinkingNeoPixel> leftNeoPixels;
    private ArrayList<BlinkingNeoPixel> rightNeoPixels;
    private Buzzer buzzerMiddle;
    private Timer updater;

    public Driver(Configuration config) {
        this.config = config;
        this.speed = 0;
        this.servoStatus = ServoStatus.DONT_MOVE;
        this.leftServo = new Servo(config.getLeftServoPin());
        this.rightServo = new Servo(config.getRightServoPin());
        accelerationSpeed = 1;
        this.leftNeoPixels = new ArrayList<>(Arrays.asList(new BlinkingNeoPixel(2, config.getDriverBlinkingLedTimer(), Color.ORANGE), new BlinkingNeoPixel(3, config.getDriverBlinkingLedTimer(), Color.ORANGE)));
        this.rightNeoPixels = new ArrayList<>(Arrays.asList(new BlinkingNeoPixel(0, config.getDriverBlinkingLedTimer(), Color.ORANGE), new BlinkingNeoPixel(5, config.getDriverBlinkingLedTimer(), Color.ORANGE)));
        this.buzzerMiddle = new Buzzer(config.getDriverBuzzerGoingBackwardsPin());
        makeSameSpeed();
        updater = new Timer(100);
        update();
    }

    /**
     * Updates the servos compared to the state they are in right now.
     */
    @Override
    public void update() {
        if(!updater.timeout())
            return;
        if(servoStatus == ServoStatus.ROTATE_LEFT || servoStatus == ServoStatus.ROTATE_LEFT_BACKWARDS || servoStatus == ServoStatus.ROTATE_LEFT_FORWARD) {
            for(BlinkingNeoPixel blinkingNeoPixel : leftNeoPixels) {
                blinkingNeoPixel.update();
            }
        } else if(servoStatus == ServoStatus.ROTATE_RIGHT  || servoStatus == ServoStatus.ROTATE_RIGHT_BACKWARDS || servoStatus == ServoStatus.ROTATE_RIGHT_FORWARD) {
            for(BlinkingNeoPixel blinkingNeoPixel : rightNeoPixels) {
                blinkingNeoPixel.update();
            }
        }
        if(servoStatus == ServoStatus.FORWARD || servoStatus == ServoStatus.BACKWARDS) {
            for(BlinkingNeoPixel blinkingNeoPixel : rightNeoPixels) {
                blinkingNeoPixel.off();
            }
            for(BlinkingNeoPixel blinkingNeoPixel : leftNeoPixels) {
                blinkingNeoPixel.off();
            }
        }
        if(servoStatus == ServoStatus.BACKWARDS || servoStatus == ServoStatus.ROTATE_RIGHT_BACKWARDS || servoStatus == ServoStatus.ROTATE_LEFT_BACKWARDS) {
            //buzzerMiddle.buzz(100);
        }
        int speedLeftServo = 1500 - servoStatus.getLeftServoMultiplier() * speed/2 + config.getLeftServoOffset();
        int speedRightServo = 1500 - servoStatus.getRightServoMultiplier() * speed/2 + config.getRightServoOffset();

        checkServo(speedLeftServo, leftServo);
        checkServo(speedRightServo, rightServo);
        //System.out.println("left: " + leftServo.getPulseWidth() + " right: " + rightServo.getPulseWidth());
    }

    private void checkServo(int speedRightServo, Servo rightServo) {
        if(speedRightServo >= rightServo.getPulseWidth()+accelerationSpeed) {
            if(speedRightServo < rightServo.getPulseWidth()) {
                rightServo.move(rightServo.getPulseWidth()-accelerationSpeed);
            } else if(speedRightServo > rightServo.getPulseWidth()) {
                rightServo.move(rightServo.getPulseWidth()+accelerationSpeed);
            }
        } else if(speedRightServo != rightServo.getPulseWidth()) {
            rightServo.move(speedRightServo);
        }
    }

    /**
     * Change the speed and servoStatus of the servos
     * @param servoStatus The new servoStatus
     * @param speed The new speed
     */
    public void change(ServoStatus servoStatus, int speed) {
        change(servoStatus, speed, 1);
    }

    public void change(ServoStatus servoStatus, int speed, int accelerationSpeed) {
        setServoStatus(servoStatus);
        this.speed = speed;
        this.accelerationSpeed = Math.abs(accelerationSpeed);
    }

    public Servo getLeftServo() {
        return leftServo;
    }

    public void setLeftServo(Servo leftServo) {
        this.leftServo = leftServo;
    }

    public Servo getRightServo() {
        return rightServo;
    }

    public void setRightServo(Servo rightServo) {
        this.rightServo = rightServo;
    }

    public ServoStatus getServoStatus() {
        return servoStatus;
    }

    public void setServoStatus(ServoStatus servoStatus) {
        this.servoStatus = servoStatus;
    }

    /**
     * Makes the speed of both servos the same using ServoStatus
     */
    public void makeSameSpeed() {
        int speedLeftServo = 1500 - servoStatus.getLeftServoMultiplier() * speed/2 + config.getLeftServoOffset();
        int speedRightServo = 1500 - servoStatus.getRightServoMultiplier() * speed/2 + config.getRightServoOffset();
        leftServo.move(speedLeftServo);
        rightServo.move(speedRightServo);
    }

    /**
     * Makes the speed of both servos the same
     * @param speed new speed of the servos
     */
    public void makeSameSpeed(int speed) {
        int speedLeftServo = 1500 - servoStatus.getLeftServoMultiplier() * speed/2 + config.getLeftServoOffset();
        int speedRightServo = 1500 - servoStatus.getRightServoMultiplier() * speed/2 + config.getRightServoOffset();
        leftServo.move(speedLeftServo);
        rightServo.move(speedRightServo);
    }

    public int getSpeed() {
        return speed;
    }
}
