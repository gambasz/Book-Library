
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Controller {
    ArrayList<Course> table_data;
    @FXML
    TextField crnInfoTf, nameInfoTf, departInfoTf, crnSearchTF, profSearchTF, courseSearchTF, departSearchTF, resourceSearchTF;
    @FXML
    Label crnInfoLbl;
    @FXML
    Button searchBtn, crnInfoBtn, resEditBtn, addBtn, commitBtn, deleteBtn;
    @FXML
    TableView<Course> tableTV;
    @FXML
    TableColumn<Course, String> semesterCol, profCol, courseCol, resourceCol;

    @FXML
    ComboBox semesterComBox, semesterComBoxEdit;
    @FXML
    ComboBox yearComBox, yearComBoxEdit;
    @FXML
    VBox Cock;


    @FXML
    public void initialize() {
        Cock.setVisible(false);
        Cock.setMaxWidth(0);
        initComboBoxes();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        init_tables();


    }


    private void initComboBoxes() {
        semesterComBox.setItems(FXCollections.observableArrayList("Fall", "Winter", "Spring", "Summer"));
        ArrayList<String> years = new ArrayList<>();
        for (int i = 1920; i < 2018; i++)
            years.add("" + i);
        yearComBox.setItems(FXCollections.observableArrayList(years));
    }

    public void getStuff(Course person) {

        try {

            Connection con = establishDB();

            Statement st = con.createStatement();

            String selectquery = "SELECT * FROM PERSON WHERE  IDTMP !=1";

            ResultSet rs = st.executeQuery(selectquery);

            while (rs.next()) {

                person.setIntstutor(rs.getString(3) + " " + rs.getString(4));
            }

            st.close();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void getCourse(Course ct) {

        try {

            Connection con = establishDB();

            Statement st = con.createStatement();

            String selectquery = "SELECT * FROM COURSECT WHERE IDTMP=1";

            ResultSet rs = st.executeQuery(selectquery);

            while (rs.next()) {

                ct.setTitle(rs.getString(2));
            }
            st.close();


        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void getResource(Course r) {

        try {

            Connection con = establishDB();

            Statement st = con.createStatement();

            String selectquery = "SELECT * FROM RESOURCES WHERE IDTMP=1";

            ResultSet rs = st.executeQuery(selectquery);

            while (rs.next()) {
                r.setResource(rs.getString(2));

            }

            st.close();


        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }


    public static Connection establishDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:USERNAME/PASSWORD@HOST:PORT:SID");
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void search() {

        ArrayList<Course> temp_table = new ArrayList<>();
        tableTV.getItems().clear();
        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS


        updateTable(temp_table);

    }

    public void updateRowSelected() {
        tableTV.getSelectionModel().getSelectedItems();

    }

    private void updateTable(ArrayList<Course> temp_table) {

    }

    public void add() {

        System.out.println("Added to the db");


    }


    public void apply() {
    }


    private void init_tables() {

//        Course temp = new Course("Fall", "CMSC 140", "java", "Webb");
       for (int i = 0 ; i < 2;i++) {
           Course c1 = new Course();
           getResource(c1);
           getCourse(c1);
           getStuff(c1);
           tableTV.getItems().add(c1);
       }

    }


    private void setCellValueOfColumns() {

        profCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("intstutor"));
        courseCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("title"));
        semesterCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("Semester"));
        resourceCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("resource"));

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
//        VBox mainPane = new VBox();
//        VBox resourcePane = new VBox(5);
//        VBox publisherPane = new VBox(5);
//        Dialog dlg = new Dialog();
//        TitledPane resourceTitlePane = new TitledPane();
//        TitledPane publisherTitlePane = new TitledPane();
//
//        ImageView icon = new ImageView(this.getClass().getResource("/media/icon.png").toString());
//        icon.setFitHeight(75);
//        icon.setFitWidth(75);
//
//        ComboBox listOFResources = new ComboBox();
//        TextField nameBTF = new TextField();
//        TextField authorTF = new TextField();
//        TextArea descriptionRTa = new TextArea();
//        TextField idRTf = new TextField();
//
//        ComboBox typeBox = new ComboBox();
//
//        typeBox.setItems(FXCollections.observableArrayList("Z", "Mc Only", "Software", "Book"));
//
//        Label nameRLbl = new Label("Title: ");
//        Label authorLbl = new Label("Author: ");
//        Label typeLbl = new Label("Type: ");
//        Label descriptionRLbl = new Label("Description: ");
//        Label idRLbl = new Label("ID: ");
//
//
//        Label currentCBoxLbl = new Label("Current Resources as Template: ");
//
//        ButtonType assign = new ButtonType("Assign the  Selected Resources", ButtonBar.ButtonData.OK_DONE);
//        Button addNAssignNewResource = new Button("Add and Assign new Resource");
//        resourcePane.getChildren().addAll(
//                new HBox(currentCBoxLbl, listOFResources),
//                new HBox(idRLbl, idRTf),
//                new HBox(nameRLbl, nameBTF),
//                new HBox(authorLbl, authorTF),
//                new HBox(typeLbl, typeBox),
//                new HBox(descriptionRLbl, descriptionRTa));
//        resourcePane.setSpacing(20);
//
//        ComboBox listOfPublisher = new ComboBox();
//        TextField namePTf = new TextField();
//        TextArea descriptionPTa = new TextArea();
//        TextArea contactsPTa = new TextArea();
//
//        descriptionPTa.setPrefWidth(150);
//        descriptionRTa.setPrefWidth(150);
//        contactsPTa.setPrefWidth(150);
//        descriptionPTa.setPrefHeight(100);
//        descriptionRTa.setPrefHeight(100);
//        contactsPTa.setPrefHeight(100);
//
//        Label listOfPublisherLbl = new Label("Current Publishers as Template: ");
//        Label namePLbl = new Label("Name: ");
//        Label descriptionPLbl = new Label("Description: ");
//        Label contactsPTLbl = new Label("Contacts: ");
//
//        publisherPane.getChildren().addAll(
//                new HBox(listOfPublisherLbl, listOfPublisher),
//                new HBox(namePLbl, namePTf),
//                new HBox(descriptionPLbl, descriptionPTa),
//                new HBox(contactsPTLbl, contactsPTa)
//
//        );
//        publisherTitlePane.setContent(publisherPane);
//        publisherTitlePane.setAlignment(Pos.CENTER);
//        publisherTitlePane.setCollapsible(false);
//        publisherTitlePane.setText(" PUBLISHER INFO ");
//
//        resourceTitlePane.setContent(resourcePane);
//        resourceTitlePane.setAlignment(Pos.CENTER);
//        resourceTitlePane.setCollapsible(false);
//        resourceTitlePane.setText(" RESOURCE INFO ");
//        resourceTitlePane.setPrefWidth(300);
//
//        mainPane.getChildren().addAll(new HBox(resourceTable,resourceTitlePane, publisherTitlePane),addNAssignNewResource);
//        mainPane.setAlignment(Pos.CENTER);
//
//        dlg.setTitle("Assigning Resource");
//        dlg.setHeaderText("Assigning Resource");
//
//        dlg.setGraphic(icon);
//        dlg.getDialogPane().setMinWidth(300);
//
//
//        dlg.getDialogPane().setContent(mainPane);
//        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);
//
//
//        dlg.show();
//        dlg.setResultConverter(dialogButton -> {
//            if (dialogButton == assign) {
//                System.out.print("Assign new resources");
//                return null;
//            }
//            return null;
//        });
    }

    public void selectProf() {

    }

    public void add_btn_mal() {
        VBox mainPane = new VBox();

        Dialog dlg = new Dialog();


        TextField nameBTF = new TextField();
        TextField authorTF = new TextField();


        ComboBox typeBox = new ComboBox();


        Label nameRLbl = new Label("Title: ");
        Label authorLbl = new Label("Author: ");
        Label typeLbl = new Label("Type: ");


        ButtonType assign = new ButtonType("ADD", ButtonBar.ButtonData.OK_DONE);


        TextField c = new TextField();
        TextField r = new TextField();
        TextField p = new TextField();


        Label cl = new Label("Title: ");
        Label pl = new Label("Person: ");
        Label rl = new Label("Resource: ");


        mainPane.getChildren().addAll(
                new HBox(pl, p),
                new HBox(cl, c),
                new HBox(rl, r));

        mainPane.setAlignment(Pos.CENTER);

        dlg.setTitle("Add");
        dlg.setHeaderText("Add");

        dlg.getDialogPane().setMinWidth(300);


        dlg.getDialogPane().setContent(mainPane);
        dlg.getDialogPane().getButtonTypes().addAll(assign, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == assign) {
                System.out.print(c.getText());
                return null;
            }
            return null;
        });


    }
}
