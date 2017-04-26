/**
 * Created by Artem on 23.04.2017.
 */
enum IDCommand{
    tryToConnect,
    tryToLogin
}
public class Command {
    public String login;
    public String pass;
    public IDCommand idCommand;
    public String address;

    public Command(IDCommand idCommand){
        this.idCommand = idCommand;
    }
    public Command(IDCommand idCommand, String text){
        switch(idCommand){
            case tryToConnect:{
                if(text.isEmpty())
                    address = "стандартный IP";
                else
                    address = text;
            } break;
        }
        this.idCommand = idCommand;

    }

    public Command(IDCommand idCommand, String login, String pass){
        this.idCommand = idCommand;
        this.login = login;
        this.pass = pass;
    }
}
