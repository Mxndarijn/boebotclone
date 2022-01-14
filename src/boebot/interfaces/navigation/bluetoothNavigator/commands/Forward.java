package boebot.interfaces.navigation.bluetoothNavigator.commands;

import boebot.config.Configuration;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Driver;
import boebot.interfaces.navigation.BluetoothNavigator;
import boebot.interfaces.navigation.bluetoothNavigator.Command;

public class Forward implements Command {
    private final BluetoothNavigator bluetoothNavigator;

    public Forward(BluetoothNavigator bluetoothNavigator) {
        this.bluetoothNavigator = bluetoothNavigator;
    }

    @Override
    public void start() {
        update();
    }

    @Override
    public void update() {
        ServoStatus servoStatus = bluetoothNavigator.getLineFollower().getAdvice();
        Driver driver = bluetoothNavigator.getDriver();
        Configuration config = bluetoothNavigator.getConfig();
        switch (servoStatus) {
            case FORWARD:
                driver.change(servoStatus, config.getRandomRouteMaxForwardSpeed(), 1);
                break;
            case BACKWARDS:
                driver.change(servoStatus, config.getRandomRouteMaxBackwardsSpeed(), 1);
                break;
            case ROTATE_LEFT:
                driver.change(servoStatus, 40, 1);
                break;
            case ROTATE_RIGHT:
                driver.change(servoStatus,40, 1);
                break;
            case ROTATE_RIGHT_FORWARD:
                driver.change(servoStatus, 40, 1);
                break;
            case ROTATE_LEFT_FORWARD:
                driver.change(servoStatus, 40, 1);
                break;
            case ROTATE_RIGHT_BACKWARDS:
                driver.change(servoStatus, 40, 1);
                break;
            case ROTATE_LEFT_BACKWARDS:
                driver.change(servoStatus, 40, 1);
                break;
            case DONT_MOVE:
                driver.change(servoStatus, 0, 1);
                driver.makeSameSpeed();
                break;
        }
    }
}
