import java.util.Observable;

/**
 * Created by Artem on 23.04.2017.
 */
enum MyStates
{
    disconnectState,
    canNotConnectToServer,
    connectedState,
    errorLoginState,
    isLoginState,
}
public class GameProcessModel extends Observable {
    MyStates curState;

    public GameProcessModel()
    {
        curState = MyStates.disconnectState;
    }
    public void DoCommand(Command command)
    {
        switch (command.idCommand){
            case tryToConnect:
            {
                curState = MyStates.connectedState;
                setChanged();
            } break;
            case tryToLogin:
            {
                if (!command.login.equals("admin") || !command.pass.equals("123"))
                    curState = MyStates.errorLoginState;
                else
                    curState = MyStates.isLoginState;
                setChanged();
            }
        }
        notifyObservers();
    }
    public MyStates getCurState()
    {
        return curState;
    }
}
