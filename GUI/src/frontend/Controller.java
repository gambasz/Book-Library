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
import javafx.util.StringConverter;

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
    private ArrayList<Course> courseList, templateList;
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
        String semester = semesterComBox.getValue().toString();
        String year = yearComBox.getValue().toString();
        String fName, lName;
        fName = lName = "";

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

        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS

        courseList = DBManager.searchByNameCourseList(fName, lName, semester, year);
        System.out.println(courseList.size());
        updateCourseTable();

    }

    public void updateRowSelected() {
        selectedCourse = tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1);
        if (selectedCourse != null) {
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);
            updateBtn.setManaged(true);
            deleteBtn.setManaged(true);
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

        selectedPerson = new Person(selectedPerson);

        ArrayList<Resource> tempRes = new ArrayList<Resource>(resourceTable.getSelectionModel().getSelectedItems());
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


        System.out.println("PrfoessorID before adding" + tempCour.getProfessor().getID());
        courseList.add(tempCour); // later on it should be gone, nothing should not be in courseList manually
        // Everything in coureseList should be get from the DB.

        DBManager.relationalInsertByID2(tempCour);

        updateCourseTable();
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
        setTablesSelectionProperty(tableTV);
        setTablesSelectionProperty(resourceTable);
        //todo: when hash tables are done remove the *contains codes*
        //======================BEGIN CODE BACKEND
        DBManager.openConnection();


        ArrayList<Course> pulledDatabase = DBManager.returnEverything(52);
        if(pulledDatabase==null){
            showError("Connection Error","Server did not return any  data","The returnEverything did not return any course");
            pulledDatabase = new ArrayList<>();
        }
        for (int k = 0; k < pulledDatabase.size(); k++) {
            courseList.add(pulledDatabase.get(k));
            for (Resource r : pulledDatabase.get(k).getResource()) {
                if (!resList.contains(r))
                    resList.add(r);

            }
            System.out.println("If this is the error, desription:" + pulledDatabase.get(k).getDescription() + pulledDatabase.get(k).getDepartment());

        }


        for (int i = 0; i < com.mbox.DBManager.getPersonFromTable().size(); i++) {
            if (!profList.contains(com.mbox.DBManager.getPersonFromTable().get(i).initPersonGUI()))
                profList.add(com.mbox.DBManager.getPersonFromTable().get(i).initPersonGUI());
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

        DBManager.delete_relation_course(selectedCourse);
        courseList.remove(selectedCourse);
        selectedCourse = null;
        updateCourseTable();
        //needs to show in the gui

    }

    public void update() {
        Person tempPerson = new Person(selectedPerson);
        courseList.remove(selectedCourse);

        ArrayList<Resource> tempRes = new ArrayList<Resource>(resourceTable.getSelectionModel().getSelectedItems());
        Course tempCour = new Course(
                selectedCourse.getID(),
                tableTV.getSelectionModel().getSelectedItems().get(tableTV.getSelectionModel().getSelectedItems().size() - 1).getID(),
                Integer.parseInt(yearComBoxEdit.getSelectionModel().getSelectedItem().toString()),
                semesterComBoxEdit.getSelectionModel().getSelectedItem().toString(),
                selectedCourse.getTitle(),
                selectedCourse.getDepartment(),
                tempPerson, selectedCourse.getDescription(), tempRes);

        System.out.println("PrfoessorID before changing" + tempCour.getProfessor().getID());


        // I create a new object and then check if the information in the boxes are different form
        // the Selected Course Object, then if there is a difference, I will add a new course/person/...
        boolean courseChanged = false, professorChanged = false, resourceChanged = false;

        if (tempPerson.getFirstName().equals(profInfoFName.getText()) &&
                tempPerson.getLastName().equals(profInfoLName.getText())) {

            professorChanged = false;
            System.out.println(professorChanged);

        } else {
            professorChanged = true;
            System.out.println(professorChanged);
            System.out.println("=======================");
            System.out.println(tempPerson.getFirstName());
            System.out.println(profInfoFName.getText());
            System.out.println("=======================");
            System.out.println(tempPerson.getLastName());
            System.out.println(profInfoLName.getText());
            System.out.println("=======================");
        }

        if (tempCour.getTitle().equals(courseInfoTitle.getText()) &&
                tempCour.getDescription().equals(courseInfoDepart.getText()) &&
                tempCour.getDepartment().equals(courseInfoDescrip.getText())) {

            courseChanged = false;
            System.out.println(courseChanged);

        } else {
            courseChanged = true;
            System.out.println(courseChanged);
            System.out.println("=======================");
            System.out.println(tempCour.getTitle());
            System.out.println(courseInfoTitle.getText());
            System.out.println("=======================");
            System.out.println(tempCour.getDescription());
            System.out.println(courseInfoDepart.getText());
            System.out.println("=======================");
            System.out.println(tempCour.getDepartment());
            System.out.println(courseInfoDescrip.getText());
            System.out.println("=======================");
        }


//        professorChanged = tempCour.getProfessor().getFirstName() != profInfoFName.getText() ||
//                tempCour.getProfessor().getLastName() != profInfoLName.getText();
//
//        courseChanged = tempCour.getTitle() != courseInfoTitle.getText() || tempCour.getDescription() != courseInfoDepart.getText() ||
//                tempCour.getDepartment() != courseInfoDescrip.getText();

        System.out.println(tempCour.getTitle() + " and " + courseInfoTitle.getText());
        System.out.println(tempCour.getDescription() + " and " + courseInfoDescrip.getText());
        System.out.println(tempCour.getDepartment() + " and " + courseInfoDepart.getText());
        //TODO: Check again why the courseChanged keeps turning true @@ Khanh
        // I don't check type rn. Need to check later with fk.. enum :))
        if (courseChanged) {
            tempCour.setDepartment(courseInfoDepart.getText());
            tempCour.setTitle(courseInfoTitle.getText());
            tempCour.setDescription(courseInfoDescrip.getText());
            DBManager.updateCourseQuery(tempCour);
            System.out.println("Fixed this because there is no change");
        }
        if (professorChanged) {
            tempCour.getProfessor().setFirstName(profInfoFName.getText());
            tempCour.getProfessor().setLastName(profInfoLName.getText());
            tempCour.getProfessor().setType(profInfoType.getSelectionModel().getSelectedItem().toString());
            DBManager.updatePersonQuery(tempPerson);
        }


        System.out.println("PrfoessorID after being changed, it should be the same" + tempCour.getProfessor().getID());
        courseList.add(tempCour);// later on it should be gone, nothing should not be in courseList manually
        // Everything in coureseList should be get from the DB.

        updateCourseTable();
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
                selectedPublisher = new Publisher(nameTF.getText(), contactsTF.getText(), descriptionTF.getText());
                pubList.add(selectedPublisher);
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
    }

    private void deleteResource(TextField titleTF, TextField authorTF, TextField idTF, TextField totalAmTF, TextField currentAmTF, TextField descriptionTF, Button publisherBtn, ComboBox typeCB, Button addNAssignNewResource, Button delete, Button update) {
        ArrayList<Resource> temp = new ArrayList<>();
        temp.addAll(resourceTable.getSelectionModel().getSelectedItems());
        for (Resource r : temp) {
            resList.remove(r);
            resourceTable.getItems().remove(r);
            resInfoList.getItems().remove(r.getTitle());
            for (Course c : courseList) {
                c.getResource().remove(r);
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
        resList.add(temp);
        resourceTable.getItems().add(temp);
        selectedPublisher = tempPub;
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
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);


        ButtonType assign = new ButtonType("Assign the  Selected Resources", ButtonBar.ButtonData.OK_DONE);

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
                selectedCourse.getResource().clear();
                selectedCourse.getResource().addAll(resourceTable.getItems());
                resInfoList.getItems().clear();
                for (Resource r : selectedCourse.getResource())
                    resInfoList.getItems().add(r.getTitle());

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
        ComboBox<Person> currentProfessors = new ComboBox();
        currentProfessors.getItems().addAll(profList);

        Label currentCBoxLbl = new Label("Current Professor : ");
        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);
        Button PersonResources = new Button("View Person's Resources");

        Button deleteBtn = new Button("Delete");
        PersonResources.setVisible(false);
        PersonResources.setManaged(false);
        currentProfessors.setOnAction(e -> {
            System.out.print(currentProfessors.getSelectionModel().getSelectedItem());
            if (currentProfessors.getSelectionModel().getSelectedItem() != null) {
                PersonResources.setVisible(true);
                PersonResources.setManaged(true);

            } else {
                PersonResources.setVisible(false);
                PersonResources.setManaged(false);

            }
        });
        deleteBtn.setOnAction(e -> {
            deleteProfessor(currentProfessors.getSelectionModel().getSelectedItem());
            currentProfessors.getItems().clear();
            currentProfessors.getItems().addAll(profList);

        });
        PersonResources.setOnAction(e -> {
            ArrayList<Resource> tempRes =new ArrayList<>();
            tempRes.addAll(resList);
            System.out.print(tempRes);
            currentProfessors.getSelectionModel().getSelectedItem().setResources(tempRes);
            showPersonsResources(currentProfessors.getSelectionModel().getSelectedItem());
        });
        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, currentProfessors),
                new HBox(deleteBtn, PersonResources)

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

    private void showPersonsResources(Person selectedItem) {
        VBox mainPane = new VBox();
        Dialog dlg = new Dialog();
        TitledPane resourceTitlePane = new TitledPane();
        VBox resourceEditPane = resourceDetailedView();
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);


        ButtonType assign = new ButtonType("Assign the  Selected Resources", ButtonBar.ButtonData.OK_DONE);

        resourceTable.getItems().clear();
        if (selectedItem.getResources() != null)
            resourceTable.getItems().addAll(selectedItem.getResources());
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
                selectedItem.getResources().clear();
                selectedItem.getResources().addAll(resourceTable.getItems());

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

                selectedCourse = courseTemplates.getSelectionModel().getSelectedItem();
                courseInfoDescrip.setText(selectedCourse.getDescription());
                courseInfoTitle.setText(selectedCourse.getTitle());
                courseInfoDepart.setText(selectedCourse.getDepartment());
                //TODO:// uncomment when resources added
//                ArrayList<Resource> tempRes = selectedCourse.getResource();
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

        templateList.remove(selectedCourseTemplate);

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

    protected static void showError(String title, String headerMessage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
