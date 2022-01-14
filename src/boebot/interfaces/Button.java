package boebot.interfaces;

import TI.BoeBot;
import TI.PinMode;

public class Button implements Updateable {
    private int buttonPin;
    private ButtonCallBack buttonCallBack;
    private boolean state;

    public Button (int buttonPin, ButtonCallBack callBack) {
        this.buttonPin = buttonPin;
        this.buttonCallBack = callBack;
        BoeBot.setMode(buttonPin, PinMode.Input);
        this.state = BoeBot.digitalRead(buttonPin);
    }

    @Override
    public void update() {
        boolean pressed = BoeBot.digitalRead(buttonPin);
        if(pressed != state) {
            buttonCallBack.buttonCallBack(this);
            state = pressed;
        }
    }
}
