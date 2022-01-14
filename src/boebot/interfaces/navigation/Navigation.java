package boebot.interfaces.navigation;


import boebot.interfaces.Updateable;
import boebot.interfaces.obstacledetector.ObstacleDetector;

public interface Navigation extends Updateable {
    void obstacleCallBack(ObstacleDetector obstacleDetector);

}
