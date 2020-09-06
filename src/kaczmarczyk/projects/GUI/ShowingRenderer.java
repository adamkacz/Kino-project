package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Showing;

import javax.swing.*;
import java.awt.*;

/**Allows to show full information about the showing in the showing list*/
public class ShowingRenderer extends DefaultListCellRenderer {
    public ShowingRenderer() {

    }
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus){
        super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        Showing s = (Showing) value;
        setText(s.toFullString());
        return this;
    }
}
