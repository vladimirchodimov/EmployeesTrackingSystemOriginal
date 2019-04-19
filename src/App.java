import controller.AccountChecker;
import controller.ClientAndEmployeeChecker;
import controller.FileContentsWriter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EmployeeStatsModel;
import model.FXControlDataLoader;
import view.DataValidator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class App extends Application {

    public static final String EMPLOYEES_FILE_NAME = "employees.txt";
    public static final String ADMINS_FILE_NAME = "administrators.txt";

    public static void main(String[] args) {
        launch(args);
    }

    String userName;
    Stage stage;
    Scene loginScene;
    Scene employeeScene;
    Scene adminScene;
    TextField textName;
    PasswordField textPassword;
    RadioButton radioEmployee;
    RadioButton radioAdmin;
    Button loginButton;
    Button clearButton;
    ComboBox<String> comboClients;
    TextField textWorkedHours;
    Button saveProtocolButton;
    Button toLogginScreenButton;
    FXControlDataLoader loaderClients;
    FXControlDataLoader loaderEmployees;
    FXControlDataLoader loaderTable;
    Date date;
    String dateString;
    String userFullName;
    TextField textClientName;
    TextField textClientEmail;
    Button saveClientButton;
    Button clearClientButton;
    Button toLoginFromClientButton;
    TextField textEmployeeName;
    TextField textEmployeeEmail;
    PasswordField textEmployeePassword;
    Button saveEmployeeButton;
    Button clearEmployeeButton;
    Button toLoginFromEmployeeButton;
    ComboBox<String> comboEmployee;
    TableView<EmployeeStatsModel> tableEmployeeStats;
    Button toLoginFromEmployeeStatsButton;
    Button seeEntireStats;
    Label workedHoursSummary;
    TableView<EmployeeStatsModel> tableEmployeeStatsForPeriod;
    ComboBox<String> comboEmployeeForPeriod;
    DatePicker datePickerStart;
    DatePicker datePickerEnd;
    Label workedHoursSummaryForPeriod;
    Button toLoginFromEmployeeStatsForPeriodButton;
    Button clearCboxAndDatePickersButton;
    Button applyFiltersButton;
    TabPane tabPane;
    Tab tabClient;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        //loginScene
        Text textHeading = new Text("Влезте в системата като служител или администратор.");
        textHeading.setFont(new Font("Verdana", 23));
        HBox paneTop = new HBox(textHeading);
        paneTop.setAlignment(Pos.CENTER);
        paneTop.setPadding(new Insets(20, 10, 20, 10));

        radioEmployee = new RadioButton("Служител");
        radioAdmin = new RadioButton("Администратор");
        HBox paneChecks = new HBox(10, radioEmployee, radioAdmin);
        paneChecks.setAlignment(Pos.CENTER);
        ToggleGroup employeeOrAdmin = new ToggleGroup();
        radioEmployee.setToggleGroup(employeeOrAdmin);
        radioAdmin.setToggleGroup(employeeOrAdmin);
        radioEmployee.setSelected(true);

        Label labelName = new Label("Име");
        labelName.setPrefWidth(100);
        textName = new TextField();
        textName.setText("");
        textName.setPrefColumnCount(30);
        textName.setPromptText("Въведете трите си имена.");
        HBox paneName = new HBox(-50, labelName, textName);
        paneName.setAlignment(Pos.CENTER);

        Label labelPassword = new Label("Парола");
        labelPassword.setPrefWidth(100);
        textPassword = new PasswordField();
        textPassword.setPrefColumnCount(30);
        textPassword.setText("");
        textPassword.setPromptText("Поне 6 знака, малка и голяма буква, число, специален сивол.");
        HBox panePassword = new HBox(-50, labelPassword, textPassword);
        panePassword.setAlignment(Pos.CENTER);
        VBox paneCenter = new VBox(20, paneChecks, paneName, panePassword);
        paneCenter.setAlignment(Pos.CENTER);

        loginButton = new Button("ОК");
        loginButton.setMinWidth(80);
        loginButton.setOnAction(e -> loginButtonOnClick());
        loginButton.setDefaultButton(true);
        clearButton = new Button("Изчисти");
        clearButton.setMinWidth(80);
        clearButton.setOnAction(e -> clearButtonOnClick());
        HBox paneBottom = new HBox(20, loginButton, clearButton);
        paneBottom.setAlignment(Pos.CENTER);

        BorderPane paneMain = new BorderPane();
        paneMain.setTop(paneTop);
        paneMain.setCenter(paneCenter);
        paneMain.setBottom(paneBottom);
        paneMain.setPadding(new Insets(50, 10, 90, 10));

        loginScene = new Scene(paneMain, 900, 500);
        stage.setScene(loginScene);
        stage.setTitle("Система за следене на работното време");
        stage.show();

        //empolyeeScene
        Text textHeadingEmployee = new Text("Създайте протокол за деня");
        textHeadingEmployee.setFont(new Font("Verdana", 23));
        HBox paneTopEmployee = new HBox(textHeadingEmployee);
        paneTopEmployee.setAlignment(Pos.CENTER);
        paneTop.setPadding(new Insets(20, 10, 20, 10));

        Label labelComboClients = new Label("Избери клиент");
        comboClients = new ComboBox<String>();
        comboClients.setPromptText("Избери клиент");
        loaderClients = new FXControlDataLoader("clients.txt");
        comboClients.setItems(FXCollections.observableArrayList(loaderClients.setComboModel()));
        comboClients.setPrefWidth(140);
        HBox paneComboClients = new HBox(10, labelComboClients, comboClients);
        paneComboClients.setAlignment(Pos.CENTER);

        Label workedHours = new Label("Работено време");
        textWorkedHours = new TextField();
        textWorkedHours.setPromptText("h:mm");
        textWorkedHours.setPromptText("Изработени часове");
        HBox paneTextWorkedHours = new HBox(10, workedHours, textWorkedHours);
        paneTextWorkedHours.setAlignment(Pos.CENTER);
        VBox paneCenterEmployee = new VBox(30, paneComboClients, paneTextWorkedHours);
        paneCenterEmployee.setAlignment(Pos.CENTER);
        paneCenterEmployee.setPadding(new Insets(40, 10, 30, 10));

        saveProtocolButton = new Button("Запази");
        saveProtocolButton.setMinWidth(80);
        saveProtocolButton.setOnAction(e -> saveProtocolButtonOnClick());
        saveProtocolButton.setDefaultButton(true);
        toLogginScreenButton = new Button("Към логин екрана");
        toLogginScreenButton.setMinWidth(80);
        toLogginScreenButton.setOnAction(e -> toLogginScreenButtonOnClick());
        HBox paneBottomEmployee = new HBox(10, saveProtocolButton, toLogginScreenButton);
        paneBottomEmployee.setAlignment(Pos.CENTER);
        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateString = formatter.format(date);

        BorderPane paneMainEmployee = new BorderPane();
        paneMainEmployee.setTop(paneTopEmployee);
        paneMainEmployee.setCenter(paneCenterEmployee);
        paneMainEmployee.setBottom(paneBottomEmployee);
        paneMainEmployee.setPadding(new Insets(80, 10, 180, 10));
        employeeScene = new Scene(paneMainEmployee, 900, 500);

        //adminScene
        //tabClient
        Text textHeadingClient = new Text("Добави клиент");
        textHeadingClient.setFont(new Font("Verdana", 18));
        HBox paneTopNewClient = new HBox(textHeadingClient);
        paneTopNewClient.setAlignment(Pos.CENTER);
        paneTopNewClient.setPadding(new Insets(20, 10, 15, 10));

        Label labelClientName = new Label("Клиент");
        labelClientName.setPrefWidth(100);
        textClientName = new TextField();
        textClientName.setPrefColumnCount(30);
        textClientName.setPromptText("Въведете клиент");
        HBox paneClientName = new HBox(-30, labelClientName, textClientName);
        paneClientName.setPadding(new Insets(10, 10, 5, 10));
        paneClientName.setAlignment(Pos.CENTER);

        Label labelClientEmail = new Label("Имейл");
        labelClientEmail.setPrefWidth(100);
        textClientEmail = new TextField();
        textClientEmail.setPrefColumnCount(30);
        textClientEmail.setPromptText("Въведете имейл");
        HBox paneClientEmail = new HBox(-30, labelClientEmail, textClientEmail);
        paneClientEmail.setAlignment(Pos.CENTER);

        VBox paneCenterNewClient = new VBox(20, paneClientName, paneClientEmail);
        paneCenterNewClient.setAlignment(Pos.CENTER);

        saveClientButton = new Button("Запази");
        saveClientButton.setMinWidth(80);
        saveClientButton.setOnAction(e -> saveClientButtonOnClick());
        clearClientButton = new Button("Изчисти");
        clearClientButton.setMinWidth(80);
        clearClientButton.setOnAction(e -> clearClientButtonOnClick());
        toLoginFromClientButton = new Button("Към логин екрана");
        toLoginFromClientButton.setMinWidth(80);
        toLoginFromClientButton.setOnAction(e -> toLoginFromClientButtonOnClick());

        HBox paneBottomNewClient = new HBox(20, saveClientButton, clearClientButton, toLoginFromClientButton);
        paneBottomNewClient.setAlignment(Pos.CENTER);
        paneBottomNewClient.setPadding(new Insets(40, 10, 20, 10));

        BorderPane paneMainClient = new BorderPane();
        BorderPane.setMargin(paneBottomNewClient, new Insets(0, 0, 0, 70));
        paneMainClient.setTop(paneTopNewClient);
        paneMainClient.setCenter(paneCenterNewClient);
        paneMainClient.setBottom(paneBottomNewClient);
        paneMainClient.setPadding(new Insets(80, 10, 180, 10));

        //tabEmployee
        Text textHeadingNewEmployee = new Text("Добави служител");
        textHeadingNewEmployee.setFont(new Font("Verdana", 18));
        HBox paneTopNewEmployee = new HBox(textHeadingNewEmployee);
        paneTopNewEmployee.setAlignment(Pos.CENTER);
        paneTopNewEmployee.setPadding(new Insets(20, 10, 15, 10));

        Label labelEmployeeName = new Label("Служител");
        labelEmployeeName.setPrefWidth(100);
        textEmployeeName = new TextField();
        textEmployeeName.setPrefColumnCount(30);
        textEmployeeName.setPromptText("Трите имена на служителя");
        HBox paneEmployeeName = new HBox(-30, labelEmployeeName, textEmployeeName);
        paneEmployeeName.setPadding(new Insets(10, 10, 5, 10));
        paneEmployeeName.setAlignment(Pos.CENTER);

        Label labelEmployeeEmail = new Label("Имейл");
        labelEmployeeEmail.setPrefWidth(100);
        textEmployeeEmail = new TextField();
        textEmployeeEmail.setPrefColumnCount(30);
        textEmployeeEmail.setPromptText("Въведете имейл");
        HBox paneEmployeeEmail = new HBox(-30, labelEmployeeEmail, textEmployeeEmail);
        paneEmployeeEmail.setAlignment(Pos.CENTER);

        Label labelEmployeePassword = new Label("Парола");
        labelEmployeePassword.setPrefWidth(100);
        textEmployeePassword = new PasswordField();
        textEmployeePassword.setPrefColumnCount(30);
        textEmployeePassword.setPromptText("Въведете парола");
        HBox paneEmployeePassword = new HBox(-30, labelEmployeePassword, textEmployeePassword);
        paneEmployeePassword.setAlignment(Pos.CENTER);

        VBox paneCenterNewEmployee = new VBox(20, paneEmployeeName, paneEmployeeEmail, paneEmployeePassword);
        paneCenterNewEmployee.setAlignment(Pos.CENTER);

        saveEmployeeButton = new Button("Запази");
        saveEmployeeButton.setMinWidth(80);
        saveEmployeeButton.setOnAction(e -> saveEmployeeButtonOnClick());
        clearEmployeeButton = new Button("Изчисти");
        clearEmployeeButton.setMinWidth(80);
        clearEmployeeButton.setOnAction(e -> clearEmployeeButtonOnClick());
        toLoginFromEmployeeButton = new Button("Към логин екрана");
        toLoginFromEmployeeButton.setMinWidth(80);
        toLoginFromEmployeeButton.setOnAction(e -> toLoginFromEmployeeButtonOnClick());

        HBox paneBottomNewEmployee = new HBox(20, saveEmployeeButton, clearEmployeeButton, toLoginFromEmployeeButton);
        paneBottomNewEmployee.setAlignment(Pos.CENTER);
        paneBottomNewEmployee.setPadding(new Insets(40, 10, 20, 10));

        BorderPane paneMainEmployeeForm = new BorderPane();
        BorderPane.setMargin(paneBottomNewEmployee, new Insets(0, 0, 0, 70));
        paneMainEmployeeForm.setTop(paneTopNewEmployee);
        paneMainEmployeeForm.setCenter(paneCenterNewEmployee);
        paneMainEmployeeForm.setBottom(paneBottomNewEmployee);
        paneMainEmployeeForm.setPadding(new Insets(80, 10, 180, 10));

        //tabEmoloyeeStats
        Text textHeadingEmployeeStats = new Text("Изберете служител от падащият списък");
        textHeadingEmployeeStats.setFont(new Font("Verdana", 18));
        HBox paneTopEmployeeStats = new HBox(textHeadingEmployeeStats);
        paneTopEmployeeStats.setAlignment(Pos.CENTER);
        paneTopEmployeeStats.setPadding(new Insets(10, 10, 25, 10));

        comboEmployee = new ComboBox<String>();
        comboEmployee.setPromptText("Избери служител");
        comboEmployee.setPrefWidth(140);
        loaderEmployees = new FXControlDataLoader("employees.txt");
        comboEmployee.setItems(FXCollections.observableArrayList(loaderEmployees.setComboModel()));
        comboEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (comboEmployee.getValue() == null) {
                } else {
                    FXControlDataLoader tableUpdater = new FXControlDataLoader("protocols.txt", comboEmployee.getValue());
                    tableEmployeeStats.setItems(FXCollections.observableArrayList(tableUpdater.updateTableModel()));
                    seeEntireStats.setVisible(true);
                    workedHoursSummary.setVisible(true);
                    workedHoursSummary.setText(FXControlDataLoader.workedHoursSummary);
                }
            }
        });

        seeEntireStats = new Button("За всички служители");
        seeEntireStats.setMinWidth(120);
        seeEntireStats.setOnAction(e -> seeEntireStatsButtonOnClick());
        seeEntireStats.setVisible(false);
        VBox emploeeStatsVbox = new VBox(20, comboEmployee, seeEntireStats);

        TableColumn<EmployeeStatsModel, String> columnEmployee =
                new TableColumn<EmployeeStatsModel, String>("СЛУЖИТЕЛ");
        TableColumn<EmployeeStatsModel, String> columnClient =
                new TableColumn<EmployeeStatsModel, String>("КЛИЕНТ");
        TableColumn<EmployeeStatsModel, String> columnDate =
                new TableColumn<EmployeeStatsModel, String>("ДАТА");
        TableColumn<EmployeeStatsModel, String> columnWorkedHours =
                new TableColumn<EmployeeStatsModel, String>("РАБОТЕНО ВРЕМЕ");
        columnEmployee.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("EmployeeName"));
        columnClient.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("ClientName"));
        columnDate.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("Date"));
        columnWorkedHours.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("WorkedHours"));
        columnWorkedHours.setMinWidth(150);
        columnDate.setStyle("-fx-alignment: CENTER;");
        columnWorkedHours.setStyle("-fx-alignment: CENTER;");
        tableEmployeeStats = new TableView<EmployeeStatsModel>();
        tableEmployeeStats.getColumns().addAll(columnEmployee, columnClient, columnDate, columnWorkedHours);
        loaderTable = new FXControlDataLoader("protocols.txt");
        tableEmployeeStats.setItems(FXCollections.observableArrayList(loaderTable.setTableModel()));
        tableEmployeeStats.setMinWidth(650);
        tableEmployeeStats.setMinHeight(300);
        HBox paneCenterEmployeeStats = new HBox(20, emploeeStatsVbox, tableEmployeeStats);
        paneCenterEmployeeStats.setAlignment(Pos.TOP_CENTER);

        toLoginFromEmployeeStatsButton = new Button("Към логин екрана");
        toLoginFromEmployeeStatsButton.setMinWidth(80);
        toLoginFromEmployeeStatsButton.setOnAction(e -> toLoginFromEmployeeStatsButtonOnClick());
        workedHoursSummary = new Label();
        Region spacer = new Region();
        spacer.setPrefWidth(220);

        HBox paneBottomEmployeeStats = new HBox(toLoginFromEmployeeStatsButton, spacer, workedHoursSummary);
        paneBottomEmployeeStats.setAlignment(Pos.CENTER);
        paneBottomEmployeeStats.setPadding(new Insets(25, 10, 20, 350));

        BorderPane paneMainEmployeeStats = new BorderPane();
        BorderPane.setMargin(paneBottomNewEmployee, new Insets(0, 0, 0, 70));
        paneMainEmployeeStats.setTop(paneTopEmployeeStats);
        paneMainEmployeeStats.setCenter(paneCenterEmployeeStats);
        paneMainEmployeeStats.setBottom(paneBottomEmployeeStats);
        paneMainEmployeeStats.setPadding(new Insets(40, 10, 180, 10));

        //tabEmoloyeeStatsForPeriod
        Text textHeadingEmployeeStatsForPeriod = new Text("Изберете служител, начална и крайна дата");
        textHeadingEmployeeStatsForPeriod.setFont(new Font("Verdana", 18));
        HBox paneTopEmployeeStatsForPeriod = new HBox(textHeadingEmployeeStatsForPeriod);
        paneTopEmployeeStatsForPeriod.setAlignment(Pos.CENTER);
        paneTopEmployeeStatsForPeriod.setPadding(new Insets(10, 10, 25, 10));

        Label labelEmployeeForPeriodCombo = new Label("Избери служител");

        comboEmployeeForPeriod = new ComboBox<String>();
        comboEmployeeForPeriod.setPromptText("Избери служител");
        comboEmployeeForPeriod.setPrefWidth(140);
        loaderEmployees = new FXControlDataLoader("employees.txt");
        comboEmployeeForPeriod.setItems(FXCollections.observableArrayList(loaderEmployees.setComboModel()));

        Label dateStartLabel = new Label("Избери начална дата");
        datePickerStart = new DatePicker();

        Label dateEndLabel = new Label("Избери крайна дата");
        datePickerEnd = new DatePicker();

        clearCboxAndDatePickersButton = new Button("Изчисти филтъра");
        clearCboxAndDatePickersButton.setOnAction(e -> clearCboxAndDatePickersButtonOnClick());
        applyFiltersButton = new Button("Приложи филтъра");
        applyFiltersButton.setOnAction(e -> applyFiltersButtonOnClick());


        VBox datePickerStartVbox = new VBox(10, dateStartLabel, datePickerStart);
        VBox datePickerEndVbox = new VBox(10, dateEndLabel, datePickerEnd);
        VBox emploeeStatsVboxForPeriod = new VBox(20, comboEmployeeForPeriod, datePickerStartVbox, datePickerEndVbox, clearCboxAndDatePickersButton, applyFiltersButton);

        TableColumn<EmployeeStatsModel, String> columnEmployeePeriod =
                new TableColumn<EmployeeStatsModel, String>("СЛУЖИТЕЛ");
        TableColumn<EmployeeStatsModel, String> columnClientPeriod =
                new TableColumn<EmployeeStatsModel, String>("КЛИЕНТ");
        TableColumn<EmployeeStatsModel, String> columnDatePeriod =
                new TableColumn<EmployeeStatsModel, String>("ДАТА");
        TableColumn<EmployeeStatsModel, String> columnWorkedHoursPeriod =
                new TableColumn<EmployeeStatsModel, String>("РАБОТЕНО ВРЕМЕ");
        columnEmployeePeriod.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("EmployeeName"));
        columnClientPeriod.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("ClientName"));
        columnDatePeriod.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("Date"));
        columnWorkedHoursPeriod.setCellValueFactory(
                new PropertyValueFactory<EmployeeStatsModel, String>("WorkedHours"));
        columnWorkedHoursPeriod.setMinWidth(150);
        columnDatePeriod.setStyle("-fx-alignment: CENTER;");
        columnWorkedHoursPeriod.setStyle("-fx-alignment: CENTER;");
        tableEmployeeStatsForPeriod = new TableView<EmployeeStatsModel>();
        tableEmployeeStatsForPeriod.getColumns().addAll(columnEmployeePeriod, columnClientPeriod, columnDatePeriod, columnWorkedHoursPeriod);
        loaderTable = new FXControlDataLoader("protocols.txt");
        tableEmployeeStatsForPeriod.setItems(FXCollections.observableArrayList(loaderTable.setTableModel()));
        tableEmployeeStatsForPeriod.setMinWidth(650);
        tableEmployeeStatsForPeriod.setMinHeight(300);

        HBox paneCenterEmployeeStatsForPeriod = new HBox(20, emploeeStatsVboxForPeriod, tableEmployeeStatsForPeriod);
        paneCenterEmployeeStatsForPeriod.setAlignment(Pos.TOP_CENTER);


        toLoginFromEmployeeStatsForPeriodButton = new Button("Към логин екрана");
        toLoginFromEmployeeStatsForPeriodButton.setMinWidth(80);
        toLoginFromEmployeeStatsForPeriodButton.setOnAction(e -> toLoginFromEmployeeStatsForPeriodButtonOnClick());
        workedHoursSummaryForPeriod = new Label();
        Region spacerForPeriod = new Region();
        spacerForPeriod.setPrefWidth(220);

        HBox paneBottomEmployeeStatsForPeriod = new HBox(toLoginFromEmployeeStatsForPeriodButton, spacerForPeriod, workedHoursSummaryForPeriod);
        paneBottomEmployeeStatsForPeriod.setAlignment(Pos.CENTER);
        paneBottomEmployeeStatsForPeriod.setPadding(new Insets(25, 10, 20, 350));

        BorderPane paneMainEmployeeStatsForPeriod = new BorderPane();
        BorderPane.setMargin(paneBottomNewEmployee, new Insets(0, 0, 0, 70));
        paneMainEmployeeStatsForPeriod.setTop(paneTopEmployeeStatsForPeriod);
        paneMainEmployeeStatsForPeriod.setCenter(paneCenterEmployeeStatsForPeriod);
        paneMainEmployeeStatsForPeriod.setBottom(paneBottomEmployeeStatsForPeriod);
        paneMainEmployeeStatsForPeriod.setPadding(new Insets(40, 10, 180, 10));


        tabPane = new TabPane();
        tabClient = new Tab("Регистрирай нов клиент");
        Tab tabEmployee = new Tab("Регистрирай нов служител");
        Tab tabEmployeeStats = new Tab("Виж статистика за служител");
        Tab tabEmployeeStatsForPeriod = new Tab("Виж статистика за слувител за период");
        tabClient.setContent(paneMainClient);
        tabEmployee.setContent(paneMainEmployeeForm);
        tabEmployeeStats.setContent(paneMainEmployeeStats);
        tabEmployeeStatsForPeriod.setContent(paneMainEmployeeStatsForPeriod);

        tabClient.setOnSelectionChanged(event -> {
            if (tabClient.isSelected()) {
                saveClientButton.setDefaultButton(true);
                saveEmployeeButton.setDefaultButton(false);
                toLoginFromEmployeeStatsButton.setDefaultButton(false);
                toLoginFromEmployeeStatsForPeriodButton.setDefaultButton(false);
            }
        });

        tabEmployee.setOnSelectionChanged(event -> {
            if (tabEmployee.isSelected()) {
                saveEmployeeButton.setDefaultButton(true);
                saveClientButton.setDefaultButton(false);
                toLoginFromEmployeeStatsButton.setDefaultButton(false);
                toLoginFromEmployeeStatsForPeriodButton.setDefaultButton(false);
            }
        });

        tabEmployeeStats.setOnSelectionChanged(event -> {
            if (tabEmployeeStats.isSelected()) {
                toLoginFromEmployeeStatsButton.setDefaultButton(true);
                saveClientButton.setDefaultButton(false);
                saveEmployeeButton.setDefaultButton(false);
                toLoginFromEmployeeStatsForPeriodButton.setDefaultButton(false);
            }
        });

        tabEmployeeStatsForPeriod.setOnSelectionChanged(event -> {
            if (tabEmployeeStatsForPeriod.isSelected()) {
                toLoginFromEmployeeStatsForPeriodButton.setDefaultButton(true);
                saveClientButton.setDefaultButton(false);
                saveEmployeeButton.setDefaultButton(false);
                toLoginFromEmployeeStatsButton.setDefaultButton(false);

            }
        });

        tabClient.setClosable(false);
        tabEmployee.setClosable(false);
        tabEmployeeStats.setClosable(false);
        tabEmployeeStatsForPeriod.setClosable(false);
        tabPane.getTabs().addAll(tabClient, tabEmployee, tabEmployeeStats, tabEmployeeStatsForPeriod);
        adminScene = new Scene(tabPane, 900, 500);
    }

    private void loginButtonOnClick() {
        boolean accountExists;
        String fileToReadFrom;
        String userType;
        if (radioEmployee.isSelected()) {
            fileToReadFrom = EMPLOYEES_FILE_NAME;
            userType = "empolyee";
        } else {
            fileToReadFrom = ADMINS_FILE_NAME;
            userType = "administrator";
        }
        DataValidator validator = new DataValidator(textName.getText(), textPassword.getText());
        String validatorResult = validator.validateLoginInput();
        if (!validatorResult.equals("OK")) {
            Alert a = new Alert(Alert.AlertType.WARNING, validatorResult);
            a.setTitle("Некоректни входни данни!");
            a.showAndWait();
        } else {
            AccountChecker accountChecker = new AccountChecker(textName.getText(), textPassword.getText(), fileToReadFrom);
            accountExists = accountChecker.fetchAccount();
            if (accountExists) {
                if (userType.equals("empolyee")) {
                    userFullName = textName.getText();
                    stage.setScene(employeeScene);
                    stage.setTitle("Система за следене на работното време. Влезли сте като: " + textName.getText());
                    textName.setText("");
                    textPassword.setText("");
                    stage.show();
                } else if (userType.equals("administrator")) {
                    userFullName = textName.getText();
                    stage.setScene(adminScene);
                    stage.setTitle("Система за следене на работното време. Администратор: " + textName.getText());
                    textName.setText("");
                    textPassword.setText("");
                    stage.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Нещо се обърка.");
                    a.setTitle("Екран на администраторът");
                    a.showAndWait();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING, "Акаунтът не е намерен.");
                a.setTitle("Търсене на акаунт");
                a.showAndWait();
            }
        }
    }

    private void clearButtonOnClick() {
        textName.setText("");
        textPassword.setText("");
    }

    private void saveProtocolButtonOnClick() {
        DataValidator validator = new DataValidator();
        String message = "";
        if (comboClients.getValue() == null) {
            message += "Изберете клиент от падащият списък! ";
        }
        if (!validator.validateEmployeeForm(textWorkedHours.getText())) {
            message += "Въведете отработено време във формат h:mm! ";
        }
        if (message.length() != 0) {
            Alert a = new Alert(Alert.AlertType.WARNING, message);
            a.setTitle("Търсене на акаунт");
            a.showAndWait();
        } else {
            String name = userFullName;
            String client = comboClients.getValue();
            String dateFormated = dateString;
            String workedHours = textWorkedHours.getText();
            String[] protocolArray = {name, client, dateFormated, workedHours};
            FileContentsWriter writer = new FileContentsWriter("protocols.txt", protocolArray);
            writer.writeToFile();
            textWorkedHours.setText("");
            comboClients.setValue(null);
        }
    }

    private void toLogginScreenButtonOnClick() {
        comboClients.setValue(null);
        textWorkedHours.setText("");
        textPassword.setText("");
        stage.setScene(loginScene);
        stage.setTitle("Система за следене на работното време");
        stage.show();
    }

    private void saveClientButtonOnClick() {
        DataValidator mailValidator = new DataValidator();
        ClientAndEmployeeChecker clientAndEmployeeChecker = new ClientAndEmployeeChecker(textClientName.getText().trim(), textClientEmail.getText().trim(), "clients.txt");
        String message = "";
        String[] clientData = new String[2];
        if (textClientName.getText().trim().length() == 0) {
            message += "Моля въведете клиент! ";
        }
        if (textClientEmail.getText().trim().length() == 0) {
            message += "Моля въведете имейл! ";
        }
        if (textClientEmail.getText().trim().length() != 0) {
            if (!mailValidator.validateEmail(textClientEmail.getText().trim())) {
                message += "Невалиден формат на имейл!";
            }
        }
        if (message.length() == 0) {
            if (clientAndEmployeeChecker.fetchAccount()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Този клиент или тази парола вече съществуват в базата данни!");
                a.setTitle("Проверка за контрагент");
                a.showAndWait();
            } else {
                clientData[0] = textClientName.getText().trim();
                clientData[1] = textClientEmail.getText().trim();
                FileContentsWriter writer = new FileContentsWriter("clients.txt", clientData);
                writer.writeToFile();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, message);
            a.setTitle("Невалидни данни");
            a.showAndWait();
        }
    }

    private  void clearClientButtonOnClick() {
        textClientName.setText("");
        textClientEmail.setText("");
    }

    private  void toLoginFromClientButtonOnClick() {
        clearClientButtonOnClick();
        stage.setScene(loginScene);
        stage.setTitle("Система за следене на работното време");
        stage.show();
    }

    private void saveEmployeeButtonOnClick() {
        DataValidator emailValidator = new DataValidator();
        AccountChecker emailChecker = new AccountChecker("employees.txt", textEmployeeEmail.getText().trim());
        String message = "";
        String[] employeeData = new String[3];
        if (textEmployeeName.getText().trim().length() == 0) {
            message += "Моля въведете трите имена на служителят! ";
        }
        if (textEmployeeName.getText().trim().length() != 0) {
            if (!textEmployeeName.getText().trim().matches("[А-Яа-я]{2,10}[\\s]{1}[А-Яа-я]{2,15}[\\s]{1}[А-Яа-я]{2,15}")) {
                message += "Неправилен формат на името! Въведете на кирилица трите си имена, разделени с интервал. ";
            }
        }
        if (textEmployeeEmail.getText().trim().length() == 0) {
            message += "Моля въведете имейл! ";
        }
        if (textEmployeeEmail.getText().trim().length() != 0) {
            if (!emailValidator.validateEmail(textEmployeeEmail.getText().trim())) {
                message += "Невалиден фоормат на имейл! ";
            }
        }
        if (textEmployeePassword.getText().trim().length() == 0) {
            message += "Моля въведете парола! ";
        }
        if (textEmployeePassword.getText().trim().length() != 0) {
            if (!textEmployeePassword.getText().trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{6,}$")) {
                message += "Невалиден формат на паролата! Поне шест символа, малка буква, голяма буква, число и специален символ на латиница! ";
            }
        }
        if (message.length() == 0) {
            if (emailChecker.fetchEmail()) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Този имейл вече съществува в базата!");
                a.setTitle("Валидация на нов акаунт");
                a.showAndWait();
            } else {
                String[] newEmployee = new String[3];
                newEmployee[0] = textEmployeeName.getText().trim();
                newEmployee[1] = textEmployeeEmail.getText().trim();
                newEmployee[2] = textEmployeePassword.getText().trim();
                FileContentsWriter writer = new FileContentsWriter("employees.txt", newEmployee);
                writer.writeToFile();
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Записът е успешен.");
                a.setTitle("Валидация акаунт на нов служител");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, message);
            a.setTitle("Валидация акаунт на нов служител");
            a.showAndWait();
        }
    }

    private  void clearEmployeeButtonOnClick() {
        textEmployeeName.setText("");
        textEmployeeEmail.setText("");
        textEmployeePassword.setText("");
    }

    private void toLoginFromEmployeeButtonOnClick() {
        clearEmployeeButtonOnClick();
        stage.setScene(loginScene);
        stage.setTitle("Система за следене на работното време");
        stage.show();
    }

    private void toLoginFromEmployeeStatsButtonOnClick() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);
        loaderTable = new FXControlDataLoader("protocols.txt");
        tableEmployeeStats.setItems(FXCollections.observableArrayList(loaderTable.setTableModel()));
        comboEmployee.setValue(null);
        workedHoursSummary.setVisible(false);
        stage.setScene(loginScene);
        stage.setTitle("Система за следене на работното време");
        stage.show();
    }

    private void seeEntireStatsButtonOnClick() {
        loaderTable = new FXControlDataLoader("protocols.txt");
        tableEmployeeStats.setItems(FXCollections.observableArrayList(loaderTable.setTableModel()));
        seeEntireStats.setVisible(false);
        workedHoursSummary.setVisible(false);
        comboEmployee.setValue(null);
    }
}
