package boebot.application;

import TI.BoeBot;
import boebot.config.Configuration;
import boebot.hardware.Remote;
import boebot.hardware.RemoteCallBack;
import boebot.hardware.servo.ServoStatus;
import boebot.interfaces.Button;
import boebot.interfaces.ButtonCallBack;
import boebot.interfaces.Driver;
import boebot.interfaces.navigation.*;
import boebot.interfaces.obstacledetector.FeelerDetector;
import boebot.interfaces.obstacledetector.ObstacleDetector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Robot {
    private Configuration config;
    private Navigation navigation;
    private Remote remote;
    private Button emergencyStopButton;
    private int switchModeCounter;

    public Robot(Configuration config) {
        switchModeCounter = 1000;
        this.config = config;
        this.navigation = new DefaultNavigator(this.config);
        this.remote = new Remote(config);
        emergencyStopButton = new Button(this.config.getEmergencyStopPin(), button -> {
            this.navigation = new DefaultNavigator(this.config);
        });
        this.remote.registerCallBack(Remote.modeSwitch, code -> {
            // All navigations
            ArrayList<Class<? extends Navigation>> modes = new ArrayList<>(Arrays.asList(RandomRouteNavigator.class, LineFollowerNavigator.class, IRNavigator.class, BluetoothNavigator.class));
            Class<? extends Navigation> currentMode = navigation.getClass();
            int newIndex = 0;

            //If mode does not equal to DefaultNavigator add 1 to mode in arraylist to get new mode.
            if(!currentMode.equals(DefaultNavigator.class)) {
                int index = modes.indexOf(currentMode);
                if(index < modes.size()-1) {
                    newIndex = index + 1;
                }
            }
            System.out.println("Changing to mode " + newIndex);
            try {
                //Get Constructor with Parameters (Configuration) and create new object.
                this.navigation = modes.get(newIndex).getDeclaredConstructor(Configuration.class).newInstance(this.config);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("Error on trying to switch modes. Using default navigation");
                e.printStackTrace();
                this.navigation = new DefaultNavigator(this.config);
            }
        });
        this.remote.registerCallBack(Remote.emergencyStop, code -> {
            if(switchModeCounter != 0)
                return;
            switchModeCounter = 1000;
            this.navigation = new DefaultNavigator(this.config);
        });
    }

    public void update() {
        this.remote.update();
        this.navigation.update();
        this.emergencyStopButton.update();
        if(switchModeCounter > 0)
            switchModeCounter--;
    }

    public void setNavigation(Navigation nav) {
        this.navigation = nav;
        this.navigation.update();
    }

    public Configuration getConfig() {
        return this.config;
    }

}
