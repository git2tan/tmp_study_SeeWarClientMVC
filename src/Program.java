import javax.swing.*;
import java.awt.*;

/**
 * Created by Artem on 23.04.2017.
 */
public class Program {
    public static void main(String[] args){
        GameProcessModel myGameProcess = new GameProcessModel();
        MyGameController myGameController = new MyGameController(myGameProcess);


        MyView myView = new MyView(myGameProcess, myGameController);
        myGameProcess.addObserver(myView);
    }
}
