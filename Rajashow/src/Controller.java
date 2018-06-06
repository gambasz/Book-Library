import data.Course;
import data.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

    @FXML
    public void initialize() {
        initComboBoxes();
        resourceCol.setSortable(false);
        table_data = new ArrayList<>();
        setCellValueOfColumns();
        tableTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        makeCellsEditable();
        init_tables();
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

    private void makeCellsEditable() {
        courseCol.setCellFactory(TextFieldTableCell.forTableColumn());
        departCol.setCellFactory(TextFieldTableCell.forTableColumn());


    }

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

//        resourceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, Button>, ObservableValue<Button>>() {
//            final TableCell<Person, String> cell = new TableCell<Person, String>() {
//
//                final Button btn = new Button("Just Do It");
//
//                @Override
//            public ObservableValue<Button> call(TableColumn.CellDataFeatures<Course, Button> param) {
//                return ;
//            }
//        });
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
        System.out.print("needs to be implemented");
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
        ComboBox tpyeBox = new ComboBox();

        Label fNameLbl = new Label("Professor's First Name: ");
        Label lNameLbl = new Label("Professor's Last Name: ");
        Label typeLbl = new Label("Type: ");
        Label currentCBoxLbl = new Label("Current Professor templates: ");

        ButtonType assign = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);


        mainAddPane.getChildren().addAll(
                new HBox(currentCBoxLbl, listOfCurrentProf),
                new HBox(fNameLbl, fNameTF),
                new HBox(lNameLbl, lNameTF),
                new HBox(typeLbl, tpyeBox));
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
