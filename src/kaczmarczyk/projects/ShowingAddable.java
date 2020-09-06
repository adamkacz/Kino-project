package kaczmarczyk.projects;

public interface ShowingAddable {
    void addShowing(Showing s) throws WrongHourException;
    void deleteShowing(Showing s);
    void addFilm(Film f) throws FilmAlreadyExistsException;
    void removeFilm(Film f);
    void generatePotentialTickets(Showing s);
    boolean isHallEmpty(Showing s);
}
