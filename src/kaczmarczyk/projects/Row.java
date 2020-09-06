package kaczmarczyk.projects;

import java.io.Serializable;
import java.util.ArrayList;

/**Represents a row of seats in a cinema hall*/
public class Row implements Serializable {
    public ArrayList<Seat> seats;
    int number;

    public Row(int number, ArrayList<Seat> seats){
        this.number = number;
        this.seats = seats;
    }
}
