import data.Course;
import data.Person;
import data.Publisher;
import data.Resource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Controller {
    ArrayList<Course> table_data;
    @FXML
    TextField crnTF;
    @FXML
    TextField profTF;
    @FXML
    TextField departTF;
    @FXML
    TextField courseTF;
    @FXML
    TextField resourceTF;
    @FXML
    Button searchBtn;
    @FXML
    TableView<Course> tableTV;
    @FXML
    TableColumn<Course, Integer> crnCol;
    @FXML
    TableColumn<Course, Person> profCol;
    @FXML
    TableColumn<Course, String> courseCol;
    @FXML
    TableColumn<Course, String> departCol;
    @FXML
    TableColumn<Course, ArrayList> resourceCol;
    @FXML
    ComboBox semesterComBox, semesterComBoxEdit;
    @FXML
    ComboBox yearComBox, yearComBoxEdit;

    private TableView resourceTable;
    TableColumn<Resource, Publisher> publisherCol;
    TableColumn<Resource, String> nameCol, authorCol, idcCol;

    @FXML
    public void initialize() {
        initComboBoxes();
        resourceCol.setSortable(false);
        table_data = new ArrayList<>();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        makeCellsEditable();
        init_tables();
        initResourcesTable();

    }

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

    private void initComboBoxes() {
        semesterComBox.setItems(FXCollections.observableArrayList("Fall", "Winter", "Spring", "Summer"));
        semesterComBoxEdit.setItems(FXCollections.observableArrayList("Fall", "Winter", "Spring", "Summer"));
        ArrayList<String> years = new ArrayList<>();
        for (int i = 1920; i < 2018; i++)
            years.add("" + i);
        yearComBox.setItems(FXCollections.observableArrayList(years));
        yearComBoxEdit.setItems(FXCollections.observableArrayList(years));
    }


    public void search() {

        ArrayList<Course> temp_table = new ArrayList<>();
        String crn = crnTF.getText();
        String professor = profTF.getText();
        String department = departTF.getText();
        String course = courseTF.getText();
        String resource = resourceTF.getText();

        tableTV.getItems().clear();
        tableTV.getItems().add(table_data.get(Integer.parseInt(crn)));
        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS
//        System.out.print(crn + "||" + professor + "||" + department + "||" + course + "||" + resource);

        // make sure the resulting sql query is turned into array list of courses

//        ResultSet rs= st.executeQuery(query1);
//        while(rs.next()) {
//        data.Course temp = new data.Course();
//        tableTV.getItems().add(temp);
        //     }


//        System.out.print(  ((TableColumn)tableTV.getColumns().get(0)).getWidth());

        updateTable(temp_table);

    }

    public void updateRowSelected() {

    }

    private void updateTable(ArrayList<Course> temp_table) {

    }

    public void add() {

        System.out.println("Added to the db");


    }


    public void apply() {
    }


    private void init_tables() {
        ArrayList<String> books = new ArrayList<>();
        books.add("major");
//        Course temp = new Course(0, "bob", "bob", "bob", books);
//        table_data.add(temp);

        for (int i = 1; i < 10; i++) {
//            table_data.add(temp);
        }
        tableTV.getItems().addAll(table_data);

    }

//    private void makeCellsEditable() {
//        courseCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        departCol.setCellFactory(TextFieldTableCell.forTableColumn());
//
//
//    }

    private void setCellValueOfColumns() {
        crnCol.setCellValueFactory(
                new PropertyValueFactory<Course, Integer>("CRN"));
        profCol.setCellValueFactory(
                new PropertyValueFactory<Course, Person>("professor"));
        courseCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("name"));
        departCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("department"));
        resourceCol.setCellValueFactory(
                new PropertyValueFactory<Course, ArrayList>("resource"));


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

    public void modifyResources() {
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
        TextField nameBTF = new TextField();
        TextField authorTF = new TextField();
        TextArea descriptionRTa = new TextArea();
        TextField idRTf = new TextField();

        ComboBox typeBox = new ComboBox();

        typeBox.setItems(FXCollections.observableArrayList("Z", "Mc Only", "Software", "Book"));

        Label nameRLbl = new Label("Title: ");
        Label authorLbl = new Label("Author: ");
        Label typeLbl = new Label("Type: ");
        Label descriptionRLbl = new Label("Description: ");
        Label idRLbl = new Label("ID: ");


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

        Label listOfPublisherLbl = new Label("Current Publishers as Template: ");
        Label namePLbl = new Label("Name: ");
        Label descriptionPLbl = new Label("Description: ");
        Label contactsPTLbl = new Label("Contacts: ");

        publisherPane.getChildren().addAll(
                new HBox(listOfPublisherLbl, listOfPublisher),
                new HBox(namePLbl, namePTf),
                new HBox(descriptionPLbl, descriptionPTa),
                new HBox(contactsPTLbl, contactsPTa)

        );
        publisherTitlePane.setContent(publisherPane);
        publisherTitlePane.setAlignment(Pos.CENTER);
        publisherTitlePane.setCollapsible(false);
        publisherTitlePane.setText(" PUBLISHER INFO ");

        resourceTitlePane.setContent(resourcePane);
        resourceTitlePane.setAlignment(Pos.CENTER);
        resourceTitlePane.setCollapsible(false);
        resourceTitlePane.setText(" RESOURCE INFO ");
        resourceTitlePane.setPrefWidth(300);

        mainPane.getChildren().addAll(new HBox(resourceTable,resourceTitlePane, publisherTitlePane),addNAssignNewResource);
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
                System.out.print("Assign new resources");
                return null;
            }
            return null;
        });
    }


    public void selectProf() {
        VBox mainAddPane = new VBox(2);

        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource("/media/icon.png").toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        ComboBox listOfCurrentProf = new ComboBox();
        TextField fNameTF = new TextField();
        TextField lNameTF = new TextField();
        ComboBox typeBox = new ComboBox();
        typeBox.setItems(FXCollections.observableArrayList("Program Cord.", "Course Cord.", " Course Instruc  tor"));
        Label fNameLbl = new Label("Professor's First Name: ");
        Label lNameLbl = new Label("Professor's Last Name: ");
        Label typeLbl = new Label("Type: ");
        Label currentCBoxLbl = new Label("Current Professor templates: ");

        ButtonType assign = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);


        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, listOfCurrentProf),
                new HBox(fNameLbl, fNameTF),
                new HBox(lNameLbl, lNameTF),
                new HBox(typeLbl, typeBox));
        mainAddPane.setSpacing(20);

        dlg.setTitle("Assigning Professor");
        dlg.setHeaderText("Assigning Professor");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                System.out.print("Assign new prof");
                return null;
            }
            return null;
        });

    }

}
