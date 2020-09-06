package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.FormatException;
import kaczmarczyk.projects.User;

import javax.swing.*;
import java.util.Arrays;

/** Extension of JDialog class enabling user to change password */
public class PasswordChangingForm extends JDialog {
    /** Logged user changing their password */
    private final User user;
    public JPanel contentPane;
    private JPasswordField pfOldPass;
    private JPasswordField pfNewPass;
    private JPasswordField pfRepeatPass;
    private JButton btConfirm;

    public PasswordChangingForm(User u) {
        super();
        this.user = u;
        setContentPane(contentPane);
        setTitle("Zmiana hasła");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,300);

        btConfirm.addActionListener(e -> {
            confirm();
        });
        pfOldPass.addKeyListener(new PasswordChangingActionListener(this));
        pfNewPass.addKeyListener(new PasswordChangingActionListener(this));
        pfRepeatPass.addKeyListener(new PasswordChangingActionListener(this));
        btConfirm.addKeyListener(new PasswordChangingActionListener(this));
    }

    /** Validates data - password given */
    public void confirm(){
        if (!Arrays.equals(user.getPassword().toCharArray(), pfOldPass.getPassword())) {
            JOptionPane.showMessageDialog(null, "Błędne hasło!",
                    "Komunikat zmiany hasła", JOptionPane.ERROR_MESSAGE);
            pfOldPass.setText("");
            pfNewPass.setText("");
            pfRepeatPass.setText("");
            pfOldPass.grabFocus();
            return;
        }

        if (!Arrays.equals(pfNewPass.getPassword(), pfRepeatPass.getPassword())) {
            JOptionPane.showMessageDialog(null,
                    "Nowe hasło i hasło powtórzone musza być jednakowe",
                    "Komunikat zmiany hasła", JOptionPane.ERROR_MESSAGE);
            pfNewPass.setText("");
            pfRepeatPass.setText("");
            pfNewPass.grabFocus();
            return;
        }

        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pfNewPass.getPassword().length; ++i) {
                sb.append(pfNewPass.getPassword()[i]);
            }
            user.setPassword(sb.toString());
        } catch (FormatException i) {
            JOptionPane.showMessageDialog(null,
                    "Podane hasło nie spełnia wymagań:" +
                            "\n - 5-40 liter," +
                            "\n - co najmniej jedna duża, " +
                            "\n - co najmniej jedna mała " +
                            "\n - i co najmniej jedna cyfra.",
                    "Komunikat zmiany hasła", JOptionPane.ERROR_MESSAGE);
            pfNewPass.setText("");
            pfRepeatPass.setText("");
            pfNewPass.grabFocus();
            return;
        }
        JOptionPane.showMessageDialog(null, "Hasło zmienione pomyślnie",
                "Komunikat zmiany hasła", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
}
