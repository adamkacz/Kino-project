package kaczmarczyk.projects.GUI;

import javax.swing.*;

/**Extension of the JButton class including indication whether it was clicked even or uneven number of times*/
public class MagicSeatButton extends JButton {
    boolean isYellow;
    public MagicSeatButton(){
        super();
        isYellow = false;
    }
}
