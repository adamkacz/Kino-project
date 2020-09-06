package kaczmarczyk.projects.GUI;

import com.sun.jdi.JDIPermission;
import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.User;
import org.w3c.dom.events.Event;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/** Extension of the JDialog class enabling admin to use various administrative functions.
 * It is also a root for functional windows */
public class AdminForm extends JDialog{
    /** Main platform */
    private final Cinema cinema;
    /** Root JFrame object related with AdminForm by logging in and logging out */
    private final KinoGUI rootFrame;
    /** User got from logging root */
    private final User user;
    /** Child-forms to be closed if Admin form is closed */
    private ArrayList<JDialog> children = new ArrayList<>();
    private JPanel contentPane;
    private JLabel lbAdminPage;
    /** Button btToSeller enabling admin to use normal user's function */
    private JButton btToSeller;
    private JButton btLogOut;
    private JButton btChangePass;
    private JButton btAddProgram;
    private JButton btAddUser;
    private JButton btDeleteUser;
    private JButton btEditProgram;

    public AdminForm(Cinema cinema, User user, KinoGUI root){
        super();
        this.cinema = cinema;
        this.user = user;
        rootFrame = root;
        setContentPane(contentPane);
        lbAdminPage.setText(user.toString());
        setTitle("Konto administratora ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        btToSeller.grabFocus();


        btToSeller.addActionListener(e -> {
            AdminForm.this.dispatchEvent(new WindowEvent(AdminForm.this, WindowEvent.WINDOW_CLOSING));
            LoggedUserForm adminAsUser = new LoggedUserForm(AdminForm.this.cinema,
                    AdminForm.this.user, AdminForm.this.rootFrame);
            adminAsUser.setVisible(true);
        });

        btLogOut.addActionListener(e -> {
            dispatchEvent(new WindowEvent(AdminForm.this, WindowEvent.WINDOW_CLOSING));
            rootFrame.setVisible(true);
        });

        btChangePass.addActionListener(e -> {
            PasswordChangingForm passChange = new PasswordChangingForm(AdminForm.this.user);
            children.add(passChange);
            passChange.setVisible(true);
        });
        btToSeller.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                dispatchEvent(new WindowEvent(AdminForm.this, WindowEvent.WINDOW_CLOSING));
                LoggedUserForm adminAsUser = new LoggedUserForm(AdminForm.this.cinema,
                        AdminForm.this.user, AdminForm.this.rootFrame);
                adminAsUser.setVisible(true);            }
        });
        btAddProgram.addActionListener(e -> {
            AddProgramForm addProgramForm = new AddProgramForm(AdminForm.this.cinema);
            children.add(addProgramForm);
            addProgramForm.setVisible(true);
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for(JDialog dialog : children)
                    if(dialog != null && dialog.isDisplayable())
                        dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));

                AdminForm.this.dispose();
                rootFrame.setVisible(true);
            }
        });
        btEditProgram.addActionListener(e -> {
            EditProgramForm editProgramForm = new EditProgramForm(AdminForm.this.cinema);
            children.add(editProgramForm);
            editProgramForm.setVisible(true);
        });
        btAddUser.addActionListener(e -> {
            AddUserForm addUserForm = new AddUserForm(AdminForm.this.cinema);
            children.add(addUserForm);
            addUserForm.setVisible(true);
        });
        btDeleteUser.addActionListener(e -> {
            DeleteUserForm deleteUserForm = new DeleteUserForm(AdminForm.this.cinema);
            children.add(deleteUserForm);
            deleteUserForm.setVisible(true);
        });
    }
}
