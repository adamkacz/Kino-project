package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.*;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/** Extension of the JDialog class enabling managing the programs of the cinema */
public class EditProgramForm extends JDialog{
    /** Main platform */
    private final Cinema cinema;
    private JDialog child;
    private Vector<Showing> showingLsData;
    private JPanel contentPane;
    private JComboBox<Program> cbProgram;
    private JList<Showing> lsShowings;
    private JButton btDelete;
    private JButton btAdd;
    private JButton btChangeDates;
    private JSpinner spStartDate;
    private JSpinner spStopDate;
    private JButton btDeleteP;
    private JButton btCopy;

    public EditProgramForm(Cinema cinema){
        super();
        this.cinema = cinema;
        setSize(700, 500);
        setTitle("Edycja programu");
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if(child != null && child.isDisplayable())
                    child.dispatchEvent(new WindowEvent(child, WindowEvent.WINDOW_CLOSING));

            }
        });

        ShowingRenderer showingRenderer = new ShowingRenderer();
        lsShowings.setCellRenderer(showingRenderer);

        for(Program p : this.cinema.programs){
            cbProgram.addItem(p);
        }
        if(cbProgram.getItemCount() > 0) {
            Program temp = (Program) cbProgram.getSelectedItem();
            Date startDate = Date.from(temp.getStartDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            Date stopDate = Date.from(temp.getStopDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            spStartDate.setValue(startDate);
            spStopDate.setValue(stopDate);
            this.showingLsData = new Vector<>(temp.showingList);
            showingLsData.sort(new ShowingComparator());
            lsShowings.setListData(showingLsData);
            if(temp.showingList.size() == 0){
                btDelete.setEnabled(false);
            }
        }else{
            cbProgram.setEnabled(false);
            lsShowings.setEnabled(false);
            btDelete.setEnabled(false);
            btAdd.setEnabled(false);
            btChangeDates.setEnabled(false);
            spStartDate.setEnabled(false);
            spStopDate.setEnabled(false);
            btDeleteP.setEnabled(false);
            btCopy.setEnabled(false);
        }

        btChangeDates.addActionListener(e -> EditProgramForm.this.changeDates((Program)cbProgram.getSelectedItem()));
        btDeleteP.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć program?",
                    "Komunikat usunięcia programu", JOptionPane.YES_NO_OPTION);
            if(choice == 0) {
                EditProgramForm.this.cinema.deleteProgram((Program) cbProgram.getSelectedItem());
                cbProgram.removeItemAt(cbProgram.getSelectedIndex());
            }
            if(cbProgram.getItemCount() == 0){
                lsShowings.removeAll();
                cbProgram.setEnabled(false);
                lsShowings.setEnabled(false);
                btDelete.setEnabled(false);
                btAdd.setEnabled(false);
                btChangeDates.setEnabled(false);
                spStartDate.setEnabled(false);
                spStopDate.setEnabled(false);
                btDeleteP.setEnabled(false);
                btCopy.setEnabled(false);
            }
        });
        btCopy.addActionListener(e -> {
            Program temp = ((Program)cbProgram.getSelectedItem()).clone();
            LocalDate start = ((Date)spStartDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate stop = ((Date)spStopDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            temp.startDate = start;
            temp.stopDate = stop;
            try {
                EditProgramForm.this.cinema.addProgram(temp);
                JOptionPane.showMessageDialog(null, "Pomyślnie dodano",
                        "Komunikat dodania programu", JOptionPane.INFORMATION_MESSAGE);
            }catch (WrongDatesException i){
                JOptionPane.showMessageDialog(null, "Daty programu zachodzą na obowiązywanie " +
                        "innego programu", "Komunikat dodania programu", JOptionPane.ERROR_MESSAGE);
            }
        });
        btDelete.addActionListener(e -> {
            if(lsShowings.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "Wybierz seans",
                        "Komunikat usuwania seansu", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Program temp = ((Program)cbProgram.getSelectedItem());
            temp.deleteShowing(lsShowings.getSelectedValue());
            showingLsData.remove(lsShowings.getSelectedValue());
            showingLsData.sort(new ShowingComparator());
            lsShowings.setListData(showingLsData);
            if(showingLsData.size() == 0){
                btDelete.setEnabled(false);
            }
        });
        btAdd.addActionListener(e -> {
            AddShowingForm addShowingForm = new AddShowingForm(EditProgramForm.this,
                    (Program)cbProgram.getSelectedItem());
            child = addShowingForm;
            addShowingForm.setVisible(true);
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date startDate = calendar.getTime();
        Date stopDate = calendar.getTime();
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(now, startDate, null, Calendar.DAY_OF_WEEK);
        SpinnerDateModel spinnerDateModel1 = new SpinnerDateModel(now, stopDate, null, Calendar.DAY_OF_WEEK);
        spStartDate = new JSpinner(spinnerDateModel);
        spStopDate = new JSpinner(spinnerDateModel1);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spStartDate, "dd.MM.yyyy");
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(spStopDate, "dd.MM.yyyy");
        spStartDate.setEditor(editor);
        spStopDate.setEditor(editor1);
        DateFormatter dateFormatter = (DateFormatter)editor.getTextField().getFormatter();
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);
        DateFormatter dateFormatter1 = (DateFormatter)editor1.getTextField().getFormatter();
        dateFormatter1.setAllowsInvalid(false);
        dateFormatter1.setOverwriteMode(true);
    }

    /** Extends the Cinema.changeProgramDates function to the form fitting the window application */
    public void changeDates(Program p){
        LocalDate start = ((Date)spStartDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate stop = ((Date)spStopDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            EditProgramForm.this.cinema.changeProgramDates(p, start, stop);
            JOptionPane.showMessageDialog(null, "Pomyślnie zmieniono daty",
                    "Komunikat dodania programu", JOptionPane.INFORMATION_MESSAGE);
        }catch(WrongDatesException i){
            JOptionPane.showMessageDialog(null, "Daty programu zachodzą na obowiązywanie " +
                    "innego programu", "Komunikat dodania programu", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Updates showing list when there is new added by the special JDialog window */
    public void updateShowings(Showing s){
        showingLsData.add(s);
        showingLsData.sort(new ShowingComparator());
        lsShowings.setListData(showingLsData);
        btDelete.setEnabled(true);
    }
}
