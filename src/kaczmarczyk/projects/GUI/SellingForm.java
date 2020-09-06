package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.PotentialTicket;
import kaczmarczyk.projects.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** Extension of the JDialog class showing the chosen tickets and enabling selling them */
public class SellingForm extends JDialog {
    private final ArrayList<PotentialTicket> potentialTickets;
    private final Cinema cinema;
    private final AvailabilityForm parent;
    private JPanel contentPane;
    private JButton btSell;
    private JButton btBack;
    private JButton btCancel;
    private JPanel ticketPane;
    private JLabel lbSum;
    private JRadioButton[] radioButtons;
    private JLabel[] jLabels;

    public SellingForm(Cinema cinema, ArrayList<PotentialTicket> potentialTickets, AvailabilityForm parent){
        super();
        this.potentialTickets = potentialTickets;
        this.cinema = cinema;
        this.parent = parent;
        setTitle("Sprzedaj bilet");
        setContentPane(contentPane);
        setSize(700, 500);
        countPrice();
        btSell.addActionListener(e -> {
            int i = 0;
            for(PotentialTicket t : SellingForm.this.potentialTickets){
                kaczmarczyk.projects.Type type;
                if(radioButtons[i].isSelected())
                    type = kaczmarczyk.projects.Type.FULL;
                else
                    type = kaczmarczyk.projects.Type.REDUCED;

                Ticket ticket = new Ticket(t,type);
                SellingForm.this.cinema.createTicket(ticket);
            }
            JOptionPane.showMessageDialog(null, "Sprzedaż zakońcona pomyślnie",
                    "Komunikat sprzedaży", JOptionPane.INFORMATION_MESSAGE);
            SellingForm.this.dispose();
            SellingForm.this.parent.dispose();
        });
        btBack.addActionListener(e -> SellingForm.this.dispose());
        btCancel.addActionListener(e -> {
            SellingForm.this.dispose();
            SellingForm.this.parent.dispose();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ticketPane = new JPanel();
        JPanel panelToScroll = new JPanel(new GridLayout(potentialTickets.size(), 3));
        panelToScroll.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        JTextPane[] textPanes = new JTextPane[potentialTickets.size()];
        this.radioButtons = new JRadioButton[potentialTickets.size()];
        this.jLabels = new JLabel[potentialTickets.size()];
        int i = 0;
        for(PotentialTicket ticket : potentialTickets){
            textPanes[i] = new JTextPane();
            textPanes[i].setText(ticket.toString());
            textPanes[i].setEnabled(false);
            textPanes[i].setDisabledTextColor(Color.black);
            textPanes[i].setBackground(Color.lightGray);
            radioButtons[i] = new JRadioButton("Ulgowy");
            radioButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            radioButtons[i].setActionCommand(Integer.toString(i));
            radioButtons[i].addActionListener(e -> {
                int i1 = Integer.parseInt(e.getActionCommand());
                if(radioButtons[i1].isSelected())
                    jLabels[i1].setText((Math.round(Ticket.price*(1-Ticket.discount)*100)/(double)100)+" zł");
                else
                    jLabels[i1].setText(Ticket.price+" zł");

                countPrice();
            });
            jLabels[i] = new JLabel(Ticket.price+" zł");
            jLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            panelToScroll.add(textPanes[i]);
            panelToScroll.add(radioButtons[i]);
            panelToScroll.add(jLabels[i]);
            ++i;
        }
        JScrollPane panel = new JScrollPane(panelToScroll);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.setPreferredSize(new Dimension(680, 350));
        ticketPane.add(panel);
    }

    public void countPrice(){
        double sum = 0.0;
        for(JLabel label : jLabels){
            String text = label.getText();
            text = text.replaceFirst(" zł", "");
            sum += Double.parseDouble(text);
        }
        lbSum.setText("Suma: "+sum+" zł");
    }
}
