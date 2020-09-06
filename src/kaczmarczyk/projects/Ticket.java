package kaczmarczyk.projects;

import java.io.Serializable;
import java.time.LocalDate;


/** Includes full ticket information */
public class Ticket extends TicketTemplate implements Serializable {
    Type type;
    long ticketNumber;
    double ticketPrice;
    /**Indicates whether the ticket was given back*/
    boolean givenBack;
    static long currentNumber = 0;
    public static double price = 20;
    public static double discount = 0.37;


    public Ticket(Seat seat, Showing showing, LocalDate date, Type type){
        super(seat, showing, date);
        this.type = type;
        ticketNumber = ++currentNumber;
        givenBack = false;
        countPrice();
    }

    protected Ticket(Seat seat, Showing showing, LocalDate date, Type type, long ticketNumber) {
        super(seat, showing, date);
        this.type = type;
        this.ticketNumber = ticketNumber;
        givenBack = true;
        countPrice();
    }

    /** Creates potential ticket based ticket*/
    public Ticket(PotentialTicket potentialTicket, Type type){
        this(potentialTicket.seat, potentialTicket.showing, potentialTicket.date, type);
        potentialTicket.isSold = true;
    }

    /**Counts price of the ticket depending on the ticket's type*/
    public void countPrice(){
        if(type == Type.FULL)
            ticketPrice = price;
        else
            ticketPrice =  Math.round(price*(1-discount)*100)/(double)100;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket ");
        sb.append(ticketNumber).append("\t").append(type).append("\n");
        sb.append(date).append("\n");
        sb.append(showing.toTicketString()).append("\n");
        sb.append("Rząd ").append(seat.rowNumber).append(", Miejsce ").append(seat.seatInRowNumber);
        return sb.toString();
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public void setGivenBack(boolean givenBack) {
        this.givenBack = givenBack;
    }
}
