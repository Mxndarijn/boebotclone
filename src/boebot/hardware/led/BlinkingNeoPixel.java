package boebot.hardware.led;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class BlinkingNeoPixel {
    private int pin;
    private boolean state;
    private Timer timer;
    private Color color;

    public BlinkingNeoPixel(int pin, int timerSpeed, Color color){
        if(pin >= 0 && pin <= 5) {
            this.pin = pin;
        }else{
            throw new IllegalArgumentException("Error, no neopixel with that pin.");
        }

        this.color = color;
        this.timer = new Timer(timerSpeed);
        this.state = false;
    }

    /**
     * Method used for sending a change to the NeoPixel. This method makes use of the other methods in this class for turning the NeoPixel on or off.
     */
    public void update(){
        if(this.timer.timeout()){
            state = !state;
            if(state){
                on();
            }else{
                off();
            }
        }
    }

    public boolean isToggled() {return state;}

    public void on() {
        BoeBot.rgbSet(this.pin, this.color);
        BoeBot.rgbShow();
    }

    public void off(){
        BoeBot.rgbSet(this.pin, 0, 0, 0);
        BoeBot.rgbShow();
    }
}