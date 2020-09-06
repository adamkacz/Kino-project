package kaczmarczyk.projects;

import java.io.Serializable;
import java.time.LocalDate;

/** Includes the basic information for a ticket*/
public abstract class TicketTemplate implements Serializable {
    public Seat seat;
    public Showing showing;
    public LocalDate date;

    public TicketTemplate(){

    }

    public TicketTemplate(Seat seat, Showing showing, LocalDate date){
        this();
        this.seat = seat;
        this.showing = showing;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof TicketTemplate))
            return false;

        TicketTemplate t = (TicketTemplate) obj;
        return seat.equals(t.seat) && showing.equals(t.showing) && date.equals(t.date);
    }

    public LocalDate getDate() {
        return date;
    }
}
