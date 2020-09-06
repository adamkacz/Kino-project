package kaczmarczyk.projects;

import java.time.DayOfWeek;

public class Main {

    public static void main(String[] args) {
	    /*Film film = new Film("Film testowy", "Reżyser testowy", 30);
        System.out.println(film);
        LocalTime hour = LocalTime.of(7,0);
        LocalTime hour1 = LocalTime.of(8,0);*/
        //Seat seat = new Seat(1,2);
        //Days day = Days.SUN;
        //System.out.println(day);
        DayOfWeek d1 = DayOfWeek.MONDAY;
        DayOfWeek d2 = DayOfWeek.THURSDAY;
        System.out.println(d1.compareTo(d2));
    }
}
