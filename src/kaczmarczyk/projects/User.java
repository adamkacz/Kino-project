package kaczmarczyk.projects;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**User is a cashier that do the basic ticket services for the cinema*/
public class User implements Serializable, UserValidatable {
    String name;
    String surname;
    long userID;
    String password;
    boolean isAdmin;
    static long ID = 1;

    public User(){

    }

    /**
     * Creates a user instance
     * for params {@link Cinema#addUser}
     * @param name  The user's name
     * @param surname The user's surname
     * @param isAdmin The user's status in the system
     * @param password The user's password
     * @throws FormatException Thrown if the format of the password or the name or the surname is wrong
     */
    public User(String name, String surname, boolean isAdmin, String password) throws FormatException{
        setPassword(password);
        setName(name);
        setSurname(surname);
        this.isAdmin = isAdmin;
        userID = ID++;
    }

    /**
     * Set password of the valid format
     * @param password Password given
     * @throws PasswordFormatException Thrown when the password's format is wrong.
     * See requirements in {@link #validatePassword(String)}
     */
    public void setPassword(String password) throws PasswordFormatException{
        if(!validatePassword(password))
            throw new PasswordFormatException();

        this.password = password;
    }

    /**
     * Validates the format of the password
     * @param pass Password given
     * @return true - if the password meet requirements, false - otherwise
     * Requirements:
     * 4 - 40 chars,
     * at least one small letter,
     * at least one capital letter,
     * at least one digit.
     *
     */
    public boolean validatePassword(String pass){
        Pattern p1 = Pattern.compile("[A-Z]");
        Pattern p2 = Pattern.compile("[a-z]");
        Pattern p3 = Pattern.compile("[0-9]");
        if(pass.length() < 4 || pass.length() > 40)
            return false;

        Matcher m1 = p1.matcher(pass);
        Matcher m2 = p2.matcher(pass);
        Matcher m3 = p3.matcher(pass);
        return m1.find() && m2.find() && m3.find();
    }

    /**
     * Sets name if it has valid format
     * @param name Name to be set
     * @throws FormatException thrown when the format is invalid
     * See requirements in {@link #validateNameOrSurname}
     */
    public void setName(String name) throws FormatException{
        if(!validateNameOrSurname(name))
            throw new FormatException();

        this.name = name;
    }

    /**
     * Sets surname if it has valid format
     * @param surname Surname to be set
     * @throws FormatException thrown when the format is invalid
     */
    public void setSurname(String surname) throws FormatException{
        if(!validateNameOrSurname(surname))
            throw new FormatException();

        this.surname = surname;
    }

    /**Validates the format of the name and the surname
     *
     * @param name Name or surname to be validated
     * @return true - if name meets requirements, false - otherwise
     * Requirements: at lest three letters
     */
    public boolean validateNameOrSurname(String name){
        return name.length() > 2;
    }

    public String getPassword(){
        return password;
    }

    public boolean getUserStatus(){
        return isAdmin;
    }

    @Override
    public String toString() {
        return "ID: " + userID + " (" + name + " " + surname + ") ";
    }

    public static long getID(){
        return ID;
    }

    public long getUserID() {
        return userID;
    }
}
