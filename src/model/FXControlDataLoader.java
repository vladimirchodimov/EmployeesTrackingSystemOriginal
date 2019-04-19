package model;
import controller.DateComparator;
import controller.FileContentsReader;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class FXControlDataLoader {
    public static final String CLIENTS_FILE_NAME = "clients.txt";
    public static final String PROTOCOLS_FILE_NAME = "protocols.txt";
    FileContentsReader contentsLoader;
    private ArrayList<String> comboModel;
    private ArrayList<EmployeeStatsModel> tableModel;
    private String fileName;
    private String employeeName;
    public static String workedHoursSummary;
    public static String workedHoursSummaryForPeriod;
    private String startDate;
    private String endDate;

    public FXControlDataLoader(String fileName) {
        this.fileName = fileName;
    }

    public FXControlDataLoader(String fileName, String employeeName) {
        this.fileName = fileName;
        this.employeeName = employeeName;
    }

    public FXControlDataLoader(String fileName, String employeeName, String startDate, String endDate) {
        this.fileName = fileName;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ArrayList<String> setComboModel() {
        contentsLoader = new FileContentsReader(fileName);
        comboModel = new ArrayList<String>();
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] allRecordsArray = allRecords.split("\n");
        for (int i = 0; i < allRecordsArray.length; i++) {
            String[] rowArray = allRecordsArray[i].split("\t");
            comboModel.add(rowArray[0]);
        }
        return comboModel;
    }

    public ArrayList<EmployeeStatsModel> updateTableModel() {
        contentsLoader = new FileContentsReader(fileName);
        tableModel = new ArrayList<EmployeeStatsModel>();
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] allRecordsArray = allRecords.split("\n");
        int indexEmployeeName = 0;
        String workedHoursAll;
        int hours = 0;
        int minutes = 0;

        for (int i = 0; i < allRecordsArray.length; i++) {
            indexEmployeeName = allRecordsArray[i].toLowerCase().indexOf(employeeName.toLowerCase());
            if ((indexEmployeeName != -1)) {
                String[] rowArray = allRecordsArray[i].split("\t");
                EmployeeStatsModel rowModel = new EmployeeStatsModel(rowArray[0], rowArray[1],rowArray[2],rowArray[3]);
                String[] workedHours = rowArray[3].split(":");
                hours += Integer.parseInt(workedHours[0]);
                minutes += Integer.parseInt(workedHours[1]);
                tableModel.add(rowModel);
            }
        }
        hours += minutes / 60;
        minutes = minutes % 60;
        workedHoursSummary = " Общо: " + hours + ":" + minutes + " ч.";
        return tableModel;
    }

    public ArrayList<EmployeeStatsModel> updateTableModelForPeriod() {
        contentsLoader = new FileContentsReader(fileName);
        tableModel = new ArrayList<EmployeeStatsModel>();
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] allRecordsArray = allRecords.split("\n");
        int indexEmployeeName;
        String workedHoursAll;
        int hours = 0;
        int minutes = 0;
        DateComparator comparatorStart = new DateComparator();
        DateComparator comparatorEnd = new DateComparator();

        for (int i = 0; i < allRecordsArray.length; i++) {
            indexEmployeeName = allRecordsArray[i].toLowerCase().indexOf(employeeName.toLowerCase());
            if ((indexEmployeeName != -1)) {
                String[] rowArray = allRecordsArray[i].split("\t");
                if ((comparatorStart.compareDates(rowArray[2], startDate)) && (comparatorEnd.compareDates(endDate, rowArray[2]))) {
                    EmployeeStatsModel rowModel = new EmployeeStatsModel(rowArray[0], rowArray[1], rowArray[2], rowArray[3]);
                    String[] workedHours = rowArray[3].split(":");
                    hours += Integer.parseInt(workedHours[0]);
                    minutes += Integer.parseInt(workedHours[1]);
                    tableModel.add(rowModel);
                }
            }
        }
        hours += minutes / 60;
        minutes = minutes % 60;
        workedHoursSummaryForPeriod = " Общо: " + hours + ":" + minutes + " ч.";
        return tableModel;
    }

    public ArrayList<EmployeeStatsModel> setTableModel() {
        contentsLoader = new FileContentsReader(fileName);
        tableModel = new ArrayList<EmployeeStatsModel>();
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] allRecordsArray = allRecords.split("\n");

        for (int i = 0; i < allRecordsArray.length; i++) {
            String[] rowArray = allRecordsArray[i].split("\t");
            EmployeeStatsModel rowModel = new EmployeeStatsModel(rowArray[0], rowArray[1], rowArray[2], rowArray[3]);
            tableModel.add(rowModel);
        }
        return tableModel;
    }
}
