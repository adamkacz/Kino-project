package kaczmarczyk.projects;

public interface UserValidatable {
    boolean validateNameOrSurname(String name);
    boolean validatePassword(String password);
}
