package controller;

public class ClientAndEmployeeChecker implements IAccountFetcher {
    private String name;
    private String email;
    private String fileName;
    FileContentsReader contentsLoader;

    public ClientAndEmployeeChecker(String clientName, String clientEmail, String fileName) {
        this.name = clientName;
        this.email = clientEmail;
        this.fileName = fileName;
    }

    public boolean fetchAccount() {
        boolean accountFound = false;
        contentsLoader = new FileContentsReader(fileName);
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] recordsArray = allRecords.split("\n");
        int indexClientName;
        int indexClientEmail;
        for (int i = 0; i < recordsArray.length; i++) {
            indexClientName = recordsArray[i].toLowerCase().indexOf(name.toLowerCase());
            indexClientEmail = recordsArray[i].toLowerCase().indexOf(email.toLowerCase());
            if ((indexClientName != -1) || (indexClientEmail != -1)) {
                accountFound = true;
                break;
            }
        }
        return accountFound;
    }
}
