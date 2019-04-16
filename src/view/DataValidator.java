package view;

public class DataValidator implements IValidator {
    private String name;
    private String password;
    private String email;


    public DataValidator() {
    }

    public DataValidator(String email) {
        this.email = email;
    }

    public DataValidator(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean validateEmail(String email) {
        if(!email.matches("^[A-Za-z0-9_\\-.]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-.]{2,}$")) {
            return false;
        } else {
            return true;
        }
    }

    public String validateLoginInput() {
        String message = "";
        if (name.trim() == "") {
            message += "Моля въведете трите си имена! ";
        }
        if (!name.matches("[А-Яа-я]{2,10}[\\s]{1}[А-Яа-я]{2,15}[\\s]{1}[А-Яа-я]{2,15}")) {
            message += "Неправилен формат на името! Въведете име, презиме и фамилия разделени с интервал. ";
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,}$")) {
            message += "Невалиден формат на паролата! ";
        }
        if (message.length() != 0) {
            return message;
        } else {
            return "OK";
        }
    }

    public boolean validateEmployeeForm(String workedHours) {
        if (!workedHours.matches("^[0-7]{1}[:]{1}[0-5]{1}[0-9]{1}$")) {
            return false;
        } else {
            return true;
        }
    }
}
