package boebot.interfaces;

import TI.BoeBot;
import TI.Timer;
import boebot.config.Configuration;
import boebot.hardware.servo.ServoStatus;

import java.awt.*;

public class LineFollower implements Updateable {
    private Configuration config;
    private int leftPin;
    private int middlePin;
    private int rightPin;
    private int lineFollowerThreshold;

    private boolean leftTriggered;
    private boolean middleTriggered;
    private boolean rightTriggered;

    public LineFollower(Configuration config) {
        this.config = config;
        this.leftPin = config.getLeftLineFollowerPin();
        this.middlePin = config.getMiddleLineFollowerPin();
        this.rightPin = config.getRightLineFollowerPin();
        this.lineFollowerThreshold = config.getLineFollowerThreshold();
    }


    @Override
    public void update() {
        leftTriggered = BoeBot.analogRead(this.leftPin) > lineFollowerThreshold;
        rightTriggered = BoeBot.analogRead(this.rightPin) > lineFollowerThreshold;
        middleTriggered = BoeBot.analogRead(this.middlePin) > lineFollowerThreshold;
    }

    public ServoStatus getAdvice() {
        if(middleTriggered && leftTriggered && rightTriggered)
            return ServoStatus.DONT_MOVE;
        if (middleTriggered || leftTriggered || rightTriggered) {
            if (!middleTriggered) {
                if (rightTriggered) {
                    return ServoStatus.ROTATE_RIGHT;
                } else {
                    return ServoStatus.ROTATE_LEFT;
                }
            } else {
                if (rightTriggered && !leftTriggered) {
                    return ServoStatus.ROTATE_LEFT_FORWARD;
                } else if (leftTriggered && !rightTriggered) {
                    return ServoStatus.ROTATE_RIGHT_FORWARD;
                } else {
                    return ServoStatus.FORWARD;
                }
            }
        }
        return ServoStatus.FORWARD;
    }
}
