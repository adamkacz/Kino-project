package kaczmarczyk.projects;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;


/** Represents a film showing that is planned for an indicated day of week*/
public class Showing implements Serializable, Comparator<Showing> {
    Film film;
    LocalTime hour;
    int hall;
    DayOfWeek dayOfWeek;


    public Showing(){

    }

    public Showing(Film film, LocalTime hour, int hall, DayOfWeek dayOfWeek){
        this.film = film;
        this.hour = hour;
        this.hall = hall;
        this.dayOfWeek = dayOfWeek;
    }
    /** Ordinary toString method for the list of hours of the film showing */
    @Override
    public String toString() {
        return hour.toString();
    }

    /** toString method for ticket printing purposes*/
    public String toTicketString(){
        final StringBuilder sb = new StringBuilder();
        sb.append(film.toString()).append(" \n");
        sb.append(hour.toString()).append(" \n");
        sb.append("Sala ").append(hall);
        return sb.toString();
    }

    /** toString method including all the information about the showing*/
    public String toFullString(){
        final StringBuilder sb = new StringBuilder();
        sb.append(dayOfWeek).append("; ");
        sb.append(hour.toString()).append("; ");
        sb.append(film.toString()).append("; ");
        sb.append("Sala ").append(hall);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Showing))
            return false;

        Showing s = (Showing) obj;
        return s.film.equals(film) && s.hour.equals(hour) && s.hall == hall && s.dayOfWeek.equals(dayOfWeek);
    }

    public LocalTime getHour() {
        return hour;
    }

    public Film getFilm() {
        return film;
    }

    public int getHall() {
        return hall;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public int compare(Showing o1, Showing o2) {
        int comparison = o1.getDayOfWeek().compareTo(o2.getDayOfWeek());
        if(comparison == 0)
            return o1.getHour().compareTo(o2.getHour());

        return comparison;
    }
}
