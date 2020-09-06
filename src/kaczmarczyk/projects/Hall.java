package kaczmarczyk.projects;

import java.io.Serializable;
import java.util.ArrayList;

/** Represents a cinema hall */
public class Hall implements Serializable {
    int hallNumber;
    public ArrayList<Row> rows = new ArrayList<>();
    int currentRowNumber = 0;

    public Hall(){}

    public Hall(int hallNumber){
        this.hallNumber = hallNumber;
    }

    /**Adds a row of specified number of seats to the hall
     *
     * @param numberOfSeats number of seats to add
     */
    public void addRow(int numberOfSeats){
        int row = ++currentRowNumber;
        ArrayList<Seat> temp = new ArrayList<>();
        for(int i = 0; i < numberOfSeats; ++i) {
            temp.add(new Seat(row,i+1));
        }
        rows.add(new Row(row,temp));
    }

    @Override
    public String toString() {
        return Integer.toString(hallNumber);
    }

    public int getCurrentRowNumber() {
        return currentRowNumber;
    }

    public int getHallNumber() {
        return hallNumber;
    }
}
