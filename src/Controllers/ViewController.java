package Controllers;

import Controllers.BookAPI.BookAPI;
import Controllers.DatabaseControllers.DBInitialize;
import Models.Book;
import Models.frontend.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main controller class for th gui fxml file that has all the functionality need to provide a smooth user experience.
 * The class also interacts with the data manager.
 *
 * @author Rajashow
 */
public class ViewController {

    private final String addIconImg = "/Models/media/add.png";
    private final String updateIconImg = "/Models/media/upload.png";
    private final String deleteIconImg = "/Models/media/delete.png";
    private final String programeIconImg = "/Models/media/icon.png";

    private int runningLimit = 0;

    @FXML
    private LimitedTextField courseInfoTitle = new LimitedTextField(), courseInfoDepart = new LimitedTextField(),
            courseInfoDescrip = new LimitedTextField(), profInfoFName = new LimitedTextField(),
            profInfoLName = new LimitedTextField(), crnSearchTF = new LimitedTextField(),
            profSearchTF = new LimitedTextField(), courseSearchTF = new LimitedTextField(),
            departSearchTF = new LimitedTextField(), resourceSearchTF = new LimitedTextField(),
            courseInfoCRN = new LimitedTextField(), courseInfoNotes = new LimitedTextField(),
            profInfoFNameTf = new LimitedTextField(), profInfoLNameTf = new LimitedTextField();

    //promt text


    @FXML
    ListView<String> resInfoList;
    @FXML
    Button infoBtn, searchBtn, profInfoBtn, resEditBtn, addBtn, updateBtn, deleteBtn, filterBtn, helpBtn;
    @FXML
    TableView<Course> tableTV;
    @FXML
    TableColumn<Course, String> resourceCol, profCol, courseCol, departCol, timeCol;
    @FXML
    ComboBox<Integer> yearComBox, yearComBoxEdit;
    @FXML
    ComboBox semesterComBox, semesterComBoxEdit, profInfoType;
    @FXML
    CheckBox profCB, courseCB, departCB, resCB;
    @FXML
    TitledPane moreInfoTP;
    private boolean debugging;
    private TableView<Resource> resourceTable;
    private TableColumn<Resource, String> publisherCol, nameCol, authorCol, idcCol, editionCol;
    private ArrayList<Course> courseList, templateList;
    private ArrayList<Person> profList;
    private ArrayList<Resource> resList;
    private ArrayList<Publisher> pubList;
    private Course selectedCourse;
    private Course selectedCourseTemplate;
    private Resource selectedResource;
    private Publisher selectedPublisher;
    private Person selectedPerson;
    private int defaultSemester = 5;
    private Models.backend.Semester defaultSemest = new Models.backend.Semester();
    private boolean isPersonResourcesView = false;


    /**
     * This is initializes the start state.
     * The initial state includes:
     * - values for the combo boxes
     * - value factory for the columns based on the Object "Course"
     * - initial values to display on the table
     * - initializes the resource table
     * - marking all the filter checkboxes to true
     */
    private void test() {
        System.out.println("The program started running now!");

    }


    private static void showError(String title, String headerMessage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    private final ArrayList<ComboBox> askSemester() {
        Dialog dlg = new Dialog();
        HBox mainPane = new HBox();
        ArrayList<ComboBox> returnedList = new ArrayList<ComboBox>();
        dlg.setTitle("Choose a semester for Course_Resources");
        dlg.setHeaderText("Please choose a semester for exporting Course Resources data!");
        ImageView icon = new ImageView(programeIconImg);
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);

        Label semesterLB = new Label("Choose a semester   ");
        ComboBox semester = new ComboBox();
        ComboBox<Integer> yearCB = new ComboBox<Integer>();
        ArrayList<Integer> year = new ArrayList<>();
        ButtonType next = new ButtonType("Next", ButtonBar.ButtonData.NEXT_FORWARD);


        semester.getItems().addAll(Semester.values());
        for (int i = Calendar.getInstance().get(Calendar.YEAR) - 1; i < Calendar.getInstance().get(Calendar.YEAR) + 2; i++)
            year.add(i);
        yearCB.getItems().addAll(year);

        yearCB.getSelectionModel().select(new Integer(defaultSemest.getYear()));
        semester.getSelectionModel().select(controller.convertSeasonDBtoGUI(defaultSemest.getSeason()));


        mainPane.getChildren().

                addAll(
                        new VBox(semesterLB),
                        new VBox(10, semester),
                        new VBox(10, yearCB)
                );
        returnedList.add(yearCB);
        returnedList.add(semester);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setStyle("-fx-border-radius: 10px;");

        dlg.getDialogPane().

                setContent(mainPane);
        dlg.getDialogPane().

                getButtonTypes().

                addAll(next);

        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == next) {
                File exportFile = pickSaveFile("All Courses with Resources");
                saveFile(DBManager.exportCSVCourseResources(semester, yearCB), exportFile);
            }
            return null;
        });
        return returnedList;
    }


    @FXML
    public void initialize() {
        ShowHelpContextMenuOnHelpBtnClick();
        infoBtn.setOnMouseClicked(e -> showInfo());
        try {
            DBManager.openConnection();
        } catch (FileNotFoundException e) {
            showError("DBinformation file not found",
                    "The DBinformation.txt file was not found",
                    "The DBinformation.txt file was not found. Press Ok to continue to add Database information.");
            runningLimit++;
            if (runningLimit >= 2) {
                System.exit(15);
            }

            databaseInit();
            initialize();


        }
        catch (SQLException e) {
            e.printStackTrace();
            showError("Connection Error", "The database did not return any  data",
                    "Check your internet connection, and database settings provided" +
                            " in DBinformation.txt file");
            // a pop up window ask if she wants to re-install db?
            return;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        defaultSemest = controller.findDefaultSemester();
        debugging = true;
        courseList = new ArrayList<>();
        profList = new ArrayList<>();
        resList = new ArrayList<>();
        pubList = new ArrayList<>();
        initComboBoxes();
        initResourcesTable();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTables();
        initCheckBoxes();
        addButtonGraphics();
        resetSelect();
        if (debugging) {
            test();
            // Department textbox in the search is disabled. As default always computer science
            departSearchTF.setDisable(true);
            departSearchTF.setText("Computer Science");
        }
        setTextFieldSMaxLength();

    }

    private void ShowHelpContextMenuOnHelpBtnClick() {

        helpBtn.setOnMouseClicked(e -> {
            contextMenu.show(helpBtn, e.getScreenX(), e.getScreenY());

        });


    }

  

    private void databaseInit() {

//        final String serverPath;
//        File file = new File("DBinformation.txt");

        Dialog dlg = new Dialog();
        dlg.setTitle("Database Information");
        dlg.setHeaderText("Add the database information or Install the database\n\nWarning: Installing the Database will" +
                " delete everything from the it!\nPlease contact your administrator if needed.");
        VBox mainpane = new VBox(20);

        TextField host = new TextField();
        host.setPromptText("e.g., acoracle.host.edu");
        host.setMaxWidth(Double.MAX_VALUE);
        host.setMinWidth(500);

        TextField port = new TextField();
        port.setPromptText("e.g., 1515");
        port.setMaxWidth(Double.MAX_VALUE);
        port.setMinWidth(500);

        TextField userName = new TextField();
        userName.setPromptText("e.g., admin");
        userName.setMaxWidth(Double.MAX_VALUE);
        userName.setMinWidth(500);

        TextField password = new TextField();
        password.setPromptText("e.g., password");
        password.setMaxWidth(Double.MAX_VALUE);
        password.setMinWidth(500);

        TextField SID = new TextField("acoradb");


        SID.setPromptText("e.g., XE");
        SID.setMaxWidth(Double.MAX_VALUE);
        SID.setMinWidth(500);

        mainpane.getChildren().addAll(
                new HBox(25, new Label("Host:* "), host),
                new HBox(25, new Label("Username:* "), userName),
                new HBox(25, new Label("Password:* "), password),
                new HBox(25, new Label("Port:* "), port),
                new HBox(25, new Label("SID:* "), SID)
        );
        dlg.getDialogPane().setContent(mainpane);
        ButtonType addTxtBtn = new ButtonType("Add DB Info text File");
        ButtonType initBtn = new ButtonType("Add file and Install DB",ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType cancelBtn = ButtonType.CANCEL;


        dlg.getDialogPane().getButtonTypes().addAll(cancelBtn, addTxtBtn, initBtn );
        dlg.setWidth(1000);
        dlg.setResultConverter(dialogButton -> {

            if (dialogButton == addTxtBtn || dialogButton == initBtn) {
                System.out.println("init or addTxt button pressed!");
                //Check for textBoxes

                if (!userName.getText().equals("") && !password.getText().equals("") && !host.getText().equals("") &&
                        !port.getText().equals("") && !SID.getText().equals("")) {
                    if (!controller.writeDBTxt(userName, password, host, port, SID)) {

                        showError("Warning", "Failed to add DB information text file!",
                                "");
                        exit();
                    }


                    boolean error = false;
                    try {
                        DBManager.openConnection();
                    } catch (FileNotFoundException e) {
                        error = true;
                    } catch (SQLException e) {
                        error = true;
                    } catch (ClassNotFoundException e) {
                        error = true;
                    }

                    if (error) {
                        showError("Connection Error", "Setting up the database failed!",
                                "There was a problem with connecting to the DB! Please try again and make sure" +
                                        "you are putting the credentials correctly!");
                        exit();
                    }


                    if (dialogButton == initBtn)
                        askForPassword();
                }
                else
                    showError("Missing data", "Please make sure to add all the data and try agian!",
                            "");
            }

            return null;
        });

        dlg.showAndWait();


    }


    private final void askForPassword() {


        Dialog dlg = new Dialog();
        HBox mainPane = new HBox();

        dlg.setTitle("Enter the password");
        dlg.setHeaderText("Please enter the password for initializing the database!"+
                "(Remember, initializing db will delete all the current data on database)");
        ImageView icon = new ImageView(programeIconImg);
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);

        Label passwordLB = new Label("Admin Password:  ");
        LimitedTextField passwordTxt = new LimitedTextField();

        ButtonType next = new ButtonType("Next", ButtonBar.ButtonData.NEXT_FORWARD);

        mainPane.getChildren().

                addAll(
                        new VBox(passwordLB),
                        new VBox(10, passwordTxt)
                );

        mainPane.setAlignment(Pos.CENTER);
        mainPane.setStyle("-fx-border-radius: 10px;");

        dlg.getDialogPane().

                setContent(mainPane);
        dlg.getDialogPane().

                getButtonTypes().removeAll();

        dlg.getDialogPane().getButtonTypes().addAll(next);

        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == next) {
               if(passwordTxt.getText().equals("9090"))
               {
                   if (controller.confirmation())
                        DBInitialize.InitDBTables();
               }
               else{
                   showError("Password is Wrong!", "The password entered is wrong!","");
               }
            }
            return null;
        });

        dlg.showAndWait();
    }


    private void showInfo() {
        Dialog dlg = new Dialog();
        dlg.setTitle("MC Books Library App Information");
        ImageView icon = new ImageView(programeIconImg);
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);
        dlg.setHeaderText(
                controller.multiplyStr("\t", 6) + "MC Books Library Manager\n" +
                        controller.multiplyStr("\t", 8) + "Version 1.0");
        VBox box = new VBox(25);
        Button btn = new Button("Click here to open GitHub!");
        btn.setTextFill(javafx.scene.paint.Paint.valueOf("#21618C"));
        Text t = new Text();
        t.setText("Found a bug or want to contribute?\n" +
                "Feel free to fork the project, make changes, and make a pull request!");

        controller.setTextStyle(t);


        box.getChildren().addAll(t, btn);
        dlg.getDialogPane().setContent(box);
        box.setAlignment(Pos.CENTER);
        dlg.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dlg.setResizable(true);
        dlg.setWidth(300);
        dlg.setHeight(300);

        btn.setOnMouseClicked(e -> {
            String gitHubIssuePageURL = "https://github.com/mmohades/Book-Library";
            try {
                if (Desktop.isDesktopSupported()) {

                    Desktop.getDesktop().browse(URI.create(gitHubIssuePageURL));
                } else {
                    throw new IOException();
                }
            } catch (IOException exx) {
                try {
                    new ProcessBuilder("x-www-browser", gitHubIssuePageURL).start();
                } catch (IOException e1) {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        runtime.exec("xdg-open " + gitHubIssuePageURL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        dlg.showAndWait();

    }

    private void setTextFieldSMaxLength() {

        courseInfoTitle.setMaxLength(8);
        courseInfoDepart.setMaxLength(120);
        courseInfoDescrip.setMaxLength(250);
        courseInfoCRN.setMaxLength(16);
        courseInfoNotes.setMaxLength(120);

        profInfoFName.setMaxLength(25);
        profInfoLName.setMaxLength(25);

        courseSearchTF.setMaxLength(8);
        departSearchTF.setMaxLength(120);
        profSearchTF.setMaxLength(50);
        crnSearchTF.setMaxLength(8);
        resourceSearchTF.setMaxLength(256);
    }


    private void addButtonGraphics() {
        String searchIconImg = "/Models/media/search.png";
        ImageView searchImg = new ImageView(searchIconImg);
        ImageView addImg = new ImageView(addIconImg);
        ImageView deleteImg = new ImageView(deleteIconImg);
        ImageView updateImg = new ImageView(updateIconImg);
        String filterIconImg = "/Models/media/filter.png";
        ImageView filterImg = new ImageView(filterIconImg);

        ImageView tutorialImg = new ImageView("/Models/media/tutorial.png");
        ImageView infoImg = new ImageView("Models/media/info.png");


        addGraphicToButtons(searchImg, searchBtn);
        addGraphicToButtons(addImg, addBtn);
        addGraphicToButtons(deleteImg, deleteBtn);
        addGraphicToButtons(updateImg, updateBtn);
        addGraphicToButtons(filterImg, filterBtn);
        addGraphicToButtons(tutorialImg, helpBtn);
        addGraphicToButtons(infoImg, infoBtn);


    }

    private void addGraphicToButtons(ImageView img, Button button) {
        img.setFitHeight(20);
        img.setFitWidth(90);
        img.setPreserveRatio(true);
        button.setGraphic(img);
        button.setText("");
    }

    /**
     * Marks all the filter checkboxes to true
     */
    private void initCheckBoxes() {
        profCB.setSelected(true);
        courseCB.setSelected(true);
        departCB.setSelected(true);
        resCB.setSelected(true);
    }

    /**
     * Initializes the resource table.
     * > makes a instance of TableView object
     * > makes columns for the Resource :
     * - publisher
     * - Author
     * - ID
     * - Title
     * > sets attributes for the resource table
     */
    private void initResourcesTable() {
        resourceTable = new TableView<Resource>();
        publisherCol = new TableColumn<Resource, String>("Publisher");
        nameCol = new TableColumn<Resource, String>("Title");
        authorCol = new TableColumn<Resource, String>("Author");
        idcCol = new TableColumn<Resource, String>("ID");
        editionCol = new TableColumn<Resource, String>("Edition");

        if (!resourceTable.getColumns().addAll(nameCol, authorCol, editionCol, publisherCol)) {
            showError("Resource Table could not be created",
                    "Quiting",
                    "Error code 5567\n" +
                            " Resource table cannot be created. If this persists contact your fellow interns.");
            System.exit(5567);
        }

        idcCol.setPrefWidth(100);
        editionCol.setPrefWidth(70);
        nameCol.setPrefWidth(170);
        authorCol.setPrefWidth(110);
        publisherCol.setPrefWidth(150);
        resourceTable.setPrefWidth(500);

    }

    /**
     * Initialize values for the combo boxes.
     * <p>
     * For the semester combo boxes add the semesters.
     * <p>
     * For the year combo boxes add the all the years since 1946 to next year
     */
    private void initComboBoxes() {
        semesterComBox.getItems().addAll(Semester.values());
        semesterComBoxEdit.getItems().addAll(Semester.values());
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR) - 1; i < Calendar.getInstance().get(Calendar.YEAR) + 2; i++)
            years.add(i);
        yearComBox.getItems().addAll(years);
        yearComBoxEdit.getItems().addAll(years);
        profInfoType.getItems().addAll(PersonType.values());

        //init default semester
        yearComBox.getSelectionModel().select(new Integer(defaultSemest.getYear()));
        yearComBoxEdit.getSelectionModel().select(new Integer(defaultSemest.getYear()));
        semesterComBox.getSelectionModel().select(controller.convertSeasonDBtoGUI(defaultSemest.getSeason()));
        semesterComBoxEdit.getSelectionModel().select(controller.convertSeasonDBtoGUI(defaultSemest.getSeason()));

    }


    private Boolean[] getSearchPattern(TextField... textFields) {
        Boolean[] searchPattern = new Boolean[textFields.length];
        for (int index = 0; index < textFields.length; index++) {
            Boolean isTextfieldEmpty;
            isTextfieldEmpty = !textFields[index].getText().isEmpty();
            searchPattern[index] = isTextfieldEmpty;
        }
        return searchPattern;
    }

    public void search() {

        // Searching professor individually: works
        // Searching course individually: works
        // Seraching resource individually: does not work
        // Any combination: does not work
        // Common ID:

        // get the info in all the comboboxes
        //check if they are empty.

        String commonid = "";
        String professorname = "";
        String coursename = "";
        String courseCode = "";
        String resource = "";
        boolean commonid_full = false;
        boolean professorname_full = false;
        boolean coursename_full = false;
        boolean resource_full = false;
        ArrayList<Integer> ids_from_professorname = new ArrayList<>();
        ArrayList<Integer> ids_from_coursename = new ArrayList<>();
        ArrayList<Integer> ids_from_resources = new ArrayList<>();
        ArrayList<Integer> all_ids = new ArrayList<>();
        ArrayList<Integer> semester_ids = new ArrayList<>();
        ArrayList<Course> tmp_courses = new ArrayList<>();

        Set<Integer> hashset = new HashSet<>();

        String year = yearComBox.getValue().toString();
        String semester = semesterComBox.getValue().toString();
        semester = semester.toLowerCase();
        semester = semester.substring(0, 1).toUpperCase() + semester.substring(1);
        semester = semester.replace('_', ' ');
        int semesterid = DBManager.getSemesterIDByName(semester, year);

        semester_ids.addAll(Objects.requireNonNull(DBManager.find_classids_by_semester_id(semesterid)));
//        Boolean[] searchPattern = getSearchPattern(crnSearchTF, courseSearchTF, profSearchTF, departSearchTF, resourceSearchTF);
        if (!crnSearchTF.getText().isEmpty()) {
            commonid = crnSearchTF.getText();
            if (!controller.isInteger(commonid)) {
                showError("Unable to search",
                        "Unable to search because the commonID you entered is not valid.",
                        "Correct format examples --> 180, 98 ");
            } else
                commonid_full = true;
        }
        if (!profSearchTF.getText().isEmpty()) {

            professorname = profSearchTF.getText().substring(0, 1).toUpperCase() + profSearchTF.getText().substring(1).toLowerCase();

            professorname_full = true;

            ids_from_professorname = DBManager.find_classids_by_professor_name(professorname);

        }
        if (!courseSearchTF.getText().isEmpty()) {
            coursename = courseSearchTF.getText();

            String tempCourseTitle = coursename.replaceAll("\\s", "");
            String[] cSplit = tempCourseTitle.split("(?<=\\D)(?=\\d)");

            if (cSplit.length != 2) {

                showError("Inout Error",
                        "Unable to search because the course title format in the search box " +
                                "is not valid.",
                        "Correct format examples --> CMSC 100, MATH 181 ");
            } else if (!controller.isInteger(cSplit[1])) {
                showError("Input Error",
                        "Unable to search because the course title format in the search box " +
                                "is not valid.",
                        "Correct format examples --> CMSC 100, MATH 181 ");
            } else
                coursename_full = true;
            coursename = cSplit[1];
            courseCode = cSplit[0].toUpperCase();


            ids_from_coursename = DBManager.find_classids_by_course_name(courseCode, coursename);
        }
        if (!resourceSearchTF.getText().isEmpty()) {
            resource = controller.capitalizeString(resourceSearchTF.getText());
            resource_full = true;

            ids_from_resources = DBManager.find_classids_by_resource_name(resource);
        }

        if (!commonid_full && !professorname_full && !coursename_full && !resource_full) {


            refreshTable();
            return;
        } else if (commonid_full) {

            Course c = DBManager.find_class_by_commonid(Integer.parseInt(commonid));
            tmp_courses.add(c);

        } else if (professorname_full && coursename_full && resource_full) {

            for (Integer id : ids_from_coursename) {
                if (ids_from_professorname.contains(id) && ids_from_resources.contains(id) && semester_ids.contains(id)) {

                    hashset.add(id);

                }
            }
            all_ids.addAll(hashset);

            hashset.clear();

            for (Integer id : all_ids) {

                tmp_courses.add(DBManager.find_class_by_commonid(id));
            }

        } else if (professorname_full && coursename_full) {

            for (Integer id : ids_from_coursename) {

                if (ids_from_professorname.contains(id) && semester_ids.contains(id)) {

                    hashset.add(id);

                }

            }

            all_ids.addAll(hashset);

            hashset.clear();

            for (Integer id : all_ids) {

                tmp_courses.add(DBManager.find_class_by_commonid(id));

            }

        } else if (professorname_full && resource_full) {

            for (int i = 0; i < ids_from_resources.size(); i++) {

                if (ids_from_professorname.contains(ids_from_resources.get(i)) && semester_ids.contains(i)) {

                    hashset.add(ids_from_resources.get(i));

                }

            }

            all_ids.addAll(hashset);

            for (int i = 0; i < all_ids.size(); i++) {

                tmp_courses.add(DBManager.find_class_by_commonid(all_ids.get(i)));

            }

            hashset.clear();
            all_ids.clear();

        } else if (coursename_full && resource_full) {

            for (int i = 0; i < ids_from_coursename.size(); i++) {

                if (ids_from_resources.contains(ids_from_coursename.get(i)) && semester_ids.contains(i)) {

                    hashset.add(ids_from_coursename.get(i));

                }

            }

            all_ids.addAll(hashset);

            for (int i = 0; i < all_ids.size(); i++) {

                tmp_courses.add(DBManager.find_class_by_commonid(all_ids.get(i)));

            }

            hashset.clear();
            all_ids.clear();

        } else if (professorname_full) {

            for (Integer id : ids_from_professorname) {

                if (semester_ids.contains(id)) {

                    tmp_courses.add(DBManager.find_class_by_commonid(id));
                }

            }


        } else if (coursename_full) {

            for (Integer id : ids_from_coursename) {

                if (semester_ids.contains(id)) {

                    tmp_courses.add(DBManager.find_class_by_commonid(id));
                }

            }

        } else if (resource_full) {

            for (Integer id : ids_from_resources) {

                if (semester_ids.contains(id)) {

                    tmp_courses.add(DBManager.find_class_by_commonid(id));
                }
            }

        } else {
            showError("Search ERROR", "Error when searching", "Something went wrong when searching.");
        }
        courseList.clear();
        courseList.addAll(tmp_courses);
        updateCourseTable();


    }

    public void updateRowSelected() {
        // Filling textboxes on the left, selectedcourse

        if (!isPersonResourcesView) {
            selectedCourse = tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1);
            if (selectedCourse != null) {
                setChildVisibility(true, updateBtn, deleteBtn);
                courseInfoDescrip.setText("" + selectedCourse.getDescription());
                profInfoFName.setText(selectedCourse.getProfessor().getFirstName());
                profInfoLName.setText(selectedCourse.getProfessor().getLastName());
                profInfoType.setValue(selectedCourse.getProfessor().getType());
                selectedPerson = selectedCourse.getProfessor();
                courseInfoTitle.setText(selectedCourse.getTitle());
                courseInfoDepart.setText(selectedCourse.getDepartment());
                if (selectedCourse.getNotes() != (null))
                    courseInfoNotes.setText(selectedCourse.getNotes());
                else
                    courseInfoNotes.setText("");

                if (selectedCourse.getCRN() != (null))
                    courseInfoCRN.setText(String.format("%d", selectedCourse.getCRN().intValue()));
                    else
                        courseInfoCRN.setText("");
                semesterComBoxEdit.getSelectionModel().select(selectedCourse.getSEMESTER());
                yearComBoxEdit.getSelectionModel().select(new Integer(selectedCourse.getYEAR()));
                ArrayList<Resource> tempRes = selectedCourse.getResource();
                resourceTable.getItems().clear();
                resInfoList.getItems().clear();
                resourceTable.getSelectionModel().select(null);
                for (Resource resource : tempRes) {
                    resInfoList.getItems().add(resource.getTitle());
                    resourceTable.getItems().add(resource);
                }
                moreInfoTP.setExpanded(false);

                if ((selectedCourse.getNotes() != null && !selectedCourse.getNotes().isEmpty()) ||
                        selectedCourse.getCRN() != null ) {
                    moreInfoTP.setExpanded(true);
                }
            }
        }
        if (tableTV.getSelectionModel().getSelectedItems().isEmpty()) {
            resetSelect();
        }
    }

    private void updateCourseTable() {

        tableTV.getItems().clear();
        tableTV.getItems().addAll(courseList);

        if (selectedCourse != null && courseList != null)
            tableTV.getSelectionModel().select(controller.searchForCourse(selectedCourse, courseList));
    }

    /**
     * Un-selects the selected row when clicking on background
     */
    private void resetSelect() {
        tableTV.getSelectionModel().clearSelection();
        setChildVisibility(false, updateBtn, deleteBtn);
    }

    private boolean checkFirstRequiredBoxes() {
        String tempCourseTitle = courseInfoTitle.getText().replaceAll("\\s", "");
        String[] cSplit = tempCourseTitle.split("(?<=\\D)(?=\\d)");

        if (courseInfoDepart.getText().trim().isEmpty() || courseInfoDescrip.getText().trim().isEmpty() || courseInfoTitle.getText().trim().isEmpty() ||
                profInfoFName.getText().trim().isEmpty() || profInfoLName.getText().trim().isEmpty() || profInfoType.getSelectionModel().getSelectedItem() == null ||
                resourceTable.getItems().isEmpty() ||
                yearComBoxEdit.getSelectionModel().getSelectedItem() == null || semesterComBoxEdit.getSelectionModel().getSelectedItem() == null) {
            showError("Error", "Missing info", "You need to fulfill all sections");
            return false;
        }
        else if (cSplit.length != 2) {

            showError("Input Error",
                    "Unable to insert because the course you title entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181 ");
            return false;

        } else if (!controller.isInteger(cSplit[1])) {
            showError("Inout Error",
                    "Unable to insert because the course you title entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181 ");
            return false;

        }
        else {
            if (courseInfoCRN.getText() != null)
                if (!courseInfoCRN.getText().isEmpty() && !controller.isInteger(courseInfoCRN.getText())) {
                    showError("Error", "CRN must be a number", "Re-type CRN");
                    return false;
                }
            return true;
        }
    }

    public void add() {

        if (checkFirstRequiredBoxes()) {

            Course tempCour = new Course();
            Person tempPers = new Person();
            ArrayList<Resource> tempRes = new ArrayList<>(resourceTable.getItems());

            String firstName = profInfoFName.getText().substring(0, 1).toUpperCase() + profInfoFName.getText().substring(1).toLowerCase();
            String lastName = profInfoLName.getText().substring(0, 1).toUpperCase() + profInfoLName.getText().substring(1).toLowerCase();
            String type = profInfoType.getSelectionModel().getSelectedItem().toString();

            tempPers = new Person(lastName, firstName, type);
            tempPers.setID(DBManager.insertPersonQuery(tempPers));

            tempCour = new Course(
                    0,
                    null,
                    Integer.parseInt(yearComBoxEdit.getSelectionModel().getSelectedItem().toString()),
                    semesterComBoxEdit.getSelectionModel().getSelectedItem().toString(),
                    courseInfoTitle.getText(),
                    courseInfoDepart.getText(),
                    tempPers,
                    courseInfoDescrip.getText(),
                    tempRes);
            tempCour.setID(DBManager.insertCourseQuery(tempCour));
            controller.addNoteAndCRN(tempCour, courseInfoNotes.getText(), courseInfoCRN.getText());
            tempCour = DBManager.relationalInsertByID2(tempCour);
            if (isClassInTheSameYear(tempCour)) {
                courseList.add(tempCour);
            }


            DBManager.updateCRNAndNoteForClass(courseInfoCRN.getText(), courseInfoNotes.getText(), tempCour);


        }
        updateCourseTable();
    }


    public void filterTableBasedOnSemesterNYear() {
        refreshTable();
    }


    /**
     * populates the table with initial values
     */
    private void initTables() {
        try {

            setTablesSelectionProperty(tableTV);
            setTablesSelectionProperty(resourceTable);
//            DBManager.openConnection();

            ArrayList<Course> coursesPulledDatabase = DBManager.returnEverything2(defaultSemest.getId());

            if (coursesPulledDatabase == null) {
                showError("Connection Error", "The database did not return any  data",
                        "Check your connection,and database credentials in DBinformation.txt file");
                coursesPulledDatabase = new ArrayList<>();
            }

            for (Course course : coursesPulledDatabase) {
                courseList.add(course);
                for (Resource r : course.getResource()) {
                    if (!resList.contains(r))
                        resList.add(r);

                }

            }

            for (Resource tempR : resList) {
                if (!pubList.contains(tempR.getPublisher()))
                    pubList.add(tempR.getPublisher());
            }


            updateCourseTable();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Connection Error", "The database did not return any  data",
                    "Check your internet connection, and database credentials provided" +
                            " in DBinformation.txt file");

        }
    }

    private <T> void setTablesSelectionProperty(TableView<T> table) {
        table.setRowFactory(new Callback<TableView<T>, TableRow<T>>() {
            @Override
            public TableRow<T> call(TableView<T> tableView2) {
                final TableRow<T> row = new TableRow<T>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < table.getItems().size() && table.getSelectionModel().isSelected(index)) {
                            table.getSelectionModel().clearSelection();
                            selectedPublisher = null;
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });

        tableTV.setOnMouseClicked(e -> {
            updateRowSelected();
            e.consume();

        });

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setCellValueOfColumns() {
        courseCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("title"));
        departCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("department"));
        profCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Course, String> c) {
                Person temp = c.getValue().getProfessor();
                return new SimpleStringProperty(temp != null ? temp.getFirstName().concat(" ").concat(temp.getLastName()) : "**NONE**");
            }
        });
        resourceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Course, String> c) {
                StringBuilder temp = new StringBuilder();
                ArrayList<Resource> res = c.getValue().getResource();
                int length = c.getValue().getResource().size();
                for (int i = 0; i < length; i++) {
                    String tTitle = res.get(i).getTitle();
                    temp.append(tTitle, 0, Math.min(tTitle.length(), (78 / (length % 10))));
                    temp.append(" , ");
                }
                return new SimpleStringProperty(controller.resourcesFormat(res, 5));
            }
        });
        timeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Course, String> c) {
                Course cTemp = c.getValue();
                StringBuilder temp = new StringBuilder();
                temp.append(cTemp.getSEMESTER());
                temp.append("-");
                temp.append(cTemp.getYEAR());
                return new SimpleStringProperty(temp.toString());
            }
        });
        publisherCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Resource, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Resource, String> c) {
                Publisher p = c.getValue().getPublisher();

                return new SimpleStringProperty(p != null ? p.getName() : "**NONE**");
            }
        });
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("title"));
        idcCol.setCellValueFactory(new
                PropertyValueFactory<Resource, String>("ID"));
        editionCol.setCellValueFactory(new
                PropertyValueFactory<Resource, String>("Edition"));
        authorCol.setCellValueFactory(new
                PropertyValueFactory<Resource, String>("author"));
    }

    public void exit() {
        DBManager.closeConnection();
        System.exit(0);
    }

    public void delete() {

        if (selectedCourse == null) {
            showError("Error", "Nothing is selected", "Choose a course to delete");
        } else {
            DBManager.delete_relation_course(selectedCourse);
            courseList.remove(selectedCourse);
            selectedCourse = null;
            updateCourseTable();

        }
    }

    public void update() {

        if (selectedCourse == null) {
            showError("Error", "Nothing is selected", "Choose a course to Update");
        }

        else if (selectedCourse != null && checkFirstRequiredBoxes()) {

//            if (courseInfoCRN.getText() != null)
//                if (!courseInfoCRN.getText().isEmpty() && !controller.isInteger(courseInfoCRN.getText())) {
//                showError("Error", "CRN must be a number", "Re-type CRN");
//                return;
//            }

            Course tempCourse = new Course(0, courseInfoTitle.getText(), courseInfoDepart.getText(),
                    courseInfoDescrip.getText());
            tempCourse.setID(DBManager.insertCourseQuery(tempCourse));
            tempCourse.setCommonID(selectedCourse.getCommonID());

            ArrayList<Resource> tempResource = new ArrayList<>(resourceTable.getItems());
            tempCourse.setResource(tempResource);

            Person updatedPerson = new Person(profInfoLName.getText(), profInfoFName.getText(),
                    profInfoType.getSelectionModel().getSelectedItem().toString());
            updatedPerson.setID(DBManager.insertPersonQuery(updatedPerson));
            tempCourse.setProfessor(updatedPerson);

            tempCourse.setYEAR(Integer.parseInt(yearComBoxEdit.getSelectionModel().getSelectedItem().toString()));
            tempCourse.setSEMESTER(semesterComBoxEdit.getSelectionModel().getSelectedItem().toString());


            DBManager.updateCoursePersonSemester(tempCourse);
            DBManager.updateRelationCourseResources(tempCourse);


            DBManager.updateCRNAndNoteForClass(courseInfoCRN.getText(), courseInfoNotes.getText(), selectedCourse);
            controller.copyCourse(selectedCourse, tempCourse);
            controller.addNoteAndCRN(selectedCourse, courseInfoNotes.getText(), courseInfoCRN.getText());



            if (!isClassInTheSameYear(selectedCourse)) {
                courseList.remove(selectedCourse);
            }

            updateCourseTable();

        }
    }


    /**
     * Handles the filter check boxes action
     */
    public void filter() {
        profCol.setVisible(profCB.isSelected());
        courseCol.setVisible(courseCB.isSelected());
        departCol.setVisible(departCB.isSelected());
        resourceCol.setVisible(resCB.isSelected());

    }

    private void selectPublisher(Button publisherBtn) {
        Dialog dlg = new Dialog();
        pubList = controller.convertArrayPubPub(Objects.requireNonNull(DBManager.getPublisherFromTable()));

        dlg.setTitle("Publisher");
        dlg.setHeaderText("Select Publisher");
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        VBox mainPane = new VBox();
        ButtonType assign = new ButtonType("Create & Assign", ButtonBar.ButtonData.OK_DONE);

        Label listOfPublisher = new Label("List of Current Publisher: ");
        Label name = new Label(controller.stringAdjustment("Description: ", "Name: ") + " ");
        Label contact = new Label(controller.stringAdjustment("Description: ", "Contacts: ") + " ");
        Label description = new Label("Description: ");

        ComboBox<Publisher> publishersCB = new ComboBox<Publisher>();
        LimitedTextField nameTF = new LimitedTextField(), contactsTF = new LimitedTextField(),
                descriptionTF = new LimitedTextField();
        nameTF.setPromptText("Name of publisher");
        contactsTF.setPromptText("Contact information");
        descriptionTF.setPromptText("Description for publisher");


        nameTF.setMaxLength(120);
        contactsTF.setMaxLength(250);
        descriptionTF.setMaxLength(250);

        Button deleteBtn = new Button("Delete");
        ImageView deletImgg = new ImageView(deleteIconImg);
        addGraphicToButtons(deletImgg, deleteBtn);

        setChildVisibility(false, deleteBtn);
        publishersCB.getItems().addAll(pubList);
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(400);
        publishersCB.setOnAction(e -> {
            setChildVisibility(false, deleteBtn);
            if (publishersCB.getSelectionModel().getSelectedItem() != null) {
                setChildVisibility(true, deleteBtn);
                Publisher tempPub = publishersCB.getSelectionModel().getSelectedItem();
                nameTF.setText(tempPub.getName());
                contactsTF.setText(tempPub.getContacts());
                descriptionTF.setText(tempPub.getDescription());
            }

        });
        if (selectedPublisher != null) {
            nameTF.setText(selectedPublisher.getName());
            contactsTF.setText(selectedPublisher.getContacts());
            descriptionTF.setText(selectedPublisher.getDescription());
        }
        if (resourceTable.getSelectionModel().getSelectedItems().size() > 0) {

            Publisher tempPub = resourceTable.getSelectionModel().getSelectedItems().get(0).getPublisher();
            if (tempPub != null) {
                nameTF.setText(tempPub.getName());
                contactsTF.setText(tempPub.getContacts());
                descriptionTF.setText(tempPub.getDescription());
            }
        }
        VBox dataInfoPane = new VBox(20);
        dataInfoPane.getChildren().addAll(
                new HBox(name, nameTF),
                new HBox(contact, contactsTF),
                new HBox(description, descriptionTF)
        );
        for (Node hboxs : dataInfoPane.getChildren()) {
            ((HBox) hboxs).setAlignment(Pos.CENTER);
            ((HBox) hboxs).setSpacing(20);
        }
        HBox deleteHB = new HBox(deleteBtn);
        deleteHB.setAlignment(Pos.CENTER);
        dataInfoPane.setAlignment(Pos.CENTER);
        TitledPane dataInfoPaneWrapper = new TitledPane("Publisher Information", dataInfoPane);

        mainPane.getChildren().addAll(
                new HBox(listOfPublisher, publishersCB),
                dataInfoPaneWrapper, deleteBtn
        );


        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(20);
        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);

        dlg.getDialogPane().setMinHeight(500);
        dlg.show();

        dataInfoPaneWrapper.setExpanded(false);
        dlg.setOnCloseRequest(e -> {
            publisherBtn.setText(selectedPublisher != null ? selectedPublisher.getName() : "Click me to add a new Publisher");
        });

        deleteBtn.setOnAction(e -> {
            if (publishersCB.getSelectionModel().getSelectedItem() == null) {
                showError("Error", "Missing publisher", "Make sure you choose the publisher in the box");
            } else {
                deletePublisher(publishersCB.getSelectionModel().getSelectedItem(), nameTF, contactsTF, descriptionTF, publishersCB);
                publishersCB.getItems().clear();
                publishersCB.getItems().addAll(controller.convertArrayPubPub(DBManager.getPublisherFromTable()));

            }
            //Then refresh the combo boxes
        });

        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                if (nameTF.getText().trim().isEmpty()) {
                    showError("Input Error", "Make sure you filled out the required fields",
                            "Make sure you entered publisher's name correctly");
                    return null;
                }
                selectedPublisher = new Publisher(nameTF.getText(), contactsTF.getText(), descriptionTF.getText());


                //TODO: Khanh, put this thing in if statement
                if (!DBManager.availablePublisher(selectedPublisher)) {
                    pubList.add(selectedPublisher);
                }

                return null;
            }
            return null;
        });
    }

    private void deletePublisher(Publisher publisher, LimitedTextField nameTF, LimitedTextField contacTF,
                                 LimitedTextField descripTF, ComboBox<Publisher> publisherComboBox) {
        if (publisher.getID() == 0) {
            showError("Error", "Missing Publisher", "Please choose publisher in the box");
        } else {
            DBManager.deletePublisherInDB(publisher);

            resourceTable.getItems().clear();
            for (Resource r : resourceTable.getItems()) {
                DBManager.setPublisherForResource2(r);
            }

            for (Person p : profList) {
                for (Resource res1 : p.getResources()) {
                    DBManager.setPublisherForResource2(res1);
                }
            }
            publisherComboBox.getItems().remove(publisher);

            nameTF.clear();
            contacTF.clear();
            descripTF.clear();
            pubList.remove(publisher);
            courseList = DBManager.returnEverything2(DBManager.getSemesterIDByName(selectedCourse.getSEMESTER(),
                    Integer.toString(selectedCourse.getYEAR())));
            updateCourseTable();
            resourceTable.getItems().addAll(tableTV.getSelectionModel().getSelectedItem().getResource());
        }

    }

    private VBox resourceEditPane() {

        Label edition = new Label("Edition:*  ");
        VBox resourceEditPane = new VBox();
        Label title = new Label(controller.stringAdjustment("Edition:* ", "Title:*  ") + "   ");
        Label author = new Label((controller.stringAdjustment("Edition:* ", "Author:* ")));
        Label ISBN10 = new Label(controller.stringAdjustment("Edition:* ", "ISBN10: "));
        Label ISBN13 = new Label(controller.stringAdjustment("Edition:* ", "ISBN13: "));
        Label totalAmount = new Label((controller.stringAdjustment("Current Amount:* ", "Total Amount:* ")
                + "  "));
        Label currentAmount = new Label(("Current Amount:* "));
        Label description = new Label(controller.stringAdjustment("Current Amount:* ", "Description: ")
                + "     ");
        Label type = new Label("Type:*     ");
        Label publisher = new Label("Publisher:* ");
        LimitedTextField titleTF = new LimitedTextField(), authorTF = new LimitedTextField(),
                totalAmTF = new LimitedTextField(), isbn10TF = new LimitedTextField(),
                isbn13TF = new LimitedTextField(), currentAmTF = new LimitedTextField(),
                descriptionTF = new LimitedTextField();

        titleTF.setPromptText("e.g., Starting out with C++");
        authorTF.setPromptText("e.g., Author");
        totalAmTF.setPromptText("e.g., 100");
        isbn10TF.setPromptText("ISBN10 of resource");
        isbn13TF.setPromptText("ISBN13 of resource");
        currentAmTF.setPromptText("Ex. 90");
        descriptionTF.setPromptText("Description for resource");

//        Label counter = new Label();
//        counter.textProperty().bind(titleTF.textProperty().length().asString("  Char Counter: %d"));
//        titleTF.setCounter(counter);
        titleTF.setMaxLength(250);

        authorTF.setMaxLength(250);
        totalAmTF.setMaxLength(6);
        currentAmTF.setMaxLength(6);
        isbn10TF.setMaxLength(10);
        isbn13TF.setMaxLength(13);
        descriptionTF.setMaxLength(250);

        Button publisherBtn = new Button("Click here to add a new Publisher");
        ComboBox<String> typeCB = new ComboBox<String>();
        ComboBox<String> editionCB = new ComboBox<String>();

        typeCB.getItems().addAll(controller.getAllTypes());
        editionCB.getItems().addAll(controller.getAllEdition());
        Button addNAssignNewResource = new Button("Add and Assign");
        addGraphicToButtons(new ImageView(addIconImg), addNAssignNewResource);
        Button delete = new Button();
        addGraphicToButtons(new ImageView(deleteIconImg), delete);
        Button update = new Button();
        addGraphicToButtons(new ImageView(updateIconImg), update);
        Button autoFillBtn = new Button("Auto Fill ");
        Button searchBtn = new Button("Search");

        autoFillBtn.setOnAction(e -> {
            selectResourceTemplates(titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF,
                    publisherBtn, typeCB, editionCB, addNAssignNewResource, update, delete);

        });

        addNAssignNewResource.setOnAction(e -> {
            addAndAssignNewResource(titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF,
                    typeCB, editionCB);
        });

        delete.setOnAction(e -> {
            deleteResource(titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF,
                    publisherBtn, typeCB, editionCB, addNAssignNewResource, delete, update);
        });

        update.setOnAction(e -> {
            updateResource(titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF, typeCB,
                    editionCB);
        });

        HBox buttons = new HBox(addNAssignNewResource, update, delete);

        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);


        publisherBtn.setOnAction(e -> {
            selectPublisher(publisherBtn);
        });

        resourceTable.setOnMouseClicked(e -> {
            onResourceTableSelect(resourceTable.getSelectionModel().getSelectedItems().get(0), titleTF, authorTF,
                    isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, editionCB,
                    addNAssignNewResource, update, delete);

        });
        searchBtn.setOnMouseClicked(e -> {
            openResourceSearchWindow(titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF,
                    publisherBtn, typeCB, editionCB, addNAssignNewResource, update, delete);
        });
        try {
            if (!resourceTable.getSelectionModel().isEmpty()) {
                Resource tempRes = resourceTable.getSelectionModel().getSelectedItems().get(0);
                onResourceTableSelect(tempRes, titleTF, authorTF, isbn10TF,
                        isbn13TF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, editionCB,
                        addNAssignNewResource, update, delete);
            }

        } catch (Exception ex) {
            if (debugging) {
                showError("Resource data temp", "RES ERROR CODE 588 ",
                        ex.getStackTrace().toString());
            }
        }
        autoFillBtn.setAlignment(Pos.CENTER_RIGHT);

        HBox hiddenSpacer = new HBox(new Separator(), new Separator(), new Separator(), new Separator());
        hiddenSpacer.setVisible(false);
        resourceEditPane.getChildren().addAll(
                new HBox(type, typeCB, hiddenSpacer, searchBtn, new Separator(), autoFillBtn),
                new HBox(title, titleTF),
                new HBox(author, authorTF),
                new HBox(ISBN10, isbn10TF),
                new HBox(ISBN13, isbn13TF),
                new HBox(edition, editionCB),
//                new HBox(id, idTF),
                new HBox(publisher, publisherBtn),
                new HBox(totalAmount, totalAmTF),
                new HBox(currentAmount, currentAmTF),
                new HBox(description, descriptionTF)

        );

        for (Node box : resourceEditPane.getChildren()) {
            ((HBox) box).setAlignment(Pos.CENTER_LEFT);
        }

        resourceEditPane.getChildren().add(buttons);
        resourceEditPane.setAlignment(Pos.CENTER);
        resourceEditPane.setSpacing(20);

        return resourceEditPane;
    }

    private void showHelp() {
        Dialog dlg = new Dialog();
        dlg.setTitle("Help and Tutorials");
        dlg.setHeaderText("Welcome to the App Tutorial :-)");
        ImageView icon = new ImageView("/Models/media/icon.png");
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);
        VBox mainPane = new VBox(20);
        HBox mediaNavBtnPane = new HBox(20);

        ImageView img = new ImageView();
        Label title = new Label("Hello");
        String pageFormat = "Page %d of %d";
        Label pageNumber = new Label("");

        ImageView leftArr = new ImageView("/Models/media/left.png");
        ImageView rightArr = new ImageView("/Models/media/right.png");

        Button previousMediaBtn = new Button("<<");
        Button nextMediaBtn = new Button(">>");
        addGraphicToButtons(leftArr, previousMediaBtn);
        addGraphicToButtons(rightArr, nextMediaBtn);

        img.setFitHeight(500);
        img.setFitWidth(900);

        title.setMinHeight(Region.USE_PREF_SIZE);
        pageNumber.setMinHeight(Region.USE_PREF_SIZE);

        mediaNavBtnPane.getChildren().addAll(previousMediaBtn, nextMediaBtn);
        mediaNavBtnPane.setSpacing((500 / mediaNavBtnPane.getChildren().size()));
        mediaNavBtnPane.setAlignment(Pos.CENTER);

        previousMediaBtn.setAlignment(Pos.CENTER_LEFT);
        nextMediaBtn.setAlignment(Pos.CENTER_RIGHT);

        mainPane.getChildren().addAll(pageNumber, img, title, mediaNavBtnPane);
        mainPane.setAlignment(Pos.CENTER);


        ArrayList<Image> images = controller.tutorialImages();
        ArrayList<String> labelText = controller.tutorialText();

        if (!images.isEmpty()) {
            img.setImage(images.get(0));
            title.setText(labelText.get(0));
            pageNumber.setText(String.format(pageFormat, 1, labelText.size()));

        }
        previousMediaBtn.setOnMouseClicked(e -> {
            int currentIndex = images.indexOf(img.getImage());
            if (currentIndex != 0) {
                img.setImage(images.get(--currentIndex));
                title.setText(labelText.get(currentIndex));
                pageNumber.setText(String.format(pageFormat, currentIndex + 1, labelText.size()));


            } else {
                img.setImage(images.get(images.size() - 1));
                title.setText(labelText.get(labelText.size() - 1));
                pageNumber.setText(String.format(pageFormat, labelText.size(), labelText.size()));


            }
        });
        nextMediaBtn.setOnMouseClicked(e -> {
            int currentIndex = images.indexOf(img.getImage());
            if (currentIndex != images.size() - 1) {

                title.setText(labelText.get(++currentIndex));
                img.setImage(images.get(currentIndex));
                pageNumber.setText(String.format(pageFormat, currentIndex + 1, labelText.size()));


            } else {
                img.setImage(images.get(0));
                title.setText(labelText.get(0));
                pageNumber.setText(String.format(pageFormat, 1, labelText.size()));


            }
        });
        ButtonType closeBtnTy = ButtonType.CLOSE;
        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().add(closeBtnTy);
        dlg.setHeight(1000);
        dlg.setResizable(true);
        dlg.setWidth(1200);
        dlg.show();
        Button closeBtn = (Button) dlg.getDialogPane().lookupButton(closeBtnTy);
        closeBtn.setVisible(false);
        dlg.setResultConverter(dialogButton -> null);

    }

    private void openResourceSearchWindow(TextField titleTF, TextField authorTF, TextField
            isbn10TF, TextField isbn13TF, TextField totalAmTF, TextField currentAmTF,
                                          TextField descriptionTF, Button publisherBtn, ComboBox<String> typeCB,
                                          ComboBox<String> editionCB, Button addNAssignNewResource, Button update,
                                          Button delete) {
        try {
            Dialog dlg = new Dialog();
            ImageView icon = new ImageView("/Models/media/icon.png");
            icon.setFitHeight(75);
            icon.setFitWidth(75);

            dlg.setHeaderText("Search full-text books");

            dlg.setGraphic(icon);
            Parent root = FXMLLoader.load(getClass().getResource("/Views/booksSearchView.fxml"));
            dlg.getDialogPane().setMinWidth(650);
            dlg.getDialogPane().setContent(root);
            ButtonType add = new ButtonType("Add");
            dlg.getDialogPane().getButtonTypes().addAll(add);
            Button finishBtn = (Button) dlg.getDialogPane().lookupButton(add);
            finishBtn.setDefaultButton(false);
            dlg.setResizable(true);
            dlg.setResultConverter(dialogButton -> {
                if (dialogButton == add) {
                    Resource searchedResource = null;
                    Object[] temp = dlg.getDialogPane().getChildren().toArray();
                    for (Object childI : temp) {
                        if (childI instanceof GridPane) {
                            for (Object childJ : ((GridPane) childI).getChildren()) {
                                if (childJ instanceof ListView) {
                                    Book tempBook = (Book) ((ListView) childJ).getSelectionModel().getSelectedItem();
                                    if (tempBook != null) {
                                        searchedResource = BookAPI.getResourceObject(tempBook);
                                    }
                                }
                            }
                        }
                    }
                    onResourceTableSelect(searchedResource, titleTF, authorTF, isbn10TF, isbn13TF, totalAmTF,
                            currentAmTF, descriptionTF, publisherBtn, typeCB, editionCB, addNAssignNewResource, update, delete);
                    return null;
                }
                return null;
            });
            dlg.initModality(Modality.APPLICATION_MODAL);
            try {
                dlg.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();

                showError("Could set state.", "State invaildco", ex.getCause().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError(
                    "Could not open the view",
                    "Unable to open the Search view",
                    "Please give the code and message to your Interns:" + ex.getMessage() + "--" + ex.getCause().getMessage());
        }
    }

    private static List<Object> flatten(Object object) {
        List<Object> l = new ArrayList<Object>();
        if (object.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(object); i++) {
                l.addAll(flatten(Array.get(object, i)));
            }
        } else if (object instanceof List) {
            for (Object element : (List<?>) object) {
                l.addAll(flatten(element));
            }
        } else {
            l.add(object);
        }
        return l;
    }

    private void updateResource(TextField titleTF, TextField authorTF, TextField isbn10TF,
                                TextField isbn13TF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF,
                                ComboBox<String> typeCB, ComboBox<String> editionCB) {
        boolean isbnFormat = !controller.isISBN(isbn10TF.getText()) || !controller.isISBN13(isbn13TF.getText());
//make sure to have method that find the resourceID & publisherID=0, to change it from 0 to the right one
        if (resourceTable.getSelectionModel().getSelectedItem() == null) {
            showError("Error", "Missing selected resource", "Make sure you choose a resource to update");
        } else if (titleTF.getText().trim().isEmpty() || authorTF.getText().trim().isEmpty() || totalAmTF.getText().trim().isEmpty() ||
                currentAmTF.getText().trim().isEmpty()) {
            showError("Could not insert the Resource", "Unable to insert the Resource",
                    "Please make sure you filled out all the required fields");

        } else if (typeCB.getSelectionModel().getSelectedItem().equals("Book") && (isbn10TF.getText().trim().isEmpty() || isbn13TF.getText().trim().isEmpty())) {
            showError("ISBN error", "Missing ISBN", "Please add ISBN");
        } else if (selectedPublisher == null) {
            showError("Update Error", "Missing publisher", "Please add publisher for resource");

        } else if (isbnFormat && typeCB.getSelectionModel().getSelectedItem().equals("Book")) {
            showError("ISBN error", "Wrong ISBN format", "ISBN must have 10 digits, ISBN13 must have 13 digits");
        } else {

            ArrayList<Resource> tempResArr = new ArrayList<Resource>(resourceTable.getItems());
            selectedResource = resourceTable.getSelectionModel().getSelectedItem();
            tempResArr.remove(selectedResource);
            String new_title = controller.capitalizeString(titleTF.getText());
            String isbn = isbn10TF.getText();
            String isbn13 = isbn13TF.getText();
            String new_author = controller.capitalizeString(authorTF.getText());
            int new_total = Integer.parseInt(totalAmTF.getText());
            int new_current = Integer.parseInt(currentAmTF.getText());
            String new_descrip = descriptionTF.getText();
            String new_type = typeCB.getSelectionModel().getSelectedItem();
            String new_edition = editionCB.getSelectionModel().getSelectedItem();
            Publisher new_publisher = selectedPublisher;


            if (selectedResource.getTYPE().equals(new_type) && selectedResource.getTitle().equals(new_title)
                    && selectedResource.getAuthor().equals(new_author) && selectedResource.getCurrentAmount() == (new_current)
                    && selectedResource.getISBN().equals(isbn) && selectedResource.getISBN13().equals(isbn13)
                    && selectedResource.getTotalAmount() == (new_total) && selectedResource.getDescription().equals(new_descrip)
                    && selectedResource.getEdition().equals(new_edition)) {
                DBManager.updatePublisherForResource(selectedResource, selectedPublisher);
                selectedResource.setPublisher(selectedPublisher);
                tempResArr.add(selectedResource);

            } else {
                Models.backend.Resource tempRes = new Models.backend.Resource(selectedResource.getID(), new_type, new_title, new_author, isbn, new_total, new_current, new_descrip);
                tempRes.setIsbn13(isbn13);
                tempRes.setEdition(new_edition);
                selectedResource = tempRes.initResourceGUI();
                DBManager.updateResource(tempRes);
                DBManager.updatePublisherForResource(selectedResource, selectedPublisher);
                selectedResource.setPublisher(selectedPublisher);
                tempResArr.add(selectedResource);
            }
            resourceTable.getItems().clear();
            resourceTable.getItems().addAll(tempResArr);

        }
        refreshTable();
    }

    private void deleteResource(TextField titleTF, TextField authorTF, TextField isbn10TF,
                                TextField isbn13TF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF,
                                Button publisherBtn, ComboBox<String> typeCB, ComboBox<String> editionCB, Button
                                        addNAssignNewResource, Button delete, Button update) {

        ArrayList<Resource> temp = new ArrayList<>(resourceTable.getSelectionModel().getSelectedItems());
        for (Resource r : temp) {
            resourceTable.getItems().remove(r);

        }
        onResourceTableSelect(resourceTable.getSelectionModel().getSelectedItems().get(0), titleTF, authorTF,
                isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, editionCB,
                addNAssignNewResource, update, delete);
    }

    private void addAndAssignNewResource(TextField titleTF, TextField authorTF,
                                         TextField isbn10, TextField isbn13, TextField totalAmTF, TextField currentAmTF,
                                         TextField descriptionTF, ComboBox<String> typeCB, ComboBox<String> editionCB) {

        Boolean requiredBoxes = titleTF.getText().trim().isEmpty() || authorTF.getText().trim().isEmpty() ||
                totalAmTF.getText().trim().isEmpty() || currentAmTF.getText().trim().isEmpty() ||
                typeCB.getSelectionModel().getSelectedItem() == null ||
                editionCB.getSelectionModel().getSelectedItem() == null;
        Boolean isbnFormat = !controller.isISBN(isbn10.getText()) || !controller.isISBN13(isbn13.getText());


        if (requiredBoxes) {
            showError("Input Error", "Make sure you filled out all the required fields Resource Error",
                    "Make sure you entered Title, Author, Edition, Total and Current amount");

        } else if (selectedPublisher == null || selectedPublisher.getName().isEmpty()) {
            showError("Input Error", "No publisher has been assigned!",
                    "Please make sure you assigned a publisher for the resource");
        } else if (!controller.isInteger(totalAmTF.getText()) ||
                !controller.isInteger(currentAmTF.getText())) {
            showError("Input Error",
                    "Total and Current format must be an integer",
                    "Correct format examples --> 100, 90");
        } else if (typeCB.getSelectionModel().getSelectedItem().equals("") && (isbn10.getText().trim().isEmpty() || isbn13.getText().trim().isEmpty())) {
            showError("ISBN error", "Missing ISBN", "Please add ISBN");
        } else if (isbnFormat && typeCB.getSelectionModel().getSelectedItem().equals("Book")) {
            showError("ISBN error", "Wrong ISBN format", "ISBN must have 10 digits, ISBN13 must have 13 digits");
        } else {


            Resource temp = new Resource(typeCB.getSelectionModel().getSelectedItem(),
                    controller.capitalizeString(titleTF.getText()),
                    controller.capitalizeString(authorTF.getText()),
                    descriptionTF.getText(),
                    true,
                    Integer.parseInt(totalAmTF.getText()),
                    ((int) Math.random()),
                    Integer.parseInt(currentAmTF.getText()),
                    selectedPublisher
            );
            temp.setISBN13(isbn13.getText());
            temp.setISBN(isbn10.getText());
            temp.setEdition(editionCB.getSelectionModel().getSelectedItem());
            DBManager.setIDforResource(temp);

            if (!resourceTable.getItems().contains(temp)) {

                if (!isPersonResourcesView) {
                    resList.add(temp);
                    resourceTable.getItems().add(temp);
                    DBManager.insertRelationResourcePublisher(temp);

                } else {

                    resourceTable.getItems().add(temp);
                    selectedPerson.getResources().add(temp);
                    DBManager.insertRelationResourcePublisher(temp);

                }
            } else {
                showError("Repetitive resource", "The resource is already exists",
                        "Please make sure you are not adding repetitive resource in one class.");
            }
        }

    }

    private void selectResourceTemplates(TextField titleTF, TextField authorTF, TextField isbn10TF,
                                         TextField isbn13TF, TextField totalAmTF, TextField currentAmTF,
                                         TextField descriptionTF, Button publisherBtn, ComboBox<String> typeCB,
                                         ComboBox<String> editionCB, Button addNAssignNewResource, Button update, Button delete) {
        VBox mainAddPane = new VBox(2);
        Dialog dlg = new Dialog();

        resList = DBManager.getResourceList();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Resource> resources = new ComboBox<Resource>();
        resources.getItems().addAll(resList);
        Label currentCBoxLbl = new Label("Resources : ");
        ButtonType  fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button deleteBtn = new Button("Delete");
        ImageView deletImgg = new ImageView(deleteIconImg);
        addGraphicToButtons(deletImgg, deleteBtn);

        deleteBtn.setOnAction(e -> {
            deleteResource(resources.getSelectionModel().getSelectedItem());
            resources.getItems().clear();
            resources.getItems().addAll(resList);

        });
        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, resources),
                deleteBtn
        );
        mainAddPane.setSpacing(20);
        mainAddPane.setAlignment(Pos.CENTER);
        dlg.setTitle("Assigning Resource");
        dlg.setHeaderText("Assigning Resource");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);

        dlg.getDialogPane().setMinHeight(500);
        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {
                onResourceTableSelect(resources.getSelectionModel().getSelectedItem(), titleTF, authorTF,
                        isbn10TF, isbn13TF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, editionCB,
                        addNAssignNewResource, update, delete);
            }
            return null;
        });

    }

    private void deleteResource(Resource res) {
        resList.remove(res);
        DBManager.deleteResourceInDB(res);
        resourceTable.getItems().remove(res);
        for (Course c : courseList) {
            c.getResource().remove(res);
        }
        for (Person p : profList) {
            p.getResources().remove(res);
        }
        resInfoList.getItems().remove(res);
        updateCourseTable();
    }

    private void onResourceTableSelect(Resource tempRes, TextField titleTF, TextField authorTF, TextField isbn10TF,
                                       TextField isbn13TF, TextField totalAmTF, TextField currentAmTF,
                                       TextField descriptionTF, Button publisherBtn, ComboBox<String> typeCB,
                                       ComboBox<String> editionCB,
                                       Button addNAssignNewResource, Button update, Button delete) {

        if (tempRes != null) {

            titleTF.setText(tempRes.getTitle());
            authorTF.setText(tempRes.getAuthor());
            if (tempRes.getISBN() != null) {
                isbn10TF.setText(tempRes.getISBN());
            } else {
                isbn10TF.setText("");
            }
            if (tempRes.getISBN13() != null) {
                isbn13TF.setText(tempRes.getISBN13());
            } else {
                isbn13TF.setText("");
            }

            if (!typeCB.getItems().contains(tempRes.getTYPE()))
                typeCB.getItems().addAll(tempRes.getTYPE());

            typeCB.getSelectionModel().select(tempRes.getTYPE());
            if (tempRes.getEdition() != null && tempRes.getEdition() != "")
                if (!editionCB.getItems().contains(tempRes.getEdition()))
                    editionCB.getItems().addAll(tempRes.getEdition());
            editionCB.getSelectionModel().select(tempRes.getEdition());


            descriptionTF.setText(tempRes.getDescription());
            publisherBtn.setText(tempRes.getPublisher() != null ? tempRes.getPublisher().toString() : "No publisher assigned.Click here.");


            if (tempRes.getPublisher() != null && !tempRes.getPublisher().getName().isEmpty()) {
                if (!DBManager.availablePublisher(tempRes.getPublisher())) {
                    pubList.add(tempRes.getPublisher());
                }
            }
            selectedPublisher = tempRes.getPublisher();
            totalAmTF.setText(String.valueOf(tempRes.getTotalAmount()));
            currentAmTF.setText(String.valueOf(tempRes.getCurrentAmount()));
            setChildVisibility(true, update, delete);
        } else {
            setChildVisibility(false, update, delete);
        }
    }

    /**
     * Assign a new Resources to Course.
     * It creates Resource objects and assign the resources as the Professor for the course object
     */
    public void openResourceView() {
        isPersonResourcesView = false;
        try {
            VBox mainPane = new VBox();
            Dialog dlg = new Dialog();
            TitledPane resourceTitlePane = new TitledPane();
            VBox resourceEditPane = resourceEditPane();
            ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
            icon.setFitHeight(75);
            icon.setFitWidth(75);


            ButtonType assign = new ButtonType("Assign the Selected Resources", ButtonBar.ButtonData.OK_DONE);
            if (selectedCourse != null) {
                resourceTable.getItems().clear();
                resourceTable.getItems().addAll(selectedCourse.getResource());
            }

            resourceTitlePane.setContent(resourceEditPane);
            resourceTitlePane.setText("Resource Details and Management");
            resourceTitlePane.setAlignment(Pos.CENTER);
            mainPane.getChildren().addAll(new HBox(resourceTitlePane, resourceTable));
            mainPane.setAlignment(Pos.CENTER);


            dlg.setTitle("Assigning Resource");
            dlg.setHeaderText("Assign Resources for Course");

            dlg.setGraphic(icon);
            dlg.getDialogPane().setMinWidth(400);


            dlg.getDialogPane().setContent(mainPane);
            dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);
            dlg.getDialogPane().setMinHeight(500);
            dlg.show();
            resourceTitlePane.setExpanded(false);
            dlg.setResultConverter(dialogButton -> {
                if (dialogButton == assign) {


                    resInfoList.getItems().clear();
                    for (Resource r : resourceTable.getItems())
                        resInfoList.getItems().add(r.getTitle());

                    return null;

                }
                return null;
            });
        } catch (Exception ex) {
            showError("Resource Views", "Unable to open the resource view  ", ex.getMessage());
        }


    }

    /**
     * Assign a new Professor to Course.
     * It creates a Person object and assign the person as the Professor for the course object
     */
    public void selectProfessor() {
        VBox mainAddPane = new VBox(2);
        profList.clear();
        profList = controller.convertBackendPersonToFrontendPerson(Objects.requireNonNull(DBManager.getPersonFromTable()));


        Dialog dlg = new Dialog();

        profInfoFNameTf.setPromptText("e.g., Alla");
        profInfoLNameTf.setPromptText("e.g., Webb");


        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());

        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Person> currentProfessors = new ComboBox<Person>();
        currentProfessors.getItems().addAll(profList);

        Label currentCBoxLbl = new Label("                      " + "Choose a Professor: ");
        Label profInfoFNameLbl = new Label("First Name:* ");
        Label profInfoLNameLbl = new Label("Last Name:* ");
        Label profInfoTypeLbl = new Label(controller.stringAdjustment("First Name:*  ", " Type:* ") +
                "    ");

        profInfoFNameTf.setMaxLength(25);
        profInfoLNameTf.setMaxLength(25);
        ComboBox profInfoTypeCB = new ComboBox<>();

        profInfoTypeCB.setItems(profInfoType.getItems());

        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button PersonResources = new Button("Views Person's Resources");
        Button addProfessor = new Button("Add");
        Button infoBtn = new Button("Info");

        Button deleteBtn = new Button("Delete");
        Button updateBtn = new Button("Update");


        setChildVisibility(false, PersonResources, infoBtn, deleteBtn, updateBtn);
        addProfessor.setOnMouseClicked(e -> {
            if (profInfoFName.getText() == null || profInfoFName.getText() == null || profInfoTypeCB.getSelectionModel().getSelectedItem() == null) {
                showError("Invalid input", "please fill all the fields", "You must enter all the information to create a new professor");
                e.consume();
            } else {
                addNewProfessor(profInfoFNameTf.getText(), profInfoLNameTf.getText(),
                        profInfoTypeCB.getSelectionModel().getSelectedItem().toString(), currentProfessors);
                currentProfessors.getItems().clear();
                currentProfessors.getItems().addAll(profList);
            }
        });
        infoBtn.setOnMouseClicked(e -> {
            resourcePersonDiffView(currentProfessors.getSelectionModel().getSelectedItem());
        });
        currentProfessors.setOnAction(e -> {

            if (currentProfessors.getSelectionModel().getSelectedItem() != null) {
                profInfoFNameTf.setText(currentProfessors.getSelectionModel().getSelectedItem().getFirstName());
                profInfoLNameTf.setText(currentProfessors.getSelectionModel().getSelectedItem().getLastName());
                profInfoTypeCB.setValue(currentProfessors.getSelectionModel().getSelectedItem().getType());

                setChildVisibility(true, PersonResources, infoBtn, deleteBtn, updateBtn);


            } else {
                setChildVisibility(false, PersonResources, infoBtn, deleteBtn, updateBtn);


            }
        });
        deleteBtn.setOnAction(e -> {
            selectedPerson = currentProfessors.getSelectionModel().getSelectedItem();
            deleteProfessor();
            currentProfessors.getItems().clear();
            currentProfessors.getItems().addAll(profList);

        });
        updateBtn.setOnMouseClicked(e -> updateProfessor(currentProfessors, profInfoFNameTf, profInfoLNameTf, profInfoTypeCB));
        PersonResources.setOnAction(e -> {
            ArrayList<Resource> tempRes = new ArrayList<>(resList);
            isPersonResourcesView = true;
            currentProfessors.getSelectionModel().getSelectedItem().setResources(tempRes);
            showPersonsResources(currentProfessors.getSelectionModel().getSelectedItem());
        });
        addGraphicToButtons(new ImageView(addIconImg), addProfessor);
        addGraphicToButtons(new ImageView(deleteIconImg), deleteBtn);
        String questionIconImg = "/Models/media/question.png";
        addGraphicToButtons(new ImageView(questionIconImg), infoBtn);

        VBox hiddenOptionSContent = new VBox(20);

        hiddenOptionSContent.getChildren().addAll(new HBox(profInfoFNameLbl, profInfoFNameTf),
                new HBox(profInfoLNameLbl, profInfoLNameTf),
                new HBox(profInfoTypeLbl, profInfoTypeCB),
                new HBox(addProfessor
//                        , updateBtn
                ));
        for (Node hboxs : hiddenOptionSContent.getChildren()) {
            ((HBox) hboxs).setAlignment(Pos.CENTER);
            ((HBox) hboxs).setSpacing(20);

        }
        TitledPane hiddenOptions = new TitledPane("Professor Information", hiddenOptionSContent);


        mainAddPane.getChildren().addAll(
                new HBox(20, currentCBoxLbl, currentProfessors),
                hiddenOptions,
                new HBox(20, infoBtn, PersonResources, deleteBtn)
        );
        for (Object tempElem : mainAddPane.getChildren()) {
            if (tempElem instanceof HBox) {
                ((HBox) tempElem).setAlignment(Pos.CENTER);
            }
        }
        mainAddPane.setSpacing(20);
        mainAddPane.setAlignment(Pos.CENTER);
        dlg.setTitle("Professor");
        dlg.setHeaderText("Assigning a Professor");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);
        dlg.getDialogPane().setMinHeight(500);

        dlg.show();
        hiddenOptions.setExpanded(false);
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {
                selectedPerson = currentProfessors.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    profInfoFName.setText(selectedPerson.getFirstName());
                    profInfoLName.setText(selectedPerson.getLastName());
                    profInfoType.setValue(selectedPerson.getType());
                }

            }
            return null;
        });

    }

    private void updateProfessor(ComboBox<Person> currentProfessors, LimitedTextField
            profInfoFName, LimitedTextField profInfoLName, ComboBox profInfoType) {
        Person professor = currentProfessors.getSelectionModel().getSelectedItem();
        String firstName = capitalizeFirstLetter(profInfoFName.getText());
        String lastName = capitalizeFirstLetter(profInfoLName.getText());
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        professor.setType(profInfoType.getSelectionModel().getSelectedItem().toString());
        DBManager.updatePersonGUI(professor);

        refreshTable();
    }

    private void setChildVisibility(Boolean state, Node... args) {
        for (Node arg : args) {
            arg.setVisible(state);
            arg.setManaged(state);
        }

    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    private void resourcePersonDiffView(Person selectedPerson) {
        Dialog dlg = new Dialog();
        HBox tablePane = new HBox();
        VBox mainPane = new VBox(20);

        resourceTable.getItems().clear();
        Models.backend.Person tempPerson = DBManager.setResourcesForPerson(selectedPerson.initPersonBackend());
        selectedPerson = Objects.requireNonNull(tempPerson).initPersonGUI();
        final Person finalSelectedPerson = selectedPerson;

        String title = selectedPerson.getFirstName().concat(" ").concat(selectedPerson.getLastName())
                .concat(", ")
                .concat(selectedPerson.getType());

        final String defaultHeader = "Here are all the resources needed for " + selectedPerson.getFirstName() + " " + selectedPerson.getLastName();

        ImageView icon = new ImageView(programeIconImg);

        icon.setFitHeight(75);
        icon.setFitWidth(75);

        ComboBox semester = new ComboBox();
        ComboBox years = new ComboBox();

        semester.getItems().addAll(semesterComBox.getItems());
        semester.getSelectionModel().select(semesterComBox.getSelectionModel().getSelectedItem());
        years.getItems().addAll(yearComBox.getItems());
        years.getSelectionModel().select(yearComBox.getSelectionModel().getSelectedItem());

        Button fillerResourcesBasedOnSemester = new Button("filter");

        TableView<Resource> profResources = new TableView<>();
        TableView<Resource> allResources = new TableView<>();
        TableView<Resource> diffResources = new TableView<>();
        TableColumn profResourcesTC = createResourceColoumnForDiffView(profResources);
        TableColumn allResourcesTC = createResourceColoumnForDiffView(allResources);
        TableColumn diffResourcesTC = createResourceColoumnForDiffView(diffResources);


        ArrayList<Resource> allRequiredResources = DBManager.getAllResourcesNeededForPerson(selectedPerson,
                semester.getSelectionModel().getSelectedItem().toString(),
                years.getSelectionModel().getSelectedItem().toString());
        profResources.getItems().addAll(selectedPerson.getResources());
        allResources.getItems().addAll(allRequiredResources);
        diffResources.getItems().addAll(allRequiredResources);
        diffResources.getItems().removeAll(selectedPerson.getResources());


        Label professorSResourcesLbl = new Label(selectedPerson.getLastName().concat("'s Resources"));
        Label resourcesLbl = new Label("Required Resources");
        Label diffResourcesLbl = new Label("Required Difference ");
        fillerResourcesBasedOnSemester.setOnMouseClicked(e -> {
            if (semester.getSelectionModel().getSelectedItem() != null && years.getSelectionModel().getSelectedItem() != null) {


                ArrayList<Resource> allRequiredResourcesRePulled = DBManager.getAllResourcesNeededForPerson(finalSelectedPerson,
                        semester.getSelectionModel().getSelectedItem().toString(),
                        years.getSelectionModel().getSelectedItem().toString());

                allResources.getItems().clear();
                diffResources.getItems().clear();


                allResources.getItems().addAll(allRequiredResourcesRePulled);
                diffResources.getItems().addAll(allRequiredResourcesRePulled);
                diffResources.getItems().removeAll(finalSelectedPerson.getResources());


                String semesterStr = semester.getSelectionModel().getSelectedItem().toString();
                semesterStr = semesterStr.replace('_', ' ').toLowerCase();
                dlg.setHeaderText(defaultHeader
                        + " for " + semesterStr
                        + " in " + years.getSelectionModel().getSelectedItem());

                diffResources.refresh();
                allResources.refresh();


            } else {
                System.out.println("Please select filter data");
            }


        });

        professorSResourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        resourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        diffResourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        tablePane.getChildren().

                addAll(
                        new VBox(5, professorSResourcesLbl, profResources),
                        new VBox(5, resourcesLbl, allResources),
                        new VBox(5, diffResourcesLbl, diffResources)
                );
        tablePane.setAlignment(Pos.CENTER);
        tablePane.setStyle("-fx-border-radius: 10px;");
        for (
                Node temp : tablePane.getChildren()) {
            if (temp.getClass().equals(VBox.class)) {
                VBox child = (VBox) temp;
                child.setAlignment(Pos.CENTER);
                child.setStyle("-fx-background-color: grey;-fx-border-color: black;");

            }

        }

        HBox semesterInfoComboBoxes = new HBox(20, semester, years, fillerResourcesBasedOnSemester);

        semesterInfoComboBoxes.setAlignment(Pos.CENTER);

        mainPane.getChildren().

                addAll(semesterInfoComboBoxes, tablePane);
        mainPane.setAlignment(Pos.CENTER);

        dlg.getDialogPane().

                setContent(mainPane);
        dlg.getDialogPane().

                getButtonTypes().

                addAll(ButtonType.CLOSE);
        dlg.setTitle(title);
        String semesterStr = semester.getSelectionModel().getSelectedItem().toString();
        semesterStr = semesterStr.replace('_', ' ').toLowerCase();
        dlg.setHeaderText(defaultHeader
                + " for " + semesterStr
                + " in " + years.getSelectionModel().getSelectedItem()
        );
        dlg.setGraphic(icon);
        dlg.setResizable(true);
        dlg.show();

    }

    private TableColumn createResourceColoumnForDiffView(TableView<Resource> diffTableView) {
        diffTableView.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                // Get the table header
                Pane header = (Pane) diffTableView.lookup("TableHeaderRow");
                if (header != null && header.isVisible()) {
                    header.setMaxHeight(0);
                    header.setMinHeight(0);
                    header.setPrefHeight(0);
                    header.setVisible(false);
                    header.setManaged(false);
                }
            }
        });
        TableColumn<Resource, String> tableColumn = new TableColumn<>();
        tableColumn.setMaxWidth(Double.MAX_VALUE);
        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Resource, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Resource, String> Res) {
                String title = Res.getValue().getTitle();
                return new SimpleStringProperty(title);
            }
        });
        diffTableView.getColumns().add(tableColumn);
        return tableColumn;
    }

    private void addNewProfessor(String firstName, String lastName, String type, ComboBox currentProfessors) {
        Person tempNewPerson = new Person(lastName, firstName, type
        );
        profList.add(tempNewPerson);
        DBManager.insertPersonQuery(tempNewPerson);
        currentProfessors.getSelectionModel().select(tempNewPerson);
    }

    private void showPersonsResources(Person selectedItem) {
        VBox mainPane = new VBox();
        Dialog dlg = new Dialog();
        TitledPane resourceTitlePane = new TitledPane();
        VBox resourceEditPane = resourceEditPane();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        selectedPerson = selectedItem;


        ButtonType assign = new ButtonType("Assign the Selected Resources", ButtonBar.ButtonData.OK_DONE);

        resourceTable.getItems().clear();
        Models.backend.Person tempPerson = DBManager.setResourcesForPerson(selectedItem.initPersonBackend());
        selectedItem = Objects.requireNonNull(tempPerson).initPersonGUI();
        selectedPerson = selectedItem;
        if (selectedItem.getResources() != null) {
            resourceTable.getItems().addAll(selectedItem.getResources());
        }


        resourceTitlePane.setContent(resourceEditPane);
        resourceTitlePane.setText("Resource Details and Management");
        resourceTitlePane.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(new HBox(resourceTitlePane, resourceTable));
        mainPane.setAlignment(Pos.CENTER);


        dlg.setTitle("Assign Resource to Professor");
        dlg.setHeaderText("Assign Resources for " + selectedItem.getFirstName() + " " + selectedPerson.getLastName());

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(400);


        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        resourceTitlePane.setExpanded(false);

        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {

                DBManager.insertPersonResources(selectedPerson);

                return null;
            }
            isPersonResourcesView = false;
            return null;
        });

    }

    private void deleteProfessor() {

        DBManager.deletePersonEveyrwhere(selectedPerson);
        profList.remove(selectedPerson);
        selectedPerson = null;
        refreshTable();

    }

    public void selectCourse() {
        ArrayList<Course> tempCourses = new ArrayList<>();
        templateList = controller.convertArrayCCBasic(Objects.requireNonNull(DBManager.getCourseFromTable()));
        VBox mainAddPane = new VBox(2);
        VBox dataInfoPane = new VBox(2);

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Course> courseTemplates = new ComboBox<Course>();


        Label currentCBoxLbl = new Label("Course Templates:   ");
        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button deleteBtn = new Button("Delete");
        Button addBtn = new Button("Add");
        Label tile = new Label(controller.stringAdjustment("Description:*", "Title:* ") + "      ");
        Label description = new Label("Description:* ");
        Label department = new Label("Department:* ");
        LimitedTextField tileTf = new LimitedTextField(), descriptionTf = new LimitedTextField(),
                departmentTf = new LimitedTextField();

        tileTf.setPromptText("e.g., CMSC 140");
        descriptionTf.setPromptText("e.g., Intro To Programming");
        departmentTf.setPromptText("e.g., Computer Science");

        tileTf.setMaxLength(8);
        descriptionTf.setMaxLength(250);
        departmentTf.setMaxLength(120);

        setCourseTemplatesCellValue(courseTemplates);
        courseTemplates.getItems().addAll(templateList);
        descriptionTf.setMinWidth(150);

        addBtn.setOnMouseClicked(e -> {
            Course tempNewCourseTemplate = new Course(-1, tileTf.getText(), departmentTf.getText(), descriptionTf.getText());
            addNewCourseTemplate(tempNewCourseTemplate);
            courseTemplates.getItems().clear();
            courseTemplates.getItems().addAll(templateList);
            courseTemplates.getSelectionModel().select(tempNewCourseTemplate);

        });
        deleteBtn.setOnAction(e -> {
                    deleteCourseTemplate(courseTemplates.getSelectionModel().getSelectedItem());
                    courseTemplates.getItems().clear();
                    courseTemplates.getItems().addAll(templateList);
                }
        );
        courseTemplates.setOnAction(e -> {
            Course tempSelectedCourse = courseTemplates.getSelectionModel().getSelectedItem();
            setChildVisibility(false, deleteBtn);

            if (tempSelectedCourse != null) {
                tileTf.setText(tempSelectedCourse.getTitle());
                departmentTf.setText(tempSelectedCourse.getDepartment());
                descriptionTf.setText(tempSelectedCourse.getDescription());
                setChildVisibility(true, deleteBtn);
            }
        });

        dataInfoPane.getChildren().addAll(
                new HBox(25, tile, tileTf),
                new HBox(25, description, descriptionTf),
                new HBox(25, department, departmentTf),
                new HBox(25, addBtn)
        );
        for (Node node : dataInfoPane.getChildren()) {
            HBox child = (HBox) node;
            child.setAlignment(Pos.CENTER);
        }
        setChildVisibility(false, deleteBtn);


        dataInfoPane.setMinWidth(300);
        dataInfoPane.setSpacing(20);
        dataInfoPane.setAlignment(Pos.CENTER);

        addGraphicToButtons(new ImageView(addIconImg), addBtn);
        addGraphicToButtons(new ImageView(deleteIconImg), deleteBtn);

        HBox buttons = new HBox(deleteBtn);
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.CENTER);
        TitledPane dataInfoPaneWrapper = new TitledPane("Course Information", dataInfoPane);
        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, courseTemplates),
                dataInfoPaneWrapper,
                buttons
        );

        mainAddPane.setSpacing(20);
        mainAddPane.setAlignment(Pos.CENTER);
        dlg.setTitle("Assigning a Course");
        dlg.setHeaderText("Assigning a Course");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);

        dlg.getDialogPane().setMinHeight(500);

        dlg.show();
        dataInfoPaneWrapper.setExpanded(false);
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {

                selectedCourseTemplate = courseTemplates.getSelectionModel().getSelectedItem();
                if (selectedCourseTemplate != null) {
                    courseInfoDescrip.setText(selectedCourseTemplate.getDescription());
                    courseInfoTitle.setText(selectedCourseTemplate.getTitle());
                    courseInfoDepart.setText(selectedCourseTemplate.getDepartment());
                }
            }
            return null;
        });


    }

    private void addNewCourseTemplate(Course tempNewCourseTemplate) {

        String tempCourseTitle = tempNewCourseTemplate.getTitle().replaceAll("\\s", "");
        String[] cSplit = tempCourseTitle.split("(?<=\\D)(?=\\d)");

        if (cSplit.length != 2) {

            showError("Inout Error",
                    "Unable to insert because the course you title entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181 ");
        } else if (!controller.isInteger(cSplit[1])) {
            showError("Inout Error",
                    "Unable to insert because the course you title entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181 ");
        } else {
            tempNewCourseTemplate.setID(DBManager.insertCourseQuery(tempNewCourseTemplate));
            templateList.add(tempNewCourseTemplate);
        }
    }

    private void deleteCourseTemplate(Course selectedCourseTemplate) {

        DBManager.delete_course(selectedCourseTemplate);
        templateList.remove(selectedCourseTemplate);
        selectedCourseTemplate = null;
        refreshTable();


        updateCourseTable();

    }

    private void updateCourse(Course course, LimitedTextField courseTitle, LimitedTextField courseDepart,
                              LimitedTextField courseDescrip) {

        String tempCourseTitle = courseInfoTitle.getText().replaceAll("\\s", "");
        String[] cSplit = tempCourseTitle.split("(?<=\\D)(?=\\d)");
        if (cSplit.length != 2) {

            showError("Inout Error",
                    "Unable to update because the course title you entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181 (Hint: Make sure to have a space in" +
                            " between the course character and number) ");
        } else if (!controller.isInteger(cSplit[1])) {
            showError("Inout Error",
                    "Unable to update because the course you title entered " +
                            "is not valid.",
                    "Correct format examples --> CMSC 100, MATH 181. (Hint: Make sure to have a number after space) ");
        } else {

            //Everything should be happening here, and then call the function in DB
            course.setTitle(courseTitle.getText());
            course.setDepartment(courseDepart.getText());
            course.setDescription(courseDescrip.getText());

            DBManager.updateCourseGUI(course);
            refreshTable();
        }

    }

    private void setCourseTemplatesCellValue(ComboBox<Course> courseTemplates) {
        courseTemplates.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course item) {
                if (item == null) {
                    return null;
                } else {
                    return item.getTitle() + " - " + item.getDescription();
                }
            }

            @Override
            public Course fromString(String string) {
                return null;
            }
        });
        courseTemplates.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> p) {
                return new ListCell<Course>() {

                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getTitle() + " - " + item.getDescription());
                        }
                    }
                };
            }
        });
    }

    public void showExportView() {
        Dialog dlg = new Dialog();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(255);
        icon.setFitWidth(255);
        Boolean[] checkBoxes = new Boolean[4];

        CheckBox pubInfo = new CheckBox("All Publisher Info");
        CheckBox resNPubInfo = new CheckBox("All the resources data, associate with publisher");
        CheckBox personWResInfo = new CheckBox("All the Person with resources associated");
        CheckBox courseWResInfo = new CheckBox(" All the courses with resources associated.");

        String exportIconImg = "/Models/media/export.png";
        ImageView exportImg = new ImageView(exportIconImg);
        Button exportNSave = new Button("Export");
        addGraphicToButtons(exportImg, exportNSave);

        exportNSave.setOnAction(e -> {
            checkBoxes[0] = pubInfo.isSelected();
            checkBoxes[1] = resNPubInfo.isSelected();
            checkBoxes[2] = personWResInfo.isSelected();
            checkBoxes[3] = courseWResInfo.isSelected();
            exportData(checkBoxes);
        });

        VBox checkboxPane = new VBox(20);
        checkboxPane.getChildren().addAll(pubInfo, resNPubInfo, personWResInfo, courseWResInfo);
        checkboxPane.setAlignment(Pos.CENTER_LEFT);

        VBox mainPane = new VBox(20);
        mainPane.getChildren().addAll(checkboxPane, exportNSave);
        mainPane.setAlignment(Pos.CENTER);

        dlg.setTitle("Exporting");
        dlg.setHeaderText("Exporting Data");
        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setMinHeight(500);

        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        dlg.show();
        dlg.setResultConverter(dialogButton -> null);


    }

    private void exportData(Boolean[] checkBoxes) {
        if (checkBoxes[0]) {
            File exportFile = pickSaveFile("All Publishers");
            saveFile(DBManager.exportCSVPublisherInfo(), exportFile);
        }
        if (checkBoxes[1]) {
            File exportFile = pickSaveFile("All Resources with Publishers");
            saveFile(DBManager.exportCSVResourcePublisher(), exportFile);
        }
        if (checkBoxes[2]) {
            File exportFile = pickSaveFile("All Persons with Resources");
            saveFile(DBManager.exportCSVPersonResources(), exportFile);

        }
        if (checkBoxes[3]) {
            askSemester();

        }
    }

    private File pickSaveFile(String titleActionText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(titleActionText);
        fileChooser.setTitle("Exporting" + titleActionText);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);


        return file;
    }

    private void saveFile(String content, File file) {
        if (file != null) {

            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();

            } catch (IOException ex) {
                Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void importData() {
    }

    private void refreshTable() {

        if (semesterComBox.getValue() == null || yearComBox.getValue() == null)
            courseList = DBManager.returnEverything2(defaultSemest.getId());
        else {

            String year = yearComBox.getValue().toString();
            String semester = semesterComBox.getValue().toString();
            semester = semester.toLowerCase();
            semester = semester.substring(0, 1).toUpperCase() + semester.substring(1);
            semester = semester.replace('_', ' ');
            int semesterid = DBManager.getSemesterIDByName(semester, year);
            courseList = DBManager.returnEverything2(semesterid);
        }
        updateCourseTable();
    }

    private boolean isClassInTheSameYear(Course tempCour) {
        if(!courseList.isEmpty()){
            return (tempCour.getYEAR() == courseList.get(0).getYEAR() &&
                    tempCour.getSEMESTER().equals(courseList.get(0).getSEMESTER()));
        }
        else if (yearComBox.getSelectionModel().getSelectedItem() == null) {
            return (tempCour.getYEAR() == defaultSemest.getYear() && tempCour.getSEMESTER().equals(defaultSemest.getSeason()));

        } else {
            return (tempCour.getYEAR() == Integer.parseInt(yearComBox.getSelectionModel().getSelectedItem().toString()) &&
                    tempCour.getSEMESTER().equals(semesterComBox.getSelectionModel().getSelectedItem().toString()));
        }
    }
}

