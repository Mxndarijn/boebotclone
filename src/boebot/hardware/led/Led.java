package boebot.hardware.led;

import TI.BoeBot;
import TI.PinMode;

public class Led {
    private boolean state;
    protected int pin;
    private boolean reversed;


    public Led(int pin, boolean reversed) {
        this.pin = pin;
        this.state = false;
        this.reversed = reversed;
        BoeBot.setMode(pin, PinMode.Output);
    }

    /*
    Turns the led on, off or toggles the led.
     */
    public void on() {
        state = true;
        if(!reversed)
            BoeBot.digitalWrite(pin, true);
        else
            BoeBot.digitalWrite(pin, false);

    }

    /**
     * Turns the led off
     */
    public void off() {
        state = false;
        if(!reversed)
            BoeBot.digitalWrite(pin, false);
        else if(reversed)
            BoeBot.digitalWrite(pin, true);
    }

    /**
     * Toggles the led.
     */
    public void toggle() {
        if(state)
            off();
        else
            on();
    }

    /**
     * Gets the state of the led.
     * @return True if the led is on, false if the led is off.
     */
    public boolean getState() {
        return state;
    }
}


