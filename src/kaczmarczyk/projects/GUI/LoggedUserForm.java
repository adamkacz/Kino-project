package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.User;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EventListener;

/** Extension of JDialog class enabling user to use normal cinema functions */
public class LoggedUserForm extends JDialog {
    /** Main platform */
    private final Cinema cinema;
    /** Logged user */
    private final User user;
    /** Root frame that is always displayed after logging out or closing account window */
    private final KinoGUI rootFrame;
    private final ArrayList<JDialog> children = new ArrayList<>();
    public JPanel contentPane;
    private JButton btChangePass;
    private JButton btSellTicket;
    private JButton btReturn;
    private JButton btLogOut;
    private JLabel lbUser;


    public LoggedUserForm(Cinema cinema, User user, KinoGUI root) {
        super();
        this.cinema = cinema;
        this.user = user;
        this.rootFrame = root;
        lbUser.setText(user.toString());
        setContentPane(this.contentPane);
        setTitle("Konto użytkownika ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,300);
        if(user.getUserStatus()) {
            btLogOut.setText("Wyloguj");
            btChangePass.setText("Powrót do widoku administratora");
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for(JDialog dialog : children)
                    if(dialog != null && dialog.isDisplayable())
                        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));

                LoggedUserForm.this.dispose();
                rootFrame.setVisible(true);
            }
        });

        btChangePass.addActionListener(e -> {
            if(LoggedUserForm.this.user.getUserStatus()) {
                dispatchEvent(new WindowEvent(LoggedUserForm.this, WindowEvent.WINDOW_CLOSING));
                AdminForm adminForm = new AdminForm(LoggedUserForm.this.cinema, LoggedUserForm.this.user,
                        LoggedUserForm.this.rootFrame);
                adminForm.setVisible(true);
            }else {
                LoggedUserForm.this.passwordChanging();
            }
        });

        btReturn.addActionListener(e -> {
            ReturnForm returnForm = new ReturnForm(LoggedUserForm.this.cinema,LoggedUserForm.this);
            children.add(returnForm);
            returnForm.setVisible(true);
        });

        btChangePass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (LoggedUserForm.this.user.getUserStatus()) {
                        dispatchEvent(new WindowEvent(LoggedUserForm.this, WindowEvent.WINDOW_CLOSING));
                        AdminForm adminForm = new AdminForm(LoggedUserForm.this.cinema, LoggedUserForm.this.user,
                                LoggedUserForm.this.rootFrame);
                        adminForm.setVisible(true);
                        return;
                    }
                    LoggedUserForm.this.passwordChanging();
                }
            }
        });

        btLogOut.addActionListener(e -> {
            dispatchEvent(new WindowEvent(LoggedUserForm.this, WindowEvent.WINDOW_CLOSING));
            rootFrame.setVisible(true);
        });
        btSellTicket.addActionListener(e -> {
            AvailabilityForm availabilityForm = new AvailabilityForm(LoggedUserForm.this.cinema);
            children.add(availabilityForm);
            availabilityForm.setVisible(true);
        });
    }

    public void passwordChanging() {
        PasswordChangingForm passChange = new PasswordChangingForm(user);
        children.add(passChange);
        passChange.setVisible(true);
    }
}
