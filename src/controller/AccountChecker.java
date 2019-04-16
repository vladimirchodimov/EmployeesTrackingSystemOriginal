package controller;

public class AccountChecker implements IAccountFetcher {
    private String userName;
    private String userPassword;
    private String fileName;
    String email;
    FileContentsReader contentsLoader;


    public AccountChecker(String fileName, String email) {
        this.fileName = fileName;
        this.email = email;
    }

    public AccountChecker(String userName, String userPassword, String fileName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.fileName = fileName;
    }

    public boolean fetchEmail() {
        boolean emailFound = false;
        contentsLoader = new FileContentsReader(fileName);
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] recordsArray = allRecords.split("\n");
        int indexEmail;
        for (int i = 0; i < recordsArray.length; i++) {
            indexEmail = recordsArray[i].toLowerCase().indexOf(email.toLowerCase());
            if ((indexEmail != -1)) {
                emailFound = true;
                break;
            }
        }
        return emailFound;
    }


    public boolean fetchAccount() {
        boolean accountFound = false;
        contentsLoader = new FileContentsReader(fileName);
        String allRecords = contentsLoader.fetchAllRecordsFromFile();
        String[] userNames = userName.split(" ");
        String userFirstName = userNames[0];
        String userSecondName = userNames[1];
        String userLastName = userNames[2];
        String[] recordsArray = allRecords.split("\n");
        int indexFirstName;
        int indexSecondName;
        int indexLastName;
        int indexPassword;
        for (int i = 0; i < recordsArray.length; i++) {
            indexFirstName = recordsArray[i].toLowerCase().indexOf(userFirstName.toLowerCase());
            indexSecondName = recordsArray[i].toLowerCase().indexOf(userSecondName.toLowerCase());
            indexLastName = recordsArray[i].toLowerCase().indexOf(userLastName.toLowerCase());
            indexPassword = recordsArray[i].indexOf(userPassword);

            if ((indexFirstName != -1) && (indexSecondName != -1) && (indexLastName != -1) && (indexPassword != -1)) {
                accountFound = true;
                break;
            }
        }
        return accountFound;
    }


}
