package boebot.interfaces.obstacledetector;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import boebot.config.Configuration;
import boebot.interfaces.Updateable;
import boebot.interfaces.navigation.Navigation;

public class UltraSoneDetector implements ObstacleDetector, Updateable {
    private int objectTooClose;
    private int trigPin;
    private int echoPin;
    private Configuration config;
    private Navigation navigation;
    private int lastMeasure;
    private int hits;
    private Timer callTimer;

    // Constructor
    public UltraSoneDetector(int trigPin, int echoPin, Navigation navigation, int objectTooClose) {
        BoeBot.setMode(this.trigPin = trigPin, PinMode.Output);
        BoeBot.setMode(this.echoPin = echoPin, PinMode.Input);
        this.navigation = navigation;
        hits = 0;
        this.lastMeasure = 100;
        this.callTimer = new Timer(0);
        update();
        this.callTimer.setInterval(250);

        // Checks if the objectTooClose isn't out of bounds for the ultraSone.
        if(objectTooClose >= 134){
            objectTooClose = 134;
        }
        if(objectTooClose <= 0){
            objectTooClose = 1;
        }

        // Sets object too close to destinated value of a range between 1 - 133.
        this.objectTooClose = objectTooClose;

        // Creating ultraSone object to check the distance to an object in isObstacleNear();.

    }

    // Checks how far away an obstacle is.
    @Override
    public boolean isObstacleNear() {
        if(lastMeasure < this.objectTooClose && lastMeasure > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if the object is detected once or not.
    @Override
    public boolean isFirstTime() {
        return this.hits <= 1;
    }

    // updates distance between bot and object.
    @Override
    public void update() {
        if(this.callTimer.timeout()) {
            BoeBot.digitalWrite(trigPin, true);
            BoeBot.wait(1);
            BoeBot.digitalWrite(trigPin, false);

            int pulse = BoeBot.pulseIn(echoPin, true, 10000);
            BoeBot.wait(50);

            lastMeasure = pulse / 58;
            if(isObstacleNear()) {
                navigation.obstacleCallBack(this);
            }
        }
    }

    public int getObjectTooClose() {
        return objectTooClose;
    }

    public int getTrigPin() {
        return trigPin;
    }

    public int getEchoPin() {
        return echoPin;
    }

    public Configuration getConfig() {
        return config;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public int getLastMeasure() {
        return lastMeasure;
    }

    public int getHits() {
        return hits;
    }

    public Timer getCallTimer() {
        return callTimer;
    }
}
