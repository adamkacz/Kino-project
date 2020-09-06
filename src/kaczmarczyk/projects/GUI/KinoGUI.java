package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.User;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/** Extension of the JFrame class that is the root frame of the program. Closing this frame will save all
 * the changes of the cinema platform
 */
public class KinoGUI extends JFrame {
    /** The main platform */
    public final Cinema cinema;
    public JPanel contentPane;
    public JTextField tfUserNumber;
    public JPasswordField pfPassword;
    private JButton btLogIn;


    public KinoGUI(Cinema cin, String title, String fileName){
        super(title);
        this.cinema = cin;
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                KinoGUI.this.cinema.serialize(fileName);
            }
        });


        btLogIn.addActionListener(e -> KinoGUI.this.logIn());

        tfUserNumber.addKeyListener(new KinoGUIKeyListener(this));
        pfPassword.addKeyListener(new KinoGUIKeyListener(this));
        btLogIn.addKeyListener(new KinoGUIKeyListener(this));
    }

    /** Validates user and let them log in to the window appropriate to the status held. */
    public void logIn(){
        long userNumber = -1;
        try {
            userNumber = Long.parseLong(tfUserNumber.getText());
        }catch(NumberFormatException i){
            JOptionPane.showMessageDialog(null,"Błędny numer użytkownika!",
                    "Komunikat logowania",JOptionPane.ERROR_MESSAGE);
            this.tfUserNumber.setText("");
            this.pfPassword.setText("");
            this.tfUserNumber.grabFocus();
            return;
        }
        User u = cinema.getUser(userNumber);
        if(u == null){
            JOptionPane.showMessageDialog(null,"Błędny numer użytkownika!",
                    "Komunikat logowania",JOptionPane.ERROR_MESSAGE);
            this.tfUserNumber.setText("");
            this.pfPassword.setText("");
            this.tfUserNumber.grabFocus();
            return;
        }
        if(!Arrays.equals(pfPassword.getPassword(),u.getPassword().toCharArray())){
            JOptionPane.showMessageDialog(null,"Błędne hasło!",
                    "Komunikat logowania",JOptionPane.ERROR_MESSAGE);
            this.pfPassword.setText("");
            this.pfPassword.grabFocus();
            return;
        }
        if(u.getUserStatus()){
            this.pfPassword.setText("");
            this.tfUserNumber.setText("");
            tfUserNumber.grabFocus();
            AdminForm adminForm = new AdminForm(cinema, u, this);
            adminForm.setVisible(true);
            this.setVisible(false);
        }
        else {
            this.pfPassword.setText("");
            this.tfUserNumber.setText("");
            tfUserNumber.grabFocus();
            LoggedUserForm loggedUser = new LoggedUserForm(cinema, u, this);
            loggedUser.setVisible(true);
            this.setVisible(false);
        }
    }
}
