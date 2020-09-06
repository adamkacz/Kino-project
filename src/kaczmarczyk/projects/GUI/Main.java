package kaczmarczyk.projects.GUI;

import kaczmarczyk.projects.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/** Main program class */
public class Main {
    public static void main(String[] args) {
        String fileName = "cinema.bin";
        Cinema cinema;
        int choice = 0;
        if(choice == 1) {
            Hall hall = new Hall(1);
            hall.addRow(15);
            hall.addRow(14);
            hall.addRow(10);
            Hall hall1 = new Hall(2);
            int i;
            Random random = new Random();
            for (i = 0; i < 13; ++i) {
                hall1.addRow(13 + random.nextInt(5) * (int) Math.pow(-1, random.nextInt(6)));
            }
            ArrayList<Hall> halls = new ArrayList<>();
            halls.add(hall);
            halls.add(hall1);
            for (i = 0; i < 3; ++i) {
                Hall newHall = new Hall(i + 3);
                int randomRows = 10 + random.nextInt(5) * (int) Math.pow(-1, random.nextInt(6));
                for (int j = 0; j < randomRows; ++j) {
                    int randomSeats = 13 + random.nextInt(7) * (int) Math.pow(-1, random.nextInt(6));
                    newHall.addRow(randomSeats);
                }
                halls.add(newHall);
            }
            cinema = new Cinema(halls, 0, 1);
            try {
                cinema.addUser("Adam", "Kaczmarczyk", true, "Pass1234");
                cinema.addUser("Adam", "Kaczmarczyk", false, "Pass1234");
            } catch (FormatException ignore) {

            }
            Program program = new Program(LocalDate.of(2020, 7, 31),
                    LocalDate.of(2020, 11, 11), cinema.halls);
            Film film = new Film("Film testowy", "Reżyser testowy", 80);
            Showing s1 = new Showing(film, LocalTime.of(14, 30), hall.getHallNumber(), DayOfWeek.WEDNESDAY);
            Showing s2 = new Showing(film, LocalTime.of(17, 30), hall.getHallNumber(), DayOfWeek.THURSDAY);
            Showing s3 = new Showing(film, LocalTime.of(11, 15), hall.getHallNumber(), DayOfWeek.FRIDAY);
            Showing s4 = new Showing(film, LocalTime.of(20, 0), hall.getHallNumber(), DayOfWeek.MONDAY);
            try {
                program.addShowing(s1);
                program.addShowing(s2);
                program.addShowing(s3);
                program.addShowing(s4);
            } catch (WrongHourException ignore) {
                System.out.println("Jest niedobrze");
            }
            try {
                cinema.addProgram(program);
            } catch (WrongDatesException ignore) {

            }
        }else {
            cinema = Cinema.deserialize(fileName);
        }
        KinoGUI mainFrame = new KinoGUI(cinema, "Cinema GUI", fileName);
        mainFrame.setVisible(true);
    }
}
