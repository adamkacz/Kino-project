package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.AlreadyGivenBackException;
import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.NotSoldException;
import kaczmarczyk.projects.WrongNumberException;

import javax.swing.*;

/** Extension of the JDialog class enabling marking the ticket given back */
public class ReturnForm extends JDialog {
    /** Main platform */
    private final Cinema cinema;
    /** Parent window that ReturnForm interacts with */
    private final LoggedUserForm parent;
    private JPanel contentPane;
    private JTextField tfTicketNumber;
    private JButton btConfirm;
    private JButton btCancel;

    public ReturnForm(Cinema cinema, LoggedUserForm parent) {
        super();
        this.cinema = cinema;
        this.parent = parent;
        setContentPane(contentPane);
        setSize(500, 300);

        btConfirm.addActionListener(e -> {
            long number;
            try{
                number = Long.parseLong(tfTicketNumber.getText());
            }catch (Exception i){
                JOptionPane.showMessageDialog(null, "Błędny numer biletu",
                        "Komunikat zwrotu biletu", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                ReturnForm.this.cinema.removeTicket(number);
            }catch(NotSoldException i){
                JOptionPane.showMessageDialog(null, "Bilet o podanym numerze nie został sprzedany",
                        "Komunikat zwrotu biletu", JOptionPane.ERROR_MESSAGE);
            }catch(WrongNumberException o){
                JOptionPane.showMessageDialog(null, "Bilet o podanym numerze nie istnieje",
                        "Komunikat zwrotu biletu", JOptionPane.ERROR_MESSAGE);
            }catch(AlreadyGivenBackException u){
                JOptionPane.showMessageDialog(null, "Bilet o podanym numerze został już zwrócony",
                        "Komunikat zwrotu biletu", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Bilet zwrócony pomyślnie", "Komunikat zwrotu biletu",
                    JOptionPane.INFORMATION_MESSAGE);
            ReturnForm.this.parent.setVisible(true);
            ReturnForm.this.dispose();
        });
        btCancel.addActionListener(e -> ReturnForm.this.dispose());
    }
}
