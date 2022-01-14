import TI.BoeBot;
import boebot.application.Robot;
import boebot.config.BB0117;

public class RobotMain {


    public static void main(String[] args) {
        Robot robot = new Robot(new BB0117());
        while(true) {
            robot.update();
            BoeBot.wait(1);
        }
    }

}
