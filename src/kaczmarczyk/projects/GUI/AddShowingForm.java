package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.*;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/** Extension of the JDialog class enabling adding a showing to the program showing list */
public class AddShowingForm extends JDialog{
    private final Program program;
    private final EditProgramForm root;
    private JPanel contentPane;
    private JComboBox<Film> cbFilm;
    private JRadioButton rbNewFilm;
    private JTextField tfTitle;
    private JTextField tfDirector;
    private JComboBox<Hall> cbHall;
    private JComboBox<DayOfWeek> cbWeekDay;
    private JButton btConfirm;
    private JSpinner spHour;
    private JSpinner spMinutes;

    public AddShowingForm(EditProgramForm root, Program program){
        super();
        this.root = root;
        this.program = program;

        setContentPane(contentPane);
        setSize(500,500);
        setTitle("Dodawanie seansu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tfDirector.setEnabled(false);
        tfTitle.setEnabled(false);
        spMinutes.setEnabled(false);


        for(Film f : this.program.films)
            cbFilm.addItem(f);

        for(Hall h : this.program.halls)
            cbHall.addItem(h);

        for(DayOfWeek d : DayOfWeek.values())
            cbWeekDay.addItem(d);

        if(cbFilm.getItemCount() == 0){
            cbFilm.setEnabled(false);
            tfDirector.setEnabled(true);
            tfTitle.setEnabled(true);
            spMinutes.setEnabled(true);
            rbNewFilm.setEnabled(false);
            rbNewFilm.setSelected(true);
        }

        rbNewFilm.addChangeListener(e -> {
            if(rbNewFilm.isSelected()){
                tfDirector.setEnabled(true);
                tfTitle.setEnabled(true);
                spMinutes.setEnabled(true);
                tfTitle.grabFocus();
                cbFilm.setEnabled(false);
            }else{
                tfDirector.setEnabled(false);
                tfTitle.setEnabled(false);
                spMinutes.setEnabled(false);
                cbFilm.setEnabled(true);
            }
        });
        btConfirm.addActionListener(e -> {
            if(rbNewFilm.isSelected()) {
                if (tfDirector.getText().equals("") || tfTitle.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Uzupełnij tytuł filmu i/lub reżysera",
                            "Komunikat dodania seansu", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if((int)spMinutes.getValue() == 0){
                    JOptionPane.showMessageDialog(null, "Film nie może trwąć 0 minut " +
                            "- uzupełnij czas trwania", "Komunikat dodania seansu", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalTime h = ((Date)spHour.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                h = LocalTime.of(h.getHour(),h.getMinute());
                Showing s = new Showing(new Film(tfTitle.getText(),tfDirector.getText(), (int)spMinutes.getValue()),
                        h, ((Hall)cbHall.getSelectedItem()).getHallNumber(), (DayOfWeek)cbWeekDay.getSelectedItem());
                try {
                    AddShowingForm.this.program.addShowing(s);
                    AddShowingForm.this.root.updateShowings(s);
                    JOptionPane.showMessageDialog(null, "Pomyślnie dodano",
                            "Komunikat dodania seansu", JOptionPane.INFORMATION_MESSAGE);
                    AddShowingForm.this.dispose();
                }catch(WrongHourException i){
                    JOptionPane.showMessageDialog(null, "Godziny seansu zachodzą na czas " +
                            "trwania innego seansu", "Komunikat dodania seansu", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                LocalTime h = ((Date)spHour.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                h = LocalTime.of(h.getHour(),h.getMinute());
                Showing s = new Showing((Film)cbFilm.getSelectedItem(), h,
                        ((Hall)cbHall.getSelectedItem()).getHallNumber(), (DayOfWeek)cbWeekDay.getSelectedItem());
                try {
                    AddShowingForm.this.program.addShowing(s);
                    AddShowingForm.this.root.updateShowings(s);
                    JOptionPane.showMessageDialog(null, "Pomyślnie dodano",
                            "Komunikat dodania seansu", JOptionPane.INFORMATION_MESSAGE);
                    AddShowingForm.this.dispose();
                }catch(WrongHourException i){
                    JOptionPane.showMessageDialog(null, "Godziny seansu zachodzą na czas " +
                            "trwania innego seansu", "Komunikat dodania seansu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Date date = new Date();
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(date, null,null, Calendar.MINUTE);
        spHour = new JSpinner(spinnerDateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spHour, "HH.mm");
        spHour.setEditor(editor);
        DateFormatter dateFormatter = (DateFormatter)editor.getTextField().getFormatter();
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spMinutes = new JSpinner(spinnerNumberModel);
        JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(spMinutes);
        spMinutes.setEditor(numberEditor);
        NumberFormatter numberFormatter = (NumberFormatter) numberEditor.getTextField().getFormatter();
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setOverwriteMode(true);

    }
}
