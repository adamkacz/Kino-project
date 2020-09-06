package kaczmarczyk.projects.GUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**Creates listener for the main frame*/
public class KinoGUIKeyListener extends KeyAdapter {
    private final KinoGUI kinoGUI;

    public KinoGUIKeyListener(KinoGUI kg){
        this.kinoGUI = kg;
    }

    /**Starts function when the 'Enter' key is pressed*/
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            kinoGUI.logIn();
        }
    }
}
