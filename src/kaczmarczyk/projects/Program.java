package kaczmarczyk.projects;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**Program is a set of showings for different days of week with indicated bounds of time*/
public class Program implements ShowingAddable, Serializable, Cloneable {
    /**obligatory break between films in one hall*/
    static int INTERVAL = 30;
    public ArrayList<Showing> showingList = new ArrayList<>();
    public ArrayList<Film> films = new ArrayList<>();
    public ArrayList<PotentialTicket> potentialTickets = new ArrayList<>();
    public ArrayList<Hall> halls;
    public LocalDate startDate;
    public LocalDate stopDate;

    public Program(LocalDate startDate, LocalDate stopDate, ArrayList<Hall> halls) {
        changeProgramDates(startDate, stopDate);
        this.halls = halls;
        halls.sort(Comparator.comparingInt(h0 -> h0.hallNumber));
    }

    /**
     * Adds a showing to a hall if there is not any other showing in this hall yet
     * @param s Showing to be added
     * @throws WrongHourException Thrown when the showing collide with another one
     */
    public void addShowing(Showing s) throws WrongHourException{
        if(!isHallEmpty(s))
            throw new WrongHourException();
        else {
            showingList.add(s);
            generatePotentialTickets(s);
            try{
                addFilm(s.film);
            }catch (FilmAlreadyExistsException ignored){

            }
        }
    }

    /**
     * Deletes a chosen showing and potential tickets that were planned for the showing
     * @param del Showing to be deleted
     */
    public void deleteShowing(Showing del){
        if(showingList.stream().filter(s->s.film.equals(del.film)).count() == 1)
            removeFilm(del.film);

        showingList.remove(del);
        potentialTickets.removeIf(pt->pt.showing.equals(del));
    }

    /**
     * Indicates which showings take place in the chosen day of week
     * @param day Chosen day of week
     * @param showingList List of showings to be searched
     * @return List of showings of the weekday
     */
    public static ArrayList<Showing> filterByDay(DayOfWeek day, ArrayList<Showing> showingList){
        ArrayList<Showing> temp = new ArrayList<>();
        showingList.stream().filter(s->s.dayOfWeek == day).forEach(temp::add);
        return temp;
    }

    /**
     * Indicates which showings take place in the chosen hall
     * @param hall Chosen hall
     * @param showingList List of showings to be searched
     * @return List of showings in the hall
     */
    public static ArrayList<Showing> filterByHall(int hall, ArrayList<Showing> showingList){
        ArrayList<Showing> temp = new ArrayList<>();
        showingList.stream().filter(s->s.hall == hall).forEach(temp::add);
        return temp;
    }

    /**
     * Indicates showings of the chosen film
     * @param film Chosen film
     * @param showingList List of showings to be searched
     * @return List of showings of the film
     */
    public static ArrayList<Showing> filterByFilm(Film film, ArrayList<Showing> showingList){
        ArrayList<Showing> temp = new ArrayList<>();
        showingList.stream().filter(s->s.film.compareTo(film) == 0).forEach(temp::add);
        return temp;
    }

    /** Indicates if there is not any other showing if the period of the chosen showing's lasting
     *
     * @param s Chosen showing
     * @return true for empty hall, false for hall with the showing added earlier
     */
    public boolean isHallEmpty(Showing s){
        DayOfWeek day = s.dayOfWeek;
        ArrayList<Showing> temp = filterByDay(day,showingList);
        temp = filterByHall(s.hall,temp);
        return temp.stream().allMatch(sh -> s.hour.compareTo(sh.hour.plusMinutes(sh.film.minutes + INTERVAL)) >= 0
                || s.hour.plusMinutes(s.film.minutes + INTERVAL).compareTo(sh.hour) <= 0);
    }

    /**
     * Adds the film to the program if it is not present in the film list of the program.
     * @param f A film to be added
     * @throws FilmAlreadyExistsException if the film is present in the program list
     */
    public void addFilm(Film f) throws FilmAlreadyExistsException {
        if(films.contains(f)){
            throw new FilmAlreadyExistsException();
        }
        else {
            films.add(f);
        }
    }

    public void removeFilm(Film f){
        films.remove(f);
    }

    /**
     * Changes program dates. If the dates' order is incorrect, the dates are swapped
     * @param newStart New date of the program's beginning
     * @param newStop New date of the program's end
     */
    public void changeProgramDates(LocalDate newStart, LocalDate newStop){
        if(newStart.compareTo(newStop) > 0){
            LocalDate tmp = newStop;
            newStop = newStart;
            newStart = tmp;
        }
        LocalDate finalNewStart = newStart;
        LocalDate finalNewStop = newStop;
        potentialTickets.removeIf(pt->pt.date.compareTo(finalNewStart) < 0 || pt.date.compareTo(finalNewStop) > 0);
        this.startDate = newStart;
        this.stopDate = newStop;
    }

    /**
     * Generates all potential tickets for planned showing
     * @param s Showing to generating potential of the tickets
     */
    public void generatePotentialTickets(Showing s){
        LocalDate i = startDate;
        while(i.compareTo(stopDate.plusDays(1)) < 1){
            for(Row r:halls.get(s.hall-1).rows)
                for(Seat st:r.seats)
                    potentialTickets.add(new PotentialTicket(st,s,i));

            i = i.plusDays(1);
        }
    }

    @Override
    public String toString() {
        return startDate.toString() + " - " + stopDate.toString();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    /**
     * Clones the program
     * @return New program object with copied properties
     */
    @Override
    public Program clone(){
        Program p = null;
        try(ByteArrayOutputStream memStream = new ByteArrayOutputStream()) {
            ObjectOutputStream objectStream = new ObjectOutputStream(memStream);
            objectStream.writeObject(this);
            objectStream.close();
            ByteArrayInputStream memStreamIn = new ByteArrayInputStream(memStream.toByteArray());
            ObjectInputStream objectIn = new ObjectInputStream(memStreamIn);
            p = (Program) objectIn.readObject();
            objectIn.close();
            memStreamIn.close();
        } catch(IOException | ClassNotFoundException i){
            i.printStackTrace();
        }
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Program))
            return false;

        Program p = (Program) obj;
        return p.startDate.equals(startDate) && p.stopDate.equals(stopDate);
    }
}
