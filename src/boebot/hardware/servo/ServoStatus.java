package boebot.hardware.servo;

public enum ServoStatus {
    FORWARD(-2,2),
    BACKWARDS(2,-2),
    ROTATE_LEFT(2, 2),
    ROTATE_RIGHT(-2, -2),
    ROTATE_RIGHT_FORWARD(-2,1),
    ROTATE_LEFT_FORWARD(-1,2),
    ROTATE_RIGHT_BACKWARDS(2,1),
    ROTATE_LEFT_BACKWARDS(1,-2),
    DONT_MOVE(0, 0);

    private final int leftServo;
    private final int rightServo;

    ServoStatus(int servo1, int servo2) {
        this.leftServo = servo1;
        this.rightServo = servo2;
    }


    public int getLeftServoMultiplier() {
        return leftServo;
    }

    public int getRightServoMultiplier() {
        return rightServo;
    }
}
