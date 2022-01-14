package boebot.interfaces.navigation;

import TI.BoeBot;
import TI.Timer;
import boebot.config.Configuration;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Driver;
import boebot.interfaces.Updateable;
import boebot.interfaces.obstacledetector.FeelerDetector;
import boebot.interfaces.obstacledetector.ObstacleDetector;
import boebot.interfaces.obstacledetector.UltraSoneDetector;

import java.awt.*;

public class RandomRouteNavigator implements Updateable, Navigation {
    private Configuration config;
    private Driver driver;
    private ObstacleDetector obstacleDetector;
    private Timer turningTimer;
    private Timer backTimer;
    private Timer timer;

    public RandomRouteNavigator(Configuration config) {
        this.config = config;
        this.driver = new Driver(config);
        //this.obstacleDetector = new FeelerDetector(this.config, this);
        this.obstacleDetector = new UltraSoneDetector(this.config.getUpperUltraSoneTrigPin(), this.config.getUpperUltraSoneEchoPin(), this, 30);
        this.driver.change(ServoStatus.FORWARD, config.getRandomRouteMaxForwardSpeed());
        this.driver.update();
        turningTimer = new Timer(config.getRandomRouteTurningTimer());
        backTimer = new Timer(config.getRandomRouteBackTimer());
        BoeBot.rgbSet(this.config.getModeLedPin(), Color.YELLOW);
        BoeBot.rgbShow();
        timer = new Timer (50);
    }

    @Override
    public void update() {
        if(!timer.timeout())
            return;
        obstacleDetector.update();
        if(turningTimer != null && turningTimer.timeout() && !obstacleDetector.isObstacleNear()) {
            this.driver.makeSameSpeed(10);
            this.driver.change(ServoStatus.FORWARD, config.getRandomRouteMaxForwardSpeed());
            turningTimer = null;
        }
        if(this.driver.getServoStatus() == ServoStatus.BACKWARDS && backTimer.timeout()) {
                this.driver.change(ServoStatus.ROTATE_RIGHT, config.getRandomRouteMaxTurningSpeed());
                this.driver.makeSameSpeed(10);
                turningTimer = new Timer(config.getRandomRouteTurningTimer());
        }
        this.driver.update();
    }

    @Override
    public void obstacleCallBack(ObstacleDetector obstacleDetector) {
        if(obstacleDetector.isFirstTime()) {
            this.driver.makeSameSpeed(-10);
        }
        this.driver.change(ServoStatus.BACKWARDS, config.getRandomRouteMaxBackwardsSpeed());
        backTimer.mark();
    }
}
