package frontend;

import com.mbox.DBManager;
import frontend.data.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * The main controller class for th gui fxml file that has all the functionality need to provide a smooth user experience.
 * The class also interacts with the data manager.
 *
 * @author Rajashow
 */
public class Controller {

    private TableView<Resource> resourceTable;
    private TableColumn<Resource, String> publisherCol, nameCol, authorCol, idcCol;
    private ArrayList<Course> courseList;
    private ArrayList<Person> profList;
    private ArrayList<Resource> resList;
    private ArrayList<Publisher> pubList;
    private Course selectedCourse;
    private Resource selectedResource;
    private Publisher selectedPublisher;
    private Person selectedPerson;

    private final String addIconImg = "/frontend/media/add.png";
    private final String updateIconImg = "/frontend/media/upload.png";
    private final String deleteIconImg = "/frontend/media/delete.png";
    private final String searchIconImg = "/frontend/media/search.png";
    private final String programeIconImg = "/frontend/media/icon.png";
    @FXML
    TextField courseInfoCRN, courseInfoTitle, courseInfoDepart, crnSearchTF, profSearchTF, courseSearchTF, departSearchTF, resourceSearchTF, profInfoFName, profInfoLName;
    @FXML
    ListView resInfolList;
    @FXML
    Button searchBtn, profInfoBtn, resEditBtn, addBtn, updateBtn, deleteBtn;
    @FXML
    TableView<Course> tableTV;
    @FXML
    TableColumn<Course, String> resourceCol, profCol, courseCol, departCol, timeCol;
    @FXML
    ComboBox semesterComBox, semesterComBoxEdit, yearComBox, yearComBoxEdit, profInfoType, courseTemplates;
    @FXML
    CheckBox profCB, courseCB, departCB, resCB;

    boolean debugging = true;

    /**
     * This is initializes the start state.
     * The initial state includes:
     * - values for the combo boxes
     * - value factory for the columns based on the Object "Course"
     * - initial values to display on the table
     * - initializes the resource table
     * - marking all the filter checkboxes to true
     */
    public static void test() {
        System.out.print("TEST WORKED");
    }

    @FXML
    public void initialize() {
        courseList = new ArrayList<>();
        profList = new ArrayList<>();
        resList = new ArrayList<>();
        pubList = new ArrayList<>();
        initComboBoxes();
        initResourcesTable();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resourceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTables();
        initCheckBoxes();
        addButtonGraphics();
        resetSelect();
        if (debugging) {
            test();
        }

    }

    private void addButtonGraphics() {
        ImageView searchImg = new ImageView(searchIconImg);
        addGraphicToButtons(searchImg, searchBtn);
        ImageView addImg = new ImageView(addIconImg);
        addGraphicToButtons(addImg, addBtn);
        ImageView deleteImg = new ImageView(deleteIconImg);
        addGraphicToButtons(deleteImg, deleteBtn);
        ImageView updateImg = new ImageView(updateIconImg);
        addGraphicToButtons(updateImg, updateBtn);
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
        resourceTable = new TableView();
        publisherCol = new TableColumn<>("Publisher");
        nameCol = new TableColumn<>("Title");
        authorCol = new TableColumn<>("Author");
        idcCol = new TableColumn<>("ID");
        resourceTable.getColumns().addAll(idcCol, nameCol, authorCol, publisherCol);
        idcCol.setPrefWidth(100);
        nameCol.setPrefWidth(200);
        authorCol.setPrefWidth(100);
        publisherCol.setPrefWidth(100);
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
        ArrayList<String> years = new ArrayList<>();
        for (int i = 2017; i < Calendar.getInstance().get(Calendar.YEAR) + 1; i++)
            years.add("" + i);
        yearComBox.getItems().addAll(years);
        yearComBoxEdit.getItems().addAll(years);
        profInfoType.getItems().addAll(PersonType.values());
    }


    public void search() {

        String fName, lName;
        fName = lName = "";
        //TODO: talk to mamali about this code
//           String fName="", lName="";
//        fName=lName="";
//        int i = 0;

//        for (String t : temp){
//            i++;
//            if (i==1)
//            fName = t;
//            if (i==2)
//            lName = t;
//        }

        String[] temp = profSearchTF.getText().split(" ");
        switch (temp.length) {
            case 2:
                lName = temp[1];
            case 1:
                fName = temp[0];
                break;
            default:
                System.err.print("Improper input for professor search output");

        }
        System.out.println(fName + " LName:\t" + lName);

//        fName = temp[0];
//        lName = temp[1];
//        int year = Integer.parseInt(yearComBox.getSelectionModel().getSelectedItem().toString());
//        String semester = semesterComBox.getSelectionModel().getSelectedItem().toString();
//        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS

        courseList = DBManager.searchByNameCourseList(fName, lName);
        System.out.println(courseList.get(0).toString());
        System.out.println("It's ok now");
        updateCourseTable();

    }

    public void updateRowSelected() {
        selectedCourse = tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1);
        if (selectedCourse != null) {
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);
            updateBtn.setManaged(true);
            deleteBtn.setManaged(true);
            courseInfoCRN.setText("" + selectedCourse.getCRN());
            profInfoFName.setText(selectedCourse.getProfessor().getFirstName());
            profInfoLName.setText(selectedCourse.getProfessor().getLastName());
            profInfoType.setValue(selectedCourse.getProfessor().getType());
            selectedPerson = selectedCourse.getProfessor();
            courseInfoTitle.setText(selectedCourse.getTitle());
            courseInfoDepart.setText(selectedCourse.getDepartment());
            semesterComBoxEdit.getSelectionModel().select(selectedCourse.getSEMESTER());
            yearComBoxEdit.getSelectionModel().select(new Integer(selectedCourse.getYEAR()));
            ArrayList<Resource> tempRes = selectedCourse.getResource();
            resourceTable.getItems().clear();
            resourceTable.getItems().addAll(resList);
            resInfolList.getItems().clear();
            resourceTable.getSelectionModel().select(null);
            for (int i = 0; i < tempRes.size(); i++) {
                if (i < 3) {
                    resInfolList.getItems().add(tempRes.get(i).getTitle());
                }
                resourceTable.getSelectionModel().select(tempRes.get(i));
            }

        }
    }

    private void updateCourseTable() {

        tableTV.getItems().clear();
        tableTV.getItems().addAll(courseList);
    }

    /**
     * Un-selects the selected row when clicking on background
     */
    public void resetSelect() {
        tableTV.getSelectionModel().clearSelection();
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
        updateBtn.setManaged(false);
        deleteBtn.setManaged(false);
    }

    public void add() {
//        selectedPerson.setFirstName(profInfoFName.getText());
//        selectedPerson.setLastName(profInfoLName.getText());
//        selectedPerson.setType(profInfoType.getSelectionModel().getSelectedItem().toString());

        Person tempPerosn = new Person(selectedPerson);

        ArrayList<Resource> tempRes = new ArrayList<Resource>(resourceTable.getSelectionModel().getSelectedItems());
        Course tempCour = new Course(
                selectedCourse.getID(),
                tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1).getID(),
                Integer.parseInt(yearComBoxEdit.getSelectionModel().getSelectedItem().toString()),
                semesterComBoxEdit.getSelectionModel().getSelectedItem().toString(),
                courseInfoTitle.getText(),
                courseInfoDepart.getText(),
                tempPerosn,
                courseInfoCRN.getText(),
                tempRes
        );
        System.out.println( "PrfoessorID before changing" + tempCour.getProfessor().getID());


        // I create a new object and then check if the information in the boxes are different form
        // the Selected Course Object, then if there is a difference, I will add a new course/person/...
        boolean courseChanged = false, professorChanged = false, resourceChanged = false;

        professorChanged = tempCour.getProfessor().getFirstName() != profInfoFName.getText() ||
                tempCour.getProfessor().getLastName() != profInfoLName.getText();
        // I don't check type rn. Need to check later with fk.. enum :))

        if (professorChanged ) {
        tempCour.getProfessor().setFirstName(profInfoFName.getText());
        tempCour.getProfessor().setLastName(profInfoLName.getText());
        tempCour.getProfessor().setType(profInfoType.getSelectionModel().getSelectedItem().toString());
        int id = DBManager.insertPersonQuery(tempPerosn);
        tempCour.getProfessor().setID(id);
        }


        System.out.println( "PrfoessorID before adding" + tempCour.getProfessor().getID());
        courseList.add(tempCour); // later on it should be gone, nothing should not be in courseList manually
        // Everything in coureseList should be get from the DB.

        DBManager.relationalInsertByID2(tempCour);

        updateCourseTable();
    }

    //TODO: look at it with guido how is it related with update button
    public void updateCourseInformation() {

        //has an exception when combobox is empty (program keeps running, needs to be fixed);

        String semester = semesterComBox.getValue().toString();
        String year = yearComBox.getValue().toString();

        System.out.println("|" + semester + "|" + year);

        int id = DBManager.getSemesterIDByName(semester, year);
        System.out.println(id);

        courseList = DBManager.returnEverything(id);

        updateCourseTable();
    }

    /**
     * populates the table with initial values
     */
    private void initTables() {
        setTablesSelectionProperty(tableTV);
        setTablesSelectionProperty(resourceTable);
        //======================BEGIN CODE BACKEND
        DBManager.openConnection();


        ArrayList<Course> pulledDatabase = DBManager.returnEverything(52);
        for (int k = 0; k < pulledDatabase.size(); k++) {
            courseList.add(pulledDatabase.get(k));
            resList.addAll(pulledDatabase.get(k).getResource());

        }

        for (int i = 0; i < com.mbox.DBManager.getPersonFromTable().size(); i++) {
            profList.add(com.mbox.DBManager.getPersonFromTable().get(i).initPersonGUI());
        }
        for (Resource tempR : resList)
            pubList.add(tempR.getPublisher());
        //====================== END CODE BACKEND


        updateCourseTable();
    }

    private void setTablesSelectionProperty(TableView table) {
        table.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
            @Override
            public TableRow<Course> call(TableView<Course> tableView2) {
                final TableRow<Course> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < table.getItems().size() && table.getSelectionModel().isSelected(index)) {
                            table.getSelectionModel().clearSelection();
                            event.consume();
                            updateRowSelected();
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
                StringBuffer temp = new StringBuffer();
                ArrayList<Resource> res = c.getValue().getResource();
                int length = c.getValue().getResource().size();
                for (int i = 0; i < length; i++) {
                    String tTitle = res.get(i).getTitle();
                    temp.append(tTitle.substring(0, Math.min(tTitle.length(), (78 / (length % 10)))));
                    temp.append(" , ");
                }
                return new SimpleStringProperty(temp.toString());
            }
        });
        timeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Course, String> c) {
                Course cTemp = c.getValue();
                StringBuffer temp = new StringBuffer();
                temp.append(cTemp.getSEMESTER().substring(0, 3));
                temp.append(" ∈");
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
        authorCol.setCellValueFactory(new
                PropertyValueFactory<Resource, String>("author"));
    }


    public void exit() {
        System.exit(0);
    }

    public void delete() {
    }

    public void exportData() {
    }

    public void importData() {
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

    private void selectPublisher() {
        Dialog dlg = new Dialog();
        dlg.setTitle("Select Resource");
        dlg.setHeaderText("Select Resource");
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        VBox mainPane = new VBox();
        ButtonType assign = new ButtonType("Create & Assign", ButtonBar.ButtonData.OK_DONE);

        Label listOfPublisher = new Label("List of Current Publisher: ");
        Label name = new Label("Name: ");
        Label contact = new Label("Contacts: ");
        Label description = new Label("Description: ");

        ComboBox publishersCB = new ComboBox();
        TextField nameTF = new TextField();
        TextField contactsTF = new TextField();
        TextField descriptionTF = new TextField();

        publishersCB.getItems().addAll(pubList);
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(400);
        publishersCB.setOnMouseClicked(e -> {
            if (publishersCB.getSelectionModel().getSelectedItem() != null) {
                Publisher tempPub = (Publisher) publishersCB.getSelectionModel().getSelectedItem();
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

        mainPane.getChildren().addAll(
                new HBox(listOfPublisher, publishersCB),
                new HBox(name, nameTF),
                new HBox(contact, contactsTF),
                new HBox(description, descriptionTF)
        );

        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(20);
        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                selectedPublisher = new Publisher(nameTF.getText(), contactsTF.getText(), descriptionTF.getText());
                pubList.add(selectedPublisher);
                return null;
            }
            return null;
        });
    }

    public VBox resourceDetailedView() {
        VBox resourceEditPane = new VBox();
        Label title = new Label("Title: ");
        Label author = new Label(("Author: "));
        Label id = new Label("ID: ");
        Label totalAmount = new Label(("Total Amount: "));
        Label currentAmount = new Label(("Current Amount: "));
        Label description = new Label("Description: ");
        Label type = new Label("Type: ");
        Label publisher = new Label("Publisher: ");

        TextField titleTF = new TextField();
        TextField authorTF = new TextField();
        TextField idTF = new TextField();
        TextField totalAmTF = new TextField();
        TextField currentAmTF = new TextField();
        TextField descriptionTF = new TextField();
        Button publisherBtn = new Button("Click me to add a new Publisher");
        ComboBox typeCB = new ComboBox();

        typeCB.getItems().add("Books");
        Button addNAssignNewResource = new Button("Add and Assign");
        addGraphicToButtons(new ImageView(addIconImg), addNAssignNewResource);
        Button delete = new Button();
        addGraphicToButtons(new ImageView(deleteIconImg), delete);
        Button update = new Button();
        addGraphicToButtons(new ImageView(updateIconImg), update);

        addNAssignNewResource.setOnAction(e -> {
            Publisher tempPub = selectedPublisher;
            Resource temp = new Resource(typeCB.getSelectionModel().getSelectedItem().toString(),
                    titleTF.getText(),
                    authorTF.getText(),
                    descriptionTF.getText(),
                    false,
                    Integer.parseInt(totalAmTF.getText()),
                    Integer.parseInt(idTF.getText()),
                    Integer.parseInt(currentAmTF.getText()),
                    selectedPublisher
            );
            resList.add(temp);
            resourceTable.getItems().clear();
            resourceTable.getItems().addAll(resList);
            selectedPublisher = tempPub;


        });
        delete.setOnAction(e -> {
            ArrayList<Resource> temp = new ArrayList<>();
            temp.addAll(resourceTable.getSelectionModel().getSelectedItems());
            for (Resource r : temp) {
                resList.remove(r);
                resourceTable.getItems().remove(r);
                for (Course c : courseList) {
                    c.getResource().remove(r);
                }

            }
            updateCourseTable();
            resInfolList.getItems().clear();
            resInfolList.getItems().addAll(resList);
            onResourceTableSelect(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);
        });
        update.setOnAction(e -> {
        });
        HBox buttons = new HBox(addNAssignNewResource, update, delete);

        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);


        publisherBtn.setOnAction(e -> {
            selectPublisher();
            publisherBtn.setText(selectedPublisher != null ? selectedPublisher.getName() : "Click me to add a new Publisher");
        });
        resourceTable.setOnMouseClicked(e -> {
            onResourceTableSelect(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);

        });
        onResourceTableSelect(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);
        resourceEditPane.getChildren().addAll(
                new HBox(type, typeCB),
                new HBox(title, titleTF),
                new HBox(author, authorTF),
                new HBox(id, idTF),
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

    private void onResourceTableSelect(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, Button publisherBtn, ComboBox typeCB, Button addNAssignNewResource, Button update, Button delete) {

        Resource tempRes = resourceTable.getSelectionModel().getSelectedItems().get(0);
        if (tempRes != null) {
            titleTF.setText(tempRes.getTitle());
            authorTF.setText(tempRes.getAuthor());
            idTF.setText(String.valueOf(tempRes.getID()));
            typeCB.getItems().addAll(tempRes.getTYPE());
            typeCB.getSelectionModel().select(tempRes.getTYPE());
            descriptionTF.setText(tempRes.getDescription());
            publisherBtn.setText(tempRes.getPublisher() != null ? tempRes.getPublisher().toString() : "No publisher assigned.Click me.");
            selectedPublisher = tempRes.getPublisher();
            totalAmTF.setText(String.valueOf(tempRes.getTotalAmount()));
            currentAmTF.setText(String.valueOf(tempRes.getCurrentAmount()));
            update.setVisible(true);
            delete.setVisible(true);
            update.setManaged(true);
            delete.setManaged(true);
        } else {
            update.setVisible(false);
            delete.setVisible(false);
            update.setManaged(false);
            delete.setManaged(false);
        }
    }


    /**
     * Assign a new Resources to Course.
     * It creates Resource objects and assign the resources as the Professor for the course object
     */
    public void openResourceView() {
        //TODO: migrate Publisher add and modify window

        VBox mainPane = new VBox();
        Dialog dlg = new Dialog();
        TitledPane resourceTitlePane = new TitledPane();
        VBox resourceEditPane = resourceDetailedView();
        ComboBox listOFResources = new ComboBox();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);


        ButtonType assign = new ButtonType("Assign the  Selected Resources", ButtonBar.ButtonData.OK_DONE);

        listOFResources.getItems().addAll(resList);
        resourceTable.getItems().clear();
        resourceTable.getItems().addAll(resList);
        updateRowSelected();


        resourceTitlePane.setContent(resourceEditPane);
        resourceTitlePane.setText("Resource Details and Management");
        resourceTitlePane.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(new HBox(resourceTitlePane, resourceTable));
        mainPane.setAlignment(Pos.CENTER);


        dlg.setTitle("Assigning Resource");
        dlg.setHeaderText("Assigning Resource");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(400);


        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                for (Resource r : resourceTable.getSelectionModel().getSelectedItems())
                    System.out.println(r);
                return null;
            }
            return null;
        });
    }

    /**
     * Assign a new Professor to Course.
     * It creates a Person object and assign the person as the Professor for the course object
     */
    public void selectProf() {
        VBox mainAddPane = new VBox(2);

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        courseTemplates = new ComboBox();
        courseTemplates.getItems().addAll(profList);

        Label currentCBoxLbl = new Label("Current Professor : ");

        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);


        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, courseTemplates)
        );
        mainAddPane.setSpacing(20);

        dlg.setTitle("Assigning Professor");
        dlg.setHeaderText("Assigning Professor");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {
                selectedPerson = ((Person) (courseTemplates.getSelectionModel().getSelectedItem()));
                profInfoFName.setText(selectedPerson.getFirstName());
                profInfoLName.setText(selectedPerson.getLastName());
                profInfoType.setValue(selectedPerson.getType());


            }
            return null;
        });

    }

    public void selectCourse() {
        //TODO: add the template information transfer  to course functionality
        ArrayList<Course> tempCourses = new ArrayList<>();

        VBox mainAddPane = new VBox(2);

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        courseTemplates = new ComboBox();
        //TODO: Course template method
        courseTemplates.getItems().addAll();

        Label currentCBoxLbl = new Label("Course Templates : ");

        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);


        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, courseTemplates)
        );
        mainAddPane.setSpacing(20);

        dlg.setTitle("Assigning Course");
        dlg.setHeaderText("Assigning Course");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {

                selectedCourse = new Course();
                courseInfoCRN.setText("" + selectedCourse.getCRN());
                profInfoFName.setText(selectedCourse.getProfessor().getFirstName());
                profInfoLName.setText(selectedCourse.getProfessor().getLastName());
                profInfoType.setValue(selectedCourse.getProfessor().getType());
                selectedPerson = selectedCourse.getProfessor();
                courseInfoTitle.setText(selectedCourse.getTitle());
                courseInfoDepart.setText(selectedCourse.getDepartment());
                semesterComBoxEdit.getSelectionModel().select(selectedCourse.getSEMESTER());
                yearComBoxEdit.getSelectionModel().select(new Integer(selectedCourse.getYEAR()));
                ArrayList<Resource> tempRes = selectedCourse.getResource();
                resourceTable.getItems().clear();
                resourceTable.getItems().addAll(resList);
                resInfolList.getItems().clear();
                resourceTable.getSelectionModel().select(null);
                for (int i = 0; i < tempRes.size(); i++) {
                    if (i < 3) {
                        resInfolList.getItems().add(tempRes.get(i).getTitle());
                    }
                    resourceTable.getSelectionModel().select(tempRes.get(i));
                }
            }
            return null;
        });


    }
}
