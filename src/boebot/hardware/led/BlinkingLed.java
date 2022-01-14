package boebot.hardware.led;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import boebot.interfaces.Updateable;


public class BlinkingLed extends Led implements Updateable {
    private Timer timer;


    public BlinkingLed (int ledPin, int timerSpeed, boolean reversed) {
        super(ledPin, reversed);
        BoeBot.setMode(ledPin, PinMode.Output);
        this.timer = new Timer(timerSpeed);
    }

    /**
     * Method for toggling the led.
     */
    public void update() {
        if (this.timer.timeout()) {
            toggle();
        }
    }
}