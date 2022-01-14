package boebot.config;

public class DefaultConfig implements Configuration {

    public DefaultConfig() {

    }

    @Override
    public int getLeftServoOffset() {
        return 0;
    }

    @Override
    public int getRightServoOffset() {
        return 0;
    }

    @Override
    public int getLeftServoPin() {
        return 12;
    }

    @Override
    public int getRightServoPin() {
        return 13;
    }

    @Override
    public int getGripperServoPin(){return 11;}

    @Override
    public int getDriverLeftTurningLightPin() {
        return 15;
    }

    @Override
    public int getDriverRightTurningLightPin() {
        return 0;
    }

    @Override
    public int getDriverBuzzerGoingBackwardsPin() {
        return 1;
    }

    @Override
    public int getDriverBlinkingLedTimer() {
        return 750;
    }

    @Override
    public int getIRDelayTime() {
        return 0; //TODO
    }

    @Override
    public int getIRPin() {
        return 4;
    }

    @Override
    public int getLeftWhiskerPin() {
        return 14;
    }

    @Override
    public int getRightWhiskerPin() {
        return 11;
    }

    @Override
    public int getRandomRouteTurningTimer() {
        return 2000;
    }

    @Override
    public int getRandomRouteMaxForwardSpeed() {
        return 100;
    }

    @Override
    public int getRandomRouteMaxTurningSpeed() {
        return 50;
    }

    @Override
    public int getRandomRouteBackTimer() {
        return 2000;
    }

    @Override
    public int getRandomRouteMaxBackwardsSpeed() {
        return 50;
    }

    @Override
    public int getBottomUltraSoneTrigPin() {
        return 8;
    }

    @Override
    public int getBottomUltraSoneEchoPin() {
        return 9;
    }

    @Override
    public int getUpperUltraSoneTrigPin() {
        return 0;
    }

    @Override
    public int getUpperUltraSoneEchoPin() {
        return 0;
    }

    @Override
    public int getLeftLineFollowerPin() {
        return 1;
    }

    @Override
    public int getMiddleLineFollowerPin() {
        return 2;
    }

    @Override
    public int getRightLineFollowerPin() {
        return 3;
    }

    @Override
    public int getLineFollowerThreshold() {
        return 1450;
    }

    @Override
    public int getModeLedPin() {
        return 1;
    }

    @Override
    public int getEmergencyStopPin() {
        return 0;
    }
}
