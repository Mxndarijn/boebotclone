package boebot.hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.SerialConnection;
import boebot.config.Configuration;
import boebot.interfaces.Updateable;

import java.util.HashMap;

public class Bluetooth implements Updateable {
    private HashMap<String, BluetoothCallBack> callbacks;
    private SerialConnection serialConnection;
    private String latestMessage;

    public Bluetooth(Configuration config) {
        this.callbacks = new HashMap<>();
        serialConnection = new SerialConnection(115200);
        latestMessage = "";
    }

    @Override
    public void update() {
        if(serialConnection.available() > 0) {
            System.out.println("serial available");
            if(latestMessage.endsWith("]"))
                latestMessage = "";
            while(serialConnection.available() > 0) {
                if(latestMessage.startsWith("["))
                    latestMessage += (char) serialConnection.readByte();
                else if((char) serialConnection.readByte() == '[') {
                    latestMessage = "[";
                }
            }
            if(latestMessage.startsWith("[") && latestMessage.endsWith("]")) {
                latestMessage = latestMessage.substring(1, latestMessage.length()-2);
                for(String s : callbacks.keySet()) {
                    if(latestMessage.contains(s)) {
                        String extraMessage = latestMessage.replaceFirst(s, "");
                        callbacks.get(latestMessage).onButtonPress(latestMessage, extraMessage);
                    }
                }
            }
        }
    }

    public void sendMessage(String message) {
        for(char s : message.toCharArray()) {
            serialConnection.writeByte(s);
        }
    }

    public void registerCallBack(String message, BluetoothCallBack bluetoothCallBack) {
        this.callbacks.put(message, bluetoothCallBack);
    }
}
