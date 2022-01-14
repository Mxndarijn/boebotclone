package boebot.hardware;
import TI.BoeBot;
import TI.PinMode;
import boebot.config.Configuration;
import boebot.interfaces.Updateable;

import java.util.Arrays;
import java.util.HashMap;

public class Remote implements Updateable {

    public static final int buttonOne =     0b10000000;
    public static final int buttonTwo =     0b10000001;
    public static final int buttonThree =   0b10000010;
    public static final int buttonFour =    0b10000011;
    public static final int buttonFive =    0b10000100;
    public static final int buttonSix =     0b10000101;
    public static final int buttonSeven =   0b10000110;
    public static final int buttonEight =   0b10000111;
    public static final int buttonNine =    0b10001000;
    public static final int emergencyStop = 0b10010101;
    public static final int modeSwitch =    0b10011101;
    public static final int gripperButton = 0b10010100;
    public static final int buttonZero =    0b10001001;

    private int pin;
    private HashMap<Integer, RemoteCallBack> callbacks;

    public Remote(Configuration config) {
        this.pin = config.getIRPin();
        this.callbacks = new HashMap<>();
        BoeBot.setMode(this.pin, PinMode.Input);
    }

    @Override
    public void update() {

        // Check if start pulse has occured.
        int pulseLen = BoeBot.pulseIn(this.pin, false, 6000);
        if (pulseLen > 2000) {
            int lengths[] = new int[12];

            // Reading out data.
            for (int i = 0; i < lengths.length; i++) {
                lengths[i] = BoeBot.pulseIn(this.pin, false, 20000);
            }

            // Build code.
            int code = 0;
            for (int i = 0; i < lengths.length; i++) {
                if (lengths[i] > 1000) {
                    code |= 1 <<  i;
                }
                // Execute callback.
            }
            System.out.println("found code: " + code + " in bytes: " + Integer.toBinaryString(code));
            if (callbacks.containsKey(code))
                callbacks.get(code).onButtonPress(code);
        }
    }

    public void registerCallBack(int code, RemoteCallBack remoteCallBack) {
        callbacks.put(code, remoteCallBack);
    }

}