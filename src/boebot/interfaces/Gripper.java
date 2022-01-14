package boebot.interfaces;
import TI.Timer;
import boebot.hardware.servo.Servo;

public class Gripper implements Updateable {

    private int pin;
    private Servo servo;
    private Timer timer;
    private int current;
    private int target;
    private int delay;
    private GripperCallback callback;

    public Gripper(int pin, int delay, GripperCallback callback) {
        this.pin = pin;
        this.delay = delay;
        this.servo = new Servo(pin);
        this.callback = callback;
        this.current = 1599;
        this.target = 1600;
        this.timer = new Timer(delay);
        open();
    }

    public void open() {
        System.out.println("opening gripper");
        this.target = 1500;
    }

    public void close() {
        System.out.println("closing gripper");
        this.target = 900;
    }

    @Override
    public void update()
    {
        if(timer.timeout())
        {
            if (this.target - this.current == -1)
                this.callback.OnGripperClose();
            if (this.target - this.current == 1)
                this.callback.OnGripperClose();


            this.current += 6 * Math.signum(this.target - this.current);
            this.servo.move(this.current);
        }
    }

    public boolean isOpen() {
        return this.target == 1500;
    }
}
