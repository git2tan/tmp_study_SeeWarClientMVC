import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Created by Artem on 23.04.2017.
 */
public class MyView implements Observer{
    GameProcessModel myGameProcessModel;
    MyGameController myGameController;
    JFrame curFrame;                        // указатель на текущий фрейм
    Stack<JFrame> myStack;                  //
    JButton backButton;

    JFrame startFrame;
    JButton connectButton;
    JLabel connectStateLable;
    JTextField connectIPField;

    JFrame loginFrame;
    JTextField loginTextField;
    JPasswordField loginPasswordField;
    JButton loginButton;
    JButton loginRegisterButton;
    JLabel loginLableMessage;




    public MyView(GameProcessModel gameProcessModel, MyGameController gameController)
    {

        this.myGameProcessModel = gameProcessModel;
        this.myGameController = gameController;
        myStack = new Stack<JFrame>();

        backButton = new JButton("<-");
        backButton.setSize(50,50);
        backButton.setLocation(300,280);
        backButton.addActionListener(event->{
            if(!myStack.isEmpty())
            {curFrame.dispose();
            curFrame = popFrame();
            curFrame.setVisible(true);
            backButton.setVisible(true);}
        });
        backButton.setVisible(true);
        backButton.setEnabled(false);

        //окно которое стартует сразу после старта приложения
        startFrame = new JFrame();
        startFrame.setSize(720,1280);
        startFrame.setResizable(false);
        startFrame.setTitle("SeeWar:Start");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(false);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.setSize(700,1200);
        myPanel.setLocation(0,0);
        myPanel.setVisible(true);
        connectIPField = new JTextField("192.168.1.0");
        connectIPField.setSize(150,20);
        connectIPField.setLocation(300,170);

        connectButton = new JButton("Connect");
        connectButton.setSize(100,50);
        connectButton.setLocation(300,200);

        connectButton.addActionListener(event ->{
            Command com = new Command(IDCommand.tryToConnect, connectIPField.getText());
            myGameController.SendCommandToModel(com);
        });

        connectStateLable = new JLabel("Отключен от сервера");
        connectStateLable.setSize(150,20);
        connectStateLable.setLocation(300,250);

        myPanel.add(connectIPField);
        myPanel.add(connectStateLable);
        myPanel.add(connectButton);
        myPanel.add(backButton);
        startFrame.add(myPanel);


        //Окно логина
        loginFrame = new JFrame();
        loginFrame.setSize(720,1280);
        loginFrame.setResizable(false);
        loginFrame.setTitle("SeeWar:Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(false);
        //loginFrame.setLayout(null);


        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setSize(300,300);
        //loginPanel.setLocation(300,600);
        loginPanel.setVisible(true);

        loginTextField = new JTextField("login");
        loginTextField.setSize(new Dimension(100,20));
        loginTextField.setLocation(310,100);

        loginPasswordField = new JPasswordField("password");
        loginPasswordField.setSize(new Dimension(100,20));
        loginPasswordField.setLocation(310,130);

        loginButton = new JButton("Вход");
        loginButton.setSize(100,30);
        loginButton.setLocation(310,160);
        loginButton.addActionListener(event->{
            myGameController.SendCommandToModel(new Command(IDCommand.tryToLogin, loginTextField.getText(), loginPasswordField.getText())); });

        loginRegisterButton = new JButton("Регистрация");
        loginRegisterButton.setSize(100,30);
        loginRegisterButton.setLocation(310,200);
        loginRegisterButton.addActionListener(event->{
            myGameController.SendCommandToModel(new Command(IDCommand.tryToLogin, loginTextField.getText(), loginPasswordField.getText())); });

        loginLableMessage = new JLabel("Пользователей с такими данными не найдено");
        loginLableMessage.setSize(300,20);
        loginLableMessage.setLocation(210,70);
        loginLableMessage.setVisible(false);

        loginPanel.add(loginLableMessage);
        loginPanel.add(loginTextField);
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);
        loginPanel.add(loginRegisterButton);
        loginPanel.add(backButton);
        loginFrame.add(loginPanel);
        //loginFrame.add(backButton);

        curFrame = startFrame;
        curFrame.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (myGameProcessModel.getCurState())
        {
            case disconnectState:
            {
                if(curFrame != startFrame) {
                    curFrame.dispose();

                    curFrame = startFrame;
                    curFrame.setVisible(true);
                }

                connectButton.setVisible(true);
                connectStateLable.setVisible(true);
            } break;
            case canNotConnectToServer:
            {
                connectButton.setVisible(true);
                connectStateLable.setText("не могу подключиться к серверу...");
            } break;
            case connectedState:
            {
                if(curFrame != loginFrame){
                    if(curFrame == startFrame)
                        pushFrame(curFrame);

                    curFrame.dispose();
                curFrame = loginFrame;

                curFrame.setVisible(true);
                }
            } break;
            case errorLoginState:
            {
                loginLableMessage.setVisible(true);
            } break;
        }
    }

    private void pushFrame(JFrame frame){
        myStack.push(frame);
        backButton.setEnabled(true);
    }

    private JFrame popFrame(){
        JFrame tmp = myStack.pop();
            backButton.setEnabled(!myStack.isEmpty());
        return tmp;
    }
}
