package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.FormatException;
import kaczmarczyk.projects.User;

import javax.swing.*;

/** Extension of the JDialog class enabling adding a user to the cinema users list */
public class AddUserForm extends JDialog{
    private final Cinema cinema;
    private JPanel contentPane;
    private JTextField tfUserNumber;
    private JTextField tfName;
    private JTextField tfSurname;
    private JTextField tfPassword;
    private JButton btConfirm;
    private JButton btCancel;
    private JRadioButton rdIsAdmin;

    public AddUserForm(Cinema cinema){
        super();
        this.cinema = cinema;
        tfUserNumber.setText(Long.toString(User.getID()));
        tfUserNumber.setEnabled(false);
        setSize(500,500);
        setContentPane(contentPane);
        setTitle("Dodawanie użytkownika");

        btConfirm.addActionListener(e -> {
            if(tfName.getText().equals("") || tfSurname.getText().equals("") || tfPassword.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Uzupełnij dane użytkownika",
                        "Komunikat dodania użytkownika", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                AddUserForm.this.cinema.addUser(tfName.getText(), tfSurname.getText(),rdIsAdmin.isSelected(),
                        tfPassword.getText());
                JOptionPane.showMessageDialog(null, "Użytkownik dodany pomyślnie",
                        "Komunikat dodania użytkownika", JOptionPane.INFORMATION_MESSAGE);
                AddUserForm.this.dispose();
            } catch(FormatException i){
                JOptionPane.showMessageDialog(null, "Podane dane są nieprawidłowe",
                        "Komunikat dodania użytkownika", JOptionPane.ERROR_MESSAGE);
            }
        });
        btCancel.addActionListener(e -> AddUserForm.this.dispose());
    }
}
