package boebot.hardware.led;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class NeoPixel {
    private int pin;
    private boolean state;
    private Color color;
    public NeoPixel(int pin, Color color){

        if(pin >= 0 && pin <= 5) {
            this.pin = pin;
        }else{
            throw new IllegalArgumentException("Error, no neopixel with that pin.");
        }

        this.color = color;
        this.state = false;
    }

    /**
     * Used to toggle the NeoPixel.
     */
    public void toggle() {
        if(isToggled())
            off();
        else
            on();
    }

    public boolean isToggled() {return state;}

    public void on() {
        BoeBot.rgbSet(this.pin, this.color);
        state = true;
        BoeBot.rgbShow();
    }

    public void off(){
        BoeBot.rgbSet(this.pin, 0, 0, 0);
        state = false;
        BoeBot.rgbShow();
    }
}