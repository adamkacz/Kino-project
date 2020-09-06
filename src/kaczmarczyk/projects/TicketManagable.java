package kaczmarczyk.projects;

public interface TicketManagable {
    void createTicket(Ticket t);
    void removeTicket(long number) throws WrongNumberException, NotSoldException, AlreadyGivenBackException;
}
