import data.Course;
import data.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    ComboBox semesterComBox, semesterComBoxEdit, yearComBox, yearComBoxEdit, profInfoType, listOfCurrentProf;
    @FXML
    CheckBox profCB, courseCB, departCB, resCB;

    /**
     * This is initializes the start state.
     * The initial state includes:
     * - values for the combo boxes
     * - value factory for the columns based on the Object "Course"
     * - initial values to display on the table
     * - initializes the resource table
     * - marking all the filter checkboxes to true
     */
    @FXML
    public void initialize() {
        courseList = new ArrayList<>();
        profList = new ArrayList<>();
        resList = new ArrayList<>();
        initComboBoxes();
        initResourcesTable();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resourceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTables();
        initCheckBoxes();
        addButtonGraphics();
        resetSelect();

    }

    private void addButtonGraphics() {
        ImageView searchImg = new ImageView("/media/search.png");
        addGraphicToButtons(searchImg, searchBtn);
        ImageView addImg = new ImageView("/media/add.png");
        addGraphicToButtons(addImg, addBtn);
        ImageView deleteImg = new ImageView("/media/delete.png");
        addGraphicToButtons(deleteImg, deleteBtn);
        ImageView updateImg = new ImageView("/media/upload.png");
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
        resourceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /**
     * Initialize values for the combo boxes.
     * <p>
     * For the semester combo boxes add the semesters.
     * <p>
     * For the year combo boxes add the all the years since 1946 to next year
     */
    private void initComboBoxes() {
        semesterComBox.setItems(FXCollections.observableArrayList(Semester.values()));
        semesterComBoxEdit.setItems(FXCollections.observableArrayList(Semester.values()));
        ArrayList<String> years = new ArrayList<>();
        for (int i = 1946; i < Calendar.getInstance().get(Calendar.YEAR) + 1; i++)
            years.add("" + i);
        yearComBox.setItems(FXCollections.observableArrayList(years));
        yearComBoxEdit.setItems(FXCollections.observableArrayList(years));
        profInfoType.setItems(FXCollections.observableArrayList(PersonType.values()));
    }


    public void search() {

        ArrayList<Course> temp_table = new ArrayList<>();
        tableTV.getItems().clear();
        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS


        updateTable(temp_table);

    }

    public void updateRowSelected() {
        Course temp = tableTV.getSelectionModel().getSelectedItems().get(0);
        if (temp != null) {
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);
            updateBtn.setManaged(true);
            deleteBtn.setManaged(true);
            courseInfoCRN.setText("" + temp.getCRN());
            profInfoFName.setText(temp.getProfessor().getFirstName());
            profInfoLName.setText(temp.getProfessor().getLastName());
            profInfoType.setValue(temp.getProfessor().getType());
            courseInfoTitle.setText(temp.getTitle());
            courseInfoDepart.setText(temp.getDepartment());
            semesterComBoxEdit.getSelectionModel().select(temp.getSEMESTER());
            yearComBoxEdit.getSelectionModel().select(new Integer(temp.getYEAR()));
            ArrayList<Resource> tempRes = temp.getResource();
            System.out.println(tempRes);
            System.out.println(resourceTable.getItems());

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

    private void updateTable(ArrayList<Course> temp_table) {

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

        System.out.println("Added to the db");


    }


    public void apply() {

    }

    /**
     * populates the table with initial values
     */
    private void initTables() {
        tableTV.setOnMouseClicked(e -> {
            updateRowSelected();
            e.consume();

        });
        ArrayList<Resource> arr = new ArrayList<>();
        Resource r = new Resource("h", 1, "automate the boring stuff with python", null, "me", "something", true);
        arr.add(r);
        Person p = new Person("P", "R", 1, PersonType.CourseCoordinator);
        Course c = new Course(0, 10, 1999, "fall", "CMSC 140", "CS", p, "something about the course", arr);
        tableTV.getItems().add(c);
        profList.add(p);
        resList.add(r);
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

    /**
     * Assign a new Resources to Course.
     * It creates Resource objects and assign the resources as the Professor for the course object
     */
    public void modifyResources() {
        //TODO: migrate Resource add and modify window
        //TODO: migrate Publisher add and modify window
        //TODO: Add Functionally and support to the resource manager
        //TODO: Add Functionally and support to the publisher manager

        VBox mainPane = new VBox();
        VBox resourcePane = new VBox(5);
        VBox publisherPane = new VBox(5);
        Dialog dlg = new Dialog();
        TitledPane resourceTitlePane = new TitledPane();
        TitledPane publisherTitlePane = new TitledPane();

        ImageView icon = new ImageView(this.getClass().getResource("/media/icon.png").toString());
        icon.setFitHeight(75);
        icon.setFitWidth(75);
        ComboBox listOFResources = new ComboBox();
        listOFResources.setItems(FXCollections.observableArrayList(resList));
        resourceTable.getItems().clear();
        resourceTable.getItems().addAll(resList);
        TextField nameBTF = new TextField();
        TextField authorTF = new TextField();
        TextArea descriptionRTa = new TextArea();
        TextField idRTf = new TextField();

        ComboBox typeBox = new ComboBox();

        typeBox.setItems(FXCollections.observableArrayList("Z", "Mc Only", "Software", "Book"));

        Label nameRLbl = new Label("Will be moved in the next overhaul Title: ");
        Label authorLbl = new Label("Will be moved in the next overhaul Author: ");
        Label typeLbl = new Label("Will be moved in the next overhaul Type: ");
        Label descriptionRLbl = new Label("Will be moved in the next overhaul Description: ");
        Label idRLbl = new Label("Will be moved in the next overhaul ID: ");


        Label currentCBoxLbl = new Label("Current Resources as Template: ");

        ButtonType assign = new ButtonType("Assign the  Selected Resources", ButtonBar.ButtonData.OK_DONE);
        Button addNAssignNewResource = new Button("Add and Assign new Resource");
        resourcePane.getChildren().addAll(
                new HBox(currentCBoxLbl, listOFResources),
                new HBox(idRLbl, idRTf),
                new HBox(nameRLbl, nameBTF),
                new HBox(authorLbl, authorTF),
                new HBox(typeLbl, typeBox),
                new HBox(descriptionRLbl, descriptionRTa));
        resourcePane.setSpacing(20);

        ComboBox listOfPublisher = new ComboBox();
        TextField namePTf = new TextField();
        TextArea descriptionPTa = new TextArea();
        TextArea contactsPTa = new TextArea();

        descriptionPTa.setPrefWidth(150);
        descriptionRTa.setPrefWidth(150);
        contactsPTa.setPrefWidth(150);
        descriptionPTa.setPrefHeight(100);
        descriptionRTa.setPrefHeight(100);
        contactsPTa.setPrefHeight(100);

        Label listOfPublisherLbl = new Label("Current Publishers");
        Label namePLbl = new Label("Will be moved in the next overhaul Name: ");
        Label descriptionPLbl = new Label("Will be moved in the next overhaul Description: ");
        Label contactsPTLbl = new Label("Will be moved in the next overhaul Contacts: ");

        publisherPane.getChildren().addAll(
                new HBox(listOfPublisherLbl, listOfPublisher),
                new HBox(namePLbl, namePTf),
                new HBox(descriptionPLbl, descriptionPTa),
                new HBox(contactsPTLbl, contactsPTa),
                new HBox(new Button("Add new or modify publishers"))

        );
        publisherTitlePane.setContent(publisherPane);
        publisherTitlePane.setAlignment(Pos.CENTER);
        publisherTitlePane.setCollapsible(false);
        publisherTitlePane.setText(" temp PUBLISHER INFO:  needs to be extracted  ");

        resourceTitlePane.setContent(resourcePane);
        resourceTitlePane.setAlignment(Pos.CENTER);
        resourceTitlePane.setCollapsible(false);
        resourceTitlePane.setText(" Temp :RESOURCE INFO:");
        resourceTitlePane.setPrefWidth(300);

        mainPane.getChildren().addAll(new HBox(resourceTable, resourceTitlePane, publisherTitlePane), addNAssignNewResource);
        mainPane.setAlignment(Pos.CENTER);

        dlg.setTitle("Assigning Resource");
        dlg.setHeaderText("Assigning Resource");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);


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

        ImageView icon = new ImageView(this.getClass().getResource("/media/icon.png").toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        listOfCurrentProf = new ComboBox();
        listOfCurrentProf.setItems(FXCollections.observableArrayList(profList));

        Label currentCBoxLbl = new Label("Current Professor : ");

        ButtonType fill = new ButtonType("Fill", ButtonBar.ButtonData.OK_DONE);


        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, listOfCurrentProf)
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
                profInfoFName.setText(((Person) (listOfCurrentProf.getSelectionModel().getSelectedItem())).getFirstName());
                profInfoLName.setText(((Person) (listOfCurrentProf.getSelectionModel().getSelectedItem())).getLastName());
                profInfoType.setValue(((Person) (listOfCurrentProf.getSelectionModel().getSelectedItem())).getType());
            }
            return null;
        });

    }

}
