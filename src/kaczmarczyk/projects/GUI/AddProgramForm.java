package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.Cinema;
import kaczmarczyk.projects.Program;
import kaczmarczyk.projects.WrongDatesException;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/** Extension of the JDialog class enabling adding a program to the cinema program list */
public class AddProgramForm extends JDialog {
    private final Cinema cinema;
    private JPanel contentPane;
    private JSpinner spStartDate;
    private JSpinner spStopDate;
    private JButton btConfirm;

    public AddProgramForm(Cinema cinema){
        super();
        this.cinema = cinema;
        setTitle("Dodaj program");
        setSize(500,300);
        setContentPane(contentPane);
        btConfirm.addActionListener(e -> {
            if(((Date)spStopDate.getValue()).compareTo((Date)spStartDate.getValue()) < 0){
                JOptionPane.showMessageDialog(null, "Podan zakres dat jest nieprawidłowy " +
                                "- zostanie domyślnie zmieniony","Komunikat dodawania programu", JOptionPane.ERROR_MESSAGE);
            }
            LocalDate startDate = ((Date) spStartDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate stopDate = ((Date) spStopDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            try{
                AddProgramForm.this.cinema.addProgram(new Program(startDate, stopDate, AddProgramForm.this.cinema.halls));
            }catch(WrongDatesException i){
                JOptionPane.showMessageDialog(null,"Podany zakres dat zachodzi na zakres dat innego programu",
                "Komunikat dadania programu", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AddProgramForm.this.dispose();
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
}
