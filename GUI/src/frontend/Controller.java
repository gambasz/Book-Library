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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main controller class for th gui fxml file that has all the functionality need to provide a smooth user experience.
 * The class also interacts with the data manager.
 *
 * @author Rajashow
 */
public class Controller {

    private final String addIconImg = "/frontend/media/add.png";
    private final String updateIconImg = "/frontend/media/upload.png";
    private final String deleteIconImg = "/frontend/media/delete.png";
    private final String searchIconImg = "/frontend/media/search.png";
    private final String programeIconImg = "/frontend/media/icon.png";
    @FXML
    TextField courseInfoDescrip, courseInfoTitle, courseInfoDepart, crnSearchTF, profSearchTF, courseSearchTF, departSearchTF, resourceSearchTF, profInfoFName, profInfoLName;
    @FXML
    ListView<String> resInfoList;
    @FXML
    Button searchBtn, profInfoBtn, resEditBtn, addBtn, updateBtn, deleteBtn, filterBtn;
    @FXML
    TableView<Course> tableTV;
    @FXML
    TableColumn<Course, String> resourceCol, profCol, courseCol, departCol, timeCol;
    @FXML
    ComboBox semesterComBox, semesterComBoxEdit, yearComBox, yearComBoxEdit, profInfoType;
    @FXML
    CheckBox profCB, courseCB, departCB, resCB;
    boolean debugging = true;
    private TableView<Resource> resourceTable;
    private TableColumn<Resource, String> publisherCol, nameCol, authorCol, idcCol;
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
    public static void test() {
        System.out.print("The program started running now!");
    }

    protected static void showError(String title, String headerMessage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    public void guido() {
        System.out.println("Hey");
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
        initTables();
        initCheckBoxes();
        addButtonGraphics();
        resetSelect();
        if (debugging) {
            test();
        }

        // Department textbox in the serach is disabled. As default always computer sciece
        departSearchTF.setDisable(true);

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
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = 2017; i < Calendar.getInstance().get(Calendar.YEAR) + 1; i++)
            years.add(i);
        yearComBox.getItems().addAll(years);
        yearComBoxEdit.getItems().addAll(years);
        profInfoType.getItems().addAll(PersonType.values());
    }

    public void search() {

        // get the info in all the comboboxes
        //check if they are empty.

        String commonid = "";
        String professorname = "";
        String coursename = "";
        String resource = "";

        if (!crnSearchTF.getText().isEmpty()) {
            commonid = crnSearchTF.getText();
        }
        if (!profSearchTF.getText().isEmpty()) {
            professorname = profSearchTF.getText();
        }
        if (!courseSearchTF.getText().isEmpty()) {
            coursename = courseSearchTF.getText();
        }
        if (!resourceSearchTF.getText().isEmpty()) {
            resource = resourceSearchTF.getText();
        }


    }

    public void updateRowSelected() {
        // Filling textboxes on the left, selectedcourse

        if (!isPersonResourcesView) {
            selectedCourse = tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1);
            if (selectedCourse != null) {
                setChildVisiblity(true, updateBtn, deleteBtn);
                courseInfoDescrip.setText("" + selectedCourse.getDescription());
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
                resInfoList.getItems().clear();
                resourceTable.getSelectionModel().select(null);
                for (int i = 0; i < tempRes.size(); i++) {
                    if (i < 3) {
                        resInfoList.getItems().add(tempRes.get(i).getTitle());
                    }
                    resourceTable.getItems().add(tempRes.get(i));
                }

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
        setChildVisiblity(false, updateBtn, deleteBtn);
    }

    public void add() {
//        selectedPerson.setFirstName(profInfoFName.getText());
//        selectedPerson.setLastName(profInfoLName.getText());
//        selectedPerson.setType(profInfoType.getSelectionModel().getSelectedItem().toString());

        if (selectedCourse != null) {
            selectedPerson = new Person(selectedPerson);
            ArrayList<Resource> tempRes = new ArrayList<Resource>(resourceTable.getItems());

            Course tempCour = new Course(
                    selectedCourse.getID(),
                    tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1).getID(),
                    Integer.parseInt(yearComBoxEdit.getSelectionModel().getSelectedItem().toString()),
                    semesterComBoxEdit.getSelectionModel().getSelectedItem().toString(),
                    courseInfoTitle.getText(),
                    courseInfoDepart.getText(),
                    selectedPerson, courseInfoDescrip.getText(), tempRes);

            //System.out.println("PrfoessorID before changing" + tempCour.getProfessor().getID());

            // I create a new object and then check if the information in the boxes are different form
            // the Selected Course Object, then if there is a difference, I will add a new course/person/...
            boolean courseChanged = false, professorChanged = false, resourceChanged = false;

            professorChanged = tempCour.getProfessor().getFirstName() != profInfoFName.getText() ||
                    tempCour.getProfessor().getLastName() != profInfoLName.getText();
            courseChanged = tempCour.getTitle() != courseInfoTitle.getText() || tempCour.getDescription() != courseInfoDepart.getText() ||
                    tempCour.getDepartment() != courseInfoDescrip.getText();
            // I don't check type rn. Need to check later with fk.. enum :))

            if (professorChanged) {
                tempCour.getProfessor().setFirstName(profInfoFName.getText());
                tempCour.getProfessor().setLastName(profInfoLName.getText());
                tempCour.getProfessor().setType(profInfoType.getSelectionModel().getSelectedItem().toString());
                int id = DBManager.insertPersonQuery(selectedPerson);
                tempCour.getProfessor().setID(id);
            }
            if (courseChanged) {
                System.out.println(courseInfoDepart.getText());
                System.out.println(courseInfoDescrip.getText());

                tempCour.setDepartment(courseInfoDepart.getText());
                tempCour.setTitle(courseInfoTitle.getText());
                tempCour.setDescription(courseInfoDescrip.getText());
                int cID = DBManager.insertCourseQuery(tempCour);
                //System.out.println("Course ID is: " + tempCour.getID()+"  Should be: " + cID);
                tempCour.setID(cID);
                System.out.println("New Course Added");
            }

            tempCour = DBManager.relationalInsertByID2(tempCour);
            courseList.add(tempCour);

            updateCourseTable();
        } else {
            System.out.println("Not selected");
            showError("Selection error", "No course selected",
                    "Please select a course to add, update or delete");
        }
    }

    public void filterTableBasedOnSemesterNYear() {

        //has an exception when combobox is empty (program keeps running, needs to be fixed);

        String semester = semesterComBox.getValue().toString();
        String year = yearComBox.getValue().toString();


        int id = DBManager.getSemesterIDByName(semester, year);

        courseList = DBManager.returnEverything(id);

        updateCourseTable();
    }

    /**
     * populates the table with initial values
     */
    private void initTables() {
        try {

            setTablesSelectionProperty(tableTV);
            setTablesSelectionProperty(resourceTable);
            //todo: when hash tables are done remove the *contains codes*
            //======================BEGIN CODE BACKEND
            DBManager.openConnection();

            ArrayList<Course> pulledDatabase = DBManager.returnEverything(defaultSemester);

            if (pulledDatabase == null) {
                showError("Connection Error", "Server did not return any  data", "The returnEverything did not return any course");
                pulledDatabase = new ArrayList<>();
            }

            for (int k = 0; k < pulledDatabase.size(); k++) {
                courseList.add(pulledDatabase.get(k));
                for (Resource r : pulledDatabase.get(k).getResource()) {
                    if (!resList.contains(r))
                        resList.add(r);

                }
                //System.out.println("If this is the error, desription:" + pulledDatabase.get(k).getDescription() + pulledDatabase.get(k).getDepartment());
                //

            }


            //Here I initialize pubList with method getPublisherFromTable()
            pubList = DBManager.convertArrayPubPub(DBManager.getPublisherFromTable());
            ///////////////////////////////////////////////////////////////
            for (Resource tempR : resList) {
                if (!pubList.contains(tempR.getPublisher()))
                    pubList.add(tempR.getPublisher());
            }
            //====================== END CODE BACKEND


            updateCourseTable();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Connection Error", "Server did not return any  data",
                    "Check you connection and try again :-)");

        }
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
                            System.out.println("Deselect, isSelected is false");
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
        authorCol.setCellValueFactory(new
                PropertyValueFactory<Resource, String>("author"));
    }

    public void exit() {
        System.exit(0);
    }
    //thjanks :)) hard to see

    public void delete() {
        System.out.println("SelectedCourse TItle BEFORE: " + selectedCourse.getTitle()+ "ObjID: " + selectedCourse.hashCode());
        if (selectedCourse != null) {
            System.out.println("CommonID: " +selectedCourse.getCommonID() + " and title: " + selectedCourse.getTitle());
            DBManager.delete_relation_course(selectedCourse);
            System.out.println("CommonID: " +selectedCourse.getCommonID() + " and title: " + selectedCourse.getTitle());
            System.out.println("SelectedCourse TItle: " + selectedCourse.getTitle()+ "ObjID: " +selectedCourse.hashCode());
            courseList.remove(selectedCourse);
            selectedCourse = null;
            updateCourseTable();

            //wtfffff :)) they can have the same hashcode even if they are not equal..
            // yeah! thats our problem! We have one CMS203 with CommonID 222 and other one with CommonID 99 and ohter professor
            // but both have the same hashchode!
  //can we override by the method remove by searching for commonID ? :))
            // in that case we need to be restrict about commonID
            // maybe but its hard.. I still don't know how to fix @@

        } else {
            showError("Selection error", "No course selected",
                    "Please select a course to add, update or delete");
        }
    }

    public void update() {
        if (selectedCourse != null) {
            courseList.remove(selectedCourse);
            DBManager.deleteRelationCourseResources(selectedCourse);


            Boolean courseChanged = false;
            courseChanged = selectedCourse.getTitle() != courseInfoTitle.getText() || selectedCourse.getDescription() != courseInfoDepart.getText() ||
                    selectedCourse.getDepartment() != courseInfoDescrip.getText();
            if (courseChanged) {
                Course comboBoxesCourse = new Course(0, courseInfoTitle.getText(), courseInfoDepart.getText(),
                        courseInfoDescrip.getText());
                //comboBoxesCourse.setCommonID(selectedCourse.getCommonID());
                comboBoxesCourse.setID(DBManager.insertCourseQuery(comboBoxesCourse));
                selectedCourse.setTitle(comboBoxesCourse.getTitle());
                selectedCourse.setDepartment(comboBoxesCourse.getDepartment());
                selectedCourse.setDescription(comboBoxesCourse.getDescription());
                selectedCourse.setID(comboBoxesCourse.getID());
            }

            ArrayList<Resource> selected_resources = selectedCourse.getResource();
            ArrayList<Resource> new_resources = resList;
            //delete all exist relation between course and its resources


            String newname = profInfoFName.getText();
            String newlastname = profInfoLName.getText();
            String newtype = profInfoType.getSelectionModel().getSelectedItem().toString();


            Person selected_person = selectedPerson;
            Person new_person = new Person(newlastname, newname, newtype);

            //checking person
            //if they are the same

            if (selected_person.getFirstName() == new_person.getFirstName() && selected_person.getLastName() == new_person.getLastName()) {

                selectedCourse.setProfessor(selected_person);
                System.out.println("The two people are the same");

            } else {

                selectedCourse.setProfessor(new_person);
                System.out.println("Two people are different");

            }


            selectedCourse.setResource(selected_resources);

            DBManager.updateCourseAndPerson(selectedCourse);

            //add new relation between current resources in course instance and that course
            DBManager.insertRelationCourseResources(selectedCourse);
            courseList.add(selectedCourse);
            updateCourseTable();


        } else {
            showError("Selection error", "No course selected",
                    "Please select a course to add, update or delete");
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
        publishersCB.setOnAction(e -> {
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
        dlg.setOnCloseRequest(e -> {
            publisherBtn.setText(selectedPublisher != null ? selectedPublisher.getName() : "Click me to add a new Publisher");
        });
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                //TODO: Assign the publisher button
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

    private VBox resourceDetailedView() {
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
        ComboBox<String> typeCB = new ComboBox();

        typeCB.getItems().add("Books");
        Button addNAssignNewResource = new Button("Add and Assign");
        addGraphicToButtons(new ImageView(addIconImg), addNAssignNewResource);
        Button delete = new Button();
        addGraphicToButtons(new ImageView(deleteIconImg), delete);
        Button update = new Button();
        addGraphicToButtons(new ImageView(updateIconImg), update);
        Button autoFillBtn = new Button("Auto Fill ");

        autoFillBtn.setOnAction(e -> {
            selectResourceTemplates(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);

        });

        addNAssignNewResource.setOnAction(e -> {
            addAndAssignNewResource(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, typeCB);
        });
        delete.setOnAction(e -> {
            deleteResource(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, delete, update);
        });
        update.setOnAction(e -> {
            updateResource(titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, typeCB);
        });
        HBox buttons = new HBox(addNAssignNewResource, update, delete);

        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);


        publisherBtn.setOnAction(e -> {
            selectPublisher(publisherBtn);
        });
        resourceTable.setOnMouseClicked(e -> {
            onResourceTableSelect(resourceTable.getSelectionModel().getSelectedItems().get(0), titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);

        });
        onResourceTableSelect(resourceTable.getSelectionModel().getSelectedItems().get(0), titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);
        autoFillBtn.setAlignment(Pos.CENTER_RIGHT);
        HBox hiddenSpacer = new HBox(new Separator(), new Separator(), new Separator(), new Separator(), new Separator(), new Separator(), new Separator());
        hiddenSpacer.setVisible(false);
        resourceEditPane.getChildren().addAll(
                new HBox(type, typeCB, hiddenSpacer, autoFillBtn),
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

    private void updateResource(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, ComboBox typeCB) {
//make sure to have method that find the resourceID & publisherID=0, to change it from 0 to the right one
        selectedResource = resourceTable.getSelectionModel().getSelectedItem();
        String new_title = titleTF.getText();
        String new_author = authorTF.getText();
        int new_total = Integer.parseInt(totalAmTF.getText());
        int new_current = Integer.parseInt(currentAmTF.getText());
        String new_descrip = descriptionTF.getText();
        String new_type = typeCB.getSelectionModel().getSelectedItem().toString();
        Publisher new_publisher = selectedPublisher;


        if (selectedResource.getTYPE() == new_type && selectedResource.getTitle() == new_title
                && selectedResource.getAuthor() == new_author && selectedResource.getCurrentAmount() == new_current
                && selectedResource.getTotalAmount() == new_total && selectedResource.getDescription() == new_descrip) {
            System.out.println("Everything is the same, no change, so do nothing");
            DBManager.updatePublisherForResource(selectedResource, selectedPublisher);
            selectedResource.setPublisher(selectedPublisher);

        } else {
            com.mbox.Resource tempRes = new com.mbox.Resource(selectedResource.getID(), new_type, new_title, new_author, "123", new_total, new_current, new_descrip);
            DBManager.executeNoReturnQuery(DBManager.updateResourceQuery(tempRes));
            System.out.println("Updated resource with ID: " + selectedResource.getID());
            DBManager.updatePublisherForResource(tempRes.initResourceGUI(), selectedPublisher);
            selectedResource.setPublisher(selectedPublisher);
        }
        System.out.println("Publisher  now is " + selectedPublisher);
        //check if this resource and publisher already had relation or not, delete the old one and add the new one
        // what if there is no publisher yet? the publisherID should be 0
    }

    private void deleteResource(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, Button publisherBtn, ComboBox typeCB, Button addNAssignNewResource, Button delete, Button update) {
        ArrayList<Resource> temp = new ArrayList<>();
        temp.addAll(resourceTable.getSelectionModel().getSelectedItems());
        for (Resource r : temp) {
//            resList.remove(r);
            resourceTable.getItems().remove(r);
            resInfoList.getItems().remove(r.getTitle());
            //TODO: THIS is Khanh'change, remember to cut and add when pull
            if (!isPersonResourcesView)
                selectedCourse.getResource().remove(r);
            else {
                selectedPerson.getResources().remove(r);
            }
        }
        updateCourseTable();
        onResourceTableSelect(resourceTable.getSelectionModel().getSelectedItems().get(0), titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);
    }

    private void addAndAssignNewResource(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, ComboBox typeCB) {

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
        if (!isPersonResourcesView) {
            resList.add(temp);
            resourceTable.getItems().add(temp);
            selectedPublisher = tempPub;
            DBManager.setIDinResourceFromArrayList(resList);
            System.out.println("This is add button");
            //add(+) button is fine
        } else {
            for (Resource resource : selectedPerson.getResources()) {
                System.out.println("ResourceID After *BEFORE* to list: " + resource.getID());
            }
            System.out.println("add button person section");
            resourceTable.getItems().add(temp);
            selectedPublisher = tempPub;
            selectedPerson.getResources().add(temp);
            //DBManager.insertPersonResources(selectedPerson);
            for (Resource resource : selectedPerson.getResources()) {
                System.out.println("ResourceID After adding to list: " + resource.getID());
            }
        }


    }

    private void selectResourceTemplates(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, Button publisherBtn, ComboBox typeCB, Button addNAssignNewResource, Button update, Button delete) {
        VBox mainAddPane = new VBox(2);
        Dialog dlg = new Dialog();

        resList = DBManager.getResourceList();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Resource> resources = new ComboBox();
        //TODO: burn this with fire
        resources.getItems().addAll(resList);
        Label currentCBoxLbl = new Label("Resources : ");
        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button deleteBtn = new Button("Delete");

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
        dlg.setTitle("Assigning Course");
        dlg.setHeaderText("Assigning Course");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {
                onResourceTableSelect(resources.getSelectionModel().getSelectedItem(), titleTF, authorTF, idTF, totalAmTF, currentAmTF, descriptionTF, publisherBtn, typeCB, addNAssignNewResource, update, delete);
            }
            return null;
        });

    }

    private void deleteResource(Resource res) {
        resList.remove(res);


    }

    private void onResourceTableSelect(Resource tempRes, TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, Button publisherBtn, ComboBox typeCB, Button addNAssignNewResource, Button update, Button delete) {

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
            setChildVisiblity(true,update,delete);
        } else {
            setChildVisiblity(false,update,delete);
        }
    }

    /**
     * Assign a new Resources to Course.
     * It creates Resource objects and assign the resources as the Professor for the course object
     */
    public void openResourceView() {
        isPersonResourcesView = false;
        //TODO: migrate Publisher add and modify window
        if (selectedCourse != null) {
            VBox mainPane = new VBox();
            Dialog dlg = new Dialog();
            TitledPane resourceTitlePane = new TitledPane();
            VBox resourceEditPane = resourceDetailedView();
            ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
            icon.setFitHeight(75);
            icon.setFitWidth(75);


            ButtonType assign = new ButtonType("Assign the Selected Resources", ButtonBar.ButtonData.OK_DONE);

            resourceTable.getItems().clear();
            resourceTable.getItems().addAll(selectedCourse.getResource());

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
                    //TODO: this is where the assign button locates
                    selectedCourse.getResource().clear();
                    selectedCourse.getResource().addAll(resourceTable.getItems());

                    resInfoList.getItems().clear();
                    for (Resource r : selectedCourse.getResource())
                        resInfoList.getItems().add(r.getTitle());

                    return null;
                }
                return null;
            });
        } else {
            showError("Selection error", "No course selected",
                    "Please select a course to add, update or delete");
        }
    }

    /**
     * Assign a new Professor to Course.
     * It creates a Person object and assign the person as the Professor for the course object
     */
    public void selectProf() {
        VBox mainAddPane = new VBox(2);

        ArrayList<com.mbox.Person> allPersonTemp = com.mbox.DBManager.getPersonFromTable();
        profList.clear();
        for (int i = 0; i < allPersonTemp.size(); i++) {
            if (!profList.contains(allPersonTemp.get(i).initPersonGUI()))
                profList.add(allPersonTemp.get(i).initPersonGUI());
        }

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());

        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Person> currentProfessors = new ComboBox();
        currentProfessors.getItems().addAll(profList);

        Label currentCBoxLbl = new Label("Current Professor : ");
        Label profInfoFNameLbl = new Label("First Name");
        Label profInfoLNameLbl = new Label("Lase Name");
        Label profInfoTypeLbl = new Label("Type");

        TextField profInfoFNameTf = new TextField();
        TextField profInfoLNameTf = new TextField();
        ComboBox profInfoTypeCB = new ComboBox<>();

        profInfoTypeCB.setItems(profInfoType.getItems());

        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button PersonResources = new Button("View Person's Resources");
        Button addProfessor = new Button("Add");
        Button NAME_ME_SOMETHING_ELSE = new Button("?");

        Button deleteBtn = new Button("Delete");


        setChildVisiblity(false, PersonResources, NAME_ME_SOMETHING_ELSE, deleteBtn);
        addProfessor.setOnMouseClicked(e -> {
            addNewProfessor(profInfoFNameTf.getText(), profInfoLNameTf.getText(), profInfoTypeCB.getSelectionModel().getSelectedItem());
            currentProfessors.getItems().clear();
            currentProfessors.getItems().addAll(profList);

        });
        NAME_ME_SOMETHING_ELSE.setOnMouseClicked(e -> {
            resourcePersonDiffView(currentProfessors.getSelectionModel().getSelectedItem());
        });
        currentProfessors.setOnAction(e -> {
            System.out.println(currentProfessors.getSelectionModel().getSelectedItem());
            if (currentProfessors.getSelectionModel().getSelectedItem() != null) {
                profInfoFNameTf.setText(currentProfessors.getSelectionModel().getSelectedItem().getFirstName());
                profInfoLNameTf.setText(currentProfessors.getSelectionModel().getSelectedItem().getLastName());
                profInfoTypeCB.setValue(currentProfessors.getSelectionModel().getSelectedItem().getType());

                setChildVisiblity(true, PersonResources, NAME_ME_SOMETHING_ELSE, deleteBtn);


            } else {
                setChildVisiblity(false, PersonResources, NAME_ME_SOMETHING_ELSE, deleteBtn);


            }
        });
        deleteBtn.setOnAction(e -> {
            deleteProfessor(currentProfessors.getSelectionModel().getSelectedItem());
            currentProfessors.getItems().clear();
            currentProfessors.getItems().addAll(profList);

        });
        PersonResources.setOnAction(e -> {
            // When the opening resources for person view button pressed
            ArrayList<Resource> tempRes = new ArrayList<>();
            isPersonResourcesView = true;
            tempRes.addAll(resList);
            System.out.print(tempRes);
            currentProfessors.getSelectionModel().getSelectedItem().setResources(tempRes);
            showPersonsResources(currentProfessors.getSelectionModel().getSelectedItem());
        });
        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, currentProfessors),
                new HBox(profInfoFNameLbl, profInfoFNameTf),
                new HBox(profInfoLNameLbl, profInfoLNameTf),
                new HBox(profInfoTypeLbl, profInfoTypeCB),
                new HBox(deleteBtn, addProfessor, NAME_ME_SOMETHING_ELSE, PersonResources)

        );
        for (Object tempElem : mainAddPane.getChildren()) {

            ((HBox) tempElem).setSpacing(20);
            ((HBox) tempElem).setAlignment(Pos.CENTER);
        }
        mainAddPane.setSpacing(20);
        mainAddPane.setAlignment(Pos.CENTER);
        dlg.setTitle("Assigning Professor");
        dlg.setHeaderText("Assigning Professor");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {
                selectedPerson = ((Person) (currentProfessors.getSelectionModel().getSelectedItem()));
                profInfoFName.setText(selectedPerson.getFirstName());
                profInfoLName.setText(selectedPerson.getLastName());
                profInfoType.setValue(selectedPerson.getType());


            }
            return null;
        });

    }

    private void setChildVisiblity(Boolean state, Node... args) {
        for (Node arg : args) {
            arg.setVisible(state);
            arg.setManaged(state);
        }

    }

    private void resourcePersonDiffView(Person selectedItem) {
        Dialog dlg = new Dialog();
        HBox mainPane = new HBox();
        String title = selectedItem.getLastName()
                .concat(", ")
                .concat(selectedItem.getFirstName())
                .concat(selectedItem.getType());
        dlg.setTitle(title + "?");
        ArrayList<Resource> diffResArr = new ArrayList<>();

        //TODO :: add naming consistency ---- Rajashow
        ListView<Resource> profResources = new ListView<>();
        ListView<Resource> allResources = new ListView<>();
        ListView<Resource> diffResources = new ListView<>();
        setCellFactoryForProfDiffvView(profResources);
        setCellFactoryForProfDiffvView(allResources);
        setCellFactoryForProfDiffvView(diffResources);

        try

        {
// Do not delete this
//            profResources.getItems().addAll(selectedPerson.getResources());
//            allResources.getItems().addAll(resList);
//            diffResArr.addAll( DBManager.getAllResourcesNeededForPerson(selectedItem));
//            diffResArr.removeAll(selectedPerson.getResources());
//            diffResources.getItems().addAll(diffResArr);


            com.mbox.Person tempPerson = DBManager.setResourcesForPerson(selectedItem.initPersonBackend());
            selectedItem = tempPerson.initPersonGUI();
            if (selectedItem.getResources() != null) {
                profResources.getItems().addAll(selectedItem.getResources());
            }

            ArrayList<Resource> allRequiredResources = DBManager.getAllResourcesNeededForPerson(selectedItem);
            if (allRequiredResources != null) {
                allResources.getItems().addAll(allRequiredResources);
                diffResources.getItems().addAll(DBManager.findDifferene(selectedItem, allRequiredResources));
            }


        } catch (
                Exception ex)

        {
            if (debugging)
                System.out.print(ex.getMessage());
        }


        Label professorSResourcesLbl = new Label(selectedItem.getLastName().concat("'s Resources"));
        Label resourcesLbl = new Label("Required Resources");
        Label diffResourcesLbl = new Label("Required Difference ");

        professorSResourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        resourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        diffResourcesLbl.setStyle("-fx-text-fill: white;-fx-font-weight: bold;");
        mainPane.getChildren().

                addAll(
                        new VBox(5, professorSResourcesLbl, profResources),
                        new

                                VBox(5, resourcesLbl, allResources),
                        new

                                VBox(5, diffResourcesLbl, diffResources)
                );
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setStyle("-fx-border-radius: 10px;");
        for (
                Node temp : mainPane.getChildren())

        {
            if (temp.getClass().equals(VBox.class)) {
                VBox child = (VBox) temp;
                child.setAlignment(Pos.CENTER);
                child.setStyle("-fx-background-color: grey;-fx-border-color: black;");

            }

        }
        dlg.getDialogPane().

                setContent(mainPane);
        dlg.getDialogPane().

                getButtonTypes().

                addAll(ButtonType.CLOSE);
        dlg.show();
    }

    private void setCellFactoryForProfDiffvView(ListView<Resource> diffListview) {
        diffListview.setCellFactory(new Callback<ListView<Resource>, ListCell<Resource>>() {
            @Override
            public ListCell<Resource> call(ListView<Resource> param) {
                return new ListCell<Resource>() {
                    @Override
                    protected void updateItem(Resource item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                };
            }
        });
    }

    private void addNewProfessor(String firstName, String lastName, Object type) {
        Person tempNewPerson = new Person(firstName, lastName, (String) type);
        profList.add(tempNewPerson);
    }

    private void showPersonsResources(Person selectedItem) {
        VBox mainPane = new VBox();
        Dialog dlg = new Dialog();
        TitledPane resourceTitlePane = new TitledPane();
        VBox resourceEditPane = resourceDetailedView();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        selectedPerson = selectedItem;


        ButtonType assign = new ButtonType("Assign the Selected Resources", ButtonBar.ButtonData.OK_DONE);

        resourceTable.getItems().clear();
        com.mbox.Person tempPerson = DBManager.setResourcesForPerson(selectedItem.initPersonBackend());
        selectedItem = tempPerson.initPersonGUI();
        selectedPerson = selectedItem;
        if (selectedItem.getResources() != null) {
            resourceTable.getItems().addAll(selectedItem.getResources());
        }
        //updateRowSelected();


        resourceTitlePane.setContent(resourceEditPane);
        resourceTitlePane.setText("Resource Details and Management");
        resourceTitlePane.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(new HBox(resourceTitlePane, resourceTable));
        mainPane.setAlignment(Pos.CENTER);


        dlg.setTitle("Assigning Resource");
        dlg.setHeaderText("Assigning Resource for " + selectedItem.getFirstName() + " " + selectedPerson.getLastName());

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(400);


        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                DBManager.insertPersonResources(selectedPerson);

//                selectedItem.getResources().clear();
//                selectedItem.getResources().addAll(resourceTable.getItems());

                return null;
            }
            return null;
        });

    }

    private void deleteProfessor(Person selectedPerson) {
        DBManager.deletePerson(selectedPerson);
        profList.remove(selectedPerson);

    }

    public void selectCourse() {
        //TODO: add the template information transfer  to course functionality
        ArrayList<Course> tempCourses = new ArrayList<>();
        templateList = DBManager.convertArrayCCBasic(DBManager.getCourseFromTable());
        VBox mainAddPane = new VBox(2);

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox<Course> courseTemplates = new ComboBox();
        setCourseTemplatesCellValue(courseTemplates);
        courseTemplates.getItems().addAll(templateList);
        Label currentCBoxLbl = new Label("Course Templates : ");
        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
                    deleteCourseTemplate(courseTemplates.getSelectionModel().getSelectedItem());
                    courseTemplates.getItems().clear();
                    courseTemplates.getItems().addAll(templateList);
                }
        );
        courseTemplates.setOnAction(e -> {


            courseTemplates.getSelectionModel().getSelectedItem();
        });
        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, courseTemplates),
                deleteBtn
        );
        mainAddPane.setSpacing(20);
        mainAddPane.setAlignment(Pos.CENTER);
        dlg.setTitle("Assigning Course");
        dlg.setHeaderText("Assigning Course");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(fill, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == fill) {

                selectedCourseTemplate = courseTemplates.getSelectionModel().getSelectedItem();

                courseInfoDescrip.setText(selectedCourseTemplate.getDescription());
                courseInfoTitle.setText(selectedCourseTemplate.getTitle());
                courseInfoDepart.setText(selectedCourseTemplate.getDepartment());

                //TODO:// uncomment when resources added
//                ArrayList<Resource> tempRes = selectedCourseTemplate.getResource();
//                resourceTable.getItems().clear();
//                resourceTable.getItems().addAll(resList);
//                resInfoList.getItems().clear();
//                resourceTable.getSelectionModel().select(null);
//                for (int i = 0; i < tempRes.size(); i++) {
//                    if (i < 3) {
//                        resInfoList.getItems().add(tempRes.get(i).getTitle());
//                    }
//                    resourceTable.getSelectionModel().select(tempRes.get(i));
//                }
            }
            return null;
        });


    }

    private void deleteCourseTemplate(Course selectedCourseTemplate) {

        System.out.println(selectedCourseTemplate.toString());
        DBManager.delete_course(selectedCourseTemplate);
        templateList.remove(selectedCourseTemplate);
        courseList.remove(selectedCourseTemplate);
        selectedCourseTemplate = null;
        selectedCourse = null;
        updateCourseTable();

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
        CheckBox resNPubInfo = new CheckBox("  All the resources data, associate with publisher");
        CheckBox personWResInfo = new CheckBox("All the Person with resources associated");
        CheckBox courseWResInfo = new CheckBox(" All the courses with resources associated.");


        Button exportNSave = new Button("Export");
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
        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        dlg.show();
        dlg.setResultConverter(dialogButton -> {

            return null;
        });


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
            //todo:: modifity this for person with resources
            saveFile(DBManager.exportCSVPersonResources(), exportFile);

        }
        if (checkBoxes[3]) {
            File exportFile = pickSaveFile("All Courses with Resources");
            saveFile(DBManager.exportCSVCourseResources(), exportFile);

        }
    }

    private File pickSaveFile(String titleActionText) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(titleActionText);
        fileChooser.setTitle("Exporting" + titleActionText);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);


        if (file != null) {
            return file;
        }
        return null;
    }

    private void saveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void importData() {
    }

    public void oldsearch() {

        //        String semester = semesterComBox.getValue().toString();
//        String year = yearComBox.getValue().toString();
//        String fName, lName;
//        fName = lName = "";
//
//        String[] temp = profSearchTF.getText().split(" ");
//        switch (temp.length) {
//            case 2:
//                lName = temp[1];
//            case 1:
//                fName = temp[0];
//                break;
//            default:
//                System.err.print("Improper input for professor search output");
//
//        }

        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS

        //courseList = DBManager.searchByNameCourseList(fName, lName, semester, year);
//        System.out.println(courseList.size());
//        updateCourseTable();
    }


}

