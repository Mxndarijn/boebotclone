package boebot.hardware;

import TI.BoeBot;
import TI.PinMode;

public class Buzzer {

    private int pin;
    public Buzzer(int pin) {
        this.pin = pin;
        BoeBot.setMode(pin, PinMode.Output);
    }

    /**
     With this method the buzzer outputs a sound for 10 ms.
     @param freq The pitch the buzzer makes.
     */
    public void buzz(int freq) {
        BoeBot.freqOut(pin, freq, 500);
    }
}
