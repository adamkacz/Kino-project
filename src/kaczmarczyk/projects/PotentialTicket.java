package kaczmarczyk.projects;

import java.io.Serializable;
import java.time.LocalDate;

/**Potential ticket is a ticket planned to be released for the specific specific realisation of the showing */
public class PotentialTicket extends TicketTemplate implements Serializable {
    /**Indicates if a ticket for a specific realisation of the showing is available*/
    boolean isSold;

    public PotentialTicket(Seat seat, Showing showing, LocalDate date){
        super(seat, showing, date);
        isSold = false;
    }

    public PotentialTicket(Seat seat, Showing showing, LocalDate date, Boolean isSold){
            super(seat, showing, date);
            this.isSold = isSold;
    }

    /**
     * Returns potential ticket's availability status
     * @return current ticket availability
     */
    public boolean askIfIsSold(){
        return isSold;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(date).append("\n");
        sb.append(showing.toTicketString()).append("\n");
        sb.append("Rząd ").append(seat.rowNumber).append(", Miejsce ").append(seat.seatInRowNumber);
        return sb.toString();
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}
