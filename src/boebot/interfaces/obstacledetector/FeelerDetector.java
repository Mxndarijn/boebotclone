package boebot.interfaces.obstacledetector;

import TI.BoeBot;
import TI.PinMode;
import boebot.config.Configuration;
import boebot.hardware.led.Led;
import boebot.interfaces.Updateable;
import boebot.interfaces.navigation.Navigation;

public class FeelerDetector implements Updateable, ObstacleDetector {
    private Configuration config;
    private Navigation navigation;
    private boolean isObstacleNear;
    private int hits;

    public FeelerDetector(Configuration config, Navigation navigation) {
        this.config = config;
        this.navigation = navigation;
        BoeBot.setMode(config.getLeftWhiskerPin(), PinMode.Input);
        BoeBot.setMode(config.getRightWhiskerPin(), PinMode.Input);
        this.isObstacleNear = isObstacleNear();
        hits = 0;
    }

    @Override
    public void update() {
        this.isObstacleNear = isObstacleNear();
        if(this.isObstacleNear) {
            hits++;
            this.navigation.obstacleCallBack(this);
        } else
            hits = 0;
    }

    @Override
    public boolean isObstacleNear() {
        if(!BoeBot.digitalRead(config.getLeftWhiskerPin()) || !BoeBot.digitalRead(config.getRightWhiskerPin())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFirstTime() {
        return hits <= 1;
    }
}
