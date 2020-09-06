package kaczmarczyk.projects;

import java.time.LocalDate;

public interface ProgramManagable {
    void addProgram(Program p) throws WrongDatesException;
    void deleteProgram(Program p);
    void changeProgramDates(Program p, LocalDate newStart, LocalDate newStop) throws WrongDatesException;
    Program findProgramByDate(LocalDate date);
}
