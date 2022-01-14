package boebot.interfaces.navigation;

import TI.BoeBot;
import TI.PinMode;
import TI.Servo;
import boebot.config.Configuration;
import boebot.hardware.Bluetooth;
import boebot.hardware.BluetoothCallBack;
import boebot.hardware.RemoteCallBack;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.*;
import boebot.hardware.Remote;
import boebot.interfaces.navigation.bluetoothNavigator.Command;
import boebot.interfaces.navigation.bluetoothNavigator.CommandCallBack;
import boebot.interfaces.navigation.bluetoothNavigator.Commands;
import boebot.interfaces.obstacledetector.ObstacleDetector;
import boebot.interfaces.obstacledetector.UltraSoneDetector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class BluetoothNavigator implements Updateable, Navigation, GripperCallback, CommandCallBack {
    private Configuration config;
    private Driver driver;
    private UltraSoneDetector ultraSoneDetectorDown;
    private UltraSoneDetector ultraSoneDetectorUp;
    private LineFollower lineFollower;
    private Gripper gripper;
    private Bluetooth bluetooth;
    private ArrayList<Command> route;
    private Command currentCommand;
    private boolean atCrossSection;

    public BluetoothNavigator(Configuration config) {
        this.config = config;
        this.atCrossSection = false;
        this.driver = new Driver(config);
        this.gripper = new Gripper(config.getGripperServoPin(), 3, this);
        this.bluetooth = new Bluetooth(this.config);
        this.ultraSoneDetectorUp = new UltraSoneDetector(this.config.getUpperUltraSoneTrigPin(), this.config.getUpperUltraSoneEchoPin(), this,20);
        this.ultraSoneDetectorDown = new UltraSoneDetector(this.config.getDownUltraSoneTrigPin(), this.config.getDownUltraSoneEchoPin(), this,5);
        this.lineFollower = new LineFollower(config);
        BoeBot.rgbSet(config.getModeLedPin(), Color.BLUE);
        BoeBot.rgbShow();
        this.bluetooth.registerCallBack("Route", (commandMes, extraMessage) -> {
            if(extraMessage.startsWith("/")) {
                extraMessage = extraMessage.replaceFirst("/", "");
                route = new ArrayList<>();
                Arrays.stream(extraMessage.split(",")).forEach(commandString -> {
                    Command command = Commands.getCommand(commandString, this);
                    if(command != null) {
                        route.add(command);
                    } else {
                        System.out.println("Could not find Command for [" + commandString + "]");
                    }
                });
                nextCommand();
            }
        });
        route = new ArrayList<>(Arrays.asList(Commands.getCommand("w", this)));
        this.bluetooth.registerCallBack("EM", (command, extraMessage) -> {
            this.driver.change(ServoStatus.DONT_MOVE,0);
            this.driver.makeSameSpeed();
        });
    }

    @Override
    public void OnGripperOpen() {

    }

    @Override
    public void OnGripperClose() {

    }

    @Override
    public void update() {
        lineFollower.update();
        if(lineFollower.getAdvice().equals(ServoStatus.DONT_MOVE) && !atCrossSection) {
            atCrossSection = true;
            nextCommand();
        }
        gripper.update();
        ultraSoneDetectorDown.update();
        ultraSoneDetectorUp.update();
        this.currentCommand.update();
        driver.update();
    }

    public void nextCommand() {
        if(route.size() > 0) {
            this.currentCommand = route.get(0);
            this.currentCommand.start();
            route.remove(0);
        } else {
            //TODO stop process
        }
    }

    @Override
    public void obstacleCallBack(ObstacleDetector obstacleDetector) {
        if(obstacleDetector instanceof UltraSoneDetector) {
            UltraSoneDetector ultraSoneDetector = (UltraSoneDetector) obstacleDetector;

        }
    }

    @Override
    public void commandCallBack(Command command) {

    }

    public Configuration getConfig() {
        return config;
    }

    public Driver getDriver() {
        return driver;
    }

    public UltraSoneDetector getUltraSoneDetectorDown() {
        return ultraSoneDetectorDown;
    }

    public UltraSoneDetector getUltraSoneDetectorUp() {
        return ultraSoneDetectorUp;
    }

    public LineFollower getLineFollower() {
        return lineFollower;
    }

    public Gripper getGripper() {
        return gripper;
    }

    public Bluetooth getBluetooth() {
        return bluetooth;
    }

    public ArrayList<Command> getRoute() {
        return route;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

    public boolean isAtCrossSection() {
        return atCrossSection;
    }
}
