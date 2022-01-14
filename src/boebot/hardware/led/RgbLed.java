package boebot.hardware.led;

import TI.BoeBot;
import TI.PWM;
import TI.PinMode;
import TI.Timer;
import boebot.interfaces.Updateable;

import java.awt.*;

public class RgbLed implements Updateable {
    private boolean state;
    private Timer timer;
    private PWM redPWM;
    private PWM greenPWM;
    private PWM bluePWM;

    /**
     *
     * @param redPin The pin for the red light in the LED.
     * @param greenPin The pin for the green light in the LED.
     * @param bluePin The pin for the blue light in the LED.
     * @param timerSpeed The time for the timer in milliseconds.
     * @param dutyCycle Percentage at which the signal is high.
     */
    public RgbLed (int redPin, int greenPin, int bluePin, int timerSpeed, int dutyCycle) {
        BoeBot.setMode(redPin, PinMode.Output);
        BoeBot.setMode(greenPin, PinMode.Output);
        BoeBot.setMode(bluePin, PinMode.Output);
        this.state = false;
        this.timer = new Timer(timerSpeed);
        this.redPWM = new PWM(redPin, dutyCycle);
        this.bluePWM = new PWM(bluePin, dutyCycle);
        this.greenPWM = new PWM(greenPin, dutyCycle);
        this.redPWM.stop();
        this.bluePWM.stop();
        this.greenPWM.stop();
    }

    public void update() {
        if (this.timer.timeout()) {
            state = !state;
            if (state) {
                on();
            } else
                off();
        }
    }

    public void changeCycle (Color color) {
        redPWM.update(color.getRed());
        greenPWM.update(color.getGreen());
        bluePWM.update(color.getBlue());
        state = true;
    }

    public void on() {
        if(!state) {
            state = true;
            redPWM.start();
            greenPWM.start();
            bluePWM.start();
        }
    }

    public void off() {
        if(state) {
            state = false;
            redPWM.stop();
            greenPWM.stop();
            bluePWM.stop();
        }
    }

    public boolean isToggled() {
        return state;
    }
}
