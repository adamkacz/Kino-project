package kaczmarczyk.projects;

public interface UserManagable {
    void addUser(String name, String surname, boolean isAdmin, String password) throws FormatException;
    void deleteUser(User u);
}
