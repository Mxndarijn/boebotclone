package boebot.interfaces.navigation;

import TI.BoeBot;
import TI.PinMode;
import TI.Servo;
import boebot.config.Configuration;
import boebot.hardware.RemoteCallBack;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Driver;
import boebot.interfaces.Gripper;
import boebot.interfaces.GripperCallback;
import boebot.interfaces.Updateable;
import boebot.hardware.Remote;
import boebot.interfaces.obstacledetector.ObstacleDetector;
import boebot.interfaces.obstacledetector.UltraSoneDetector;

import java.awt.*;
import java.sql.SQLOutput;

public class IRNavigator implements Updateable, Navigation, GripperCallback {
    private Configuration config;
    private int IRpin;
    private Remote remote;
    private Driver driver;
    private UltraSoneDetector ultraSoneDetector;
    private Gripper gripper;

    public IRNavigator(Configuration config) {
        this.config = config;
        this.IRpin = config.getIRPin();
        this.remote = new Remote(config);
        this.driver = new Driver(config);
        this.gripper = new Gripper(config.getGripperServoPin(), 3, this);
        this.ultraSoneDetector = new UltraSoneDetector(this.config.getUpperUltraSoneTrigPin(), this.config.getUpperUltraSoneEchoPin(), this, 20);
        BoeBot.rgbSet(config.getModeLedPin(), Color.ORANGE);
        BoeBot.rgbShow();



        /**
         * If obstacle is near, the robot will stop moving.
         */

        /**
         * Movements if obstacle isn't near and a button between 1 and 9 is pressed.
         */
        int maxSpeed = 50;
        this.remote.registerCallBack(Remote.buttonOne, code -> {
            System.out.println("Rotating Left Forward");
            this.driver.change(ServoStatus.ROTATE_LEFT_FORWARD,maxSpeed,1);
        });
        this.remote.registerCallBack(Remote.buttonTwo, code -> {
            System.out.println("Going forward");
            this.driver.change(ServoStatus.FORWARD, maxSpeed,1);
        });

        this.remote.registerCallBack(Remote.buttonThree, code -> {
            System.out.println(code);
            System.out.println("Rotating right forward");
            this.driver.change(ServoStatus.ROTATE_RIGHT_FORWARD, maxSpeed, 1);
        });

        this.remote.registerCallBack(Remote.buttonFour, code -> {
            System.out.println("Rotating Left");
            this.driver.change(ServoStatus.ROTATE_LEFT, maxSpeed, 1);
        });

        this.remote.registerCallBack(Remote.buttonFive, code -> {
            System.out.println("Going to not move");
            this.driver.change(ServoStatus.DONT_MOVE, 0, 5);
        });

        this.remote.registerCallBack(Remote.buttonSix, code -> {
            System.out.println("Rotating right");
            this.driver.change(ServoStatus.ROTATE_RIGHT, maxSpeed, 1);
        });

        this.remote.registerCallBack(Remote.buttonSeven, code -> {
            System.out.println("Rotating left backwards");
            this.driver.change(ServoStatus.ROTATE_LEFT_BACKWARDS, maxSpeed, 1);
        });

        this.remote.registerCallBack(Remote.buttonEight, code -> {
            System.out.println("going backwards");
            this.driver.change(ServoStatus.BACKWARDS, maxSpeed, 1);
        });

        this.remote.registerCallBack(Remote.buttonNine, code -> {
            System.out.println("Rotating right backwards");
            this.driver.change(ServoStatus.ROTATE_RIGHT_BACKWARDS, maxSpeed, 1);
        });
        this.remote.registerCallBack(Remote.buttonZero, code -> {
            System.out.println("gripper");
            if(gripper.isOpen()) {
                gripper.close();
            }
            else
                gripper.open();
        });
    }

    @Override
    public void update() {
        this.driver.update();
        this.remote.update();
        this.ultraSoneDetector.update();
        this.gripper.update();
        if(this.ultraSoneDetector.isObstacleNear()) {
            this.driver.change(ServoStatus.DONT_MOVE, 0, 5);
        }
    }

    @Override
    public void obstacleCallBack(ObstacleDetector obstacleDetector) {

    }

    @Override
    public void OnGripperOpen() {

    }

    @Override
    public void OnGripperClose() {

    }
}
