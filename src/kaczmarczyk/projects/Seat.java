package kaczmarczyk.projects;

import java.io.Serializable;

/**Represents a seat in a cinema hall*/
public class Seat implements Comparable<Seat>, Serializable {
    int rowNumber;
    int seatInRowNumber;

    public Seat(int rowNumber, int seatInRowNumber) {
        this.rowNumber = rowNumber;
        this.seatInRowNumber = seatInRowNumber;
    }

    @Override
    public int compareTo(Seat s) {
        if(rowNumber == s.rowNumber)
            return Integer.compare(seatInRowNumber, s.seatInRowNumber);

        return Integer.compare(rowNumber, s.rowNumber);
    }


    @Override
    public boolean equals(Object o){
        if(!(o instanceof Seat))
            return false;

        Seat s = (Seat) o;
        return rowNumber == s.rowNumber && seatInRowNumber == s.seatInRowNumber;
    }
}
