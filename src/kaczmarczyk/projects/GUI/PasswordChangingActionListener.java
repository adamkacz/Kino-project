package kaczmarczyk.projects.GUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**Listener for the PasswordChangingForm*/
public class PasswordChangingActionListener extends KeyAdapter {
    private final PasswordChangingForm dialog;

    public PasswordChangingActionListener(PasswordChangingForm change){
        dialog = change;
    }

    /**Starts password changing function when 'Enter' key is pressed*/
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            dialog.confirm();
        }
    }
}
