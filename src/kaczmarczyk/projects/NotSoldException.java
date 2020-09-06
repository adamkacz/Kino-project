package kaczmarczyk.projects;

/** Indicates the ticket is being tried to be given back even if it was not sold */
public class NotSoldException extends Exception {
    public NotSoldException(){
        super();
    }
}
