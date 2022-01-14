package boebot.interfaces.navigation;

import TI.BoeBot;
import boebot.config.Configuration;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Driver;
import boebot.interfaces.obstacledetector.FeelerDetector;
import boebot.interfaces.obstacledetector.ObstacleDetector;

import java.awt.*;

public class DefaultNavigator implements Navigation {
    private Configuration config;
    private Driver driver;

    public DefaultNavigator(Configuration config) {
        this.config = config;
        this.driver = new Driver(this.config);
        BoeBot.rgbSet(config.getModeLedPin(), Color.RED);
        BoeBot.rgbShow();
    }

    @Override
    public void update() {
        if(this.driver.getServoStatus() != ServoStatus.DONT_MOVE) {
            this.driver.change(ServoStatus.DONT_MOVE, 0);
            this.driver.makeSameSpeed();
        }
    }

    @Override
    public void obstacleCallBack(ObstacleDetector obstacleDetector) {

    }
}
