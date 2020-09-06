package kaczmarczyk.projects;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/** The class including the whole cinema platform */
public class Cinema implements Serializable, ProgramManagable, UserManagable, TicketManagable {
    public ArrayList<Hall> halls = new ArrayList<>();
    public ArrayList<Program> programs = new ArrayList<>();
    /** For deserializing purposes */
    long currentTicketNumber;
    /** For deserializing purposes */
    long currentUserNumber;

    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Ticket> releasedTickets = new ArrayList<>();

    public Cinema(){
        currentTicketNumber = 0;
    }

    public Cinema(ArrayList<Hall> halls, long currentTicketNumber, long currentUserNumber ){
        this.halls = halls;
        this.currentTicketNumber = currentTicketNumber;
        this.currentUserNumber = currentUserNumber;
    }

    /**
     * Saves the cinema to the chosen file.
     * @param fileName File where the cinema is supposed to be saved
     */
    public void serialize(String fileName){
        try{
            try(FileOutputStream fileOut = new FileOutputStream(fileName)){
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
            }
            System.out.println("Serialized data is saved in: " + fileName);
        } catch (IOException i){
            i.printStackTrace();
        }
    }

    /**
     * Reads a cinema object from chosen file.
     * @param fileName File to be read.
     * @return Cinema object from file.
     */
    public static Cinema deserialize(String fileName){
        Cinema c = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            c = (Cinema) in.readObject();
            in.close();
            fileIn.close();
            Ticket.currentNumber = c.currentTicketNumber;
            User.ID = c.currentUserNumber;
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Adds user with data of the proper format to the cinema list od users.
     * @param name User name (more than 2 letters)
     * @param surname User surname (more than 2 letters)
     * @param isAdmin Admin attribute
     * @param password User password (at least one capital letter, one small letter, one digit and 4-40 chars)
     * @throws FormatException Thrown when data does not have proper format
     */
    public void addUser(String name, String surname, boolean isAdmin, String password) throws FormatException{
        User u = new User(name, surname, isAdmin, password);
        users.add(u);
        ++currentUserNumber;

    }
    /**Deletes user account */
    public void deleteUser(User u){
        users.remove(u);
    }


    public User getUser(long IDNumber){
        return users.stream().filter(u->u.userID == IDNumber).findFirst().orElse(null);
    }

    /**Creates ticket by adding it to ticket list */
    public void createTicket(Ticket ticket) {
        ++currentTicketNumber;
        releasedTickets.add(ticket);
    }

    /**
     * Removes ticket by marking it 'isGivenBack'
     * @param number Ticket number
     * @throws WrongNumberException Thrown when the ticket number is not present on the ticket list
     * @throws NotSoldException Thrown when the potential ticket connected with the ticket was not sold
     * @throws AlreadyGivenBackException Thrown when the ticket was already given back
     */
    public void removeTicket(long number) throws WrongNumberException, NotSoldException, AlreadyGivenBackException{
        Ticket temp = releasedTickets.stream().filter(ticket -> ticket.ticketNumber == number)
                .findFirst().orElse(null);
        if(temp == null)
            throw new WrongNumberException();

        if(temp.givenBack)
            throw new AlreadyGivenBackException();

        Program tempProgram = findProgramByDate(temp.date);
        PotentialTicket potentialTemp = tempProgram.potentialTickets.stream().filter(pt->pt.getDate().equals(temp.date)
                &&temp.showing.equals(pt.showing)&&temp.seat.equals(pt.seat)).findFirst().orElse(null);
        if(potentialTemp != null) {
            if (!potentialTemp.askIfIsSold()) {
                throw new NotSoldException();
            }
            potentialTemp.setSold(false);
        }

        temp.givenBack = true;
    }

    /** Finds the program running in the indicated date
      * @param date Date to be indicated
     * @return Program of the date
     */
    public Program findProgramByDate(LocalDate date){
        return programs.stream().filter(p->p.startDate.compareTo(date) < 1
                && p.stopDate.compareTo(date) > -1).findFirst().orElse(null);
    }

    public long getCurrentTicketNumber() {
        return currentTicketNumber;
    }

    /** Adds indicated program if there is not any other in the period between the program's start- and stop dates
     * @throws WrongDatesException Thrown when the program collides with another one
     * */
    public void addProgram(Program p) throws WrongDatesException{
        if(!programs.stream().allMatch(pr->pr.stopDate.compareTo(p.startDate) < 0
                || pr.startDate.compareTo(p.stopDate) > 0))
            throw new WrongDatesException();

        programs.add(p);
    }

    /** Changes the indicated program's dates to new ones if possible
     * @throws WrongDatesException Thrown when the new program dates collide with another one
     */
    public void changeProgramDates(Program p, LocalDate newStart, LocalDate newStop) throws WrongDatesException{
        LocalDate oldStart = p.startDate;
        LocalDate oldStop = p.stopDate;
        ArrayList<Program> changedPrograms = cloneProgramList();
        changedPrograms.remove(p);
        p.changeProgramDates(newStart, newStop);
        if(!changedPrograms.stream().allMatch(pr->pr.stopDate.compareTo(p.startDate) < 0
                || pr.startDate.compareTo(p.stopDate) > 0)) {
            p.changeProgramDates(oldStart,oldStop);
            throw new WrongDatesException();
        }
    }
    /**Removes indicated program*/
    public void deleteProgram(Program p){
        programs.remove(p);
    }

    /**Creates a new list that is a copy of a current program list in the cinema
     *
     * @return Copy of the program list
     */
    public ArrayList<Program> cloneProgramList(){
        ArrayList<Program> newList = new ArrayList<>();
        for(Program p : programs)
            newList.add(p.clone());

        return newList;
    }
}
