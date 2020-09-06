package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.User;

import javax.swing.*;

/** Extension of the JDialog class enabling deleting the user account from the cinema users list */
public class DeleteUserForm extends JDialog {
    /** Main platform */
    private final Cinema cinema;
    private JPanel contentPane;
    private JTextField tfUserNumber;
    private JButton btConfirm;

    public DeleteUserForm(Cinema cinema){
        super();
        this.cinema = cinema;

        setContentPane(contentPane);
        setTitle("Usuwanie użytkownika");
        setSize(300,500);

        btConfirm.addActionListener(e -> {
            long number;
            try {
                number = Long.parseLong(tfUserNumber.getText());
            }catch(NumberFormatException i){
                JOptionPane.showMessageDialog(null, "Podano błędny numer użytkownika",
                        "Komunikat usunięcia użytkownika", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User user = DeleteUserForm.this.cinema.users.stream().filter(u->u.getUserID() == number).findFirst().orElse(null);
            if(user == null){
                JOptionPane.showMessageDialog(null, "Numer użytkownika nie istnieje",
                        "Komunikat usunięcia użytkownika", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int choice = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć " +
                    "użytkownika: "+user.toString()+"?", "Komunikat usunięcia użytkownika", JOptionPane.YES_NO_OPTION);
            if(choice == 0){
                DeleteUserForm.this.cinema.deleteUser(user);
                DeleteUserForm.this.dispose();
            }
        });
    }
}
