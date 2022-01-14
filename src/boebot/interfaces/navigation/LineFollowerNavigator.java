package boebot.interfaces.navigation;

import TI.BoeBot;
import TI.Timer;
import boebot.config.Configuration;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Driver;
import boebot.interfaces.Updateable;
import boebot.interfaces.obstacledetector.ObstacleDetector;
import boebot.interfaces.obstacledetector.UltraSoneDetector;

import java.awt.*;

public class LineFollowerNavigator implements Updateable, Navigation {
    private Configuration config;
    private int leftPin;
    private int middlePin;
    private int rightPin;
    private int lineFollowerThreshold;
    private Driver driver;
    private Timer stopTimer;
    private Timer timer;
    private ObstacleDetector obstacleDetector;

    public LineFollowerNavigator(Configuration config) {
        this.config = config;
        this.leftPin = config.getLeftLineFollowerPin();
        this.middlePin = config.getMiddleLineFollowerPin();
        this.rightPin = config.getRightLineFollowerPin();
        this.lineFollowerThreshold = config.getLineFollowerThreshold();
        this.driver = new Driver(config);
        stopTimer = new Timer(2500);
        stopTimer.mark();
        timer = new Timer (50);
        this.obstacleDetector = new UltraSoneDetector(this.config.getUpperUltraSoneTrigPin(), this.config.getUpperUltraSoneEchoPin(),this, 20);
        BoeBot.rgbSet(config.getModeLedPin(), Color.WHITE);
        BoeBot.rgbShow();
    }

    @Override
    public void update() {
        if (stopTimer.timeout()) {
            stopTimer.setInterval(0);
            driver.change(ServoStatus.DONT_MOVE, 0, 15);
            driver.update();
            return;
        }
        if(!timer.timeout())
            return;
        boolean middle = BoeBot.analogRead(this.middlePin) > lineFollowerThreshold;
        boolean left = BoeBot.analogRead(this.leftPin) > lineFollowerThreshold;
        boolean right = BoeBot.analogRead(this.rightPin) > lineFollowerThreshold;
        this.obstacleDetector.update();
        if (middle || left || right || this.obstacleDetector.isObstacleNear()) {
            stopTimer.mark();
        }
        if(!this.obstacleDetector.isObstacleNear()) {
            if (middle || left || right) {
                if (!middle) {
                    if (right) {
                        driver.change(ServoStatus.ROTATE_RIGHT, 40, 1);
                        driver.makeSameSpeed();
                    } else {
                        driver.change(ServoStatus.ROTATE_LEFT, 40, 1);
                        driver.makeSameSpeed();
                    }
                } else {
                    if (right && !left) {
                        driver.change(ServoStatus.ROTATE_LEFT_FORWARD, 200, 1);
                        driver.makeSameSpeed();
                    } else if (left && !right) {
                        driver.change(ServoStatus.ROTATE_RIGHT_FORWARD, 200, 1);
                        driver.makeSameSpeed();
                    } else {
                        driver.change(ServoStatus.FORWARD, 200, 1);
                        driver.makeSameSpeed();
                    }
                }
            }
        }

        driver.update();
    }

    @Override
    public void obstacleCallBack(ObstacleDetector obstacleDetector) {
        System.out.println("Obstacle detected");
        driver.change(ServoStatus.DONT_MOVE, 0, 50);
    }
}
