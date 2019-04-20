package controller;

public class DateComparator {

    public DateComparator() {
    }

    public boolean compareDates(String  dateFromTableRow, String dateFromComboBox) {
        String[] dateFromTable = dateFromTableRow.split("/");
        String[] dateFromCombo = dateFromComboBox.split("/");
        if ((Integer.parseInt(dateFromTable[0]) >= Integer.parseInt(dateFromCombo[0])) && (Integer.parseInt(dateFromTable[1]) >= Integer.parseInt(dateFromCombo[1]))) {
            return true;
        } else {
            return false;
        }
    }
}
