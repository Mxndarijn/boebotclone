package boebot.config;

public interface Configuration {

    /**
     * Servo Settings
     * Settings used for the servo's on the BoeBot. With these we get the pins the servo's are connected to and if needed the offset.
     * @return Pins for the servo's and the offsets the servo's have.
     */
    int getLeftServoOffset();

    int getRightServoOffset();

    int getLeftServoPin();

    int getRightServoPin();

    int getGripperServoPin();

    /**
     * Driver settings
     * Settings for the Driver class. Mainly used for the Buzzer and blinking led timer.
     * @return Pins the servo's are connected to.
     */

    int getDriverLeftTurningLightPin();

    int getDriverRightTurningLightPin();

    int getDriverBuzzerGoingBackwardsPin();

    int getDriverBlinkingLedTimer();

    /**
     * Settings for the infrared sensor on the BoeBot.
     * @return The pin the sensor is connected to and the delay.
     */
    int getIRDelayTime();

    int getIRPin();

    /**
     * Pins for the whiskers on the BoeBot.
     * @return The pin the whisker is connected to.
     */

    int getLeftWhiskerPin();

    int getRightWhiskerPin();

    /**
     * Settings for the random route navigator class.
     * @return The amount of time the BoeBot should turn, the maximum speed at which the Boebot drives forward, the maximum speed the BoeBot turns at, the time the BoeBot drives backwards and the maximum speed at which the BoeBot drives backward.
     */

    int getRandomRouteTurningTimer();

    int getRandomRouteMaxForwardSpeed();

    int getRandomRouteMaxTurningSpeed();

    int getRandomRouteBackTimer();

    int getRandomRouteMaxBackwardsSpeed();

    /**
     * Pins for the ultrasone module on the BoeBot.
     * @return The trigger pin and the Echo pin of the ultrasone module.
     */

    int getBottomUltraSoneTrigPin();

    int getBottomUltraSoneEchoPin();

    int getUpperUltraSoneTrigPin();

    int getUpperUltraSoneEchoPin();

    /**
     * The pins the linefollowers are connected to and the threshold which determines when the BoeBot has to act.
     * @return Pins for the linefollowers on the BoeBot and the threshold.
     */

    int getLeftLineFollowerPin();

    int getMiddleLineFollowerPin();

    int getRightLineFollowerPin();

    int getLineFollowerThreshold();

    /**
     * The mode is indicated by a NeoPixel on the BoeBot, this method gives back the pin for that NeoPixel.
     * @return the pin for the Led which indicates the mode the BoeBot is in and the pin that the bluetooth module is connected to.
     */

    int getModeLedPin();

    int getEmergencyStopPin();

    int getDownUltraSoneTrigPin();

    int getDownUltraSoneEchoPin();
}
