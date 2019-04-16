package view;

public interface IValidator {
    String validateLoginInput();
    boolean validateEmployeeForm(String workedHours);
    boolean validateEmail(String email);
}


