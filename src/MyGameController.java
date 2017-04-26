/**
 * Created by Artem on 23.04.2017.
 */
public class MyGameController {
    GameProcessModel myGameProcessModel;
    public MyGameController(GameProcessModel gameProcessModel)
    {
        myGameProcessModel = gameProcessModel;
    }

    void SendCommandToModel(Command command)
    {
        myGameProcessModel.DoCommand(command);
    }
}
