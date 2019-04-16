package model;

public class EmployeeStatsModel {
    private String employeeName;
    private String clientName;
    private String date;
    private String workedHours;


    public EmployeeStatsModel() {
        this.employeeName = "";
        this.clientName = "";
        this.date = "";
        this.workedHours = "";
    }
    public EmployeeStatsModel(String employeeName, String clientName, String date, String workedHours) {
        this.employeeName = employeeName;
        this.clientName = clientName;
        this.date = date;
        this.workedHours = workedHours;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getDate() {
        return date;
    }

    public String getWorkedHours() {
        return workedHours;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours = workedHours;
    }
}
